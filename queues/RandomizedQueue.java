import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private int size;
    private Item[] queue;

    // construct an empty randomized queue
    public RandomizedQueue() {
        queue = (Item[]) new Object[1];
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return this.size;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null)
            throw new IllegalArgumentException();

        if (queue.length == size) {
            resize(queue.length * 2);
        }
        queue[size++] = item;

    }

    // remove and return the item from the front
    public Item dequeue() {
        if (isEmpty())
            throw new NoSuchElementException();

        StdRandom.shuffle(queue, 0, size);

        Item item = queue[--size];
        queue[size] = null;

        if (size > 0 && size == queue.length / 4) {
            resize(queue.length / 2);
        }

        return item;
    }

    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < size; i++) {
            copy[i] = queue[i];
        }
        queue = copy;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty())
            throw new NoSuchElementException();

        int iRandom = StdRandom.uniform(size);
        return queue[iRandom];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RdandomizedIterator();
    }

    private class RdandomizedIterator implements Iterator<Item> {

        private int current;
        private final Item[] items;

        public RdandomizedIterator() {
            items = (Item[]) new Object[size];
            for (int i = 0; i < items.length; i++) {
                items[i] = queue[i];
            }
            StdRandom.shuffle(items);
        }

        @Override
        public boolean hasNext() {
            return current < items.length;
        }

        @Override
        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException();
            return items[current++];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // unit testing (optional)
    public static void main(String[] args) {
        RandomizedQueue<String> random = new RandomizedQueue<>();

        random.enqueue("Matheus");
        random.enqueue("Luis");
        random.enqueue("Ramos");
        random.enqueue("de Souza");

        for (int i = 0; i < 50; i++) {
            System.out.println(" >> " + random.sample());
        }

        for (String nome : random) {
            System.out.println(nome);
        }

        System.out.println(random.dequeue());
        System.out.println(random.dequeue());
        System.out.println(random.dequeue());
        System.out.println(random.dequeue());
    }

}
