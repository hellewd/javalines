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
     private static int repsLento(int n) {
        if (n <= 1024)  return 10;
        if (n <= 2048)  return 5;
        if (n <= 4096)  return 3;
        return 1;
    }
 
    private static int repsRapido(int n) {
        if (n <= 1024)  return 500;
        if (n <= 2048)  return 200;
        if (n <= 4096)  return 100;
        if (n <= 8192)  return 50;
        if (n <= 16384) return 20;
        return 10;
    }
 
    private static void ejecutarExperimentoOrdenamiento(int[] sizes) {
        for (int num : sizes) {
            Out csv = new Out("csv/sort_" + num + ".csv");
            csv.println("instancia,insertionSort,selectionSort,mergeSort,quickSort");
 
            int rLento  = repsLento(num);
            int rRapido = repsRapido(num);
 
            for (int i = 0; i < 100; i++) {
                long seed = num + i;
                ArrayList<Song> originalList = DataGenerator.generateDataBase(num, seed);
 
                // --- Insertion Sort ---
                ArrayList<SongDataBase> dbsIns = new ArrayList<>();
                for (int rep = 0; rep < rLento; rep++)
                    dbsIns.add(new SongDataBase(new ArrayList<>(originalList)));
 
                StopwatchCPU timerInsertion = new StopwatchCPU();
                for (int rep = 0; rep < rLento; rep++)
                    dbsIns.get(rep).ordenarPorAlgoritmo("insertionSort", "plays");
                double tInsertion = timerInsertion.elapsedTime() / rLento;
 
                // --- Selection Sort ---
                ArrayList<SongDataBase> dbsSel = new ArrayList<>();
                for (int rep = 0; rep < rLento; rep++)
                    dbsSel.add(new SongDataBase(new ArrayList<>(originalList)));
 
                StopwatchCPU timerSelection = new StopwatchCPU();
                for (int rep = 0; rep < rLento; rep++)
                    dbsSel.get(rep).ordenarPorAlgoritmo("selectionSort", "plays");
                double tSelection = timerSelection.elapsedTime() / rLento;
 
                // --- Merge Sort ---
                ArrayList<SongDataBase> dbsMer = new ArrayList<>();
                for (int rep = 0; rep < rRapido; rep++)
                    dbsMer.add(new SongDataBase(new ArrayList<>(originalList)));
 
                StopwatchCPU timerMerge = new StopwatchCPU();
                for (int rep = 0; rep < rRapido; rep++)
                    dbsMer.get(rep).ordenarPorAlgoritmo("mergeSort", "plays");
                double tMerge = timerMerge.elapsedTime() / rRapido;
 
                // --- Quick Sort ---
                ArrayList<SongDataBase> dbsQui = new ArrayList<>();
                for (int rep = 0; rep < rRapido; rep++)
                    dbsQui.add(new SongDataBase(new ArrayList<>(originalList)));
 
                StopwatchCPU timerQuick = new StopwatchCPU();
                for (int rep = 0; rep < rRapido; rep++)
                    dbsQui.get(rep).ordenarPorAlgoritmo("quickSort", "plays");
                double tQuick = timerQuick.elapsedTime() / rRapido;
 
                csv.println(i + "," + tInsertion + "," + tSelection + "," + tMerge + "," + tQuick);
            }
            csv.close();
        }
    }
    // Experimento de búsqueda
    private static void ejecutarExperimentoBusqueda(int[] sizes) {
        for (int num : sizes) {
            Out csv = new Out("csv/search_" + num + ".csv");
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

                for (String artist : targetArtists) {
                    
                    
                    StopwatchCPU timerLineal = new StopwatchCPU();
                    for (int rep = 0; rep < 1000; rep++) {
                        dbUnsorted.sequentialSearch(artist);
                    }
                    double tLineal = timerLineal.elapsedTime();

                    
                
                    
                    // Base desordenada nueva para que el MergeSort trabaje desde cero
                    SongDataBase dbSorted = new SongDataBase(new ArrayList<>(originalList));
                    StopwatchCPU timerBinaria = new StopwatchCPU(); 
                    
                    // 1ro: Se suma el tiempo de ordenar
                    dbSorted.ordenarPorAlgoritmo("mergeSort", "artist");
                    
                    // 2do: Se suma el tiempo de buscar 1000 veces
                    for (int rep = 0; rep < 1000; rep++) {
                        
                        dbSorted.binarySearch(artist);
                    }
                    
                    // El tiempo final de los 2 procesos
                    double tBinaria = timerBinaria.elapsedTime();

                   
                    csv.println(i + "," + artist + "," + tLineal + "," + tBinaria);
                }
            }
            csv.close();
        }
    }
}