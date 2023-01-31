/*
Write a program BruteCollinearPoints.java that examines 4 points at a time and checks
whether they all lie on the same line segment, returning all such line segments.

To check whether the 4 points p, q, r, and s are collinear, check whether the three slopes
between p and q, between p and r, and between p and s are all equal.

 */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Merge;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.LinkedList;

public class BruteCollinearPoints {
    private final Point[] points;
    private final LinkedList<LineSegment> segments;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        validate(points);
        this.points = points;
        segments = new LinkedList<>();
    }

    private void validate(Point[] points) {
        if (points == null) throw new IllegalArgumentException();
        Merge.sort(points);
        for (int i = 1; i < points.length; i++) {
            if (points[i - 1] == null || points[i].equals(points[i - 1])) throw new IllegalArgumentException();
        }
    }

    // the number of line segments
    public int numberOfSegments() {
        return segments.size();
    }

    /*
    The method segments() should include each line segment containing 4 points exactly once.
    If 4 points appear on a line segment in the order p→q→r→s, then you should include either
    the line segment p→s or s→p (but not both) and you should not include subsegments such as
    p→r or q→r.
     */

    public LineSegment[] segments() {
        int len = points.length;
        double slopeAB;
        double slopeAC;
        double slopeAD;
        for (int a = 0; a < len - 3; a++) {
            for (int b = a + 1; b < len - 2; b++) {
                for (int c = b + 1; c < len - 1; c++) {
                    slopeAB = points[a].slopeTo(points[b]);
                    slopeAC = points[a].slopeTo(points[c]);
                    if (slopeAB != slopeAC) continue;
                    for (int d = c + 1; d < len; d++) {
                        slopeAD = points[a].slopeTo(points[d]);
                        if (slopeAB == slopeAD) {
                            segments.add(new LineSegment(points[a], points[d]));
                            while ((d < len) && (slopeAB == points[a].slopeTo(points[d]))) {
                                b = d;
                                c = b + 1;
                                d++;
                            }
                            break;
                        }
                    }
                }
            }
        }
        return segments.toArray(new LineSegment[0]);
    }

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In("resources/input8.txt");
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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();

        System.out.println("Number of segments = " + collinear.numberOfSegments());
    }
}