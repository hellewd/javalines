import edu.princeton.cs.algs4.StopwatchCPU;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.Out;
import java.util.ArrayList;

public class Experiment {

    // ── Contadores de una ejecución

    private static int purchaseTotal;
    private static int queryTotal;
    private static int lendTotal;
    private static int receiveTotal;
    private static int disposeTotal;

    private static int querySuccessful;
    private static int queryFailed;
    private static int lendSuccessful;
    private static int lendFailed;
    private static int receiveSuccessful;
    private static int receiveFailed;

    public static void main(String[] args) {

        // t ∈ {12,13,...,19}  →  m = 2^t
        int[] sizes = {4096, 8192, 16384, 32768, 65536, 131072, 262144, 524288};

        for (int m : sizes) {
            System.out.println("Ejecutando experimento para m = " + m + " ...");
            runExperiment(m);
        }
    }


    private static void runExperiment(int m) {

        int keyUniverse = 4 * m;

       Out csv = new Out("inventory_experiment_" + m + ".csv");
        csv.println("instancia,estructura,m,"
                  + "purchase_total,query_total,lend_total,receive_total,dispose_total,"
                  + "query_successful,query_failed,"
                  + "lend_successful,lend_failed,"
                  + "receive_successful,receive_failed,"
                  + "final_size,final_height,elapsed_seconds");

        for (int i = 0; i < 30; i++) {
            long seed = (long) m + i;

            // Generar operaciones una vez; 
            ArrayList<InventoryOperation> ops =
                DataGenerator.generateOperations(m, keyUniverse, seed);

            // BST 
            BSTInventoryIndex bst = new BSTInventoryIndex();
            resetCounters();
            StopwatchCPU timerBST = new StopwatchCPU();
            executeAll(bst, ops);
            double tBST = timerBST.elapsedTime();

            csv.println(i + ",BST," + m + ","
                + purchaseTotal + "," + queryTotal + "," + lendTotal + ","
                + receiveTotal + "," + disposeTotal + ","
                + querySuccessful + "," + queryFailed + ","
                + lendSuccessful + "," + lendFailed + ","
                + receiveSuccessful + "," + receiveFailed + ","
                + bst.size() + "," + bst.height() + "," + tBST);

            int bstFinalSize = bst.size();

            // RedBlackBST
            RedBlackInventoryIndex rb = new RedBlackInventoryIndex();
            resetCounters();
            StopwatchCPU timerRB = new StopwatchCPU();
            executeAll(rb, ops);
            double tRB = timerRB.elapsedTime();

             csv.println(i + ",RedBlackBST," + m + ","
                + purchaseTotal + "," + queryTotal + "," + lendTotal + ","
                + receiveTotal + "," + disposeTotal + ","
                + querySuccessful + "," + queryFailed + ","
                + lendSuccessful + "," + lendFailed + ","
                + receiveSuccessful + "," + receiveFailed + ","
                + rb.size() + "," + rb.height() + "," + tRB);

            // Validación
            validate(i, m, bst, rb, ops, bstFinalSize);
        }

        csv.close();
    }

    //Ejecuta todas las operaciones sobre un índice

    private static void executeAll(InventoryIndex index,
                                   ArrayList<InventoryOperation> ops) {
        for (InventoryOperation op : ops) {
            switch (op.getType()) {
                case PURCHASE: executePurchase(index, op); break;
                case QUERY:    executeQuery   (index, op); break;
                case LEND:     executeLend    (index, op); break;
                case RECEIVE:  executeReceive (index, op); break;
                case DISPOSE:  executeDispose (index, op); break;
            }
        }
    }

    //Operaciones individuales

    private static void executePurchase(InventoryIndex index, InventoryOperation op) {
        purchaseTotal++;
        if (op.getItem() != null) {
            // Componente nuevo
            index.put(op.getKey(), op.getItem());
        } else {
            // Componente existente: agregar stock
            InventoryItem existing = index.get(op.getKey());
            if (existing != null) {
                existing.addStock(op.getQuantity());
                index.put(op.getKey(), existing);
            }
            // Si no existe (fue dado de baja antes), lo ignoramos
        }
    }

    private static void executeQuery(InventoryIndex index, InventoryOperation op) {
        queryTotal++;
        InventoryItem item = index.get(op.getKey());
        if (item != null) querySuccessful++;
        else              queryFailed++;
    }

    private static void executeLend(InventoryIndex index, InventoryOperation op) {
        lendTotal++;
        InventoryItem item = index.get(op.getKey());
        if (item != null && item.lend(op.getQuantity())) {
            index.put(op.getKey(), item);
            lendSuccessful++;
        } else {
            lendFailed++;
        }
    }

    private static void executeReceive(InventoryIndex index, InventoryOperation op) {
        receiveTotal++;
        InventoryItem item = index.get(op.getKey());
        if (item != null && item.receive(op.getQuantity())) {
            index.put(op.getKey(), item);
            receiveSuccessful++;
        } else {
            receiveFailed++;
        }
    }

    private static void executeDispose(InventoryIndex index, InventoryOperation op) {
        disposeTotal++;
        index.delete(op.getKey());
    }

    // Utilidades

    private static void resetCounters() {
        purchaseTotal = queryTotal = lendTotal = receiveTotal = disposeTotal = 0;
        querySuccessful = queryFailed = 0;
        lendSuccessful  = lendFailed  = 0;
        receiveSuccessful = receiveFailed = 0;
    }
    
    //Validación

    //ejecuta las validaciones del enunciado, devulve true si hay algun error
    private static void validate(int instancia, int m,
                                  BSTInventoryIndex bst,
                                  RedBlackInventoryIndex rb,
                                  ArrayList<InventoryOperation> ops,
                                  int bstFinalSize) {
        boolean ok = true;

        // Tamaño final igual en BST y RedBlackBST
        if (bst.size() != rb.size()) {
            System.err.println("[Validación 1 FALLA] instancia=" + instancia
                + " m=" + m + " BST.size=" + bst.size() + " RB.size=" + rb.size());
            ok = false;
        }

        // Muestra de 100 claves aleatorias, mismo resultado en get
        StdRandom.setSeed((long) m + instancia + 999999L);
        for (int k = 0; k < 100; k++) {
            int key = StdRandom.uniformInt(1, 4 * m + 1);
            InventoryItem bstItem = bst.get(key);
            InventoryItem rbItem  = rb.get(key);
            boolean bstHas = (bstItem != null);
            boolean rbHas  = (rbItem  != null);
            if (bstHas != rbHas) {
                System.err.println("[Validación 2 FALLA] instancia=" + instancia
                    + " key=" + key + " BST=" + bstHas + " RB=" + rbHas);
                ok = false;
                break;
            }
        }

        // Número de operaciones ejecutadas == m
        if (ops.size() != m) {
            System.err.println("[Validación 3 FALLA] instancia=" + instancia
                + " ops.size=" + ops.size() + " m=" + m);
            ok = false;
        }

        // No stock negativo, stockAvailable + stockOnLoan == stockTotal
        for (Integer key : rb.keys()) {
            InventoryItem item = rb.get(key);
            if (item == null) continue;
            if (!item.isConsistent()) {
                System.err.println("[Validación 4 y 5 FALLA] instancia=" + instancia
                    + " key=" + key + " " + item);
                ok = false;
                break;
            }
        }

        

        if (ok) {
            // está todo bien
        }
    }
}