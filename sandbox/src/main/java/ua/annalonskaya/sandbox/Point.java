package ua.annalonskaya.sandbox;

/**
 * Created by Admin on 13.02.2017.
 */
public class Point {

  public double x;
  public double y;

  public Point(double x, double y) {
    this.x = x;
    this.y = y;
  }

  public static void main(String[] args) {

    Point p1 = new Point(2, 3);
    Point p2 = new Point(1, 4);
    System.out.println("Distance between two points p1(" + p1.x + ", " + p1.y + ") and p2(" + p2.x + ", " + p2.y + ") is " + distance(p1, p2));

  }

  public static double distance(Point p1, Point p2) {
    return Math.sqrt((p2.x - p1.x) * (p2.x - p1.x) + (p2.y - p1.y) * (p2.y - p1.y));
  }

}
