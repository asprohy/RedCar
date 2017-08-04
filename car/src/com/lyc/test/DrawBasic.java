package com.lyc.test;


import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.applet.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.naming.InitialContext;
import javax.swing.JFrame;

import com.car.dao.MyDaoImpl;




public class DrawBasic extends Applet{
	/**
	 * 
	 */
    
//	int time;
	
	private Image bgImage;
	private Graphics bg;

	public void update(Graphics g)
	{
		int start_time = 510681;
		
		int max_time = 534481;
		
		for(int time = start_time; time < max_time; time+=200)
		{
			if (bgImage == null)
			{
				bgImage = createImage (this.getSize().width, this.getSize().height);
				bg = bgImage.getGraphics();
			}

			g.drawImage(bgImage, 0, 0, this);
		
			Map p = new Map();
			p.drawMap(bg);
//			int time = 530881;
			List<Car> fleet;
			try {
				fleet = MyDaoImpl.getCars(time);
				Car theCar;
				for(int i = 0; i < fleet.size(); i++)
				{
					theCar = fleet.get(i);
					theCar.direction = 2;
//					g.setColor(Color.RED);
					theCar.drawCar(bg);

				}
				fleet.clear();
//					Thread.sleep(200);
			} catch (Exception e) {
			// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		//paint(bg);//调用后可重复绘制
//		p.drawMap(bg);
	}
	
	private static final long serialVersionUID = 1L;
	
	private static final int X = 770;
	private static final int Y = 618;
	

	//初始化
    
	public void init(){
		super.init();
		resize(X, Y);
	}
	
	

 
	//建立好小车数据表后，绘制过程根据设置的刷新频率，反复调用绘制方法

	
	
	public static void setRoadColor(Graphics g, int roadState) {
	    /*
	     * * 道路拥堵状态
	     * * 
	     * * 1 -- 车间距较大
	     * * 2 -- 车间距较小
	     * * 3 -- 车间距很小
	     * * 
	     * */
		
	    if(roadState == 1)
		g.setColor(Color.GREEN);
	    else if(roadState == 2)
		g.setColor(Color.YELLOW);
	    else
		g.setColor(Color.RED);
	}
	
	
	public static void cleanAll(Graphics g){
	    g.setColor(Color.white);
	    g.drawRect(0, 0, X, Y);
	}

	
	public void paint(Graphics g) {
	    repaint();
	}

}


