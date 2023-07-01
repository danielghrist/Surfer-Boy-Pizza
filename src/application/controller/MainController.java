package application.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ThreadLocalRandom;

import application.Main;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Controller for the MainView FXML Scene which will be the title screen.
 * 
 * CS3443-004 - Fall 2022
 *
 * @author Danny Ghrist (kda458)
 * @author Caleb Pierce (rvt884)
 * @author Sarah Halverson
 * @author Amalia Talijancic
 * @author Carlos Martinez
 */
public class MainController extends Controllers implements EventHandler<ActionEvent>, Initializable {

    @FXML
    private AnchorPane titlePane;

    @FXML
    private ImageView logoImg, soundMessageImgView, blackFadeImg1, blackFadeImg2, blackFadeImg3, overallFade, lineTop,
            lineLeft, lineRight, lineBottom, lineRight2, lineLeft2;

    @FXML
    private Button buttonPushed, pizzaStartButton, optionsButton, exitButton, skipButton, cancelButton, saveButton;

    @FXML
    private MediaView backgroundMedia;

    @FXML
    private Text titleText;

    @FXML
    private Label creditsLabel;

    @FXML
    private VBox titleVBox, menuButtonsVBox;

    @FXML
    private HBox titleHBox1, titleHBox2;

    @FXML
    private Pane menuBackPane, optionsButtonsPane;

    @FXML
    private Slider volumeSlider;

    @FXML
    private RadioButton easyRB, mediumRB, hardRB;

    private Timer timer;
    private Timer startTimerToRunOnce;
    double TitleStretch;
    double startingStretch = 200;
    int secondsToStretch = 1;
    long start = System.currentTimeMillis();
    long finish = System.currentTimeMillis();
    int timeElapsed = (int) (finish - start);
    boolean areButtonsTransitioned = false;
    double lastTimeElapsed;
    double stretch = 200;
    private ArrayList<TranslateTransition> arrayOfAnimations = new ArrayList<TranslateTransition>();
    private FadeTransition fadeInSoundAnimation;
    private Timer startTimerButtons;
    private ArrayList<RadioButton> radioButtonArray = new ArrayList<RadioButton>();

