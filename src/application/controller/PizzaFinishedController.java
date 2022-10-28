package application.controller;

import application.Main;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * Allows user to either go back home or go back to the GameView.fxml and make
 * another pizza.
 * 
 * @author Amalia Talijancic
 * @author Danny Ghrist (kda458)
 *
 */

public class PizzaFinishedController implements EventHandler<ActionEvent> {

    @FXML
    Button home, morePizzaButton;
    
    @FXML
    Text hoorayPizzaFinished;
    
    @FXML
    ImageView winArgyle;

    /**
     * @author - Amalia's edits Here's a handle for the Home Button meant for the
     *         Game View
     * @author Danny Ghrist (kda458): I deleted the extra method as I have a
     *         combined method that can handle moving wherever the user needs to go
     *         determined by which button they press. I incorporated your home
     *         button here for brevity.
     */
    @Override
    public void handle(ActionEvent event) {
        try {

            // Determine which button was pressed.
            Button buttonPushed = (Button) event.getSource();

            String newScene = "";

            // Determines which button was pushed and loads that FXML Scene.
            if (buttonPushed.getId().equals("home")) {
                newScene = "MainView.fxml";
            } else if (buttonPushed.getId().equals("morePizzaButton")) {
                newScene = "GameView.fxml";
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

        } catch (

        Exception e) {
            e.printStackTrace();
        }
    }
}
