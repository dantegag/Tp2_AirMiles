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

        }

    }
    
    
    public MaxHeap(Comparator<T> comp){
        this.heap = new ArrayList<HandleHeap>();
        this.comparador = comp;
    }

    private void swap(int i, int j){
        HandleHeap valI=heap.get(i);
        HandleHeap valJ=heap.get(j);
        heap.set(i, valJ);
        heap.set(j,valI);
        valI.posicion=j;
        valJ.posicion=i;

        
    }

    private void siftUp(int i){
        while (i!=0 && comparador.compare(heap.get(i).valor(),heap.get((i-1)/2).valor()) >0){

            swap(i,(i-1)/2);
            i=(i-1)/2;}
            
    
    }

    private void siftDown(int i) {
        while(i*2 +1 < cardinal()){
            int hijoIzq= i*2+1;
            int hijoDer= i*2+2;
            int hijoMayor= hijoIzq; // provisional
            if(hijoDer<cardinal() && comparador.compare(heap.get(hijoDer).valor(), heap.get(hijoIzq).valor())>0){
                hijoMayor = hijoDer;
            }
            if(comparador.compare(heap.get(hijoMayor).valor(),heap.get(i).valor()) <= 0){ // padre ya mayor
                break;
            }
            swap(hijoMayor,i);
            i=hijoMayor;
        }
       
    }

    public int cardinal(){
    return heap.size();
    }

    public Handle<T> encolar(T elem){
       HandleHeap nuevo=new HandleHeap(elem, cardinal());
       heap.add(nuevo);
       siftUp(cardinal()-1); //agrego al final y luego ordenar con siftUp
       return nuevo;
    }

    public Handle<T> desencolar(){
        HandleHeap raiz= heap.get(0);
        HandleHeap ultimo= heap.remove(cardinal()-1); // remove eliimina y devuelve

        if (cardinal() > 0){
        heap.set(0, ultimo);
        ultimo.posicion=0;
        siftDown(0);
        }
        raiz.posicion=-1;
        return raiz;  
    }

    public void reEncolar(Handle<T> h){
        HandleHeap aReEncolar= (HandleHeap)h; // no puedo usar el tipo Handle<T> directamente por lo que uso un cast
        aReEncolar.posicion=cardinal();
        heap.add(aReEncolar);
        siftUp(cardinal()-1);


        
    }
}