    /**
     * Everything to be initialized upon the initial loading of the Scene (i.e,
     * animations, music, timers etc...)
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            loadConfig();
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }
        playMusic("StrangerThingsThemeSong");
        playFadeOutTransition(15, overallFade);
        playFadeOutSoundMessageTransition(5, soundMessageImgView);
        playVideo("mainMenuBackground", -1, backgroundMedia);
        playFadeInTransition(2, titleVBox);
        playFadeInTransition(20, logoImg);

        pizzaStartButton.setVisible(false);
        optionsButton.setVisible(false);
        exitButton.setVisible(false);

        volumeSliderStartDrag();
        radioButtonArray.add(easyRB);
        radioButtonArray.add(mediumRB);
        radioButtonArray.add(hardRB);

        // animateTitle(titleVBox, titleHBox1, titleHBox2);

        this.timer = new Timer();
        TimerTask task = new TimerTask() {

            @Override
            public void run() {
            }
        };
        timer.scheduleAtFixedRate(task, 0, 30);

        this.startTimerToRunOnce = new Timer();

        this.startTimerButtons = new Timer();
        TimerTask startTaskButton1 = new TimerTask() {

            @Override
            public void run() {
                if (!areButtonsTransitioned) {
                    playFadeInTransition(3, menuBackPane);
                    pizzaStartButton.setVisible(true);
                    playFadeInTransition(.5, pizzaStartButton);
                    skipButton.setVisible(false);
                    overallFade.setVisible(false);
                    soundMessageImgView.setVisible(false);
                }
            }
        };
        startTimerToRunOnce.schedule(startTaskButton1, 30150);
        this.startTimerButtons = new Timer();
        TimerTask startTaskButton2 = new TimerTask() {

            @Override
            public void run() {
                if (!areButtonsTransitioned) {
                    optionsButton.setVisible(true);
                    playFadeInTransition(.5, optionsButton);
                }
            }
        };
        startTimerToRunOnce.schedule(startTaskButton2, 30600 - 60);
        this.startTimerButtons = new Timer();
        TimerTask startTaskButton3 = new TimerTask() {

            @Override
            public void run() {
                if (!areButtonsTransitioned) {
                    exitButton.setVisible(true);
                    playFadeInTransition(.5, exitButton);

                }
            }
        };
        startTimerToRunOnce.schedule(startTaskButton3, 31200 - 135);
        this.startTimerButtons = new Timer();
        TimerTask startTaskCredits = new TimerTask() {

            @Override
            public void run() {
                if (!areButtonsTransitioned) {
                    animateText(creditsLabel,
                            "Made Possible By: Danny Ghrist, Caleb Pierce, Sarah Halverson, Amalia Talijancic, & Carlos Martinez");
                    areButtonsTransitioned = true;
                }
            }
        };
        startTimerToRunOnce.schedule(startTaskCredits, 32000);

        randomizedTranslationAnimation(titleVBox, titleHBox1, titleHBox2, -600, 600, -400, 900);
        linesAnimation();
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

            // Determine which button was pressed.
            Button buttonPushed = (Button) event.getSource();

            String newScene = "";

            // Determines which button was pushed and loads that FXML Scene.
            if (buttonPushed.getId().equals("pizzaStartButton")) {
                newScene = "Mission.fxml";
                playSound("youaresodead");
            } else if (buttonPushed.getId().equals("optionsButton")) {
                playSound("buttonclick");
                newScene = "optionsButton";
                toggleOptionsButtons();
            } else if (buttonPushed.getId().equals("exitButton")) {
                playSound("buttonclick");
                newScene = null;
            } else if (buttonPushed.getId().equals(null)) {
                System.out.println("IT'S ALL WRONG, WHAT HAVE YOU DONE!!!");
            }

            // Exit the program if the scene button clicked on is null.
            if (newScene == null) {
                this.timer.cancel();
                Stage stage = (Stage) exitButton.getScene().getWindow();
                stage.close();
            } else if (newScene.equalsIgnoreCase("optionsButton")) {
                // Don't do anything, no new scene needs to be loaded.
            } else {
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
                mediaPlayer.stop();
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
     * This method exists for aesthetic purposes. it sets the fill gradient for the
     * slider.
     */
    public void volumeSliderStartDrag() {
        Double newVal = volumeSlider.getValue();
        double percentage = 100.0 * newVal.doubleValue() / volumeSlider.getMax();
        String style = String.format(
                // in the String format,
                // %1$.1f%% gives the first format argument ("1$"),
                // i.e. percentage, formatted to 1 decimal place (".1f").
                // Note literal % signs must be escaped ("%%")
                "-track-color: linear-gradient(to right, " + "#ba3702 0%%, " + "#ba3702 %1$.1f%%, "
                        + "-default-track-color %1$.1f%%, " + "-default-track-color 100%%); " + "\n-fx-base: #ba3702;",
                percentage);
        volumeSlider.setStyle(style);

        volumeSlider.valueProperty().addListener((obs, oldValue, newValue) -> {
            double percentage2 = 100.0 * newValue.doubleValue() / volumeSlider.getMax();
            String style2 = String.format(
                    // in the String format,
                    // %1$.1f%% gives the first format argument ("1$"),
                    // i.e. percentage, formatted to 1 decimal place (".1f").
                    // Note literal % signs must be escaped ("%%")
                    "-track-color: linear-gradient(to right, " + "#ba3702 0%%, " + "#ba3702 %1$.1f%%, "
                            + "-default-track-color %1$.1f%%, " + "-default-track-color 100%%); "
                            + "\n-fx-base: #ba3702;",
                    percentage2);
            volumeSlider.setStyle(style2);
            Main.user.setVolume(volumeSlider.getValue() / 100.0);
        });
    }

    /**
     * This method exists for aesthetic purposes, called when the user is finished
     * dragging volume slider.
     */
    public void volumeSliderEndDrag() {
        Double playerVolume = volumeSlider.getValue();

        // Set the master volume variable
        Main.user.setVolume(playerVolume / 100.0);
    }

    /**
     * This method handles when the cancel options button is clicked. it loads the
     * original values from the config file.
     * 
     * @throws FileNotFoundException
     */
    public void cancelButtonClicked() throws FileNotFoundException {
        loadConfig();
        toggleOptionsButtons();
        playSound("buttonClick");
    }

