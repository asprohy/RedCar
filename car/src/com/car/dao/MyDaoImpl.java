package com.car.dao;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.car.entity.MyCar;
import com.lyc.test.Car;
import com.lyc.test.Fleet;

public class MyDaoImpl{

	//static String sql = null;  
   // static DBHepler db1 = null;  
    static ResultSet ret = null;
	
    public static void main(String[] args) {
    	Car c = new Car();
    	List<MyCar> list = new ArrayList<MyCar>();
    	try {
	//		list = getMyCars(510681);
			c = getMyCar(1, 510681);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	System.out.println(c.car_id + " " + c.channel );
	}
    
    
    //根据send_time字段或取所有小车数据
    public static List<Car> getCars(int send_time) throws Exception{
 //   	List<MyCar> list = new ArrayList<MyCar>();
    	List<Car> fleet = new ArrayList<Car>();
    	
    	ArrayList<Object> parameter = new ArrayList<Object>();
    	parameter.add(ret);
    	//sql 每个小车取最近时刻的数据 
   // 	select car_id, hall_count, distance from car_history_data where time in (select max(time) from car_history_data group by car_id)
  //  	String sql = "select car_id, channel, history_hall_count, dirive_distance from car_history_data";
  //  	String sql2 = "select car_id, history_hall_count, dirive_distance from car_history_data where send_time in (select max(send_time) from car_history_data group by car_id)";
    	String sql3 = "select car_id,  channel, history_hall_count, dirive_distance from car_history_data where send_time = " + send_time;
    	DBHelper db1 = new DBHelper(sql3);
    	ResultSet ret = db1.pst.executeQuery();//执行语句，得到结果集  
    	
    	while(ret.next()){
  //  		MyCar myCar = new MyCar(ret.getInt(3), ret.getString(2), ret.getInt(1), ret.getDouble(4));
    		Car car = new Car(ret.getInt(1), ret.getString(2), ret.getInt(3), ret.getDouble(4));
    		fleet.add(car);
 //   		list.add(myCar);
  //  		System.out.println(myCar.getCar_id());
    	}
    	db1.close();

//		return list;
		return fleet;
    }
    

//根据小车id和send_time获取小车信息
    public static Car getMyCar(long id, int send_time) throws Exception{
 //   	List<MyCar> list = new ArrayList<MyCar>();
    	
    	ArrayList<Object> parameter = new ArrayList<Object>();
    	parameter.add(ret);
    	String channel = null;
    	int history_hall_count = 0;
    	double drive_distance = 0;
    	//sql 每个小车取最近时刻的数据 
    	//select car_id, hall_count, distance from car_history_data where time in (select max(time) from car_history_data group by car_id)
    	String sql = "select car_id, channel, history_hall_count, dirive_distance from car_history_data where car_id = " + id + " and send_time = " + send_time;
    	DBHelper db1 = new DBHelper(sql);

    	ResultSet ret = db1.pst.executeQuery();//执行语句，得到结果集  
    	while(ret.next())
    	{
    		id = ret.getInt(1);
		channel = ret.getString(2);
		history_hall_count = ret.getInt(3);
		drive_distance = ret.getDouble(4);
    	}
    	
//		System.out.println(ret.getInt(1) + " " +  ret.getString(2) + " " + ret.getInt(3) + " " +  ret.getDouble(4) );
		//执行语句，得到结果集  
	  	Car myCar = new Car(id, channel, history_hall_count, drive_distance);
		db1.close();
    	
		return myCar;
		
    }
    
	
}
