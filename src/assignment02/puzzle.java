package assignment02;

public class puzzle {
    // this class defines the puzzle object

    // attributes

    private int[][] puzzleState = {
        {0, 0, 0},
        {0, 0, 0},
        {0, 0, 0}
    };

    // constructors

    puzzle() {
        // null constructor
    }

    puzzle(int[][] inputState) {
        // constructor that takes a puzzle state as input
        this.puzzleState = inputState;
    }

    // public methods

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

}
