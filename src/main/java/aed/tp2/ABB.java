package aed.tp2;

import java.util.*;

// Todos los tipos de datos "Comparables" tienen el método compareTo()
// elem1.compareTo(elem2) devuelve un entero. Si es mayor a 0, entonces elem1 > elem2
public class ABB<T extends Comparable<T>> {
    private Nodo raiz;
    private int cantidad;

    private Nodo buscador(T elem) {
        Nodo actual = raiz;
        Nodo anterior = null;

        while (actual != null) {
            int cmp = elem.compareTo(actual.valor);
            if (cmp == 0) return actual;

            anterior = actual;
            if( cmp > 0) actual= actual.derecha;
            else actual= actual.izquierda;
        }
            return anterior;
        
    }

    private class Nodo {
        T valor;
        Nodo izquierda;
        Nodo derecha;
        Nodo padre;

        Nodo(T v) {
            valor = v;
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
       if (cantidad==1)return raiz.valor;  
       else{
        Nodo actual=raiz;
        while (actual.izquierda !=null){
            actual=actual.izquierda;

        }
        return actual.valor;
       }
        
       
    }

    public T maximo(){
        if (cantidad==1)return raiz.valor;  
       else{
        Nodo actual=raiz;
        while (actual.derecha !=null){
            actual=actual.derecha;

        }
        return actual.valor;
       }
        
       
    }
    public void insertar(T elem){
        
        
        
        if (cantidad==0){
            Nodo nuevo= new Nodo(elem);
            raiz=nuevo;
            cantidad +=1;
            return ;
        }
        
        if (! pertenece(elem)){
            Nodo padre=buscador(elem);
            Nodo nuevo= new Nodo(elem);
            nuevo.padre=padre;
            if (elem.compareTo(padre.valor)>0 ){
                    
                padre.derecha=nuevo;
                }
            else {
                padre.izquierda=nuevo;
                }
            cantidad +=1;
        }
                
        }
    
        
        

        

        
        
    

    public boolean pertenece(T elem){
        if(cantidad==0 || (buscador(elem).valor).compareTo(elem) != 0){return false;}
        else return true;
        

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

    public void eliminar(T elem) {
        if (!pertenece(elem)) return;
        Nodo nodo = buscador(elem);

        if (nodo.izquierda == null) {
            reemplazar(nodo, nodo.derecha);     
        } 
        else if (nodo.derecha == null) {
            reemplazar(nodo, nodo.izquierda);    
            
        }
        else{
            Nodo s = sucesor(nodo);
            nodo.valor = s.valor;          // el nodo queda con el valor del sucesor
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
