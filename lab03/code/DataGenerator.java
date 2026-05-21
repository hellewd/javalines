import java.util.ArrayList;
import edu.princeton.cs.algs4.StdRandom;

//Observación: Considero que es poco profesional utilizar ciertas cosas en inglés y otras cosas en español; por ende, utilizaremos todo en inglés como si fueramos yankees, ya fue.
public class DataGenerator {
    
    public static ArrayList<Song> generateDataBase(int num, long seed) {
        StdRandom.setSeed(seed);
        int artistsCount =  (num / 50);
        // Comprobación de lista vacía
        if(artistsCount < 1) {
            artistsCount = 1;
        }

        String[] artists = new String[artistsCount];
        for (int i = 0; i < artistsCount; i++) {
            artists[i] = "Artist_" + i;
        }
        // Géneros ya definidos por informe.
        String[] genres = {"Pop", "Rock", "Jazz", "Electronic", "Classical", "Hip-Hop"};
        
        ArrayList<Song> songsList = new ArrayList<>();
        for(int id = 1; id <= num; id++) {
            String title = "Song_" + id;
            // Sea uniformInt proveniente de Princeton <=> Muy parecido a utilizar un MathRandom pero sin tantos cálculos.
            String artist = artists[StdRandom.uniformInt(artists.length)];
            String genre = genres[StdRandom.uniformInt(genres.length)];
            // Mismo uso, simplemente se define un margen desde 1970 - 2026
            int year = StdRandom.uniformInt(1970, 2027);
            // Nuevamente mismo uso.
            long plays = StdRandom.uniformInt(0, 10000001);

            Song newSong = new Song(id, title, artist, genre, year, plays);
            songsList.add(newSong);
        }

        return songsList;
    }
}
    