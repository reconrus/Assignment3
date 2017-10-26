/**
 * Declares methods for Binary Search Tree
 * @author Vyacheslav Yastrebov
 *
 */
public interface InterfaceBST <K extends Comparable<K>> {

    public K find (K key);
    public void insert (K key);
    public boolean remove (K key);
    public String traverse();
    public String print();

}
