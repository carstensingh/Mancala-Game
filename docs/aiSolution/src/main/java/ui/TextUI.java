package ui;

import java.util.Scanner;
import mancala.MancalaGame;
import mancala.Player;
import mancala.Board;
import mancala.Store;
import mancala.Pit;
public class TextUI {
    private MancalaGame game;
    private Scanner scanner;

    public TextUI() {
        this.scanner = new Scanner(System.in);
        this.game = new MancalaGame(new Player("Player 1"), new Player("Player 2"));
    }

    public void start() {
        while (!game.isGameOver()) {
            System.out.println(game);  // Print the current game state

            // Get the current player's move
            System.out.println(game.getCurrentPlayer().getName() + "'s turn. Enter pit number to move stones from:");
            int pitNum = scanner.nextInt();

            // Perform the move
            game.move(pitNum);

            // Switch to the other player
            switchPlayer();
        }

        // Announce the winner
        Player winner = game.getWinner();
        if (winner != null) {
            System.out.println(winner.getName() + " wins!");
        } else {
            System.out.println("It's a tie!");
        }
    }

    public void switchPlayer() {
           
        if (game.getCurrentPlayer() == game.getPlayers().get(0)) {
            game.setCurrentPlayer(game.getPlayers().get(1));
        } else {
            game.setCurrentPlayer(game.getPlayers().get(0));
        }
           
    }


    public static void main(String[] args) {
        new TextUI().start();
    }
}
