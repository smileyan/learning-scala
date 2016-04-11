package fp;

/**
 * Created by huay on 11/04/2016.
 */
public class MyClass {
//    public String toString() {
//        return "new " + getClass().getName() + "()";
//    }

    String x;

    public String toString() {
        return "new " + getClass().getName() + "(" + x + ")";
    }
}
    class Main {
        public static void main(String args []) {
            MyClass y = new MyClass();
            System.out.println(",....");
        }
    }

