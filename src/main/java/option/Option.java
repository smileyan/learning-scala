package option;

/**
 * Created by huay on 24/01/2016.
 */
public abstract class Option<T> {
    public abstract boolean hasValue();

    public abstract T get();

    public T getOrElse(T alternative) {
        return hasValue() == true ? get() : alternative;
    }
}
