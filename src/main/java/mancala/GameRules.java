package mancala;

import java.io.Serializable;

/**
 * Abstract class representing the rules of a Mancala game.
 * KalahRules and AyoRules will subclass this class.
 */
public abstract class GameRules implements Serializable{
    private MancalaDataStructure gameBoard;
    private int currentPlayer = 1; // Player number (1 or 2)
    private boolean bonusTurn;

    /**
     * Constructor to initialize the game board.
     */
    public GameRules() {
        gameBoard = new MancalaDataStructure();
    }

    /**
     * Get the number of stones in a pit.
     *
     * @param pitNum The number of the pit.
     * @return The number of stones in the pit.
     */
    public int getNumStones(int pitNum) {
        return gameBoard.getNumStones(pitNum);
    }

    /**
     * Get the game data structure.
     *
     * @return The MancalaDataStructure.
     */
    MancalaDataStructure getDataStructure() {
        return gameBoard;
    }

    /**
     * Check if a side (player's pits) is empty.
     *
     * @param pitNum The number of a pit in the side.
     * @return True if the side is empty, false otherwise.
     */
    boolean isSideEmpty(int pitNum) {
        // This method can be implemented in the abstract class.
        for (int i = pitNum; i <= pitNum + 5; i++) {
            if (gameBoard.getNumStones(i) > 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Set the current player.
     *
     * @param playerNum The player number (1 or 2).
     */
    public void setPlayer(int playerNum) {
        currentPlayer = playerNum;
    }

    /**
     * Perform a move and return the number of stones added to the player's store.
     *
     * @param startPit  The starting pit for the move.
     * @param playerNum The player making the move.
     * @return The number of stones added to the player's store.
     * @throws InvalidMoveException If the move is invalid.
     */
    public abstract int moveStones(int startPit, int playerNum) throws InvalidMoveException;

    /**
     * Distribute stones from a pit and return the number distributed.
     *
     * @param startPit The starting pit for distribution.
     * @return The number of stones distributed.
     */
    public abstract int distributeStones(int startPit);

    /**
     * Capture stones from the opponent's pit and return the number captured.
     *
     * @param stoppingPoint The stopping point for capturing stones.
     * @return The number of stones captured.
     */
    public abstract int captureStones(int stoppingPoint);

    /**
     * Register two players and set their stores on the board.
     *
     * @param one The first player.
     * @param two The second player.
     */
    public void registerPlayers(Player one, Player two) {
        // this method can be implemented in the abstract class.
        Store p1Store = new Store();
        p1Store.setOwner(one);
        one.setStore(p1Store);
        gameBoard.setStore(p1Store, 1);
        Store p2Store = new Store();
        p2Store.setOwner(two);
        two.setStore(p2Store);
        gameBoard.setStore(p2Store, 2);

        /* make a new store in this method, set the owner
         then use the setStore(store,playerNum) method of the data structure*/
    }

    /**
     * Reset the game board by setting up pits and emptying stores.
     */
    public void resetBoard() {
        gameBoard.setUpPits();
        gameBoard.emptyStores();
    }


    public void setBonus(boolean set){
        this.bonusTurn = set;
    }
    
    public boolean getBonus(){
        return bonusTurn;
    }
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        // Print the index above each pit
        for (int i = 0; i < 12; i++) {
            result.append(String.format("%2d ", i+1));
        }
        result.append("\n");

        // Print the stone count in each pit
        for (int i = 1; i < 13; i++) {
            result.append(String.format("%2d ", getDataStructure().getNumStones(i)));
        }
        result.append("\n");
        return result.toString();
    }
}
