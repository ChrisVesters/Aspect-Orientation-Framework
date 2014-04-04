/**
 * 
 * @file Node.java
 * @description This file contains the node of the AST.
 * @author Chris Vesters
 * @date 21/2/13
 * 
 **/

package aofC.Cparser.ast;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * 
 * This class represents a node in the AST.
 * 
 **/
public abstract class Node implements Serializable {

	/**
	 * 
	 * ID used for serialization.
	 * 
	 **/
	private static final long serialVersionUID = -4159543542706540746L;

	/**
	 * 
	 * The parent of the node.
	 * 
	 **/
	private Node parent = null;

	/**
	 * 
	 * The right sibling of the node.
	 * 
	 **/
	private Node rightSibling = null;

	/**
	 * 
	 * The leftmost sibling of the node. If this node is the leftmost sibling
	 * this will point to himself.
	 * 
	 **/
	private Node leftMostSibling = this;

	/**
	 * 
	 * The leftmost child of the node.
	 * 
	 **/
	private Node leftMostChild = null;

	/**
	 * 
	 * This method will verify whether a node is attachable. To be attachable a
	 * node can not have a parent, all its siblings should be attachable and
	 * following it's rightSiblings must be finite.
	 * 
	 * Note that if this method returns true for this node it will also return
	 * true for all its siblings!
	 * 
	 * @return A boolean indicating whether the node is independent or not.
	 * 
	 **/
	private boolean isAttachable() {
		ArrayList<Node> visited = new ArrayList<Node>();

		Node current = this.leftMostSibling;
		do {
			// To ensure consistency there may not be a parent!
			if (current.parent != null) {
				return false;
			}

			// To prevent losing siblings we may not encounter ourself.
			// Note that this is in normal situations impossible thanks to the
			// parent check.
			if (visited.contains(current)) {
				return false;
			}

			visited.add(current);
			current = current.rightSibling;
		} while (current != null);

		return true;
	}

	/**
	 * 
	 * This method will add all nodes as a sibling (to the right).
	 * 
	 * @param y
	 *            The node we want to add as a sibling. All siblings of y will
	 *            be added as well!
	 * 
	 **/
	public void makeSiblings(Node y) {
		assert (y != null);
		assert (y.isAttachable());

		Node rmsX = this;
		while (rmsX.rightSibling != null) {
			rmsX = rmsX.rightSibling;
		}

		// Check whether y is the leftmost sibling!
		Node lmsY = y.leftMostSibling;
		rmsX.rightSibling = lmsY;

		Node sibY = lmsY;
		do {
			sibY.parent = rmsX.parent;
			sibY.leftMostSibling = rmsX.leftMostSibling;
			sibY = sibY.rightSibling;
		} while (sibY != null);
	}

	/**
	 * 
	 * This method will add the node as a child at the end. The child must be
	 * the left most of it's siblings. Note that this will also add the siblings
	 * of the child.
	 * 
	 * 
	 * @param child
	 *            The node we want to add as a child.
	 * 
	 **/
	public void appendChild(Node child) {
		assert (child != null);
		assert (child.leftMostSibling == child);
		assert (child.isAttachable());

		if (this.leftMostChild == null) {
			this.leftMostChild = child;
		} else {
			Node rmc = this.leftMostChild;
			while (rmc.rightSibling != null) {
				rmc = rmc.rightSibling;
			}
			rmc.rightSibling = child;
		}

		Node sibling = child;
		do {
			sibling.parent = this;
			sibling.leftMostSibling = this.leftMostChild;
			sibling = sibling.rightSibling;
		} while (sibling != null);
	}

	/**
	 * 
	 * This method will add the child in front of the other children.
	 * 
	 * @param child
	 *            The child we want to add.
	 * 
	 **/
	public void prependChild(Node child) {
		assert (child != null);
		assert (child.leftMostSibling == child);
		assert (child.isAttachable());

		if (this.leftMostChild != null) {
			Node rms = child;
			while (rms.rightSibling != null) {
				rms = rms.rightSibling;
			}
			rms.rightSibling = this.leftMostChild;

			Node lmc = this.leftMostChild;
			do {
				lmc.leftMostSibling = child;
				lmc = lmc.rightSibling;
			} while (lmc != null);
		}
		this.leftMostChild = child;

		Node sibling = child;
		do {
			sibling.parent = this;
			// Note: the lms should already be correct!
			sibling.leftMostSibling = this.leftMostChild;
			sibling = sibling.rightSibling;
		} while (sibling != null);
	}

	/**
	 * 
	 * This method will return the parent of the node.
	 * 
	 * @return The parent of the node.
	 * 
	 **/
	public Node getParent() {
		return this.parent;
	}

	/**
	 * 
	 * This method will return all the siblings from left to right (himself
	 * included!).
	 * 
	 * @Note: used for testing!
	 * @TODO: use for other things as well!
	 * 
	 * @return All the siblings himself included.
	 * 
	 **/
	public Node[] getSiblings() {
		ArrayList<Node> siblings = new ArrayList<Node>();

		Node sibling = this.leftMostSibling;
		while (sibling != null) {
			siblings.add(sibling);
			sibling = sibling.rightSibling;
		}

		Node[] nodes = new Node[siblings.size()];
		siblings.toArray(nodes);
		return nodes;
	}

	/**
	 * 
	 * This method will return all the children.
	 * 
	 * @return All children in order from left to right.
	 * 
	 **/
	public Node[] getChildren() {
		if (this.leftMostChild != null) {
			return this.leftMostChild.getSiblings();
		} else {
			return new Node[0];
		}
	}

	/**
	 * 
	 * This method returns the name of the node. This will be used for pretty
	 * printing!
	 * 
	 * @return The name of the node.
	 * 
	 **/
	protected String getName() {
		String name = this.getClass().getName();
		name = name.substring(name.indexOf('.') + 1);
		return name;
	}

	/**
	 * 
	 * This method will print the tree with this node as root.
	 * 
	 * @param depth
	 *            The current depth of the node.
	 * 
	 **/
	public void print(int depth, OutputStream os) throws IOException {
		String ws = "";
		for (int i = 1; i <= depth; i++) {
			if (i == depth) {
				ws += " |-";
			} else {
				ws += " | ";
			}
		}

		os.write((ws + this.getName()).getBytes());
		os.write("\n".getBytes());

		for (Node child : this.getChildren()) {
			ws = ws.replace('-', ' ');
			os.write((ws + " |").getBytes());
			os.write("\n".getBytes());
			child.print(depth + 1, os);
		}
	}
}
