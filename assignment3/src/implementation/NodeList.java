package implementation;



public class NodeList {
	
	private int size;
    private Node head;
    private Node tail;
 
    public NodeList() {}
    
    /**
     * Returns the number of elements in the list
     * @return
     */
    public int size() {
    	return this.size;
    }
    
    /**
     * 
     * @return
     */
    public boolean isEmpty() {
    	if (this.size == 0) {
    		return true;
    	}
    	return false;
    }
    
    /**
     * Returns the node at the front of the list
     * @return
     */
    public Node getHead() {
    	return this.head;
    }
    
    /**
     * Returns the node at the back of the list
     * @return
     */
    public Node getTail() {
    	return this.tail;
    }
    
    /**
     * **************************** BAD GET METHOD *********************************
     * @param index
     * @return
     */
    public Node get(int index) {
    	Node node = this.head;
    	for (int i = 0; i < index; i++) {
    		if (node.getNext() == null) {
    			return null;
    		}
    		node = node.getNext();
    	}
    	return node;
    }
    
    /**
     * Inserts a node at the back of the list and fixes the links between the previous and next nodes
     * @param node
     * @return
     */
    public Node insertLast(Node node) {   
        if (this.head == null) {  
        	// List is empty, so the newly inserted node is both head and tail
            this.head = node;
            this.tail = node;
            
            // Since it is the only node in the list, there are no previous or next yet
            this.head.setPrevious(null);   
            this.tail.setNext(null);
            
        } else {  
            // Insert the node to the end of the list
            this.tail.setNext(node);  
            // Set the previous node of the newly added node as the previous tail
            node.setPrevious(this.tail);  
            // Newly added node becomes the new tail
            this.tail = node;           
            this.tail.setNext(null);
        }  
        
        this.size++;
        return node;
    }    
    
    /**
     * Inserts a node at the front of the list and fixes the links between the previous and next nodes
     * @param node
     * @return
     */
    public Node insertFirst(Node node) {   
        if (this.head == null) {  
        	// List is empty, so the newly inserted node is both head and tail
            this.head = node;
            this.tail = node;
            
            // Since it is the only node in the list, there are no previous or next yet
            this.head.setPrevious(null);   
            this.tail.setNext(null);  
            
        } else {  
            // Insert the node to the beginning of the list
        	node.setNext(this.head);   
        	this.head.setPrevious(node); 
            // Newly added node becomes the new head
            this.head = node;
            
        }  
        
        this.size++;
        return node;
    }    

    /**
     * Removes a node and fixes the links between the previous and next nodes
     * @param node
     * @return
     */
    public Node remove(Node node) {
    	Node temp = node;
    	
    	node.getPrevious().setNext(node.getNext());
    	node.getNext().setPrevious(node.getPrevious());
    	
    	node.setPrevious(null);
    	node.setNext(null);
    	this.size--;
    	
    	return temp;
    }
    
 
}
	

