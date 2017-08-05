package basic;

/**
 * Created by Administrator on 2017/8/5.
 */

public class BendInfo {
    int roadId;
    int channel;
    int hallCount;
    int distance;
    int x;
    int y;

    public BendInfo(int roadId, int channel, int hallCount, int distance, int x, int y){
        this.roadId = roadId;
        this.channel = channel;
        this.hallCount = hallCount;
        this.distance = distance;
        this.x = x;
        this.y = y;
    }
}
