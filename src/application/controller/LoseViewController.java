package application.controller;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import javafx.animation.RotateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Shows the user a fake loading animation screen with some tips for the game to
 * add to mimic loading screens found in games.
 * 
 * EDIT: Moving the above to this new view: the LoseViewController and
 * LoseView.fxml so as not to overcrowd the start to the game.
 * 
 * @author Amalia Talijancic
 * @author Sarah Halverson(llv920)
 * @author Danny Ghrist (kda458)
 *
 */
public class LoseViewController extends Controllers implements Initializable {

    @FXML
    Button tryAgainButton, exitButton;

    @FXML
    Label tipOneLabel, gameOverLabel;

    @FXML
    ImageView vecnaImageView, vecnaImage;

    @FXML
    Image vecnaPixelImage;

    @FXML
    MediaView loseMedia;

    /**
     * Everything to be initialized upon the initial loading of the Scene (i.e,
     * animations, music, timers etc...)
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        playVideo("LoseVideo", 1, loseMedia);

        RotateTransition rotate1 = new RotateTransition(Duration.seconds(3), gameOverLabel);
        rotate1.setFromAngle(0);
        rotate1.setToAngle(360);
        rotate1.setAutoReverse(true);
        rotate1.setCycleCount(RotateTransition.INDEFINITE);
        rotate1.play();
    }

    /**
     * Determines which button was pressed (if we end up having multiple buttons),
     * and loads the view for that corresponding button.
     * 
     * @param event Listens for button push event (ActionEvent)
     */
    public void handle(ActionEvent event) {

        try {

            // Determine which button was pressed.
            Button buttonPushed = (Button) event.getSource();

            String newScene = "";

            // Determines which button was pushed and loads that FXML Scene.
            if (buttonPushed.getId().equals("tryAgainButton")) {
                newScene = "MainView.fxml";
                mediaBackground.stop();
            } else if (buttonPushed.getId().equals("exitButton")) {
                newScene = null;
            } else if (buttonPushed.getId().equals(null)) {
                System.out.println("IT'S ALL WRONG, WHAT HAVE YOU DONE!!!");
            }

            if (newScene == null) {
                Stage stage = (Stage) exitButton.getScene().getWindow();
                stage.close();
            } else {
                // Connect to the FXML (contains our layout) and load it in.
                Parent root = FXMLLoader.load(Main.class.getResource("view/" + newScene));

                // Put the layout onto the scene.
                Scene scene = new Scene(root);

                // Set the scene on the stage that was created in Main.java.
                Main.stage.setScene(scene);

                // Set the cursor to the custom cursor upon switching Scenes.
                try {
                    setCursor("normalClick");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                Main.stage.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Event listener to play a sound effect for when a user hovers over a button.
     */
    public void handleButtonEntered() {
        playSound("buttonhover");
        try {
            setCursor("normalClick");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Event Listener for when a button is exited.
     */
    public void handleButtonExit() {
        try {
            setCursor("normalSelect");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}