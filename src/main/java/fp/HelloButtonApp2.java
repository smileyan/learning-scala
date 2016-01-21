package fp;

import java.awt.*;
import java.awt.event.*;

class HelloButtonApp2 {
  private final Button button = new Button();

  public HelloButtonApp2() {
    button.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        System.out.println("Hello There: event received: " + e);
      }
    });
  }
}

// public interface Function1Void<A> {
// void apply(A a);
//}

//class FunctionalHelloButtonApp {
//  private final Button button = new Button();
//
//  public FunctionalHelloButtonApp() {
//    button.addActionListener(new Function1Void<ActionEvent>() {
//      public void apply(ActionEvent e) {
//        System.out.println("Hello There: event received: " + e);
//      }
//    });
//  }
//}
