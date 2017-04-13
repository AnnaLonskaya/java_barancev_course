package ua.annalonskaya.sandbox;

import org.testng.Assert;
import org.testng.annotations.Test;

import static ua.annalonskaya.sandbox.Point.distance;

public class PointTests {

  @Test
  public void testDistanceP1P2() {
    Point p1 = new Point(4, 5);
    Point p2 = new Point(2, 3);
    Assert.assertEquals(distance(p1, p2), 2.8284271247461903);
  }

  @Test
  public void testDistanceqP2() {
    Point p1 = new Point(4, 5);
    Point p2 = new Point(2, 3);
    Assert.assertEquals(p1.distance(p2), 2.8284271247461903);
  }

}
