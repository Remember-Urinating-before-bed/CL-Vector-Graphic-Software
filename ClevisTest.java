package clevis.model;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import static org.junit.Assert.*;

public class ClevisTest {

    Shape[] shapes = new Shape[100];

    Shape line1 = new Line("line1", 3, 3, 1, 1);
    Shape line2 = new Line("line2", 2, 0, 4, 4);
    Shape line3 = new Line("line3", 4, -2, 1, 7);
    Shape line4 = new Line("line4", 0, 1, 2.3, 3.1);

    Shape square1 = new Square("square1", 3, 3, 4);

    Shape cir1 = new Circle("cir1", 4, 4, 1);
    Shape cir2 = new Circle("cir2", 7, 4, 2);
    Shape cir3 = new Circle("cir3", 4, 2, 4);

    Shape rec1 = new Rectangle("rec1", 1, 6, 1, 2);
    Shape rec2 = new Rectangle("rec2", 2, 0, 2, 1);

    Shape group1 = new Group("group1");
    Shape group2 = new Group("group2");
    Shape group3 = new Group("group3");

    @Before
    public void prepare() {
        shapes[0] = line1;
        shapes[1] = line2;
        shapes[2] = line3;
        shapes[3] = line4;
        shapes[4] = square1;
        shapes[5] = cir1;
        shapes[6] = cir2;
        shapes[7] = cir3;
        shapes[8] = rec1;
        shapes[9] = rec2;
        shapes[10] = group1;
        shapes[11] = group2;
        shapes[12] = group3;
        ((Group) group1).group(new String[]{"group", "group1", "line1", "line2", "cir1"}, shapes);
        ((Group) group2).group(new String[]{"group", "group2", "line1", "line4"}, shapes);

        Shape.countIndex = 13;
        System.out.println("------------------------------After Initialization------------------------------");
    }

    @Test
    public void testConstructor(){
        assertEquals("[1.0, 6.0, 1.0, 2.0]", rec1.getValues());
        assertEquals("[0.0, 1.0, 2.3, 3.1]", line4.getValues());
        assertEquals("[4.0, 4.0, 1.0]", cir1.getValues());
        assertEquals("[3.0, 3.0, 4.0]", square1.getValues());

    }

    @Test
    public void testGroup() {
        assertEquals(line1, ((Group) group1).getMember(0));
        assertEquals(line2, ((Group) group1).getMember(1));
        assertEquals(3, ((Group) group1).getMemberLength());
        assertEquals("[3.0, 3.0, 1.0, 1.0][2.0, 0.0, 4.0, 4.0][4.0, 4.0, 1.0]", group1.getValues());

        // only line4 was added to group2 as line1 was grouped in group1
        assertEquals(line4, ((Group) group2).getMember(0));
        assertEquals(1, ((Group) group2).getMemberLength());
        assertEquals("[0.0, 1.0, 2.3, 3.1]", group2.getValues());

        ((Group) group3).group(new String[]{"group", "group3", "group1", "group2", "cir2"}, shapes);
        assertEquals("[3.0, 3.0, 1.0, 1.0][2.0, 0.0, 4.0, 4.0][4.0, 4.0, 1.0][0.0, 1.0, 2.3, 3.1][7.0, 4.0, 2.0]", group3.getValues());
    }

    @Test
    public void testLineIntersect() {
        assertFalse(Shape.intersect2Lines((Line) line1, (Line) line2));
        assertTrue(Shape.intersect2Lines((Line) line2, (Line) line3));
        assertTrue(Shape.intersect2Lines((Line) line1, (Line) line3));
        assertTrue(Shape.intersect2Lines((Line) line4, (Line) line3));
        assertFalse(Shape.intersect2Lines((Line) line4, (Line) line2));


//        assertEquals(2, Shape.orientation(((Line) line3).getx1(), ((Line) line3).gety1(), ((Line) line3).getx2(), ((Line) line3).gety2(), ((Line) line4).getx1(), ((Line) line4).gety1()));
//        assertEquals(0, Shape.orientation(((Line) line3).getx1(), ((Line) line3).gety1(), ((Line) line3).getx2(), ((Line) line3).gety2(), ((Line) line4).getx2(), ((Line) line4).gety2()));
//        assertTrue(Shape.pointOnSegment((Line) line3, ((Line) line4).getx2(), ((Line) line4).gety2()));
    }

