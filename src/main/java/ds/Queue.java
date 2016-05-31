package ds;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by huay on 30/05/2016.
 */
public class Queue<T extends Comparable<T>> extends List<T> {

    public void enqueue(T e) {
        insertAsLast(e);
    }

    public T dequeue() {
        return remove(first());
    }

    public T front() {
        return first().data;
    }

    @Test
    public void test() {
        Queue<Integer> queue = new Queue<>();

        assertTrue(queue.empty());

        queue.enqueue(5);
        queue.enqueue(3);

        assertTrue(queue.dequeue() == 5);

        queue.enqueue(7);
        queue.enqueue(3);

        assertTrue(!queue.empty());

        queue.enqueue(11);

        assertTrue(queue.size() == 4);

        queue.enqueue(6);

        assertTrue(!queue.empty());

        queue.enqueue(7);

        assertTrue(queue.dequeue() == 3);
        assertTrue(queue.dequeue() == 7);
        assertTrue(queue.front() == 3);
        assertTrue(queue.size() == 4);

    }
}
