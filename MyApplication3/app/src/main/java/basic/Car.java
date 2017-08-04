package basic;

/**
 * Created by Administrator on 2017/7/14.
 */
public class Car {
    private Site centerOfCarPoint;
    private int car_id;
    private int group_id;

    private String channel;
    private int hall_count;
    private double distance;
    private int direction;

    private int headingDgree;
    private double speed;



    public Car()
    {
        centerOfCarPoint = new Site();
        car_id = 0;
        group_id = 0;
        channel = "Unknow";
        hall_count = -1;
        distance = 0;
        speed = 0;
        direction = 0;
    }

    //如果小车正在变道这个点应该还有变换
    public Car(int group_id, int car_id, int hall_count, int speed, double distance, int headingDgree, String channel)
    {
        this.car_id = car_id;
        this.group_id = group_id;
        this.hall_count = hall_count;
        this.speed = speed;
        this.distance = distance;
        this.headingDgree = headingDgree;
        this.channel = channel;
        this.direction = Hall.getDirection(group_id, hall_count, channel);
        this.centerOfCarPoint = Hall.getPoint(group_id, hall_count, channel);
    }



}
