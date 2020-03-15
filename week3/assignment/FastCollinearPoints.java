import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FastCollinearPoints {
	private final List<LineSegment> lineSegments = new ArrayList<LineSegment>();

	public FastCollinearPoints(Point[] points) {
		if (points == null)
			throw new IllegalArgumentException();
		
		for (Point p : points)
			if (p == null)
				throw new IllegalArgumentException();

		for (int i = 0; i < points.length - 1; i++) {
			for (int j = i + 1; j < points.length; j++) {
				if ((points[i].compareTo(points[j])) == 0)
					throw new IllegalArgumentException();
			}
		}
		int pointsNums = points.length;
		if (pointsNums < 4)
			return;

		Point[] pointSet = points.clone();
		Arrays.sort(pointSet);

		for (int i = 0; i < pointsNums; i++) {

			Point[] ps = pointSet.clone();
			Arrays.sort(ps, ps[i].slopeOrder());

			final Point anchor = ps[0];
			double slopeSlow = anchor.slopeTo(ps[1]);
			double slopeFast;
			int idxSlow = 1;
			int idxFast =0;
			while (idxSlow < pointsNums - 2) {
				idxFast = idxSlow + 1;
				do {
					slopeFast = anchor.slopeTo(ps[idxFast++]);
				} while (slopeSlow == slopeFast && idxFast < pointsNums);
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
				slopeSlow = slopeFast;
				idxSlow = idxFast;
			}
		}

	} // finds all line segments containing 4 or more points

	public int numberOfSegments() {
		return lineSegments.size();
	} // the number of line segments

	public LineSegment[] segments() {
		return lineSegments.toArray(new LineSegment[numberOfSegments()]);
	} // the line segments
}
