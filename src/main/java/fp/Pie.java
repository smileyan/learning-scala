package fp;

/**
 * Created by huay on 19/04/2016.
 */
abstract class PieD {
    RemAV raFn = new RemAV();
    RemFishV rfFn = new RemFishV();
    abstract PieD remA();
    abstract PieD remFish(FishD f);
}

class Bot extends PieD {
    @Override
    PieD remA() {
        return raFn.forBot();
    }

    @Override
    PieD remFish(FishD f) {
        return rfFn.forBot(f);
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

    @Override
    PieD remFish(FishD f) {
        return rfFn.forTop(t,r,f);
    }
}

abstract class FishD {}

class Anchovyv extends FishD {
    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Anchovyv);
    }
}

class Salmon   extends FishD {
    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Salmon);
    }
}

class Tuna     extends FishD {
    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Tuna);
    }
}

class RemFishV {
    PieD forBot(FishD f) {
        return new Bot();
    }

    PieD forTop(Object t, PieD r, FishD f) {
        if(f.equals(t))
            return r.remFish(f);
        else
            return new Top(t, r.remFish(f));
    }
}