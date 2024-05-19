package hw5;

import java.util.*;

public class Graph {
    private int numVertices;
    private LinkedList<Edge>[] adjacencyList;

    private static class Edge {
        int vertex;
        int weight;

        Edge(int v, int w) {
            vertex = v;
            weight = w;
        }
    }

    public Graph(int numVertices) {
        this.numVertices = numVertices;
        adjacencyList = new LinkedList[numVertices];
        for (int i = 0; i < numVertices; i++) {
            adjacencyList[i] = new LinkedList<>();
        }
    }

    public void addEdge(int startVertex, int endVertex, int weight) {
        // Part 1 : Add code to add edges here
    	adjacencyList[startVertex].add(new Edge(endVertex, weight));
    	adjacencyList[endVertex].add(new Edge(startVertex, weight)); // This line makes the edge undirected
    }

    public void dijkstra(int startVertex) {
        int[] distances = new int[numVertices];
        boolean[] visited = new boolean[numVertices];
        PriorityQueue<VertexDistance> pq = new PriorityQueue<>(Comparator.comparingInt(vd -> vd.distance));

        Arrays.fill(distances, Integer.MAX_VALUE);
        distances[startVertex] = 0;
        pq.add(new VertexDistance(startVertex, 0));

        while (!pq.isEmpty()) {
            VertexDistance current = pq.poll();
            if (!visited[current.vertex]) {
                visited[current.vertex] = true;
                for (Edge e : adjacencyList[current.vertex]) {
                    if (!visited[e.vertex] && distances[e.vertex] > distances[current.vertex] + e.weight) {
                        distances[e.vertex] = distances[current.vertex] + e.weight;
                        pq.add(new VertexDistance(e.vertex, distances[e.vertex]));
                    }
                }
            }
        }

        System.out.println("Vertex distances from vertex " + startVertex + ": " + Arrays.toString(distances));
    }

    
    
    public boolean isConnected() {
        boolean[] visited = new boolean[numVertices];
        Queue<Integer> queue = new LinkedList<>();
        queue.add(0); // Start from vertex 0
        int count = 0;

        while (!queue.isEmpty()) {
            int vertex = queue.poll();
            // Part 2 : Add code here to properly define count
            if (!visited[vertex]) {
                visited[vertex] = true;
                count++;
                for (Edge e : adjacencyList[vertex]) {
                    if (!visited[e.vertex]) {
                        queue.add(e.vertex);
                    }
                }
            }
        }
        return count == numVertices;
    }

    public boolean hasCycle() {
        // Part 3 : Check if cycle exists in the graph here
        boolean[] visited = new boolean[numVertices];

        for (int i = 0; i < numVertices; i++) {
            if (!visited[i]) {
                if (cycleDFS(i, visited, -1)) {
                    return true;
                }
            }
        }
        return false;
    }

    // Add any helper functions if needed here :

    private boolean cycleDFS(int vertex, boolean[] visited, int parent) {
        visited[vertex] = true;

        // Explore all adjacent vertices
        for (Edge neighbor : adjacencyList[vertex]) {
            // If not visited, recurse on the adjacent node
            if (!visited[neighbor.vertex]) {
                if (cycleDFS(neighbor.vertex, visited, vertex)) {
                    return true;
                }
            }
            // If visited and not the parent, it's a cycle
            else if (neighbor.vertex != parent) {
                return true;
            }
        }
        return false;
    }
    private static class VertexDistance {
        int vertex;
        int distance;

        VertexDistance(int vertex, int distance) {
            this.vertex = vertex;
            this.distance = distance;
        }
    }


    /////

    public static void main(String[] args) {
        Graph graph = new Graph(5);
        graph.addEdge(0, 1, 9);
        graph.addEdge(0, 2, 6);
        graph.addEdge(0, 3, 5);
        graph.addEdge(0, 4, 3);
        graph.addEdge(2, 1, 2);
        graph.addEdge(2, 3, 4);

        System.out.println("Running Dijkstra's Algorithm from vertex 1:");
        graph.dijkstra(1);
        // Vertex distances from vertex 1: [8, 0, 2, 6, 11]

        System.out.println("Checking if the graph is connected:");
        System.out.println(graph.isConnected() ? "The graph is connected." : "The graph is not connected.");
        // The graph is connected.
        
        System.out.println("Checking for cycles in the graph:");
        System.out.println(graph.hasCycle() ? "The graph has a cycle." : "The graph does not have a cycle.");
        // The graph has a cycle.
    }
}