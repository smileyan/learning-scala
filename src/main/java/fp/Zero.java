package fp;

/**
 * Created by huay on 11/04/2016.
 */
public class Zero extends Num{
}

class OneMoreThan extends Num {
    Num predecessor;
    OneMoreThan(Num _p) {
        predecessor = _p;
    }
}

class Main1{
    public static void main(String[] args) {
        new OneMoreThan(new Zero());
    }
}