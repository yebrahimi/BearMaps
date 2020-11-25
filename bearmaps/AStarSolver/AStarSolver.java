package bearmaps.proj2c;

import java.util.*;

import bearmaps.proj2ab.DoubleMapPQ;
import edu.princeton.cs.algs4.Stopwatch;

public class AStarSolver<Vertex> implements ShortestPathsSolver<Vertex> {
    // private variables
    private DoubleMapPQ<Vertex> pq;
    private LinkedList<Vertex> solution;
    private SolverOutcome solvedOutcome;
    private HashMap<Vertex, Double> distTo;
    private HashMap<Vertex, Vertex> edgeTo;
    //private HashSet<Vertex> marking;
    private AStarGraph<Vertex> input;
    private Vertex goal;
    private Vertex current;
    private int numStates;
    private double weight;
    private double time;

    /* Constructor from Pseudocode
    - Create a PQ where each vertex v will have priority p equal to the sum of v’s
      distance from the source plus the heuristic estimate from v to the goal.
    - Insert the source vertex into the PQ.
    - Repeat until the PQ is empty, PQ.getSmallest() is the goal, or timeout is
      exceeded:
        - p = PQ.removeSmallest()
        - relax all edges outgoing from p
     */
    public AStarSolver(AStarGraph<Vertex> input, Vertex start, Vertex end, double timeout) {
        // Initialize variables
        numStates = 0;
        goal = end;
        solution = new LinkedList<Vertex>();
        Stopwatch stopwatch = new Stopwatch();

        // Create a PQ where each vertex v will have priority p...
        this.pq = new DoubleMapPQ<>();
        this.input = input;
        distTo = new HashMap<>();
        edgeTo = new HashMap<>();
        distTo.put(start, 0.0);

        // Insert source vertex into PQ
        pq.add(start, distTo.get(start));

        // Repeat until the PQ is empty, PQ.getSmallest() is the goal, or timeout is exceeded:
        while (stopwatch.elapsedTime() < timeout && pq.size() > 0) {
            this.current = pq.getSmallest();
            if (current.equals(goal)) {
                solvedOutcome = SolverOutcome.SOLVED;
                while (!end.equals(start)) {
                    solution.addFirst(end);
                    end = edgeTo.get(end);
                }
                solution.addFirst(start);
                weight = distTo.get(current);
                time = stopwatch.elapsedTime();
                return;
            } else if (stopwatch.elapsedTime() >= timeout) {
                time = timeout;
                solvedOutcome = SolverOutcome.TIMEOUT;
                return;
            } else {
                numStates++;
                pq.removeSmallest();
                for (WeightedEdge<Vertex> e: input.neighbors(current)) {
                    relax(e);
                }
            }
        }
        time = stopwatch.elapsedTime();
        solvedOutcome = SolverOutcome.UNSOLVABLE;

    }

    private double h(Vertex v1, Vertex v2) {
        return input.estimatedDistanceToGoal(v1, v2);
    }

    /*
    Relax pseudocode:
    - p = e.from(), q = e.to(), w = e.weight()
    - if distTo[p] + w < distTo[q]:
        - distTo[q] = distTo[p] + w
        - if q is in the PQ: changePriority(q, distTo[q] + h(q, goal))
        - if q is not in PQ: add(q, distTo[q] + h(q, goal))
     */
    private void relax(WeightedEdge e) {
        Vertex q = (Vertex) e.to();
        double w = e.weight();
        double dist = distTo.get(current);

        if (!distTo.containsKey(q) || dist + w < distTo.get(q)) {
            distTo.put(q, dist + w);
            edgeTo.put(q, current);
            if (pq.contains(q)) {
                pq.changePriority(q, distTo.get(q) + h(q, goal));
            } else {
                pq.add(q, distTo.get(q) + h(q, goal));
            }
        }
    }

    @Override
    public SolverOutcome outcome() {
        return solvedOutcome;
    }

    @Override
    public List<Vertex> solution() {
        return solution;
    }

    @Override
    public double solutionWeight() {
        if (solution == null) {
            return 0.0;
        }
        return weight;
    }

    @Override
    public int numStatesExplored() {
        return numStates;
    }

    @Override
    public double explorationTime() {
        return time;
    }
}
