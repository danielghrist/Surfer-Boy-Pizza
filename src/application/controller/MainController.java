package application.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.text.Position;

import application.Main;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
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
public class MainController implements EventHandler<ActionEvent>, Initializable {

    @FXML
    AnchorPane titlePane;

    @FXML
    ImageView logoImg, cursorReferenceNormal,/*pizzaTruck, pixelArgyle,*/ blackFadeImg1, blackFadeImg2, blackFadeImg3, blackFadeImg4;

    @FXML
    Button buttonPushed, pizzaStartButton, optionsButton, exitButton, skipButton;

    MediaPlayer mediaPlayer, mediaPlayerSFX, mediaBackground;

    @FXML
    MediaView backgroundMedia;
    
    @FXML
    Text titleText;
    
    @FXML
    VBox titleVBox;
    
    @FXML
    HBox titleHBox1, titleHBox2;
    
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
	;
    
	public static double exp(double val) {
	    final long tmp = (long) (1512775 * val + (1072693248 - 60801));
	    return Double.longBitsToDouble(tmp << 32);
	}

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	
        //AnchorPane.setBottomAnchor(pizzaTruck, 50.0);
        //AnchorPane.setBottomAnchor(pixelArgyle, 25.0);
        //AnchorPane.setBottomAnchor(backgroundMedia, 0.0);
        // TODO: DELETE THIS TEST CODE BEFORE FINALIZING
//        AnchorPane.setTopAnchor(logoImg, 100.0);
//        AnchorPane.setLeftAnchor(logoImg, 800 - logoImg.getFitWidth());
//        AnchorPane.setRightAnchor(logoImg, 800 - logoImg.getFitWidth());  
        music();
        

        String mediaURL = "src/application/videos/mainMenuBackground.mp4";
        Media media1 = new Media(Paths.get(mediaURL).toUri().toString());
        mediaBackground = new MediaPlayer(media1);
        mediaBackground.setAutoPlay(true);
        mediaBackground.setCycleCount(-1);
        backgroundMedia.setMediaPlayer(mediaBackground);
        playFadeTransition(2, titleVBox);
        // Play FadeTransitions
        playFadeTransition(20, logoImg);
        //playFadeTransition(15, pizzaStartButton);
        //playFadeTransition(15, optionsButton);
        //playFadeTransition(15, exitButton);

        blackFadeImg1.setVisible(false);
        blackFadeImg2.setVisible(false);
        blackFadeImg3.setVisible(false);
        blackFadeImg4.setVisible(false);
        /*
        playFadeTransition(15, blackFadeImg2);
        playFadeTransition(15, blackFadeImg3);
        playFadeTransition(15, blackFadeImg4);
*/
		pizzaStartButton.setVisible(false);
		optionsButton.setVisible(false);
		exitButton.setVisible(false);
		
        //animateTitle(titleVBox, titleHBox1, titleHBox2);
		
