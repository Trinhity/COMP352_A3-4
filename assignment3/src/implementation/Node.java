package implementation;

public class Node {

	private int key;
	private String value;
	private int height;
	private Node left;
	private Node right;
	private Node previous;
	private Node next;
	
	/**
	 * Constructor
	 * @param key
	 * @param value
	 */
	public Node(int key, String value) {
		this.key = key;
		this.value = value;
	}
	
	/**
	 * Set the key of this node
	 * @param key
	 */
	public void setKey(int key) {
		this.key = key;
	}
	
	/**
	 * Get the key of this node
	 * @return
	 */
	public int getKey() {
		return this.key;
	}
	
	/**
	 * Set the value of this node
	 * @param value
	 */
	public void setValue(String value) {
		this.value = value;
	}
	
	/**
	 * Get the value of this node
	 * @return
	 */
	public String getValue() {
		return this.value;
	}
	
	/**
	 * Set the max height of this node
	 * @param height
	 */
	public void setHeight(int height) {
		this.height = height;	
	}
	
	/**
	 * Get the max height of this node
	 * @return
	 */
	public int getHeight() {
		return this.height;	
	}
	
	/**
	 * Set the left child node of this node
	 * @param n
	 */
	public void setLeftNode(Node n) {
		this.left = n;
	}
	
	/**
	 * Get the left child node of this node
	 * @return
	 */
	public Node getLeftNode() {
		return this.left;	
	}
	
	/**
	 * Set the right child node of this node
	 * @param n
	 */
	public void setRightNode(Node n) {
		this.right = n;	
	}
	
	/**
	 * Get the right child node of this node
	 * @return
	 */
	public Node getRightNode() {
		return this.right;	
	}
	
	/**
	 * Set the previous node of this node following the inorder traversal order of an AVL tree
	 * @param n
	 */
	public void setPrevious(Node n) {
		this.previous = n;	
	}
	
	/**
	 * Get the previous node of this node following the inorder traversal order of an AVL tree
	 * @return
	 */
	public Node getPrevious() {
		return this.previous;	
	}
	
	/**
	 * Set the next node of this node following the inorder traversal order of an AVL tree
	 * @param n
	 */
	public void setNext(Node n) {
		this.next = n;	
	}
	
	/**
	 * Get the next node of this node following the inorder traversal order of an AVL tree
	 * @return
	 */
	public Node getNext() {
		return this.next;	
	}
	
}
