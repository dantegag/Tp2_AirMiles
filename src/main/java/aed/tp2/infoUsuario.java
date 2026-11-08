package aed.tp2;


public class infoUsuario implements Comparable<infoUsuario>{
    int id;
    double totales, disponibles, gastadas;
    infoUsuario socio;
    boolean esExclusivo;

    public infoUsuario (int id, double bonoInicial) {
        this.id = id;
        this.gastadas = 0;
        this.totales = bonoInicial;      
        this.disponibles = bonoInicial;
        this.socio = null;               
        this.esExclusivo = true;
    }

    public int masDisponibles(infoUsuario otro){ //Devuelve positivo si this tiene más millas disponibles, negativo si no y 0 si tienen las mismas
        int res = 0;
        if (this.disponibles > otro.disponibles){
            res = 1;
        } else if(otro.disponibles > this.disponibles){
            res = -1;
        }
        return res;
    }
   public String categoria(){ //Devuelve la categoria de un usuairo en base a totales
    String res = "Bronce"; 
    if (10000 <= this.totales && this.totales < 50000){
        res = "Plata";
    } else if (50000 <= this.totales && this.totales < 1000000){
        res = "Oro";
    } else if (this.totales >= 1000000){
        res = "Platino";
    }
    return res;
   }

   public double factorCategoria(){ //Devuelve directamente el factor de categoría del usuario
    double res = 1.0;
    if (this.categoria().equals("Plata")){
        res = 1.5;
    } else if (this.categoria().equals("Oro")){
        res = 2.0;
    } else if (this.categoria().equals("Platino")){
        res = 0.0;
    }
    return res;
   }

   @Override 
   public int compareTo(infoUsuario otro){ //Compara a los usuarios por id
    int res = 0;
    if (this.id > otro.id){
        res = 1;
    } else if (otro.id > this.id){
        res = -1;
    }
    return res;
   }
}

