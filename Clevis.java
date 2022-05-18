package hk.edu.polyu.comp.comp2021.clevis.model;
import java.util.ListIterator;
import java.util.ArrayList;
import java.util.Scanner;
import java.lang.*;
import java.util.*;
import java.io.*;

public class Clevis {
    public Clevis(String htmlname, String txtname, int size, String[] testArr) {

        inittxt(txtname);
        inithtml(htmlname);
        int htmlcount = 1;

        Stack<String> undo = new Stack<String>();
        ArrayList<String> shapehistory = new ArrayList<String>();
        Stack<String> redo = new Stack<String>();

        Shape[] shapes = new Shape[size];
        // default we create storage size of 100 shapes
        boolean exit = false;
        int index = 0;
        boolean exist = false;
        String input;
        int i = 0;

        while (!exit) {
            System.out.print("Please enter the command here: ");

            if (testArr.length == 0) {
                Scanner scan = new Scanner(System.in);
                input = scan.nextLine();
            } else
                input = testArr[i++];

            String[] inputArr = input.split(" ");

            if (inputArr.length > 1) {
                index = Shape.search(shapes, inputArr[1]);
                exist = (index != -1);
            }
            switch (inputArr[0]) {
                case "rectangle":
                    if (exist) {
                        System.out.println(inputArr[1] + " exists, please try enter another name.");
                    } else {
                        shapes[Shape.countIndex] = new Rectangle(inputArr[1], Double.parseDouble(inputArr[2]), Double.parseDouble(inputArr[3]), Double.parseDouble(inputArr[4]), Double.parseDouble(inputArr[5]));
                        System.out.println("Rectangle " + inputArr[1] + " created.\n");
                        shapehistory.add(input);
                        undo.push(input);
                        // txt output
                        txtout(input,txtname);

                        //html output
                        htmlout(input, htmlcount,htmlname);
                        htmlcount += 1;
                        redo.clear();
                    }
                    break;

                case "line":
                    if (exist) {
                        System.out.println(inputArr[1] + " exists, please try enter another name.");
                    }else {
                        shapes[Shape.countIndex] = new Line(inputArr[1], Double.parseDouble(inputArr[2]), Double.parseDouble(inputArr[3]), Double.parseDouble(inputArr[4]), Double.parseDouble(inputArr[5]));
                        System.out.println("Line " + inputArr[1] + " created.\n");
                        shapehistory.add(input);
                        undo.push(input);
                        // txt output
                        txtout(input, txtname);

                        //html output
                        htmlout(input, htmlcount, htmlname);
                        htmlcount += 1;
                        redo.clear();
                    }
                    break;
                case "circle":
                    if (exist) {
                        System.out.println(inputArr[1] + " exists, please try enter another name.");
                    }else {
                        shapes[Shape.countIndex] = new Circle(inputArr[1], Double.parseDouble(inputArr[2]), Double.parseDouble(inputArr[3]), Double.parseDouble(inputArr[4]));
                        System.out.println("Circle " + inputArr[1] + " created.\n");
                        shapehistory.add(input);
                        undo.push(input);
                        // txt output
                        txtout(input, txtname);

                        //html output
                        htmlout(input, htmlcount, htmlname);
                        htmlcount += 1;
                        redo.clear();
                    }
                    break;

                case "square":
                    if (exist) {
                        System.out.println(inputArr[1] + " exists, please try enter another name.");
                    }else{
                        shapes[Shape.countIndex] = new Square(inputArr[1], Double.parseDouble(inputArr[2]), Double.parseDouble(inputArr[3]), Double.parseDouble(inputArr[4]));
                        System.out.println("Square " + inputArr[1] + " created.\n");
                        shapehistory.add(input);
                        undo.push(input);
                        // txt output
                        txtout(input,htmlname);

                        //html output
                        htmlout(input, htmlcount,htmlname);
                        htmlcount += 1;
                        redo.clear();
                    }
                    break;

                case "group":
                    // call group function
                    if (exist) {
                        System.out.println(inputArr[1] + " exists, please try enter another name.");
                    } else {
                        shapes[Shape.countIndex] = new Group(inputArr[1]);
                        ((Group) shapes[Shape.countIndex - 1]).group(inputArr, shapes);
                        undo.push(input);
                        // txt output
                        txtout(input,htmlname);

                        //html output
                        htmlout(input, htmlcount,htmlname);
                        htmlcount += 1;
                        redo.clear();
                    }
                    break;

                case "ungroup":
                    if(Group.ungroupShape(inputArr,shapes)){
                        undo.push(input);
                        // txt output
                        txtout(input,htmlname);

                        //html output
                        htmlout(input, htmlcount,htmlname);
                        htmlcount += 1;
                        redo.clear();
                    }
                    else{
                        System.out.println(inputArr[1] + " not ungrouped");
                    }
                    //if null cant undo
                    break;

                case "delete":
                    // call delete function
                    if (Shape.deleteShape(inputArr,shapes)){
                        //search thearraylist history

                        //iterates the shape history from newest to oldest
                        ListIterator<String> List_Iterator= shapehistory.listIterator(shapehistory.size());
                        while (List_Iterator.hasPrevious()) {
                            String curVal = List_Iterator.previous();
                            if (curVal.contains(inputArr[1])){
                                //push it into undo
                                undo.push("delete "+curVal);
                                break;
                            }
                        }
                        // txt output
                        txtout(input,htmlname);

                        //html output
                        htmlout(input, htmlcount,htmlname);
                        htmlcount += 1;
                        redo.clear();
                    }
                    break;

                case "boundingbox":
                    // "boundingBox() returns double[4] of [x, y, w, h]"
                    if (!exist) {
                        System.out.println(inputArr[1] + " does not exist.");
                    } else {
                        double[] box = shapes[index].boundingBox();
                        System.out.printf("The coordinates of the top-left corner of the bounding box: (%.2f, %.2f) %nWidth: %.2f, height: %.2f%n%n", box[0], box[1], box[2], box[3]);
                        // txt output
                        txtout(input,htmlname);

                        //html output
                        htmlout(input, htmlcount,htmlname);
                        htmlcount += 1;
                    }
                    break;

                case "move":
                    //push move to undo
                    if (!exist) {
                        System.out.println(inputArr[1] + " does not exist.");
                    } else {
                        undo.push(inputArr[0]+" "+inputArr[1]+" "+Double.parseDouble(inputArr[2])*-1+" "+Double.parseDouble(inputArr[3])*-1);
                        // if moveShape returns true, mean "move" has operated successfully.
                        if (shapes[index].moveShape(Double.parseDouble(inputArr[2]), Double.parseDouble(inputArr[3]))) {
                            System.out.println(inputArr[1] + " moved by dx: " + inputArr[2] + " and dy: " + inputArr[3] +".\n");
                            // txt output
                            txtout(input, txtname);

                            //html output
                            htmlout(input, htmlcount, htmlname);
                            htmlcount += 1;
                            redo.clear();
                        }
                    }
                    break;

                case "pick-and-move":
                    if (Shape.pickMoveShape(Double.parseDouble(inputArr[1]), Double.parseDouble(inputArr[2]), Double.parseDouble(inputArr[3]), Double.parseDouble(inputArr[4]), shapes)) {
                        System.out.println("moved by dx: " + inputArr[3] + " and dy: " + inputArr[4] + "\n");

                        double newundox = Double.parseDouble(inputArr[1]) + Double.parseDouble(inputArr[3]);
                        double newundoy = Double.parseDouble(inputArr[2]) + Double.parseDouble(inputArr[4]);
                        //push pickandmove to undo
                        undo.push(inputArr[0] + " " + inputArr[1] + " " + newundox + " " + newundoy + " " + Double.parseDouble(inputArr[3])*-1 + " " + Double.parseDouble(inputArr[4])*-1);
                        // txt output
                        txtout(input, txtname);

                        //html output
                        htmlout(input, htmlcount, htmlname);
                        htmlcount += 1;
                        redo.clear();
                    }
                    break;

                case "intersect":
                    // call the intersect function, intersect n1 n2
                    int shape1Index = Shape.search(shapes, inputArr[1]);
                    int shape2Index = Shape.search(shapes, inputArr[2]);
                    if (shape1Index == -1 || shape2Index == -1 || shapes[shape1Index].getGrouped() || shapes[shape2Index].getGrouped()) {
                        System.out.println("Invalid inputs, at least one of the shape is grouped or not existed.");
                    } else {
                        if (Shape.intersect(shapes[shape1Index], shapes[shape2Index])) {
                            System.out.println(inputArr[1] + " intersects with " + inputArr[2] + "\n");
                        } else
                            System.out.println(inputArr[1] + " does not intersect with " + inputArr[2] + "\n");
                        // txt output
                        txtout(input, txtname);

                        //html output
                        htmlout(input, htmlcount, htmlname);
                        htmlcount += 1;
                    }
                    break;

                case "list":
                    if (!exist) {
                        System.out.println(inputArr[1] + " does not exist.");
                    } else {
                        shapes[index].listShape();
                        // txt output
                        txtout(input, txtname);

                        //html output
                        htmlout(input, htmlcount, htmlname);
                        htmlcount += 1;
                    }
                    break;

                case "listAll":
                    // txt output
                    txtout(input, txtname);

                    //html output
                    htmlout(input, htmlcount, htmlname);
                    htmlcount += 1;
                    Shape.listAll(shapes);
                    break;

                case "quit":
                    exit = true;
                    break;

                case "undo":
                    undo(undo, redo, shapes);
                    // txt output
                    txtout(input, txtname);

                    //html output
                    htmlout(input, htmlcount, htmlname);
                    htmlcount += 1;
                    break;

                case "redo":
                    redo(undo, redo, shapes);
                    // txt output
                    txtout(input, txtname);

                    //html output
                    htmlout(input, htmlcount, htmlname);
                    htmlcount += 1;
                    break;

                default:
                    System.out.println("Invalid command, please enter again.");

            }
        }
    }

