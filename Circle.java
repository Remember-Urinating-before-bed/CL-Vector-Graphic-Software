package clevis.model;

import java.util.Arrays;

public class Circle extends Shape{

    private double x, y;
    private final double r;

    public Circle(String name, double x, double y, double r){
        countIndex++;
        setName(name);
        this.x = x;
        this.y = y;
        this.r = r;
    }

    public double[] boundingBox() {
        double x, y, w, h;

        x = this.x - r;
        y = this.y + r;
        w = 2 * r;
        h = 2 * r;

        return new double[]{x, y, w, h};
    }

    public void move(double dx, double dy) {
        x += dx;
        y += dy;
    }

    public boolean pickMovable(double x, double y){
        double length = Math.sqrt(Math.pow(this.x - x, 2) + Math.pow(this.y - y, 2));
        return r <= length && length < r + 0.05;
    }

    public void delete(Shape[] shapes, int index) {
        shapes[index] = null;
    }

    public void list() {
        setIndent();
        System.out.printf("%sName of circle: %s%n", getIndent(), getName());
        System.out.printf("%s  Coordinates of centre: (%.2f, %.2f)%n", getIndent(), x, y);
        System.out.printf("%s  Radius: %.2f%n%n", getIndent(), r);
    }

    public String getValues() {
        // this method is for testing purpose only
        return Arrays.toString((new double[]{getX(), getY(), getR()}));
    }

    public double getX() {return x;}
    public double getY() {return y;}
    public double getR() {return r;}
}
