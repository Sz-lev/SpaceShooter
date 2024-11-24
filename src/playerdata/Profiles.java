package playerdata;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.json.*;



public class Profiles {

    public HashMap<Integer, PlayerData> playerList;

    public Profiles() {
        playerList = new HashMap<>();

    }

    public void readJSONFile(String fileName) {
        File file = new File(fileName);
        String fileData = new String();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String row;
            while((row = br.readLine()) != null) {
                fileData = fileData + row;
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        JSONArray dataArray = new JSONArray(fileData);

        for(int i = 0; i < dataArray.length(); i++) {
            JSONObject jObject = dataArray.getJSONObject(i);

            PlayerData player = new PlayerData();
            player.setName(jObject.getString("name"));
            player.setId(jObject.getInt("id"));
            player.addMeteorsDestroyed(jObject.getInt("meteorsDestroyed"));
            player.addEnemyShipDestroyed(jObject.getInt("enemyShipsDestroyed"));
            player.addTimePlayed(jObject.getDouble("timePlayed"));
            player.addPUCollected(jObject.getInt("powerupsCollected"));
            player.setHighScore(jObject.getInt("highscore"));
            player.addGamesPlayed(jObject.getInt("gamesPlayed"));
            playerList.put(player.getId(), player);

//            System.out.println("Név"+ jObject.getString("név"));
//            System.out.println("kor"+ jObject.getInt("kor"));
//            System.out.println("átlag"+ jObject.getInt("átlag")+"\n");
        }
    }

    public PlayerData getPlayerByID(int id) {
        return playerList.get(id);
    }

    public void writeJSONFile(String fileName) {
        File file = new File(fileName);
        String fileData = new String();
        try {

            JSONArray playerJsonArray = new JSONArray();
            for(PlayerData player : playerList.values()) {
                JSONObject playerJson = new JSONObject();
                playerJson.put("name", player.name);
                playerJson.put("id", player.getId());
                playerJson.put("meteorsDestroyed", player.meteorsDestroyed);
                playerJson.put("enemyShipsDestroyed", player.enemyShipsDestroyed);
                playerJson.put("timePlayed", player.timePlayed);
                playerJson.put("powerupsCollected", player.powerupsCollected);
                playerJson.put("highscore", player.highscore);
                playerJson.put("gamesPlayed", player.gamesPlayed);
                playerJsonArray.put(playerJson);
            }


            FileWriter fw = new FileWriter(file);
            fw.write(playerJsonArray.toString(4));
            fw.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
