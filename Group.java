package hk.edu.polyu.comp.comp2021.clevis.model;

public class Group extends Shape{

    private Shape[] members;

    public Group(String name) {
        countIndex++;
        setName(name);
    }

    public void group(String[] inputArr, Shape[] shapes) {

        int inputLength = inputArr.length;
        int length = 0;
        for (int i = 2; i < inputLength; i++) {
            if (Shape.search(shapes, inputArr[i]) != -1 && !shapes[Shape.search(shapes, inputArr[i])].getGrouped()) length++;
        }

        members = new Shape[length];

        for (int i = 0, j = 0; i < inputLength - 2; i++) {

            if (Shape.search(shapes, inputArr[i + 2]) == -1) {
                System.out.println(inputArr[i + 2] + " does not exist.");
            } else {
                Shape memberShape = shapes[Shape.search(shapes, inputArr[i + 2])];
                if (memberShape.getGrouped()) {
                    System.out.println(memberShape.getName() + " is grouped before, operation \"group\" cannot be done.");
                } else {
                    members[j] = memberShape;
                    members[j].setGrouped(true);
                    System.out.println(members[j].getName() + " has been the part of shape: " + getName());
                    j++;
                }
            }
        }
        System.out.println();
    }

    public void delete(Shape[] shapes, int index) {
        shapes[index] = null;
        for (Shape member : members) {
            member.delete(shapes, search(shapes, member.getName()));
            rmNull(shapes);
        }
    }

    public static boolean ungroupShape(String[] inputArr, Shape[] shapes) {
        int index = Shape.search(shapes, inputArr[1]);
        if (index == -1) {
            System.out.println(inputArr[1] + " cannot be found, operation \"ungroup\" cannot be done.");
            return false;
        } else if (shapes[index].getGrouped()){
            System.out.println(inputArr[1] + " is grouped, operation \"ungroup\" cannot be done.");
            return false;
        } else {
            System.out.println(shapes[index].getName() + " is ungrouped.\n");
            shapes[index].ungroup(shapes, index);
            rmNull(shapes);
            return true;
        }
    }

    public void ungroup(Shape[] shapes, int index) {
        shapes[index] = null;
        for (Shape member : members) {
            member.ungroup(shapes, Shape.search(shapes, member.getName()));
            member.setGrouped(false);
            rmNull(shapes);
        }
    }

    public boolean pickMove(double x, double y, double dx, double dy, Shape[] shapes){

        for (Shape member : members){
            if (member instanceof Group){
                ((Group) member).pickMove(x, y, dx, dy, shapes);
            }
            else {
                if (member.pickMovable(x,y)){
                    return true;
                }
            }
        }
        return false;
    }

    public void list() {
        if (members == null) {
            System.out.println(getName() + " does not contain any member shapes currently.\n");
        } else {
            System.out.println("Name of shape: " + getName());
            System.out.println("  Members:");
            for (Shape member : members) {
                if (member instanceof Group) {
                    System.out.println("    Name of group: " + member.getName() + "\n");
                } else member.list();
            }
        }
    }

    public double[] boundingBox() {

        // for shape that has members.length == 1 (group that only contains one shape),
        // the boundingBox() has been called by the member-shape's dynamic type

        if (members.length == 1) {
            return members[0].boundingBox();
        } else {
            return boundingBoxGroup(members[0].boundingBox(), members, 1, members.length);
        }
    }

    public double[] boundingBoxGroup(double[] box, Shape[] memberShapes, int index, int length) {

        double x, y, w, h, wRight, hDown;

        // 0: x, 1: y, 2: w, 3: h
        x = Math.min(box[0], memberShapes[index].boundingBox()[0]);
        y = Math.max(box[1], memberShapes[index].boundingBox()[1]);

        wRight = Math.max(box[0] + box[2], memberShapes[index].boundingBox()[0] + memberShapes[index].boundingBox()[2]);
        w = wRight - x;

        hDown = Math.min(box[1] - box[3], memberShapes[index].boundingBox()[1] - memberShapes[index].boundingBox()[3]);
        h = y - hDown;

        double[] result = new double[]{x, y, w, h};

        if (index < length - 1) {
            return boundingBoxGroup(result, memberShapes, index + 1, length);
        } else return result;
    }

    public void move(double dx, double dy) {
        if (members == null) {
            System.out.println(getName() + " does not contain any member shapes currently.\n");
        } else
            for (int i = 0; i < getMemberLength(); i++) members[i].move(dx, dy);
    }

    public String getValues() {
        // this method is for testing purpose only
        String value = "";
        if (members == null) {
            System.out.println("empty group shape");
        } else for (Shape member : members) value += member.getValues();

        return value;
    }

    public Shape getMember(int i) {
        return members[i];
    }

    public int getMemberLength() {
     return this.members.length;
     }
}
