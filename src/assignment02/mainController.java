package assignment02;

import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class mainController {
    // the controller class used to control the user experience and interface

    // THIS SECTION IS FOR GENERAL APPLICATION EVENTS

    public MenuItem closeMenuItem;
    public MenuItem aboutMenuItem;


    // application methods

    public void showAbout() {
        // shows the about dialog box with appropriate Assignment 02 information on it
    }

    public void closeApp() {
        // closes the application
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


    // EVENT HANDLER METHODS

    public void setRandomState() {
        // this method creates a puzzle object, and creates a random state, and displays the values in the appropriate
        // text fields
        puzzle workingPuzzle = new puzzle();
        workingPuzzle.setRandomState();

        // write all values to the appropriate display block
        startState11.setText((Integer.toString(workingPuzzle.getCellValue(0,0))));
        startState21.setText((Integer.toString(workingPuzzle.getCellValue(1,0))));
        startState31.setText((Integer.toString(workingPuzzle.getCellValue(2,0))));
        startState12.setText((Integer.toString(workingPuzzle.getCellValue(0,1))));
        startState22.setText((Integer.toString(workingPuzzle.getCellValue(1,1))));
        startState32.setText((Integer.toString(workingPuzzle.getCellValue(2,1))));
        startState13.setText((Integer.toString(workingPuzzle.getCellValue(0,2))));
        startState23.setText((Integer.toString(workingPuzzle.getCellValue(1,2))));
        startState33.setText((Integer.toString(workingPuzzle.getCellValue(2,2))));
    }


}
