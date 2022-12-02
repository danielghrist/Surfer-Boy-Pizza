package application.controller;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import application.Main;
import application.model.Ingredient;
import application.model.Pizza;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 *
 * Controller for the GameView FXML. Allows a user to drag and drop toppings
 * from the bottom menu onto the pizza image in the center of the BorderPane.
 * Upon load a random list of ingredients is shown on the left side and those
 * are the toppings the user needs to add to the pizza. The dough is shown by
 * default.
 * 
 * CS3443-004 - Fall 2022
 *
 * @author Danny Ghrist (kda458)
 *
 */
public class GameViewController extends Controllers implements EventHandler<ActionEvent>, Initializable {

    private Pizza buildPizza;
    private ArrayList<Label> ingredientLabels;

    @FXML
    private BorderPane mainBorderPane;

    @FXML
    private StackPane centerStackPane;

    @FXML
    private VBox leftVbox, rightVbox;

    @FXML
    private ImageView doughImage, vecnaClockImage, sauceImage, topping1Image, topping2Image, topping3Image,
            topping4Image, sauceTarget, topping1Target, topping2Target, topping3Target, topping4Target;

    @FXML
    private Label pizzaLabel, ingredient1Label, ingredient2Label, ingredient3Label, ingredient4Label, ingredient5Label,
            ingredient6Label, numPizzasLabel;

    @FXML
    private Text countdownText;

    private Timer timer;
    private int counter;

    /**
     * Everything to be initialized upon the initial loading of the Scene (i.e,
     * animations, music, timers, etc...)
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        playMusic("VecnaClockSound");

        Main.user.setNumPizzasMade(0);
        this.buildPizza = new Pizza();
        this.buildPizza.setRandomIngredients();
        this.setToppingLabels();

        numPizzasLabel.setText(String.valueOf(Main.user.getNumPizzasMade()) + " / "
                + String.valueOf(Main.user.getNumPizzasToMake()) + " pizzas");

        /**
         * Timer to run until the time runs out or the player finishes making the
         * required number of pizzas.
         */
        this.counter = (60 * Main.user.getCountdownMinutes());
        this.timer = new Timer();
        this.timer.scheduleAtFixedRate(new TimerTask() {

            int minutes, seconds;

            @Override
            public void run() {
                if (counter > 0) {
                    minutes = counter / 60;
                    seconds = counter % 60;
                    countdownText.setText(String.format("%d:%02d\n", minutes, seconds));
                    counter--;

                } else {
                    /**
                     * Switch to game over screen if timer reaches 0. Redirect the player to the
                     * LoseView.fxml
                     */
                    cancel();
                    Platform.runLater(() -> {
                        loadScene("LoseView.fxml");
                    });
                }
            }
        }, 0, 1000);

