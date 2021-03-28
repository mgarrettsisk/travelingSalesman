package assignment02;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
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
    private final int[][] solutionState = {
            {1, 2, 3},
            {8, 0, 4},
            {7, 6, 5}
    };

    // define solutionState as a collection of 2D coordinates for use in heuristic calculations
    private final int[] zero = {1,1};
    private final int[] one = {0,0};
    private final int[] two = {1,0};
    private final int[] three = {2,0};
    private final int[] four = {2,1};
    private final int[] five = {2,2};
    private final int[] six = {1,2};
    private final int[] seven = {0,2};
    private final int[] eight = {0,1};


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

    public LinkedList<String> solvePuzzle(double startingTemp, double decrement) {
        // this method uses simulated annealing to solve the puzzle from a given state. It returns an array list of
        // steps used to solve the problem
        System.out.println("Has entered the function solvePuzzle");
        LinkedList<String> solutionStack = new LinkedList<>();
        double currentTemp = startingTemp;
        int maxIterations = 10000;
        int iterations = 0;

        System.out.println("About to enter the while loop");
        while (!(Arrays.deepEquals(this.puzzleState,this.solutionState))&& iterations < maxIterations) {
            System.out.println("The current iteration is: " + iterations);
            String[] moves = getPossibleMoves();
            Random rNumber = new Random();
            int randomChoice = rNumber.nextInt(4) + 1;
            switch (randomChoice) {
                case 1:
                    if (moves[0].equalsIgnoreCase("up")) {
                        int currentValue = currentHeuristic();
                        moveUp();
                        int nextValue = currentHeuristic();
                        int delta = nextValue - currentValue;
                        if (delta < 0) {
                            // keep the move
                            solutionStack.push("Move Zero Up");
                        } else if (delta >= 0){
                            Random rand = new Random();
                            double randValue = rand.nextInt(100)/100.0;
                            double tempValue = Math.exp(-delta/startingTemp);
                            if (randValue < tempValue) {
                                // keep the move
                                solutionStack.push("Move Zero Up");
                            } else {
                                // reject the move
                                moveDown();
                            }
                        }
                    }
                    break;
                case 2:
                    if (moves[1].equalsIgnoreCase("down")) {
                        int currentValue = currentHeuristic();
                        moveDown();
                        int nextValue = currentHeuristic();
                        int delta = nextValue - currentValue;
                        if (delta < 0) {
                            // keep the move
                            solutionStack.push("Move Zero Down");
                        } else if (delta >= 0){
                            Random rand = new Random();
                            double randValue = rand.nextInt(100)/100.0;
                            double tempValue = Math.exp(-delta/startingTemp);
                            if (randValue < tempValue) {
                                // keep the move
                                solutionStack.push("Move Zero Down");
                            } else {
                                // reject the move
                                moveUp();
                            }
                        }
                    }
                    break;
                case 3:
                    if (moves[2].equalsIgnoreCase("left")) {
                        int currentValue = currentHeuristic();
                        moveLeft();
                        int nextValue = currentHeuristic();
                        int delta = nextValue - currentValue;
                        if (delta < 0) {
                            // keep the move
                            solutionStack.push("Move Zero Left");
                        } else if (delta >= 0){
                            Random rand = new Random();
                            double randValue = rand.nextInt(100)/100.0;
                            double tempValue = Math.exp(-delta/startingTemp);
                            if (randValue < tempValue) {
                                // keep the move
                                solutionStack.push("Move Zero Left");
                            } else {
                                // reject the move
                                moveRight();
                            }
                        }
                    }
                    break;
                case 4:
                    if (moves[3].equalsIgnoreCase("right")) {
                        int currentValue = currentHeuristic();
                        moveRight();
                        int nextValue = currentHeuristic();
                        int delta = nextValue - currentValue;
                        if (delta < 0) {
                            // keep the move
                            solutionStack.push("Move Zero Right");
                        } else if (delta >= 0){
                            Random rand = new Random();
                            double randValue = rand.nextInt(100)/100.0;
                            double tempValue = Math.exp(-delta/startingTemp);
                            if (randValue < tempValue) {
                                // keep the move
                                solutionStack.push("Move Zero Right");
                            } else {
                                // reject the move
                                moveLeft();
                            }
                        }
                    }
                    break;
                default:
                    // do nothing
            }
            iterations++;
            if (iterations % 100 == 0) {
                currentTemp = currentTemp * decrement;
            }
        }
        return solutionStack;
    }

    public String[] getPossibleMoves() {
        // this method creates a string array of possible moves = {'up','down','left','right'}
        String[] possibleMoves = {"","","",""};


        // find the blank square location (denoted by a zero)
        int[] blankLocation = findCellLocation(0);

        // determine if the "up" move is possible
        if (blankLocation[1] == 1 || blankLocation[1] == 2) {
            // location is on second or third row, thus an "up" move is possible
            possibleMoves[0] = "up";
        }
        // determine if "down" move is possible
        if (blankLocation[1] == 0 || blankLocation[1] == 1) {
            // location is on first and second row, thus a "down" move is possible
            possibleMoves[1] = "down";
        }
        // determine if the "left" move is possible
        if (blankLocation[0] == 2 || blankLocation[0] == 1) {
            // location is second or third column
            possibleMoves[2] = "left";
        }
        // determine if the "right" (direction) move is possible
        if (blankLocation[0] == 0 || blankLocation[0] == 1) {
            // location is on first or second column, thus a "right" (direction) is possible
            possibleMoves[3] = "right";
        }
        System.out.println("Possible Moves are:\n");
        System.out.println(possibleMoves[0] + ", " + possibleMoves[1] + ", " + possibleMoves[2] + ", " + possibleMoves[3] + "\n");
        return possibleMoves;
    }

    public int[] findCellLocation(int inputValue) {
        // takes an integer as input and returns the location of the element in the puzzle. Will return (-1,-1) if not
        // found within the puzzle
        int[] outputCoordinates = {-1,-1};
        for (int yIndex = 0; yIndex < 3; yIndex++) {
            for (int xIndex = 0; xIndex < 3; xIndex++) {
                if (puzzleState[yIndex][xIndex] == inputValue) {
                    outputCoordinates[0] = xIndex;
                    outputCoordinates[1] = yIndex;
                }
            }
        }
        System.out.println("The cell with value " + inputValue + " is located at X = " + outputCoordinates[0] + ", " +
                "and Y = " + outputCoordinates[1] + ".");
        return outputCoordinates;
    }

    public int currentHeuristic() {
        // returns the heuristic value for the current state. The heuristic used is the summation of moves for each
        // cell to move independently via a Manhattan Distance from its current location to the goal location

        // find the location of all elements
        int[] zeroLocation = findCellLocation(0);
        int[] oneLocation = findCellLocation(1);
        int[] twoLocation = findCellLocation(2);
        int[] threeLocation = findCellLocation(3);
        int[] fourLocation = findCellLocation(4);
        int[] fiveLocation = findCellLocation(5);
        int[] sixLocation = findCellLocation(6);
        int[] sevenLocation = findCellLocation(7);
        int[] eightLocation = findCellLocation(8);

        //Determine the distances (in moves) from current location to goal location
        int zeroDistance = (Math.abs(zero[0] - zeroLocation[0]) + Math.abs(zero[1] - zeroLocation[1]));
        int oneDistance = (Math.abs(one[0] - oneLocation[0]) + Math.abs(one[1] - oneLocation[1]));
        int twoDistance = (Math.abs(two[0] - twoLocation[0]) + Math.abs(two[1] - twoLocation[1]));
        int threeDistance = (Math.abs(three[0] - threeLocation[0]) + Math.abs(three[1] - threeLocation[1]));
        int fourDistance = (Math.abs(four[0] - fourLocation[0]) + Math.abs(four[1] - fourLocation[1]));
        int fiveDistance = (Math.abs(five[0] - fiveLocation[0]) + Math.abs(five[1] - fiveLocation[1]));
        int sixDistance = (Math.abs(six[0] - sixLocation[0]) + Math.abs(six[1] - sixLocation[1]));
        int sevenDistance = (Math.abs(seven[0] - sevenLocation[0]) + Math.abs(seven[1] - sevenLocation[1]));
        int eightDistance = (Math.abs(eight[0] - eightLocation[0]) + Math.abs(eight[1] - eightLocation[1]));

        // sum up the distances
        return (zeroDistance + oneDistance + twoDistance + threeDistance + fourDistance + fiveDistance +
                sixDistance + sevenDistance + eightDistance);

    }

    public int[] nextHeuristics() {
        // this method takes the current state, and performs all possible moves (up, down, left, right) and compiles an
        // array of values for each. If a move is not possible, the value is -1.

        int[] heuristics = {-1, -1, -1, -1, -1};

        String[] possibleMoves = getPossibleMoves();

        if (possibleMoves[0].equalsIgnoreCase("up")) {
            moveUp();
            heuristics[0] = currentHeuristic();
            moveDown();
        }
        if (possibleMoves[1].equalsIgnoreCase("down")) {
            moveDown();
            heuristics[1] = currentHeuristic();
            moveUp();
        }
        if (possibleMoves[2].equalsIgnoreCase("left")) {
            moveLeft();
            heuristics[2] = currentHeuristic();
            moveRight();
        }
        if (possibleMoves[3].equalsIgnoreCase("right")) {
            moveRight();
            heuristics[3] = currentHeuristic();
            moveLeft();
        }

        int smallestValue = -1;
        for (int i = 0; i < 4; i++) {
            if (!(heuristics[i] == -1)) {
                int currentValue = heuristics[i];
                if (smallestValue == -1) {
                    smallestValue = currentValue;
                    heuristics[4] = i;
                } else if (currentValue < smallestValue) {
                    smallestValue = currentValue;
                    heuristics[4] = i;
                }
            } else {
                // do nothing because this element is -1, move to next one
            }
        }
        System.out.println("The index of the smallest value is " + heuristics[4]);
        return heuristics;
    }

    public void moveUp() {
        // this method finds the blank square, and switches it with the square above it. It checks whether the cell can
        // actually be moved up. If it cannot, no action is performed and the console reports no move was made.
        int[] blankLocation = findCellLocation(0);

        if (blankLocation[1] == 0) {
            // do nothing
            System.out.println("The blank cell cannot perform 'moveUp' as it is on the top row.");
        } else {
            int swapValue = getCellValue(blankLocation[0], (blankLocation[1]-1));
            setCellValue(0, blankLocation[0], (blankLocation[1]-1));
            setCellValue(swapValue, blankLocation[0], blankLocation[1]);
        }
    }

    public void moveDown() {
        // this method finds the blank square, and switches it with the square below it. It checks whether the cell can
        // actually be moved down. If it cannot, no action is performed and the console reports no move was made.
        int[] blankLocation = findCellLocation(0);

        if (blankLocation[1] == 2) {
            // do nothing
            System.out.println("The blank cell cannot perform 'moveDown' as it is on the bottom row.");
        } else {
            int swapValue = getCellValue(blankLocation[0], (blankLocation[1]+1));
            setCellValue(0, blankLocation[0], (blankLocation[1]+1));
            setCellValue(swapValue, blankLocation[0], blankLocation[1]);
        }
    }

    public void moveLeft() {
        // this method finds the blank square, and switches it with the square to the left. It checks whether the cell
        // can actually be moved. If it cannot, no action is performed and the console reports no move was made.
        int[] blankLocation = findCellLocation(0);

        if (blankLocation[0] == 0) {
            // do nothing
            System.out.println("The blank cell cannot perform 'moveLeft' as it is on the left column.");
        } else {
            int swapValue = getCellValue((blankLocation[0]-1), (blankLocation[1]));
            setCellValue(0, (blankLocation[0]-1), (blankLocation[1]));
            setCellValue(swapValue, blankLocation[0], blankLocation[1]);
        }
    }

    public void moveRight() {
        // this method finds the blank square, and switches it with the square to the right. It checks whether the cell
        // can actually be moved. If it cannot, no action is performed and the console reports no move was made.
        int[] blankLocation = findCellLocation(0);

        if (blankLocation[0] == 2) {
            // do nothing
            System.out.println("The blank cell cannot perform 'moveRight' as it is on the right column.");
        } else {
            int swapValue = getCellValue((blankLocation[0]+1), (blankLocation[1]));
            setCellValue(0, (blankLocation[0]+1), (blankLocation[1]));
            setCellValue(swapValue, blankLocation[0], blankLocation[1]);
        }
    }
}
