package fp;

/**
 * Created by huay on 12/04/2016.
 */
public abstract class Kebab {
    abstract boolean isVeggie();
}

class Holder extends Kebab {
    Object o;
    Holder(Object _o){
        o = _o;
    }

    @Override
    boolean isVeggie() {
        return true;
    }
}

class Shallot extends Kebab {
    Kebab k;
    Shallot(Kebab _k) {
        k = _k;
    }

    @Override
    boolean isVeggie() {
        return k.isVeggie();
    }
}

class Shrimp extends Kebab {
    Kebab k;
    Shrimp(Kebab _k) {
        k = _k;
    }

    @Override
    boolean isVeggie() {
        return false;
    }
}

class Radish extends Kebab{
    Kebab k;
    Radish(Kebab _k) {
        k = _k;
    }

    @Override
    boolean isVeggie() {
        return k.isVeggie();
    }
}

abstract class Rod {}
class Dagger extends Rod {}
class Sabre extends Rod {}
class Sword extends Rod {}

abstract class Plate {}
class Gold extends Plate {}
class Silver extends Plate {}
class Brass extends Plate {}
class Copper extends Plate {}
class Wood extends Plate {}