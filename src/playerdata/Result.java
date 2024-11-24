package playerdata;

public class Result implements Comparable<Result> {
    public String name;
    public int point;
    public int time;

    public Result(String name, int point, int time) {
        this.name = name;
        this.point = point;
        this.time = time;

    }
    @Override
    public int compareTo(Result res2) {
        if(point == res2.point) {
            return time - res2.time;
        } else
            return res2.point - point;
    }

    @Override
    public String toString() {
        return "Name: "+name+", point: "+point+", time: "+time+" s";
    }
}
