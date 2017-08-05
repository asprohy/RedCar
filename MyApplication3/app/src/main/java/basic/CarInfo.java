package basic;


public class CarInfo {


    private int x;
    private int y;
    private int direction;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public CarInfo(){
        x = 0;
        y = 0;
        direction = 0;
    }

    public CarInfo(int x, int y, int direction) {
	    this.x = x;
        this.y = y;
	    this.direction = direction;
    }

}
