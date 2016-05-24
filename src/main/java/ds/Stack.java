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

    public Stack<Character> convert(int n, int base) {
        Stack<Character> s = new Stack<>(Character.class);
        Character digit[] = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};

        while ( n > 0 ) {
            int remainder = (n % base);
            Character c = digit[remainder];
            s.push( c );
            n /= base;
        }
        return s;
    }
}
