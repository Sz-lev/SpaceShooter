package playerdata;

/**
 * Az eredmény osztálya
 */
public class Result implements Comparable<Result> {
    public String name;
    public int point;
    public double time;

    public Result(String name, int point, double time) {
        this.name = name;
        this.point = point;
        this.time = time;
    }

    /**
     * Összehasonlító függvény. A nagyobb pontszám kisebb idővel kerül előrébb.
     * @param res2 az összehasonlítandó objektum
     * @return < 0 - Ha az objektumnak nagyobb a pontja, vagy egyenlőség esetén kisebb az ideje. 0 - Ha teljesen megegyeznek.
     * > 0 - egyébként.
     */
    @Override
    public int compareTo(Result res2) {
        if(point == res2.point) {
            if(time < res2.time)
                return -1;
            else if(time > res2.time)
                return 1;
            else
                return 0;
        } else
            return res2.point - point;
    }
    
}
