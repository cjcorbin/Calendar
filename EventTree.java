import java.util.*;

/*
 * EventTree
 * 
 * This class is simply a binary search tree (interval tree) with each node containing an Event. It
 * is the main mechanism behind determining event overlaps/collisions. It represents
 * the schedule for a particular day.
 * 
 * The idea for doing this: https://en.wikipedia.org/wiki/Interval_tree
 * 
 * The implementation guide (in C++): https://www.geeksforgeeks.org/interval-tree/
 * 
 * Code from a previous data structures course I took which implements a BST in Java was also used as
 * a reference.
 *
 * */

public class EventTree {
	
	/*
	 * ------private members (Node)------
	 * 
	 * (event, Event):
	 * represents the event for this node in the tree.
	 * 
	 * (maxMinutes, int):
	 * represents the max end time for an event for a subtree.
	 * 
	 * (leftChild, node):
	 * node's left child.
	 * 
	 * (rightNode, node):
	 * node's right child.
	 * 
	 * */
	
	private class Node
	{
		private Event event;
		private int maxMinutes;
		private Node leftChild;
		private Node rightChild;
	
		public Node(Event event)
		{
			this.event = event;
			this.maxMinutes = event.getEnd();
			this.leftChild = null;
			this.rightChild = null;
		}
	}
	
	//tree root
	public Node root;
	
	/*
	 * Default constructor for EventTree
	 * 
	 * */
	public EventTree()
	{
		this.root = null;
	}
	
	public void insert(Event newEvent)
	{
		Node newNode = new Node(newEvent);
		
		if(root == null)//empty tree
		{
			root = newNode;
		}
		else
		{
			//TODO call recursive insert
			insert(root, newEvent);
		}
	}
	
	/*
	 * Inserts node at spot in a BST manner.
	 * 
	 * */
	public Node insert(Node node, Event newEvent)
	{
		//it found the empty spot where it belongs
		if(node == null)
		{
			Node newNode = new Node(newEvent);
			node = newNode;
		}
		
		int start = node.event.getStart();
		
		//Go left
		if (newEvent.getStart() < start)
		{
			node.leftChild = insert(node.leftChild, newEvent);
		}
		
		//Go right
		else if (newEvent.getStart() > start)
		{
			node.rightChild = insert(node.rightChild, newEvent);
		}
		
		//Update the max for the subtree
		if (node.maxMinutes < newEvent.getEnd())
		{
			node.maxMinutes = newEvent.getEnd();
		}
		
		return node;
	}
	
	/*
	 * Method used for traversing the tree in order to determine overlap. If
	 * this method returns null, then an overlap was not found.
	 * */
	public Event overlapSearch(Node root, Event searchEvent)
	{
		if (root == null)
		{
			return null;
		}
		
		//Overlap between root and event
		if (root.event.isOverlap(searchEvent))
		{
			return root.event;
		}
		
		//If the left subtree's max is >= the event's start time, go left
		if (root.leftChild != null && (root.leftChild.maxMinutes >= searchEvent.getStart()))
		{
			return overlapSearch(root.leftChild, searchEvent);
		}
		
		//Otherwise, go right
		return overlapSearch(root.rightChild, searchEvent);
	}

}
