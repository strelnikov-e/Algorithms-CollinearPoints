import edu.princeton.cs.algs4.StdDraw;

/*************************************************************************
 *  Compilation:  javac LineSegment.java
 *  Execution:    none
 *  Dependencies: Point.java
 *
 *  An immutable data type for Line segments in the plane.
 *  For use on Coursera, Algorithms Part I programming assignment.
 *
 *  DO NOT MODIFY THIS CODE.
 *
 *************************************************************************/

public class LineSegment {
    private final Point p;   // one endpoint of this line segment
    private final Point q;   // the other endpoint of this line segment

    /**
     * Initializes a new line segment.
     *
     * @param  p one endpoint
     * @param  q the other endpoint
     * @throws NullPointerException if either <tt>p</tt> or <tt>q</tt>
     *         is <tt>null</tt>
     */
    public LineSegment(Point p, Point q) {
        if (p == null || q == null) {
            throw new IllegalArgumentException("argument to LineSegment constructor is null");
        }
        if (p.compareTo(q) == 0) {
            throw new IllegalArgumentException("both arguments to LineSegment constructor are the same point: " + p);
        }
        this.p = p;
        this.q = q;
    }


    /**
     * Draws this line segment to standard draw.
     */
    public void draw() {
        p.drawTo(q);
    }

    /**
     * Returns a string representation of this line segment
     * This method is provided for debugging;
     * your program should not rely on the format of the string representation.
     *
     * return a string representation of this line segment
     */
    public String toString() {
        return p + " -> " + q;
    }

    /**
     * Throws an exception if called. The hashCode() method is not supported because
     * hashing has not yet been introduced in this course. Moreover, hashing does not
     * typically lead to good *worst-case* performance guarantees, as required on this
     * assignment.
     *
     * throws UnsupportedOperationException if called
     */
    public int hashCode() {
        throw new UnsupportedOperationException("hashCode() is not supported");
    }

    public static void main(String[] args) {
        Point p0 = new Point(5000,6000);
        Point p1 = new Point(5000,6000);
        Point p2 = new Point(10000,7000);

        try {
            new LineSegment(null, p0);
        } catch (IllegalArgumentException e) {
            System.out.println("IllegalArgumentException thrown");
        }
        try {
            new LineSegment(p0, p1);
        } catch (IllegalArgumentException e) {
            System.out.println("IllegalArgumentException thrown: p0 == p1");
        }


        LineSegment lineSegment = new LineSegment(p1,p2);
        System.out.println(lineSegment);
        System.out.println(p1.slopeTo(p2));

        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);

        StdDraw.setPenRadius(0.005);  //delete

        lineSegment.draw();

        StdDraw.show();
    }
}
