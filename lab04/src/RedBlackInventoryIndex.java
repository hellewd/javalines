import edu.princeton.cs.algs4.RedBlackBST;

public class RedBlackInventoryIndex implements InventoryIndex {

    private RedBlackBST<Integer, InventoryItem> st;

    public RedBlackInventoryIndex() {
        st = new RedBlackBST<>();
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

    
    public String toString() {
        return "RedBlackBSTInventoryIndex";
    }
}
