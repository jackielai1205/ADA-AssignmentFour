public class GraphNode {

    private final String name;
    private double value;
    public static double Infinity = Double.MAX_VALUE;
    private GraphNode predecessor;

    public GraphNode(String name) {
        this.name = name;
        this.value = Infinity;
        predecessor = null;
    }

    public String getName() {
        return name;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public void setPredecessor(GraphNode predecessor) {
        this.predecessor = predecessor;
    }

    public GraphNode getPredecessor() {
        return predecessor;
    }

    @Override
    public String toString() {
        return "GraphNode{" +
                "name='" + name + '\'' +
                '}';
    }
}
