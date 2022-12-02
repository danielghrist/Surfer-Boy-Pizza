package application.controller;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import javafx.animation.PathTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.shape.HLineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;

/**
 * Short driving animation scene to show pizzas being delivered before end win
 * scene.
 * 
 * CS3443-004 - Fall 2022
 *
 * @author Carlos Martinez
 * @author Danny Ghrist (kda458)
 *
 */
public class DrivingController extends Controllers implements Initializable {

    @FXML
    ImageView pizzaTruck, roadImage;
    @FXML
    Button nextSceneButton;

    /**
     * Everything to be initialized upon the initial loading of the Scene (i.e,
     * animations, music, timers, etc...)
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Path path = new Path();
        MoveTo moveTo = new MoveTo(-200, 62);
        HLineTo line = new HLineTo(1000);
        path.getElements().add(moveTo);
        path.getElements().add(line);
        PathTransition transition = new PathTransition();
        transition.setNode(pizzaTruck);
        transition.setDuration(Duration.seconds(4));
        transition.setPath(path);
        transition.setCycleCount(1);
        transition.play();
        playSound("surfsupmydude");
        transition.setOnFinished(event -> loadScene("PizzaFinished.fxml"));
    }

    /**
     * Determines which button was pressed (if we end up having multiple buttons),
     * and loads the view for that corresponding button.
     * 
     * @param event Listens for button push event (ActionEvent)
     */
    public void loadScene(String sceneName) {
        try {
            // Connect to the FXML (contains our layout) and load it in.
            Parent root = FXMLLoader.load(Main.class.getResource("view/" + sceneName));
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
}
