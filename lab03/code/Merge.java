import java.util.ArrayList;
import java.util.Comparator;

// Nota = Ordenamiento de O(n log n)
public class Merge {
    
    public static void sort(ArrayList<Song> songs, Comparator<Song> comparator) {
        ArrayList<Song> aux = new ArrayList<>(songs);
        sort(songs, aux, 0, songs.size() - 1, comparator);
    }

    private static void sort(ArrayList<Song> songs, ArrayList<Song> aux, int lo, int hi, Comparator<Song> comparator) {
        if (hi <= lo) {
            return;
        }
        int mid = lo + (hi - lo) / 2;
        sort(songs, aux, lo, mid, comparator); // Mitad izquierda
        sort(songs, aux, mid + 1, hi, comparator); // Mitad derecha
        merge(songs, aux, lo, mid, hi, comparator); // Fusión
    }

    private static void merge(ArrayList<Song> songs, ArrayList<Song> aux, int lo, int mid, int hi, Comparator<Song> comparator) {
        for (int k = lo; k <= hi; k++) {
            aux.set(k, songs.get(k));
        }

        int i = lo, j = mid + 1;
        for (int k = lo; k <= hi; k++) {
            if (j > mid) {
                songs.set(k, aux.get(j++));
            } else if (j > hi) {
                songs.set(k, aux.get(i++));
            } else if (less(comparator, aux.get(j), aux.get(i))) {
                songs.set(k, aux.get(j++));
            } else {
                songs.set(k, aux.get(i++))
            }
        }
    }

    private static boolean less(Comparator<Song> comparator, Song v0, Song v1) {
        return comparator.compare(v0, v1) < 0;
    }
}
