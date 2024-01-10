package ui;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.awt.GridLayout;
import java.awt.Dimension;


import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingWorker;

import mancala.MancalaGame;
import mancala.NoSuchPlayerException;
import mancala.Player;
import mancala.Saver;
import mancala.UserProfile;
import mancala.AyoRules;
import mancala.GameNotOverException;
import mancala.InvalidMoveException;
import mancala.KalahRules;

public class MancalaController {
    private MancalaGame game;
    private MancalaView view;

    private static UserProfile profile1 = new UserProfile("Player 1");
    private static UserProfile profile2 = new UserProfile("Player 2");
    private Player player1;
    private Player player2;
    private String errorMsg = "";
    private int rulesUsed; //0 for kalah, 1 for ayo
    public MancalaController(MancalaGame game, MancalaView view) {
        this.game = game;
        this.view = view;
        initController();
    }


    private void start(){       
        view.addText(new StringBuilder()
        .append(player1.getName())
        .append("'s Score: ")
        .append(player1.getStore())
        .append(" | ")
        .append(player2.getName())
        .append("'s Score:")
        .append(player2.getStore())
        .toString());
        view.addSaveButton(e->saveGame(), e-> backToHome());
        view.addPanel(makeButtonGrid(getPitValues()));
    }

    private void saveGame(){
        try{
            Saver.saveObject(game, "mancala_game.ser");
        }catch(IOException e){
            errorMsg = e.getMessage();
        }
        
    }

    private JPanel makeButtonGrid(int[] buttonValues){
        JPanel panel = new JPanel(new GridLayout(2,6));
        
        // Set the preferred size of the panel to be 70% of the JFrame's size
        Dimension frameSize = new Dimension(900,500);
        panel.setPreferredSize(new Dimension((int)(frameSize.width * 0.7), (int)(frameSize.height * 0.7)));
        
        final int[] indices = {12,11,10,9,8,7,1,2,3,4,5,6};
        JButton[] buttons = new JButton[12];
        
        for(int i = 0; i < 12; i++) {
            final int index = i;
            buttons[i] = new JButton(String.valueOf(buttonValues[indices[i]-1]));
            buttons[i].addActionListener(e -> {
                performMove(indices[index]);
            });
            panel.add(buttons[i]);
        }
        return panel;
    }
    




    private void performMove(int index){
        errorMsg = "";
        try{
            game.move(index);
            if(!game.getBoard().getBonus()){
                if (game.getCurrentPlayer() == game.getPlayers().get(0)) {
                    game.setCurrentPlayer(game.getPlayers().get(1));
                } else {
                    game.setCurrentPlayer(game.getPlayers().get(0));
                }
            }

        }catch(InvalidMoveException e){
            errorMsg = e.getMessage();
        }
        view.clearScreen();
        
        view.addText(new StringBuilder()
        .append(player1.getName())
        .append("'s' Score: ")
        .append(player1.getStore())
        .append(" | ")
        .append(player2.getName())
        .append("'s Score: ")
        .append(player2.getStore())
        .append("\n" + errorMsg)
        .toString());
        view.addSaveButton(e->saveGame(), e->backToHome());
        view.addPanel(makeButtonGrid(getPitValues()));
        if(game.isGameOver() == true){
            try{
                Player winner = game.getWinner();
                view.clearScreen();
                view.addText("The Winner is: " + String.valueOf(winner) + " with " + String.valueOf(game.getStoreCount(winner)) + " stones");
                if(rulesUsed == 0){
                    profile1.incrementKalahGamesPlayed();
                    profile2.incrementKalahGamesPlayed();
                    if(player1.equals(winner)){
                        profile1.incrementKalahGamesWon();
                    }else{
                        profile2.incrementKalahGamesWon();
                    }
                }else{
                    profile1.incrementAyoGamesPlayed();
                    profile2.incrementAyoGamesPlayed();
                    if(player1.equals(winner)){
                        profile1.incrementAyoGamesWon();
                    }else{
                        profile2.incrementAyoGamesWon();
                    }
                }
                saveProfiles();
                showStats();
            }catch(GameNotOverException e){
                errorMsg = e.getMessage();
            }catch(NoSuchPlayerException e){
                errorMsg = e.getMessage();
            }
        }
    }

    private boolean doesProfileExist(String userName){
        Path folderPath = Paths.get(System.getProperty("user.dir")).resolve("src/main/assets/");
        File file = folderPath.resolve(userName+".ser").toFile();
        return file.exists();
    }

