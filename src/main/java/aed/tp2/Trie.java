package aed.tp2;
import java.util.ArrayList;

public class Trie<T> {
    private Nodo raiz;
    private int cantidadClaves;

    private class Nodo{
        char clave;
        Nodo hermano;
        Nodo hijo;
        T valor;
        
        public Nodo(char c){
            clave = c;
            hermano = null;
            hijo = null;
            valor = null;
        }
    }

    public Trie(){
        raiz = new Nodo('\0');
        cantidadClaves = 0;
    }

    public void nuevaClave(String clave, T val){
        char[] iterable = new char[clave.length()];
        for (int i = 0; i < clave.length(); i++){
            iterable[i] = clave.charAt(i);
        }
        if (raiz.hijo == null){ //Caso primera clave del Trie
            raiz.hijo = new Nodo(iterable[0]);
            Nodo actual = raiz.hijo;
            for (int i = 1; i < iterable.length; i++){
                actual.hijo = new Nodo(iterable[i]);
                actual = actual.hijo;
            }
            actual.valor = val;
            cantidadClaves++;
            return;
        }
        Nodo actual = raiz.hijo;
        while(actual.clave != iterable[0] && actual.hermano != null){
            actual = actual.hermano;
        }
        if (actual.clave != iterable[0] && actual.hermano == null){
            actual.hermano = new Nodo(iterable[0]);
            actual = actual.hermano;
        }
        for (int i = 1; i < iterable.length; i++){ //Caso general
            if (actual.hijo == null){
                actual.hijo = new Nodo(iterable[i]);
                actual = actual.hijo;
            } else if (actual.hijo.clave == iterable[i]){
                actual = actual.hijo;
            } else if (actual.hijo.clave != iterable[i]){
                actual = actual.hijo;
                while (actual.hermano != null && actual.hermano.clave != iterable[i]){
                    actual = actual.hermano;
                }
                if (actual.hermano == null){
                    actual.hermano = new Nodo(iterable[i]);
                    actual = actual.hermano;
                } else if(actual.hermano.clave == iterable[i]){
                    actual = actual.hermano;
                }
            }
        }
        if (actual.valor == null){
            cantidadClaves++;
        }
        actual.valor = val;
    }

    public T obtener(String clave){
        if (!pertenece(clave)){
            return null;
        }
        char[] iterable = new char[clave.length()];
        for (int i = 0; i < clave.length(); i++){
            iterable[i] = clave.charAt(i);
        }
        Nodo actual = raiz;
        for (int i = 0; i < iterable.length; i++){
            actual = actual.hijo;
            while(actual.clave != iterable[i]){
                actual = actual.hermano;
            }
        }
        return actual.valor;
    }

    public boolean pertenece(String clave){
        char[] iterable = new char[clave.length()];
        for (int i = 0; i < clave.length(); i++){
            iterable[i] = clave.charAt(i);
        }
        Nodo actual = raiz;
        for (int i = 0; i < iterable.length; i++){
            if (actual.hijo == null){
                return false;
            } else {actual = actual.hijo;}
            while (actual.clave != iterable[i] && actual.hermano != null){
                actual = actual.hermano;
            }
            if (actual.clave != iterable[i]){
                return false;
            } 
        }
        return actual.valor != null;
    }

    public void borrar(String clave){
        if (!pertenece(clave)){
            return;
        }
        char[] iterable = new char[clave.length()];
        for (int i = 0; i < clave.length(); i++){
            iterable[i] = clave.charAt(i);
        }
        Nodo actual = raiz;
        ArrayList<Nodo> camino = new ArrayList<Nodo>();
        camino.add(raiz);
        for (int i = 0; i < iterable.length; i++){
            actual = actual.hijo;
            while(actual.clave != iterable[i]){
                actual = actual.hermano;
            }
            camino.add(actual);
        }
        actual.valor = null;
        cantidadClaves--; 
        for (int i = camino.size() - 1; i > 0; i--){
            Nodo padre = camino.get(i-1);
            if (actual.hijo == null && actual.valor == null){
                if (padre.hijo == actual){
                    padre.hijo = actual.hermano;
                } else {
                    Nodo prev = padre.hijo;
                    while (prev.hermano != actual){
                        prev = prev.hermano;
                    }
                    prev.hermano = actual.hermano;
                }
            } else {break;}
            actual = padre;
        }
    }

    public int size(){
        return cantidadClaves;
    }
}