package ui;

import java.util.ArrayList;
import java.util.Scanner;
import mancala.MancalaGame;
import mancala.Player;
import mancala.Saver;
import mancala.UserProfile;
import mancala.InvalidMoveException;
import mancala.GameNotOverException;
public class TextUI {
    private MancalaGame game;
    private Scanner scanner;
    private static UserProfile profile1 = new UserProfile("Player 1");
    private static UserProfile profile2 = new UserProfile("Player 2");

    public TextUI() {
        this.scanner = new Scanner(System.in);
        this.game = new MancalaGame(new Player(profile1), new Player(profile2));
    }

    public void start() {
        
        while (!game.isGameOver()) {
            printGameState();
            performMove();
            switchPlayer();
        }
        declareWinner();
    }

    public void printGameState() {
        System.out.println(game);  // Print the current game state
    }

    public void performMove() {
        // Get the current player's move
        int pitNum = -1;
        int result = 0;

        // Perform the move
        boolean validMove = false;
        while(!validMove){
            try{
                System.out.println(game.getCurrentPlayer().getName() + "'s turn. Enter pit # to move stones from:");
                pitNum = scanner.nextInt();
                result = game.move(pitNum); 
                
                validMove = true;
            }catch(InvalidMoveException e){
               
                System.out.println(e.getMessage()); 
            }
        }
    }

    public void switchPlayer() { // used from AI solution
        if (!game.getBoard().getBonus()) {
            if (game.getCurrentPlayer() == game.getPlayers().get(0)) {
                game.setCurrentPlayer(game.getPlayers().get(1));
            } else {
                game.setCurrentPlayer(game.getPlayers().get(0));
            }
        }

    }

    public void declareWinner() {
        try {
            Player winner = game.getWinner();
            game.setCurrentPlayer(winner);
            if (winner != null) {
                System.out.println("=====================================");
                printGameState();
                System.out.println(winner.getName() + " wins! With " + winner.getStoreCount() + " stones.");
            } else {
                System.out.println("It's a tie!");
            }
        } catch (GameNotOverException e) {
            System.out.println(e.getMessage());
        } 
    }



    public static void main(String[] args) {
        TextUI textUI = new TextUI();
        try {
            textUI.start();
    
            // Save UserProfile objects
            

    
            Saver.saveObject(profile1, "player1_profile.ser");
            Saver.saveObject(profile2, "player2_profile.ser");
            Saver.saveObject(textUI.game, "mancala_game.ser");
    
            // Load UserProfile objects
            UserProfile loadedPlayer1Profile = (UserProfile) Saver.loadObject("player1_profile.ser");
            UserProfile loadedPlayer2Profile = (UserProfile) Saver.loadObject("player2_profile.ser");
            MancalaGame loadedGame = (MancalaGame) Saver.loadObject("mancala_game.ser");
    
            // Display loaded UserProfile objects
            System.out.println("Loaded Player 1 Profile: " + loadedPlayer1Profile);
            System.out.println("Loaded Player 2 Profile: " + loadedPlayer2Profile);
            System.out.println(loadedGame);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
}
