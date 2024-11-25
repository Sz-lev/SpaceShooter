package playerdata;

public class Result implements Comparable<Result> {
    public String name;
    public int point;
    public double time;

    public Result(String name, int point, double time) {
        this.name = name;
        this.point = point;
        this.time = time;
    }
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

//    @Override
//    public String toString() {
//        return "Name: "+name+", point: "+point+", time: "+time+" s";
//    }
}
