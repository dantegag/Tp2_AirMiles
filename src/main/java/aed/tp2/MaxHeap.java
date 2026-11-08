package aed.tp2;

import java.util.Comparator;
import java.util.ArrayList;

public class MaxHeap<T extends Comparable<T>>{

    private ArrayList<HandleHeap> heap;
    private Comparator<T> comparador;

    private class HandleHeap implements Handle<T> {
        private  T valor;
        private  int posicion;

        private HandleHeap(T v, int pos){
            this.valor = v;
            this.posicion = pos;
        }
        
        public T valor() {
            return this.valor;
        }

        public void modificar(T v){
            this.valor = v;
            siftUp(this.posicion);
            siftDown(this.posicion);
        }

        public int compareTo(Handle<T> otro) {
            return this.valor.compareTo(otro.valor());
        }
    
        public boolean equals(Handle<T> h){
            return this.compareTo(h) == 0;
        }
        
        public void eliminar(){
        //Nunca se eliminan usuarios, solo está acá para cumplir con la interfaz
        }

    }
    
    
    public MaxHeap(Comparator<T> comp){
        this.heap = new ArrayList<HandleHeap>();
        this.comparador = comp;
    }

    private void swap(int i, int j){
        HandleHeap aux = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, aux);
        heap.get(i).posicion = i;
        heap.get(j).posicion = j;
    }

    private void siftUp(int i){
        while(i > 0){
            int padre = (i - 1) / 2;
            if (comparador.compare(heap.get(i).valor, heap.get(padre).valor) > 0){
                swap(i, padre);
                i = padre;
            } else {
                break;
            }
        }
    }

    private void siftDown(int i) {
        while (2 * i + 1 < heap.size()) {
            int izq = 2 * i + 1;
            int der = 2 * i + 2;
            int mayor = izq;
            if (der < heap.size() && comparador.compare(heap.get(der).valor, heap.get(izq).valor) > 0) {
                mayor = der;
            }
            if (comparador.compare(heap.get(mayor).valor, heap.get(i).valor) > 0){
                swap(i, mayor);
                i = mayor;
            } else{
                break;
            }
        }
    }

    public int cardinal(){
    return heap.size();
    }

    public Handle<T> crearHandleBusqueda(T elem){
        return new HandleHeap(elem, -1);
    }

    public Handle<T> encolar(T elem){
        HandleHeap nuevo = new HandleHeap(elem, heap.size());
        heap.add(nuevo);
        siftUp(nuevo.posicion);
        return nuevo;
    }

    public Handle<T> desencolar(){
        HandleHeap max = heap.get(0);
        HandleHeap ultimo = heap.remove(heap.size() - 1);
        if (!heap.isEmpty()){
            heap.set(0, ultimo);
            ultimo.posicion = 0;
            siftDown(0);
        }
        return max;
    }

    public void reEncolar(Handle<T> h){
        HandleHeap hh = (HandleHeap) h;
        hh.posicion = heap.size();
        heap.add(hh);
        siftUp(hh.posicion);
    }
}