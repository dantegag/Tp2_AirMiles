package aed.tp2;
public class Trie<V> {

    

    private Nodo<V> raiz;
    private int cantidadClaves;

    private static class Nodo<V> {
        Nodo<V>[] hijos;
        int cantHijos;
        V valor;
        Nodo() {
            
            hijos = new Nodo[null];
            cantHijos = 0;
            valor = null;
        }
    }

    public Trie() {
        raiz = new Nodo<V>();
        cantidadClaves = 0;
    }

    
    public void definir(String clave, V valor) {
        if (valor == null) {
            throw new IllegalArgumentException("El valor no puede ser null: null marca un nodo de paso.");
        }
        Nodo<V> actual = raiz;
        for (int i = 0; i < clave.length(); i++) {
            int c = indiceDe(clave, i);
            if (actual.hijos[c] == null) {
                actual.hijos[c] = new Nodo<V>();
                actual.cantHijos++;
            }
            actual = actual.hijos[c];
        }
        if (actual.valor == null) {
            cantidadClaves++;
        }
        actual.valor = valor;
    }

    /** Devuelve el valor asociado a la clave, o null si no esta definida. O(|L|). */
    public V obtener(String clave) {
        Nodo<V> n = buscarNodo(clave);
        return n == null ? null : n.valor;
    }

    
    public boolean pertenece(String clave) {
        Nodo<V> n = buscarNodo(clave);
        return n != null && n.valor != null;
    }

    public void borrar(String clave) {
        if (!pertenece(clave)) {
            return;
        }
        borrarDesde(raiz, clave, 0);
        cantidadClaves--;
    }

    public int cantidadClaves() {
        return cantidadClaves;
    }

    public boolean estaVacio() {
        return cantidadClaves == 0;
    }

    

    private Nodo<V> buscarNodo(String clave) {
        Nodo<V> actual = raiz;
        for (int i = 0; i < clave.length(); i++) {
            actual = actual.hijos[indiceDe(clave, i)];
            if (actual == null) {
                return null;
            }
        }
        return actual;
    }

   
    private boolean borrarDesde(Nodo<V> nodo, String clave, int i) {
        if (i == clave.length()) {
            nodo.valor = null;
            return nodo.cantHijos == 0;
        }
        int c = indiceDe(clave, i);
        Nodo<V> hijo = nodo.hijos[c];
        if (hijo == null) {
            return false;
        }
        if (borrarDesde(hijo, clave, i + 1)) {
            nodo.hijos[c] = null;
            nodo.cantHijos--;
        }
        return nodo.valor == null && nodo.cantHijos == 0;
    }

    private int indiceDe(String clave, int i) {
        int c = clave.charAt(i);
        if (c >= ALFABETO) {
            throw new IllegalArgumentException(
                "Caracter fuera del alfabeto en la posicion " + i + " de \"" + clave + "\".");
        }
        return c;
    }
}
