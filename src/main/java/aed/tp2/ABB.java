package aed.tp2;
// Diccionario ordenado por una clave int: cada nodo guarda (clave, valor) y se compara SOLO por la clave
public class ABB<T> {
    private Nodo raiz;
    private int cantidad;

    // Devuelve el nodo con esa clave, o (si no está) el nodo que sería su padre
    private Nodo buscador(int clave) {
        Nodo actual = raiz;
        Nodo anterior = null;

        while (actual != null) {
            if (clave == actual.clave) return actual;
            anterior = actual;
            if (clave > actual.clave) actual = actual.derecha;
            else actual = actual.izquierda;
        }
        return anterior;
    }

    private class Nodo {
        T valor;
        int clave;
        Nodo izquierda;
        Nodo derecha;
        Nodo padre;

        Nodo(T v, int c) {
            valor = v;
            clave = c;
        }
    }


    public ABB() {
        this.raiz=null;
        this.cantidad=0;
    }

    public int cardinal() {
        return cantidad;
    }

    public T minimo(){
        Nodo actual = raiz;
        while (actual.izquierda != null){
            actual = actual.izquierda;
        }
        return actual.valor;
    }

    public T maximo(){
        Nodo actual = raiz;
        while (actual.derecha != null){
            actual = actual.derecha;
        }
        return actual.valor;
    }

    public void insertar(int clave, T valor){
        if (cantidad == 0){
            raiz = new Nodo(valor, clave);
            cantidad += 1;
            return;
        }

        if (!pertenece(clave)){
            Nodo padre = buscador(clave);
            Nodo nuevo = new Nodo(valor, clave);
            nuevo.padre = padre;
            if (clave > padre.clave){
                padre.derecha = nuevo;
            } else {
                padre.izquierda = nuevo;
            }
            cantidad += 1;
        }
    }

    public T obtener(int clave){
        if (!pertenece(clave)){
            return null;
        }
        return buscador(clave).valor;
    }

    public boolean pertenece(int clave){
        return cantidad != 0 && buscador(clave).clave == clave;
    }


    private void reemplazar(Nodo viejo, Nodo nuevo) {
    if (viejo.padre == null) raiz = nuevo;
    else if (viejo == viejo.padre.izquierda) viejo.padre.izquierda = nuevo;
    else viejo.padre.derecha = nuevo;
    if (nuevo != null) nuevo.padre = viejo.padre;
}
    private Nodo sucesor(Nodo eliminado){
        
        
        if(eliminado.derecha!=null){
        Nodo actual=eliminado.derecha;
        while (actual.izquierda!=null){
            actual=actual.izquierda;

        }
        return actual;
        }
        Nodo actual=eliminado;
        Nodo padre=eliminado.padre;
        while(padre !=null && actual==padre.derecha){
            actual=actual.padre;
            padre=padre.padre;
        }
        return padre;

    }

    public void eliminar(int clave) {
        if (!pertenece(clave)) return;
        Nodo nodo = buscador(clave);

        if (nodo.izquierda == null) {
            reemplazar(nodo, nodo.derecha);     
        } 
        else if (nodo.derecha == null) {
            reemplazar(nodo, nodo.izquierda);    
            
        }
        else{
            Nodo s = sucesor(nodo);
            nodo.valor = s.valor;          // el nodo queda con el valor del sucesor
            nodo.clave = s.clave;          // ...y con su clave, si no el árbol queda desordenado
            reemplazar(s, s.derecha);
        }
        
        cantidad -= 1;
}
    

    public String toString(){
        String texto="{";
        String fin="}";
        ABB_Iterador it = iterador();
        
        while (it.haySiguiente()){
            if (texto.length() > 1) texto += ",";
            texto +=it.siguiente();
            
        }
        return  texto+= fin;

    }

    public class ABB_Iterador {
        private Nodo _actual;
        public ABB_Iterador(){
            _actual=raiz;
            if (_actual !=null){
                while (_actual.izquierda!=null){
                    _actual=_actual.izquierda;
                }
            }

        }

        public boolean haySiguiente() {            
                return _actual!=null;
        }
    
        public T siguiente() {
            T valor =_actual.valor;
            _actual=sucesor(_actual);
            return valor;
        }
    }

    public ABB_Iterador iterador() {
        return new ABB_Iterador();
    }

}
