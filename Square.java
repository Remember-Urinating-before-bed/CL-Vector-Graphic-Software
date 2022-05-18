package clevis.model;

import java.util.Arrays;

public class Square extends Rectangle{

    private final double l;

    public Square(String name, double x, double y, double l) {
        countIndex++;
        setName(name);
        setX(x);
        setY(y);
        setW(l);
        setH(l);
        this.l = l;
        lines[0] = new Line(x, y, x + l, y);
        lines[1] = new Line(x + l, y, x + l, y - l);
        lines[2] = new Line(x + l, y - l, x, y - l);
        lines[3] = new Line(x, y - l, x, y);
    }

    public void list() {
        setIndent();
        System.out.printf("%sName of square: %s%n", getIndent(), getName());
        System.out.printf("%s  Coordinates of top-left corner: (%.2f, %.2f)%n", getIndent(), getX(), getY());
        System.out.printf("%s  Length: %.2f%n%n", getIndent(), l);
    }

    public String getValues() {
        // this method is for testing purpose only
        return Arrays.toString((new double[]{getX(), getY(), l}));
    }
}
