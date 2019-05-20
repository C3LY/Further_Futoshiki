/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg198893part2;

import java.io.*;
import java.sql.SQLOutput;
import java.util.*;
import java.util.concurrent.TimeUnit;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

/**
 * @author cy214
 */
public class FutoshikiPuzzle implements Serializable {

    private Random rand = new Random();
    private FutoshikiSquare[][] grid;
    private FutoshikiSquare[][] solvedPuzzle;
    public Stack<FutoshikiSquare[][]> saveState = new Stack<FutoshikiSquare[][]>();
    private Constraint[][] rowConstraints;
    private Constraint[][] colConstraints;
    private int size;

    /**
     * Constructor
     *
     * @param size of the grid
     */
    public FutoshikiPuzzle(int size) {
        this.size = size;
        grid = new FutoshikiSquare[size][size];
        solvedPuzzle = new FutoshikiSquare[size][size];
        rowConstraints = new Constraint[size][size - 1];//row first, then column
        colConstraints = new Constraint[size - 1][size];//row first, then column

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                grid[i][j] = new FutoshikiSquare(true, 0);
            }
        }
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size - 1; j++) {
                rowConstraints[i][j] = new EmptyConstraint(null, null, true);
            }
        }
        for (int i = 0; i < size - 1; i++) {
            for (int j = 0; j < size; j++) {
                colConstraints[i][j] = new EmptyConstraint(null, null, false);

            }
        }

    }

    /**
     * Clear the whole board
     */
    public void clear() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (grid[i][j].isEditable()) {
                    grid[i][j].setData(0);
                }
            }
        }
    }

    /**
     * Empty a certain square
     *
     * @param x - row it will be placed -x
     * @param y - column it will be placed, set constraint below square - y
     * @return true if square can be emptied
     */
    public boolean empty(int x, int y) {

        if (grid[x][y].isEditable()) {
            grid[x][y].setData(0);
            grid[x][y].clearMarked();
            return true;
        } else {
            System.out.println("Not editable");
            return false;
        }
    }

    /**
     * Check if the whole grid is filled and isLegal - if the puzzle if complete
     *
     * @return true if the puzzle is complete, false otherwise
     */
    public boolean isPuzzleComplete() {
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (grid[row][col].getData() == 0) {
                    return false;
                }
            }
        }
        return isLegal();
    }

    /**
     * Method for putting a number into the grid
     *
     * @param value you want to set in
     * @param x - int - row it will be placed
     * @param y - int - column it will be placed, set constraint below square
     */
    public void setSquare(int value, int x, int y) {
        if ((x < size) && (y < size) && value <= size) { //&& (x >= 0 && y >= 0) && (value > 0
            if (grid[x][y].isEditable()) {
                grid[x][y].setData(value);
            } else {
                System.out.println(" at " + x + "," + y + " This box cannot be edited, it already contains " + grid[x][y].getData() + "edtitable is set at " + grid[x][y].isEditable());
            }
        } //        else if (!(value > 0 && value <= size)) {
        //            System.out.println("You are trying to insert a invalid value in the square grid " + x + "," + y);
        //        }
        else {
            System.out.println("You are trying to insert an item outside the square grid " + x + "," + y);
        }
    }

    /**
     * Get the value from a square in the grid
     *
     * @param x - int row it is in
     * @param y - int column it is in, set constraint below square
     * @return int value in the grid
     */
    public int getSquare(int x, int y) {
        if ((x < size) && (y < size) && (x >= 0 && y >= 0)) {
            return (grid[x][y].getData());
        } else {
            System.out.println("You are find an item outside the square grid with " + x + "," + y);
            return (0);
        }
    }

    /**
     * Return FutoshikiSquare instead of getSquare integer return
     *
     * @param x - int row
     * @param y - int column
     * @return FutoshikiSquare
     */
    public FutoshikiSquare getGridFutoSquare(int x, int y) {
        return grid[x][y];
    }

    /**
     * Set a row constraint
     *
     * @param string - symbol
     * @param x - int row it will be placed
     * @param y - int column it will be placed
     */
    public void setRowConstraint(String string, int x, int y) {
        if ((x < size) && (y < size - 1) && (x >= 0 && y >= 0)) {
            if (string.equals("<")) {
                rowConstraints[x][y] = new LessThan(grid[x][y], grid[x][y + 1], true);
            } else if (string.equals(">")) {
                rowConstraints[x][y] = new GreaterThan(grid[x][y], grid[x][y + 1], true);
            } else {
                System.out.println("Your input is not a constraint");
            }
        } else {
            System.out.println("You are trying to insert outside the Row grid with " + x + "," + y);
        }
    }

    /**
     * Get a row constraint
     *
     * @param x - int row it will be placed , set constraint to the right of
     * square
     * @param y - int column it will be placed
     * @return String - row constraint
     */
    public String getRowConstraint(int x, int y) {
        if ((x < size) && (y < size - 1) && (x >= 0 && y >= 0)) {
            return (rowConstraints[x][y].getConstraintData());
        } else {
            System.out.println("You are find an item outside the Row grid with " + x + "," + y);
            return (" ");
        }
    }

    /**
     * Set a col constraint
     *
     * @param string - String - symbol
     * @param x - int row it will be placed
     * @param y - int column it will be placed, set constraint below square
     */
    public void setColConstraint(String string, int x, int y) {
        if ((x < size - 1) && (y < size) && (x >= 0 && y >= 0)) {
            if (string.equals("^")) {
                colConstraints[x][y] = new LessThan(grid[x][y], grid[x + 1][y], false);
            } else if (string.equals("v")) {
                colConstraints[x][y] = new GreaterThan(grid[x][y], grid[x + 1][y], false);
            } else {
                System.out.println("Your input is not a constraint");
            }
        } else {
            System.out.println("You are trying to insert outside the Col grid with " + x + "," + y);
        }
    }

    /**
     * Get a col constraint
     *
     * @param x - int row it will be placed
     * @param y - int - column it will be placed, set constraint below square
     * @return String col constraint symbol
     */
    public String getColConstraint(int x, int y) {
        if ((x < size - 1) && (y < size) && (x >= 0 && y >= 0)) {
            return (colConstraints[x][y].getConstraintData());
        } else {
            System.out.println("You are find an item outside the Column grid with " + x + "," + y);
            return (" ");
        }
    }

    /**
     * Display the grid
     *
     * @return String - grid representation with numbers and constraints
     */
    public String displayString() {
        StringBuilder sb = new StringBuilder();
        for (int r = 0; r < size; r++) {
            sb.append("\n");
            for (int i = 0; i < size; i++) { //tops of cells
                sb.append("----" + "  ");
            }
            sb.append("\n");

            for (int c = 0; c < size; c++) {
                if (grid[r][c].getData() == 0) {
                    sb.append("|" + "  " + "|");
                } else {
                    if (grid[r][c].getData() < 10) {
                        sb.append("|" + " " + grid[r][c].getData() + "|");
                    } else {
                        sb.append("|" + grid[r][c].getData() + "|");
                    }
                }
                sb.append(" ");
                if (c < size - 1) {
                    sb.append(rowConstraints[r][c].getConstraintData());

                } else {
                    sb.append(" ");
                }
            }
            sb.append("\n");

            for (int j = 0; j < size; j++) { //bottoms of cells
                sb.append("----" + "  ");
            }

            sb.append("\n");
            for (int k = 0; k < size; k++) {
                if (r < size - 1) {
                    sb.append("  " + colConstraints[r][k].getConstraintData() + "   ");

                }
            }
        }
        return (sb.toString());
    }

    /**
     * Checks if the puzzle is legal
     *
     * @return Boolean return true if the puzzle is legal, false otherwise
     */
    public boolean isLegal() {
        boolean valid = true;
        HashSet<String> tempHS = noRepeatsRow();
        if (!(tempHS.isEmpty())) {
            valid = false;
        }
        tempHS = noRepeatsCol();
        if (!(tempHS.isEmpty())) {
            valid = false;
        }
        tempHS = validRowConstraints();
        if (!(tempHS.isEmpty())) {
            valid = false;
        }
        tempHS = validColConstraints();
        if (!(tempHS.isEmpty())) {
            valid = false;
        }
        return valid;
    }
    
    /**
     * Checks if a square is legal
     * @param x
     * @param y
     * @return boolean , true if square is legal
     */
    public boolean isLegalSquare(int x,int y){
        boolean legal = true;
        
        int[] list = new int[size];
        for (int row = 0; row < size; row++) {
             list[row]=(grid[row][y].getData());  
             System.out.println(Arrays.toString(list));
        }
        list[x]=0;
        if(isRepeated(list,grid[x][y].getData())){
            System.out.println("isRepeat1");
            legal=false;
        }
        int[] list2 = new int[size];
        for (int col = 0; col < size; col++) {
             list2[col]=(grid[x][col].getData());
             System.out.println(Arrays.toString(list2));
        }
        list2[y]=0;
        if(isRepeated(list2,grid[x][y].getData())){
            System.out.println("isRepeat2");
            legal=false;
        }
        
        if (x < size-1) {
            if (x != 0) {
                if (!colConstraints[x - 1][y].isLegal()) {
                    System.out.println("constrinat1");
                    legal = false;
                }
                if (!colConstraints[x][y].isLegal()) {
                    System.out.println("constrinat2");
                    legal = false;
                }
            } else {
                if (!colConstraints[x][y].isLegal()) {
                    System.out.println("constrinat3");
                    legal = false;
                }
            }
        }
        
        if (y < size-1) {
            if (y > 0) {
                if (!rowConstraints[x][y - 1].isLegal()) {
                    System.out.println("constrinat4");
                    legal = false;
                }
                if (!rowConstraints[x][y].isLegal()) {
                    System.out.println("constrinat5");
                    legal = false;
                }
            } else {
                if (!rowConstraints[x][y].isLegal()) {
                    System.out.println("constrinat6");
                    legal = false;
                }
            }
        }
        return legal;
    }

    /**
     * return a list of all the problems in the grid
     *
     * @return String - a list of all the problems with the grid
     */
    public String getProblems() {
        HashSet<String> allProblems = new HashSet<String>();
        allProblems.addAll(noRepeatsRow());
        allProblems.addAll(noRepeatsCol());
        allProblems.addAll(validRowConstraints());
        allProblems.addAll(validColConstraints());

        String problemsString = "";
        for (String prob : allProblems) {
            problemsString += prob + "\n";
        }
        // return (noRepeatsRow().toString() + "," + noRepeatsCol().toString() + "," + validRowConstraints().toString() + "," + validColConstraints().toString());
        return problemsString;
    }

    /**
     * checks if there is any repeated numbers in each row
     *
     * @return HashSet - returns a set of error descriptions if a row numbers
     * has been repeated
     */
    private HashSet<String> noRepeatsRow() {

        HashSet<String> RepeatRowProblems = new HashSet<String>();
        int[] currentRow = new int[size]; //keeps track of what is in the row
        boolean valid = true;

        for (int row = 0; row < size; row++) { //go through each row
            Arrays.fill(currentRow, -1); //refresh array so the previous row will not affect the comparisons
            for (int col = 0; col < size; col++) { //go through each column in the row
                if (isRepeated(currentRow, grid[row][col].getData()) && grid[row][col].getData() != 0) { //if the number has appeared before in the row then there is a repeat
                    RepeatRowProblems.add(grid[row][col].getData() + " is repeated along row" + row);
                    valid = false;
                }
                currentRow[col] = grid[row][col].getData();
            }
        }
        return RepeatRowProblems;
    }

    /**
     * checks if there is any repeated numbers in each column
     *
     * @return HashSet - returns a set of error descriptions if a column numbers
     * has been repeated
     */
    private HashSet<String> noRepeatsCol() {
        HashSet<String> RepeatColProblems = new HashSet<String>();

        int[] currentCol = new int[size];
        boolean valid = true;

        for (int row = 0; row < size; row++) {
            Arrays.fill(currentCol, -1);
            for (int col = 0; col < size; col++) {
                if (isRepeated(currentCol, grid[col][row].getData()) && grid[col][row].getData() != 0) {
                    RepeatColProblems.add(grid[col][row].getData() + " is repeated along col" + row);
                    valid = false;
                }
                currentCol[col] = grid[col][row].getData();
            }
        }
        return RepeatColProblems;
    }

    /**
     * checks to see if a certain number is already in the array
     *
     * @param currentArray - in[] array - list of numbers to check from
     * @param key - int - checking if this number is repeated
     * @return boolean true if it is repeated
     */
    private boolean isRepeated(int[] currentArray, int key) {
        boolean found = false;
        for (int i : currentArray) {
            if (i == key && i != 0) {
                found = true;
            }
        }
        return found;
    }

    /**
     * check if all the row constraints are valid
     *
     * @return HashSet<String> return set of all the problems with the row
     * constraints
     */
    private HashSet<String> validRowConstraints() {
        HashSet<String> RowConstriantProblems = new HashSet<String>();

        boolean valid = true;
        for (int row = 0; row < size - 1; row++) {
            for (int col = 0; col < size - 1; col++) {
                if (rowConstraints[row][col].getClass() == LessThan.class && (rowConstraints[row][col].getSquare1Data() == size || !(rowConstraints[row][col].isLegal()))) {
                    RowConstriantProblems.add("row constraint at " + row + "," + col + " is illegal with " + rowConstraints[row][col].getConstraintData());
                    valid = false;
                }
                if (rowConstraints[row][col].getClass() == GreaterThan.class && (rowConstraints[row][col].getSquare2Data() == size || !(rowConstraints[row][col].isLegal()))) {
                    valid = false;
                    RowConstriantProblems.add("row constraint at " + row + "," + col + " is illegal with " + rowConstraints[row][col].getConstraintData());
                }
            }
        }
        return RowConstriantProblems;
    }

    /**
     * check if all the col constraints are valid
     *
     * @return HashSet<String> return set of all the problems with the col
     * constriants
     */
    private HashSet<String> validColConstraints() {
        HashSet<String> ColConstraintProblems = new HashSet<String>();

        boolean valid = true;
        for (int col = 0; col < size - 1; col++) {
            for (int row = 0; row < size - 1; row++) {
                if (colConstraints[row][col].getClass() == LessThan.class && (colConstraints[row][col].getSquare1Data() == size || !(colConstraints[row][col].isLegal()))) {
                    ColConstraintProblems.add("row constraint at " + row + "," + col + " is illegal with " + colConstraints[row][col].getConstraintData());
                    valid = false;
                }
                if (colConstraints[row][col].getClass() == GreaterThan.class && (colConstraints[row][col].getSquare2Data() == size || !(colConstraints[row][col].isLegal()))) {
                    valid = false;
                    ColConstraintProblems.add("row constraint at " + row + "," + col + " is illegal with " + colConstraints[row][col].getConstraintData());
                }
            }
        }

        return ColConstraintProblems;
    }

    /**
     * adds a pencil mark into the square
     *
     * @param value you want to pencil mark in
     * @param x - int - row it will be placed
     * @param y - int - column it will be placed, set constraint below square
     */
    public void addPencilMark(int value, int x, int y) {
        if (x < size && y < size) {
            if (grid[x][y].isEditable()) {
                grid[x][y].addpencilMarked(value);
            }
        }
    }

    /**
     * Create a deep copy of the grid and store it in solvedPuzzle
     *
     * @param A - the grid
     */
    public FutoshikiSquare[][] DeepCopyGrid(FutoshikiSquare[][] A) {
        FutoshikiSquare[][] copy = new FutoshikiSquare[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                copy[i][j] = new FutoshikiSquare(true, 0);
            }
        }
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                copy[x][y].setData(A[x][y].getData());
            }
        }
        return copy;
    }

    /**
     * Fill the grid up with numbers and constraints
     *
     * @param difficulty - int - higher the number, lesser the constraints and
     * numbers
     */
    public void fillPuzzle(int difficulty) {
        grid[0][0].setData(rand.nextInt(size) + 1);

        solverSetup();

        solvedPuzzle = DeepCopyGrid(grid);

        int r = 0;
        int c = 0;
        boolean b;

        //put in constraints
        for (int j = 0; j < ((size * size) / difficulty); j++) {
            b = rand.nextBoolean();

            if (b) { //horizontal constraints
                //   do{ //this makes my program really slow 
                r = rand.nextInt(size);
                c = rand.nextInt(size - 1);
                //   }while(!(rowConstraints[r][c].getConstraintData().equals(" ")));
                if (grid[r][c].getData() < grid[r][c + 1].getData()) {
                    rowConstraints[r][c] = new LessThan(grid[r][c], grid[r][c + 1], true);
                } else {
                    rowConstraints[r][c] = new GreaterThan(grid[r][c], grid[r][c + 1], true);
                }
            } else {
                //  do{ //this makes my program really slow 
                r = rand.nextInt(size - 1);
                c = rand.nextInt(size);
                //  }while(!(colConstraints[r][c].getConstraintData().equals(" ")));

                if (grid[r][c].getData() < grid[r + 1][c].getData()) {
                    colConstraints[r][c] = new LessThan(grid[r][c], grid[r + 1][c], false);
                } else {
                    colConstraints[r][c] = new GreaterThan(grid[r][c], grid[r + 1][c], false);
                }
            }
        }

        //take out some numbers
        for (int i = 0; i < (difficulty * (size * 3) / (size)); i++) {
            //     do{ //this makes my program really slow 
            r = rand.nextInt(size);
            c = rand.nextInt(size);
            //   }while(grid[r][c].getData()==0);
            grid[r][c].setData(0);
        }

        //stop the user from editing all boxes
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (grid[row][col].getData() != 0) {
                    grid[row][col].setEditable(false);
                }
            }

        }

        grid = emptyMarkings(new TupleSolveParams(grid, 0, 0)).grid;

    }

    /**
     * method to setup and start the solver
     */
    public void solverSetup() {
        FutoshikiSquare[][] iniGrid = fillMarkings(new TupleSolveParams(grid, 0, 1)).grid;
        midSolver(new TupleSolveParams(iniGrid, 0, 1));

    }

    /**
     * loops solver method until complete (instead of making solving recursive)
     *
     * @param params - Contains the grid, row and col it should start on
     */
    public void midSolver(TupleSolveParams params) {
        saveState.push(grid);
        TupleSolveParams params2 = params;
        while (!isPuzzleComplete()) { // This is not recursive anymore so I could implement stepSolver feature
            //   System.out.println("cords " + params2.rowC + "," + params2.colC);
            params2 = solving(new TupleSolveParams(params2.grid, params2.rowC, params2.colC));
        }
    }

    /**
     * The main solver method using Depth First Search
     *
     * @param params - contains grid, row and column it should start from
     * @return TupleSolveParams - contains grid, row and column
     */
    public TupleSolveParams solving(TupleSolveParams params) {
        //   if(!isPuzzleComplete()) {
        if (!grid[params.rowC][params.colC].isEditable()) { //if it is not editable, means it shouldnt have any pencil marks apart from the number it is
            grid[params.rowC][params.colC].clearMarked();
            grid[params.rowC][params.colC].addpencilMarked(grid[params.rowC][params.colC].getData());
        }

        if (!(grid[params.rowC][params.colC].getpencilMarkedList().isEmpty())) { //if the square has any possible numbers
            grid[params.rowC][params.colC].setData(grid[params.rowC][params.colC].getpencilMarkedList().get(rand.nextInt(grid[params.rowC][params.colC].getpencilMarkedList().size()))); // !!IMPORTANT I found using an arraylist always ended with a similar puzzle. Instead of going in order from an arraylist (how dfs should be, going down the left most branch first), it is random to allow for a more randomized puzzle                 
            saveState.push(grid); //save the state of the puzzle in the stack

            if (params.colC == size - 1) { //move to the next square
                params.colC = 0;
                params.rowC += 1;
            } else {
                params.colC += 1;
            }
            grid = emptyMarkings(new TupleSolveParams(params.grid, params.rowC, params.colC)).grid; //refresh the markings

            grid = fillMarkings(new TupleSolveParams(params.grid, params.rowC, params.colC)).grid; //fill another grid with the markings
        } else {
            if (params.rowC == 0 && params.colC == 0) {
                grid[params.rowC][params.colC].deleteMarked(grid[params.rowC][params.colC].getData()); //delete the mark tried
                grid[params.rowC][params.colC].setData(grid[params.rowC][params.colC].getpencilMarkedList().get(rand.nextInt(grid[params.rowC][params.colC].getpencilMarkedList().size()))); // !!IMPORTANT I found using an arraylist always ended with a similar puzzle. Instead of going in order from an arraylist (how dfs should be, going down the left most branch first), it is random to allow for a more randomized puzzle                                    
            } else {
                grid = saveState.pop(); //there is no possible numbers for the square, go to previous state to change the number
                if (params.colC == 0) {
                    params.colC = size - 1;
                    params.rowC--;
                } else {
                    params.colC--;
                }

                while (!(grid[params.rowC][params.colC].isEditable())) { //if it is editable, skip
                    if (params.colC == 0) {
                        params.colC = size - 1;
                        params.rowC--;
                    } else {
                        params.colC--;
                    }
                }
                grid[params.rowC][params.colC].deleteMarked(grid[params.rowC][params.colC].getData()); //delete the mark tried
                if (grid[params.rowC][params.colC].getpencilMarkedList().isEmpty()) {
                    grid = saveState.pop();
                }
            }
        }
//solving(params)
        return params;
    }

    /**
     * fills all the markings in the grid with **possible numbers
     *
     **
     * @param params - grid, row and col to start from
     * @return params
     */
    public TupleSolveParams fillMarkings(TupleSolveParams params) { //start filling from certain square
        int colFrom = 0;
        for (int row = params.rowC; row < size; row++) {
            if (row == params.rowC) {
                colFrom = params.colC;
            } else {
                colFrom = 0;
            }
            for (int col = colFrom; col < size; col++) {
                if (grid[row][col].isEditable()) {
                    for (int i = 1; i <= size; i++) { //go through to see what numbers are possible
                        grid[row][col].setData(i);
                        if (isLegal()) {
                            params.grid[row][col].addpencilMarked(i);
                        }
                        grid[row][col].setData(0);
                    }
                }
            }
        }
        return params;
    }

    /**
     * clearing all the markings out
     *
     * @param params
     * @return params
     */
    public TupleSolveParams emptyMarkings(TupleSolveParams params) {
        int colFrom = 0;
        for (int row = params.rowC; row < size; row++) {
            if (row == params.rowC) {
                colFrom = params.colC;
            } else {
                colFrom = 0;
            }
            for (int col = colFrom; col < size; col++) {
                if (grid[row][col].isEditable()) {
                    grid[row][col].clearMarked();
                }
            }
        }
        return params;
    }

    /**
     * solvedPuzzle is copied to grid
     */
    public void solveIt() {
        System.out.println(displayString());
        grid = solvedPuzzle;
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                grid[row][col].setEditable(false);
            }
        }
        System.out.println(displayString());
    }

    /**
     * solve one of the squares in the grid
     */
    public void hint() {
        int r = rand.nextInt(size);
        int c = rand.nextInt(size);
        while (!grid[r][c].isEditable()) {
            r = rand.nextInt(size);
            c = rand.nextInt(size);
        }
        grid[r][c].setData(solvedPuzzle[r][c].getData());
        grid[r][c].setEditable(false);
    }

    /**
     * return the grid
     *
     * @return grid - FutoshikiSquare[][]
     */
    public FutoshikiSquare[][] getGrid() { //used only for testing
        return grid;
    }

    /**
     * set grid to the grid given
     *
     * @param grid
     */
    public void setGrid(FutoshikiSquare[][] grid) {
        this.grid = grid;
    }

}
