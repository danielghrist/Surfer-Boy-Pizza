package application.model;

/**
 * Contains information for the Player to be used throughout the program to keep
 * track of their progress.
 * 
 * CS3443-004 - Fall 2022
 *
 * @author Danny Ghrist (kda458)
 *
 */
public class Player {

    // Time constants depending on difficulty selected, saved to the save file.
    public final int DEMOGORGON_COUNTDOWN_MINUTES = 5;
    public final int DEMOGORGON_TIME_REDUCTION_FOR_ERROR = 5;
    public final int DEMOGORGON_PIZZAS_TO_MAKE = 5;
    public final int MINDFLAYER_COUNTDOWN_MINUTES = 3;
    public final int MINDFLAYER_TIME_REDUCTION_FOR_ERROR = 15;
    public final int MINDFLAYER_PIZZAS_TO_MAKE = 10;
    public final int VECNA_COUNTDOWN_MINUTES = 2;
    public final int VECNA_TIME_REDUCTION_FOR_ERROR = 30;
    public final int VECNA_PIZZAS_TO_MAKE = 20;

    private String name;
    private int numPizzasToMake;
    private int numPizzasMade;
    private int numPizzasRemaining;
    private int countdownMinutes;
    private int errorReductionSecs;
    private double volume;

    /**
     * Constructs Player and sets the difficulty which in turn sets the count down
     * timer, number of pizzas to make and the reduction in seconds for errors.
     */
    public Player() {
        this.setDifficulty("default");
        this.numPizzasMade = 0;
    }

    /**
     * Set the difficulty to either easy (Demogorgon), medium (default/Mind Flayer),
     * or hard (Vecna) with the corresponding time and pizzas to make numbers.
     * 
     * @param difficulty The difficulty the user chooses or default (String)
     */
    public void setDifficulty(String difficulty) {
        if (difficulty.equalsIgnoreCase("demogorgon")) {
            this.numPizzasToMake = this.DEMOGORGON_PIZZAS_TO_MAKE;
            this.countdownMinutes = this.DEMOGORGON_COUNTDOWN_MINUTES;
            this.errorReductionSecs = this.DEMOGORGON_TIME_REDUCTION_FOR_ERROR;
        } else if (difficulty.equalsIgnoreCase("mind flayer")) {
            this.numPizzasToMake = this.MINDFLAYER_PIZZAS_TO_MAKE;
            this.countdownMinutes = this.MINDFLAYER_COUNTDOWN_MINUTES;
            this.errorReductionSecs = this.MINDFLAYER_TIME_REDUCTION_FOR_ERROR;
        } else if (difficulty.equalsIgnoreCase("vecna")) {
            this.numPizzasToMake = this.VECNA_PIZZAS_TO_MAKE;
            this.countdownMinutes = this.VECNA_COUNTDOWN_MINUTES;
            this.errorReductionSecs = this.VECNA_TIME_REDUCTION_FOR_ERROR;
        } else {
            // Set to default/medium/Mind Flayer difficulty as default.
            this.numPizzasToMake = this.MINDFLAYER_PIZZAS_TO_MAKE;
            this.countdownMinutes = this.MINDFLAYER_COUNTDOWN_MINUTES;
            this.errorReductionSecs = this.MINDFLAYER_TIME_REDUCTION_FOR_ERROR;
        }
    }

    /**
     * Returns the number of pizzas that the Player needs to complete making.
     * 
     * @return The number of pizzas Player needs to make (int)
     */
    public int calculatePizzasRemaining() {
        return this.getNumPizzasToMake() - this.getNumPizzasMade();
    }

    /**
     * Returns true if the Player is done making pizzas or false if they still have
     * pizzas remaining to be made.
     * 
     * @return If all the pizzas have been completed by Player (boolean)
     */
    public boolean isPlayerFinished() {
        if (this.calculatePizzasRemaining() < 1) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @return The Player name (String)
     */
    public String getName() {
        return this.name;
    }

    /**
     * @param name The Player name to set (String)
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return The numPizzasToMake (int)
     */
    public int getNumPizzasToMake() {
        return this.numPizzasToMake;
    }

    /**
     * @param numPizzasToMake The numPizzasToMake to set (int)
     */
    public void setNumPizzasToMake(int numPizzasToMake) {
        this.numPizzasToMake = numPizzasToMake;
    }

    /**
     * @return The numPizzasMade (int)
     */
    public int getNumPizzasMade() {
        return this.numPizzasMade;
    }

    /**
     * @param numPizzasMade The numPizzasMade to set (int)
     */
    public void setNumPizzasMade(int numPizzasMade) {
        this.numPizzasMade = numPizzasMade;
    }

    /**
     * @return The numPizzasRemaining (int)
     */
    public int getNumPizzasRemaining() {
        return this.numPizzasRemaining;
    }

    /**
     * Add a pizza to the Player's pizzaMade count;
     */
    public void addPizzaMade() {
        this.numPizzasMade++;
    }

    /**
     * Returns the number of minutes player has to make pizzas.
     * 
     * @return the countdownMinutes (int)
     */
    public int getCountdownMinutes() {
        return this.countdownMinutes;
    }

    /**
     * Sets the number of minutes player has to make pizzas.
     * 
     * @param countdownMinutes The countdownMinutes to set (int)
     */
    public void setCountdownMinutes(int countdownMinutes) {
        this.countdownMinutes = countdownMinutes;
    }

    /**
     * Returns the number of seconds to deduct if player makes error.
     * 
     * @return The errorReductionSecs (int)
     */
    public int getErrorReductionSecs() {
        return this.errorReductionSecs;
    }

    /**
     * Sets the number of seconds to deduct if player makes error.
     * 
     * @param errorReductionSecs the errorReductionSecs to set (int)
     */
    public void setErrorReductionSecs(int errorReductionSecs) {
        this.errorReductionSecs = errorReductionSecs;
    }

    /**
     * Returns the volume set using the slider in the Options menu on the Main view.
     * 
     * @return The volume for all scenes in options (double)
     */
    public double getVolume() {
        return this.volume;
    }

    /**
     * Sets the volume using the slider in the Options menu on the Main view.
     * 
     * @param volume The volume to set for all scenes (double)
     */
    public void setVolume(double volume) {
        this.volume = volume;
    }
}
