package assignment02;

import java.util.ArrayList;
import java.util.Random;

public class puzzle {
    // this class defines the puzzle object and all associated attributes and methods

    // attributes

    private int[][] puzzleState = {
            {0, 0, 0},
            {0, 0, 0},
            {0, 0, 0}
    };

    // define solutionState as a 2D array of integers with zero denoting the blank space
    private int[][] solutionState = {
            {1, 2, 3},
            {8, 0, 4},
            {7, 6, 5}
    };

    // define solutionState as a collection of 2D coordinates for use in heuristic calculations
    private int[] one = {0,0};
    private int[] two = {1,0};
    private int[] three = {2,0};
    private int[] four = {2,1};
    private int[] five = {2,2};
    private int[] six = {1,2};
    private int[] seven = {0,2};
    private int[] eight = {0,1};


    // constructors

    puzzle() {
        // null constructor, creates a random puzzleState
        this.setRandomState();
    }

    puzzle(int[][] inputState) {
        // constructor that takes a puzzle state as input
        this.puzzleState = inputState;
    }

    // public methods
    public void setRandomState() {
        // this method generates a random state of integers, ensuring none are repeated and all lie within limits
        // create an array of all tile values
        ArrayList<Integer> tileList = new ArrayList<>();
        Random rand = new Random();
        for (int index = 0; index <= 8; index++) {
            tileList.add(index);
        }
        // set the cells of the initial state in a random fashion, removing values that have already been
        int xCoordinate = 0;
        int yCoordinate = 0;
        while (!(tileList.isEmpty())) {
            if (tileList.size() == 1) {
                setCellValue(tileList.get(0), 2, 2);
                tileList.remove(0);
            } else {
                int workingIndex = rand.nextInt(tileList.size() - 1);
                int result = tileList.get(workingIndex);
                setCellValue(result, xCoordinate, yCoordinate);
                tileList.remove(workingIndex);
                xCoordinate++;
                if (xCoordinate % 3 == 0) {
                    yCoordinate++;
                    xCoordinate = 0;
                }
            }
        }

    }

    public int[][] getPuzzleState() {
        // returns the current state of the puzzle as a 2D integer array
        return this.puzzleState;
    }

    public void setPuzzleState(int[][] puzzleState) {
        // permits the setting of a puzzle state as a whole unit
        this.puzzleState = puzzleState;
    }

    public int getCellValue(int x, int y) {
        // this method returns the value in a given cell as specified by the input array of (x,y) coordinates
        return this.puzzleState[y][x];
    }

    public void setCellValue(int inputValue, int x, int y) {
        // this method takes a value and (x,y) coordinates and sets the specified cell to that value
        this.puzzleState[y][x] = inputValue;
    }

    public String[] getPossibleMoves() {
        // this method creates a string array of possible moves = {'up','down','left','right'}
        String[] possibleMoves = {"","","",""};

        // find the blank square location (denoted by a zero)
        int[] blankLocation = findCellLocation(0);

        // determine if the "up" move is possible
        if (blankLocation[1] == 2 || blankLocation[1] == 3) {
            // location is on second or third row, thus an "up" move is possible
            possibleMoves[0] = "up";
        }
        // determine if "down" move is possible
        if (blankLocation[1] == 1 || blankLocation[1] == 2) {
            // location is on first and second row, thus a "down" move is possible
            possibleMoves[1] = "down";
        }
        // determine if the "left" move is possible
        if (blankLocation[0] == 3 || blankLocation[0] == 2) {
            // location is second or third column
            possibleMoves[2] = "left";
        }
        // determine if the "right" (direction) move is possible
        if (blankLocation[0] == 1 || blankLocation[0] == 2) {
            // location is on first or second column, thus a "right" (direction) is possible
            possibleMoves[3] = "right";
        }
        return possibleMoves;
    }

    public int[] findCellLocation(int inputValue) {
        // takes an integer as input and returns the location of the element in the puzzle. Will return (-1,-1) if not
        // found within the puzzle
        int[] outputCoordinates = {-1,-1};
        for (int yIndex = 0; yIndex < 3; yIndex++) {
            for (int xIndex = 0; xIndex < 3; xIndex++) {
                if (puzzleState[xIndex][yIndex] == inputValue) {
                    outputCoordinates[0] = xIndex;
                    outputCoordinates[1] = yIndex;
                    return outputCoordinates;
                }
            }
        }
        return outputCoordinates;
    }

    public int currentHeuristic() {
        // returns the heuristic value for the current state. The heuristic used is the summation of moves for each
        // cell to move independently via a Manhattan Distance from its current location to the goal location
        int totalValue = 0;

        return totalValue;
    }

    public int[] nextHeuristics() {
        // this method takes the current state, and performs all possible moves (up, down, left, right) and compiles an
        // array of values for each. If a move is not possible, the value is -1.
        int[] heuristics = {-1,-1,-1,-1};

        return heuristics;
    }

    public void moveUp() {
        // this method finds the blank square, and switches it with the square above it. It checks whether the cell can
        // actually be moved up. If it cannot, no action is performed and the console reports no move was made.
    }

    public void moveDown() {
        // this method finds the blank square, and switches it with the square below it. It checks whether the cell can
        // actually be moved down. If it cannot, no action is performed and the console reports no move was made.
    }

    public void moveLeft() {
        // this method finds the blank square, and switches it with the square to the left. It checks whether the cell
        // can actually be moved. If it cannot, no action is performed and the console reports no move was made.
    }

    public void moveRight() {
        // this method finds the blank square, and switches it with the square to the right. It checks whether the cell
        // can actually be moved. If it cannot, no action is performed and the console reports no move was made.
    }
}
