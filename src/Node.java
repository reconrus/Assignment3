/**
 *
 * @author Vyacheslav Yastrebov
 */
public class Node <K> {

    K key;
    Node<K> left;
    Node<K> right;
    Node<K> parent;
    int height;


    Node(K key, Node parent){
        this.parent = parent;
        this.key = key;
        this.left = null;
        this.right = null;
        this.height = 0;
    }
}