        this.timer = new Timer();
        TimerTask task = new TimerTask() {

			@Override
			public void run() {
			    //finish = System.currentTimeMillis();
			    //lastTimeElapsed = timeElapsed;
				timeElapsed += 1.0;
				
				// TODO Auto-generated method stub
				//animateTitle(titleVBox, titleHBox1, titleHBox2);
				//System.out.print("running animation\n");
			
				//stretch = (500*((exp(-timeElapsed/3000.0)))-.1);
				stretch -= .41;
				//System.out.print("done calculation\n");
				
		    	if(stretch>0)
		    	{
					//System.out.print("set stretch\n");
		    		titleVBox.setSpacing(stretch*2);
		    		titleHBox1.setSpacing(stretch*1.2);
		    		titleHBox2.setSpacing(stretch*1.2);
		    	}
		    	else if(stretch <= 0)
		    	{
		    		pizzaStartButton.setVisible(true);
		    		optionsButton.setVisible(true);
		    		exitButton.setVisible(true);
		            //blackFadeImg1.setVisible(true);

		    		if(!areButtonsTransitioned&&skipButton.isVisible()) {
			    		pizzaStartButton.setOpacity(0);
			    		optionsButton.setOpacity(0);
			    		exitButton.setOpacity(0);
		            playFadeTransition(3, pizzaStartButton);
		            playFadeTransition(3, optionsButton);
		            playFadeTransition(3, exitButton);
		            skipButton.setVisible(false);
		            //playFadeTransition(3, blackFadeImg1);
		            areButtonsTransitioned = true;
		    		}
		    	}
	    		else if(skipButton.isVisible()==false && stretch>0)
	    		{
		    		pizzaStartButton.setOpacity(1);
		    		optionsButton.setOpacity(1);
		    		exitButton.setOpacity(1);
		    		titleVBox.setOpacity(1);
	    		}
			}
                
        };
        timer.scheduleAtFixedRate(task, 0, 30);
        this.startTimerToRunOnce = new Timer();
        TimerTask startTask = new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
		    	try {
					setCursor("normalSelect");
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
        };
        startTimerToRunOnce.schedule(startTask, 100);
    }
    public void ready()
    {

    }
    public void setCursor(String imageName) throws FileNotFoundException
    {
    	Image myImage = new Image(new FileInputStream("src/application/images/"+imageName+".png"));
    	ImageCursor cursor = new ImageCursor(myImage, 0, 0);
    	Scene scene = Main.stage.getScene();
    	scene.getRoot().setCursor(cursor);
    }
    /*
    public void animateTitle(VBox titleVB, HBox titleHB1, HBox titleHB2)
    {
		double Stretch = Duration.millis(secondsToStretch*1000).toMillis()-(timeElapsed/1000);
    	if(Stretch>0)
    	{
    		titleVB.setSpacing(Stretch);
    		titleHB1.setSpacing(Stretch);
    		titleHB2.setSpacing(Stretch);
    	}
    }
    
    public void playAnimateTitle(double seconds, VBox titleVB, HBox titleHB1, HBox titleHB2) {

    }
    */
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
                playSound("buttonclick");
            } else if (buttonPushed.getId().equals("optionsButton")) {
                // TODO: Update this to a new options view once implemented.
                playSound("buttonclick");
                newScene = null;
                System.out.println("OPTIONS TO BE IMPLEMENTED SOON...");
            } else if (buttonPushed.getId().equals("exitButton")) {
                playSound("buttonclick");
                newScene = null;
            } else if (buttonPushed.getId().equals(null)) {
                System.out.println("IT'S ALL WRONG, WHAT HAVE YOU DONE!!!");
            }

            // Exit the program if the scene button clicked on is null.
            if (newScene == null) {
//                Platform.exit();
                Stage stage = (Stage) exitButton.getScene().getWindow();
                stage.close();
            } else {
                // Connect to the FXML (contains our layout) and load it in.
                Parent root = FXMLLoader.load(Main.class.getResource("view/" + newScene));

                // Put the layout onto the scene.
                Scene scene = new Scene(root);

                // Set the scene on the stage that was created in Main.java.
                Main.stage.setScene(scene);
                Main.stage.show();
            	try {
        			setCursor("normalSelect");
        		} catch (FileNotFoundException e) {
        			// TODO Auto-generated catch block
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
    public void buttonEntered() {
        playSound("buttonhover");
    	try {
			setCursor("normalClick");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    public void buttonExit()
    {
    	try {
			setCursor("normalSelect");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    public void skipButtonClicked() {
    	stretch = .42;
    	skipButton.setVisible(false);

    }
    /**
     * Amalia's edits for Stranger Things music/mp3 to play. Creates a new Media
     * object to play the background music.
     */
    public void music() {
        String s = "src/application/audio/StrangerThingsThemeSong.mp3";
        Media h = new Media(Paths.get(s).toUri().toString());
        // Media(getClass().getResource("application/audio/StrangerThingsThemeSong.mp3").toExternalForm());
        mediaPlayer = new MediaPlayer(h);
        mediaPlayer.autoPlayProperty();
        mediaPlayer.setCycleCount(-1);
        mediaPlayer.play();
        // mediaPlayer.setStartTime(Duration.seconds(0));
        // mediaPlayer.setAutoPlay(true);
    }

    /**
     * Creates a new Media object to play sound effects.
     * 
     * @param soundName The name of the sound effect audio (String)
     */
    public void playSound(String soundName) {
        String s = "src/application/audio/" + soundName + ".mp3";
        Media h = new Media(Paths.get(s).toUri().toString());
        mediaPlayerSFX = new MediaPlayer(h);
        mediaPlayerSFX.play();
        // mediaPlayer.setStartTime(Duration.seconds(0));
        // mediaPlayer.setAutoPlay(true);
    }

    /**
     * Apply a FadeTransition on a node given the duration in seconds.
     * 
     * @param seconds Time of the fade in seconds (Double)
     * @param node    The child node to have the transition applied to (Node)
     */
    public void playFadeTransition(double seconds, Node node) {
        FadeTransition transition = new FadeTransition(Duration.seconds(seconds), node);
        transition.setFromValue(0);
        transition.setToValue(1.0);
        transition.play();
    }
}