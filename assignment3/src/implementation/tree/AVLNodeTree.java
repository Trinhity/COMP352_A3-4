package implementation.tree;

import java.util.ArrayList;
import java.util.List;

import exceptions.DuplicateKeyException;
import exceptions.EmptyTreeException;
import exceptions.KeyNotFoundException;
import implementation.Node;
import implementation.NodeList;
import exceptions.InvalidKeyException;

public class AVLNodeTree {

	private Node root;
	private NodeList orderedSequence;
	
	// Optional variables to check total number of keys - dupes - invalid = actual size of orderedSequence
	private int dupeCounter = 0;
	private int invalidKeyCounter = 0;

	public AVLNodeTree() {
		this.orderedSequence = new NodeList();
	}

	/**
	 * Returns the root node
	 * @return
	 */
	public Node getRoot() {
		return this.root;
	}

	/**
	 * Public insert method to be used by external classes
	 * @param key
	 * @param value
	 * @throws DuplicateKeyException
	 * @throws InvalidKeyException 
	 */
	public int insert(int key, String value) throws DuplicateKeyException, InvalidKeyException {
		System.out.println("Adding new node with key: " + key + " and value: " + value);
		this.root = insert(this.root, key, value);
		return this.root.getKey();	
	}
	
	/**
	 * Public delete method to be used by external classes
	 * @param key
	 * @throws KeyNotFoundException
	 */
	public int delete(int key) throws KeyNotFoundException {
		System.out.println("Removing node with key: " + key);
		try {
			// Search for the key within the tree
			Node found = search(key);
			if (found.getKey() > 0) {
				// Key is found
				this.root = delete(this.root, key);
							
				return found.getKey();	
				
			} else {
				throw new KeyNotFoundException("Operation failed. Student ID does not exist: ");

			}

		} catch (KeyNotFoundException kdne) {
			System.out.println(kdne.getMessage() + key);
		}
		return -1;
	}

	/**
	 * Returns the height of the tree
	 * @param node
	 * @return
	 */
	public int getTreeHeight(Node node) {
		if (node == null) {
			return -1;
			
		}
		return node.getHeight();
	}

	/**
	 * Traverses the tree to find a node with a specific key
	 * @param key
	 * @return
	 * @throws KeyNotFoundException 
	 */
	public Node search(int key) throws KeyNotFoundException {
		Node dummyNode = new Node(0,"");
		Node currentNode = this.root;
		
		try {
			while (currentNode != null) {
				if (currentNode.getKey() == key) {
					// Node with key is found
					return currentNode;
	
				}
	
				if (currentNode.getKey() < key) {
					currentNode = currentNode.getRightNode();
	
				} else if (currentNode.getKey() > key){
					currentNode = currentNode.getLeftNode();
	
				} else {
					throw new KeyNotFoundException("Operation failed. Key does not exist: ");
					
				}
			}

		} catch (KeyNotFoundException knfe) {
			System.out.println(knfe.getMessage() + key);
			
		}		
		return dummyNode;
	}

	/**
	 * Traverses the AVL tree in an inorder traversal and stores the sequence a given list
	 * @param node
	 * @return
	 * @throws EmptyTreeException 
	 */
	public NodeList inOrderTraversal(Node node) throws EmptyTreeException {	
		try {
			if (this.root == null) {
				throw new EmptyTreeException("Operation failed. There are no student IDs. ");
			}
			// Get left children
			if (node.getLeftNode() != null) {
				inOrderTraversal(node.getLeftNode());

			}

			this.orderedSequence.insertLast(node);

			// Get right children
			if (node.getRightNode() != null) {
				inOrderTraversal(node.getRightNode());

			}	  
			
		} catch (EmptyTreeException ete) {
			System.out.println(ete.getMessage());

		}		
		return this.orderedSequence;
	}	
	