    public void inittxt(String filename) {
        try {
            PrintWriter writer = new PrintWriter(filename);
            writer.print("");
            writer.close();
        } catch (Exception e) {
            System.err.println("Error while writing to file: " +
                    e.getMessage());
        }
    }

    public void txtout(String input, String filename) {
        try {
            FileWriter fstream = new FileWriter(filename, true);
            BufferedWriter out = new BufferedWriter(fstream);
            out.write(input + "\n");
            out.close();
        } catch (Exception e) {
            System.err.println("Error while writing to file: " +
                    e.getMessage());
        }
    }

    public void inithtml(String filename) {
        try {
            File fstream = new File(filename);
            BufferedWriter bw = new BufferedWriter(new FileWriter(fstream));
            bw.write("<html><body><h1>Log Output</h1>");
            bw.write("<style>table, th, td {border:1px solid black;}</style>");
            bw.write("<table style=\"width:100%;font-size:20px\">");
            bw.write("<tr>");
            bw.write("<th>Index</th>");
            bw.write("<th>Operation Commands</th>");
            bw.write("</tr>");
            bw.write("<!--head-->");
            bw.write("</table>");
            bw.write("</body></html>");
            bw.close();
        } catch (Exception e) {
            System.err.println("Error while writing to file: " +
                    e.getMessage());
        }
    }

