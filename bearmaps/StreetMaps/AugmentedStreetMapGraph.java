package bearmaps.proj2d;

import bearmaps.proj2c.streetmap.StreetMapGraph;
import bearmaps.proj2c.streetmap.Node;

import java.util.List;
import java.util.Map;
import java.util.LinkedList;
import java.util.*;
import bearmaps.proj2ab.*;

/**
 * An augmented graph that is more powerful that a standard StreetMapGraph.
 * Specifically, it supports the following additional operations:
 *
 *
 * @author Alan Yao, Josh Hug, ________
 */
public class AugmentedStreetMapGraph extends StreetMapGraph {
    private HashMap<Point, Node> pointToNode;
    private HashMap<String, String> fullNames;
    private HashMap<String, List<Node>> names;
    private KDTree kd;
    private List<Point> points;

    public AugmentedStreetMapGraph(String dbPath) {
        super(dbPath);
        // You might find it helpful to uncomment the line below:
        List<Node> nodes = this.getNodes();
        pointToNode = new HashMap<>();
        fullNames = new HashMap<>();
        names = new HashMap<>();
        points = new ArrayList<>();

        for (Node n: nodes) {
            if (n.name() != null) {
                named(n);
            }
            if (!neighborEmpty(n)) {
                Point p = new Point(n.lon(), n.lat());
                pointToNode.put(p, n);
                points.add(p);
            }
        }
        kd = new KDTree(points);
    }

    private boolean neighborEmpty(Node n) {
        return (this.neighbors(n.id()).isEmpty());
    }

    private void named(Node n) {
        String clean = cleanString(n.name());
        fullNames.put(clean, n.name());
        List<Node> nodeList;
        if (!names.containsKey(clean)) {
            nodeList = new ArrayList<>();
        } else {
            nodeList = names.get(clean);
        }
        nodeList.add(n);
        names.put(clean, nodeList);
    }

    /**
     * For Project Part II
     * Returns the vertex closest to the given longitude and latitude.
     * @param lon The target longitude.
     * @param lat The target latitude.
     * @return The id of the node in the graph closest to the target.
     */
    public long closest(double lon, double lat) {
        return pointToNode.get(kd.nearest(lon, lat)).id();
    }


    /**
     * For Project Part III (gold points)
     * In linear time, collect all the names of OSM locations that prefix-match the query string.
     * @param prefix Prefix string to be searched for. Could be any case, with our without
     *               punctuation.
     * @return A <code>List</code> of the full names of locations whose cleaned name matches the
     * cleaned <code>prefix</code>.
     */
    public List<String> getLocationsByPrefix(String prefix) {
        return new LinkedList<>();
    }

    /**
     * For Project Part III (gold points)
     * Collect all locations that match a cleaned <code>locationName</code>, and return
     * information about each node that matches.
     * @param locationName A full name of a location searched for.
     * @return A list of locations whose cleaned name matches the
     * cleaned <code>locationName</code>, and each location is a map of parameters for the Json
     * response as specified: <br>
     * "lat" -> Number, The latitude of the node. <br>
     * "lon" -> Number, The longitude of the node. <br>
     * "name" -> String, The actual name of the node. <br>
     * "id" -> Number, The id of the node. <br>
     */
    public List<Map<String, Object>> getLocations(String locationName) {
        return new LinkedList<>();
    }


    /**
     * Useful for Part III. Do not modify.
     * Helper to process strings into their "cleaned" form, ignoring punctuation and capitalization.
     * @param s Input string.
     * @return Cleaned string.
     */
    private static String cleanString(String s) {
        return s.replaceAll("[^a-zA-Z ]", "").toLowerCase();
    }
}
