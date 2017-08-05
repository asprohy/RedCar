/**
 * Created by Administrator on 2017/8/5.
 */

public class Bend {
    int x;
    int y;
    int roadId;
    String channel;
    int hallCount;
    int distance;

    public Bend(int x, int y, int roadId, int hallCount, int distance, String channel){
        this.x = x;
        this.y = y;
        this.roadId = roadId;
        this.hallCount = hallCount;
        this.distance = distance;
        this.channel = channel;
    }
}
