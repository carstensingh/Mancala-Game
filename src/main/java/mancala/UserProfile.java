package mancala;
import java.io.Serializable;

public class UserProfile implements Serializable {
    private static final long serialVersionUID = 17L;

    private String userName;
    private int kalahGamesPlayed;
    private int ayoGamesPlayed;
    private int kalahGamesWon;
    private int ayoGamesWon;
    public UserProfile(){}

    public UserProfile(String userName) {
        this.userName = userName;
        this.kalahGamesPlayed = 0;
        this.ayoGamesPlayed = 0;
        this.kalahGamesWon = 0;
        this.ayoGamesWon = 0;
    }

    public String getUserName() {
        return userName;
    }

    public int getKalahGamesPlayed() {
        return kalahGamesPlayed;
    }

    public int getAyoGamesPlayed() {
        return ayoGamesPlayed;
    }

    public int getKalahGamesWon() {
        return kalahGamesWon;
    }

    public int getAyoGamesWon() {
        return ayoGamesWon;
    }

    public void incrementKalahGamesPlayed() {
        kalahGamesPlayed++;
    }

    public void incrementAyoGamesPlayed() {
        ayoGamesPlayed++;
    }

    public void incrementKalahGamesWon() {
        kalahGamesWon++;
    }

    public void incrementAyoGamesWon() {
        ayoGamesWon++;
    }

    @Override
    public String toString() {
        return "UserProfile{" +
                "User Name='" + userName + '\'' +
                ", kalahGamesPlayed=" + kalahGamesPlayed +
                ", ayoGamesPlayed=" + ayoGamesPlayed +
                ", kalahGamesWon=" + kalahGamesWon +
                ", ayoGamesWon=" + ayoGamesWon +
                '}';
    }
}
