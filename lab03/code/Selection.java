import java.util.ArrayList;
import java.util.Comparator;

//Nota = Ordenamiento de O(n^2)
public class Selection {
    
    public static void sort(ArrayList<Song> songs, Comparator<Song> comparator) {
        int num = songs.size();
        for (int i = 0; i < num; i++) {
            int min = i;
            for (int j = i + 1; j < num; j++) {
                if(less(comparator, songs.get(j), songs.get(min))) {
                    min = j;
                }
            }
            exch(songs, i, min);
        }
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