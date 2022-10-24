import java.io.BufferedReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;
import java.util.Stack;

public class BridgeExchangeFinder {
    public static void main(String[] args) {

        ArrayList<BridgeGraphNode> visitedNode = new ArrayList<>();

        int[][] list = { {0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 1, 0, 0, 1, 1, 1, 0, 1, 0, 0, 0},
                {0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 1},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0} };

        BridgeGraphNode[] nodes = { new BridgeGraphNode("a", null),
                new BridgeGraphNode("b", null),
                new BridgeGraphNode("c", null),
                new BridgeGraphNode("d", null),
                new BridgeGraphNode("e", null),
                new BridgeGraphNode("f", null),
                new BridgeGraphNode("g", null),
                new BridgeGraphNode("h", null),
                new BridgeGraphNode("i", null),
                new BridgeGraphNode("j", null),
                new BridgeGraphNode("k", null),
                new BridgeGraphNode("l", null),
                new BridgeGraphNode("m", null) };

        for(int currentNodeIndex = 0; currentNodeIndex < list.length; currentNodeIndex++){
            BridgeGraphNode currentNode = nodes[currentNodeIndex];
            for(int index = 0; index < list[currentNodeIndex].length; index++){
                if(list[currentNodeIndex][index] == 1){
                    currentNode.childrens.add(nodes[index]);
                }
            }
        }

        BridgeExchangeFinder finder = new BridgeExchangeFinder();
        finder.depthFirstSearch(nodes, visitedNode);
        finder.distanceSearch(nodes, visitedNode);
    }

    public void depthFirstSearch(BridgeGraphNode[] nodes, ArrayList<BridgeGraphNode> visited){
        Queue<BridgeGraphNode> waitingList = new ArrayDeque<>();
        BridgeGraphNode currentNode = nodes[0];
        waitingList.add(currentNode);
        int value = 0;

        while(waitingList.size() != 0){
            currentNode = waitingList.poll();
            visited.add(currentNode);
            currentNode.value = value;
            value++;
            if(currentNode.childrens.size() != 0){
                for(BridgeGraphNode node : currentNode.childrens){
                    boolean repeated = false;
                    for(BridgeGraphNode newNode : visited){
                        if (newNode == node) {
                            repeated = true;
                            break;
                        }
                    }
                    for(BridgeGraphNode newNode : waitingList){
                        if (newNode == node){
                            repeated = true;
                            break;
                        }
                    }
                    if(!repeated){
                        waitingList.add(node);
                    }
                }
            }
            System.out.println(currentNode.name + ": " + currentNode.value);
        }
    }

    public void distanceSearch(BridgeGraphNode[] nodes, ArrayList<BridgeGraphNode> visited){
        Queue<BridgeGraphNode> waitingList = new ArrayDeque<>();
        BridgeGraphNode currentNode = nodes[0];
        waitingList.add(currentNode);

        while(waitingList.size() != 0){
            currentNode = waitingList.poll();
            visited.add(currentNode);
            if(currentNode.childrens.size() != 0){
                for(BridgeGraphNode node : currentNode.childrens){
                    boolean repeated = false;
                    for(BridgeGraphNode newNode : visited){
                        if (newNode == node) {
                            repeated = true;
                            break;
                        }
                    }
                    for(BridgeGraphNode newNode : waitingList){
                        if (newNode == node){
                            repeated = true;
                            break;
                        }
                    }
                    if(!repeated){
                        waitingList.add(node);
                    }
                }
            }
            System.out.println(currentNode.name + ": " + currentNode.value);
        }
    }

    static class BridgeGraphNode{
        public String name;
        public BridgeGraphNode parent;
        public int value;
        public ArrayList<BridgeGraphNode> childrens;

        BridgeGraphNode(String name, BridgeGraphNode parent){
            this.name = name;
            this.parent = parent;
            this.childrens = new ArrayList<>();
        }
    }
}
