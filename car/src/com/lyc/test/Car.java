package com.lyc.test;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.*;

import javax.imageio.ImageIO;
import javax.swing.JPanel;




public class Car extends JPanel{
	
	

	public static void main(String[] args) throws IOException {
	

		
		File imageFile = new File("D:/Workspaces/car/src/resource/car.png");
//		mimageCar = ImageIO.read(imageFile);
		System.out.print(imageFile.exists());
		
	
		
	//	mimageCar = ImageIO.read(new File("Car2.png"));
//			imageCar = ImageIO.read(new File("/resources/car.png"));
//			imageCar = ImageIO.read(getClass().getResource(new File("Car2.png")));
//			BufferedImage im = ImageIO.read(new File(getClass().getResource("Car2.png").toURI()));

	//	System.out.print(car.car_id);
	}
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	public static int length = 40;
	public static int width = 20;
	
	static int r = 5; //小车半径
	
	
	public String channel;
	
	public long car_id;
	
	public double speed;
	
	Point centerPointOfCar;
	
	public Image imageCar;
	public Image imageCar2;
	public Image imageBens;
	Image imageRoad;
	Image imageRoad2;
	
	
//	int out = 0;
	
	Car(int car_id, int x, int y){
	    this.centerPointOfCar = new Point(x, y);
	    this.car_id = car_id;
	    channel = "outside";
	    try {
		imageCar = ImageIO.read(new File("D:/Workspaces/car/src/resource/car.png"));
		imageCar2 = ImageIO.read(new File("D:/Workspaces/car/src/resource/Car2.png"));
	    } catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	}
	
	
	//小车的行驶方向跟现在小车所在位置以及小车以后所在位置有关
	static int directionOfCar(Holzer thisHolzer, Holzer nextHolzer){
		if(thisHolzer.p.x == nextHolzer.p.x)
			if(thisHolzer.p.y < nextHolzer.p.y)
				return 1;
			else
				return 2;
		if(thisHolzer.p.y == nextHolzer.p.y)
			if(thisHolzer.p.x < nextHolzer.p.x)
				return 4;
			else
				return 3;
		return 5;
	}
	
	
	// 
	
	static Point pointOfCar(Holzer thisHolzer, int direction, double distance){
		Point headPoint = new Point();
		/*	now car only have four direction
		**	1--up
		**  2--down
		**  3--left
		**  4--right
		**  5--error
		*/
		switch (direction) {
		case 1:
			headPoint.x = thisHolzer.p.x;
			headPoint.y = (int) (thisHolzer.p.y - distance);
			return headPoint;

		case 2:
			headPoint.x = thisHolzer.p.x;
			headPoint.y = (int) (thisHolzer.p.y + distance);
			return headPoint;
			
		case 3:
			headPoint.x = (int) (thisHolzer.p.x + distance);
			headPoint.y = thisHolzer.p.y;
			return headPoint;
			
		case 4:
			headPoint.x = (int) (thisHolzer.p.x - distance);
			headPoint.y = thisHolzer.p.y;
			return headPoint;
			
		default:
			return headPoint;
		}
		
	}
	
	
	

	/*	now car only have four direction
	**	1--up
	**  2--down
	**  3--left
	**  4--right
	**  5--error
	*/
	int direction;
	
	// 
	
