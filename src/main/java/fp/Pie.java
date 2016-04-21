package fp;

/**
 * Created by huay on 19/04/2016.
 */
abstract class PieD {
//    RemAV raFn = new RemAV();
//    RemFishV rfFn = new RemFishV();
//    abstract PieD remA();
//    abstract PieD remFish(FishD f);
    RemV remFn = new RemV();
    abstract PieD rem(Object o);
}

class Bot extends PieD {
//    @Override
//    PieD remA() {
//        return raFn.forBot();
//    }
//
//    @Override
//    PieD remFish(FishD f) {
//        return rfFn.forBot(f);
//    }

    @Override
    PieD rem(Object o) {
        return remFn.forBot(o);
    }
}

class Top extends PieD {
    Object t;
    PieD r;
    Top(Object _t, PieD _r) {
        t = _t;
        r = _r;
    }

//    @Override
//    PieD remA() {
//        return raFn.forTop(t,r);
//    }
//
//    @Override
//    PieD remFish(FishD f) {
//        return rfFn.forTop(t,r,f);
//    }

    @Override
    PieD rem(Object o) {
        return remFn.forTop(t,r,o);
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

class RemV {
    PieD forBot(Object o) {
        return new Bot();
    }

    PieD forTop(Object t, PieD r, Object o) {
        if (o.equals(t))
            return r.rem(o);
        else
            return new Top(t,r.rem(o));
    }
}
//
//class RemIntV {
//    PieD forBot(Integer i) {
//        return new Bot();
//    }
//
//    PieD forTop(Object t, PieD r, Integer i) {
//        if (i.equals(t))
//            return r.remInt(i);
//        else
//            return new Top(t,r.remInt(i));
//    }
//}
//
//class RemFishV {
//    PieD forBot(FishD f) {
//        return new Bot();
//    }
//
//    PieD forTop(Object t, PieD r, FishD f) {
//        if(f.equals(t))
//            return r.remFish(f);
//        else
//            return new Top(t, r.remFish(f));
//    }
//}