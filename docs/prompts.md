Build A mancala game in java using Object Oriented Methodology. The java project will contan 5 different classes listed below along with their methods. Write a very basic solution for this class in a mancala game. Write any assumptions you make as a comment in the code. Put all the files in a package called Mancala

Player
Player()
Player(name: String)
getName() : : String
setName(name: String) : :  void
setStore(store: Store) : : void
getStoreCount() : : int


Pit
getStoneCount() : : int
addStone() : : void
removeStones() : : int

Store
setOwner(player: Player) : : void
getOwner() : : Player
addStones(amount: int) : : void
getTotalStones() : : int
emptyStore() : : int

Board
setUpPits() : : void
getPits() :: ArrayList
getStores() :: ArrayList
setUpStores() :: void
intializeBoard() :: void
resetBoard() : : void
registerPlayers(one: Player, two: Player) : : void
moveStones(startPit: int, player: Player) : : int
distributeStones(startingPoint: int) : : int
captureStones(stoppingPoint: int) : : int
getNumStones(pitNum: int) :: int
isSideEmpty(pitNum: int) boolean

MancalaGame
setPlayers(onePlayer: Player, twoPlayer: Player) : : void
getPlayer() : : ArrayList
getCurrentPlayer() : : Player
setCurrentPlayer(player: Player) : : void
setBoard(theBoard: Board) : : void
getBoard() : : Board
getNumStones(pitNum: int): : int
move(startPit: int) : : int
getStoreCount(player: Player) : : int
getWinner() : : Player
isGameOver() : : boolean
startNewGame() :: void
toString() :: String
The pits are numbered as such
Player2 Well|12|11|10|9|8|7|Player1Well| 6| 5|  4|3|2|1

Create a textUI class that manages the above methods and controls the game flow

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
