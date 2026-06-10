public class InventoryOperation {

    private OperationType type;
    private int key;
    private int quantity;
    private InventoryItem item;   // solo se usa en PURCHASE de componente nuevo  -revisar esto

    public InventoryOperation(
            OperationType type,
            int key,
            int quantity,
            InventoryItem item){
        this.type     = type;
        this.key      = key;
        this.quantity = quantity;
        this.item     = item;
    }

    // Getters

    public OperationType getType(){ return type; }
    public int getKey(){ return key; }
    public int getQuantity(){ return quantity; }
    public InventoryItem getItem(){ return item; }
}
