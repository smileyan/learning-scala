package ds;

public class ListModule {
  public static interface List<T> {
    public abstract T       head();
    public abstract List<T> tail();
    public abstract boolean isEmpty();
  }

  public static final class NonEmptyList<T> implements List<T> {
    public T       head()    { return _head; }
    public List<T> tail()    { return _tail; }
    public boolean isEmpty() { return false; }
  
    protected NonEmptyList(T head, List<T> tail) {
      this._head = head;
      this._tail = tail;
    }

    private final T       _head;
    private final List<T> _tail;

    @Override
    public boolean equals(Object other) {
      if (other == null || getClass() != other.getClass()){
        return false;
      }

      List<?> that = (List<?>) other;
      return head().equals(that.head()) && tail().equals(that.tail());
    }

    @Override
    public int hashCode() {
      return  37*(head().hashCode() + tail().hashCode());
    }

    @Override
    public String toString() {
      return "(" + head().toString() + "," + tail().toString() + ")";
    }
  }

  public static class EmptyListHasNoHead extends RuntimeException {}
  public static class EmptyListHasNoTail extends RuntimeException {}

}