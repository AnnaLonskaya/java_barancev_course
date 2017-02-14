package ua.annalonskaya.sandbox;

import static ua.annalonskaya.sandbox.Point.distance;

public class MyProgram {

    public static void main(String[] args) {

        Point p1 = new Point(4, 5);
        Point p2 = new Point(2, 3);
        System.out.println("Distance between two points p1(" + p1.x + ", " + p1.y + ") and p2(" + p2.x + ", " + p2.y + ") is " + distance(p1, p2));
        System.out.println("Distance between two points p1(" + p1.x + ", " + p1.y + ") and p2(" + p2.x + ", " + p2.y + ") is " + p1.distance(p2));

    }

}
