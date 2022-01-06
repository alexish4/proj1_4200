import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Stack;

public class AStar {
    public int [] findNeighbors(int [][] matrix) {
        int emptyTileRowIndex = 0;
        int emptyTileColumnIndex = 0;

        int [] adjacency = new int [4];

        for (int i = 0; i < matrix.length; i++) { //find where blank tile is
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix [i][j] == 0) {
                    emptyTileRowIndex = i; //remembering where the blank tile is
                    emptyTileColumnIndex = j;
                }
            }
        }

        if (emptyTileRowIndex == 0 && emptyTileColumnIndex == 0) { //a bunch of if statements to get neighbors
            adjacency [0] = matrix[0][1];
            adjacency[1] = matrix[1][0];
        }
        else if (emptyTileRowIndex == 0 && emptyTileColumnIndex == 1) {
            adjacency[0] = matrix [0][0];
            adjacency[1] = matrix [0][2];
            adjacency[2] = matrix [1][1];
        }
        else if (emptyTileRowIndex == 0 && emptyTileColumnIndex == 2) {
            adjacency [0] = matrix [0][1];
            adjacency[1] = matrix[1][2];
        }
        else if (emptyTileRowIndex == 1 && emptyTileColumnIndex == 0) {
            adjacency[0] = matrix [0][0];
            adjacency[1] = matrix [1][1];
            adjacency[2] = matrix [2][0];
        }
        else if (emptyTileRowIndex == 1 && emptyTileColumnIndex == 1) {
            adjacency[0] = matrix [0][1];
            adjacency[1] = matrix [1][0];
            adjacency[2] = matrix [1][2];
            adjacency[3] = matrix [2][1];
        }
        else if (emptyTileRowIndex == 1 && emptyTileColumnIndex == 2) {
            adjacency[0] = matrix [0][2];
            adjacency[1] = matrix [1][1];
            adjacency[2] = matrix [2][2];
        }
        else if (emptyTileRowIndex == 2 && emptyTileColumnIndex == 0) {
            adjacency[0] = matrix [1][0];
            adjacency[1] = matrix [2][1];
        }
        else if (emptyTileRowIndex == 2 && emptyTileColumnIndex == 1) {
            adjacency[0] = matrix [1][1];
            adjacency[1] = matrix [2][0];
            adjacency[2] = matrix [2][2];
        }
        else if (emptyTileRowIndex == 2 && emptyTileColumnIndex == 2) {
            adjacency[0] = matrix [1][2];
            adjacency[1] = matrix [2][1];
        }
        return adjacency;
    }
    public boolean checkIfSolvable(int [][] matrix) {
        boolean solvable = false;
        int numOfInversions = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] != 0 && matrix[i][j] != 1) {
                    for (int currentRowIndex = i; currentRowIndex < matrix.length; currentRowIndex++) {//check all rows after
                        boolean ifOnCurrentRow = true; //don't want to backtrack
                        for(int currentColumnIndex = 0; currentColumnIndex < matrix[0].length; currentColumnIndex++) {               
                            if (currentRowIndex == i && ifOnCurrentRow) {
                                currentColumnIndex = j;
                                ifOnCurrentRow = false;
                            }
                            if (matrix[currentRowIndex][currentColumnIndex] < matrix[i][j] 
                            && matrix[currentRowIndex][currentColumnIndex] != 0) { //Don't compare with 0
                                numOfInversions++;
                            }
                        }
                    }
                }
            }
        }
        System.out.println("The number of Inversions is " + numOfInversions);
        if(numOfInversions %2 == 0) //if even then true
            solvable = true;
        return solvable;
    }


    public class Node {
        int cost = 0;
        LinkedList<Node> edges = new LinkedList<Node>();
        int [][] configuration = new int [3][3];
        int [][] childConfiguration = new int [3][3];
        int gCost = 0;
        Node father = new Node();

        public int calculateFCost(boolean hInput) {
            int hCost = 0; //maybe not = to cost?
            int noOfMisplaced = 0;
            int [][] goalState = { {0, 1, 2}, {3, 4, 5}, {6, 7, 8} }; //going to check if we have reached the goal state
            if (hInput) { //if h1
                for (int i = 0; i < configuration.length; i++) {
                    for (int j = 0; j < configuration[0].length; j++) {
                        if (configuration[0][0] != 0) 
                            noOfMisplaced++;
                        else if (configuration[0][1] != 1)
                            noOfMisplaced++;
                        else if (configuration[0][2] != 2)
                            noOfMisplaced++;
                        else if (configuration[1][0] != 3)
                            noOfMisplaced++;
                        else if (configuration[1][1] != 4)
                            noOfMisplaced++;
                        else if (configuration[1][2] != 5) 
                            noOfMisplaced++;
                        else if (configuration[2][0] != 6)
                            noOfMisplaced++;
                        else if (configuration[2][1] != 7)
                            noOfMisplaced++;
                        else if (configuration[2][2] != 8)
                            noOfMisplaced++;
                    }
                }
                hCost = noOfMisplaced;
            }
            int h2Index = 0; //going in order
            int h2RowIndex = 0;
            int h2ColumnIndex = 0;
            int xCount = 0;
            int yCount = 0;
            int totalCount = 0;
            if (!hInput) { //if h2
                for (int i = 0; i < configuration.length; i++) {
                    for (int j = 0; j < configuration[0].length; j++) {
                        if (configuration[i][j] == h2Index) { //if misses this condition once then done?
                            // System.out.println(configuration[i][j] + " is the tile");
                            // System.out.println(h2Index + " is the compared tile");
                            while ( (i + xCount) < h2RowIndex) {
                                xCount++;
                                //System.out.println("increase x");
                            }
                            while ( (i - xCount) > h2RowIndex)  { //possible error is they were preceding before
                                xCount++;
                                //System.out.println("decrease x");
                            }
                            while ( (j + yCount) < h2ColumnIndex) { //thinking of opposite row is y and column is x
                                yCount++;
                                //System.out.println("increase y");
                            }
                            while ( (j - yCount) > h2ColumnIndex) {
                                yCount++;
                                //System.out.println("decrease y");
                            } //counted for one tile
                            if (h2Index < 8) { //loop again to calculate next tile in order
                                h2Index++;
                                i = 0;
                                j = -1;
                            }
                            // else { //end loop early
                            //     i = configuration.length - 1;
                            //     j = configuration[0].length - 1;
                            //     System.out.println("Test if ends early");
                            // }
                            if(h2ColumnIndex == 2 && h2RowIndex < 2) { //iterating through 2d array
                                h2RowIndex++;
                                h2ColumnIndex = -1;
                                //System.out.println("This is " + h2ColumnIndex);
                            }
                            h2ColumnIndex++;
                            //System.out.println("This is " + h2ColumnIndex);
                            totalCount += xCount + yCount;
                            if (Arrays.deepToString(configuration).equals(Arrays.deepToString(goalState))) //h = 0 when goal state
                                totalCount = 0;
                            xCount = 0;
                            yCount = 0;
                        }
                    }
                }
                hCost = totalCount;
            }
            return hCost + gCost; //add both
        }



        public void addEdges(int [][] matrix, int [] adjacency, HashSet <String> hashes, PriorityQueue<Node> frontier) {
            int emptyTileRowIndex = 0;
            int emptyTileColumnIndex = 0;
            int edgesIndex = 0;

            for (int i = 0; i < configuration.length; i++) { //find where blank tile is
                for (int j = 0; j < configuration[0].length; j++) {
                    if (configuration [i][j] == 0) {
                        emptyTileRowIndex = i; //remembering where the blank tile is
                        emptyTileColumnIndex = j;
                    }
                }
            }
            System.out.println("Adjacency is " + Arrays.toString(adjacency));

            //childConfiguration = new int [3][3];
            for (int i = 0; i < childConfiguration.length; i++) { //making a copy of configuration
                for (int j = 0; j < childConfiguration[0].length; j++) {
                    childConfiguration[i][j] = configuration[i][j];
                }
            }
            //childConfiguration = configuration.clone();

            int adjacencyIndex = 0;
            for(int i = 0; i < childConfiguration.length; i++) {
                for (int j = 0; j < childConfiguration[0].length; j++) {
                    if (childConfiguration[i][j] == adjacency[adjacencyIndex]) { //swapping tiles
                        //System.out.println(Arrays.deepToString(configuration) + " see if changes");
                        
                        childConfiguration[i][j] = 0;
                        childConfiguration[emptyTileRowIndex][emptyTileColumnIndex] = adjacency[adjacencyIndex];
                        
                        //System.out.println(Arrays.deepToString(configuration) + " the real test");
                        
                        
                        if (!hashes.contains(Arrays.deepToString(childConfiguration))) { //If no repeated add edges
                            hashes.add(Arrays.deepToString(childConfiguration));
                            edges.add(new Node());
                            edges.get(edgesIndex).gCost = gCost + 1; //g cost is depth aka parents g cost + 1
                            for (int row = 0; row < childConfiguration.length; row++) { //making a copy of configuration
                                for (int column = 0; column < childConfiguration[0].length; column++) {
                                    edges.get(edgesIndex).configuration[row][column] = childConfiguration[row][column];
                                }
                            }
                            edges.get(edgesIndex).cost = edges.get(edgesIndex).calculateFCost(false); //REMEMBER TO CHANGE!!!!!!
                            //System.out.println(Arrays.deepToString(edges.getFirst().configuration) + " testing edges");
                            //System.out.println(Arrays.deepToString(configuration) + " see if configuration changes");
                            frontier.add(edges.get(edgesIndex));
                            edgesIndex++;
                        }

                        if( adjacencyIndex < adjacency.length - 1 && adjacency[adjacencyIndex+1] != 0) //if still possible edges to add
                        {
                            i = 0;
                            j = -1;
                            adjacencyIndex++;
                            //System.out.println("Test if does");
                            for (int row = 0; row < childConfiguration.length; row++) { //making a copy of configuration
                                for (int column = 0; column < childConfiguration[0].length; column++) {
                                    childConfiguration[row][column] = configuration[row][column];
                                }
                            }
                        }
                        else {
                            i = childConfiguration.length - 1; //ending loop early
                            j = childConfiguration[0].length - 1;
                        }
                    } //end of swapping tiles if statement
                }
            }
            
        }
    } //end of nodes class
    public class NodeComparator implements Comparator<Node> {
        @Override
        public int compare(Node n1, Node n2) {
            if (n1.cost < n2.cost) 
                return -1;
            else if (n1.cost > n2.cost) 
                return 1;
            else 
                return 0;
        }
    }

    PriorityQueue<Node> frontier = new PriorityQueue<Node>(new NodeComparator());

    public void printPattern(int [][] matrix, int step) {
        System.out.println("Step: " + step);
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
        step++;
    }
    
    public void aStar(int [][] matrix, int [] adjacency, HashSet <String> hashes) {
        Node root = new Node();
        root.configuration = matrix.clone();
        int step = 1;
        printPattern(root.configuration, step);
        //NEED PATH!!!!!!!!!!!!


        int [][] goalState = { {0, 1, 2}, {3, 4, 5}, {6, 7, 8} }; //going to check if we have reached the goal state

        root.addEdges(matrix, adjacency, hashes, frontier); //got the children of root
        
        //maybe make it a do while loop
        Node comparedNode = new Node(); //going to make it equal to the first node out of priority queue
        Iterator<Node> it = frontier.iterator();
        // while (it.hasNext())
        //     System.out.println();

        do {
            comparedNode = frontier.poll();
            //System.out.println(Arrays.deepToString(comparedNode.configuration) + " is the answer");
            printPattern(comparedNode.configuration, step);
            adjacency = findNeighbors(comparedNode.configuration);
            comparedNode.addEdges(matrix, adjacency, hashes, frontier);
        } while (!Arrays.deepToString(comparedNode.configuration).equals(Arrays.deepToString(goalState))); //keep doing until next expanded node is equal to goal state
        System.out.println(Arrays.deepToString(comparedNode.configuration) + " is the answer");
        System.out.println(comparedNode.cost + " this is the cost");

        // System.out.println(root.edges.getFirst().calculateFCost(true) + " is # of misplaced");
    }

}