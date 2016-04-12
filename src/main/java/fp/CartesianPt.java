package fp;

/**
 * Created by huay on 11/04/2016.
 */
public class CartesianPt extends Point{
    int x;
    int y;

    CartesianPt(int _x, int _y) {
        x = _x;
        y = _y;
    }
    int distanceToO() {
        return (int) Math.sqrt(x*x + y * y);
    }
}
