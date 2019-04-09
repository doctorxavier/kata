package org.geeksforgeeks.code.test;

import java.util.LinkedList;
import java.util.Queue;

public class GFGOddLevelDifference {

	public static final class Node {

		private int		data;
		private int		index;
		private Node	left;
		private Node	right;

		public Node(final int data, final int index) {
			this.data = data;
			this.index = index;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(final int index) {
			this.index = index;
		}

		public int getData() {
			return data;
		}

		public void setData(final int data) {
			this.data = data;
		}

		public Node getLeft() {
			return left;
		}

		public void setLeft(final Node left) {
			this.left = left;
		}

		public Node getRight() {
			return right;
		}

		public void setRight(final Node right) {
			this.right = right;
		}

	}

	public int evenOddLevelDifference(final Node root) {

		final Queue<Node> queue = new LinkedList<Node>();
		queue.add(root);

		int level = 0;
		int evenSum = 0;
		int oddSum = 0;

		while (queue.size() != 0) {
			int size = queue.size();
			level++;
			while (size > 0) {

				final Node node = queue.poll();
				if (node.getLeft() != null) {
					queue.add(node.getLeft());
				}
				if (node.getRight() != null) {
					queue.add(node.getRight());
				}

				if (level % 2 == 0) {
					evenSum += node.data;
				} else {
					oddSum += node.data;
				}

				System.out.println(String.format("index: %d, level: %d, size: %d", node.getIndex(), level, size));
				size--;
			}
		}
		return oddSum - evenSum;
	}

	// CHECKSTYLE:OFF
	public static void main(String args[]) {

		GFGOddLevelDifference gfg = new GFGOddLevelDifference();
		// construct a tree
		final Node root = new Node(5, 1);
		root.left = new Node(2, 2);
		root.right = new Node(6, 3);

		root.left.left = new Node(1, 4);
		root.left.right = new Node(4, 5);

		root.right.right = new Node(8, 6);

		root.left.right.left = new Node(3, 7);
		root.right.right.left = new Node(7, 8);
		root.right.right.right = new Node(9, 9);

		System.out.println("\nDiffence between sums is " + gfg.evenOddLevelDifference(root));
	}
	// CHECKSTYLE:ON

}
