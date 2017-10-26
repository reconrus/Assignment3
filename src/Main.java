import java.io.*;
import java.util.Scanner;

/**
 * @author Vyacheslav Yastrebov
 */
public class Main {
    public static void main(String[] args) throws IOException {

        Scanner sc = new Scanner(new FileReader("input.txt"));
        FileWriter fileWriter =  new FileWriter("output.txt");

        BST<Integer> bst = new BST<>();

        String str = sc.nextLine();

        String[] keys = str.split(" ");
        for(int i=0; i<keys.length; i++){
            bst.insert(Integer.valueOf(keys[i]));
        }

        fileWriter.write(bst.find(Integer.valueOf(sc.nextInt()))+ "\n");

        bst.remove(sc.nextInt());
        bst.insert(sc.nextInt());

        fileWriter.write(bst.traverse() + "\n");

        fileWriter.write("BST: \n");
        fileWriter.write(bst.print() + "\n");

        fileWriter.write("BSMT: \n");
        fileWriter.write(bst.mirror());

        fileWriter.close();
        sc.close();

    }
}
