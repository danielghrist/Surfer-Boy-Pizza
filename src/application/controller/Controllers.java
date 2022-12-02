package application.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Paths;

import application.Main;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.Transition;
import javafx.fxml.FXML;
import javafx.scene.ImageCursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;

/**
 * This is a helper class which includes all the methods that are re-used in the
 * controllers. The other controllers can inherit these methods throughout each
 * different view so we can prevent duplication of code.
 * 
 * CS3443-004 - Fall 2022
 *
 * @author Danny Ghrist (kda458)
 *
 */
public abstract class Controllers {

    @FXML
    MediaPlayer mediaPlayer, mediaPlayerSFX, mediaBackground;
    FadeTransition fadeInAnimation;

    /**
     * Constructs an object of the Controller class, however this is a helper class
     * and this is not used.
     */
    public Controllers() {
    }

    /**
     * Sets the custom cursor.
     * 
     * @param imageName The name of the image file of the cursor. (String)
     * @throws FileNotFoundException (Exception)
     */
    public void setCursor(String imageName) throws FileNotFoundException {
        Image myImage = new Image(new FileInputStream("src/application/images/" + imageName + ".png"));
        ImageCursor cursor = new ImageCursor(myImage, 0, 0);
        Scene scene = Main.stage.getScene();
        scene.getRoot().setCursor(cursor);
    }

    /**
     * Apply a FadeTransition on a node given the duration in seconds.
     * 
     * @param seconds Time of the fade in seconds (Double)
     * @param node    The child node to have the transition applied to (Node)
     */
    public void playFadeInTransition(double seconds, Node node) {
        FadeTransition transition = new FadeTransition(Duration.seconds(seconds), node);
        transition.setFromValue(0);
        transition.setToValue(1.0);
        transition.play();
    }

    /**
     * Apply a FadeTransition on a node given the duration in seconds.
     * 
     * @param seconds Time of the fade in seconds (Double)
     * @param node    The child node to have the transition applied to (Node)
     */
    public void playFadeOutTransition(double seconds, Node node) {
        FadeTransition transition = new FadeTransition(Duration.seconds(seconds), node);
        transition.setFromValue(1);
        transition.setToValue(0.0);
        transition.play();
        fadeInAnimation = transition;
    }

    /**
     * Animates the contents of a Label's text and animates it.
     * 
     * @param lbl          The Label to set the text in (Label)
     * @param stringToType The String to type to be animated(String)
     */
    public void animateText(Label lbl, String stringToType) {
        String content = stringToType;
        final Animation animation = new Transition() {
            {
                setCycleDuration(Duration.millis(6000));
            }

            protected void interpolate(double frac) {
                final int length = content.length();
                final int n = Math.round(length * (float) frac);
                lbl.setText(content.substring(0, n));
            }
        };
        animation.play();
    }

    /**
     * Creates a new Media Player to play background music for the Scene.
     * 
     * @param music Name of mp3 music audio (String)
     */
    public void playMusic(String musicName) {
        String s = "src/application/audio/" + musicName + ".mp3";
        Media music = new Media(Paths.get(s).toUri().toString());
        // Media(getClass().getResource("application/audio/StrangerThingsThemeSong.mp3").toExternalForm());
        mediaPlayer = new MediaPlayer(music);
        mediaPlayer.autoPlayProperty();
        mediaPlayer.setCycleCount(-1);
        mediaPlayer.play();
        // mediaPlayer.setStartTime(Duration.seconds(0));
        // mediaPlayer.setAutoPlay(true);
    }

    /**
     * Creates a new Media object to play sound effects.
     * 
     * @param soundName Name of mp3 sound effect audio (String)
     */
    public void playSound(String soundName) {
        String s = "src/application/audio/" + soundName + ".mp3";
        Media soundEffect = new Media(Paths.get(s).toUri().toString());
        mediaPlayerSFX = new MediaPlayer(soundEffect);
        mediaPlayerSFX.play();
        // mediaPlayer.setStartTime(Duration.seconds(0));
        // mediaPlayer.setAutoPlay(true);
    }

    /**
     * Plays a video in a given MediaView Node.
     * 
     * @param videoName  Name of mp4 video without extension (String)
     * @param cycleCount Number of cycles to play the video (int)
     * @param mediaVideo Name of MediaView Node (MediaView)
     */
    public void playVideo(String videoName, int cycleCount, MediaView mediaVideo) {
        String mediaURL = "src/application/videos/" + videoName + ".mp4";
        Media media1 = new Media(Paths.get(mediaURL).toUri().toString());
        mediaBackground = new MediaPlayer(media1);
        mediaBackground.setAutoPlay(true);
        mediaBackground.setCycleCount(cycleCount);
        mediaVideo.setMediaPlayer(mediaBackground);
    }
}
