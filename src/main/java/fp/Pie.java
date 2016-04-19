package fp;

/**
 * Created by huay on 19/04/2016.
 */
abstract class PieD {
    RemAV raFn = new RemAV();
    abstract PieD remA();
}

class Bot extends PieD {
    @Override
    PieD remA() {
        return raFn.forBot();
    }
}

class Top extends PieD {
    Object t;
    PieD r;
    Top(Object _t, PieD _r) {
        t = _t;
        r = _r;
    }

    @Override
    PieD remA() {
        return raFn.forTop(t,r);
    }
}

abstract class FishD {}

class Anchovyv extends FishD {}

class Salmon   extends FishD {}

class Tuna     extends FishD {}

