package aed.tp2;

import java.util.ArrayList;
public class AirMiles implements SistemaAirMiles {
    private infoUsuario masAcumulador;
    private infoUsuario masCanjeador;
    private ABB<Handle<infoUsuario>> usuarios;
    private MaxHeap<infoUsuario> heapDisponibles;
    private Trie<promo> promos;
    
    public AirMiles() { //Constructor del sistema
            usuarios = new ABB<Handle<infoUsuario>>(); //Creo un nuevo ABB vacío de handles
            promos = new Trie<promo>();  //Creo un Trie vacío para las promos       
            heapDisponibles = new MaxHeap<infoUsuario>((u1, u2) -> u1.masDisponibles(u2)); //Le paso un comparator al MaxHeap de usuarios para que esté ordenado por millasDisponibles
            masCanjeador = null; //Sin usuarios
            masAcumulador = null;
            promos.definir("NoPromo", new promo("noPromo", 1.0, 0)); //Creo la noPromo
        }

    private Handle<infoUsuario> buscarHandle(int id){ //Método auxiliar para recuperar el handle de un usuario usando su id
        infoUsuario temp = new infoUsuario(id, 0);
        Handle<infoUsuario> hBusqueda = heapDisponibles.crearHandleBusqueda(temp);
        return usuarios.obtener(hBusqueda);
    } 

    public void registrarUsuario(int id) {
        infoUsuario nuevo = new infoUsuario(id, 0); //Creo la infoUsuario del nuevo usuario (sin bono)
        Handle<infoUsuario> h = heapDisponibles.encolar(nuevo); //Creo el handle del nuevo usuario y lo guardo en el heap
        usuarios.insertar(h); //Guardo el handle en el ABB
        if(masAcumulador == null){ //Si es el primer usuario, es automáticamente el mas acumulador
            masAcumulador = nuevo;
        }
    }

    public void registrarUsuarioConReferidor(int id, int ref) {        
        infoUsuario nuevo = new infoUsuario(id, 500); //Creo la infoUsuario del nuevo usuario con su bono de referido
        Handle<infoUsuario> h = heapDisponibles.encolar(nuevo); 
        usuarios.insertar(h);
        Handle<infoUsuario> hRef = buscarHandle(ref); //Busco el handle del referidor
        infoUsuario uRef = hRef.valor(); //Accedo al usuario del handle
        uRef.disponibles += 500; //Sumo el bono de 500 a sus millas disponibles
        uRef.totales += 500; //Sumo el bono a sus millas totales
        hRef.modificar(uRef); //Actualizo la posición del referidor en el heap teniendo en cuenta sus nuevas millas
        if (masAcumulador == null || uRef.totales > masAcumulador.totales){ //Reviso si el referidor superó al más acumulador con el bono
            masAcumulador = uRef;
        }
    }

    public void acumularMillas(int id, double distancia, String nombrePromo) {
        Handle<infoUsuario> h = buscarHandle(id); //Primero busco el handle del id proporcionado
        infoUsuario u = h.valor(); //Accedo al valor del handle, ahora puedo interactuar con el usuario per sé
        promo p = promos.obtener(nombrePromo); //Busco la promo de nombrePromo
        if (p == null){
            p = promos.obtener("NoPromo"); 
        }
        double millasGanadas = distancia * p.factor * u.factorCategoria(); //Calculo las millas ganadas con la distancia y los factores de categoría y promo
        if (p != null && p.tieneTope){ //Voy a actualizar el tope de la promo en caso de que lo tenga y ésta exista
            p.tope -= millasGanadas;
            if (p.tope <= 0){ //Reviso si le queda tope a la promo
                promos.borrar(p.nombre); //Si se agotó el tope, borro la promo
            }
        }
        u.disponibles += millasGanadas; //Sumo las millas ganadas a las disponibles y las totales
        u.totales += millasGanadas;
        h.modificar(u); //Actualizo la posición del usuario en el heap según sus nuevas millas disponibles
        if (u.totales > masAcumulador.totales){ //Reviso si el usuario superó al actual más acumulador, si lo hizo lo actualizo.
            masAcumulador = u;
        }
    }

    public void canjearMillas(int id, double cantidad) {
        Handle<infoUsuario> h = buscarHandle(id); //Busco el handle del usuario
        infoUsuario u = h.valor(); //Accedo al usuario al que apunta el handle
        u.disponibles -= cantidad; //Actualizo las millas disponibles del usuario luego del canje
        u.gastadas += cantidad; //Actualizo las millas gastadas del usuario
        h.modificar(u); //Actualizo la posición del usuario en el heap
        if (masCanjeador == null || u.gastadas > masCanjeador.gastadas){ //Reviso si ya hay un más canjeador o si éste usuario lo superó en millas gastadas para actualizarlo
            masCanjeador = u;
        }
    }

