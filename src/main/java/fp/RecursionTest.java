package fp;

/**
 * Created by huay on 22/01/2016.
 */

public class RecursionTest {
    static class Tree {
        public final Tree left;
        public final Tree right;
        public final int value;

        public Tree(Tree left, int value, Tree right) {
            this.left = left;
            this.value = value;
            this.right = right;
        }

        public final String toString() {
            String leftStr = left == null ? "^" : left.toString();
            String rightStr = right == null ? "^" : right.toString();
            return "(" + leftStr + "-" + value + "-" + rightStr + ")";
        }
    }
}
