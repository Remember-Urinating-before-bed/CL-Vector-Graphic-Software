package hk.edu.polyu.comp.comp2021.clevis.model;

import java.util.Arrays;

public class Line extends Shape{

    private double x1, y1, x2, y2;


    public Line(String name, double x1, double y1, double x2, double y2) {
        countIndex++;
        setName(name);
        setx1(x1);
        setx2(x2);
        sety1(y1);
        sety2(y2);
    }

    public Line(double x1, double y1, double x2, double y2) {
        setx1(x1);
        setx2(x2);
        sety1(y1);
        sety2(y2);
    }

    public double[] boundingBox() {
        double x, y, w, h;

        w = Math.abs(getx1() - getx2());
        h = Math.abs(gety1() - gety2());
        x = Math.min(getx1(), getx2());
        y = Math.max(gety1(), gety2());

        return new double[]{x, y, w, h};
    }

    public void move(double dx, double dy) {
        setx1(x1 + dx);
        sety1(y1 + dy);
        setx2(x2 + dx);
        sety2(y2 + dy);
    }

    public boolean pickMovable(double x, double y){

        double a = y1 - y2;
        double b = x2 -x1;
        double c = x1 * y2 - x2 * y1;

        // point line distance
        double distance = Math.abs((a*x + b*y + c) / Math.sqrt(Math.pow(a,2) + Math.pow(b,2)));
        return 0 <= distance && distance <= 0.05;
    }

    public void delete(Shape[] shapes, int index) {
        shapes[index] = null;
    }

    public void list() {
        setIndent();
        System.out.printf("%sName of line: %s%n", getIndent(), getName());
        System.out.printf("%s  Coordinates of first point:  (%.2f, %.2f)%n", getIndent(), x1, y1);
        System.out.printf("%s  Coordinates of second point: (%.2f, %.2f)%n%n", getIndent(), x2, y2);
    }

    public String getValues() {
        // this method is for testing purpose only
        return Arrays.toString((new double[]{getx1(), gety1(), getx2(), gety2()}));
    }

    public double getx1(){
        return x1;
    }
    public double getx2(){
        return x2;
    }
    public double gety1(){
        return y1;
    }
    public double gety2(){
        return y2;
    }
    public void setx1(double x1){
        this.x1= x1;
    }
    public void setx2(double x2){
        this.x2= x2;
    }
    public void sety1(double y1){
        this.y1= y1;
    }
    public void sety2(double y2){
        this.y2= y2;
    }

}