    @Test
    public void testCircleIntersect() {
        assertTrue(Shape.intersect2Circles((Circle) cir1, (Circle) cir2));
        assertFalse(Shape.intersect2Circles((Circle) cir3, (Circle) cir1));
        assertTrue(Shape.intersect2Circles((Circle) cir3, (Circle) cir2));

    }

    @Test
    public void testLineIntersectCircle() {
        assertTrue(Shape.lineIntersectCircle((Line) line2, (Circle) cir1));
        assertTrue(Shape.lineIntersectCircle((Line) line3, (Circle) cir3));
        assertTrue(Shape.lineIntersectCircle((Line) line4, (Circle) cir3));
        assertFalse(Shape.lineIntersectCircle((Line) line1, (Circle) cir3));
        assertFalse(Shape.lineIntersectCircle((Line) line1, (Circle) cir1));
    }

    @Test
    public void testIntersectSimple() {
        assertFalse(Shape.intersectSimple(line1, line2));

        assertTrue(Shape.intersectSimple(cir1, cir2));
        assertFalse(Shape.intersectSimple(cir3, cir1));
        assertTrue(Shape.intersectSimple(cir3, cir2));

        assertTrue(Shape.intersectSimple(line2, cir1));
        assertTrue(Shape.intersectSimple(line3, cir3));
        assertTrue(Shape.intersectSimple(line4, cir3));
        assertFalse(Shape.intersectSimple(line1, cir3));
        assertFalse(Shape.intersectSimple(line1, cir1));
        assertFalse(Shape.intersectSimple(cir1, line1));

        assertTrue(Shape.intersectSimple(rec1, line3));
        assertTrue(Shape.intersectSimple(line3, rec1));
        assertTrue(Shape.intersectSimple(square1, line3));
        assertTrue(Shape.intersectSimple(line3, square1));
        assertFalse(Shape.intersectSimple(line4, square1));
        assertFalse(Shape.intersectSimple(square1, line4));

        assertFalse(Shape.intersectSimple(rec1, square1));
        assertTrue(Shape.intersectSimple(rec2, square1));

        assertTrue(Shape.intersectSimple(square1, cir1));
        assertTrue(Shape.intersectSimple(cir1, square1));

    }

    @Test
    public void testIntersect() {
        // most tests are from testIntersectSimple()
        assertFalse(Shape.intersect(line1, line2));

        assertTrue(Shape.intersect(cir1, cir2));
        assertFalse(Shape.intersect(cir3, cir1));

        assertTrue(Shape.intersect(line2, cir1));
        assertTrue(Shape.intersect(cir1, line2));
        assertFalse(Shape.intersect(line1, cir3));
        assertFalse(Shape.intersect(cir3, line1));

        assertFalse(Shape.intersect(line4, square1));
        assertFalse(Shape.intersect(square1, line4));

        assertFalse(Shape.intersect(rec1, square1));
        assertTrue(Shape.intersect(cir1,square1));
        assertFalse(Shape.intersect(rec1, cir1));
        assertFalse(Shape.intersect(cir1, rec1));

        ((Group) group3).group(new String[]{"group", "group3", "group2", "square1"}, shapes);
        assertTrue(Shape.intersect(group1, group3));
        assertFalse(Shape.intersect(group1, group2));

    }

    @Test
    public void testList() {
        group1.listShape();
        line1.listShape();
        group2.listShape();
        line3.listShape();

        group3.listShape();
        ((Group) group3).group(new String[]{"group", "group3", "group1", "group2", "cir2"}, shapes);
        group3.listShape();
    }

    @Test
    public void testBoundingBox() {
        String expectedBox1 = "[1.0, 5.0, 4.0, 5.0]";
        double[] box1 = group1.boundingBox();
        assertEquals(expectedBox1, Arrays.toString(box1));

        String expectedBox2 = "[0.0, 3.1, 2.3, 2.1]";
        double[] box2 = group2.boundingBox();
        assertEquals(expectedBox2, Arrays.toString(box2));

        ((Group) group3).group(new String[]{"group", "group3", "group1", "group2", "cir2"}, shapes);
        String expectedBox3 = "[0.0, 6.0, 9.0, 6.0]";
        double[] box3 = group3.boundingBox();
        assertEquals(expectedBox3, Arrays.toString(box3));

        String expectedBox4 = "[3.0, 3.0, 4.0, 4.0]";
        double[] box4 = square1.boundingBox();
        assertEquals(expectedBox4, Arrays.toString(box4));

    }

