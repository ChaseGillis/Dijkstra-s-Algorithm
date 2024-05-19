package hw5;
import hw5.Graph;

public class GraphTest {

    public static void main(String[] args) {
        testSimpleGraph();
        testGraphWithNegativeEdge(); // Note: Dijkstra's shouldn't handle negative weights.
        testGraphWithMultipleShortestPaths();
        testDisconnectedGraph();
    }

    private static void testSimpleGraph() {
        Graph graph = new Graph(4);
        graph.addEdge(0, 1, 1);
        graph.addEdge(0, 2, 4);
        graph.addEdge(1, 2, 2);
        graph.addEdge(2, 3, 1);
        graph.addEdge(1, 3, 5);
        

        System.out.println("Simple Graph Test below:");
        graph.dijkstra(0);
        System.out.println("Check if cycle exists below:");
        System.out.println(graph.hasCycle() ? "The graph has a cycle." : "The graph does not have a cycle.");
        // Expecting Output to be:  Vertex distances from vertex 0: [0, 1, 3, 4]
        System.out.println("****************************************");
    }

    private static void testGraphWithNegativeEdge() {
        Graph graph = new Graph(3);
        graph.addEdge(0, 1, 2);
        graph.addEdge(1, 2, -4);  // Dijkstra's algorithm does not support negative weights
        graph.addEdge(0, 2, 8);
        
        
        System.out.println("Graph with Negative Edge Test below:");
        graph.dijkstra(0);
        System.out.println("****************************************");
        // expect : Vertex distances from vertex 0: [0, 2, -2]
        // This is just illustrative; in a real-world scenario, this should either not happen.
    }

    private static void testGraphWithMultipleShortestPaths() {
        Graph graph = new Graph(5);
        graph.addEdge(0, 1, 1);
        graph.addEdge(0, 2, 1);
        graph.addEdge(1, 3, 1);
        graph.addEdge(2, 3, 1);
        graph.addEdge(3, 4, 1);
        graph.addEdge(1, 4, 3);  // Another path to 4, but not the shortest one
        

        System.out.println("Graph with Multiple Shortest Paths Test");
        graph.dijkstra(0);
        System.out.println("****************************************");
        // Output : Vertex distances from vertex 0: [0, 1, 1, 2, 3]
    }

    private static void testDisconnectedGraph() {
        Graph graph = new Graph(6);
        graph.addEdge(0, 1, 1);
        graph.addEdge(1, 2, 2);
        // Disconnected component
        graph.addEdge(4, 5, 1);
        
        System.out.println("Disconnected Graph Test - looking at Dji. weights");
        graph.dijkstra(0); // Expecting that the distances to vertices 4 and 5 are still Integer.MAX_VALUE
        System.out.println("Checking connectivity");
        System.out.println(graph.isConnected() ? "The graph is connected." : "The graph is not connected.");
        System.out.println("****************************************");
    }
}
