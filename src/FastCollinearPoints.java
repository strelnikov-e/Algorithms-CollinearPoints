/*
% spotbugs *.class
*-----------------------------------------------------------
M V EI_EXPOSE_REP2 EI2: Stores a reference to an externally mutable object in the instance variable 'points', exposing the internal representation of the class 'BruteCollinearPoints'. Instead, create a defensive copy of the object referenced by the parameter variable 'points' and store that copy in the instance variable 'points'.  At BruteCollinearPoints.java:[line 24]
M V EI_EXPOSE_REP2 EI2: Stores a reference to an externally mutable object in the instance variable 'points', exposing the internal representation of the class 'FastCollinearPoints'. Instead, create a defensive copy of the object referenced by the parameter variable 'points' and store that copy in the instance variable 'points'.  At FastCollinearPoints.java:[line 34]
SpotBugs ends with 2 warnings.


================================================================


% pmd .
*-----------------------------------------------------------


================================================================


% checkstyle *.java
*-----------------------------------------------------------
[WARN] BruteCollinearPoints.java:28:35: The local (or parameter) variable 'points' has the same name as an instance variable. Use a different name. [HiddenField]
[WARN] BruteCollinearPoints.java:64:35: Control variable 'b' is modified inside loop. [ModifiedControlVariable]
[WARN] BruteCollinearPoints.java:65:35: Control variable 'c' is modified inside loop. [ModifiedControlVariable]
[WARN] BruteCollinearPoints.java:66:34: Control variable 'd' is modified inside loop. [ModifiedControlVariable]
[WARN] BruteCollinearPoints.java:101:11: '//' or '/*' is not followed by whitespace. [WhitespaceAfter]
[WARN] FastCollinearPoints.java:40:35: The local (or parameter) variable 'points' has the same name as an instance variable. Use a different name. [HiddenField]
[WARN] FastCollinearPoints.java:124:11: '//' or '/*' is not followed by whitespace. [WhitespaceAfter]
[WARN] Point.java:65:86: '//' or '/*' is not followed by whitespace. [WhitespaceAfter]
[WARN] Point.java:66:46: '//' or '/*' is not followed by whitespace. [WhitespaceAfter]
[WARN] Point.java:89:7: '//' or '/*' is not followed by whitespace. [WhitespaceAfter]
[WARN] Point.java:130:34: ',' is not followed by whitespace. [WhitespaceAfter]
[WARN] Point.java:131:35: ',' is not followed by whitespace. [WhitespaceAfter]
[WARN] Point.java:132:34: ',' is not followed by whitespace. [WhitespaceAfter]
[WARN] Point.java:133:34: ',' is not followed by whitespace. [WhitespaceAfter]
[WARN] Point.java:134:34: ',' is not followed by whitespace. [WhitespaceAfter]
[WARN] Point.java:144:58: ',' is not followed by whitespace. [WhitespaceAfter]
Checkstyle ends with 0 errors and 16 warnings.

 */


/*
Given a point p, the following method determines whether p participates in a set of 4 or more collinear points.

 - Think of p as the origin.
 - For each other point q, determine the slope it makes with p.
 - Sort the points according to the slopes they make with p.
 - Check if any 3 (or more) adjacent points in the sorted order have equal slopes with respect to p.
 - If so, these points, together with p, are collinear.
 - Applying this method for each of the n points in turn yields an efficient algorithm to the problem.

The algorithm solves the problem because points that have equal slopes with respect to p are collinear,
and sorting brings such points together. The algorithm is fast because the bottleneck operation is sorting.

Corner cases. Throw an IllegalArgumentException if the argument to the constructor is null,
if any point in the array is null, or if the argument to the constructor contains a repeated point.
 */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Merge;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {

    private final Point[] points;
    private final ArrayList<LineSegment> segments;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        this.points = points;
        segments = new ArrayList<>();

        validate(points);
    }

    private void validate(Point[] points) {
        if (points == null) throw new IllegalArgumentException("Array is null");
        for (Point point : points) {
            if (point == null) throw new IllegalArgumentException("Array has a null point");
        }
        Point[] aux = Arrays.copyOf(points, points.length);
        Merge.sort(aux);
        for (int i = 1; i < points.length; i++) {
            if (points[i].compareTo(points[i - 1]) == 0) throw new IllegalArgumentException();

        }
    }

    // the number of line segments
    public int numberOfSegments() {
        return segments.size();
    }

    /*
    The method segments() should include each maximal line segment containing 4 (or more) points exactly once.
    For example, if 5 points appear on a line segment in the order p→q→r→s→t,
    then do not include the subsegments p→s or q→t.
     */
    public LineSegment[] segments() {
        int len = points.length;
        if (len < 4) return segments.toArray(new LineSegment[0]);
        int count;
        Point[] aux = Arrays.copyOf(points, len);

        for (Point p : points) {
            Arrays.sort(aux, p.slopeOrder());
            double slope = p.slopeTo(aux[0]);
            count = 1;
            int i;

            for (i = 1; i < len; i++) {
                if (p.slopeTo(aux[i]) == slope) {
                    count++;
                } else {
                    if (count >= 3) {
                        Arrays.sort(aux, i - count, i);
                        if (p.compareTo(aux[i - count]) < 0) {
                            segments.add(new LineSegment(p, aux[i - 1]));
                        }
                    }
                    slope = p.slopeTo(aux[i]);
                    count = 1;
                }
            }
            if (count >= 3) {
                Arrays.sort(aux, i - count, i);
                if (p.compareTo(aux[i - count]) < 0) {
                    segments.add(new LineSegment(p, aux[i - 1]));
                }
            }
        }
        return segments.toArray(new LineSegment[0]);
    }


    public static void main(String[] args) {

        // read the n points from a file
        In in = new In("resources/input20.txt");
//        In in = new In(args[0]);

        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);

        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        //print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);

        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        System.out.println("Number of segments: " + collinear.numberOfSegments());
        StdDraw.show();
    }

}
