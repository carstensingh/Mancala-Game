package mancala;

import java.io.Serializable;


public class Store implements Countable, Serializable {
    private Player owner;
    private int stoneCount;

    public Store() {
        this.owner = null;
        this.stoneCount = 0;
    }

    public int getStoneCount() {
        // Return the number of stones in the store
        return this.stoneCount;
    }

    public void addStone() {
        // Add a stone to the store
        this.stoneCount++;
    }

    public void addStones(int amount) {
        // Add stones to the store
        this.stoneCount += amount;
    }

    public int removeStones() {
        // Remove and return all stones from the store
        int temp = this.stoneCount;
        this.stoneCount = 0;
        return temp;
    }

    public String toString() {
        return String.valueOf(stoneCount);
    }

    public void setOwner(Player player) {
        // Set the owner of the store
        this.owner = player;
    }

    public Player getOwner() {
        // Return the owner of the store
        return this.owner;
    }

    public int getTotalStones() {
        // Return the total number of stones in the store
        return this.stoneCount;
    }

    public int emptyStore() {
        // Remove and return all stones from the store
        int temp = this.stoneCount;
        this.stoneCount = 0;
        return temp;
    }
}
