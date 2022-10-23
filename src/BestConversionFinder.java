import java.util.ArrayList;

public class BestConversionFinder {

    public int nodesLength;
    public int pathsOfLength;
    public GraphNode[] nodes;
    public double[][] table;
    public GraphNode startNode;


    public ArrayList<ArrayList<GraphNode>> findShortestPath(GraphNode destinationNode){
        ArrayList<ArrayList<GraphNode>> result = new ArrayList<>();
        ArrayList<GraphNode> firstList = this.findShortestPathHelper(destinationNode);
        int destinationNodeIndex = -1;
        for(int x = 0; x < nodesLength; x++){
            if(destinationNode == nodes[x]){
                destinationNodeIndex = x;
            }
        }
        BestConversionFinder destinationFinder = new BestConversionFinder(table, destinationNodeIndex);
        for(int x = 0; x < destinationFinder.nodesLength; x++){
            if(destinationFinder.nodes[x].getName().equals(startNode.getName())){
                destinationNodeIndex = x;
            }
        }
        ArrayList<GraphNode> secondList = destinationFinder.findShortestPathHelper(destinationFinder.nodes[destinationNodeIndex]);
        result.add(firstList);
        result.add(secondList);
        return result;
    }


    public ArrayList<GraphNode> findShortestPathHelper(GraphNode destinationNode){
        for(int z = 0; z < nodesLength - 1; z++){
            for(int x = 0; x < nodesLength; x++){
                for(int y = 0; y < nodesLength; y++){
                    if(table[x][y] != 0 && nodes[x].getValue() != Double.MAX_VALUE){
                        double newWeight = nodes[x].getValue() + table[x][y];
                        double originalWeight = nodes[y].getValue();
                        if(newWeight < originalWeight){
                            nodes[y].setPredecessor(nodes[x]);
                            nodes[y].setValue(newWeight);
                        }
                    }
                }
            }
        }
        ArrayList<GraphNode> result = new ArrayList<>();
        GraphNode currentNode = destinationNode;
        while(currentNode.getPredecessor() != null){
            result.add(currentNode);
            currentNode = currentNode.getPredecessor();
        }
        return result;

//        for(int x = 0; x < nodesLength; x++){
//            for(int y = 0; y < nodesLength; y++){
//                if(table[x][y] != 0 && table[x][y] != 1 && nodes[x].getValue() != Double.MAX_VALUE){
//                    double newWeight = nodes[x].getValue() + table[x][y];
//                    double originalWeight = nodes[y].getValue();
//                    if(newWeight < originalWeight){
//                        ArrayList<GraphNode> arbitrage = new ArrayList<>();
//                        GraphNode currentNode = nodes[x];
//                        while(true){
//                            arbitrage.add(currentNode);
//                            currentNode = currentNode.getPredecessor();
//                            if(arbitrage.contains(currentNode)){
//                                break;
//                            }
//                        }
//                        System.out.println(arbitrage);
//                    }
//                }
//            }
//        }
    }

    public BestConversionFinder(double[][] table, int startIndex) {
        this.nodesLength = table.length;
        this.table = table;
        this.nodes = new GraphNode[]{
                new GraphNode("AUD"),
                new GraphNode("EUR"),
                new GraphNode("MXN"),
                new GraphNode("NZD"),
                new GraphNode("USD"),
        };
        this.nodes[startIndex].setValue(0);
        startNode = this.nodes[startIndex];
        this.pathsOfLength = 1;
    }

    private ArrayList<GraphNode> arbitrageFinderHelper(){
        ArrayList<GraphNode> output = new ArrayList<>();
        double originalPointValue = startNode.getValue();
        if(originalPointValue < 0){
            GraphNode currentNode = startNode;
            do {
                output.add(currentNode);
                currentNode = currentNode.getPredecessor();
            } while (!output.contains(currentNode));
        }
        return output;
    }

    public ArrayList<ArrayList<GraphNode>> arbitrageFinder(){
        ArrayList<ArrayList<GraphNode>> arbitrageList = new ArrayList<>();
        for(int x = 0; x < nodesLength; x++){
            BestConversionFinder currentFinder = new BestConversionFinder(this.table, x);
            currentFinder.findShortestPathHelper(nodes[x]);
            ArrayList<GraphNode> arbitrageFinder = currentFinder.arbitrageFinderHelper();
            if(arbitrageFinder.size() > 0){
                arbitrageList.add(arbitrageFinder);
            }
        }
        return arbitrageList;
    }

    public String toString() {
        String output = "Matrix for k=" + pathsOfLength + "\n";
        for (int i = 0; i < nodesLength; i++) {
            for (int j = 0; j < nodesLength; j++) {
                output += (" " + table[i][j]);
            }
            output += "\n";
        }
        return output;
    }

    public static void main(String[] args) {
        double[][] table = {
//                {1, 0.61, 0, 1.08, 0.72},
//                {1.64, 1, 0, 1.77, 1.18},
//                {0, 0, 1, 0, 0.047},
//                {0.92, 0.56, 0, 1, 0.66},
//                {1.39, 0.85, 21.19, 1.4, 1},
                {1, 0.6, 0, 1.07, 0.71},
                {1.63, 1, 0, 1.76, 1.17},
                {0, 0, 1, 0, 0.046},
                {0.91, 0.55, 0, 1, 0.65},
                {1.38, 0.84, 21.18, 1.3, 1},
        };
        for(int x = 0; x < table.length; x++){
            for(int y = 0; y < table[x].length; y++){
                if(table[x][y] != 0 && table[x][y] != 1){
                    table[x][y] = Math.log(1/table[x][y]);
                }
            }
        }
        BestConversionFinder bcf = new BestConversionFinder(table, 1);
        System.out.println(bcf.findShortestPath(bcf.nodes[0]));
        System.out.println(bcf.arbitrageFinder());
        for(int x = 0; x < bcf.nodesLength; x++){
            System.out.println(1/Math.exp(bcf.nodes[x].getValue()));
        }
        for(int x = 0; x < bcf.nodesLength; x++){
            System.out.println(bcf.nodes[x].getValue());
        }
    }
}
