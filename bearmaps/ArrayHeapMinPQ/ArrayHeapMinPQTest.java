package bearmaps;

import org.junit.Test;
import edu.princeton.cs.algs4.Stopwatch;
import static org.junit.Assert.*;
import java.util.ArrayList;

public class ArrayHeapMinPQTest {

    @Test
    public void testEquivilance() {
        NaiveMinPQ<Integer> naive = new NaiveMinPQ<>();
        ArrayHeapMinPQ<Integer> array = new ArrayHeapMinPQ<>();
        int j = 99;
        for(int i = 0; i < 100; i++) {
            naive.add(i, j);
            array.add(i, j);
            j--;
        }
        for (int i = 0; i < 100; i++) {
            assertEquals((int) naive.removeSmallest(), (int) array.removeSmallest());
        }
    }

    @Test
    public void testSize() {
        ArrayHeapMinPQ<Integer> array = new ArrayHeapMinPQ<>();
        int j = 9;
        for(int i = 0; i < 10; i++) {
            array.add(i, j);
            j--;
        }
        assertTrue(array.size() == 10);
        array.add(11, 11);
        array.add(12, 12);
        assertTrue(array.size() == 12);
        for(int i = 0; i < 12; i++) {
            array.removeSmallest();
        }
        assertTrue(array.size() == 0);
    }

    @Test
    public void testContains() {
        ArrayHeapMinPQ<Integer> array = new ArrayHeapMinPQ<>();
        int j = 9;
        for(int i = 0; i < 10; i++) {
            array.add(i, j);
            j--;
        }
        for(int i = 0; i < 10; i++) {
            assertTrue(array.contains(i));
        }
        assertFalse(array.contains(11));
        for(int i = 0; i < 10; i++) {
            array.removeSmallest();
        }
        for(int i = 0; i < 10; i++) {
            assertFalse(array.contains(i));
        }
    }

    @Test
    public void testSmallest() {
        ArrayHeapMinPQ<Integer> array = new ArrayHeapMinPQ<>();
        int j = 9;
        for(int i = 0; i < 10; i++) {
            array.add(i, j);
            j--;
        }
        assertEquals(9, (int) array.getSmallest());
        assertEquals(9, (int) array.removeSmallest());
        assertEquals(8, (int) array.removeSmallest());
        assertEquals(7, (int) array.getSmallest());
    }

    @Test
    public void testChangePriorty() {
        ArrayHeapMinPQ<Integer> array = new ArrayHeapMinPQ<>();
        int j = 99;
        for(int i = 0; i < 100; i++) {
            array.add(i, j);
            j--;
        }
        assertEquals(99, (int) array.removeSmallest());
        array.changePriority(10, 0);
        assertEquals(10, (int) array.getSmallest());
        array.changePriority(11, 10);
        assertTrue(array.size() == 99);
    }

    @Test
    public void testTimeOne() {
        NaiveMinPQ<Integer> naive = new NaiveMinPQ<>();
        ArrayHeapMinPQ<Integer> array = new ArrayHeapMinPQ<>();
        int j = 9999999;
        Stopwatch stopwatch = new Stopwatch();

        // test time for NaiveMinPQ
        for(int i = 0; i < 10000000; i++) {
            naive.add(i, j);
            j--;
        }
        double time1 = stopwatch.elapsedTime();
        System.out.println("For NaiveMinPQ, the time elapsed was: " + time1);

        // test time for ArrayHeapMinPQ
        stopwatch = new Stopwatch();
        j = 9999999;
        for(int i = 0; i < 10000000; i++) {
            naive.add(i, j);
            j--;
        }
        double time2 = stopwatch.elapsedTime();
        System.out.println("For ArrayHeapMinPQ, the time elapsed was: " + time2);
        assertTrue(time1 >= time2);
    }

    @Test
    public void testTime2() {
        NaiveMinPQ<Integer> naive = new NaiveMinPQ<>();
        ArrayHeapMinPQ<Integer> array = new ArrayHeapMinPQ<>();
        int j = 999999;

        // test time for NaiveMinPQ
        for(int i = 0; i < 1000000; i++) {
            naive.add(i, j);
            array.add(i, j);
            j--;
        }

        // time for NaiveMinPQ
        Stopwatch stopwatch = new Stopwatch();
        naive.removeSmallest();
        double time1 = stopwatch.elapsedTime();
        System.out.println("For NaiveMinPQ, the time elapsed was: " + time1);

        // time for ArrayHeapMinPQ
        stopwatch = new Stopwatch();
        array.removeSmallest();
        double time2 = stopwatch.elapsedTime();
        System.out.println("For ArrayHeapMinPQ, the time elapsed was: " + time2);
        assertTrue(time1 >= time2);
    }

    @Test
    public void testEquivalenceRandom() {
        // initialize variables
        NaiveMinPQ<Integer> naive = new NaiveMinPQ<>();
        ArrayHeapMinPQ<Integer> array = new ArrayHeapMinPQ<>();
        ArrayList<Integer> index = new ArrayList<>();
        for(int i = 0; i < 100; i++) {
            index.add(i);
        }

        for(int i = 0; i < 100; i++) {
            int randomIndex = (int) (Math.random() * 100);
            while(!index.contains(randomIndex)) {
                randomIndex = (int) (Math.random() * 100);
            }
            naive.add(i, randomIndex);
            array.add(i, randomIndex);
            int r = index.indexOf(randomIndex);
            index.remove(r);
        }

        for(int i = 0; i < 100; i++) {
            int n = (int) naive.removeSmallest();
            int a = (int) array.removeSmallest();
            assertEquals(n, a);
        }
    }
}
