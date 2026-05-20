import java.util.Comparator;

public class Song {

    private int id, year;
    private String title, artist, genre;
    private long plays; 

    public Song(int id, String title, String artist, String genre, int year, long plays) {
        this.id     = id;
        this.title  = title;
        this.artist = artist;
        this.genre  = genre;
        this.year   = year;
        this.plays  = plays;
    }
    // # Típicos Getters
    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getArtist() { return artist; }
    public String getGenre() { return genre; }
    public int getYear() { return year; }
    public long getPlays() { return plays; }

    // # Ordena por número de reproducciones (ascendente).
    public static Comparator<Song> byPlays() {
        return (s1, s2) -> Long.compare(s1.getPlays(), s2.getPlays());
    }

    // # Ordena por artista
    public static Comparator<Song> byArtist() {
        return (s1, s2) -> s1.getArtist().toLowerCase().compareTo(s2.getArtist().toLowerCase());
    }
    // # Ordena por género
    public static Comparator<Song> byGenre() {
        return (s1, s2) -> s1.getGenre().toLowerCase().compareTo(s2.getGenre().toLowerCase());
    }

    /** Ordena por año (ascendente). */
    public static Comparator<Song> byYear() {
        return (s1, s2) -> Integer.compare(s1.getYear(), s2.getYear());
    }
}