package examples;

import java.util.List;

/**
 * Created by huay on 11/04/2016.
 */
public abstract class Shape {
    public abstract void draw(Canvas c);
}

class Canvas {
    public void draw(Shape s) {
        s.draw(this);
    }

    public void drawAll(List<? extends Shape> shapes) {
        for (Shape s: shapes) {
            s.draw(this);
        }
    }

}

class Circle extends Shape {

    private int x,y,radius;

    public void draw(Canvas s) {

    }
}