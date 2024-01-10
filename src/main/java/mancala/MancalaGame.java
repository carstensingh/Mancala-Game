package mancala;
import java.io.Serializable;
import java.util.ArrayList; 

public class MancalaGame implements Serializable{
    private static final long serialVersionUID = 1L;
    private ArrayList<Player> players;
    private Player currentPlayer;
    private int playerNum;

    private GameRules gameRules;


    /**
     * Default constructor for MancalaGame. Initializes the players and uses AyoRules by default.
     */
    public MancalaGame(){
        this.players = new ArrayList<>();
        this.gameRules = new AyoRules();
    }


    /**
     * Constructor for MancalaGame with two players and KalahRules.
     *
     * @param onePlayer The first player.
     * @param twoPlayer The second player.
     */
    public MancalaGame(Player onePlayer, Player twoPlayer) {
        this.players = new ArrayList<>();
        this.gameRules = new KalahRules();
        this.setPlayers(onePlayer, twoPlayer);
        this.currentPlayer = onePlayer;  // Assuming player one starts
        this.playerNum = 1;

        this.gameRules.registerPlayers(onePlayer, twoPlayer);
    }

    /**
     * Constructor for MancalaGame with two players and custom game rules.
     *
     * @param onePlayer The first player.
     * @param twoPlayer The second player.
     * @param rules The custom game rules.
     */
    public MancalaGame(Player onePlayer, Player twoPlayer, GameRules rules) {
        this.players = new ArrayList<>();
        this.gameRules = rules;
        this.setPlayers(onePlayer, twoPlayer);
        this.currentPlayer = onePlayer;  // Assuming player one starts
        this.playerNum = 1;

        this.gameRules.registerPlayers(onePlayer, twoPlayer);
    }
    /**
     * Sets the players for the game.
     *
     * @param onePlayer The first player.
     * @param twoPlayer The second player.
     */
    public void setPlayers(Player onePlayer, Player twoPlayer) {
        this.players.add(onePlayer);
        this.players.add(twoPlayer);
        this.gameRules.registerPlayers(onePlayer, twoPlayer);


    }

    /**
     * Retrieves the list of players in the game.
     *
     * @return The list of players.
     */
    public ArrayList<Player> getPlayers() {
        return this.players;
    }

    /**
     * Retrieves the current player.
     *
     * @return The current player.
     */
    public Player getCurrentPlayer() {
        return this.currentPlayer;
    }

    /**
     * Sets the current player.
     *
     * @param player The player to set as the current player.
     */
    public void setCurrentPlayer(Player player) {
        this.currentPlayer = player;
    }

    /**
     * Sets the game rules for the board.
     *
     * @param newGameRules The new game rules.
     */
    public void setBoard(GameRules newGameRules){
        this.gameRules = newGameRules;
    }

    /**
     * Retrieves the current game rules.
     *
     * @return The current game rules.
     */
    public GameRules getBoard() {
        return this.gameRules;
    }

    /**
     * Retrieves the number of stones in the specified pit.
     *
     * @param pitNum The pit number.
     * @return The number of stones in the specified pit.
     */
    public int getNumStones(int pitNum) {
        int numStones = 0;
        numStones = this.gameRules.getDataStructure().getNumStones(pitNum);
        return numStones;
    }

   /**
     * Moves stones from the specified pit and returns the total number of stones moved.
     *
     * @param startPit The pit from which stones are moved.
     * @return The total number of stones moved.
     * @throws InvalidMoveException If the move is invalid.
     */
    public int move(int startPit)throws InvalidMoveException{
        // Check if the pit belongs to the current player
        int playerIndex = this.players.indexOf(this.currentPlayer);
        int startPitIndex = (playerIndex) * 6 + 1;
        int endPitIndex = startPitIndex + 5;
        int value = 0;
        int stoneCount = 0;
        if ((startPit < startPitIndex || startPit > endPitIndex)) {
            if (startPit > 12 || startPit <= 0){
                throw new InvalidMoveException("Pit number " + startPit + " not found.");
            }else{
                throw new InvalidMoveException("Invalid move. The pit does not belong to the current player.");
            }
            
        }
        // Check if the pit is empty
        if (gameRules.getDataStructure().getNumStones(startPit) == 0) {
            throw new InvalidMoveException("Invalid move. The pit is empty.");
        }
        
        
        try{
            playerNum = 2;
            if(currentPlayer == getPlayers().get(0)){
                playerNum = 1;
            }
            value = this.gameRules.moveStones(startPit, playerNum);
        }catch(Exception e){
           throw new InvalidMoveException(e.getMessage());
        }
    
        for(int i = startPitIndex; i <= endPitIndex; i++){
            stoneCount+= gameRules.getDataStructure().getNumStones(i);
        }
        return stoneCount;
    }

    /**
     * Retrieves the total number of stones in a player's store.
     *
     * @param player The player.
     * @return The total number of stones in the player's store.
     * @throws NoSuchPlayerException If the player is not found.
     */
    public int getStoreCount(Player player)throws NoSuchPlayerException {
        
        if(this.players.indexOf(player) == -1){
            throw new NoSuchPlayerException("Could not find player");
        }
        
        return player.getStore().getTotalStones();
    }

    /**
     * Determines the winner of the game.
     *
     * @return The winning player or null if it's a tie.
     * @throws GameNotOverException If the game is not yet over.
     */
    public Player getWinner() throws GameNotOverException {
        int cleanUp = 0;
        int playerOneScore = 0;
        int playerTwoScore = 0;
        if (!this.isGameOver()) {
            throw new GameNotOverException("The game is not over yet.");
        }
            playerOneScore = this.players.get(0).getStore().getTotalStones();
            playerTwoScore = this.players.get(0).getStore().getTotalStones();
        
        for(cleanUp = 1; cleanUp < 7; cleanUp++){
            playerOneScore += gameRules.getDataStructure().getNumStones(cleanUp);
        }
        this.players.get(1).getStore().emptyStore();
        this.players.get(1).getStore().addStones(playerTwoScore);
        for(cleanUp = 7; cleanUp <= 12; cleanUp++){
            playerTwoScore += gameRules.getDataStructure().getNumStones(cleanUp);
        }
        this.players.get(0).getStore().emptyStore();
        this.players.get(0).getStore().addStones(playerOneScore);
        if (playerOneScore > playerTwoScore) {
            return this.players.get(0);
        } else if (playerTwoScore > playerOneScore) {
            return this.players.get(1);
        } else {
            return null;  // It's a tie
        }
    }

    /**
     * Checks if the game is over.
     *
     * @return True if the game is over, false otherwise.
     */
    public boolean isGameOver() { //used AI Solution
        // The game is over if all pits on one side are empty
        boolean result = true;
        result = this.gameRules.isSideEmpty(1) || this.gameRules.isSideEmpty(7);
        return result;
    }

    /**
     * Returns a string representation of the Mancala game, including the current player, board state, and store counts.
     *
     * @return The string representation of the game.
     */
    @Override
    public String toString() {
        // Represent the game as a string
        StringBuilder sb = new StringBuilder();
        sb.append("Current Player: ").append(this.currentPlayer.getName()).append("\n");
        sb.append("Board: ").append("\n");
        
        // Print the index above each pit
        sb.append(gameRules);
        
        // Print the total stones in each store
        sb.append("Stores: ").append("\n");
        
        // Assuming you have a list of players
        for (Player player : players) {
            sb.append(player);
            sb.append("'s Store: ");
            sb.append(player.getStore()).append("\n");
        }
        
        return sb.toString();  
    }

}

