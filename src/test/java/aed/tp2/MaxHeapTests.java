package aed.tp2;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MaxHeapTests {

    @Test
    void maxHeapGenericoConEnteros() {
        MaxHeap<Integer> heap = new MaxHeap<Integer>((a, b) -> a.compareTo(b));

        Handle<Integer> h10 = heap.encolar(10);
        Handle<Integer> h50 = heap.encolar(50);
        Handle<Integer> h30 = heap.encolar(30);

        assertEquals(3, heap.cardinal());

        // Probamos siftUp y siftDown desde modificar()
        h10.modificar(100);
        h50.modificar(5);

        assertEquals(100, heap.desencolar().valor());
        assertEquals(30, heap.desencolar().valor());
        assertEquals(5, heap.desencolar().valor());
        assertEquals(0, heap.cardinal());
    }

    @Test
    void maxHeapGenericoConStringsYReEncolar() {
        // Ordenado por longitud del String
        MaxHeap<String> heap = new MaxHeap<String>((s1, s2) -> Integer.compare(s1.length(), s2.length()));

        heap.encolar("sol");          // largo 3
        heap.encolar("computadora");  // largo 11
        heap.encolar("casa");         // largo 4

        Handle<String> max = heap.desencolar();
        assertEquals("computadora", max.valor());

        heap.reEncolar(max);
        max.modificar("ir"); // pasa a largo 2

        assertEquals("casa", heap.desencolar().valor());
        assertEquals("sol", heap.desencolar().valor());
        assertEquals("ir", heap.desencolar().valor());
    }
}