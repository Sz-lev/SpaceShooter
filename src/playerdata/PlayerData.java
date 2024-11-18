package playerdata;

public class PlayerData {
    enum Level {

        NONE,
        BEGINNER,
        NOVICE,
        MASTER
    }
    private String name;
    private int id;

    private int meteorsDestroyed;

    private int enemyShipsDestroyed;

    private double timePlayed;

    private int powerupsCollected;

    private int highscore;

    private int gamesPlayed;



    public PlayerData(String name) {
        this.name = name;
    }

}
