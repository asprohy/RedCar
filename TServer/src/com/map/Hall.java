package com.map;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.entity.CarCoordinateInfo;

public class Hall {

	// �����洢С����Ϣ��Ӧ���꣬����
	// ��������ʱ���ã������ݿ���ץȡ����
	public static HashMap<CarMapState, Coordinate> hallMap = new HashMap<CarMapState, Coordinate>();

	// ��ʼ��ʱ��Ҫ���ã������ݿ�����Ϣȡ����
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
	// ����
	// ��С��ת���Լ�����λ���복�������ӱ�ţ���Hall�����Լ��������
	// Hall ������Ӧ�ı���ϢӦ���� 1.������������ 2.С���г�����

	// ����С��ת��
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

	// ����С������
	// ��ȡ�ĵ�
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
