package main;

import java.util.Random;

import exceptions.DuplicateKeyException;
import exceptions.EmptyTreeException;
import exceptions.KeyNotFoundException;
import exceptions.InvalidKeyException;
import exceptions.ThresholdExceededException;
import implementation.Node;
import implementation.NodeList;
import implementation.tree.AVLNodeTree;
import implementation.hashTable.NodeHashTable;

public class CleverSIDC {
	private int size;
	private AVLNodeTree tree;

	/**
	 * Constructor for CleverSIDC
	 */
	public CleverSIDC(int size) {
		this.size = size;
		this.tree = new AVLNodeTree();		
	}
	
	/**
	 * Sets the max number of student IDs to be stored
	 * @param size
	 */
	public void SetSIDCThreshold(int size) {
		this.size = size;	
	}
	
	/**
	 * Returns the max number of student IDs
	 * @return
	 */
	public int getSIDCThreshold() {
		return this.size;
	}
	
	/**
	 * Generates a unique random 8 digit student ID
	 * @return
	 * @throws KeyNotFoundException 
	 */
	public int generate() throws KeyNotFoundException {
		Random random = new Random();		
		int newSIDC = 10000000 + random.nextInt(90000000);
		
		while (this.tree.search(newSIDC).getKey() > 0) {
			newSIDC = 10000000 + random.nextInt(90000000);
			
		}	
		return newSIDC;			
	}
	
	/**
	 * Inserts a new entry in the tree
	 * @param csidc
	 * @param key
	 * @param value
	 * @return
	 * @throws DuplicateKeyException
	 * @throws InvalidKeyException 
	 * @throws ThresholdExceededException 
	 */
	public int add(CleverSIDC csidc, int key, String value) throws DuplicateKeyException, InvalidKeyException, ThresholdExceededException {	
		try {	
			// Check if threshold has been reached
			if ((Math.pow(2, this.tree.getTreeHeight(this.tree.getRoot())+1)-1) <= csidc.getSIDCThreshold()) {
				return csidc.tree.insert(key, value);
				
			} else {
				throw new ThresholdExceededException("Operation failed. Maximum threshold exceeded. ");
				
			}
				
		} catch (ThresholdExceededException tee) {
			System.out.println(tee.getMessage());
			
		}			
		return -1;
	}
	
	/**
	 * Deletes an entry in the tree
	 * @param csidc
	 * @param key
	 * @return
	 * @throws KeyNotFoundException
	 */
	public int remove(CleverSIDC csidc, int key) throws KeyNotFoundException {
		return csidc.tree.delete(key);
	}
	
	/**
	 * Returns a sorted sequence of all student IDs
	 * @param csidc
	 * @return
	 * @throws EmptyTreeException 
	 */
	public NodeList allKeys(CleverSIDC csidc) throws EmptyTreeException {	
		// The method inOrderTraversal must be called before displaying the total number of keys
		// If displaying is not needed, then the body of this method would be a single line of code: 
		// return csidc.tree.inOrderTraversal(this.tree.getRoot());
		NodeList list = csidc.tree.inOrderTraversal(csidc.tree.getRoot());
		
		// Optional method calls for display clarity
		csidc.tree.displayTotalKeys();
		csidc.tree.print(csidc.tree.getRoot());
		return list;	
	}
	
	/**
	 * Searches the tree for an entry with a specified key and returns the value if it is found
	 * @param csidc
	 * @param key
	 * @return
	 * @throws KeyNotFoundException 
	 */
	public String getValue(CleverSIDC csidc, int key) throws KeyNotFoundException {
		System.out.println("Searching for value of node with key: " + key);
		try { 
			Node found = csidc.tree.search(key);
			if (found.getKey() > 0) {
				System.out.println("Key " + key + "'s value is: " + found.getValue());
				return found.getValue();
				
			} else {
				throw new KeyNotFoundException("Operation failed. Key does not exist: ");
				
			}
		} catch (KeyNotFoundException knfe) {
			System.out.println(knfe.getMessage() + key);
		}
		return "Operation failed. No value found.";
	}
	
	/**
	 * Return the successor of a given key
	 * @param csidc
	 * @param key
	 * @return
	 * @throws EmptyTreeException
	 * @throws KeyNotFoundException
	 */
	public int nextKey(CleverSIDC csidc, int key) throws KeyNotFoundException {
		System.out.println("Searching for successor of node with key: " + key);
		// Search for the key within the tree
		Node found = csidc.tree.search(key);
		if (found.getKey() > 0) {
			// Check if it is the tail node
			if (found.getNext() != null) {
				System.out.println(key + "'s successor is " + found.getNext().getKey());
				return found.getNext().getKey();
				
			} else {
				throw new KeyNotFoundException("Operation failed. Successor for key " + key + " does not exist. ");
				
			}			
		}	
		return -1;	
	}
	
	/**
	 * Return the predecessor of a given key
	 * @param csidc
	 * @param key
	 * @return
	 * @throws KeyNotFoundException
	 */
	public int prevKey(CleverSIDC csidc, int key) throws KeyNotFoundException {
		System.out.println("Searching for predecessor of node with key: " + key);
		// Search for the key within the tree
		Node found = csidc.tree.search(key);
		if (found.getKey() > 0) {
			// Check if it is a head node
			if (found.getPrevious() != null) {
				System.out.println(key + "'s predecessor is " + found.getPrevious().getKey());
				return found.getPrevious().getKey();
				
			} else {
				throw new KeyNotFoundException("Operation failed. Predecessor of " + key + " does not exist. ");
				
			}
			
		}
		return -1;	
	}
	
	/**
	 * Returns the number of keys between 2 keys
	 * @param key1
	 * @param key2
	 * @return
	 * @throws KeyNotFoundException
	 * @throws InvalidKeyException
	 * @throws EmptyTreeException
	 */
	public int rangeKey(int key1, int key2) throws KeyNotFoundException, InvalidKeyException, EmptyTreeException {	
			return tree.keysBetween(key1, key2);	
	}	

}