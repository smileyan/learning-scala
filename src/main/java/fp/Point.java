package fp;

/**
 * Created by huay on 11/04/2016.
 */
abstract class Point {
    int x;
    int y;
    Point(int _x, int _y) {
        x = _x;
        y = _y;
    }
    boolean closerToO(Point p) {
        return distanceToO() <= p.distanceToO();
    }

    abstract int distanceToO();

}
