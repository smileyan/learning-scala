package ds;

import org.junit.Assert;
import org.junit.Test;

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

        Assert.assertTrue(queue.empty());

        queue.enqueue(5);
        queue.enqueue(3);

        Assert.assertTrue(queue.dequeue() == 5);

        queue.enqueue(7);
        queue.enqueue(3);

        Assert.assertTrue(!queue.empty());

        queue.enqueue(11);

        Assert.assertTrue(queue.size() == 4);

        queue.enqueue(6);

        Assert.assertTrue(!queue.empty());

        queue.enqueue(7);

        Assert.assertTrue(queue.dequeue() == 3);
        Assert.assertTrue(queue.dequeue() == 7);
        Assert.assertTrue(queue.front() == 3);
        Assert.assertTrue(queue.size() == 4);

    }
}
