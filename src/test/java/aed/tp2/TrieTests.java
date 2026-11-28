package aed.tp2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TrieTests {
    private Trie<Integer> am;

    @BeforeEach
    void setUp() {
        am = new Trie<>();
    }

    @Test 
    void nuevoTrie(){
        assertNotNull(am);
        assertEquals(0, am.size());
    }

    @Test
    void agregarYObtenerClaves(){
        am.nuevaClave("  prueba1", 10);
        am.nuevaClave("prueb  a2", 20);
        am.nuevaClave("prueba3  ", 30);
        assertEquals(3, am.size());
        assertTrue(am.pertenece("  prueba1"));
        assertEquals(10, am.obtener("  prueba1"));
        assertEquals(20, am.obtener("prueb  a2"));
        assertEquals(30, am.obtener("prueba3  "));
        assertFalse(am.pertenece("prueba4"));
    }

    @Test 
    void agregarObtenerYBorrarClaves(){
        am.nuevaClave("  prueba1", 10);
        am.nuevaClave("prueb  a2", 20);
        am.nuevaClave("prueba3  ", 30);
        assertEquals(3, am.size());
        am.borrar("  prueba1");
        assertEquals(2, am.size());
        am.borrar("prueba4");
        assertEquals(2, am.size());
        am.nuevaClave(" ", 40);
        assertEquals(3, am.size());
        assertEquals(40, am.obtener(" "));
        am.borrar("prueba3  ");
        am.borrar(" ");
        assertEquals(1, am.size());
        assertEquals(20, am.obtener("prueb  a2"));
        am.borrar("prueb  a2");
        assertEquals(0, am.size());
    }

    @Test 
    void sobreescribirClaves(){
        am.nuevaClave("prueba", 10);
        assertEquals(10, am.obtener("prueba"));
        am.nuevaClave("prueba", 20);
        assertEquals(1, am.size());
        assertEquals(20, am.obtener("prueba"));
    }

    @Test
    void borrarPrefijosYSufijos(){
        am.nuevaClave("pru", 10);
        am.nuevaClave("prueb", 20);
        am.nuevaClave("prueba1", 30);
        assertEquals(3, am.size());
        am.borrar("pru");
        assertFalse(am.pertenece("pru"));
        assertEquals(20, am.obtener("prueb"));
        am.borrar("prueba1");
        assertFalse(am.pertenece("prueba1"));
        assertEquals(20, am.obtener("prueb"));
    }
}