    public void transferirMillas(int origen, int destino, double cantidad) {
        Handle<infoUsuario> hOrigen = buscarHandle(origen); //Busco los handle de los id de los usuarios origen y destino
        Handle<infoUsuario> hDestino = buscarHandle(destino);
        infoUsuario uOrigen = hOrigen.valor(); //Accedo a los usuarios a los que ambos handle apuntan
        infoUsuario uDestino = hDestino.valor();
        uOrigen.disponibles -= cantidad; //Origen pierde cantidad de disponibles y la gana de gastadas
        uOrigen.gastadas += cantidad;
        uDestino.disponibles += cantidad; //Origen gana cantidad de disponibles
        hOrigen.modificar(uOrigen); //Actualizo la posición de ambos en el heap
        hDestino.modificar(uDestino);
        if(masCanjeador == null || uOrigen.gastadas >= masCanjeador.gastadas){ //Reviso si uOrigen superó al más canjeador, en cuyo caso lo actualizo
            masCanjeador = uOrigen;
        }
        if (uOrigen.socio == null){ //Actualizo el estado de exclusividad de destino y origen, en caso de que no tuvieran socio o tuvieran un socio diferente
            uOrigen.socio = uDestino;
        } else if (uOrigen.socio != uDestino){
            uOrigen.esExclusivo = false;
        }
        if (uDestino.socio == null){
            uDestino.socio = uOrigen;
        } else if (uDestino.socio != uOrigen){
            uDestino.esExclusivo = false;
        }
    }

    public double consultarSaldo(int id) {
        return buscarHandle(id).valor().disponibles; //Busco el handle del id y devuelvo sus disponibles
    }

    public String consultarCategoria(int id) {
       return buscarHandle(id).valor().categoria(); //busco el handle del id y uso la función categoría
    }

    public int[] rankingMillas(int n) {
        int k = n; //Guardo a priori n como cantidad de usuarios a devolver
        if (k > heapDisponibles.cardinal()){ //Reviso que la cantidad no sea mayor a la cantidad total de usuarios en el sistema
            k = heapDisponibles.cardinal(); //Si me piden más usuarios de los que tengo, devuelvo todos
        }
        int[] res = new int[k]; //Creo la estructura que debo devolver
        ArrayList<Handle<infoUsuario>> sacados = new ArrayList<Handle<infoUsuario>>(); //Creo una estructura que me permita guardar los usuarios que voy a desencolar del heap para poder volverlos a meter luego
        for(int i = 0; i <= k - 1; i++){ //Guardo todos los usuarios que necesito en mis 2 estructuras auxiliares
            Handle<infoUsuario> h = heapDisponibles.desencolar();
            res[i] = h.valor().id;
            sacados.add(h);
        }
        for(int i = 0; i <= k - 1; i++){ //Vuevlo a meter en el heap a todos los usuarios que saqué
            heapDisponibles.reEncolar(sacados.get(i));
        }
        return res;
    }

    public void iniciarPromocion(String nombre, double factor) {
        promos.definir(nombre, new promo(nombre, factor, 0)); //Defino la nueva promo, como el tope es 0 automáticamente se asigna tieneTope como false
    }

    public void iniciarPromocionConTope(String nombre, double factor, double tope) {
        promos.definir(nombre, new promo(nombre, factor, tope)); //Defino la nueva promo, al tener tope automáticamente se asigna True a tieneTope
    }

    public void finalizarPromocion(String nombre) {
        promos.borrar(nombre); //Se borra la promo de nombre proporcionado
    }

    public int elMasAcumulador() {
        return masAcumulador.id; //Devuelvo el id del infoUsuario que guardo como atributo del sistema, masAcumulador
    }

    public int elMasCanjeador() {
        return masCanjeador.id; //Devuelve eel id del infoUsuario que guardo como atributo del sistema, masCanjeador
    }

    public int[][] paresTransferidoresExclusivos() {
        ArrayList<int[]> listaPares = new ArrayList<int[]>(); //Creo la estructura en la que guardaré los pares exclusivos
        ABB<Handle<infoUsuario>>.ABB_Iterador it = usuarios.iterador(); //Solicito el iterador de usuarios para recorrerlo por completo
        while(it.haySiguiente()){
            infoUsuario u = it.siguiente().valor(); //Guardo el usuario del handle actual del iterador
            if (u.socio != null && (u.esExclusivo && u.socio.esExclusivo) && u.socio.socio == u && u.id < u.socio.id){ //Aplico todas las condiciones para que dos usuarios sean trans exclusivos, la última previene que un par exclusivo se vuelva a sumar cuando revise el segundo usaurio de un par que ya agregué a la lista más adelante
                listaPares.add(new int[]{u.id, u.socio.id}); //Agrego el par exclusivo a la lista
            }
        }
        int[][] res = new int[listaPares.size()][2];
        for (int i = 0; i < listaPares.size(); i++){ //Agrego todos los pares a la matriz res para devolverla
            res[i] = listaPares.get(i);
        }
        return res;
    }
}
