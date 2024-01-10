package mancala;

import java.io.Serializable;

public class Pit implements Countable, Serializable {
    private int stoneCount;

    public Pit() {
        this.stoneCount = 4;  // Assuming each pit starts with 4 stones in Mancala
    }

    
    public int getStoneCount() {
        // Return the number of stones in the pit
        return this.stoneCount;
    }

    
    public void addStone() {
        // Add a stone to the pit
        this.stoneCount++;
    }

    
    public void addStones(int amount) {
        // Add stones to the pit
        this.stoneCount += amount;
    }

    
    public int removeStones() {
        // Remove and return all stones from the pit
        int temp = this.stoneCount;
        this.stoneCount = 0;
        return temp;
    }

    public String toString() {
        return String.valueOf(stoneCount);
    }
}
