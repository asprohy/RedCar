package com.lyc.test;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.List;

import javax.swing.JOptionPane;
import javax.xml.crypto.dsig.spec.C14NMethodParameterSpec;

import com.car.dao.MyDaoImpl;


public class MyCar extends Applet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final int X = 770;
	private static final int Y = 700;
	
	private String str1 = "����С����׷β���գ�";
	private String str2 = "����С���п��ܻᷢ����ײ��";
	
//	private Thread t;2
//	int id = 2;
//	Car c1;
	int time = 510681;
	Fleet fleet = new Fleet();
	
	Car c1 = new Car();

//	Car c1 = new Car(1,"inside",2,0.25);
//	c1 = Fleet.getCarByid(2);
	//��ʼ��
	
	public void init(){
		super.init();
		resize(X, Y);
	}
	
	private Image bgImage;
	private Graphics bg;

	public void update(Graphics g)
	{
		int start_time = 510681;
		
		int max_time = 527881;
		
		int id = 2;
		int i;
		
		for(int time = start_time; time < max_time; time+=200)
		{
			
			if (bgImage == null)
			{
				bgImage = createImage(this.getSize().width, this.getSize().height);
				bg = bgImage.getGraphics();
			}
		
			g.drawImage(bgImage, 0, 0, this);
		
			try {
				i = CollisionAvoidance.judge(id, time);
				

				Map p = new Map();
				p.drawMap(bg);
		
				c1 = MyDaoImpl.getMyCar(id, time);
				c1.direction = 2;
				System.out.println("car_id " + c1.car_id);
//				System.out.println(c1.centerPointOfCar.y);
				c1.drawCar(bg);
				
				showWarning(i);


/*
			if(i == 0){
				bg.setColor(Color.WHITE);
				bg.drawRect(0, 618, 770, 82);

			}
			else if(i == 1){
				JOptionPane.showMessageDialog(null, "���棡����С��������׷β���գ�");
				bg.setColor(Color.WHITE);
				bg.drawRect(0, 618, 770, 82);
				bg.setColor(Color.RED);
				bg.drawString(str1, 20, 650);
					
			}else{
				JOptionPane.showMessageDialog(null, "���棡С�����ܷ�����ײ��");
				bg.setColor(Color.WHITE);
				bg.drawRect(0, 618, 770, 82);
				bg.setColor(Color.RED);
				bg.drawString(str2, 20, 650);
			}
				*/	
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
				
	}

}
	//��δ����
	public void showWarning(int i) {
	    Boolean sw = true;//�Ҳ������������ܶྯ�浯��
	    if(i == 1 && sw)
	    {
		JOptionPane.showMessageDialog(null, "���棡����С��������׷β���գ�");
		sw = false;
	    }
	    else if(i == 2 && sw)
	    {
		JOptionPane.showMessageDialog(null, "���棡С�����ܷ�����ײ��");
		sw = false;
	    }
	}
	
	
	public void paint(Graphics g) {
		
		repaint();
	}
	
}
