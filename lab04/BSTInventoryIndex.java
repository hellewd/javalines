import edu.princeton.cs.algs4.BST;

public class BSTInventoryIndex implements InventoryIndex {

    private BST<Integer, InventoryItem> st;

    public BSTInventoryIndex() {
        st = new BST<>();
    }

    public void put(Integer key, InventoryItem value) {
        st.put(key, value);
    }

    public InventoryItem get(Integer key) {
        return st.get(key);
    }

    public void delete(Integer key) {
        st.delete(key);
    }

    public boolean contains(Integer key) {
        return st.contains(key);
    }

    public Iterable<Integer> keys() {
        return st.keys();
    }

    public int size() {
        return st.size();
    }

    public int height() {
        return st.height();
    }

    @Override
    public String toString() {
        return "BSTInventoryIndex";
    }
}
