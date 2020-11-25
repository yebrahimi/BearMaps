package bearmaps;
import java.util.List;

public class KDTree implements PointSet {
    // private variables
    private Node KDTreeNode;
    private static final double DELTA = 0.00001;

    // private class
    private class Node {
        private double x;
        private double y;
        private Node left;
        private Node right;
        private int axis;

        Node(Point p, int axis) {
            x = p.getX();
            y = p.getY();
            this.axis = axis;
        }
    }

    private void constructor(Point p, Node KDTreeNode) {
        double space;
        if (KDTreeNode.axis == 1) {
            space = KDTreeNode.x - p.getX();
        } else {
            space = KDTreeNode.y - p.getY();
        }
        if (space < DELTA) {
            if (KDTreeNode.right == null) {
                KDTreeNode.right = new Node(p, (KDTreeNode.axis + 1) % 2);
            } else {
                constructor(p, KDTreeNode.right);
            }
        } else {
            if (KDTreeNode.left == null) {
                KDTreeNode.left = new Node(p, (KDTreeNode.axis + 1) % 2);
            } else {
                constructor(p, KDTreeNode.left);
            }
        }
    }

    // Constructor
    public KDTree(List<Point> points) {
        KDTreeNode = new Node(points.get(0), 1);
        for (Point p : points) {
            constructor(p, KDTreeNode);
        }
    }

    @Override
    public Point nearest(double x, double y) {
        Point pGoal = new Point(x, y);
        Node n = recursiveNearest(KDTreeNode, pGoal, KDTreeNode);
        return new Point(n.x, n.y);
    }

    private Node recursiveNearest(Node current, Point pGoal, Node nearest) {
        if (current == null) {
            return nearest;
        }
        Point pCurrent = new Point(current.x, current.y);
        Point pNearest = new Point(nearest.x, nearest.y);
        if (Point.distance(pCurrent, pGoal) < Point.distance(pNearest, pGoal)) {
            nearest = current;
        }
        Node goodSide;
        Node badSide;
        double space;
        if (current.axis == 1) {
            space = current.x - pGoal.getX();
        } else {
            space = current.y - pGoal.getY();
        }
        if (space < DELTA) {
            goodSide = current.right;
            badSide = current.left;
        } else {
            goodSide = current.left;
            badSide = current.right;
        }
        nearest = recursiveNearest(goodSide, pGoal, nearest);
        pNearest = new Point(nearest.x, nearest.y);
        double dist = Point.distance(pNearest, pGoal);
        if (current.axis == 1) {
            if (Math.pow(Math.abs(current.x - pGoal.getX()), 2) - dist < DELTA) {
                nearest = recursiveNearest(badSide, pGoal, nearest);
            }
        } else {
            if (Math.pow(Math.abs(current.y - pGoal.getY()), 2) - dist < DELTA) {
                nearest = recursiveNearest(badSide, pGoal, nearest);
            }
        }
        return nearest;
    }
}
