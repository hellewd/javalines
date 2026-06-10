import edu.princeton.cs.algs4.StdRandom;
import java.util.ArrayList;
import java.util.HashSet;

public class DataGenerator {

    //Datos recomendados por enunciado

    private static final String[] CATEGORIES = {
        "Sensor", "Motor", "Microcontrolador",
        "Cable", "Bateria", "Herramienta", "Modulo", "Kit"
    };

    private static final String[] LOCATIONS = {
        "Estante_A", "Estante_B", "Caja_1",
        "Caja_2", "Laboratorio", "Bodega"
    };



    
     //Genera un InventoryItem con atributos aleatorios.
   
    public static InventoryItem generateItem(int id) {
        String category = CATEGORIES[StdRandom.uniformInt(CATEGORIES.length)];
        String location = LOCATIONS[StdRandom.uniformInt(LOCATIONS.length)];
        int    total    = StdRandom.uniformInt(1, 21); // [1, 20]
        return new InventoryItem(id, "Componente_" + id, category, location,
                                 total, total, 0);
    }

    // Generación de operaciones 
    public static ArrayList<InventoryOperation> generateOperations(
            int m, int keyUniverse, long seed) {

        StdRandom.setSeed(seed);

        // Conjunto de IDs presentes en el inventario en este momento
        HashSet<Integer> presentKeys = new HashSet<>();

        ArrayList<InventoryOperation> ops = new ArrayList<>(m);

        for (int i = 0; i < m; i++) {
            double r   = StdRandom.uniformDouble(); // [0, 1)
            int    key = StdRandom.uniformInt(1, keyUniverse + 1); // [1, keyUniverse]

            // PURCHASE 35% | QUERY 30% | LEND 15% | RECEIVE 10% | DISPOSE 10%
            OperationType type;
            if      (r < 0.35)                         type = OperationType.PURCHASE;
            else if (r < 0.35 + 0.30)                  type = OperationType.QUERY;
            else if (r < 0.35 + 0.30 + 0.15)          type = OperationType.LEND;
            else if (r < 0.35 + 0.30 + 0.15 + 0.10)   type = OperationType.RECEIVE;
            else                                        type = OperationType.DISPOSE;

            InventoryItem newItem = null;
            int quantity = 0;

            switch (type) {
                case PURCHASE:
                    quantity = StdRandom.uniformInt(1, 6); // [1, 5]
                    if (!presentKeys.contains(key)) {
                        // Componente nuevo → se crea el item y se registra la clave
                        newItem = generateItem(key);
                        presentKeys.add(key);
                    }
                    // Si ya existe, item queda null (addStock sobre el existente)
                    break;

                case LEND:
                case RECEIVE:
                    quantity = StdRandom.uniformInt(1, 6); // [1, 5]
                    break;

                case QUERY:
                case DISPOSE:
                    quantity = 0;
                    if (type == OperationType.DISPOSE) {
                        // La baja puede eliminar la clave del conjunto
                        presentKeys.remove(key);
                    }
                    break;
            }

            ops.add(new InventoryOperation(type, key, quantity, newItem));
        }

        return ops;
    }
}