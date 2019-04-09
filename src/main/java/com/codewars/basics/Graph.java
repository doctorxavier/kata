package com.codewars.basics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import java.util.stream.Collectors;

public final class Graph {

	public static final class Vertex {

		private String	key;
		private String	label;

		public Vertex(final String key, final String label) {
			this.key = key;
			this.label = label;
		}

		public String getKey() {
			return key;
		}

		public void setKey(final String key) {
			this.key = key;
		}

		public String getLabel() {
			return label;
		}

		public void setLabel(final String label) {
			this.label = label;
		}

		@Override
		public boolean equals(final Object obj) {
			if (obj == null || !(obj instanceof Vertex)) {
				return false;
			}
			final Vertex vertex = (Vertex) obj;
			return vertex.key.compareTo(this.key) == 0;
		}

		@Override
		public int hashCode() {
			return key.hashCode();
		}

		@Override
		public String toString() {
			return key;
		}

	}

	private Map<Vertex, List<Vertex>> adjVertices;

	private Graph() {
		this.adjVertices = new HashMap<Vertex, List<Vertex>>();
	}

	public List<Vertex> getAdjVertices(final Vertex vertex) {
		return adjVertices.get(vertex);
	}

	public void addVertex(final Vertex vertex) {
		adjVertices.putIfAbsent(vertex, new ArrayList<Vertex>(0));
	}

	public void addEdge(final Vertex v1, final Vertex v2) {
		adjVertices.get(v1).add(v2);
		adjVertices.get(v2).add(v1);
	}

	public void removeVertex(final Vertex vertex) {
		adjVertices.values().stream().map(e -> e.remove(vertex)).collect(Collectors.toList());
		adjVertices.remove(vertex);
	}

	public void removeEdge(final Vertex v1, final Vertex v2) {
		final List<Vertex> eV1 = adjVertices.get(v1);
		final List<Vertex> eV2 = adjVertices.get(v2);
		if (eV1 != null) {
			eV1.remove(v2);
		}
		if (eV2 != null) {
			eV2.remove(v1);
		}
	}

	public Set<Vertex> depthFirstTraversal(final Graph graph, final Vertex root) {
		final Set<Vertex> visited = new LinkedHashSet<Vertex>();
		final Stack<Vertex> stack = new Stack<Vertex>();
		stack.push(root);
		while (!stack.isEmpty()) {
			final Vertex vertex = stack.pop();
			if (!visited.contains(vertex)) {
				visited.add(vertex);
				for (final Vertex v : graph.getAdjVertices(vertex)) {
					stack.push(v);
				}
			}
		}
		return visited;
	}

	public Set<Vertex> breadthFirstTraversal(final Graph graph, final Vertex root) {
		final Set<Vertex> visited = new LinkedHashSet<Vertex>();
		final Queue<Vertex> queue = new LinkedList<Vertex>();
		queue.add(root);
		visited.add(root);
		while (!queue.isEmpty()) {
			final Vertex vertex = queue.poll();
			for (final Vertex v : graph.getAdjVertices(vertex)) {
				if (!visited.contains(v)) {
					visited.add(v);
					queue.add(v);
				}
			}
		}
		return visited;
	}

	// CHECKSTYLE:OFF
	public static void main(final String[] args) {
		final Graph graph = new Graph();

		final String[] people = new String[]
			{"Bob", "Alice", "Mark", "Rob", "Maria"};

		final List<Vertex> vertices = new ArrayList<Vertex>(0);

		for (final String person : people) {
			Vertex vertex = new Vertex(person.toLowerCase(Locale.ENGLISH), person);
			vertices.add(vertex);
			graph.addVertex(vertex);
		}

		graph.addEdge(vertices.get(0), vertices.get(1));
		graph.addEdge(vertices.get(0), vertices.get(3));
		graph.addEdge(vertices.get(1), vertices.get(2));
		graph.addEdge(vertices.get(3), vertices.get(2));
		graph.addEdge(vertices.get(1), vertices.get(4));
		graph.addEdge(vertices.get(3), vertices.get(4));

		Set<Vertex> verticesSet = graph.breadthFirstTraversal(graph, vertices.get(0));
		System.out.println(verticesSet.toString());

		verticesSet = graph.depthFirstTraversal(graph, vertices.get(0));
		System.out.println(verticesSet.toString());

	}
	// CHECKSTYLE:ON

}
