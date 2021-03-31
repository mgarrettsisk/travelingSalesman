package assignment02;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.LinkedList;

public class mainController {
    //------------------------------------------------------------------------------------------------------------------
    //                                         APPLICATION ATTRIBUTES
    //------------------------------------------------------------------------------------------------------------------
    public BorderPane baseBorderPane;
    public MenuItem closeMenuItem;
    public MenuItem aboutMenuItem;
    //------------------------------------------------------------------------------------------------------------------
    //                                           APPLICATION METHODS
    //------------------------------------------------------------------------------------------------------------------
    public void showAbout() throws Exception {
        // shows the about dialog box with appropriate Assignment 02 information on it
        aboutDialogController about = new aboutDialogController();
        about.showWindow();
    }
    public void closeApp() {
        // closes the application
        Stage appWindow = (Stage)baseBorderPane.getScene().getWindow();
        appWindow.close();
    }
    //------------------------------------------------------------------------------------------------------------------
    //                                               ATTRIBUTES
    //------------------------------------------------------------------------------------------------------------------
    // for 8-Puzzle Problem
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
    public ListView<String> solutionListView;
    public puzzle workingPuzzle = new puzzle();
    // For Shortest Toolpath Problem
    public Button randomArrangementButton;
    public Button solveToolpathButton;
    public Canvas toolpathCanvas;
    public GraphicsContext gc;
    public TextField targetQuantity;
    public toolpathOptimizer toolpath = new toolpathOptimizer();
    public TextField pathLengthBeforeTextField;
    public TextField pathLengthAfterTextField;
    public TextField quenchingTempTextField;
    public TextField startingTempTextField;
    public TextField decrementTextField;
    public TextField maxIterationsTextField;
    public Button resetButton;
    //------------------------------------------------------------------------------------------------------------------
    //                                          EVENT HANDLER METHODS
    //------------------------------------------------------------------------------------------------------------------
    public void setRandomState() {
        // this method creates a puzzle object, and creates a random state, and displays the values in the appropriate
        // text fields
        workingPuzzle.setRandomState();
        drawGrid();
    }
    public void moveZeroUp() {
        workingPuzzle.moveUp();
        drawGrid();
    }
    public void moveZeroDown() {
        workingPuzzle.moveDown();
        drawGrid();
    }
    public void moveZeroLeft() {
        workingPuzzle.moveLeft();
        drawGrid();
    }
    public void moveZeroRight() {
        workingPuzzle.moveRight();
        drawGrid();
    }
    public void solveButton() {
        ObservableList<String> visibleList;
        visibleList = FXCollections.observableArrayList();
        visibleList.setAll(workingPuzzle.solvePuzzle(20000, 0.95));
        solutionListView.setItems(visibleList);
        drawGrid();
    }
    public void generateTargets() {
        // handler method that generates a set number of targets
        int quantity = Integer.parseInt(targetQuantity.getText());
        double maxWidth = toolpathCanvas.getWidth();
        double maxHeight = toolpathCanvas.getWidth();
        gc = toolpathCanvas.getGraphicsContext2D();
        toolpath.generateRandomTargets(quantity, (int)maxWidth, (int)maxHeight);
        LinkedList<toolpathOptimizer.target> drawTargets = toolpath.getTargetList();
        clearCanvas(gc);
        drawTarget(gc, drawTargets.get(0), Color.GREEN);
        for (int i = 1; i < drawTargets.size(); i++) {
            drawTarget(gc, drawTargets.get(i), Color.BLUE);
            if (i == (drawTargets.size()-1)) {
                drawPath(gc,drawTargets.getLast(), drawTargets.getFirst());
            } else {
                drawPath(gc,drawTargets.get(i), drawTargets.get(i+1));
            }
        }
        randomArrangementButton.setDisable(true);
    }
    public void solveToolpath() {
        // handler method that optimizes the toolpath
        showToolpath(pathLengthBeforeTextField);
        double startTemp = Double.parseDouble(startingTempTextField.getText());
        double userDecrement = Double.parseDouble(decrementTextField.getText());
        int userIterations = Integer.parseInt(maxIterationsTextField.getText());
        double quenchingTemperature = toolpath.optimize(startTemp, userDecrement, userIterations);
        LinkedList<toolpathOptimizer.target> drawTargets = toolpath.getTargetList();
        clearCanvas(gc);
        drawTarget(gc, drawTargets.get(0), Color.GREEN);
        for (int i = 1; i < drawTargets.size(); i++) {
            drawTarget(gc, drawTargets.get(i), Color.BLUE);
            if (i == (drawTargets.size()-1)) {
                drawPath(gc,drawTargets.getLast(), drawTargets.getFirst());
            } else {
                drawPath(gc,drawTargets.get(i), drawTargets.get(i+1));
            }
        }
        showToolpath(pathLengthAfterTextField);
        quenchingTempTextField.setText(Double.toString(quenchingTemperature));
    }
    public void resetToolpath() {
        // event handler to call the reset method of the toolpath optimizer
        toolpath.reset();
        targetQuantity.clear();
        targetQuantity.setPromptText("Enter Qty...");
        randomArrangementButton.setDisable(false);
        clearCanvas(gc);
        pathLengthBeforeTextField.clear();
        pathLengthAfterTextField.clear();
        quenchingTempTextField.clear();
    }

    //------------------------------------------------------------------------------------------------------------------
    //                                              PRIVATE METHODS
    //------------------------------------------------------------------------------------------------------------------
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
    private void drawTarget(GraphicsContext contextInput, toolpathOptimizer.target inputTarget, Color colorInput) {
        // this method takes a target object as input and draws a circle on the input canvas graphics context.
        contextInput.setFill(colorInput);
        contextInput.fillOval(inputTarget.getXpos()-5, inputTarget.getYpos()-5, 10,10);
    }
    private void drawPath(GraphicsContext contextInput, toolpathOptimizer.target source, toolpathOptimizer.target destination) {
        // method takes two targets as input and draws a line between them to represent the path
        contextInput.setStroke(Color.RED);
        contextInput.strokeLine(source.getXpos(), source.getYpos(), destination.getXpos(), destination.getYpos());
    }
    private void clearCanvas(GraphicsContext inputCanvas) {
        // this method clears the canvas such as to prevent over writing of imagery from different parts of the program
        gc.clearRect(0, 0, toolpathCanvas.getWidth(), toolpathCanvas.getHeight());
    }
    private void showToolpath(TextField outputField) {
        // method to display toolpath of current path in GUI
        outputField.setText((Double.toString(toolpath.getPathlength())));
    }
    //------------------------------------------------------------------------------------------------------------------
    //                                     END OF CLASS mainController.java
    //------------------------------------------------------------------------------------------------------------------
}