        pizzaLabel.setText("Drag the Pizza into the center box.");
    }

    /**
     * Not used.
     */
    @Override
    public void handle(ActionEvent event) {
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

    /**
     * This is tied to the Event Handler onDragDetected for each of the images to be
     * dragged, and handles the start of the drag and drop process.
     * 
     * @param event Event handler for dragging pizza topping images (MouseEvent)
     */
    @FXML
    public void handleDragDetected(MouseEvent event) {
        /**
         * MainController type and handler method types: public class MainController
         * implements EventHandler<MouseEvent> makeDraggable(MouseEvent event)
         */

        // Drag was detected, start drag-and-drop gesture

        // Create Dragboard and ClipboardContent Objects for use when dragging.
        Dragboard db = null;
        ClipboardContent content = new ClipboardContent();

        if (event.getSource() == sauceImage) {
            db = sauceImage.startDragAndDrop(TransferMode.ANY);
            content.putImage(sauceImage.getImage());
        } else if (event.getSource() == topping1Image) {
            db = topping1Image.startDragAndDrop(TransferMode.ANY);
            content.putImage(topping1Image.getImage());
        } else if (event.getSource() == topping2Image) {
            db = topping2Image.startDragAndDrop(TransferMode.ANY);
            content.putImage(topping2Image.getImage());
        } else if (event.getSource() == topping3Image) {
            db = topping3Image.startDragAndDrop(TransferMode.ANY);
            content.putImage(topping3Image.getImage());
        } else if (event.getSource() == topping4Image) {
            db = topping4Image.startDragAndDrop(TransferMode.ANY);
            content.putImage(topping4Image.getImage());
        }

        // Puts an image on Dragboard
        db.setContent(content);
        event.consume();
    }

    /**
     * This is tied to the Event Handler onDragOver for each of the target images to
     * be dragged to, and handles when a dragged object is over a target drop
     * source.
     * 
     * @param event Event handler for detecting DragOver pizza topping images
     *              (DragEvent)
     */
    public void handleDragOver(DragEvent event) {
        /* data is dragged over the target */

        // Accept it only if it has an Image.
        if (event.getDragboard().hasImage()) {
            // Allow for both copying and moving, whatever user chooses
            event.acceptTransferModes(TransferMode.ANY);
            event.consume();
        }
    }

    /**
     * This is tied to the Event Handler onDragEntered for the target source to be
     * dragged to, and handles when a dragged object has entered the bounds of a
     * target drop source.
     * 
     * @param event Event handler for detecting DragEntered pizza topping images
     *              (DragEvent)
     */
    @FXML
    public void handleDragEntered(DragEvent event) {
        // The drag-and-drop gesture entered the target
        // Show to the user that it is an actual gesture target
        if (event.getDragboard().hasImage()) {
            pizzaLabel.setText("Now Drop The Topping On the Pizza.");
        }
        event.consume();
    }

    /**
     * This is tied to the Event Handler onDragEntered for the target source to be
     * dragged to, and handles when a dragged object has exited the bounds of a
     * target drop source.
     * 
     * @param event Event handler for detecting DragExited pizza topping images
     *              (DragEvent)
     */
    @FXML
    public void handleDragExited(DragEvent event) {
        /* the drag-and-drop gesture exited the target */
        /* show to the user that it is an actual gesture target */
        if (event.getDragboard().hasImage()) {
            pizzaLabel.setText("Drag The Topping Onto the Pizza.");
        }
        if (Main.user.isPlayerFinished()) {
            this.timer.cancel();
            this.loadScene("DrivingView.fxml");
        }
        event.consume();
    }

    /**
     * This is tied to the Event Handler onDragDropped for the target source to be
     * dragged to, and handles when a dragged object has been dropped into a target
     * drop source.
     * 
     * @param event Event handler for detecting DragExited pizza topping images
     *              (DragEvent)
     */
    @FXML
    public void handleDroppedImage(DragEvent event) {

        if (event.getDragboard().hasImage()) {
            if (event.getGestureSource() == sauceImage) {
                setToppingInfo(sauceImage, sauceTarget, this.getLabel("Pizza Sauce"),
                        this.ingredientLabels.indexOf(this.getLabel("Pizza Sauce")));
                playSound("sauceEffect");
            } else if (event.getGestureSource() == topping1Image) {
                setToppingInfo(topping1Image, topping1Target, this.getLabel("Ham"),
                        this.ingredientLabels.indexOf(this.getLabel("Ham")));
                playSound("placeIngredientEffect");
            } else if (event.getGestureSource() == topping2Image) {
                setToppingInfo(topping2Image, topping2Target, this.getLabel("Mushroom"),
                        this.ingredientLabels.indexOf(this.getLabel("Mushroom")));
                playSound("placeIngredientEffect");
            } else if (event.getGestureSource() == topping3Image) {
                setToppingInfo(topping3Image, topping3Target, this.getLabel("Pineapple"),
                        this.ingredientLabels.indexOf(this.getLabel("Pineapple")));
                playSound("placeIngredientEffect");
            } else if (event.getGestureSource() == topping4Image) {
                setToppingInfo(topping4Image, topping4Target, this.getLabel("Onion"),
                        this.ingredientLabels.indexOf(this.getLabel("Onion")));
                playSound("placeIngredientEffect");
            }
            pizzaLabel.setText("You Dropped The Topping Onto the Pizza!!!");
        }

        /*
         * Logic for checking if pizza is done being built and if Player has finished
         * building all required pizzas. As well as creating new pizzas and resetting
         * topping labels and images.
         */
        if (this.buildPizza.isFinished() && Main.user.isPlayerFinished() == false) {
            Main.user.addPizzaMade();

            // Update the Text of the numPizzaText with pizzas made/need to make.
            numPizzasLabel.setText(String.valueOf(Main.user.getNumPizzasMade()) + " / "
                    + String.valueOf(Main.user.getNumPizzasToMake()) + " pizzas");

            // Remove Topping Labels Text.
            for (Label topping : this.ingredientLabels) {
                topping.setText("");
                topping.setTextFill(Color.RED);
            }
            this.ingredientLabels.clear();

            // Build new pizza with random toppings.
            this.buildPizza = new Pizza();
            this.buildPizza.setRandomIngredients();

            // Rebuild Labels with new pizza's random toppings.
            this.setToppingLabels();

            // Reset the topping images to null.
            this.sauceTarget.setImage(null);
            this.topping1Target.setImage(null);
            this.topping2Target.setImage(null);
            this.topping3Target.setImage(null);
            this.topping4Target.setImage(null);
        } else {
        }
        event.consume();
    }

    /**
     * Sets the Labels of the random toppings to match their topping for use with
     * the drag and drop functionality and determining whether a topping has been
     * placed on the pizza already.
     */
    public void setToppingLabels() {
        // Set the ingredient labels to be composed of what is in the random Array.
        // Set counter variable to 2, that is first topping label Node in leftVbox.
        this.ingredientLabels = new ArrayList<Label>();
        // i==2 because the first two nodes in the lefVbox are not topping Label nodes.
        int i = 2;
        List<Node> leftVboxLabels = (List<Node>) leftVbox.getChildren();
        for (Ingredient ingredient : this.buildPizza.getIngredients()) {
            Label label = (Label) leftVboxLabels.get(i);
            label.setText(ingredient.getName());
            this.ingredientLabels.add(label);
            label.setId(this.buildPizza.getIngredients().get(i - 2).getName());
            label.setStyle("-fx-text-fill: #ff3000;");
            i++;
        }
    }

    /**
     * Sets the topping information for a topping and updates the label for that
     * topping to determine if it has already been added or not so that it will not
     * update the target image if the topping has already been added. Also subtracts
     * time from the timer counter variable if the topping is already on the pizza
     * or doesn't need to be on the pizza.
     * 
     * @param sourceImage     The image being dragged (ImageView)
     * @param targetImage     The image to be dropped into (ImageView)
     * @param ingredientLabel The Label of the item being dragged (Label)
     * @param i               Counter variable used to obtain individual Ingredient
     *                        (int)
     */
    private void setToppingInfo(ImageView sourceImage, ImageView targetImage, Label ingredientLabel, int i) {
        if (i < 0) {

//            //***TESTING***
//            //vecnas face shows up if you make an error
//            
//    		Image image = new Image("file/application/images/vecnaPixelImage.jpg");
//    		vecnaClockImage.setImage(image);
//            

            this.counter -= Main.user.getErrorReductionSecs();
        } else if (!this.buildPizza.getIngredients().get(i).isOnPizza() /* !this.pizzaSauce.isOnPizza() */) {
            targetImage.setImage(sourceImage.getImage());
            this.buildPizza.getIngredients().get(i).setOnPizza(true);
            ingredientLabel.setText("ADDED!!");
            ingredientLabel.setTextFill(Color.NAVAJOWHITE);
        } else if (this.buildPizza.getIngredients().get(i).isOnPizza()) {
//            
//            //***TESTING***
//            //vecnas face shows up if you make an error
//    		Image image = new Image("file:application/images/vecnaPixelImage.jpg");
//    		vecnaClockImage.setImage(image);
//            

            ingredientLabel.setText("NO MORE!");
            this.counter -= Main.user.getErrorReductionSecs();
        }
    }

    /**
     * Return the Label of the specified topping to be used in updating Label text.
     * 
     * @param topping Name of the topping to find Label info (String)
     * @return The Label that the topping is currently in (Label)
     */
    private Label getLabel(String topping) {
        Label returnLabel = null;
        for (Label label : this.ingredientLabels) {

            if (label.getId() != null && label.getId().equalsIgnoreCase(topping)) {
                returnLabel = label;
            }
        }
        return returnLabel;
    }

    /**
     * Uses sceneName in the form of fxml document file name with .fxml extension to
     * load a new scene.
     * 
     * @param sceneName The fxml file name to be loaded (String)
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
            mediaPlayer.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}