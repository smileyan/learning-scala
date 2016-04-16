package fp;

/**
 * Created by huay on 14/04/2016.
 */
abstract class Pizza {
    abstract Pizza remA();
    abstract Pizza topAwC();
    abstract Pizza subAbC();
}

class Crust extends Pizza {
    @Override
    Pizza remA() {
        return new Crust();
    }

    @Override
    Pizza topAwC() {
        return new Crust();
    }

    @Override
    Pizza subAbC() {
        return new Crust();
    }
}

class Cheese extends Pizza {
    Pizza p;
    Cheese(Pizza _p){
        p = _p;
    }

    @Override
    Pizza remA() {
        return new Cheese(p.remA());
    }

    @Override
    Pizza topAwC() {
        return new Cheese(p.topAwC());
    }

    @Override
    Pizza subAbC() {
        return new Cheese(p.subAbC());
    }
}

class Olive extends Pizza {
    Pizza p;
    Olive(Pizza _p) {
        p = _p;
    }

    @Override
    Pizza remA() {
        return new Olive(p.remA());
    }

    @Override
    Pizza topAwC() {
        return new Olive(p.topAwC());
    }

    @Override
    Pizza subAbC() {
        return new Olive(p.subAbC());
    }
}

class Anchovy extends Pizza {
    Pizza p;
    Anchovy(Pizza _p) {
        p = _p;
    }

    @Override
    Pizza remA() {
        return new Crust();
    }

    @Override
    Pizza topAwC() {
        return new Cheese(new Anchovy(p.topAwC()));
    }

    @Override
    Pizza subAbC() {
        return new Cheese(p.subAbC());
    }
}

class Sausage extends Pizza {
    Pizza p;
    Sausage(Pizza _p) {
        p = _p;
    }

    @Override
    Pizza remA() {
        return new Sausage(p.remA());
    }

    @Override
    Pizza topAwC() {
        return new Sausage(p.topAwC());
    }

    @Override
    Pizza subAbC() {
        return new Sausage(p.subAbC());
    }
}

class Spinach extends Pizza {
    Pizza p;

    Spinach(Pizza _p) {
        p = _p;
    }

    @Override
    Pizza remA() {
        return new Spinach(p.remA());
    }

    @Override
    Pizza topAwC() {
        return new Spinach(p.topAwC());
    }

    @Override
    Pizza subAbC() {
        return new Spinach(p.subAbC());
    }
}