package application.controller;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import javafx.animation.RotateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.media.MediaView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Allows user to either go back home, or go back to the GameView.fxml and make
 * another pizza, or exit the application.
 * 
 * @author Amalia Talijancic
 * @author Danny Ghrist (kda458)
 * @author Sarah Halverson (llv920)
 *
 */
public class PizzaFinishedController extends Controllers implements EventHandler<ActionEvent>, Initializable {

    @FXML
    private Button homeButton, morePizzaButton, exitButton;

    @FXML
    private Text hoorayPizzaFinishedText;

    @FXML
    private ImageView winArgyle, winPizza, winPizza1;

    @FXML
    private MediaView winMedia;

    /**
     * Determines which button was pressed (if we end up having multiple buttons),
     * and loads the view for that corresponding button.
     * 
     * @author Danny Ghrist (kda458): I deleted the extra method as I have a
     *         combined method that can handle moving wherever the user needs to go
     *         determined by which button they press. I incorporated your home
     *         button here for brevity.
     * 
     * @param event Listens for button push event (ActionEvent)
     */

    /**
     * Rotates pizzas on the screen.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        playVideo("WinVideo", -1, winMedia);

        RotateTransition rotate = new RotateTransition(Duration.seconds(3), winPizza);
        rotate.setFromAngle(0);
        rotate.setToAngle(360);
        rotate.setAutoReverse(true);
        rotate.setCycleCount(RotateTransition.INDEFINITE);
        rotate.play();

        RotateTransition rotate1 = new RotateTransition(Duration.seconds(3), winPizza1);
        rotate1.setFromAngle(0);
        rotate1.setToAngle(360);
        rotate1.setAutoReverse(true);
        rotate1.setCycleCount(RotateTransition.INDEFINITE);
        rotate1.play();
    }

    /**
     * Event listener to switch scenes depending on button pushed.
     * 
     * @param event Mouse click on Button to be listened for (ActionEvent)
     */
    @Override
    public void handle(ActionEvent event) {
        try {

            // Determine which button was pressed.
            Button buttonPushed = (Button) event.getSource();

            String newScene = "";

            // Determines which button was pushed and loads that FXML Scene.
            if (buttonPushed.getId().equals("homeButton")) {
                mediaBackground.stop();
                newScene = "MainView.fxml";
                playSound("buttonclick");
            } else if (buttonPushed.getId().equals("morePizzaButton")) {
                mediaBackground.stop();
                newScene = "GameView.fxml";
                playSound("buttonclick");
            } else if (buttonPushed.getId().equals("exitButton")) {
                mediaBackground.stop();
                playSound("buttonclick");
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
        } catch (

        Exception e) {
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
