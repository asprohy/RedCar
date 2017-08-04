package basic;


public class CarInfo {
    public Site site;
    public int direction;
    
    public CarInfo(int x, int y, int direction) {
	this.site = new Site(x, y);
	this.direction = direction;
    }

}
