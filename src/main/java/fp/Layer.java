package fp;

/**
 * Created by huay on 11/04/2016.
 */
abstract class Layer {

}

class Base extends Layer {
    Object o;
    Base(Object _o) {
        o = _o;
    }
}

class Slice extends Layer {
    Layer l;
    Slice(Layer _l) {
        l = _l;
    }
}