    public void htmlout(String input, int htmlcount, String filename) {
        try {
            Scanner sc = new Scanner(new File(filename));
            StringBuffer buffer = new StringBuffer();
            while (sc.hasNextLine()) {
                buffer.append(sc.nextLine() + System.lineSeparator());
            }
            String fileContents = buffer.toString();
            sc.close();
            String oldLine = "<!--head-->";
            String newLine = "<tr><td>" + htmlcount + "</td>" + "<td>" + input + "</td></tr><!--head-->";
            fileContents = fileContents.replaceAll(oldLine, newLine);
            FileWriter writer = new FileWriter(filename);
            writer.append(fileContents);
            writer.flush();
        } catch (Exception e) {
            System.err.println("Error while writing to file: " + e.getMessage());
        }
    }

    public void undo(Stack<String> undo,Stack<String> redo, Shape[] shapes){
        if (undo.isEmpty()){
            System.out.println("There is nothing to undo");
        }else {
            String input = undo.pop();
            String[] inputSplit = input.split(" ");
            switch (inputSplit[0]) {
                case "rectangle":
                    redo.push(input);
                    Shape.deleteShape(inputSplit, shapes);
                    System.out.println("Rectangle undo is successful.");
                    break;
                case "line":
                    redo.push(input);
                    Shape.deleteShape(inputSplit, shapes);
                    System.out.println("Line undo is successful.");
                    break;
                case "circle":
                    redo.push(input);
                    Shape.deleteShape(inputSplit, shapes);
                    System.out.println("Circle undo is successful.");
                    break;
                case "square":
                    redo.push(input);
                    Shape.deleteShape(inputSplit, shapes);
                    System.out.println("Square undo is successful.");
                    break;
                case "group":
                    redo.push(input);
                    Group.ungroupShape(inputSplit, shapes);
                    System.out.println("Group undo is successful.");
                    break;
                case "ungroup":
                    redo.push(input);
                    shapes[Shape.countIndex] = new Group(inputSplit[1]);
                    ((Group) shapes[Shape.countIndex - 1]).group(inputSplit, shapes);
                    System.out.println("Ungroup undo is successful.");
                    break;
                case "delete":
                    String redoinput = "delete "+inputSplit[2];
                    redo.push(redoinput);
                    switch (inputSplit[1]) {
                        case "rectangle":
                            shapes[Shape.countIndex] = new Rectangle(inputSplit[2], Double.parseDouble(inputSplit[3]), Double.parseDouble(inputSplit[4]), Double.parseDouble(inputSplit[5]), Double.parseDouble(inputSplit[6]));
                            break;
                        case "line":
                            shapes[Shape.countIndex] = new Line(inputSplit[2], Double.parseDouble(inputSplit[3]), Double.parseDouble(inputSplit[4]), Double.parseDouble(inputSplit[5]), Double.parseDouble(inputSplit[6]));
                            break;
                        case "circle":
                            shapes[Shape.countIndex] = new Circle(inputSplit[2], Double.parseDouble(inputSplit[3]), Double.parseDouble(inputSplit[4]), Double.parseDouble(inputSplit[5]));
                            break;
                        case "square":
                            shapes[Shape.countIndex] = new Square(inputSplit[2], Double.parseDouble(inputSplit[3]), Double.parseDouble(inputSplit[4]), Double.parseDouble(inputSplit[5]));
                            break;
                    }
                    System.out.println("Delete undo is successful.");
                    break;
                case "move":
                    redo.push(inputSplit[0] + " " + inputSplit[1] + " " + Double.parseDouble(inputSplit[2])*-1 + " " + Double.parseDouble(inputSplit[3])*-1);

                    int index = Shape.search(shapes, inputSplit[1]);
                    shapes[index].move(Double.parseDouble(inputSplit[2]), Double.parseDouble(inputSplit[3]));
                    System.out.println("Move undo is successful.");
                    break;
                case "pick-and-move":
                    double newredox = Double.parseDouble(inputSplit[2]) + Double.parseDouble(inputSplit[4]);
                    double newredoy = Double.parseDouble(inputSplit[3]) + Double.parseDouble(inputSplit[5]);
                    //push pickandmove to redo
                    redo.push(inputSplit[0] + " " + inputSplit[1] + " " + newredox + " " + newredoy + " " + Double.parseDouble(inputSplit[4])*-1 + " " + Double.parseDouble(inputSplit[5])*-1);
                    Shape.pickMoveShape(Double.parseDouble(inputSplit[1]), Double.parseDouble(inputSplit[2]), Double.parseDouble(inputSplit[3]), Double.parseDouble(inputSplit[4]), shapes);
                    System.out.println("Pick-and-move undo is successful.");
                    break;
            }
        }
    }

