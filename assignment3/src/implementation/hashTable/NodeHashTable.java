package implementation.hashTable;

import implementation.Node;
import implementation.NodeList;


public class NodeHashTable {

	 private int currentSize;
	 private int maxSize;
	 private int[] keys;
	 private Node[] values;
	 private NodeList sequence;
	 
	 private int dupeCounter;
	 private int invalidCounter;
	
	 // Constructor of this class
	 public NodeHashTable(int capacity) {
	     this.currentSize = 0;
	     this.maxSize = capacity;
	     this.keys = new int[capacity];
	     this.values = new Node[capacity];
	     this.sequence = new NodeList();
	     this.dupeCounter = 0;
	     this.invalidCounter = 0;
	 }

	 // Function to get size of hash table
	 public int getSize() { 
		 return this.currentSize; 
		 
	 }

	 // Function to check if hash table is full
	 public boolean isFull() {
	     return this.currentSize == maxSize;
	 }

	 // Function to check if hash table is empty
	 public boolean isEmpty() { 
		 return this.currentSize == 0; 
	 }

	 public int insert(int key, String value) {
		 System.out.println(isFull());
		 System.out.println("Current size: " + this.currentSize + "\nMax size: " + this.maxSize);
		 System.out.println("Adding new node with key: " + key + " and value: " + value);
		 Node newNode = insert(key, new Node(key, value));
		 return newNode.getKey();
	 }
	 
	 public int delete(int key) {	 
		 return remove(key);
	 }
	 
	 // Function to get hash code of a given key
	 private int hash(int key) {
	     return key % this.maxSize;
	 }

	 // Function to insert key-value pair
	 private Node insert(int key, Node node) {
	     int temp = hash(key);
	     int i = temp;
	     
	     do {
	         if (keys[i] == 0) {        	 
	             keys[i] = key;
	             values[i] = node;
	             currentSize++;
	             
	         }	
	         
	         if (keys[i] == key) {
	             values[i] = node;
	             
	         }	        

	         i = (i+1) % maxSize;
	         
	     } while (i != temp);
	     
	     return node;
	 }
	
	 // Function to get value for a given key
	 public Node search(int key) {
		 Node dummyNode = new Node(0, "");
	     int i = hash(key);
	     while (keys[i] < 0) {
	         if (keys[i] == key) {
	             return values[i];
	             
	         } else {
	        	 i = (i+1) % maxSize;
	         }
	         
	     }
	     return dummyNode;
	 }
	
	 // Function to remove key and its value
	 private int remove(int key) {
	     // Find position key and delete
	     int i = hash(key);
	     while (key != keys[i]) {
	         i = (i+1) % maxSize;
	         
	     }
	     keys[i] = 0;
	     values[i] = null;
	
	     // rehash all keys
	     for (i = (i+1) % maxSize; keys[i] != 0; i = (i+1) % maxSize) {
	         int tempKey = keys[i];
	         Node tempValue = values[i];
	         keys[i] = 0;
	         values[i] = null;
	         currentSize--;
	         insert(tempKey, tempValue);
	     }
	     currentSize--;
	     
	     return key;
	 }
	 
	 public void mergeSort(NodeList list) {
		 NodeList unsortedList = new NodeList();
		 for (int i = 0; i < values.length; i++) {
			 unsortedList.insertLast(values[i]);
		 }
		 
		 if (list.size() > 1) {
			 NodeList firstHalf = new NodeList();
			 NodeList secondHalf = new NodeList();
			 
			 for (int i = 0; i < unsortedList.size()/2; i++) {
				 firstHalf.insertLast(unsortedList.remove(unsortedList.getHead()));
			 }
			 for (int i = 0; i < unsortedList.size(); i++) {
				 secondHalf.insertLast(unsortedList.remove(unsortedList.getHead()));
			 }
			 
			 mergeSort(firstHalf);
			 mergeSort(secondHalf);
			 this.sequence = merge(firstHalf, secondHalf);
		 }
	 }
	 
	 private NodeList merge(NodeList first, NodeList second) {
		 NodeList newList = new NodeList();	 
		 while (!first.isEmpty() && !second.isEmpty()) {
			 if (first.getHead().getKey() < second.getHead().getKey()) {
				 newList.insertLast(first.remove(first.getHead()));
				 
			 } else {
				 newList.insertLast(second.remove(second.getHead()));
				 
			 }
		 }
		 
		 while (!first.isEmpty()) {
			 newList.insertLast(first.remove(first.getHead()));
			 
		 }
		 
		 while (!first.isEmpty()) {
			 newList.insertLast(second.remove(second.getHead()));
			 
		 }
		 
		 return newList;
	 }
	
	 // Method 10
	 // Function to print HashTable
	 public void printHashTable() {
	     System.out.println("\nHash Table: ");
	     for (int i = 0; i < maxSize; i++) {
	         if (keys[i] != 0) {
	             System.out.println(keys[i] + " " + values[i]);
	             
	         }
	     }
	     System.out.println();
	 }
}
