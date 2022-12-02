package application.controller;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.media.MediaView;

/**
 * Controls the Mission.fxml view and displays a video with some tutorial
 * instructions before main game begins.
 * 
 * CS3443-004 - Fall 2022
 *
 * @author Amalia Talijancic
 * @author Danny Ghrist (kda458)
 *
 */
public class MissionController extends Controllers implements EventHandler<ActionEvent>, Initializable {

    @FXML
    private MediaView media;

    @FXML
    private Button buttonPushed, gameStartButton;

    @FXML
    private Label contextLabel, missionLabel;

    /**
     * Everything to be initialized upon the initial loading of the Scene (i.e,
     * animations, music, timers etc...)
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        playVideo("missionBackground", -1, media);
        animateText(missionLabel, "YOUR MISSION:  Deliver (" + Main.user.getNumPizzasToMake() + ") pizzas in ("
                + Main.user.getCountdownMinutes() + ") minutes.");
    }

    /**
     * Determines which button was pressed (if we end up having multiple buttons),
     * and loads the view for that corresponding button.
     * 
     * @param event Listens for button push event (ActionEvent)
     */
    @Override
    public void handle(ActionEvent event) {
        try {

            // Determines which button was pushed and loads that FXML Scene.
            Button buttonPushed = (Button) event.getSource();

            String newScene = "";

            if (buttonPushed.getId().equals("gameStartButton")) {
                newScene = "GameView.fxml";
                playSound("mindfighton");
                mediaBackground.stop();
            } else if (buttonPushed.getId().equals(null)) {
                System.out.println("IT'S ALL WRONG, WHAT HAVE YOU DONE!!!");
            }

            // Connect to the FXML (contains our layout) and load it in.
            Parent root = FXMLLoader.load(Main.class.getResource("view/" + newScene));

            // Put the layout onto the scene.
            Scene scene = new Scene(root);

            // Set the scene on the stage that was created in Main.java.
            Main.stage.setScene(scene);
            Main.stage.show();

            // Set the cursor to the custom cursor upon switching Scenes.
            try {
                setCursor("normalSelect");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
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
