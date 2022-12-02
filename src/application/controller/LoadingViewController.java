package application.controller;

import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
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

/**
 * Shows the user a fake loading animation screen with some tips for the game to
 * add to mimic loading screens found in games.
 * 
 * @author Amalia Talijancic
 * @author Danny Ghrist (kda458)
 *
 */
public class LoadingViewController extends Controllers implements Initializable {

    @FXML
    Button continueButton;

    @FXML
    Label tipOneLabel;

    @FXML
    ImageView vecnaImageView;

    @FXML
    Image vecnaPixelImage;

    @FXML
    MediaView mediaLoading;

    // private static final String MEDIA_URL=
    // "src/application/videos/ArgleMission.mp4";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        playVideo("mainMenuBackground, -1, backgroundMedia);

        playVideo("ClipChampRedBar", 1, mediaLoading);

//		System.out.println(location.toString());
//		System.out.println(this.getClass().getResource(MEDIA_URL).toExternalForm());
//		
//		mediaPlayer = new MediaPlayer(new Media(this.getClass().getResource(MEDIA_URL).toExternalForm()));
//		mediaPlayer.setAutoPlay(true);
//		mediaLoading.setMediaPlayer(mediaPlayer);
    }

    /**
     * Determines which button was pressed (if we end up having multiple buttons),
     * and loads the view for that corresponding button.
     * 
     * @param event Listens for button push event (ActionEvent)
     */
    public void handle(ActionEvent event) {
        try {

            // Determines which button was pushed and loads that FXML Scene.
            Button buttonPushed = (Button) event.getSource();

            String newScene = "";

            if (buttonPushed.getId().equals("continueButton")) {
                newScene = "GameView.fxml";

                // Connect to the FXML (contains our layout) and load it in.
                Parent root = FXMLLoader.load(Main.class.getResource("view/" + newScene));

                // Put the layout onto the scene.
                Scene scene = new Scene(root);

                // Set the scene on the stage that was created in Main.java.
                Main.stage.setScene(scene);
                Main.stage.show();

            }
        } catch (Exception e) {

        }
    }
}