    private void showStats() {
        int choice = view.showConfirmationDialog("Would you like to see the stats of the users?");
        if (choice == JOptionPane.YES_OPTION) {
            view.clearScreen();
            view.addText(String.valueOf(profile1));
            view.addText(String.valueOf(profile2));

            SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
                @Override
                protected Void doInBackground() throws Exception {
                    Thread.sleep(5000);
                    return null;
                }

                @Override
                protected void done() {
                    playOrHome();
                }
            };

            // Execute the SwingWorker
            worker.execute();
        } else {
            playOrHome();
        }
    }

    private void playOrHome(){
         int choice = view.showConfirmationDialog("Would you like to play again?");
          if (choice == JOptionPane.YES_OPTION){
                view.clearScreen();
                if(rulesUsed == 0){
                    startKalahGame();
                }
                else{
                    startAyoGame();
                }
        }else{
            backToHome();
        }

    }
    private void saveProfiles(){
        try{
            Saver.saveObject(profile1, new StringBuilder().append(profile1.getUserName()).append(".ser").toString());
            Saver.saveObject(profile2, new StringBuilder().append(profile2.getUserName()).append(".ser").toString());
        }catch(IOException e){}
        
        
    }


    private int[] getPitValues(){
        int[] pitValues = new int[12];
        for(int i = 1; i <= 12; i++){
            pitValues[i-1] = game.getNumStones(i);
        }
        return pitValues;
    }

    private void initController() {
        view.addMenuButtons(
            e -> handleStartGame(),
            e -> System.exit(0)
        );
    }

    private void handleStartGame() {
        view.clearScreen();
        view.addStartButtons(
            e -> startKalahGame(),
            e -> startAyoGame(),
            e -> loadPreviousGame(),
            e -> backToHome()
        );
    }

    private void startKalahGame() {
        String player1Name = view.getPlayerName("Enter Player 1's Name Entering an Existing Name Will Load That Profile:");
        String player2Name = view.getPlayerName("Enter Player 2's Name Entering an Existing Name Will Load That Profile:");
        profile1 = new UserProfile(player1Name);
        profile2 = new UserProfile(player2Name);
        player1 = new Player(profile1);
        player2 = new Player(profile2);
        try{
            if(doesProfileExist(player1Name)){
                profile1 = (UserProfile) Saver.loadObject(player1Name+".ser"); 
            }
            if(doesProfileExist(player2Name)){
                profile2 = (UserProfile) Saver.loadObject(player2Name+".ser"); 
            }
        }catch(IOException e){
            errorMsg = e.getMessage();
        }
       
        int choice = view.showConfirmationDialog("Are you sure you want to start the game?");
        if (choice == JOptionPane.YES_OPTION) {
            game = new MancalaGame(player1, player2, new KalahRules());
            view.clearScreen();
            rulesUsed = 0;
            start();
        } else {
            backToHome();
        }
    }

    private void startAyoGame() {
        String player1Name = view.getPlayerName("Enter Player 1's Name. Entering an Existing Name Will Load That Profile:");
        String player2Name = view.getPlayerName("Enter Player 2's Name. Entering an Existing Name Will Load That Profile:");
        profile1 = new UserProfile(player1Name);
        profile2 = new UserProfile(player2Name);
        player1 = new Player(profile1);
        player2 = new Player(profile2);
        try{
            if(doesProfileExist(player1Name)){
                profile1 = (UserProfile) Saver.loadObject(player1Name+".ser"); 
            }
            if(doesProfileExist(player2Name)){
                profile2 = (UserProfile) Saver.loadObject(player2Name+".ser"); 
            }
        }catch(IOException e){
            errorMsg = e.getMessage();
        }
       
        int choice = view.showConfirmationDialog("Are you sure you want to start the game?");
        if (choice == JOptionPane.YES_OPTION) {
            game = new MancalaGame(player1, player2, new AyoRules());
            view.clearScreen();
            rulesUsed = 1;
            start();
        } else {
            backToHome();
        }
    }

    private void loadPreviousGame() {
        view.clearScreen();
        try{
            game = (MancalaGame) Saver.loadObject("mancala_game.ser");
            profile1 = new UserProfile(game.getPlayers().get(0).getName());
            profile2 = new UserProfile(game.getPlayers().get(1).getName());
            player1 = game.getPlayers().get(0);
            player2 = game.getPlayers().get(1);

            if(doesProfileExist(game.getPlayers().get(0).getName())){
                profile1 = (UserProfile) Saver.loadObject(game.getPlayers().get(0).getName()+".ser"); 
            }
            if(doesProfileExist(game.getPlayers().get(1).getName())){
                profile2 = (UserProfile) Saver.loadObject(game.getPlayers().get(1).getName()+".ser"); 
            }
            
        }catch(IOException e){
            errorMsg = e.getMessage();
        }
        start();
    }

    private void backToHome() {
        view.clearScreen();
        view.addMenuButtons(
            e -> handleStartGame(),
            e -> System.exit(0)
        );
    }

    public static void main(String[] args) {
        MancalaGame game = new MancalaGame();
        MancalaView view = new MancalaView();
        MancalaController controller = new MancalaController(game, view);
        view.showGui();
        
    }
}
