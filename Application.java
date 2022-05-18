package hk.edu.polyu.comp.comp2021.clevis;

import hk.edu.polyu.comp.comp2021.clevis.model.Clevis;

public class Application {

    public static void main(String[] args){
        String htmlname="log.html", txtname="log.txt";
        if(args.length == 0){
            htmlname = "log.html";
            txtname = "log.txt";
        } else if (args.length == 1) {
            htmlname = args[0];
        } else {
            htmlname = args[0];
            txtname = args[1];
        }
        Clevis clevis = new Clevis(htmlname, txtname, 100, new String[0]);
    }
}
