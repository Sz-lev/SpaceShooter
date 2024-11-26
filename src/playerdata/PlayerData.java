package playerdata;

import java.util.HashMap;

/**
 * A játékos adatainak osztálya.
 */
public class PlayerData {

    /**
     * A kitüntetés szintjét jelző enum.
     */
    public enum Rank {
        ALAP,
        KEZDŐ,
        HALADÓ,
        MESTER
    }
    public HashMap<String, Rank> rankList;
    public String name;
    public int meteorsDestroyed;
    public int enemyShipsDestroyed;
    public double timePlayed;
    public int powerupsCollected;
    public int highscore;
    public int gamesPlayed;
    public int maxLevel;

    /**
     * Konstruktor ami létrehoz egy új játékos profilt.
     */
    public PlayerData() {
        rankList = new HashMap<>();
        rankListInit();
    }

    /**
     * Konstruktor ami beállít egy játékos nevet.
     * @param name A beállítandó játékosnév.
     */
    public PlayerData(String name) {
        this.name = name;
        rankList = new HashMap<>();
        rankListInit();
    }

    /**
     * A játékos kitüntetés listájának inícializálását végző függvény.
     */
    public void rankListInit() {
        rankList.put("meteorsDestroyed", Rank.ALAP);
        rankList.put("enemyShipsDestroyed", Rank.ALAP);
        rankList.put("timePlayed", Rank.ALAP);
        rankList.put("highscore", Rank.ALAP);
        rankList.put("powerupsCollected", Rank.ALAP);
        rankList.put("gamesPlayed", Rank.ALAP);
        rankList.put("maxLevel", Rank.ALAP);
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Hozzáadja a paraméterként kapott számot az elpusztított meteorok számához,
     * és utána frissíti a hozzá tartozó kitüntetést.
     * @param num A hozzáadandó elpusztított meteorok száma.
     */
    public void addMeteorsDestroyed(int num) {
        meteorsDestroyed += num;

        if(meteorsDestroyed >= 100)
            rankList.put("meteorsDestroyed", Rank.MESTER);
        else if(meteorsDestroyed >= 50)
            rankList.put("meteorsDestroyed", Rank.HALADÓ);
        else if (meteorsDestroyed >= 10) {
            rankList.put("meteorsDestroyed", Rank.KEZDŐ);
        }
    }
    /**
     * Hozzáadja a paraméterként kapott számot az elpusztított ellenséges űrhajók számához,
     * és utána frissíti a hozzá tartozó kitüntetést.
     * @param num A hozzáadandó elpusztított ellenséges űrhajók száma.
     */
    public void addEnemyShipDestroyed(int num) {
        enemyShipsDestroyed += num;

        if(enemyShipsDestroyed >= 100)
            rankList.put("enemyShipsDestroyed", Rank.MESTER);
        else if(enemyShipsDestroyed >= 50)
            rankList.put("enemyShipsDestroyed", Rank.HALADÓ);
        else if (enemyShipsDestroyed >= 10) {
            rankList.put("enemyShipsDestroyed", Rank.KEZDŐ);
        }
    }

    /**
     * Hozzáadja a paraméterként kapott számot a játékban töltött időhöz,
     * és utána frissíti a hozzá tartozó kitüntetést.
     * @param time A hozzáadandó játékban töltött idő.
     */
    public void addTimePlayed(double time) {
        timePlayed += time;

        if(timePlayed >= 1000)
            rankList.put("timePlayed", Rank.MESTER);
        else if(timePlayed >= 500)
            rankList.put("timePlayed", Rank.HALADÓ);
        else if (timePlayed >= 100) {
            rankList.put("timePlayed", Rank.KEZDŐ);
        }
    }
    /**
     * Hozzáadja a paraméterként kapott számot a gyűjtött powerupok számához,
     * és utána frissíti a hozzá tartozó kitüntetést.
     * @param puNum A hozzáadandó gyűjtött powerupok száma.
     */
    public void addPUCollected(int puNum) {
        powerupsCollected += puNum;

        if(powerupsCollected >= 500)
            rankList.put("powerupsCollected", Rank.MESTER);
        else if(powerupsCollected >= 100)
            rankList.put("powerupsCollected", Rank.HALADÓ);
        else if (powerupsCollected >= 10) {
            rankList.put("powerupsCollected", Rank.KEZDŐ);
        }
    }

    /**
     * Ellenőrzi, hogy az új pontszám nagyobb-e, mint a régi, ha igen akkor frissíti az értékét és a kitüntetését.
     * @param score Az újonnan elért pontszám.
     */
    public void setHighScore(int score) {
        if(highscore < score) {
            highscore = score;

            if(highscore >= 1000)
                rankList.put("highscore", Rank.MESTER);
            else if(highscore >= 500)
                rankList.put("highscore", Rank.HALADÓ);
            else if (highscore >= 100) {
                rankList.put("highscore", Rank.KEZDŐ);
            }
        }

    }
    /**
     * Hozzáadja a paraméterként kapott számot a játékok számához,
     * és utána frissíti a hozzá tartozó kitüntetést.
     * @param num A hozzáadandó játékok száma.
     */
    public void addGamesPlayed(int num) {
        gamesPlayed += num;

        if(gamesPlayed >= 20)
            rankList.put("gamesPlayed", Rank.MESTER);
        else if(gamesPlayed >= 10)
            rankList.put("gamesPlayed", Rank.HALADÓ);
        else if (gamesPlayed >= 1) {
            rankList.put("gamesPlayed", Rank.KEZDŐ);
        }
    }

    /**
     * Ellenőrzi, hogy a szint nagyobb-e, mint a jelenlegi.
     * Ha igen, akkor frissíti az értékét és a kitüntetését.
     * @param level Az újonnan elért szint.
     */
    public void setMaxLevel(int level) {
        if(level > maxLevel){
            maxLevel = level;

            if(maxLevel >= 12)
                rankList.put("maxLevel", Rank.MESTER);
            else if(maxLevel >= 6)
                rankList.put("maxLevel", Rank.HALADÓ);
            else if (maxLevel >= 1) {
                rankList.put("maxLevel", Rank.KEZDŐ);
            }
        }
    }

    /**
     * Visszaadja a játékos nevét.
     * @return A játékos neve.
     */
    public String toString() {
        return name;
    }
}
