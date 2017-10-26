/**
 * @author Vyacheslav Yastrebov
 */
public class AVL<K extends Comparable<K>> extends BST {


    AVL(){
        super();

    }

    @Override
    public void insert(K k){
        super.insert(k);
        balance(search(k));
    }


    private void balance(Node<K> node){

        int difference = node.left.height - node.right.height;

        if(difference == 0 || difference == -1 || difference == 1) {
            if (node.parent == null) return;
            balance(node.parent);
        }

        //Right subtree is higher
        if(difference == -2){

            //Left subtree of right subtree is higher than left one
            if(node.right.left.height - node.right.right.height > 0){

                Node<K> z = node;
                Node<K> y = node.right;
                Node<K> x = node.right.left;

                z.right = x;
                x.parent = z;

                y.left = x.right;
                x.right.parent = y;

                x.right = y;
                y.parent = x;
            }

            Node<K> z = node;
            Node<K> y = node.right;
            Node<K> x = node.right.right;

            y.parent = z.parent;
            z.parent = y;

            y.left.parent = z;
            z.right = y.left.parent;

            y.left = z;
        }
        //Left subtree is higher
        else{

        }





    }



}
