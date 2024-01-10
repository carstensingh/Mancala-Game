package mancala;

public class Store {
    private Player owner;
    private int stoneCount;

    public void setOwner(Player player) {
        owner = player;
    }

    public Player getOwner() {
        return owner;
    }

    public void addStones(int amount) {
        stoneCount += amount;
    }

    public int getTotalStones() {
        return stoneCount;
    }

    public int emptyStore() {
        int stones = stoneCount;
        stoneCount = 0;
        return stones;
    }
}