    @Test
    public void testListAll() {
        Shape.listAll(shapes);
    }

    @Test
    public void testMoveShape() {
        line1.moveShape(-1.5, 2.1);
        line1.setGrouped(false);
        line1.moveShape(-1.5, 2.1);

        String expectedLine1 = "[1.5, 5.1, -0.5, 3.1]";
        assertEquals(expectedLine1, line1.getValues());


        cir1.setGrouped(false);
        rec1.setGrouped(false);
        square1.setGrouped(false);

        cir1.moveShape(4.3, -1);
        String expectedCir1 = "[8.3, 3.0, 1.0]";
        assertEquals(expectedCir1, cir1.getValues());

        rec1.moveShape(0.5, 1);
        String expectedRec1 = "[1.5, 7.0, 1.0, 2.0]";
        assertEquals(expectedRec1, rec1.getValues());

        square1.moveShape(1, 1);
        String expectedSq1 = "[4.0, 4.0, 4.0]";
        assertEquals(expectedSq1, square1.getValues());

        ((Group) group3).group(new String[]{"group", "group3", "group1", "group2", "cir2"}, shapes);

        // before move                    line1               line2             cir1             line4             cir2
        String expectedGroup3 = "[1.5, 5.1, -0.5, 3.1][2.0, 0.0, 4.0, 4.0][8.3, 3.0, 1.0][0.0, 1.0, 2.3, 3.1][7.0, 4.0, 2.0]";
        assertEquals(expectedGroup3, group3.getValues());
        group3.moveShape(2, -1);

        // after move                     line1                line2              cir1             line4             cir2
                expectedGroup3 = "[3.5, 4.1, 1.5, 2.1][4.0, -1.0, 6.0, 3.0][10.3, 2.0, 1.0][2.0, 0.0, 4.3, 2.1][9.0, 3.0, 2.0]";
        assertEquals(expectedGroup3, group3.getValues());
    }

    @Test
    public void testDelete(){

        ((Group) group3).group(new String[]{"group", "group3", "group1", "cir3", "rec2"}, shapes);

        // circle
        // validation
        Shape.deleteShape(new String[]{"delete", "cir100"}, shapes);
        assertEquals(cir3, shapes[7]);

        Shape.deleteShape(new String[]{"delete", "cir3"}, shapes);
        assertEquals(cir3, shapes[7]);

        // actual deletion
        Shape.deleteShape(new String[]{"delete", "cir2"}, shapes);
        assertNotEquals(cir2,shapes[6]);
        assertEquals(cir3, shapes[6]);
        assertEquals(-1, Shape.search(shapes, "cir2"));
        assertEquals(12, Shape.countIndex);

        // line
        // validation
        Shape.deleteShape(new String[]{"delete", "line100"}, shapes);
        assertEquals(line4, shapes[3]);

        Shape.deleteShape(new String[]{"delete", "line4"}, shapes);
        assertEquals(line4, shapes[3]);

        // actual deletion
        Shape.deleteShape(new String[]{"delete", "line3"}, shapes);
        assertNotEquals(line3, shapes[2]);
        assertEquals(line4, shapes[2]);
        assertEquals(-1, Shape.search(shapes, "line3"));
        assertEquals(11, Shape.countIndex);

        // rectangle
        // validation
        Shape.deleteShape(new String[]{"delete", "rec100"}, shapes);
        assertEquals(rec2, shapes[7]);

        Shape.deleteShape(new String[]{"delete", "rec2"}, shapes);
        assertEquals(rec2, shapes[7]);

        // actual deletion
        Shape.deleteShape(new String[]{"delete", "rec1"}, shapes);
        assertNotEquals(rec1, shapes[6]);
        assertEquals(rec2, shapes[6]);
        assertEquals(-1, Shape.search(shapes, "rec1"));
        assertEquals(10, Shape.countIndex);

        // group
        // validation
        assertFalse(Shape.deleteShape(new String[]{"delete", "rec100"}, shapes));
        assertEquals(group3, shapes[9]);

        Shape.deleteShape(new String[]{"delete", "rec2"}, shapes);
        assertEquals(group3, shapes[9]);


        // actual deletion wth group in group
        assertTrue(Shape.deleteShape(new String[]{"delete", "group3"}, shapes));
        assertNotEquals(group3, shapes[9]);
        assertEquals(3, Shape.countIndex);
        System.out.println(shapes[2].getName());
        Shape.deleteShape(new String[] {"delete", "group2"}, shapes);
        assertNull(shapes[3]);
        assertEquals(-1, Shape.search(shapes, "group3"));
    }

