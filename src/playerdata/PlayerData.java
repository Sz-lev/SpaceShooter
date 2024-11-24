package playerdata;

public class PlayerData {
    enum Level {
        NONE,
        BEGINNER,
        NOVICE,
        MASTER
    }
    public String name;
    private int id;
    public int meteorsDestroyed;
    public int enemyShipsDestroyed;
    public double timePlayed;
    public int powerupsCollected;
    public int highscore;
    public int gamesPlayed;

    public PlayerData() {}
    public PlayerData(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
    public void addMeteorsDestroyed(int num) {
        meteorsDestroyed += num;
    }
    public void addEnemyShipDestroyed(int num) {
        enemyShipsDestroyed += num;
    }
    public void addTimePlayed(double time) {
        timePlayed += time;
    }
    public void addPUCollected(int puNum) {
        powerupsCollected += puNum;
    }

    public void setHighScore(int score) {
        if(highscore < score) {
            highscore = score;
        }
    }
    public void addGamesPlayed(int num) {
        gamesPlayed += num;
    }

}
