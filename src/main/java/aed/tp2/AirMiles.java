package aed.tp2;
public record Id(int value) {}

public class AirMiles implements SistemaAirMiles {
    private Id elMasAcumulador;
    private Id elMasCanjeador;
    private ABB<Handle<InfoUsuario>> usuarios;
    
    class InfoUsuario {
    int id;
    double bono, voladas, canjeadas, enviadas, recibidas;
    double totales, disponibles;
    int handleDisponibles, handleTotales;
    InfoUsuario socio;
    boolean esExclusivo;

    InfoUsuario(int id, double bonoInicial) {
        this.id = id;
        this.bono = bonoInicial;
        this.totales = bonoInicial;      
        this.disponibles = bonoInicial;  
        this.handleDisponibles = -1;    
        this.handleTotales = -1;
        this.socio = null;               
        this.esExclusivo = true;
    }
}

    public AirMiles() {
            usuarios = new ABB<Handle<InfoUsuario>>;                 // adentro: raiz = null
            promos = new Trie();                  
            heapDisponibles = new MaxHeap();
            heapTotales = new MaxHeap();
            masCanjeador = null;                  // no hay usuarios todavía
            promos.definir("NoPromo", new Promo(1.0, false, 0));
            }

    public void registrarUsuario(int id) {
        usuarios.insertar(id);
    }

    public void registrarUsuarioConReferidor(int id, int ref) {
        throw new UnsupportedOperationException("Sin implementar");
    }

    public void acumularMillas(int id, double distancia, String nombrePromo) {
        throw new UnsupportedOperationException("Sin implementar");
    }

    public void canjearMillas(int id, double cantidad) {
        throw new UnsupportedOperationException("Sin implementar");
    }

    public void transferirMillas(int origen, int destino, double cantidad) {
        throw new UnsupportedOperationException("Sin implementar");
    }

    public double consultarSaldo(int id) {
        throw new UnsupportedOperationException("Sin implementar");
    }

    public String consultarCategoria(int id) {
       throw new UnsupportedOperationException("Sin implementar");
    }

    public int[] rankingMillas(int n) {
        throw new UnsupportedOperationException("Sin implementar");
    }

    public void iniciarPromocion(String nombre, double factor) {
        throw new UnsupportedOperationException("Sin implementar");
    }

    public void iniciarPromocionConTope(String nombre, double factor, double tope) {
        throw new UnsupportedOperationException("Sin implementar");
    }

    public void finalizarPromocion(String nombre) {
        throw new UnsupportedOperationException("Sin implementar");
    }

    public int elMasAcumulador() {
        throw new UnsupportedOperationException("Sin implementar");
    }

    public int elMasCanjeador() {
        throw new UnsupportedOperationException("Sin implementar");
    }

    public int[][] paresTransferidoresExclusivos() {
        throw new UnsupportedOperationException("Sin implementar");
    }
}