    @Test
    public void testUngroup(){
        ((Group) group3).group(new String[]{"group", "group3", "group1", "cir3", "rec2"}, shapes);

        // group
        // validation
        assertFalse(Group.ungroupShape(new String[]{"ungroup", "cir100"}, shapes));
        assertEquals(group3, shapes[12]);

        Group.ungroupShape(new String[]{"ungroup", "cir3"}, shapes);
        assertEquals(group3, shapes[12]);

        //actual ungroup wth group in group
        assertTrue(Group.ungroupShape(new String[]{"ungroup", "group3"}, shapes));
        assertNotEquals(group3, shapes[12]);
        assertEquals(group2, shapes[10]);
        assertFalse(line1.getGrouped());
        assertEquals(-1, Shape.search(shapes, "group1"));
        assertEquals(-1, Shape.search(shapes, "group3"));
    }

    @Test
    public void testPickMove(){
        ((Group) group3).group(new String[]{"group", "group3", "group1", "cir3", "rec2"}, shapes);

        // validation
        assertFalse(Shape.pickMoveShape(100, 100, 1, 1, shapes));

        // actual pickMove
        Shape.pickMoveShape(3.02, 2.98, 1 ,1, shapes);
        String expectedSquare1 = "[4.0, 4.0, 4.0]";
        assertEquals(expectedSquare1, square1.getValues());

        // actual pickMove
        Shape.pickMoveShape(7, 6.04, 2, 2, shapes);
        String expectedCir2 = "[9.0, 6.0, 2.0]";
        assertEquals(expectedCir2, cir2.getValues());

        // actual pickMove
        Shape.pickMoveShape(1.01, 7.01, 3, 3, shapes);
        String expectedLine3 = "[7.0, 1.0, 4.0, 10.0]";
        assertEquals(expectedLine3, line3.getValues());

        // actual pickMove group in group
        Shape.pickMoveShape(8.01, 2, 2, 2, shapes);
        String expectedGroup3 = "[5.0, 5.0, 3.0, 3.0][4.0, 2.0, 6.0, 6.0][6.0, 6.0, 1.0][6.0, 4.0, 4.0][4.0, 2.0, 2.0, 1.0]";
        assertEquals(expectedGroup3, group3.getValues());

    }

    @Test
    public void main() {
        Shape.countIndex = 0;
        String[] testArr = {
                "rectangle rec1 5 8 4 7",
                "undo",
                "redo",
                "rectangle rec1 5 8 4 7",

                "circle cir1 9 6 1",
                "undo",
                "redo",
                "circle cir1 9 6 1",

                "line line1 4 2 6 5",
                "undo",
                "redo",
                "line line1 4 2 6 5",

                "square square1 1 1 1",
                "undo",
                "redo",
                "square square1 1 1 1",

                // group
                "group group1 rec1 line1 cir1",
                "undo",
                "redo",
                "group group1 rec1",
                "group group1 rec1 cir1",

                // ungroup
                "ungroup group1",
                "ungroup group2",
                "undo",
                "redo",

                // listAll
                "listAll",

                // list
                "list nnn",
                "list square1",

                // bounding box
                "boundingbox square1",
                "boundingbox group100",

                // move
                "move nnn 11 11",
                "move rec1 1 1",
                "undo",
                "redo",
                "move square1 1 1",
                "undo",
                "redo",
                "move circle1 1 1",
                "undo",
                "redo",
                "move line1 1 1",
                "undo",
                "redo",

                // pick move
                "pick-and-move 1 1 1 1",
                "undo",
                "redo",
                "pick-and-move 100 100 1 1",

                // intersect
                "intersect square1 cir1",
                "intersect rec1 line1",
                "intersect rec2000 line1",

                // delete
                "delete rec1",
                "undo",
                "redo",
                "delete square1",
                "undo",
                "redo",
                "delete cir1",
                "undo",
                "redo",
                "delete line1",
                "undo",
                "redo",
                "group group1 rec1 cir1",
                "delete group1",
                "undo",
                "redo",
                "quit"};

        Clevis clevis = new Clevis("log", "log", 100, testArr);
    }
}