	/**
	 * Returns the number of keys between 2 keys if both inputs are valid and exists within the tree
	 * @param key1
	 * @param key2
	 * @return
	 * @throws KeyNotFoundException
	 * @throws InvalidKeyException
	 * @throws EmptyTreeException
	 */
	public int keysBetween(int key1, int key2) throws KeyNotFoundException, InvalidKeyException, EmptyTreeException {	
		System.out.println("Searching for number of keys between " + key1 + " & " + key2);
		Node foundKey1 = search(key1);
		Node foundKey2 = search(key2);
		int numberOfKeys = 0;
		
		try {
			// Check if both keys exist within the tree
			if ((foundKey1.getKey() > 0) && (foundKey2.getKey() > 0)) {
				if (foundKey1.getKey() < foundKey2.getKey()) {
					// Parameters were inserted from min to max 
					// Therefore, from min get next key to max
					Node currentNode = foundKey1.getNext();
					while (currentNode.getKey() < foundKey2.getKey()) {
						numberOfKeys++;
						currentNode = currentNode.getNext();
						
					}
					
				} else {
					// Parameters were inserted from max to min
					// Therefore, from max get previous key to min
					Node currentNode = foundKey2.getPrevious();
					while (currentNode.getKey() > foundKey1.getKey()) {
						numberOfKeys++;
						currentNode = currentNode.getPrevious();
						
					}
				}
				
			} else {
				throw new KeyNotFoundException("Operation failed. One or more keys do not exist. ");
				
			}
			
		} catch (KeyNotFoundException knfe) {
			System.out.println(knfe.getMessage());
			return -1;
			
		}	
		System.out.println("There are " + numberOfKeys + " keys between " + key1 + " & " + key2);
		return numberOfKeys;
	}

	/**
	 * Updates the height of the tree
	 * @param node
	 */
	private void updateHeight(Node node) {
		node.setHeight(1 + Math.max(getTreeHeight(node.getLeftNode()), getTreeHeight(node.getRightNode())));
	}

	/**
	 * Returns the difference between the height of the left node and the height of the right node
	 * @param node
	 * @return
	 */
	private int getBalance(Node node) {
		if (node == null) {
			return 0;
			
		}
		return getTreeHeight(node.getRightNode()) - getTreeHeight(node.getLeftNode());
	}

	/**
	 * Performs a right rotation trinode restructuring where node Y is the root node and X < Z < Y
	 * @param nodeY
	 * @return
	 */
	private Node rotateRight(Node nodeY) {
		Node nodeX = nodeY.getLeftNode();
		Node nodeZ = nodeX.getRightNode();

		// rotate right where node X becomes the root, node Y is the right child of node X, and node Z is the left child of node Y
		nodeX.setRightNode(nodeY);
		nodeY.setLeftNode(nodeZ);

		// update height of the new tree
		updateHeight(nodeY);
		updateHeight(nodeX);

		return nodeX;
	}

	/**
	 * Performs a left rotation trinode restructuring where node Y is the root node and Y < Z < X
	 * @param nodeY
	 * @return
	 */
	private Node rotateLeft(Node nodeY) {
		Node nodeX = nodeY.getRightNode();
		Node nodeZ = nodeX.getLeftNode();

		// rotate left where node X becomes the root, node Y is the left child of X, and node Z is the right child of Y
		nodeX.setLeftNode(nodeY);
		nodeY.setRightNode(nodeZ);

		// update height of the new tree
		updateHeight(nodeY);
		updateHeight(nodeX);

		return nodeX;
	}

