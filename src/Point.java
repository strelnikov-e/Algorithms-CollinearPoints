/******************************************************************************
 *  Compilation:  javac Point.java
 *  Execution:    java Point
 *  Dependencies: none
 *
 *  An immutable data type for points in the plane.
 *  For use on Coursera, Algorithms Part I programming assignment.
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.StdDraw;

import java.util.Comparator;

public class Point implements Comparable<Point> {


//    public final Comparator<Point> SLOPE_ORDER = new SlopeOrder();
    private final int x;     // x-coordinate of this point
    private final int y;     // y-coordinate of this point

    /**
     * Initializes a new point.
     *
     * @param  x the <em>x</em>-coordinate of the point
     * @param  y the <em>y</em>-coordinate of the point
     */
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    /**
     * Draws this point to standard draw.
     */
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    /**
     * Draws the line segment between this point and the specified point
     * to standard draw.
     *
     * @param that the other point
     */
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    /**
     * Returns the slope between this point and the specified point.
     * Formally, if the two points are (x0, y0) and (x1, y1), then the slope
     * is (y1 - y0) / (x1 - x0). For completeness, the slope is defined to be
     * +0.0 if the line segment connecting the two points is horizontal;
     * Double.POSITIVE_INFINITY if the line segment is vertical;
     * and Double.NEGATIVE_INFINITY if (x0, y0) and (x1, y1) are equal.
     *
     * @param  that the other point
     * @return the slope between this point and the specified point
     */
    public double slopeTo(Point that) {
        if (this.x == that.x && this.y == that.y) return Double.NEGATIVE_INFINITY; //points are equal;
        if (this.y == that.y) return +0.0; //horizontal line
        if (this.x == that.x) return Double.POSITIVE_INFINITY; // vertical line;
        return (double) (that.y - this.y)/(double) (that.x - this.x);
    }

    /**
     * Compares two points by y-coordinate, breaking ties by x-coordinate.
     * Formally, the invoking point (x0, y0) is less than the argument point
     * (x1, y1) if and only if either y0 < y1 or if y0 = y1 and x0 < x1.
     *
     * @param  that the other point
     * @return the value <tt>0</tt> if this point is equal to the argument
     *         point (x0 = x1 and y0 = y1);
     *         a negative integer if this point is less than the argument
     *         point; and a positive integer if this point is greater than the
     *         argument point
     */
    public int compareTo(Point that) {
        if (this.y < that.y) return -1;
        if (this.y > that.y) return 1;
        return Integer.compare(this.x, that.x);
    }

    //check the angle this-o1-o2. Return +1 if o1 > o2;
    private int ccw(Point o1, Point o2) {
        int area = (o1.x - this.x)*(o2.y - this.y) - (o1.y - this.y)*(o2.x - this.x);
        return Integer.compare(area, 0);
    }

    /**
     * Compares two points by the slope they make with this point.
     * The slope is defined as in the slopeTo() method.
     *
     * return the Comparator that defines this ordering on points
     */
    private class SlopeOrder implements Comparator<Point> {

        @Override
        public int compare(Point o1, Point o2) {
            return Double.compare(slopeTo(o1), slopeTo(o2));
        }
    }

    public Comparator<Point> slopeOrder() {
        return new SlopeOrder();
    }


    /**
     * Returns a string representation of this point.
     * This method is provided for debugging;
     * your program should not rely on the format of the string representation.
     *
     * return a string representation of this point
     */
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    /**
     * Unit tests the Point data type.
     */
    public static void main(String[] args) {
        Point p1 = new Point(5000,5000);
        Point p2 = new Point(10000,5000);
        Point p3 = new Point(5000,5000);
        Point p4 = new Point(5000,2500);
        Point p5 = new Point(2000,5000);
        System.out.println(p1.compareTo(p2));
        System.out.println(p1.compareTo(p3));
        System.out.println(p1.compareTo(p4));

        System.out.println("slope " + p1.slopeTo(p2));
        System.out.println("slope " + p1.slopeTo(p5));

        System.out.println("p1 == p2 " + p1.compareTo(p2));

        System.out.println("p1.ccw(p2, p4): " + p1.ccw(p2,p4));

        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.setPenRadius(0.005);
        p1.draw();
        p2.draw();
        p3.draw();
        p4.draw();
        p5.draw();
//        StdDraw.point(14000.0, 13000.0);
        p1.drawTo(p2);
        StdDraw.show();
    }
}