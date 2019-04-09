package com.codewars.basics;

// Java program for different tree traversals

/*
 * https://www.geeksforgeeks.org/level-order-tree-traversal/
 * https://www.geeksforgeeks.org/?p=618/
 * 
 * Class containing left and right child of current
 * node and key value
 */

public final class BinaryTree {

	public static final class Node {

		private int		key;
		private Node	left;
		private Node	right;

		public Node(final int item) {
			this.key = item;
			this.right = null;
			this.left = null;
		}

		public int getKey() {
			return key;
		}

		public void setKey(final int key) {
			this.key = key;
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

	// Root of Binary Tree
	private Node root;

	private BinaryTree() {
		root = null;
	}

	/*
	 * Given a binary tree, print its nodes according to the
	 * "bottom-up" postorder traversal.
	 */
	void printPostorder(final Node node) {
		if (node == null) {
			return;
		}

		// first recur on left subtree
		printPostorder(node.left);

		// then recur on right subtree
		printPostorder(node.right);

		// now deal with the node
		System.out.print(node.key + " ");
	}

	/* Given a binary tree, print its nodes in inorder */
	void printInorder(final Node node) {
		if (node == null) {
			return;
		}

		/* first recur on left child */
		printInorder(node.left);

		/* then print the data of node */
		System.out.print(node.key + " ");

		/* now recur on right child */
		printInorder(node.right);
	}

	/* Given a binary tree, print its nodes in preorder */
	void printPreorder(final Node node) {
		if (node == null) {
			return;
		}

		/* first print data of node */
		System.out.print(node.key + " ");

		/* then recur on left sutree */
		printPreorder(node.left);

		/* now recur on right subtree */
		printPreorder(node.right);
	}
	
		/* function to print level order traversal of tree */
	public void printLevelOrder() {
		int h = height(root);
		int i;
		for (i = 1; i <= h; i++) {
			printGivenLevel(root, i);
		}
	}

	/*
	 * Compute the "height" of a tree -- the number of
	 * nodes along the longest path from the root node
	 * down to the farthest leaf node.
	 */
	public int height(final Node root) {
		if (root == null) {
			return 0;
		} else {
			/* compute height of each subtree */
			int lheight = height(root.left);
			int rheight = height(root.right);

			/* use the larger one */
			if (lheight > rheight) {
				return (lheight + 1);
			} else {
				return (rheight + 1);
			}
		}
	}

	/* Print nodes at the given level */
	public void printGivenLevel(final Node root, final int level) {
		if (root == null) {
			return;
		}
		if (level == 1) {
			System.out.print(root.key + " ");
		} else if (level > 1) {
			printGivenLevel(root.left, level - 1);
			printGivenLevel(root.right, level - 1);
		}
	}

	// Wrappers over above recursive functions
	void printPostorder() {
		printPostorder(root);
	}

	void printInorder() {
		printInorder(root);
	}

	void printPreorder() {
		printPreorder(root);
	}

	// Driver method
	// CHECKSTYLE:OFF
	public static void main(final String[] args) {
		final BinaryTree tree = new BinaryTree();
		tree.root = new BinaryTree.Node(1);
		tree.root.left = new BinaryTree.Node(2);
		tree.root.left.left = new BinaryTree.Node(3);
		tree.root.left.left.left = new BinaryTree.Node(4);
		tree.root.left.left.right = new BinaryTree.Node(5);
		tree.root.left.right = new BinaryTree.Node(6);
		tree.root.left.right.left = new BinaryTree.Node(7);
		tree.root.left.right.right = new BinaryTree.Node(8);
		tree.root.right = new BinaryTree.Node(9);
		tree.root.right.left = new BinaryTree.Node(10);
		tree.root.right.right = new BinaryTree.Node(11);

		System.out.println("Preorder traversal of binary tree is ");
		tree.printPreorder();

		System.out.println("\nInorder traversal of binary tree is ");
		tree.printInorder();

		System.out.println("\nPostorder traversal of binary tree is ");
		tree.printPostorder();

		System.out.println("\nLevel order traversal of binary tree is ");
		tree.printLevelOrder();
	}
	// CHECKSTYLE:ON

}
