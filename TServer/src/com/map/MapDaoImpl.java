package com.map;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import Bean.PostgreSQL;

import com.entity.CarBasicInfo;
import com.entity.CarBendInfo;
import com.entity.CarCoordinateInfo;
import com.entity.CarState;
import com.entity.CarWarnInfo;
import com.entity.CoordinateXY;
import com.entity.ShuntId;
import com.sql.JdbcUtils;

public class MapDaoImpl extends JdbcUtils implements MapDao,PostgreSQL{

	public int getDiection(int road_id, int hall_count, String channel) throws Exception {
		//super.getConnection();
		int direction = 0;
		String sql = "select direction from car_coordinate_info where road_id = ? and hall = ? and channel = ?";
		List<Object> params = new ArrayList<Object>();
		params.add(road_id);
		params.add(hall_count);
		params.add(channel);
		
		List<Map<String, Object>> list = findMoreResult(sql,params);
		if(list.size()!=0){
			direction = (Integer) list.get(0).get("direction");
		}
		
		//super.releaseConn();
		return direction;
	}
	
	
	public Point getPoint(int road_id, int hall_count, String channel) throws Exception {
	//	super.getConnection();
		Point p = new Point();
		String sql = "select coordinate_x,coordinate_y from car_coordinate_info where road_id = ? and hall = ? and channel = ?";
		List<Object> params = new ArrayList<Object>();
		params.add(road_id);
		params.add(hall_count);
		params.add(channel);
		BigDecimal x;
		BigDecimal y;
		System.out.println("roadId  " + road_id + "hall_count " + hall_count + "channel" + channel);
		
		List<Map<String, Object>> list = findMoreResult(sql,params);
		if(list.size()!=0){
			 x = (BigDecimal) list.get(0).get("coordinate_x");
			 y = (BigDecimal) list.get(0).get("coordinate_y");
			 System.out.println("x: " + x);
			 System.out.println("y: " + y);
			p.x = (int)x.doubleValue();
			p.y = (int) y.doubleValue();
		}
		
	//	super.releaseConn();
		return p;
	}

	
	public List<Long> getCarId() throws Exception{
		// TODO Auto-generated method stub
		//super.getConnection();
		long id;
		String sql = "select distinct car_id from t_car_group";
		List<Map<String, Object>> list1 = findMoreResult(sql,null);
		List<Long> list = new ArrayList<Long>();
		for(int i=0;i<list1.size();i++){
			id = (Long) list1.get(i).get("car_id");
			list.add(id);
		}
		
		//super.releaseConn();
		return list;
	}
	
	

	public List<CarState> getLeastCarInfoById(Long carId) throws Exception {//得到最新的小车状态数据
		// TODO Auto-generated method stub
		//super.getConnection();
		String sql = "select * from car_history_data where car_id=? and send_time =( select max(send_time) from car_history_data where car_id=?)";
		List<Object> params = new ArrayList<Object>();
		params.add(carId);
		params.add(carId);
		
		List<CarState> list = findMoreRefResult(sql,params,CarState.class);
		//super.releaseConn();
		return list;
	}

	public double getMinDistanceByIdAndHall(long hallCount, long carId) throws Exception{
		//得到当前霍尔计数的最小distance
		//super.getConnection();
		BigDecimal distance = new BigDecimal(0);
		//String sql = "select dirive_distance from car_history_data where history_hall_count = ? and car_id = ? and min(dirive_distance)";
		String sql = "select min(drive_distance) from car_history_data where history_hall_count = ? and car_id = ?";
		List<Object> params = new ArrayList<Object>();
		params.add(hallCount);
		params.add(carId);
		
		List<Map<String, Object>> list = findMoreResult(sql,params);
		System.out.println(list);
		if(list.size() != 0){
			distance = (BigDecimal) list.get(0).get("min");
		}
		System.out.println(distance);
		//super.releaseConn();
		// TODO Auto-generated method stub
		return distance.doubleValue();
	}

