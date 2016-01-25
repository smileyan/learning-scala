package ds;

public class ListModule {
  public interface List<T> {
      T       head();
      List<T> tail();
      boolean isEmpty();
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

  public static final List<? extends Object> EMPTY = new List<Object>() {

    public Object       head()    { throw new EmptyListHasNoHead(); }
    public List<Object> tail()    { throw new EmptyListHasNoTail(); }
    public boolean      isEmpty() { return  true; }

    @Override
    public String toString() { return "()"; }
  };

  @SuppressWarnings(value = "unckecked")
  public static <T> List<T> emptyList() {
    return (List<T>) EMPTY;
  }

  public static <T> List<T> list(T head, List<T> tail) {
    return new NonEmptyList<T>(head, tail);
  }
}