	/**
	 * Shifts nodes around to satisfy the height-balance property of the AVL tree
	 * @param nodeZ
	 * @return
	 */
	private Node rebalanceTree(Node nodeZ) {
		updateHeight(nodeZ);
		int heightDifference = getBalance(nodeZ);

		if (heightDifference > 1) {
			// The right side of the tree has a higher height
			// Compare the height of the right child of node Z, designated as node Y	
			if (getTreeHeight(nodeZ.getRightNode().getRightNode()) > getTreeHeight(nodeZ.getRightNode().getLeftNode())) {
				// The height of the right child of node Y is greater than the height of the left child of node Y
				// Perform a single rotation type trinode restructuring to the left
				nodeZ = rotateLeft(nodeZ);

			} else {
				// The height of the left child of node Y is greater than the height the right child of node Y
				// Take the left subtree of node Y and set it as the right child of node Z
				nodeZ.setRightNode(rotateRight(nodeZ.getRightNode()));
				// Perform a single rotation type trinode restructurin to the left
				nodeZ = rotateLeft(nodeZ);

			}

		} else if (heightDifference < -1) {
			// The left side of the tree has a higher height
			// Compare the height of the left child of node Z, designated as node Y			
			if (getTreeHeight(nodeZ.getLeftNode().getLeftNode()) > getTreeHeight(nodeZ.getLeftNode().getRightNode())) {
				// The height of the left child of node Y is greater than the height of the right child of node Y
				// Perform single rotation type trinode restructuring to the right
				nodeZ = rotateRight(nodeZ);

			} else {
				// The height of the right child of node Y is greater than the height of the left child of node Y
				// Take the right subtree of node Y and set it as the left child of node Z
				nodeZ.setLeftNode(rotateLeft(nodeZ.getLeftNode()));
				// Perform a single rotation type trinode restructuring to the right
				nodeZ = rotateRight(nodeZ);

			}			
		}
		return nodeZ;
	}

	/**
	 * Inserts a new node into the tree and rebalances the tree
	 * @param node
	 * @param key
	 * @return
	 * @throws DuplicateKeyException
	 * @throws InvalidKeyException 
	 */
	private Node insert(Node node, int key, String value) throws DuplicateKeyException, InvalidKeyException {
		try {
			if (key < 10000000) {
				throw new InvalidKeyException("Operation failed. Student ID must be 8 digits: ");				
	
			} else if (node == null) {		
				// Becomes the root node
				return new Node(key, value);
	
			} else if (node.getKey() > key) {
				node.setLeftNode(insert(node.getLeftNode(), key, value));
	
			} else if (node.getKey() < key) {
				node.setRightNode(insert(node.getRightNode(), key, value));
	
			} else {
				throw new DuplicateKeyException("Operation failed. Key already exists: ");
	
			}
			
		} catch (InvalidKeyException kle) {
			this.invalidKeyCounter++;
			System.out.println(kle.getMessage() + key);
			
		} catch (DuplicateKeyException dke) {
			this.dupeCounter++;
			System.out.println(dke.getMessage() + key);
		}	
		return rebalanceTree(node);
	}

	/**
	 * Removes a node from the tree and rebalances the tree
	 * @param node
	 * @param key
	 * @return
	 * @throws KeyNotFoundException 
	 */
	private Node delete(Node node, int key) throws KeyNotFoundException {
		// Search for the key within the tree
		if (node == null) {
			throw new KeyNotFoundException("Operation failed. Unable to delete non-existent node. ");

		} else if (node.getKey() > key) {
			// The key is smaller than the current node
			node.setLeftNode(delete(node.getLeftNode(), key));

		} else if (node.getKey() < key) {
			// The key is larger than the current node
			node.setRightNode(delete(node.getRightNode(), key));

		} else {
			// Key is found		
			// Need to find a replacement for the node to be deleted
			if (node.getLeftNode() == null || node.getRightNode() == null) {

				if (node.getLeftNode() == null) {
					node = node.getRightNode();

				} else {
					node = node.getLeftNode();

				}

			} else {
				Node currentNode = node.getRightNode().getLeftNode();
				while (currentNode != null) {
					currentNode = node.getLeftNode();
				}

				node.setKey(currentNode.getKey());
				node.setRightNode(delete(node.getRightNode(), node.getKey()));

			}
		}

		if (node != null) {
			// Deleted node is replaced, need to rebalance tree
			node = rebalanceTree(node);

		}
		return node;
	}
	
	
	
	/********************************* OPTIONAL *********************************/
	/* These methods are only used to display results in the console
		Not required according to the assignment instructions, therefore
		 are omitted from the time and complexity calculations*/
	