	public Car() {
		// TODO Auto-generated constructor stub
		car_id = 1;
		channel = "inside";

			
		try {
			imageCar = ImageIO.read(new File("D:/Workspaces/car/src/resource/car.png"));
			imageCar2 = ImageIO.read(new File("D:/Workspaces/car/src/resource/Car2.png"));
//			imageRoad = ImageIO.read(new File("road.png"));
//			imageRoad2 = ImageIO.read(new File("road2.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		

		direction = 2;
		centerPointOfCar = new Point(50, 50);
	}
	
	
	public Car(long car_id, String channel, int holzerCounting, double distance){
		this.car_id = car_id;
		this.channel = channel;
		
		try {
			imageCar = ImageIO.read(new File("D:/Workspaces/car/src/resource/car.png"));
			imageCar2 = ImageIO.read(new File("D:/Workspaces/car/src/resource/Car2.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		centerPointOfCar = pointOfCar(holzerCounting, distance);
	}
	
	public void drawCar(Graphics g) {
		/*
		if(direction == 1){
			g.drawRect(centerPointOfCar.x - this.width/2, centerPointOfCar.y, this.width, this.length);
		}else if(direction == 2) {
			g.drawRect(centerPointOfCar.x - this.width/2, centerPointOfCar.y-this.length, this.width, this.length);
		}else if (direction == 3){
			g.drawRect(centerPointOfCar.x, centerPointOfCar.y-this.width/2, this.length, this.width);
		}else{
			g.drawRect(centerPointOfCar.x - length, centerPointOfCar.y - width/2, this.length, this.width);
		}
		*/
		
		g.setColor(Color.red);
		System.out.println(this.channel);
		
		if(direction == 1){
			g.drawImage(imageCar, centerPointOfCar.x, centerPointOfCar.y, this);
		}else if(direction == 2 && this.channel.equals("inside")) {
			g.drawImage(imageCar, centerPointOfCar.x - 20, centerPointOfCar.y, this);
		}else if(direction == 2 && this.channel.equals("outside")){
		    	g.drawImage(imageCar, centerPointOfCar.x, centerPointOfCar.y, this);
		}else if (direction == 3){
			g.drawImage(imageCar2, centerPointOfCar.x, centerPointOfCar.y , this);
		}else{
			g.drawImage(imageCar2, centerPointOfCar.x - length, centerPointOfCar.y, this);
		}
		/*
		if(this.channel.equals("inside"))
		{
			g.drawImage(imageCar, centerPointOfCar.x-25, centerPointOfCar.y, this);
		}else{
			g.drawImage(imageCar, centerPointOfCar.x, centerPointOfCar.y, this);
		}
		*/
		/*
		if(this.channel == "inside")
		{
			g.fillOval(centerPointOfCar.x-25, centerPointOfCar.y, Car.r, Car.r);
		}else{
			g.fillOval(centerPointOfCar.x, centerPointOfCar.y, Car.r, Car.r);
		}
		*/
		/*
		
		if(direction == 1){
			g.drawImage(imageCar, centerPointOfCar.x - Car.width/2, centerPointOfCar.y, this);
		}else if(direction == 2) {
			g.drawImage(imageCar, centerPointOfCar.x - Car.width/2, centerPointOfCar.y-Car.length, this);
		}else if (direction == 3){
			g.drawImage(imageCar2, centerPointOfCar.x, centerPointOfCar.y - Car.width/2, this);
		}else{
			g.drawImage(imageCar2, centerPointOfCar.x - length, centerPointOfCar.y - width/2, this);
		}
		*/
	}
	
	public void drawCarCar (Graphics g) {
	    g.drawImage(imageBens, centerPointOfCar.x , centerPointOfCar.y ,this);
	}
	
	
	//绘制小车，根据角度，旋转小车
	public void drawCar(int degree, Graphics g)
	{
	    Graphics2D g2 = (Graphics2D) g;
	    //注意修改为小车中心
	    g2.rotate(Math.toRadians(degree), this.centerPointOfCar.x, this.centerPointOfCar.y);
	    
	    g.drawImage(imageCar, this.centerPointOfCar.x, this.centerPointOfCar.y, this);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
	   super.paintComponent(g);
	   g.drawImage(imageCar, this.centerPointOfCar.x, this.centerPointOfCar.y, this);
	}
	
/*
	public void drawCar(int degree, Graphics g){
	    Image bgImage;
	    Graphics bg;

	    bgImage = createImage(this.getSize().width, this.getSize().height);
	    bg = bgImage.getGraphics();
	    
	    Graphics2D g2 = (Graphics2D) bg;
	    //注意修改为小车中心
	    g2.rotate(Math.toRadians(degree), centerPointOfCar.x, centerPointOfCar.y);
	    
	    g2.drawImage(imageCar, centerPointOfCar.x, centerPointOfCar.y, this);
//	    g.drawImage(bgImage, 0, 0, this);
	}
	*/
	public void cleanCar(Graphics g) {
		//this.setVisible(false);
		/*
		if(direction == 1){
			g.drawImage(imageRoad2, centerPointOfCar.x - Car.width/2, centerPointOfCar.y, this);
		}else if(direction == 2) {
			g.drawImage(imageRoad2, centerPointOfCar.x - Car.width/2, centerPointOfCar.y-Car.length, this);
		}else if (direction == 3){
			g.drawImage(imageRoad, centerPointOfCar.x, centerPointOfCar.y - Car.width/2, this);
		}else{
			g.drawImage(imageRoad, centerPointOfCar.x - length, centerPointOfCar.y - width/2, this);
		}
		*/
		/*if(direction == 1){
			g.clearRect(centerPointOfCar.x - Car.width/2 - 1, centerPointOfCar.y - 1, Car.width + 3, Car.length + 3);
		}else if(direction == 2) {
			g.clearRect(centerPointOfCar.x - Car.width/2 - 1, centerPointOfCar.y-Car.length - 1, Car.width + 3, Car.length + 3);
		}else if (direction == 3){
			g.clearRect(centerPointOfCar.x - 1, centerPointOfCar.y-Car.width/2 - 1, Car.length + 3, Car.width + 3);
		}else{
			g.clearRect(centerPointOfCar.x - length - 1, centerPointOfCar.y - width/2 - 1, Car.length + 3, Car.width + 3);
		}
		*/
		g.setColor(Color.WHITE);
		g.fillOval(centerPointOfCar.x, centerPointOfCar.y, Car.r, Car.r);
	}
	

	
	
	static Point pointOfCar(int holzerCounting, double distance){
		Point headPoint = new Point();
		Point holzerPoint = Holzer.getPoint(holzerCounting);
		
		switch (Holzer.getDirection(holzerCounting)) {
		case 1:
			headPoint.x = holzerPoint.x;
			headPoint.y = (int) (holzerPoint.y - distance * 100);
			return headPoint;

		case 2:
			headPoint.x = holzerPoint.x;
			headPoint.y = (int) (holzerPoint.y + distance * 100);
			return headPoint;
			
		case 3:
			headPoint.x = (int) (holzerPoint.x + distance * 100);
			headPoint.y = holzerPoint.y;
			return headPoint;
			
		case 4:
			headPoint.x = (int) (holzerPoint.x - distance * 100);
			headPoint.y = holzerPoint.y;
			return headPoint;
			
		default:
			return headPoint;
		}
	}
	
	
	
	
	
	//根据霍尔计数的不同返回增加距离的方向是多少
	static int getDirection(Holzer h)
	{
	    return 1;
	}

	


}
