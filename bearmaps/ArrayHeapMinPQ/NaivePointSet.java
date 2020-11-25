package bearmaps;
import java.util.List;

public class NaivePointSet implements PointSet {
    // private variables
    private List<Point> points;

    // Constructor
    public NaivePointSet(List<Point> points) {
        this.points = points;
    }

    @Override
    public Point nearest(double x, double y) {
        Point closest = points.get(0);
        double closestDistance = distance(x, closest.getX(), y, closest.getY());
        for(int i = 1; i < points.size(); i++) {
            double tempX = points.get(i).getX();
            double tempY = points.get(i).getY();
            if (distance(x, tempX, y, tempY) < closestDistance) {
                closest = points.get(i);
                closestDistance = distance(x, closest.getX(), y, closest.getY());
            }
        }
        return closest;
    }

    // Calculates the distance between two points
    private double distance(double X1, double X2, double Y1, double Y2) {
        double inside = Math.pow(X1 - X2, 2) + Math.pow(Y1 - Y2, 2);
        return Math.sqrt(inside);
    }
}
