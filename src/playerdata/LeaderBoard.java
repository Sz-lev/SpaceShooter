package playerdata;

import javax.swing.table.AbstractTableModel;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * A ranglista tábla model osztálya.
 */
public class LeaderBoard extends AbstractTableModel {

    public static List<Result> resultList = new ArrayList<>();
    public static final int maxSize = 10;

    private static final String[] columnNames = {
            "Helyezés",
            "Név",
            "Pont",
            "Idő"
    };

    /**
     * Megadja a sorok számát.
     * @return A táblázat sorainak a száma.
     */
    @Override
    public int getRowCount() {
        return resultList.size();
    }

    /**
     * Megadja a táblázat oszlopainak a számát.
     * @return Az oszlopok száma 4.
     */
    @Override
    public int getColumnCount() {
        return 4;
    }


    /**
     * Megadja egy sor és oszlop indexnek megfelelően az oda tartozó értéket.
     * @param rowIndex        A sor indexe, aminek a tartalmát lekérdezik.
     * @param columnIndex     Az oszlop indexe, aminek a tartalmát lekérdezik.
     * @return A sor és oszlopindexnek megfelelő cella érték.
     */
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0: return rowIndex+1;
            case 1: return resultList.get(rowIndex).name;
            case 2: return resultList.get(rowIndex).point;
            default: return roundDouble(resultList.get(rowIndex).time, 2);
        }
    }

    /**
     * Egy double szám bizonyos tizedes helyiértékre kerekítése.
     * @param number A kerekítendő szám.
     * @param places A tizedes helyiérték.
     * @return A kerekített double szám.
     */
    public Double roundDouble(Double number, int places) {
        if(places > 0) {
            double multiplyBy =  Math.pow(10.0, places);
            int newNum = (int) (number*multiplyBy);
            return  newNum / multiplyBy;
        }
        return null;
    }

    /**
     * Beállítja a táblázat oszlopainak nevét.
     * @param index  the column being queried
     * @return Az indexnek megfelelő oszlopnév.
     */
    @Override
    public String getColumnName(int index) {
        return columnNames[index];
    }

    /**
     * Az oszlopok típusát határozza meg.
     * @param index  A keresett oszlop értéke.
     * @return A keresett oszlop típusa.
     */
    @Override
    public Class<?> getColumnClass(int index){
        switch (index){
            case 0: return Integer.class;
            case 1: return String.class;
            case 2: return Integer.class;
            default: return Double.class;

        }
    }

    /**
     * Statikus függvény a ranglista txt-ből történő beolvasására.
     * A beolvasás után rendezi a listát, és ha 10-nél több elem lenne benne,
     * akkor törli a 10.-től kezdve az elemeket.
     * @param fileName A beolvasandó fájlhoz vezető cím.
     * @return True - Ha sikeresen beolvasta a fájlt, False egyébként.
     */
    public static boolean readLeaderboardFile(String fileName)  {
        File file = new File(fileName);
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String row = null;
            while((row = br.readLine()) != null) {
                String[] rowElements = row.split(",");
                if(rowElements.length == 3) {
                    String name = rowElements[0];
                    int point = Integer.parseInt(rowElements[1]);
                    double time = Double.parseDouble(rowElements[2]);

                    if(!name.equals("")) {
                        Result newRes = new Result(name, point, time);
                        resultList.add(newRes);
                    }
                }
            }
            br.close();

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        resultList.sort(Result::compareTo);
        if(resultList.size() > maxSize)
            resultList = resultList.subList(0, 10);
        return true;
    }

    /**
     * Statikus függvény a ranglista txt-be olvasására.
     * @param fileName Az írandó txt-hez vezető cím.
     * @return True - Ha sikeres volt az írás, egyébként False.
     */
    public static boolean writeLeaderboardToFile(String fileName) {
        File file = new File(fileName);

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            for(Result res : resultList) {
                bw.write(res.name+","+res.point+","+res.time);
                bw.newLine();
            }
            bw.close();

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }


    /**
     * Hozzáad egy új elemet a ranglistához, majd rendezi azt.
     * Ha több mint 10 elem lenne benne, akkor az utolsó törli.
     * @param res A hozzáadandó Result objektum.
     */
    public static void addResult(Result res) {
        resultList.add(res);
        resultList.sort(Result::compareTo);
        if(resultList.size() > 10) {
            resultList.remove(maxSize);
        }
    }


}
