package aed.tp2;

import aed.tp2.AirMiles.InfoUsuario;

public class infoUsuario {
    private int id;
    private double bono, voladas, canjeadas, enviadas, recibidas;
    private double totales, disponibles;
    private int handleDisponibles, handleTotales;
    private InfoUsuario socio;
    private boolean esExclusivo;

    public void InfoUsuario(int id, double bonoInicial) {
        this.id = id;
        this.bono = bonoInicial;
        this.voladas = 0;
        this.canjeadas = 0;
        this.enviadas = 0;
        this.recibidas = 0;
        this.totales = bonoInicial;      
        this.disponibles = bonoInicial;  
        this.handleDisponibles = -1;    
        this.handleTotales = -1;
        this.socio = null;               
        this.esExclusivo = true;
    }

}

