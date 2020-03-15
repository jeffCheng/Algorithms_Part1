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

		for (int i = 0; i < pointSet.length - 3; i++) {
			Arrays.sort(pointSet);
			// order by slope
			Arrays.sort(pointSet, pointSet[i].slopeOrder());

			for (int a = 0, slow = 1, fast = 2; fast < pointSet.length; fast++) {
				while (fast < pointSet.length && Double.compare(pointSet[a].slopeTo(pointSet[slow]),
				        pointSet[a].slopeTo(pointSet[fast])) == 0) {
					fast++;
				}
				if (fast - slow >= 3 && pointSet[a].compareTo(pointSet[slow]) < 0) {
					lineSegments.add(new LineSegment(pointSet[a], pointSet[fast - 1]));
				}
				slow = fast;
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
