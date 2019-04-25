import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private Node<Item> first, last;
    private int size;

    // construct an empty deque
    public Deque() {
    }

    // is the deque empty?
    public boolean isEmpty() {
        return this.first == null && this.last == null;
    }

    // return the number of items on the deque
    public int size() {
        return this.size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null)
            throw new IllegalArgumentException();

        Node<Item> newItem = new Node<>();
        newItem.item = item;

        if (isEmpty()) {
            first = newItem;
            last = newItem;
        }
        else {
            Node<Item> oldFirst = first;
            oldFirst.previous = newItem;

            first = newItem;
            first.next = oldFirst;
        }
        this.size++;
    }

    // add the item to the end
    public void addLast(Item item) {
        if (item == null)
            throw new IllegalArgumentException();

        Node<Item> newItem = new Node<>();
        newItem.item = item;

        if (isEmpty()) {
            first = newItem;
            last = newItem;
        }
        else {
            last.next = newItem;
            newItem.previous = last;

            last = newItem;
        }
        this.size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty())
            throw new NoSuchElementException();

        Item item = first.item;

        // if the last and first are equals means that there is just one element
        if (last.equals(first)) {
            last = null;
            first = null;
        }
        else {
            first = first.next;
        }

        this.size--;
        return item;
    }

    // remove and return the item from the end
    public Item removeLast() {
        if (isEmpty())
            throw new NoSuchElementException();
        Item item = last.item;

        // if the last and first are equals means that there is just one element
        if (last.equals(first)) {
            last = null;
            first = null;
        }
        else {
            Node<Item> previous = last.previous;

            // remove the ref from previous to the last
            previous.next = null;
            // and the ref from the last to the previous
            last.previous = null;
            // now the last is the previous
            last = previous;
        }


        this.size--;

        return item;
    }

    // return an iterator over items in order from front to end
    public Iterator<Item> iterator() {
        return new DequeIterator(first);
    }

    private class Node<T> {
        public T item;
        public Node<T> next;
        public Node<T> previous;
    }

    private class DequeIterator implements Iterator<Item> {

        private Node<Item> current;

        public DequeIterator(Node<Item> first) {
            this.current = first;
        }

        @Override

        public boolean hasNext() {
            return this.current != null;
        }

        @Override
        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException();

            Item item = this.current.item;
            this.current = this.current.next;
            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // unit testing (optional)
    public static void main(String[] args) {
        Deque<String> deque = new Deque<>();

        deque.addFirst("Ramos");
        deque.addFirst("Luis");
        deque.addFirst("Matheus");
        deque.addLast("de Souza");

        for (String item : deque) {
            System.out.println(item);
        }

        System.out.println(">>> " + deque.removeFirst());

        for (String item : deque) {
            System.out.println(item);
        }

        for (int i = 0; i < 3; i++) {
            System.out.println(">>> first " + deque.removeFirst());

            for (String item : deque) {
                System.out.println(item);
            }
        }
    }

}
