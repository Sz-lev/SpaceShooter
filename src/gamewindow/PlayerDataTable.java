package gamewindow;

import playerdata.PlayerData;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.util.HashMap;

/**
 * A kitüntetések táblázat model osztálya.
 */
public class PlayerDataTable extends AbstractTableModel {

    public HashMap<String, PlayerData.Rank> rankList;
    public PlayerData player;

    public PlayerDataTable(PlayerData player) {
        this.player = player;
        this.rankList = player.rankList;
    }

    /**
     * Beállítja a táblázat oszlopainak nevét.
     * @param index  the column being queried
     * @return Az indexnek megfelelő oszlopnév.
     */
    @Override
    public String getColumnName(int index) {
        if(index == 0)
            return "Jelvények";
        else if(index == 1)
            return "Eredmény";
        else
            return "Rank";
    }

    /**
     * Megadja a sorok számát.
     * @return A táblázat sorainak a száma.
     */
    @Override
    public int getRowCount() {
        return rankList.size();
    }

    /**
     * Megadja a táblázat oszlopainak a számát.
     * @return Az oszlopok száma 3.
     */
    @Override
    public int getColumnCount() {
        return 3;
    }

    /**
     * Megadja egy sor és oszlop indexnek megfelelően az oda tartozó értéket.
     * @param rowIndex        A sor indexe, aminek a tartalmát lekérdezik.
     * @param columnIndex     Az oszlop indexe, aminek a tartalmát lekérdezik.
     * @return A sor és oszlopindexnek megfelelő cella érték.
     */
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

    /**
     * Az oszlopok típusát határozza meg.
     * @param index  A keresett oszlop értéke.
     * @return A keresett oszlop típusa.
     */
    @Override
    public Class<?> getColumnClass(int index){
        switch (index){
            case 0: return String.class;
            case 1: return Integer.class;
            default: return String.class;

        }
    }
}
