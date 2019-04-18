import java.util.Iterator;

public class Deque<Item> {

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
        Node<Item> newItem = new Node<>();
        newItem.item = item;

        if (isEmpty()) {
            first = newItem;
            last = newItem;
        }
        else {
            Node<Item> oldFirst = first;

            first = newItem;

            if (oldFirst != null) {
                oldFirst.next = first;
            }
        }


    }

    // add the item to the end
    public void addLast(Item item) {

    }

    // remove and return the item from the front
    public Item removeFirst() {

    }

    // remove and return the item from the end
    public Item removeLast() {

    }

    // return an iterator over items in order from front to end
    public Iterator<Item> iterator() {

    }

    private class Node<T> {
        public T item;
        public Node<T> next;
    }

    // unit testing (optional)
    public static void main(String[] args) {

    }

}
