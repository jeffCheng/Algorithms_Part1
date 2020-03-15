import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {

    private final ArrayList<LineSegment> lineSegments = new ArrayList<>();

    public FastCollinearPoints(Point[] points) {
        validate(points);
        int numOfPoints = points.length;
        if (numOfPoints < 4) { return; }
        Point[] pointSet = points.clone();
        Arrays.sort(pointSet);
        // Given a point p, the following method determines
        // whether p participates in a set of 4 or more collinear points.
        for (int i = 0; i < numOfPoints; i++) {
            // Think of p as the origin.
            // Sort the points according to the slopes they makes with p.
            Point[] ps = pointSet.clone();
            Arrays.sort(ps, ps[i].slopeOrder());
            // For each other point q, determine the slope it makes with p.
            // Also note that ps[0] == pointSet[i]
            final Point anchor = ps[0];
            double slopeSlow = anchor.slopeTo(ps[1]), slopeFast;
            for (int idxSlow = 1, idxFast; idxSlow < numOfPoints - 2; idxSlow = idxFast, slopeSlow = slopeFast) {
                idxFast = idxSlow + 1;
                do {
                    slopeFast = anchor.slopeTo(ps[idxFast++]);
                } while (slopeSlow == slopeFast && idxFast < numOfPoints);
                idxFast--;
                // Check if any 3 or more adjacent points in the sorted order
                // have equal slopes with respect to p.
                // If so, these points, together with p, are collinear.
                int numOfAdjacentPoint = idxFast - idxSlow;
                if (numOfAdjacentPoint >= 3) {
                    // sort the array as previous sort is unstable
                    Point[] segment = new Point[numOfAdjacentPoint + 1];
                    segment[0] = anchor;
                    System.arraycopy(ps, idxSlow, segment, 1, numOfAdjacentPoint);
                    Arrays.sort(segment);
                    // make sure no duplicate subsegment
                    if (segment[0] == anchor) {
                        lineSegments.add(new LineSegment(anchor, segment[numOfAdjacentPoint]));
                    }
                }
            }
        }
    }

    public int numberOfSegments() {
        return lineSegments.size();
    }

    public LineSegment[] segments() {
        return lineSegments.toArray(new LineSegment[numberOfSegments()]);
    }

    private void validate(Point[] points) {
        if (points == null) { throw new IllegalArgumentException(); }

        for (Point point : points) {
            if (point == null) { throw new IllegalArgumentException(); }
        }

        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                if ((points[j].equals(points[i]))) { throw new IllegalArgumentException(); }
            }
        }
    }
}
