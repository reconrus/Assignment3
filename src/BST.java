import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Implementation of Binary Search Tree
 * @author Vyacheslav Yastrebov
 * @param <K>
 */
public class BST <K extends Comparable<K>> implements InterfaceBST<K> {
    //The root node of a tree
    private Node<K> root;
    //Number of nodes
    private int size;

    BST (){
        root = null;
        size = 0;
    }

    /**
     * Checks whether tree contains a node with a key k .
     * If so, returns its key, null otherwise
     * @param k key of the node
     * @return key if the tree consists sought node, null otherwise
     */
    @Override
    public K find(K k) {
        Node<K> node = search(k);
        if(node==null) return null;
        return node.key;
    }

    /**
     * Looks for a node with a key k. If found, returns a reference to this node, null otherwise
     * @param k key of the node
     * @return a reference to the sought node if the tree consists it, null otherwise
     */
    protected Node<K> search(K k) {
        Node<K> node = root;

        while((node.left!=null || node.right!=null) && node.key != k) {
            if (k.compareTo(node.key) == -1) node = node.left;
            if (k.compareTo(node.key) == 1) node = node.right;
        }

        if(k!=node.key) return null;
        return node;
    }

    /**
     * Adds a node with a key k to the tree
     * @param k key of the node
     */
    @Override
    public void insert(K k) {
        if(root == null){
            root = new Node<>(k, null);
            updateHeight(root);
            return;
        }

        Node<K> node = root;

        size++;

        while(node.key != k) {

            int compare = k.compareTo(node.key);

            if (compare == 0) return;

            if (compare == 1)
                if (node.right == null) {
                    node.right = new Node<K>(k, node);
                } else node = node.right;

            if (compare == -1)
                if (node.left == null) {
                    node.left = new Node<K>(k, node);
                } else node = node.left;
        }

        updateHeight(search(k));
    }


    /**
     * Removes a node with a key k from the tree.
     * If node is not in the tree, returns false, true otherwise.
     * @param key key of the node
     * @return true if the node is removed from the tree, null otherwise.
     */
    @Override
    public boolean remove(K key) {
        Node<K> node = search(key);

        boolean isItRoot = node == root;

        //First case: the node with key k is not in the tree
        if(node == null) return false;

        //Second case: the node has no children (a leaf)
        if(node.right == null && node.left == null){
            if(isItRoot){
                root = null;
                return true;
            }
            if(node.key.compareTo(node.parent.key) == -1) node.parent.left = null;
            else node.parent.right = null;

            updateHeight(node);

            return true;
        }

        //Third case: the node has only right child
        if(node.left == null) {

            if(isItRoot){
                root = node.right;
                root.parent = null;
                updateHeight(root);
                return true;
            }

            node.right.parent = node.parent;
            if(node.key.compareTo(node.parent.key) == -1 )node.parent.left = node.right;
            else node.parent.right = node.right;

            updateHeight(node);

            return true;
        }

        //Fourth case: the node has only left child
        if(node.right == null){

            if(isItRoot){
                root = node.left;
                root.parent = null;
                updateHeight(root);
                return true;
            }

            node.left.parent = node.parent;

            if(node.key.compareTo(node.parent.key) == -1 )node.parent.left = node.left;
            else node.parent.right = node.left;

            updateHeight(node);

            return true;
        }


        //Fifth case: the node has two children
        //Find a leftmost node of right subtree and swap it with removable node
        Node<K> substitution = node.right;

        while(substitution.left!=null || substitution.right!=null){

            if(substitution.left != null) {
                substitution = substitution.left;
                continue;
            }

            if(substitution.right != null){
                substitution = substitution.right;
            }
        }

        substitution.height = 0;
        updateHeight(substitution);

        if(isItRoot){
            if(substitution.key.compareTo(substitution.parent.key) == -1) substitution.parent.left = null;
            else substitution.parent.right = null;
            substitution.parent = null;

            substitution.left= root.left;
            substitution.right = root.right;

            root = substitution;
        }
        else{

            if(substitution.key.compareTo(substitution.parent.key) == -1) substitution.parent.left = null;
            else substitution.parent.right = null;

            if(node.key.compareTo(node.parent.key) == -1) node.parent.left = substitution;
            else node.parent.right = substitution;

            substitution.parent = node.parent;
            substitution.left = node.left;
            substitution.right = node.right;

        }

        updateHeight(substitution);

        return true;
    }


    /**
     * Implements in-order Binary Search Tree traversal strategy and
     * returns a string with a keys of nodes.
     * @return string with a keys
     */
    @Override
    public String traverse() {

        StringBuilder str = new StringBuilder();
        Node<K> node = root;
        ArrayDeque<Node> output = new ArrayDeque<>(size);
        new Object(){
            private void traverseTree(Node<K> node){
                if(node.left!=null) traverseTree(node.left);
                output.add(node);
                if(node.right!=null) traverseTree(node.right);
            }
        }.traverseTree(root);

        while(output.size()!=0){
            str.append(output.pop().key.toString() + " ");
        }
        return str.toString();
    }


    /**
     * Returns a string of nodes of the tree in such order: Parent - left child - right child
     * @return a string of nodes of the tree
     */
    @Override
    public String print() {
        StringBuilder str = new StringBuilder();

        Queue<Node<K>> queue = new LinkedList<>();

        if(root == null) return null;

        queue.add(root);
        while(!queue.isEmpty()){

            Node<K> node = queue.remove();

            if(node.left == null && node.right == null){
                continue;
            }

            if(node!=root) str.append("\n");

            str.append(node.key + " ");

            if(node.left != null){
                str.append(node.left.key + " ");
                queue.add(node.left);
            }
            if(node.right != null){
                str.append(node.right.key + " ");
                queue.add(node.right);
            }
        }

        return str.toString();
    }


    /**
     * Returns a string of nodes of the mirrored version of the tree.
     * @return a string of nodes of the mirrored version of the tree
     */
    public String mirror() {
        StringBuilder str = new StringBuilder();

        Queue<Node<K>> queue = new LinkedList<>();

        if(root == null) return null;

        queue.add(root);
        while(!queue.isEmpty()){

            Node<K> node = queue.remove();

            if(node.left == null && node.right == null){
                continue;
            }

            if(node!=root) str.append("\n");

            str.append(node.key + " ");

            if(node.right != null){
                str.append(node.right.key + " ");
                queue.add(node.right);
            }

            if(node.left != null){
                str.append(node.left.key + " ");
                queue.add(node.left);
            }

        }

        return str.toString();
    }

    private void updateHeight(Node<K> node){

        if(node.right == null)
            if(node.left == null)
                node.height = 1;
            else node.height = node.left.height + 1;

        else if(node.left == null) node.height = node.right.height + 1;
             else node.height = max(node.left.height, node.right.height) + 1;

        if(node.parent != null) updateHeight(node.parent);

    }


    private int max(int a, int b){
        if(a>=b) return a;
        else return b;
    }


}
