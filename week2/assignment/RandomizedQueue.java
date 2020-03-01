import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {

	private Item[] items;
	private int size; // Item in queue

	// construct an empty randomized queue
	public RandomizedQueue() {
		int defaultItemsSize = 2;
		items = (Item[]) new Object[defaultItemsSize];
		size = 0;
	}

	// is the randomized queue empty?
	public boolean isEmpty() {
		return size == 0;
	}

	// return the number of items on the randomized queue
	public int size() {
		return size;
	}

	// add the item
	public void enqueue(Item item) {
		if (item == null)
			throw new IllegalArgumentException();
		if (size == items.length)
			resizeItems(items.length * 2);
		items[size] = item;
		size++;
	}

	// Resize items array
	private void resizeItems(int length) {
		Item[] newItems = (Item[]) new Object[length];
		for (int i = 0; i < size; i++)
			newItems[i] = items[i];
		items = newItems;
	}

	// remove and return a random item
	public Item dequeue() {
		if (isEmpty())
			throw new NoSuchElementException();

		int index = StdRandom.uniform(size);
		Item item = items[index];

		// replace taken element with last one
		items[index] = items[size - 1];
		items[size - 1] = null;
		size--;

		if (size > 0 && (size == items.length / 4)) {
			resizeItems(items.length / 2);
		}

		return item;
	}

	// return a random item (but do not remove it)
	public Item sample() {
		if (isEmpty())
			throw new NoSuchElementException();
		int index = StdRandom.uniform(size);
		return items[index];
	}

	// return an independent iterator over items in random order
	public Iterator<Item> iterator() {
		return new RandomizedQueueIterator();
	}

	private class RandomizedQueueIterator implements Iterator<Item> {
		// Items in queue during iteration
		private Item[] itemsCopy;
		// RandomizedQueue size during iteration
		private int sizeCopy;

		// Init with coping items and size
		private RandomizedQueueIterator() {
			sizeCopy = size;
			itemsCopy = (Item[]) new Object[sizeCopy];

			for (int i = 0; i < sizeCopy; i++)
				itemsCopy[i] = items[i];
		}

		@Override
		public boolean hasNext() {
			return sizeCopy > 0;
		}

		@Override
		public Item next() {
			if (!hasNext())
				throw new NoSuchElementException();
			int idx = StdRandom.uniform(sizeCopy);
			Item item = itemsCopy[idx];

			// replace taken element with last one
			itemsCopy[idx] = itemsCopy[sizeCopy - 1];
			itemsCopy[sizeCopy - 1] = null;
			sizeCopy--;
			return item;
		}

	}

	// unit testing (required)
	public static void main(String[] args) {
		RandomizedQueue<String> queue = new RandomizedQueue<>();

		String text = "A";
		queue.enqueue(text);
		StdOut.println("enqueue() with: '" + text + "'");

		text = "B";
		queue.enqueue(text);
		StdOut.println("enqueue() with: '" + text + "'");

		text = "C";
		queue.enqueue(text);
		StdOut.println("enqueue() with: '" + text + "'");

		queue.dequeue();

		for (String item : queue) {
			StdOut.println("Iterate element: " + item);
		}
	}

}