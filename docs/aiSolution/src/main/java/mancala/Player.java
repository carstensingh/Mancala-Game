package mancala;
public class Player {
    private String name;
    private Store store;

    public Player() {
        // Default constructor
        this.name = "";
        this.store = new Store();
    }

    public Player(String name) {
        // Constructor with player's name
        this.name = name;
        this.store = new Store();
    }

    public String getName() {
        // Return player's name
        return this.name;
    }

    public void setName(String name) {
        // Set player's name
        this.name = name;
    }

    public void setStore(Store store) {
        // Set player's store
        this.store = store;
    }

    public Store getStore() {
        // Get player's store
        return this.store;
    }

    public int getStoreCount() {
        // Return the stone count in the player's store
        return this.store.getTotalStones();
    }
}
