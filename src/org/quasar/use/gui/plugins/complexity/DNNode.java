package org.quasar.use.gui.plugins.complexity;

public class DNNode {

	private String className;
	private int weight;
	private DNNode leftChild;
	private DNNode rightChild;

	public static DNNode createNode(String className) {
		return new DNNode(className, 1);
	}

	private DNNode(String className, int weight) {
		this.className = className;
		this.weight = weight;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public DNNode getLeftChild() {
		return leftChild;
	}

	public void setLeftChild(DNNode leftChild) {
		this.leftChild = leftChild;
	}

	public DNNode getRightChild() {
		return rightChild;
	}

	public void setRightChild(DNNode rightChild) {
		this.rightChild = rightChild;
	}

	public int getLength() {
		return getLength(this, 0);
	}

	private int getLength(DNNode node, int commulative) {
		int leftPath = 0;
		int rightPath = 0;
		int nodeValue = node.weight == 1 ? 1 : 3;
		if (node.getLeftChild() != null)
			leftPath = getLength(node.getLeftChild(), commulative + nodeValue);
		if (node.getRightChild() != null)
			rightPath = getLength(node.getLeftChild(), commulative + nodeValue);
		if (leftPath + rightPath == 0)
			return commulative + nodeValue;
		else
			return Math.max(leftPath, rightPath);
	}

}
