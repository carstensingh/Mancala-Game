package mancala;

import java.io.Serializable;

public class Player implements Serializable{
    private UserProfile profile;
    private String name;
    private Store store;

    public Player() {
        // Default constructor
        this.name = "";
        this.profile = new UserProfile("Temp Name");
        this.store = new Store();
    }

    public Player(UserProfile userProfile) {
        this.profile = userProfile;
        this.name = userProfile.getUserName();
        this.store = new Store();
    }


    public void setUserProfile(UserProfile userProfile) {
        this.profile = userProfile;
        this.name = userProfile.getUserName();
    }

    public String getName() {
        // Return player's name
        return this.name;
    }

    public void setName(String newName) {
        // Set player's name
        this.name = newName;
    }

    public void setStore(Store newStore) {
        // Set player's store
        this.store = newStore;
    }

    public Store getStore() {
        // Get player's store
        return this.store;
    }

    public int getStoreCount() {
        // Return the stone count in the player's store
        return this.store.getTotalStones();
    }

    @Override
    public String toString() {
        return name;
    }
}
