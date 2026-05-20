package code;

import java.util.ArrayList; // Solicitado en el punto 2.2
import java.util.Collections;
import java.util.Comparator;

public class SongDataBase {
    private ArrayList<Song> songs;

    public SongDataBase(ArrayList<Song> songs) {
        this.songs = songs;
    }

    public ArrayList<Song> getSongs() {
        return this.songs;
    }
    // # Método 2.2.1 == Ordenamiento
    public void ordenarPorAlgoritmo(String algoritmo, String atributo) {
        Comparator<Song> comparator = Song.byPlays();
        // Atributo
        if (atributo != null) {
            switch(atributo.toLowerCase()) {
                case "artist" -> comparator = Song.byArtist(); 
                case "genre" -> comparator = Song.byGenre(); 
                case "year" -> comparator = Song.byYear(); 
                default -> comparator = Song.byPlays();
            }
        }
        // Algoritmo
        switch (algoritmo) {
            case "insertionSort" -> Insertion.sort(this.songs, comparator);
            case "selectionSort" -> Selection.sort(this.songs, comparator);
            case "mergeSort" -> Merge.sort(this.songs, comparator);
            case "quickSort" -> Quick.sort(this.songs, comparator);
            default -> Collections.sort(this.songs, comparator);
        }
    }
    // Método 2.2.2 == Búsqueda se divide en dos partes: Búsqueda secuencial = (a); y Búsqueda binaria = (b).
    //(a)
    public ArrayList<Song> sequentialSearch(String artist) {
        ArrayList<Song> resultados = new ArrayList<>();
        // Validación básica de vacíos
        if(artist == null) {
            return resultados;
        }
        // Recorremos la lista
        for(Song tmp : this.songs) {
            if(artist.equalsIgnoreCase(tmp.getArtist())) {
                resultados.add(tmp);
            }
        }
        return resultados;
    }
    //(b)
    public ArrayList<Song> binarySearch(String artist) {
        ArrayList<Song> resultados = new ArrayList<>();
        // Validación básica de vacíos
        if(artist == null) {
            return resultados;
        }
        // Inspirado en el método Rank
        int index = rank(this.songs, artist);
        // Recorremos la lista desde el indice encontrado
        while(index < this.songs.size() && artist.equalsIgnoreCase(this.songs.get(index).getArtist())) {
            resultados.add(this.songs.get(index));
            index++; 
        }
        return resultados;
    }
    // Al utilizar método <<rank>> implica que se implemente.
    private int rank(ArrayList<Song> data, String key) {
        int lo = 0;
        int hi = data.size();

        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            if (data.get(mid).getArtist().compareTo(key) < 0) {
                lo = mid + 1;
            } else {
                hi = mid;
            }
        }
        return lo;
    }

}