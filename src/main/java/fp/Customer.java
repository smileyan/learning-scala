package fp;

import java.util.List;
import java.util.ArrayList;

public class Customer {
  // No setter method
  private final List<Order> orders;
  public List<Order> getOrders() { return orders; }
  public Customer(){
    orders = new ArrayList();
    orders.add(new Order());
  }
}

class Order {

}