package fp;

/**
 * Created by huay on 14/04/2016.
 */
abstract class Pizza {

}

class Crust extends Pizza {

}

class Cheese extends Pizza {
    Pizza p;
    Cheese(Pizza _p){
        p = _p;
    }
}

class Olive extends Pizza {
    Pizza p;
    Olive(Pizza _p) {
        p = _p;
    }
}

class Anchovy extends Pizza {
    Pizza p;
    Anchovy(Pizza _p) {
        p = _p;
    }
}

class Sausage extends Pizza {
    Pizza p;
    Sausage(Pizza _p) {
        p = _p;
    }
}