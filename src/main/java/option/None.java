package option;


/**
 * Created by huay on 24/01/2016.
 */
public final class None<T> extends Option<T> {
    public static class NoneHasValue extends RuntimeException {}

    public None() {}

    public boolean hasValue() { return false; }

    public T get() { throw new NoneHasValue(); }

    @Override
    public String toString() { return "None"; }

    @Override
    public boolean equals(Object other) {
        if (other == null || other.getClass() != None.class) {
            return false;
        } else {
            return true;
        }
    }
    @Override
    public int hashCode() { return  -1; }
}
