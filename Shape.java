package hk.edu.polyu.comp.comp2021.clevis.model;

import java.util.ArrayList;

public class Shape {

    private String name;
    private String indent = "";
    private Boolean grouped = false;
    public static int countIndex = 0;
    final static double THRESHOLD = 0.00000001;

    public Shape() {}

    public void ungroup(Shape[] shapes, int index){}
        // empty method to be overridden

    public double[] boundingBox() { return new double[]{}; }
        // empty method to be overridden

    public void delete(Shape[] shapes, int index) {}
        // empty method to be overridden

    public static boolean deleteShape(String[] inputArr, Shape[] shapes) {
        int index = Shape.search(shapes, inputArr[1]);
        if (index == -1) {
            System.out.println(inputArr[1] + " cannot be found, operation \"delete\" cannot be done.");
            return false;
        } else if (shapes[index].getGrouped()){
            System.out.println(inputArr[1] + " is grouped, operation \"delete\" cannot be done.");
            return false;
        } else {
            System.out.println(shapes[index].getName() + " is deleted.\n");
            shapes[index].delete(shapes, index);
            rmNull(shapes);
            return true;
        }
    }

    public boolean pickMovable(double x, double y) { return false; }
        // empty method to be overridden

    public static boolean pickMoveShape(double x, double y, double dx, double dy, Shape[] shapes){
        for (int i = Shape.countIndex - 1; i >= 0; i--){
            if (!shapes[i].getGrouped()){
                // if shape[i] is a group, use recursion to compare all members
                if (shapes[i] instanceof Group){
                    if (((Group) shapes[i]).pickMove(x, y, dx, dy, shapes)){
                        shapes[i].moveShape(dx, dy);
                        return true;
                    }
                }
                // if shape[i] has no group
                else {
                    // shape [i] can be moved under x, y
                    if (shapes[i].pickMovable(x, y)){
                        shapes[i].moveShape(dx, dy);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void move(double dx, double dy) {}
        // empty method to be overridden
    
    public boolean moveShape(double dx, double dy) {
        // separate move function is for input validation
        if (getGrouped()) {
            System.out.println(getName() + " is grouped before, operation \"move\" cannot be done.\n");
            return false;
        } else {
            // returns true if move successfully
            move(dx, dy);
            return true;
        }
    }

    public static int orientation(double p1X, double p1Y, double p2X, double p2Y, double p3X, double p3Y) {

        double n =  (p2Y - p1Y) * (p3X - p2X) - (p2X - p1X) * (p3Y - p2Y);

        // as to compare 2 double type, if absolute value smaller than THRESHOLD, automatically equals to 0
        if (Math.abs(n) < THRESHOLD) return 0;
        return (n > 0) ? 1 : 2; // 0 means collinear, 1 means clockwise, 2 means counterclockwise
    }

    public static boolean pointOnSegment(Line line, double x, double y) {
        return x >= Math.min(line.getx1(), line.getx2()) && x <= Math.max(line.getx1(), line.getx2()) &&
                y >= Math.min(line.gety1(), line.gety2()) && y <= Math.max(line.gety1(), line.gety2());
    }

    public static boolean intersect2Lines(Line line1, Line line2) {

        int o1 = orientation(line1.getx1(), line1.gety1(), line1.getx2(), line1.gety2(), line2.getx1(), line2.gety1());
        int o2 = orientation(line1.getx1(), line1.gety1(), line1.getx2(), line1.gety2(), line2.getx2(), line2.gety2());
        int o3 = orientation(line2.getx1(), line2.gety1(), line2.getx2(), line2.gety2(), line1.getx1(), line1.gety1());
        int o4 = orientation(line2.getx1(), line2.gety1(), line2.getx2(), line2.gety2(), line1.getx2(), line1.gety2());

        if (o1 != o2 && o3 != o4) return true; // different orientation means intersect

        if (o1 == 0) return pointOnSegment(line1, line2.getx1(), line2.gety1());

        if (o2 == 0) return pointOnSegment(line1, line2.getx2(), line2.gety2());

        if (o3 == 0) return pointOnSegment(line2, line1.getx1(), line1.gety1());

        if (o4 == 0) return pointOnSegment(line2, line1.getx2(), line1.gety2());

        return false;
    }

    public static boolean intersect2Circles(Circle cir1, Circle cir2) {

        double distanceX = Math.abs(cir1.getX() - cir2.getX());
        double distanceY = Math.abs(cir1.getY() - cir2.getY());
        double distance = Math.hypot(distanceX, distanceY);
        double n1 = Math.abs(cir1.getR() - cir2.getR()); // difference of two radii
        double n2 = cir1.getR() + cir2.getR(); // sum of two radii

        // as to compare 2 double type, +-THRESHOLD can eliminate decimal places issue i.e. 5.99999999999... < 6.0
        return (distance >= n1 - THRESHOLD && distance <= n2 + THRESHOLD);
    }

    public static boolean lineIntersectCircle(Line line, Circle cir) {

        // two points of line are (x1, y1) and (x2, y2), centre and radius of cir are (h, k) and r respectively
        // a point on the line segment can be represented as x = t * x1 + (1 - t) * x2 and y = t * y1 + (1 - t) * y2
        // substitute both equations into equation of circle,
        // we get [(x1 - x2)t + x2 - h]^2 + [(y1 - y2)t + y2 - k]^2 - r^2 = 0
        // for better readability, we have (lt + m)^2 + (nt + o)^2 - r^2 = 0
        // by solving it, we have roots = t1, t2
        // if either t1 or t2 is between 0 and 1, that means the point that intersect circle is on the line segment
        // else, there are no intersections

        double l = line.getx1() - line.getx2(); // l = x1 - x2
        double m = line.getx2() - cir.getX();   // m = x2 - h
        double n = line.gety1() - line.gety2(); // n = y1 - y2
        double o = line.gety2() - cir.getY();   // o = y2 - k

        // basic quadratic formula
        double a = Math.pow(l, 2) + Math.pow(n, 2);                             // a = l^2 + n^2
        double b = 2 * l * m + 2 * n * o;                                       // b = 2lm + 2no
        double c = Math.pow(m, 2) + Math.pow(o, 2) - Math.pow(cir.getR(), 2);   // c = m^2 + o^2 - r^2

        double delta = Math.sqrt(Math.pow(b, 2) - 4 * a * c);
        double t1 = (-b + delta) / (2 * a);
        double t2 = (-b - delta) / (2 * a);

        return ((t1 >= 0 - THRESHOLD && t1 <= 1 + THRESHOLD) || (t2 >= 0 - THRESHOLD && t2 <= 1 + THRESHOLD));
    }

    public static <T1, T2> boolean intersectSimple(T1 shape1, T2 shape2) {

        String twoType = "";
        String type1, type2;

        if (shape1 instanceof Line) {
            type1 = "0";
        } else if (shape1 instanceof Rectangle) {
            type1 = "1";
        } else type1 = "2";

        if (shape2 instanceof Line) {
            type2 = "0";
        } else if (shape2 instanceof Rectangle) {
            type2 = "1";
        } else type2 = "2";

        twoType = type1 + type2;
        // 0: line, 1: rectangle/square, 2: circle

        switch (twoType) {
            case "00":
                // line intersects line
                return intersect2Lines((Line) shape1, (Line) shape2);

            case "01":
                // line intersects rectangle or square
                for (int i = 0; i < 4; i++) {
                    if (intersect2Lines((Line) shape1, ((Rectangle) shape2).lines[i])) return true;
                }
                return false;

            case "02":
                // line intersects circle
                return lineIntersectCircle((Line) shape1, (Circle) shape2);

            case "10":
                // rectangle or square intersects line
                for (int i = 0; i < 4; i++) {
                    if (intersect2Lines((Line) shape2, ((Rectangle) shape1).lines[i])) return true;
                }
                return false;

            case "11":
                // rectangle or square intersects rectangle or square
                for (int i = 0; i < 4; i++) {
                    for (int j = 0; j < 4; j++) {
                        if (intersect2Lines(((Rectangle) shape1).lines[i], ((Rectangle) shape2).lines[j])) return true;
                    }
                }
                return false;

            case "12":
                // not tested yet
                // rectangle or square intersects circle
                for (int i = 0; i < 4; i++) {
                    if (lineIntersectCircle(((Rectangle) shape1).lines[i], (Circle) shape2)) return true;
                }
                return false;

            case "20":
                // not tested yet
                // circle intersects line
                return lineIntersectCircle((Line) shape2, (Circle) shape1);

            case "21":
                // not tested yet
                // circle intersects rectangle or square
                for (int i = 0; i < 4; i++) {
                    if (lineIntersectCircle(((Rectangle) shape2).lines[i], (Circle) shape1)) return true;
                }
                return false;

            case "22":
                return intersect2Circles((Circle) shape1, (Circle) shape2);
        }
        // missing group shape here
        return true;
    }

    public static void addList(ArrayList<Shape> list, Shape shape) {
        if (!(shape instanceof Group)){
            list.add(shape);
        } else
            for (int i = 0; i < ((Group) shape).getMemberLength(); i++)
                addList(list, ((Group) shape).getMember(i));
    }

    public static <T1, T2> boolean intersect(T1 shape1, T2 shape2) {
        if (!(shape1 instanceof Group) && !(shape2 instanceof Group))
            return intersectSimple(shape1, shape2);

        ArrayList<Shape> shape1L = new ArrayList<>();
        ArrayList<Shape> shape2L = new ArrayList<>();

        addList(shape1L, (Shape) shape1);
        addList(shape2L, (Shape) shape2);

        for (Shape members1 : shape1L) {
            for (Shape members2 : shape2L) {
                if (intersectSimple(members1, members2)) return true;
            }
        }
        return false;
    }

    public void list() {}
    // empty method to be overridden

    public boolean listShape() {
        // if shape is grouped, cannot list it directly
        // if shape is ungrouped, list() of the specific dynamic type is called
        // separate list function is for input validation
        if (getGrouped()) {
            System.out.println(getName() + " is grouped, and cannot be listed directly .\n");
            return false;
        } else {
            this.list();
            return true;
        }
    }

    public static void listAll(Shape[] shapes) {
        for (int i = 0; i < Shape.countIndex; i++) if (!shapes[i].getGrouped()) shapes[i].list();
    }

    public String getValues() {
        // this method is for testing purpose only
        // empty method to be overridden
        return null;
    }

    public void setName(String name) {this.name = name;}

    public String getName() {return this.name;}

    public boolean getGrouped() {return this.grouped;}

    public void setGrouped(boolean b) {this.grouped = b;}

    public void setIndent() {
        indent = getGrouped() ? "    " : "";
    }

    public String getIndent() {return indent;}

    public static int search(Shape[] shapes, String name) {
        for (int i = 0; i < Shape.countIndex; i++) {
            if (name.equals(shapes[i].getName())) return i;
        }
        return -1;
    }

    // push null to after non-null shape in shape[]
    public static void rmNull(Shape[] shapes){
        for (int i = 0; i < Shape.countIndex; i++){
            if (shapes[i] == null){
                for (int j = i, n = Shape.countIndex - 1; j < n; j++){
                    shapes[j] = shapes[j+1];
                }
                shapes[countIndex] = null;
                Shape.countIndex--;
            }
        }
    }
}