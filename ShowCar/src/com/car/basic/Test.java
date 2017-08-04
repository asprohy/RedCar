package com.car.basic;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;


import javax.imageio.ImageIO;

import com.car.carState.CarInfo;
import com.car.carState.Point;
import com.car.carState.DBHelper;



public class Test extends Applet {

    /**
     * 
     */

    private static final long serialVersionUID = 1L;
    
    private static final int X = 1105;
    private static final int Y = 947;
    public void init() {
	super.init();
	resize(X, Y);
    };
    

    public List<CarInfo> getAllPoint() {
/*
	List<CarInfo> carList = new LinkedList<CarInfo>();
	String sql1 = "select coordinate_x, coordinate_y, direction from car_coordinate_info";
    	DBHelper db = new DBHelper(sql1);
    	String sql2 = "select x, y , direction from car_bend_info";
    	DBHelper db2 = new DBHelper(sql2);
  */
	String sql = "select coordinate_x, coordinate_y, hall from car_coordinate_info where channel = 'outside' and road_id = 1";
	DBHelper db = new DBHelper(sql);
	
	
    	try {
    	    
    	    ResultSet ret = db.pst.executeQuery();
    	 while(ret.next()){
    	     double x = ret.getInt(1) * 1.578;
    	     double y = ret.getInt(2) * 1.578;
    	     int hall = ret.getInt(3);
    	 }
    	    
    	    /*
    	    ResultSet ret1 = db.pst.executeQuery();
    	    while(ret1.next()){
                
                double x = ret1.getInt(1) * 1.578;
	    	double y = ret1.getInt(2) * 1.578;
	    	int iX = (int) x;
	    	int iY = (int) y;
	    	int direction = (ret1.getInt(3) / 10 * 10) % 360;
	    	CarInfo cInfo = new CarInfo(iX, iY, direction);
	    	carList.add(cInfo);
            }
    	    */
    	    
    	    ResultSet ret2 = db2.pst.executeQuery();
    	    while(ret2.next()){
    		int x = ret2.getInt(1);
    		int y = ret2.getInt(2);
    		int direction = (ret2.getInt(3) / 10 * 10) % 360;
    		CarInfo cInfo = new CarInfo(x, y, direction);
    		carList.add(cInfo);
    	    }
    	    
	} catch (SQLException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    	
    	db.close();
	return carList;
	
    }
    
    public static BufferedImage getCarImage(int direction) {

	String carImgString = "../resources/red_car_" + String.valueOf(direction) + ".png";
	File carImgFile = new File(carImgString);
	BufferedImage carImg = null;
	try {
	    carImg = ImageIO.read(carImgFile);
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	return carImg;
    }
    
    public void drawCar(Graphics g, CarInfo cInfo) {
	int direction = cInfo.direction % 360;
	int x = cInfo.site.x;
	int y = cInfo.site.y;
	BufferedImage car = getCarImage(direction);
	g.drawImage(car, x - 22, y -22, this);
    }
    
    
    public void paint(Graphics g) {
	/*
	BufferedImage bImage = Map.getMapImg();
	g.drawImage(bImage, 0, 0, this);
	g.setColor(Color.red);
	*/
	/*
	Point point = new Point(505, 400);
	Point point2 = new Point(605, 275);
	Point point3 = new Point(250, 70);
	Point point4 = new Point(600, 80);
	Point point5 = new Point(620, 135);
	Point point6 = new Point(630, 160);
	Point point7 = new Point(635, 200);
	Point point8 = new Point(625, 240);
	Point point9 = new Point(337, 610);
	Point.drawPoint(point, g);
	Point.drawPoint(point2, g);
	Point.drawPoint(point3, g);
	Point.drawPoint(point4, g);
	Point.drawPoint(point5, g);
	Point.drawPoint(point6, g);
	Point.drawPoint(point7, g);
	Point.drawPoint(point8, g);
	Point.drawPoint(point9, g);
	*/
	/*
	Point point = new Point(457, 460);
	Point point2 = new Point(105, 717);
	Point point3 = new Point(270, 690);
	Point point4 = new Point(210, 717);
	
	g.drawLine(105, 717, 210, 717);
	g.drawLine(210, 717, 270, 690);
	g.drawLine(457, 460, 270, 690);
	
	Point.drawPoint(point, g);
	Point.drawPoint(point2, g);
	Point.drawPoint(point3, g);
	Point.drawPoint(point4, g);
	*/
	
	List<CarInfo> carsInfo = getAllPoint();
	
	/*
	for(CarInfo cInfo: carsInfo){
	    BufferedImage bImage = Map.getMapImg();
	    g.drawImage(bImage, 0, 0, this);
	    g.setColor(Color.RED);
	    drawCar(g, cInfo);
	    try {
		Thread.sleep(1000);
	    } catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	}
	
	*/
	
	
	
	/*
	List<Point> pointList = getAllPoint();
	for(Point p: pointList){
	    System.out.println("x: " + p.x + " y: " + p.y);
		g.fillOval(p.x, p.y, r, r);
	}
	*/
	
    };
    
}
