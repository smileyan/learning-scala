package fp;

/**
 * Created by huay on 11/04/2016.
 */
public class CartesianPt extends Point{

    CartesianPt(int _x, int _y) {
        super(_x,_y);
    }
    int distanceToO() {
        return (int) Math.sqrt(x*x + y * y);
    }
}