    public void redo(Stack<String> undo,Stack<String> redo, Shape[] shapes){
        if (redo.isEmpty()){
            System.out.println("There is nothing to undo.");
        }else {
            String input = redo.pop();
            String[] inputSplit = input.split(" ");
            switch (inputSplit[0]) {
                case "rectangle":
                    shapes[Shape.countIndex] = new Rectangle(inputSplit[1], Double.parseDouble(inputSplit[2]), Double.parseDouble(inputSplit[3]), Double.parseDouble(inputSplit[4]), Double.parseDouble(inputSplit[5]));
                    System.out.println("Rectangle redo is successful.");
                    break;
                case "line":
                    shapes[Shape.countIndex] = new Line(inputSplit[1], Double.parseDouble(inputSplit[2]), Double.parseDouble(inputSplit[3]), Double.parseDouble(inputSplit[4]), Double.parseDouble(inputSplit[5]));
                    System.out.println("Line redo is successful.");
                    break;
                case "circle":
                    shapes[Shape.countIndex] = new Circle(inputSplit[1], Double.parseDouble(inputSplit[2]), Double.parseDouble(inputSplit[3]), Double.parseDouble(inputSplit[4]));
                    System.out.println("Circle redo is successful.");
                    break;
                case "square":
                    shapes[Shape.countIndex] = new Square(inputSplit[1], Double.parseDouble(inputSplit[2]), Double.parseDouble(inputSplit[3]), Double.parseDouble(inputSplit[4]));
                    System.out.println("Square redo is successful.");
                    break;
                case "group":
                    shapes[Shape.countIndex] = new Group(inputSplit[1]);
                    ((Group) shapes[Shape.countIndex - 1]).group(inputSplit, shapes);
                    System.out.println("Group redo is successful.");
                    break;
                case "ungroup":
                    Group.ungroupShape(inputSplit, shapes);
                    System.out.println("Ungroup redo is successful.");
                    break;
                case "delete":
                    Shape.deleteShape(inputSplit, shapes);
                    System.out.println("Delete redo is successful.");
                    break;
                case "move":
                    int index = Shape.search(shapes, inputSplit[1]);

                    shapes[index].move(Double.parseDouble(inputSplit[2]), Double.parseDouble(inputSplit[3]));
                    System.out.println("Move redo is successful.");
                    break;
                case "pick-and-move":
                    Shape.pickMoveShape(Double.parseDouble(inputSplit[1]), Double.parseDouble(inputSplit[2]), Double.parseDouble(inputSplit[3]), Double.parseDouble(inputSplit[4]), shapes);
                    System.out.println("Pick-and-move redo is successful.");
                    break;
            }
        }
    }
}