	public String getChannelByCarId(long carId) throws Exception{
		// TODO Auto-generated method stub
		//super.getConnection();
		String channel="";
		String sql = "select channel from car_history_data a where send_time = (select max(b.send_time) from car_history_data as b where a.car_id = b.car_id) and car_id = ?";
		List<Object> params = new ArrayList<Object>();
		params.add(carId);
		
		List<Map<String, Object>> list = findMoreResult(sql,params);
		if(list.size()!=0){
			channel = (String) list.get(0).get("channel");
		}
	//	super.releaseConn();
		return channel;
	}

	public List<CarCoordinateInfo> getCarCoordinateInfo() throws Exception{
		// TODO Auto-generated method stub
	//	super.getConnection();
		List<CarCoordinateInfo> list = new ArrayList<CarCoordinateInfo>();
		String sql ="select * from car_coordinate_info";
		
		list = findMoreRefResult(sql,null,CarCoordinateInfo.class);
	//	super.releaseConn();
		return list;
	}

	public List<CarBendInfo> getSpecialCarBend(int roadId, String ch,
			long hallCount) throws Exception{//根据条件查询小车特殊的拐角点
		// TODO Auto-generated method stub
		int channel;
		if(ch.equals("inside")){
			channel = 1;
		}else
			channel = 2;
				
		//super.getConnection();
		String sql = "select * from car_bend_info where road_id = ? and channel = ? and hall_count = ?";
		List<Object> params = new ArrayList<Object>();
		params.add(roadId);
		params.add(channel);
		params.add(hallCount);
		
		List<CarBendInfo> list = findMoreRefResult(sql,params,CarBendInfo.class);
		//super.releaseConn();
		return list;
	}

	public List<CarBendInfo> getSpecialCarBend1(int roadId, int ch,
			long hallCount, int iDistance)throws Exception {
		// TODO Auto-generated method stub
		//super.getConnection();
		String sql = "select * from car_bend_info where road_id = ? and channel = ? and hall_count = ? and distance =?";
		List<Object> params = new ArrayList<Object>();
		params.add(roadId);
		params.add(ch);
		params.add(hallCount);
		params.add(iDistance);
		
		List<CarBendInfo> list = findMoreRefResult(sql,params,CarBendInfo.class);
	//	super.releaseConn();
		return list;	
		}

	public List<CarBasicInfo> getCarBasicInfo() throws Exception{//得到小车的基础信息
		// TODO Auto-generated method stub
	//	super.getConnection();
		String sql = "select * from car_basic_info order by id";
		List<CarBasicInfo> list = findMoreRefResult(sql,null,CarBasicInfo.class);
		//super.releaseConn();
		return list;
	}

	public List<CarBendInfo> getAllBendInfo() throws Exception{//得到特殊霍尔信息
		// TODO Auto-generated method stub
	//	super.getConnection();
		String sql = "select * from car_bend_info order by id";
		List<CarBendInfo> list = findMoreRefResult(sql,null,CarBendInfo.class);
	//	super.releaseConn();
		return list;
	}

	public List<CarCoordinateInfo> getAllHallCoordinate() throws Exception{
		// TODO Auto-generated method stub
	//	super.getConnection();
		String sql = "select * from car_coordinate_info order by id";
		List<CarCoordinateInfo> list = findMoreRefResult(sql,null,CarCoordinateInfo.class);
		
	//	super.releaseConn();
		return list;
	}

