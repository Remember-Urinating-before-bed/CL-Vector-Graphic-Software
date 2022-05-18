package clevis.model;
import java.util.Arrays;

public class Rectangle extends Shape{

    private double x, y, w, h;
    public Line[] lines = new Line[4];

    public Rectangle() {}

    public Rectangle(String name, double x, double y, double w, double h) {
        countIndex++;
        setName(name);
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        lines[0] = new Line(x, y, x + w, y);
        lines[1] = new Line(x + w, y, x + w, y - h);
        lines[2] = new Line(x + w, y - h, x, y - h);
        lines[3] = new Line(x, y - h, x, y);
    }

    public double[] boundingBox() {
        double x, y, w, h;

        x = this.x;
        y = this.y;
        w = this.w;
        h = this.h;

        return new double[]{x, y, w, h};
    }

    public void move(double dx, double dy) {
        for (Line line : lines) line.move(dx, dy);
        setX(x + dx);
        setY(y + dy);
    }

    public boolean pickMovable(double x, double y){
        // suppose y down = y+
        double centerX = this.x + w/2;
        double centerY = this.y + h/2;
        double slope = (centerY - y) / (centerX - x);
        double distance = Math.sqrt(Math.pow(centerX - x, 2) + Math.pow(centerY - y, 2));

        double topX = (this.y - centerY) / slope + centerX;
        double bottomX = (this.y + h - centerY) / slope + centerX;
        double leftY = slope * (this.x - centerX) + centerY;
        double rightY = slope * (this.x + w - centerX) + centerY;

        double topDistance = Math.sqrt(Math.pow(centerX - topX, 2) + Math.pow(centerY - this.y, 2));
        double bottomDistance = Math.sqrt(Math.pow(centerX - bottomX, 2) + Math.pow(centerY - this.y - h, 2));
        double leftDistance = Math.sqrt(Math.pow(centerX - this.x, 2) + Math.pow(centerY - leftY, 2));
        double rightDistance = Math.sqrt(Math.pow(centerX - topX - w, 2) + Math.pow(centerY - rightY, 2));
        if (0 <= distance - topDistance && distance - topDistance <= 0.05){
            return true;
        }
        else if (0 <= distance - bottomDistance && distance - bottomDistance <= 0.05){
            return true;
        }
        else if (0 <= distance - leftDistance && distance - leftDistance <= 0.05){
            return true;
        }
        else return 0 <= distance - rightDistance && distance - rightDistance <= 0.05;
    }

    public void delete(Shape[] shapes, int index) {
        shapes[index] = null;
    }
        
    
    public void list() {
        setIndent();
        System.out.printf("%sName of rectangle: %s%n", getIndent(), getName());
        System.out.printf("%s  Coordinates of top-left corner: (%.2f, %.2f)%n", getIndent(), x, y);
        System.out.printf("%s  Width:  %.2f%n", getIndent(), w);
        System.out.printf("%s  Height: %.2f%n%n", getIndent(), h);
    }

    public String getValues() {
        // this method is for testing purpose only
        return Arrays.toString((new double[]{getX(), getY(), getW(), getH()}));
    }

    public void setX(double x) {this.x = x;}
    public void setY(double y) {this.y = y;}
    public void setW(double w) {this.w = w;}
    public void setH(double h) {this.h = h;}
    public double getX() {return x;}
    public double getY() {return y;}
    public double getW() {return w;}
    public double getH() {return h;}
}
