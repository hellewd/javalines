import java.util.ArrayList;
import edu.princeton.cs.algs4.StopwatchCPU;
import edu.princeton.cs.algs4.Out;

public class Experimento {

    public static void main(String[] args) {
        int[] sizes = {1024, 2048, 4096, 8192, 16384, 32768};

        System.out.println("Experimento 1");
        ejecutarExperimentoOrdenamiento(sizes);

        System.out.println("Experimento 2");
        ejecutarExperimentoBusqueda(sizes);

        System.out.println("¡Experimentos completados con éxito! Archivos CSV generados.");
    }
    // Experimento de ordenamiento
    private static void ejecutarExperimentoOrdenamiento(int[] sizes) {
        for (int num : sizes) {
            Out csv = new Out("lab03/csv/sort_" + num + ".csv");
            csv.println("instancia,insertionSort,selectionSort,mergeSort,quickSort");

            for (int i = 0; i < 100; i++) {
                long seed = num + i;
                ArrayList<Song> originalList = DataGenerator.generateDataBase(num, seed);

                // Insertion Sort
                ArrayList<Song> copyInsertion = new ArrayList<>(originalList);
                SongDataBase dbInsertion = new SongDataBase(copyInsertion);
                StopwatchCPU timerInsertion = new StopwatchCPU();
                dbInsertion.ordenarPorAlgoritmo("insertionSort", "plays");
                double tInsertion = timerInsertion.elapsedTime();

                // Selection Sort
                ArrayList<Song> copySelection = new ArrayList<>(originalList);
                SongDataBase dbSelection = new SongDataBase(copySelection);
                StopwatchCPU timerSelection = new StopwatchCPU();
                dbSelection.ordenarPorAlgoritmo("selectionSort", "plays");
                double tSelection = timerSelection.elapsedTime();

                // Merge Sort
                ArrayList<Song> copyMerge = new ArrayList<>(originalList);
                SongDataBase dbMerge = new SongDataBase(copyMerge);
                StopwatchCPU timerMerge = new StopwatchCPU();
                dbMerge.ordenarPorAlgoritmo("mergeSort", "plays");
                double tMerge = timerMerge.elapsedTime();

                // Quick Sort
                ArrayList<Song> copyQuick = new ArrayList<>(originalList);
                SongDataBase dbQuick = new SongDataBase(copyQuick);
                StopwatchCPU timerQuick = new StopwatchCPU();
                dbQuick.ordenarPorAlgoritmo("quickSort", "plays");
                double tQuick = timerQuick.elapsedTime();

                csv.println(i + "," + tInsertion + "," + tSelection + "," + tMerge + "," + tQuick);
            }
            csv.close();
        }
    }
    // Experimento de búsqueda
    private static void ejecutarExperimentoBusqueda(int[] sizes) {
        for (int num : sizes) {
            Out csv = new Out("lab03/csv/search_" + num + ".csv");
            csv.println("instance,artist,t_linear,t_binary");

            // Los 5 artistas por índice fijo (el último es num/50 - 1, no num/50)
            String[] targetArtists = {
                "Artist_0",
                "Artist_" + (num / 200),
                "Artist_" + (num / 100),
                "Artist_" + (3 * num / 200),
                "Artist_" + (num / 50 - 1)
            };

            for (int i = 0; i < 100; i++) {
                long seed = num + i;
                ArrayList<Song> originalList = DataGenerator.generateDataBase(num, seed);

                // DB desordenada para búsqueda lineal
                SongDataBase dbUnsorted = new SongDataBase(new ArrayList<>(originalList));
                // DB ordenada por artista para búsqueda binaria
                SongDataBase dbSorted = new SongDataBase(new ArrayList<>(originalList));
                dbSorted.ordenarPorAlgoritmo("mergeSort", "artist");

                for (String artist : targetArtists) {
                    StopwatchCPU timerLineal = new StopwatchCPU();
                    for (int rep = 0; rep < 1000; rep++) {
                        dbUnsorted.sequentialSearch(artist);
                    }
                    double tLineal = timerLineal.elapsedTime();

                    StopwatchCPU timerBinaria = new StopwatchCPU();
                    for (int rep = 0; rep < 1000; rep++) {
                        dbSorted.binarySearch(artist);
                    }
                    double tBinaria = timerBinaria.elapsedTime();

                    csv.println(i + "," + artist + "," + tLineal + "," + tBinaria);
                }
            }
            csv.close();
        }
    }
}