package assignment02;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class mainController {
    // the controller class used to control the user experience and interface

    // THIS SECTION IS FOR GENERAL APPLICATION EVENTS

    public BorderPane baseBorderPane;
    public MenuItem closeMenuItem;
    public MenuItem aboutMenuItem;


    // application methods

    public void showAbout() {
        // shows the about dialog box with appropriate Assignment 02 information on it
    }

    public void closeApp() {
        // closes the application
        Stage appWindow = (Stage)baseBorderPane.getScene().getWindow();
        appWindow.close();
    }

    // THIS SECTION IS RESERVED SOLELY FOR THE FIRST QUESTION OF ASSIGNMENT 2.
    // FOR THE SECOND TAB, PLEASE CRTL+F "SECTION 2: TOOLPATH"

    public TextField startState11;
    public TextField startState21;
    public TextField startState31;
    public TextField startState12;
    public TextField startState22;
    public TextField startState32;
    public TextField startState13;
    public TextField startState23;
    public TextField startState33;
    public Button upButton;
    public Button downButton;
    public Button leftButton;
    public Button rightButton;
    public TextField upHeuristic;
    public TextField downHeuristic;
    public TextField leftHeuristic;
    public TextField rightHeuristic;
    public TextField startingTemp;

    public puzzle workingPuzzle = new puzzle();


    // EVENT HANDLER METHODS

    public void setRandomState() {
        // this method creates a puzzle object, and creates a random state, and displays the values in the appropriate
        // text fields
        workingPuzzle.setRandomState();
        drawGrid();
        startingTemp.setText(Integer.toString(workingPuzzle.currentHeuristic()));
    }

    public void moveZeroUp() {
        workingPuzzle.moveUp();
        drawGrid();
        startingTemp.setText(Integer.toString(workingPuzzle.currentHeuristic()));
    }

    public void moveZeroDown() {
        workingPuzzle.moveDown();
        drawGrid();
        startingTemp.setText(Integer.toString(workingPuzzle.currentHeuristic()));
    }

    public void moveZeroLeft() {
        workingPuzzle.moveLeft();
        drawGrid();
        startingTemp.setText(Integer.toString(workingPuzzle.currentHeuristic()));
    }

    public void moveZeroRight() {
        workingPuzzle.moveRight();
        drawGrid();
        startingTemp.setText(Integer.toString(workingPuzzle.currentHeuristic()));
    }

    // private methods

    private void drawGrid() {
        // this method redraws the current state grid to reflect the change in the puzzle state
        startState11.setText((Integer.toString(workingPuzzle.getCellValue(0,0))));
        startState21.setText((Integer.toString(workingPuzzle.getCellValue(1,0))));
        startState31.setText((Integer.toString(workingPuzzle.getCellValue(2,0))));
        startState12.setText((Integer.toString(workingPuzzle.getCellValue(0,1))));
        startState22.setText((Integer.toString(workingPuzzle.getCellValue(1,1))));
        startState32.setText((Integer.toString(workingPuzzle.getCellValue(2,1))));
        startState13.setText((Integer.toString(workingPuzzle.getCellValue(0,2))));
        startState23.setText((Integer.toString(workingPuzzle.getCellValue(1,2))));
        startState33.setText((Integer.toString(workingPuzzle.getCellValue(2,2))));

        // recomputes the heuristics for each move as well
        int[] nextValues = workingPuzzle.nextHeuristics();
        upHeuristic.setText(Integer.toString(nextValues[0]));
        downHeuristic.setText(Integer.toString(nextValues[1]));
        leftHeuristic.setText(Integer.toString(nextValues[2]));
        rightHeuristic.setText(Integer.toString(nextValues[3]));
    }


}
