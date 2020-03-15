import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BruteCollinearPoints {

	private final List<LineSegment> lineSegments = new ArrayList<>();

	public BruteCollinearPoints(Point[] points) {
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

		for (int a = 0; a < points.length - 3; a++) {
			for (int b = a + 1; b < points.length - 2; b++) {
				for (int c = b + 1; c < points.length - 1; c++) {
					for (int d = c + 1; d < points.length; d++) {
						if (points[a].slopeTo(points[b]) == points[a].slopeTo(points[c])
								&& points[a].slopeTo(points[b]) == points[a].slopeTo(points[d])) {
							Point[] segment = { points[a], points[b], points[c], points[d] };
							Arrays.sort(segment);
							lineSegments.add(new LineSegment(segment[0], segment[segment.length - 1]));
						}
					}
				}
			}
		}
	}

	/**
	 * // the number of line segments
	 * 
	 * @return
	 */
	public int numberOfSegments() {
		return lineSegments.size();
	}

	/**
	 * // the line segments
	 * 
	 * @return
	 */
	public LineSegment[] segments() {
		return lineSegments.toArray(new LineSegment[numberOfSegments()]);
	}
}
