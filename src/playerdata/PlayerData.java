package playerdata;

import javax.swing.table.AbstractTableModel;
import java.util.HashMap;

public class PlayerData {

    public enum Rank {
        NONE,
        BEGINNER,
        NOVICE,
        MASTER
    }
    public HashMap<String, Rank> rankList;
    public String name;
    private int id;
    public int meteorsDestroyed;
    public int enemyShipsDestroyed;
    public double timePlayed;
    public int powerupsCollected;
    public int highscore;
    public int gamesPlayed;
    public int maxLevel;

    public PlayerData() {
        rankList = new HashMap<>();
        rankListInit();
    }
    public PlayerData(String name) {
        this.name = name;
        rankList = new HashMap<>();
        rankListInit();
    }

    public void rankListInit() {
        rankList.put("meteorsDestroyed", Rank.NONE);
        rankList.put("enemyShipsDestroyed", Rank.NONE);
        rankList.put("timePlayed", Rank.NONE);
        rankList.put("highscore", Rank.NONE);
        rankList.put("powerupsCollected", Rank.NONE);
        rankList.put("gamesPlayed", Rank.NONE);
        rankList.put("maxLevel", Rank.NONE);
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

        if(meteorsDestroyed >= 100)
            rankList.put("meteorsDestroyed", Rank.MASTER);
        else if(meteorsDestroyed >= 50)
            rankList.put("meteorsDestroyed", Rank.NOVICE);
        else if (meteorsDestroyed >= 10) {
            rankList.put("meteorsDestroyed", Rank.BEGINNER);
        }
    }
    public void addEnemyShipDestroyed(int num) {
        enemyShipsDestroyed += num;

        if(enemyShipsDestroyed >= 100)
            rankList.put("enemyShipsDestroyed", Rank.MASTER);
        else if(enemyShipsDestroyed >= 50)
            rankList.put("enemyShipsDestroyed", Rank.NOVICE);
        else if (enemyShipsDestroyed >= 10) {
            rankList.put("enemyShipsDestroyed", Rank.BEGINNER);
        }
    }
    public void addTimePlayed(double time) {
        timePlayed += time;

        if(timePlayed >= 1000)
            rankList.put("timePlayed", Rank.MASTER);
        else if(timePlayed >= 500)
            rankList.put("timePlayed", Rank.NOVICE);
        else if (timePlayed >= 100) {
            rankList.put("timePlayed", Rank.BEGINNER);
        }
    }
    public void addPUCollected(int puNum) {
        powerupsCollected += puNum;

        if(powerupsCollected >= 500)
            rankList.put("powerupsCollected", Rank.MASTER);
        else if(powerupsCollected >= 100)
            rankList.put("powerupsCollected", Rank.NOVICE);
        else if (powerupsCollected >= 10) {
            rankList.put("powerupsCollected", Rank.BEGINNER);
        }
    }

    public void setHighScore(int score) {
        if(highscore < score) {
            highscore = score;

            if(highscore >= 1000)
                rankList.put("highscore", Rank.MASTER);
            else if(highscore >= 500)
                rankList.put("highscore", Rank.NOVICE);
            else if (highscore >= 100) {
                rankList.put("highscore", Rank.BEGINNER);
            }
        }

    }
    public void addGamesPlayed(int num) {
        gamesPlayed += num;

        if(gamesPlayed >= 20)
            rankList.put("gamesPlayed", Rank.MASTER);
        else if(gamesPlayed >= 10)
            rankList.put("gamesPlayed", Rank.NOVICE);
        else if (gamesPlayed >= 1) {
            rankList.put("gamesPlayed", Rank.BEGINNER);
        }
    }
    public void setMaxLevel(int level) {
        if(level > maxLevel){
            maxLevel = level;

            if(maxLevel >= 12)
                rankList.put("maxLevel", Rank.MASTER);
            else if(maxLevel >= 6)
                rankList.put("maxLevel", Rank.NOVICE);
            else if (maxLevel >= 1) {
                rankList.put("maxLevel", Rank.BEGINNER);
            }
        }
    }


    public String toString() {
        return name;
    }
}
