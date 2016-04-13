package fp;

/**
 * Created by huay on 11/04/2016.
 */
public class ManhattanPt extends Point{

    ManhattanPt(int _x, int _y) {
        super(_x,_y);
    }

    @Override
    int distanceToO() {
        return x + y;
    }
}
