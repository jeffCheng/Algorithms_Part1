import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * 
 * @author https://coursera.cs.princeton.edu/algs4/assignments/queues/specification.php
 * @param <Item>
 */
public class Deque<Item> implements Iterable<Item> {

	private Node first, last;
	private int n;

	private class Node {
		Item item;
		Node next;
		Node prev;
	}

	// construct an empty deque
	public Deque() {
		first = last = null;
		n = 0;
	}

	// is the deque empty?
	public boolean isEmpty() {
		return size() == 0;
	}

	// return the number of items on the deque
	public int size() {
		return n;
	}

	// add the item to the front
	public void addFirst(Item item) {
		if (item == null)
			throw new IllegalArgumentException();
		Node node = new Node();
		node.item = item;
		if (isEmpty()) {
			node.next = null;
			node.prev = null;
			first = node;
			last = node;
		} else {
			node.next = first;
			node.prev = null;
			first.prev = node;
			first = node;
		}
		n++;
	}

	// add the item to the back
	public void addLast(Item item) {
		if (item == null)
			throw new IllegalArgumentException();
		Node node = new Node();
		node.item = item;
		if (isEmpty()) {
			node.next = null;
			node.prev = null;
			first = node;
			last = node;
		} else {
			node.prev = last;
			node.next = null;
			last.next = node;
			last = node;
		}
		n++;
	}

	// remove and return the item from the front
	public Item removeFirst() {
		if (first == null && n == 0)
			throw new NoSuchElementException();
		Item item = first.item;
		first = first.next;
		if (first == null)
			last = null;
		else
			first.prev = null;
		n--;
		return item;
	}

	// remove and return the item from the back
	public Item removeLast() {
		if (last == null && n == 0)
			throw new NoSuchElementException();
		Item item = last.item;
		last = last.prev;
		if (last == null)
			first = null;
		else
			last.next = null;
		n--;
		return item;
	}

	// return an iterator over items in order from front to back
	public Iterator<Item> iterator() {
		return new DequeIterator();
	}

	private class DequeIterator implements Iterator<Item> {
		Node curr = first;

		@Override
		public boolean hasNext() {
			return curr != null;
		}

		@Override
		public Item next() {
			if (!hasNext())
				throw new NoSuchElementException();
			Item item = curr.item;
			curr = curr.next;
			return item;
		}

		public void remove() {
			throw new java.lang.UnsupportedOperationException();
		}
	}

	// unit testing (required)
	public static void main(String[] args) {
		Deque<String> queue = new Deque<String>();
		queue.addFirst("d");
		queue.addFirst("c");
		queue.removeLast();
		queue.addFirst("a");
		for (String s : queue)
			System.out.println(s);
	}
}
