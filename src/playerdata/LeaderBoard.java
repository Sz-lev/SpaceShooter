package playerdata;

import javax.swing.table.AbstractTableModel;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class LeaderBoard extends AbstractTableModel {

    public static List<Result> resultList = new ArrayList<>();
    public final int maxSize = 10;

    private static final String[] columnNames = {
            "Rang",
            "Név",
            "Pont",
            "Idő"
    };
    public LeaderBoard() {
//        resultList = new ArrayList<>();
//        if(!readLeaderboardFile("./data/leaderboard.txt"))
//            System.out.println("Sikertelen fájl beolvasás");
    }

    @Override
    public int getRowCount() {
        return resultList.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0: return rowIndex+1;
            case 1: return resultList.get(rowIndex).name;
            case 2: return resultList.get(rowIndex).point;
            default: return resultList.get(rowIndex).time;

        }
    }

    @Override
    public String getColumnName(int index) {
        return columnNames[index];
    }

    @Override
    public Class<?> getColumnClass(int index){
        switch (index){
            case 0: return Integer.class;
            case 1: return String.class;
            case 2: return Integer.class;
            default: return Integer.class;

        }
    }




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
                    int time = Integer.parseInt(rowElements[2]);

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
        resultList = resultList.subList(0, 10);
        return true;
    }

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


    public void addResult(Result res) {
        resultList.add(res);
        resultList.sort(Result::compareTo);
        if(resultList.size() > 10) {
            resultList.remove(maxSize);
        }
        fireTableDataChanged();
    }


}
