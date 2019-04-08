package com.codewars.basics;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/*
 * Java program to print BFS traversal from a given source vertex.
 * BFS(int s) traverses vertices reachable from s.
 * 
 * This class represents a directed graph using adjacency list
 * representation
 * This code is contributed by Aakash Hasija
 */
public final class BFSGraph {

	// No. of vertices
	private int vertices;
	// Adjacency Lists
	private List<Integer>[] adj;

	// Constructor
	@SuppressWarnings("unchecked")
	private BFSGraph(final int v) {
		vertices = v;
		adj = (LinkedList<Integer>[]) new LinkedList<?>[v];
		for (int i = 0; i < v; ++i) {
			adj[i] = new LinkedList<Integer>();
		}
	}

	// Function to add an edge into the graph
	public void addEdge(final int v, final int w) {
		adj[v].add(w);
	}

	// prints BFS traversal from a given source s
	public void bfs(final int s) {
		int vertex = s;
		// Mark all the vertices as not visited(By default
		// set as false)
		final boolean[] visited = new boolean[vertices];

		// Create a queue for BFS
		final LinkedList<Integer> queue = new LinkedList<Integer>();

		// Mark the current node as visited and enqueue it
		visited[vertex] = true;
		queue.add(vertex);

		while (queue.size() != 0) {
			// Dequeue a vertex from queue and print it
			vertex = queue.poll();
			System.out.print(vertex + " ");

			// Get all adjacent vertices of the dequeued vertex s
			// If a adjacent has not been visited, then mark it
			// visited and enqueue it
			Iterator<Integer> i = adj[vertex].listIterator();
			while (i.hasNext()) {
				int n = i.next();
				if (!visited[n]) {
					visited[n] = true;
					queue.add(n);
				}
			}
		}
	}

	// Driver method to
	public static void main(final String[] args) {
		final BFSGraph g = new BFSGraph(4);

		g.addEdge(0, 1);
		g.addEdge(0, 2);
		g.addEdge(1, 2);
		g.addEdge(2, 0);
		g.addEdge(2, 3);
		g.addEdge(3, 3);

		System.out.println("Following is Breadth First Traversal " + "(starting from vertex 2)");

		g.bfs(2);
	}

}
