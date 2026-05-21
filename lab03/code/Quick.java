import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

// Nota = Ordenamiento de O(n log n)
public class Quick {
    
    public static void sort(ArrayList<Song> songs, Comparator<Song> comparator) {
        Collections.shuffle(songs);
        sort(songs, 0, songs.size() - 1, comparator);
    }

    private static void sort(ArrayList<Song> songs, int lo, int hi, Comparator<Song> comparator) {
        if (hi <= lo) {
            return;
        }
        int j = partition(songs, lo, hi, comparator);
        sort(songs, lo, j - 1, comparator);
        sort(songs, j + 1, hi, comparator);
    }

    private static int partition(ArrayList<Song> songs, int lo, int hi, Comparator<Song> comparator) {
        int i = lo;
        int j = hi + 1;
        Song v0 = songs.get(lo);

        while (true) {
            while (less(comparator, songs.get(++i), v0)) {
                if (i == hi) {
                    break;
                }
            }
            while (less(comparator, v0, songs.get(--j))) {
                if (j == lo) {
                    break;
                }
            }
            if (i >= j) {
                break;
            }
            exch(songs, i, j);
        }
        exch(songs, lo, j);
        return j;
    }

    private static boolean less(Comparator<Song> comparator, Song v0, Song v1) {
        return comparator.compare(v0, v1) < 0;
    }

    private static void exch(ArrayList<Song> songs, int i, int j) {
        Song swap = songs.get(i);
        songs.set(i, songs.get(j));
        songs.set(j, swap);
    }


}