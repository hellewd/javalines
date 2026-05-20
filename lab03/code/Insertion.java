import java.util.ArrayList;
import java.util.Comparator;

public class Insertion {

    public static void sort(ArrayList<Song> songs, Comparator<Song> comparator) {
        int num = songs.size();
        for (int i = 1; i < n; i++) {
            for (int j = i; j > 0 && less(comparator, songs.get(j), songs.get(j - 1)); j--) {
                exch(songs, j, j - 1);
            }
        }
    }

    private static boolean less(Comparator<Song> comparator, Song v0, Song v1) {
        return comparator.compare(v0, v1) < 0;
    }

    private static void exch(ArrayList<Song> songs, int i, int j) {
        Song swap = songs.get(i);
        songs.set(i, songs.get(j));
        song.set(j, swap);
    }
}
