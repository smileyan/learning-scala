package ds;

/**
 * Created by huay on 23/05/2016.
 */
public class Stack<T extends Comparable<T>> extends Vector<T>{

    public Stack (Class<? extends T> tClass) {
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

    public static Stack<Character> convert(int n, int base) {
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

    public static boolean paren (Character[] exp, int lo, int hi) {
        Stack<Character> s = new Stack<>(Character.class);

        for (int i = lo; i <= hi; i++) {
            switch (exp[i]) {
                case '(': case '[':case '{': s.push(exp[i]); break;

                case ')': if ((s.empty()) || ('(' != s.pop())) return false; break;
                case ']': if ((s.empty()) || ('[' != s.pop())) return false; break;
                case '}': if ((s.empty()) || ('{' != s.pop())) return false; break;
                default: break;
            }
        }
        return s.empty();
    }

    public float evaluate (char[] s, char[] rpn) {
        Stack<Float> opnd = new Stack<>(Float.class);
        Stack<Character> optr = new Stack<>(Character.class);

        optr.push('\0');

        int i = 0;
        while (!optr.empty()) {
            if (isdigit(s[i])) {
                // readNumber(s[i], opnd);
                // append(rpn, opnd.top());
            } else {
                switch ( orderBetween(optr.top(), s[i])) {
                    case '<' :
                        break;
                    case '=' :
                        break;
                    case '>' : {

                    }
                    default :
                        break;
                }
            }
        }

        return 0;
    }

    private boolean isdigit(Character c) {
        return false;
    }



    private char orderBetween(Character a, char b) {
        return '=';
    }
}
