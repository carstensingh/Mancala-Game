package mancala;
import java.util.ArrayList; 
public class MancalaGame {
    private ArrayList<Player> players;
    private Player currentPlayer;
    private Board board;

    public MancalaGame(Player onePlayer, Player twoPlayer) {
        this.players = new ArrayList<>();
        this.setPlayers(onePlayer, twoPlayer);
        this.board = new Board();
        this.currentPlayer = onePlayer;  // Assuming player one starts
    }

    public void setPlayers(Player onePlayer, Player twoPlayer) {
        this.players.add(onePlayer);
        this.players.add(twoPlayer);
    }

    public ArrayList<Player> getPlayers() {
        return this.players;
    }

    public Player getCurrentPlayer() {
        return this.currentPlayer;
    }

    public void setCurrentPlayer(Player player) {
        this.currentPlayer = player;
    }

    public Board getBoard() {
        return this.board;
    }

    public int getNumStones(int pitNum) {
        return this.board.getNumStones(pitNum);
    }

    public int move(int startPit) {
        return this.board.moveStones(startPit, this.currentPlayer);
    }

    public int getStoreCount(Player player) {
        return player.getStore().getTotalStones();
    }

    public Player getWinner() {
        int playerOneScore = this.getStoreCount(this.players.get(0));
        int playerTwoScore = this.getStoreCount(this.players.get(1));
        if (playerOneScore > playerTwoScore) {
            return this.players.get(0);
        } else if (playerTwoScore > playerOneScore) {
            return this.players.get(1);
        } else {
            return null;  // It's a tie
        }
    }

    public boolean isGameOver() {
        // The game is over if all pits on one side are empty
        return this.board.isSideEmpty(0) || this.board.isSideEmpty(6);
    }

    public void startNewGame() {
        // Start a new game
        this.board.resetBoard();
        this.currentPlayer = players.get(0);  // Assuming player one starts
    }

    @Override
    public String toString() {
        // Represent the game as a string
        StringBuilder sb = new StringBuilder();
        sb.append("Current Player: ").append(this.currentPlayer.getName()).append("\n");
        sb.append("Board: ").append("\n");
        for (Pit pit : board.getPits()) {
            sb.append(pit.getStoneCount()).append(" ");
        }
        sb.append("\n");
        for (Store store : board.getStores()) {
            sb.append(store.getTotalStones()).append(" ");
        }
        return sb.toString();
    }
}
