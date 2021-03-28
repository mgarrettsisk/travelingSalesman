package assignment02;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class aboutDialogController {

    // attributes

    public Button closeAboutButton;

    // Initialize GUI Elements

    public Stage activeStage = new Stage();
    // Public Methods
    public void showWindow() throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("aboutDialog.fxml"));
        activeStage.setTitle("About");
        activeStage.setScene(new Scene(root, 350, 250));
        activeStage.setResizable(false);
        activeStage.show();
    }
    public void closeWindow() {
        Stage currentStage = (Stage) closeAboutButton.getScene().getWindow();
        currentStage.close();
    }
}

