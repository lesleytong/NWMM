package edu.ustb.sei.mde.graph.typedGraph;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import edu.ustb.sei.mde.bxcore.exceptions.NothingReturnedException;

public class TopoGraph {
    // Number of vertices
    int V;
    // An array of list which contains
    // references to the adjacency list of each vertex
    List<Integer> adj[];

    // Constructor
    TopoGraph(int v) {
        this.V = v;
        adj = new ArrayList[V];
        for (int i = 0; i < V; i++) {
            adj[i] = new ArrayList<Integer>();
        }
    }

    // Add an edge to the graph
    public void addEdge(int u, int v) {
        adj[u].add(v);
    }

    // Prints a topological sort of the graph
    public ArrayList<Integer> topologicalSort() throws NothingReturnedException {
        // Create an array to sore indegrees of all vertices
        int[] indegree = new int[V];
        // Traverse adjacency lists to fill indegrees of vertices
        // This step takes O(V+E) time
        for (int i = 0; i < V; i++) {
            ArrayList<Integer> temp = (ArrayList<Integer>) adj[i];
            for (int node : temp) {
                indegree[node]++;
            }
        }
        // Create an queue and enqueue all vertices with indegree 0
        Queue<Integer> q = new LinkedList<>();
        for (int i = 0; i < V; i++) {
            if (indegree[i] == 0) {
                q.add(i);
            }
        }
        // Initialize count of visited vertices
        int cnt = 0;
        // Create a vector to store result
        ArrayList<Integer> order = new ArrayList<>();
        // A topological sort of the vertices
        while(!q.isEmpty()){
            // Extract front of queue
            // and add it to order
            int u = q.poll();
            order.add(u);
            cnt++;
            // Iterate through all its neighbouring nodes of dequeued node u
            // and decrease their indegree by 1
            for(int node : adj[u]){
                if(--indegree[node] == 0){
                    // If indegree becomes zero, add it to queue
                    q.add(node);
                }
            }
        }
        // Check if there was a cycle
        if(cnt != V){
            throw new NothingReturnedException("There exists a cycle in the graph");
        }
        return order;

    }

}