	/**
	 * Helper method that displays the number of keys that were inserted/excluded from the test file
	 */
	public void displayTotalKeys() {
		int total = this.orderedSequence.size() + this.dupeCounter + this.invalidKeyCounter;
		System.out.println("Number of valid keys added from the test file: " + this.orderedSequence.size() 
			+ "\nNumber of duplicate keys in the test file: " + this.dupeCounter 
			+ "\nNumber of keys with invalid length in the test file: " + this.invalidKeyCounter
			+ "\nTotal keys in the test file: " + total);
	}
	
	/**
	 * Helper method to check number of duplicate keys
	 * @return
	 */
	public int getNumberOfDupeKeys() {
		return this.dupeCounter;
	}

	/**
	 * Helper method to check number of invalid keys
	 * @return
	 */
	public int getNumberOfInvalidKeys() {
		return this.invalidKeyCounter;
	}

	/**
	 * Print the tree
	 * @param root
	 */
    public void print(Node root) {
        List<List<String>> lines = new ArrayList<List<String>>();
        List<Node> level = new ArrayList<Node>();
        List<Node> next = new ArrayList<Node>();

        level.add(root);
        int nn = 1;
        int widest = 0;
        while (nn != 0) {
            List<String> line = new ArrayList<String>();
            nn = 0;
            for (Node n : level) {
                if (n == null) {
                    line.add(null);
                    next.add(null);
                    next.add(null);
                    
                } else {               	
                    String aa = String.valueOf(n.getKey());
                    line.add(aa);
                    if (aa.length() > widest) {
                    	widest = aa.length();
                    	
                    }

                    next.add(n.getLeftNode());
                    next.add(n.getRightNode());

                    if (n.getLeftNode() != null) {
                    	nn++;
                    	
                    }
                    
                    if (n.getRightNode() != null) {
                    	nn++;
                    	
                    }
                }
            }

            if (widest % 2 == 1) {
            	widest++;
            	
            }

            lines.add(line);
            List<Node> tmp = level;
            level = next;
            next = tmp;
            next.clear();
        }

        int perpiece = lines.get(lines.size()-1).size() * (widest+4);
        for (int i = 0; i < lines.size(); i++) {
            List<String> line = lines.get(i);
            int hpw = (int) Math.floor(perpiece/2f) - 1;
            if (i > 0) {
                for (int j = 0; j < line.size(); j++) {
                    // split node
                    char c = ' ';
                    if (j % 2 == 1) {
                        if (line.get(j-1) != null) {                         
                            c = '\'';
                            
                        } else {
                            if (j < line.size() && line.get(j) != null) {
                            	c = '\'';
                            	
                            }
                        }
                    }             
                    System.out.print(c);
                    // lines and spaces
                    if (line.get(j) == null) {
                        for (int k = 0; k < perpiece - 1; k++) {
                            System.out.print(" ");
                            
                        }
                        
                    } else {
                        for (int k = 0; k < hpw; k++) {
                            System.out.print(j % 2 == 0 ? " " : "-");
                            
                        }
                        
                        System.out.print(j % 2 == 0 ? "/" : "\\");                        
                        for (int k = 0; k < hpw; k++) {
                            System.out.print(j % 2 == 0 ? "-" : " ");
                            
                        }
                    }
                }               
                System.out.println();
            }
            // print line of numbers
            for (int j = 0; j < line.size(); j++) {
                String f = line.get(j);
                if (f == null) {
                	f = "";
                	
                }
                
                int gap1 = (int) Math.ceil(perpiece/2f - f.length()/2f);
                int gap2 = (int) Math.floor(perpiece/2f - f.length()/2f);
                // a number
                for (int k = 0; k < gap1; k++) {
                    System.out.print(" ");
                    
                }            
                System.out.print(f);            
                for (int k = 0; k < gap2; k++) {
                    System.out.print(" ");
                    
                }
            }           
            System.out.println();
            perpiece /= 2;
        }
    }
}
