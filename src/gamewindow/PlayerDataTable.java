package gamewindow;

import playerdata.PlayerData;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.util.HashMap;

public class PlayerDataTable extends AbstractTableModel {

    public HashMap<String, PlayerData.Rank> rankList;
    public PlayerData player;

    public PlayerDataTable(PlayerData player) {
        this.player = player;
        this.rankList = player.rankList;
    }

    @Override
    public String getColumnName(int index) {
        if(index == 0)
            return "Jelvények";
        else if(index == 1)
            return "Eredmény";
        else
            return "Rank";
    }

    @Override
    public int getRowCount() {
        return rankList.size();
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if(columnIndex == 0) {
            switch (rowIndex) {
                case 0:
                    return "Kilőtt űrhajók";
                case 1:
                    return "Kilőtt meteorok";
                case 2:
                    return "Játszott idő";
                case 3:
                    return "Gyűjtött Powerup";
                case 4:
                    return "Max Pontszám";
                case 5:
                    return "Játékmenet száma";
                default:
                    return "Max szint";
            }
        } else if (columnIndex == 1) {
            switch (rowIndex) {
                case 0:
                    return player.enemyShipsDestroyed;
                case 1:
                    return player.meteorsDestroyed;
                case 2:
                    return (int) player.timePlayed;
                case 3:
                    return player.powerupsCollected;
                case 4:
                    return player.highscore;
                case 5:
                    return player.gamesPlayed;
                default:
                    return player.maxLevel;
            }
        } else
            switch (rowIndex) {
                case 0:
                    return rankList.get("enemyShipsDestroyed");
                case 1:
                    return rankList.get("meteorsDestroyed");
                case 2:
                    return rankList.get("timePlayed");
                case 3:
                    return rankList.get("powerupsCollected");
                case 4:
                    return rankList.get("highscore");
                case 5:
                    return rankList.get("gamesPlayed");
                default:
                    return rankList.get("maxLevel");
            }


    }

    @Override
    public Class<?> getColumnClass(int index){
        switch (index){
            case 0: return String.class;
            case 1: return Integer.class;
            default: return String.class;

        }
    }
}
