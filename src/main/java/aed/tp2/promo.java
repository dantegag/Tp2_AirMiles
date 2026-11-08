package aed.tp2;

public class promo {
    boolean tieneTope;
    double factor;
    double tope;
    String nombre;

    public promo(String n, double f, double t){
        nombre = n;
        factor = f;
        if (t == 0){
            tieneTope = false;
            tope = 0;
        } else {
            tieneTope = true;
            tope = t;
        }
    }
}
