package basic;

/**
 * Created by Administrator on 2017/7/14.
 */
public class Hall {
    String channel;
    int hallCount;
    int roadId;

    public Hall(int roadId, int hallCount, String channel){
        this.roadId = roadId;
        this.hallCount = hallCount;
        this.channel = channel;
    }
}
