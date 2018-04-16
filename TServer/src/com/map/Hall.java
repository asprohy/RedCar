package com.map;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.entity.CarCoordinateInfo;

public class Hall {

	// 这个表存储小车信息对应坐标，方向
	// 程序运行时调用，从数据库中抓取出来
	public static HashMap<CarMapState, Coordinate> hallMap = new HashMap<CarMapState, Coordinate>();

	// 初始化时需要调用，将数据库中信息取出来
	/*
	Hall() throws Exception {
		getHallMap();
	}

*/
	static ResultSet ret  = null;
/*
	public HashMap<CarMapState, Coordinate> getHallMap() throws Exception {
		
		List<CarCoordinateInfo> list = new ArrayList<CarCoordinateInfo>();
		MapDaoImpl dao = new MapDaoImpl();

		list = dao.getCarCoordinateInfo();
		for(int i =0;i<list.size();i++){
			CarMapState carState = new CarMapState((int)list.get(i).getRoad_id(), (int)list.get(i).getHall(),
					list.get(i).getChannel());
			Coordinate coordinate = new Coordinate((int)list.get(i).getCoordinate_x(), (int)list.get(i).getCoordinate_y(),
					(int)list.get(i).getDirection());
			hallMap.put(carState, coordinate);
		}
		
		return hallMap;
	}
*/
	// 建表
	// 将小车转角以及磁铁位置与车道（车队编号），Hall计数以及内外道绑定
	// Hall 计数对应的表信息应该有 1.霍尔计数坐标 2.小车行车方向

	// 返回小车转角
	public int getDirection(int group_id, int hall_count, String channel) {
		int direction = 0;
		Coordinate cf = null;
		for(CarMapState c :hallMap.keySet()){
			if(c.getRoad_id() == group_id
					&& c.getHallCount() == hall_count && 
					c.getChannel().equals(channel))
			{
				cf = hallMap.get(c);
			}
		}

		direction = cf.getDirection();
		return direction;
	}

	// 返回小车坐标
	// 获取的点
	public Point getPoint(int group_id, int hall_count, String channel) {

		CarMapState carState = new CarMapState(group_id, hall_count, channel);
		Coordinate cd = null;
		for(CarMapState c :hallMap.keySet()){
			if(c.getRoad_id() == group_id
					&& c.getHallCount() == hall_count && 
					c.getChannel().equals(channel))
			{
				cd = hallMap.get(c);
			}
		}
		Point p = new Point(cd.getX(), cd.getY());
		return p;
	}
}
