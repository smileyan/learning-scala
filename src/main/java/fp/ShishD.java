package fp;

/**
 * Created by huay on 12/04/2016.
 */
abstract class ShishD {
    abstract boolean onlyOnions();
    abstract boolean isVegetarian();
}

class Skewer extends ShishD {
    @Override
    boolean onlyOnions() {
        return true;
    }

    @Override
    boolean isVegetarian() {
        return true;
    }
}

class Onion extends ShishD {
    ShishD s;
    Onion(ShishD _s) {
        s = _s;
    }

    @Override
    boolean onlyOnions() {
        return s.onlyOnions();
    }

    @Override
    boolean isVegetarian() {
        return s.isVegetarian();
    }
}

class Lamb extends  ShishD {
    ShishD s;
    Lamb (ShishD _s) {
        s = _s;
    }

    @Override
    boolean onlyOnions() {
        return false;
    }

    @Override
    boolean isVegetarian() {
        return false;
    }
}

class Tomato extends ShishD{
    ShishD s;
    Tomato(ShishD _s){
        s = _s;
    }

    @Override
    boolean onlyOnions() {
        return false;
    }

    @Override
    boolean isVegetarian() {
        return s.isVegetarian();
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