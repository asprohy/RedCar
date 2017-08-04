package com.lyc.test;

import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import org.omg.PortableServer.ID_ASSIGNMENT_POLICY_ID;

import com.car.dao.MyDaoImpl;
import com.car.entity.MyCar;



public class Fleet {
	
	
	public static void main(String[] args) {
		JOptionPane.showMessageDialog(null, "警告！小车可能发生碰撞！");
	}
	
	//汽车车队
//	public static List<Car> fleet = new ArrayList<Car>();
	
/*
	// 从数据库中读取所有汽车信息，添加到车队中、
	public static List<Car> getCars()
	{
		List<Car> fleet = new ArrayList<Car>();

//		Holzer h1 = new Holzer(1);
//		Holzer h2 = new Holzer(2);
		Car c1 = new Car(1,"inside",2,0.25);
		Car c2 = new Car(2,"outside",4,0.29);
		Car c3 = new Car(3,"inside",4,3);
//		Car c1 = new Car(50, 50, 1);
//		Car c2 = new Car();
//		Car c3 = new Car(h1, h2, 30);	
//		Car c4 = new Car(HolzerMap.Holzer5 , HolzerMap.Holzer6, 10);
//		Car c5 = new Car(HolzerMap.Holzer7 , HolzerMap.Holzer11, 10);
//		Car c6 = new Car(HolzerMap.Holzer7 , HolzerMap.Holzer11, 40);
		
		fleet.add(c1);
		fleet.add(c2);
		fleet.add(c3);
//		fleet.add(c4);
//		fleet.add(c5);
//		fleet.add(c6);
	

		return fleet;
	}
	
	*/

/*
	public static void main(String[] args) throws Exception {
		List<Car> fleet = new ArrayList<Car>();
		int id = 2;
		int time = 510681;

		fleet = getCars();
		Car car = getCarByidAndTime(id, time);
		System.out.println(car.car_id + " " + car.channel);
	}
	
	public static List<Car> getCars() throws Exception
	{
		List<Car> fleet = new ArrayList<Car>();
		List<MyCar> list = new ArrayList<MyCar>();

		list = MyDaoImpl.getMyCars();
		
		for(int i = 0; i < list.size(); i++){
			Car car = new Car(list.get(i).getCar_id(),list. get(i).getChannel(), list.get(i).getHolzerCounting(), list.get(i).getDistance());
//			System.out.println(list.get(i).getCar_id() + " " + list. get(i).getChannel() + " " + list.get(i).getHolzerCounting() + " " + list.get(i).getDistance());
			fleet.add(car);
		}
		
		return fleet;
	}

	public static List<Car> getCars(int send_time) throws Exception
	{
		List<Car> fleet = new ArrayList<Car>();
		List<MyCar> list = new ArrayList<MyCar>();

		list = MyDaoImpl.getMyCars(send_time);
		
		for(int i = 0; i < list.size(); i++){
			Car car = new Car(list.get(i).getCar_id(), list. get(i).getChannel(), list.get(i).getHolzerCounting(), list.get(i).getDistance());
			fleet.add(car);
		}
		
		return fleet;
	}

	

	public static Car getCarByidAndTime(int id, int time) {
		Car car = new Car();
		List<Car> fleet = null;
		try {
			fleet = getCars(time);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(int i = 0; i < fleet.size(); i++)
		{
			if(fleet.get(i).car_id == id){
				return fleet.get(i);
			}
		}
		return car;
	}
	

	*/

	
	/*
	public static List<Car> getCars()
	{
		 try {
			List<carState> carList =  DaoImpl.getCarState();
			for(int i = 0; i < carList.size(); i++)
			{
				carState cs =  carList.get(i);
				//1.如何将霍尔计数转化为坐标
				//2.要知道方向还需要知道现在的霍尔和下一个时刻的霍尔
				Car c = new Car(HolzerCountingToPoint(cs.getHolzerCounting()), HolzerCountingToPoint(cs.getHolzerCounting()), cs.getDistance());
				fleet.add(c);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			
		}
		return fleet;
	}
	*/

}