	public List<Map<String, Object>> getCongestionHall() throws Exception{//得到拥堵小车的霍尔
		// TODO Auto-generated method stub

		
		String sql = "select a.car_id,a.history_hall_count,a.channel,d.road_id from car_history_data a,car_warning_info b,car_basic_info d where send_time = (select max(c.send_time) from car_history_data as c where a.car_id = c.car_id) and b.congestion = true and a.car_id = b.car_id and a.car_id = d.id";
		List<Map<String, Object>> list = findMoreResult(sql,null);				
	//	super.releaseConn();		
		return list;
	}

//	public List<CarWarnInfo> getCongestionCarInfo() throws Exception{//得到拥堵的小车信息
//	//	super.getConnection();
//		
//		String sql = "select a.*,b.road_id from car_warning_info a,car_basic_info b where congestion = true and a.car_id = b.id";
//		List<CarWarnInfo> list = findMoreRefResult(sql,null,CarWarnInfo.class);
//		
//	//	super.releaseConn();
//		return list;
//	}

	public List<Map<String,Object>> getCongestionCarMaxHall() throws Exception{//得到每个道路分别内外道的最大霍尔
		// TODO Auto-generated method stub
	//	super.getConnection();
		
		String sql = "select road_id,channel,Max(hall) from car_coordinate_info group by road_id,channel";
		List<Map<String,Object>> list = findMoreResult(sql,null);
		
	//	super.releaseConn();
		return list;
	}

	public long getRoadIdByCarId(long id) throws Exception{
		long roadId = 0;
		String sql = "select road_id from car_basic_info where id = ?";
		List<Object> params = new ArrayList<Object>();
		params.add(id);
		List<Map<String,Object>> list = findMoreResult(sql,params);
		roadId = (Long) list.get(0).get("road_id");
		return roadId;
	}
	
	public List<CarState> getCarState(long id) throws Exception{
		String sql = "select * from car_history_data where id > ?";
		List<Object> params = new ArrayList<Object>();
		params.add(id);
		
		List<CarState> CarStateList = findMoreRefResult(sql,params,CarState.class);
		return CarStateList;
	}
	
	public List<ShuntId> getShuntCarId() throws Exception{
		String sql = "select distinct a.* from car_history_data as a where send_time = (select max(b.send_time) from car_history_data as b where a.car_id = b.car_id)  and a.car_id  in (select id from car_basic_info where road_id = 3 and shunt = true)";
		
		List<CarState> shuntCar = findMoreRefResult(sql,null,CarState.class);
		
		List<ShuntId> shuntIdList = new ArrayList<ShuntId>();
		if(shuntCar.size() == 0){
			return shuntIdList;
		}
		
		
		for(int i = 0; i<shuntCar.size();i++){
			if(shuntCar.get(0).getChannel().equals("inside")){
				if(shuntCar.get(0).getHistory_hall_count() > 27 && shuntCar.get(0).getHistory_hall_count() < 51){
					ShuntId shuntId = new ShuntId();
					shuntId.setShuntId(shuntCar.get(0).getCar_id());
					shuntIdList.add(shuntId);
				}
			}else if(shuntCar.get(0).getChannel().equals("outside")){
				if(shuntCar.get(0).getHistory_hall_count() >  22 && shuntCar.get(0).getHistory_hall_count() < 51){
					ShuntId shuntId = new ShuntId();
					shuntId.setShuntId(shuntCar.get(0).getCar_id());
					shuntIdList.add(shuntId);
				}
			}
		} 
		return shuntIdList;
		
	}
	
	public List<ShuntId> getAllShuntCarId() throws Exception{
		String sql = "select distinct a.* from car_history_data as a where send_time = (select max(b.send_time) from car_history_data as b where a.car_id = b.car_id)  and a.car_id  in (select id from car_basic_info where road_id = 3 and shunt = true)";
		
		List<CarState> shuntCar = findMoreRefResult(sql,null,CarState.class);
		
		List<ShuntId> shuntIdList = new ArrayList<ShuntId>();
		if(shuntCar.size() == 0){
			return shuntIdList;
		}
		
			ShuntId shuntId = new ShuntId();
			shuntId.setShuntId(shuntCar.get(0).getCar_id());
			shuntIdList.add(shuntId);
			
		
		
		return shuntIdList;
		
	}

	
}
