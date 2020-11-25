package bearmaps;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import java.util.List;
import edu.princeton.cs.algs4.Stopwatch;

public class KDTreeTest {
    private static final double DELTA = 0.00000001;
    @Test
    public void NaivePointSetTest() {
        // Construct a set of points
        Point p1 = new Point(1.1, 2.2);
        Point p2 = new Point(3.3, 4.4);
        Point p3 = new Point(-2.9, 4.2);
        Point p4 = new Point(1.0, 1.0);
        double x = 3.0;
        double y = 4.0;

        // Use NaivePointSet to find the nearest points
        NaivePointSet nn = new NaivePointSet(List.of(p1, p2, p3));
        Point ret = nn.nearest(x, y);
        assertEquals(ret.getX(), p2.getX(), DELTA);
        assertEquals(ret.getY(), p2.getY(), DELTA);

        // Edge Case - Only one point in the set
        NaivePointSet nn2 = new NaivePointSet(List.of(p4));
        Point ret2 = nn2.nearest(x, y);
        assertEquals(ret2.getX(), p4.getX(), DELTA);
        assertEquals(ret2.getY(), p4.getY(), DELTA);
    }

    @Test
    public void SameDistanceDeterministic() {
        // Construct a set of points
        Point p1 = new Point(1.1, 2.2);
        Point p2 = new Point(3.3, 4.4);
        Point p3 = new Point(-2.9, 4.2);
        Point p4 = new Point(1.0, 1.0);
        double x = 3.0;
        double y = 4.0;

        // Use NaivePointSet to find the nearest point
        NaivePointSet nn = new NaivePointSet(List.of(p1, p2, p3));
        Point retNPS = nn.nearest(x, y);
        double xNPS = retNPS.getX();
        double yNPS = retNPS.getY();

        // Use KDTree to find the nearest point
        KDTree kd = new KDTree(List.of(p1, p2, p3));
        Point retKD = kd.nearest(x, y);
        double xKD = retKD.getX();
        double yKD = retKD.getY();

        // Edge Case - Only one point in the set
        // Use NaivePointSet to find the nearest point
        NaivePointSet nn2 = new NaivePointSet(List.of(p4));
        Point retNPS2 = nn2.nearest(x, y);
        double xNPS2 = retNPS2.getX();
        double yNPS2 = retNPS2.getY();

        // Use KDTree to find the nearest point
        KDTree kd2 = new KDTree(List.of(p4));
        Point retKD2 = kd2.nearest(x, y);
        double xKD2 = retKD2.getX();
        double yKD2 = retKD2.getY();

        // Test equal distance
        assertEquals(distance(xNPS, x, yNPS, y), distance(xKD, x, yKD, y), DELTA);
        assertEquals(distance(xNPS2, x, yNPS2, y), distance(xKD2, x, yKD2, y), DELTA);
    }

    @Test
    public void SameDistanceRandom() {
        // 20 trials of random tests
        for(int trial = 0; trial < 20; trial++) {

            // Construct a set of 100 points
            double x = 10 * Math.random() - 5.0;
            double y = 10 * Math.random() - 5.0;
            Point [] points = new Point[100];

            // Add random points from -5 to 5
            for(int i = 0; i < 100; i++) {
                double randX = 10 * Math.random() - 5.0;
                double randY = 10 * Math.random() - 5.0;
                points[i] = new Point(randX, randY);
            }

            // Use NaivePointSet to find the nearest point
            NaivePointSet nn = new NaivePointSet(List.of(points));
            Point retNPS = nn.nearest(x, y);
            double xNPS = retNPS.getX();
            double yNPS = retNPS.getY();

            // Use KDTree to find the nearest point
            KDTree kd = new KDTree(List.of(points));
            Point retKD = kd.nearest(x, y);
            double xKD = retKD.getX();
            double yKD = retKD.getY();

            // Test equal distance
            assertEquals(distance(xNPS, x, yNPS, y), distance(xKD, x, yKD, y), DELTA);
        }
    }

    @Test
    public void timeTest() {
        // Construct a set of 100000 points
        double x = 10000 * Math.random() - 5000.0;
        double y = 10000 * Math.random() - 5000.0;
        Point [] points = new Point[1000000];

        // Add random points from -500.0 to 500.0
        for (int i = 0; i < points.length; i++) {
            double randX = 10000 * Math.random() - 5000.0;
            double randY = 10000 * Math.random() - 5000.0;
            points[i] = new Point(randX, randY);
        }

        // Construct the NaivePointSet and the KDTree
        NaivePointSet nn = new NaivePointSet(List.of(points));
        KDTree kd = new KDTree(List.of(points));

        // Create a stopwatch and start it
        Stopwatch stopwatch = new Stopwatch();
        nn.nearest(x, y);
        System.out.println("For NaivePointSet, the time elapsed was: " + stopwatch.elapsedTime());

        stopwatch = new Stopwatch();
        kd.nearest(x, y);
        System.out.println("For KDTree, the time elapsed was: " + stopwatch.elapsedTime());

    }

    // Calculates the distance between two points
    private double distance(double X1, double X2, double Y1, double Y2) {
        double inside = Math.pow(X1 - X2, 2) + Math.pow(Y1 - Y2, 2);
        return Math.sqrt(inside);
    }
}
