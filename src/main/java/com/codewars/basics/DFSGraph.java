package com.codewars.basics;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/*
 * Java program to print DFS traversal from a given given graph
 * This class represents a directed graph using adjacency list
 * representation
 * This code is contributed by Aakash Hasija
 */
public final class DFSGraph {

	private int				vertices;	// No. of vertices

	// Array of lists for Adjacency List Representation
	private List<Integer>[]	adj;

	// Constructor
	@SuppressWarnings("unchecked")
	private DFSGraph(final int v) {
		vertices = v;
		adj = (LinkedList<Integer>[]) new LinkedList<?>[v];
		for (int i = 0; i < v; ++i) {
			adj[i] = new LinkedList<Integer>();
		}
	}

	// Function to add an edge into the graph
	public void addEdge(final int v, final int w) {
		// Add w to v's list.
		adj[v].add(w);
	}

	// A function used by DFS
	public void dfsUtil(final int v, final boolean[] visited) {
		// Mark the current node as visited and print it
		visited[v] = true;
		System.out.print(v + " ");

		// Recur for all the vertices adjacent to this vertex
		Iterator<Integer> i = adj[v].listIterator();
		while (i.hasNext()) {
			int n = i.next();
			if (!visited[n]) {
				dfsUtil(n, visited);
			}
		}
	}

	// The function to do DFS traversal. It uses recursive DFSUtil()
	public void dfs(final int v) {
		// Mark all the vertices as not visited(set as
		// false by default in java)
		boolean[] visited = new boolean[vertices];

		// Call the recursive helper function to print DFS traversal
		dfsUtil(v, visited);
	}

	public static void main(final String[] args) {
		final DFSGraph g = new DFSGraph(4);

		g.addEdge(0, 1);
		g.addEdge(0, 2);
		g.addEdge(1, 2);
		g.addEdge(2, 0);
		g.addEdge(2, 3);
		g.addEdge(3, 3);

		System.out.println("Following is Depth First Traversal " + "(starting from vertex 2)");

		g.dfs(2);
	}
}