    /**
     * This method handles when the save options button is clicked. it writes to the
     * option values to the config file.
     * 
     * @throws IOException
     */
    public void saveButtonClicked() throws IOException {
        FileWriter file = new FileWriter("src/application/config/config.txt");
        PrintWriter write = new PrintWriter(file);
        String volumeString = "" + ((int) volumeSlider.getValue());
        String difficultyString = "";
        if (easyRB.isSelected()) {
            difficultyString = "Demogorgon";
        } else if (mediumRB.isSelected()) {
            difficultyString = "Mind Flayer";
        } else if (hardRB.isSelected()) {
            difficultyString = "Vecna";
        }

        // Set the difficulty in the Player class to set up time limits and number of
        // pizzas to make.
        Main.user.setDifficulty(difficultyString);

        write.write((Double.parseDouble(volumeString) / 100.0) + "\n" + difficultyString);
        write.close();
        file.close();
        volumeSlider.setValue(Main.user.getVolume() * 100.0);
        playSound("buttonClick");

        // Save to a config.txt file
        toggleOptionsButtons();
    }

    /**
     * This method will invert the visibility of the main buttons and option buttons
     */
    public void toggleOptionsButtons() {
        menuButtonsVBox.setVisible(!menuButtonsVBox.isVisible());
        optionsButtonsPane.setVisible(!optionsButtonsPane.isVisible());
    }

