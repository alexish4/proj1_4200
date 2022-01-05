import java.io.File;
import java.io.FileNotFoundException; //should check 1 to 8 for matrix
import java.util.Scanner;
import java.awt.FileDialog;
import java.awt.Frame;
import java.util.Arrays;
//import java.util.PriorityQueue;
import java.util.HashSet;

public class AStarDemo {
    public static void main (String [] args) throws FileNotFoundException {
        AStarDemo demo = new AStarDemo();
        boolean heuristicChoice = true;
        //int [][] goalState = { {0, 1, 2}, {3, 4, 5}, {6, 7, 8} };
        int [][] demoArray = demo.readFile(3, 3);
        AStar nodeDemo = new AStar();
        int [] adjacency = nodeDemo.findNeighbors(demoArray);
        System.out.print("h1 or h2? ");
        // Scanner keyboard = new Scanner(System.in);
        // String heuristicInput = keyboard.nextLine();
        // keyboard.close();

        // if(heuristicInput.equals("h2")) {
        //     heuristicChoice = false;
        // }

        if(nodeDemo.checkIfSolvable(demoArray)) {
            HashSet<String> hashDemo = new HashSet<String>();
            //System.out.println(Arrays.deepToString(demoArray));
            nodeDemo.aStar(demoArray, adjacency, hashDemo);
        }
        else
            System.out.println("Not solvable odd number of inversions.");
        
    }
    public int[][] readFile(int ROWS, int COLUMNS) throws FileNotFoundException {
        int[][] numArray = new int[ROWS][COLUMNS];
        Scanner sc = new Scanner(choseTextFile());
        while (sc.hasNextLine()) {
            for (int i = 0; i < numArray.length; i++) {
                String[] line = sc.nextLine().trim().split(" ");
                for (int j = 0; j < line.length; j++) {
                    numArray[i][j] = Integer.parseInt(line[j]);
                }
            }
        }
        //System.out.println(Arrays.deepToString(numArray));
        return numArray;
    }

    private static File choseTextFile() {
        FileDialog dialog = new FileDialog((Frame) null, "Select File To Open");
        dialog.setMode(FileDialog.LOAD);
        dialog.setVisible(true);
        File[] file = dialog.getFiles();
        return file[0];
    }

}
