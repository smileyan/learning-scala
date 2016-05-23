package ds;

/**
 * Created by huay on 23/05/2016.
 */
public class Stack<T extends Comparable<T>> extends Vector<T>{

    public Stack (Class<T> tClass) {
        super(tClass);
    }

    public void push(T e) {
        insert(size(), e);
    }

    public T pop() {
        return remove(size() - 1);
    }

    public T top() {
        return this.get(size() - 1);
    }

}