    /**
     * This method will be called when any radio button is clicked.
     * 
     * @param event Listens for event (ActionEvent)
     */
    public void radioButtonClicked(ActionEvent event) {
        // Uncheck radio buttons that weren't clicked
        RadioButton radioButtonPushed = (RadioButton) event.getSource();
        if (radioButtonPushed.getId().equals("easyRB")) {
            if (radioButtonPushed.isSelected()) {
                mediumRB.setSelected(false);
                hardRB.setSelected(false);
            } else
                radioButtonPushed.setSelected(true);
        }
        if (radioButtonPushed.getId().equals("mediumRB")) {
            if (radioButtonPushed.isSelected()) {
                easyRB.setSelected(false);
                hardRB.setSelected(false);
            } else
                radioButtonPushed.setSelected(true);
        }
        if (radioButtonPushed.getId().equals("hardRB")) {
            if (radioButtonPushed.isSelected()) {
                mediumRB.setSelected(false);
                easyRB.setSelected(false);
            } else
                radioButtonPushed.setSelected(true);
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

    /**
     * This method is responsible for loading values from the config.txt file. it is
     * first called on initialize.
     * 
     * @throws FileNotFoundException
     */
    public void loadConfig() throws FileNotFoundException {
        File file = new File("src/application/config/config.txt");
        Scanner scan = new Scanner(file);
        String storedVolume = scan.nextLine();
        String storedDifficulty = scan.nextLine();
        if (storedDifficulty.contentEquals("Demogorgon")) {
            mediumRB.setSelected(false);
            hardRB.setSelected(false);
            easyRB.setSelected(true);
        }
        if (storedDifficulty.contentEquals("Mind Flayer")) {
            easyRB.setSelected(false);
            hardRB.setSelected(false);
            mediumRB.setSelected(true);
        }
        if (storedDifficulty.contentEquals("Vecna")) {
            mediumRB.setSelected(false);
            easyRB.setSelected(false);
            hardRB.setSelected(true);
        }

        // Set the difficulty in the Player class to set up time limits and number of
        // pizzas to make.
        Main.user.setDifficulty(storedDifficulty);

        Main.user.setVolume(Double.parseDouble(storedVolume));
        volumeSlider.setValue(Main.user.getVolume() * 100.0);
        scan.close();
    }

    /**
     * Handles the skip introduction button to skip the introduction animation.
     */
    public void skipButtonClicked() {

        pizzaStartButton.setVisible(true);
        optionsButton.setVisible(true);
        exitButton.setVisible(true);
        stretch = .42;
        skipButton.setVisible(false);
        playSound("buttonClick");
        areButtonsTransitioned = true;
        for (int i = 0; i < arrayOfAnimations.size(); i++) {
            arrayOfAnimations.get(i).stop();
            arrayOfAnimations.get(i).setDuration(Duration.millis(1));
            arrayOfAnimations.get(i).play();
        }
        fadeInAnimation.stop();
        fadeInAnimation.setDuration(Duration.millis(1));
        fadeInAnimation.play();
        overallFade.setVisible(false);
        fadeInSoundAnimation.stop();
        fadeInSoundAnimation.setDuration(Duration.millis(1));
        fadeInSoundAnimation.play();
        soundMessageImgView.setVisible(false);
        animateText(creditsLabel,
                "Made Possible By: Danny Ghrist, Caleb Pierce, Sarah Halverson, Amalia Talijancic, & Carlos Martinez");
        playFadeInTransition(.01, menuBackPane);
    }

    /**
     * Fades out the sound.
     * 
     * @param seconds Number of seconds to fade out (double)
     * @param node    The Node to act upon (Node)
     */
    public void playFadeOutSoundMessageTransition(double seconds, Node node) {
        FadeTransition transition = new FadeTransition(Duration.seconds(seconds), node);
        transition.setFromValue(1);
        transition.setToValue(0.0);
        transition.play();
        fadeInSoundAnimation = transition;
    }

    /**
     * Hard-coded animations for the lines.
     */
    public void linesAnimation() {
        int translationTime = 30000;
        TranslateTransition ttLL = new TranslateTransition(Duration.millis(translationTime), lineLeft);
        TranslateTransition ttLR = new TranslateTransition(Duration.millis(translationTime), lineRight);
        TranslateTransition ttLT = new TranslateTransition(Duration.millis(translationTime), lineTop);
        TranslateTransition ttLL2 = new TranslateTransition(Duration.millis(translationTime), lineLeft2);
        TranslateTransition ttLR2 = new TranslateTransition(Duration.millis(translationTime), lineRight2);
        TranslateTransition ttLB = new TranslateTransition(Duration.millis(translationTime), lineBottom);
        ttLL.setToX(-40);
        ttLL.setToY(-45);
        ttLR.setToX(25);
        ttLR.setToY(-45);
        ttLT.setToX(0);
        ttLT.setToY(60);

        ttLB.setToX(lineBottom.getTranslateX());
        ttLB.setToY(lineBottom.getTranslateY());
        ttLR2.setToX(lineRight2.getTranslateX());
        ttLR2.setToY(lineRight2.getTranslateY());
        ttLL2.setToX(lineLeft2.getTranslateX());
        ttLL2.setToY(lineLeft2.getTranslateY());

        ttLL.setFromX(900);
        ttLR.setFromX(700);
        ttLT.setFromX(-1000);

        ttLB.setFromX(1000);
        ttLR2.setFromY(1000);
        ttLL2.setFromY(-380);

        arrayOfAnimations.add(ttLL);
        arrayOfAnimations.add(ttLR);
        arrayOfAnimations.add(ttLT);
        arrayOfAnimations.add(ttLL2);
        arrayOfAnimations.add(ttLR2);
        arrayOfAnimations.add(ttLB);
        ttLL.play();
        ttLR.play();
        ttLT.play();
        ttLL2.play();
        ttLR2.play();
        ttLB.play();
    }

    /**
     * Randomized a Translation animation.
     * 
     * @param titleVB  (Vbox)
     * @param titleHB1 (HBox)
     * @param titleHB2 (HBox)
     * @param minX     (int)
     * @param maxX     (int)
     * @param minY     (int)
     * @param maxY     (int)
     */
    public void randomizedTranslationAnimation(VBox titleVB, HBox titleHB1, HBox titleHB2, int minX, int maxX, int minY,
            int maxY) {
        ObservableList<Node> listH1 = titleHB1.getChildren();
        for (int h1 = 0; h1 < listH1.size(); h1++) {
            TranslateTransition ttH1 = new TranslateTransition(Duration.millis(30000), listH1.get(h1));
            arrayOfAnimations.add(ttH1);
            int randomNum = ThreadLocalRandom.current().nextInt(minX, maxX);
            ttH1.setFromX(randomNum);
            ttH1.setToX(listH1.get(h1).getTranslateX());
            randomNum = ThreadLocalRandom.current().nextInt(minY, maxY);
            ttH1.setFromY(randomNum);
            ttH1.setToY(listH1.get(h1).getTranslateY());
            ttH1.play();
        }
        ObservableList<Node> listH2 = titleHB2.getChildren();
        for (int h2 = 0; h2 < listH2.size(); h2++) {
            TranslateTransition ttH2 = new TranslateTransition(Duration.millis(30000), listH2.get(h2));
            arrayOfAnimations.add(ttH2);
            int randomNum = ThreadLocalRandom.current().nextInt(minX, maxX);
            ttH2.setFromX(randomNum);
            ttH2.setToX(listH2.get(h2).getTranslateX());
            randomNum = ThreadLocalRandom.current().nextInt(minY, maxY);
            ttH2.setFromY(randomNum);
            ttH2.setToY(listH2.get(h2).getTranslateY());
            ttH2.play();
        }
    }

    /**
     * 
     * 
     * @param val double value (double)
     * @return (double)
     */
    public static double exp(double val) {
        final long tmp = (long) (1512775 * val + (1072693248 - 60801));
        return Double.longBitsToDouble(tmp << 32);
    }
}