package fp;

/**
 * Created by huay on 12/04/2016.
 */
abstract class ShishD {
    OnlyOnionsV ooFn = new OnlyOnionsV();
    IsVegetarianV ivFn = new IsVegetarianV();
    abstract boolean onlyOnions();
    abstract boolean isVegetarian();
}

class Skewer extends ShishD {
    @Override
    boolean onlyOnions() {
        return ooFn.forSkewer();
    }

    @Override
    boolean isVegetarian() {
        return ivFn.forSkewer();
    }
}

class Onion extends ShishD {
    ShishD s;
    Onion(ShishD _s) {
        s = _s;
    }

    @Override
    boolean onlyOnions() {
        return ooFn.forOnion(s);
    }

    @Override
    boolean isVegetarian() {
        return ivFn.forOnion(s);
    }
}

class Lamb extends  ShishD {
    ShishD s;
    Lamb (ShishD _s) {
        s = _s;
    }

    @Override
    boolean onlyOnions() {
        return ooFn.forLamb(s);
    }

    @Override
    boolean isVegetarian() {
        return ivFn.forLamb(s);
    }
}

class Tomato extends ShishD{
    ShishD s;
    Tomato(ShishD _s){
        s = _s;
    }

    @Override
    boolean onlyOnions() {
        return ooFn.forTomato(s);
    }

    @Override
    boolean isVegetarian() {
        return ivFn.forTomato(s);
    }
}

class IsVegetarianV {
    boolean forSkewer() {
        return true;
    }

    boolean forOnion(ShishD s) {
        return s.isVegetarian();
    }

    boolean forLamb(ShishD s) {
        return false;
    }

    boolean forTomato(ShishD s) {
        return s.isVegetarian();
    }
}

class OnlyOnionsV {
    boolean forSkewer() {
        return true;
    }

    boolean forOnion(ShishD s) {
        return s.onlyOnions();
    }

    boolean forLamb(ShishD s) {
        return false;
    }

    boolean forTomato(ShishD s) {
        return false;
    }
}

class Main2 {
    public static void main(String[] args){
        ShishD s = new Skewer();
        ShishD s1 = new Onion(
                     new Skewer());
        ShishD s2 = new Onion(
                     new Lamb(
                             new Onion(
                                     new Skewer())));
        boolean o = new Onion(new Onion(new Skewer())).onlyOnions();
    }
}