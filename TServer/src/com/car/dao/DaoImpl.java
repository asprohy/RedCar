package com.car.dao;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import Bean.PostgreSQL;

import com.entity.CarBasicInfo;
import com.entity.CarCoordinateInfo;
import com.entity.CarState;
import com.entity.CarWarnInfo;
import com.entity.OrderInfo;
import com.sql.JdbcUtils;


public class DaoImpl extends JdbcUtils implements Dao,PostgreSQL{

	//private JdbcUtils jdbcUtils = new JdbcUtils();
	public List<CarState> getCarInfoById(long carId) throws Exception {
		// TODO Auto-generated method stub
		
		//jdbcUtils.getConnection();//select * from car_history_data where send_time =( select max(send_time) from car_history_data )
		//测试使用
	//	super.getConnection();
		//String sql = "select * from car_history_data where car_id=? and send_time =?";
		
		String sql = "select distinct a.*,c.road_id from car_history_data as a, car_basic_info as c where send_time = (select max(b.send_time) from car_history_data as b where a.car_id = b.car_id) and a.car_id = c.id and a.car_id =?";
		List<Object> params = new ArrayList<Object>();
		params.add(carId);
		//params.add(carId);
		//params.add(time);
		//params.add(id);
		
		//System.out.println(params.get(0));
		
		List<CarState> list = findMoreRefResult(sql,params,CarState.class);
	//	super.releaseConn();
		return list;
	}

	public double getCarDistById(long carId) throws Exception {
		// TODO Auto-generated method stub
		
		//jdbcUtils.getConnection();//select * from car_history_data where send_time =( select max(send_time) from car_history_data )
		//测试使用
	//	super.getConnection();
		//String sql = "select * from car_history_data where car_id=? and send_time =?";
		
		String sql = "select distinct a.drive_distance from car_history_data as a where send_time = (select max(b.send_time) from car_history_data as b where a.car_id = b.car_id) and a.car_id =?";
		List<Object> params = new ArrayList<Object>();
		params.add(carId);
		//params.add(carId);
		//params.add(time);
		//params.add(id);
		
		//System.out.println(params.get(0));
		Map<String,Object> result = new HashMap<String,Object>();
		result = findSimpleResult(sql,params);
		
		double dist = Double.parseDouble( String.valueOf(result.get("drive_distance")));
		
	//	super.releaseConn();
		return dist;
	}
	
	public String getCarChannelById(long carId) throws Exception {
		// TODO Auto-generated method stub
		
		//jdbcUtils.getConnection();//select * from car_history_data where send_time =( select max(send_time) from car_history_data )
		//测试使用
	//	super.getConnection();
		//String sql = "select * from car_history_data where car_id=? and send_time =?";
		
		String sql = "select distinct a.drive_distance from car_history_data as a where send_time = (select max(b.send_time) from car_history_data as b where a.car_id = b.car_id) and a.car_id =?";
		List<Object> params = new ArrayList<Object>();
		params.add(carId);
		//params.add(carId);
		//params.add(time);
		//params.add(id);
		
		//System.out.println(params.get(0));
		Map<String,Object> result = new HashMap<String,Object>();
		result = findSimpleResult(sql,params);
		
		String dist = (String) result.get("channel");
		
	//	super.releaseConn();
		return dist;
	}
	
	
	public List<CarState> getCarInfo() throws Exception {//真实的时候去掉时间
		// TODO Auto-generated method stub
		//JdbcUtils jdbcUtils = new JdbcUtils();
	//	super.getConnection();
	
		//params.add(time);取出每个小车的最新数据
		String sql = "select a.*,c.road_id from car_history_data as a,car_basic_info as c where send_time = (select max(b.send_time) from car_history_data as b where a.car_id = b.car_id) and a.car_id = c.id";
		
		List<CarState> list = findMoreRefResult(sql,null,CarState.class);
	//	super.releaseConn();
		return list;
	}


	public List<CarState> getFirstInfo() throws Exception {//得到每个车队头车信息，判断时候需要避让
		// TODO Auto-generated method stub
		long groupId;
	//	super.getConnection();
		String sql1 = "select distinct group_id from car_basic_info group by group_id";
		
		List<Map<String, Object>> list1 = findMoreResult(sql1,null);
		//System.out.println(222);
		//System.out.println(list1.get(0).get("group_id"));
		String sql2;
		List<CarState> list = new ArrayList<CarState>();
		for(int i=0;i<list1.size();i++){
			List<Object> params = new ArrayList<Object>();
			groupId = (Long) list1.get(i).get("group_id");
			params.add(groupId);
			params.add(groupId);
			sql2 = "select a.*,b.road_id from car_history_data a,car_basic_info b where a.group_id =? and drive_distance = (select max(drive_distance) from car_history_data where a.group_id =?) and a.car_id = b.id";
			List<CarState> list2 = findMoreRefResult(sql2,params,CarState.class);
			list.addAll(list2);
			//System.out.println(list.get(i).getCar_id());
		}

	//	super.releaseConn();
		return list;
	}

//	public List<CarWarnInfo> getCarWarnInfo() throws Exception {//得到小车预警信息
//		// TODO Auto-generated method stub
//	//	super.getConnection();
//		String sql = "select * from car_warning_info where channel = '' order by car_id";
//		//List<Object> params = new ArrayList<Object>();
//		//params.add(time);
//		//String sql = "select * from car_history_data where send_time =( select max(send_time) from car_history_data )";
//				
//		List<CarWarnInfo> list = findMoreRefResult(sql,null,CarWarnInfo.class);
//	//	super.releaseConn();
//		return list;
//	}

	public List<CarState> getCarAllInfoById(long id) throws Exception {
		// TODO Auto-generated method stub
	//	super.getConnection();		
		String sql = "select * from car_history_data where car_id=? order by id";		
		
		List<Object> params = new ArrayList<Object>();
		params.add(id);
			
		List<CarState> list = findMoreRefResult(sql,params,CarState.class);
	//	super.releaseConn();
		//System.out.println(list.size()-1);
		return list;
	}

//	public void updateCarChannel(long id,String channel) throws SQLException {
//		// TODO Auto-generated method stub
//	//	super.getConnection();
//		String sql = "update car_warning_info set channel = ? where car_id=?";
//		List<Object> params = new ArrayList<Object>();
//		params.add(channel);
//		params.add(id);
//		boolean confirm = updateByPreparedStatement(sql, params);
//	//	super.releaseConn();
//		
//		if(confirm)
//			System.out.println("车道已设置");
//	}

//	public void updateHall(long carId,long id,long hall) throws SQLException {
//		// TODO Auto-generated method stub ,String channel
//	//	super.getConnection();
////		String sql = "update car_warning_info set channel = ? where car_id=?";
////		List<Object> params = new ArrayList<Object>();
////		params.add(channel);
////		params.add(carId);
////		//System.out.println(channel);
////		boolean confirm = updateByPreparedStatement(sql, params);
////		if(confirm)
////			System.out.println("车道已变更");
//		
//		
//		String sql1 = "update car_history_data set history_hall_count = ? where id=?";
//		List<Object> params1 = new ArrayList<Object>();
//		params1.add(hall);
//		params1.add(id);
//		boolean confirm1 = updateByPreparedStatement(sql1, params1);
//		
//	//	super.releaseConn();
//		if(confirm1)
//			System.out.println("霍尔已更新");
//		
//	}

//	public List<CarWarnInfo> getAllCarWarnInfo() throws Exception {
//		// TODO Auto-generated method stub
//	//	super.getConnection();
//		String sql = "select a.*,b.road_id from car_warning_info a,car_basic_info b where a.car_id = b.id order by car_id";
//		//List<Object> params = new ArrayList<Object>();
//		//params.add(time);
//		//String sql = "select * from car_history_data where send_time =( select max(send_time) from car_history_data )";
//				
//		List<CarWarnInfo> list = findMoreRefResult(sql,null,CarWarnInfo.class);
//	//	super.releaseConn();
//		return list;
//	}

//	public void restoreRoad(long carId) throws Exception{//还原规划  已经舍弃
//		// TODO Auto-generated method stub
//	//	super.getConnection();
//		String sql = "update car_warning_info set road = 0 where car_id=?";
//		List<Object> params = new ArrayList<Object>();
//
//		params.add(carId);
//		boolean confirm = updateByPreparedStatement(sql, params);
//	//	super.releaseConn();
//		
//		if(confirm)
//			System.out.println("道路规划已还原");
//	}

	public List<CarState> getNewestCarInfo() throws Exception {//得到所有最新小车的信息
		// TODO Auto-generated method stub
	//	super.getConnection();
		String sql = "select distinct * from car_history_data where send_time = (select max(send_time) from car_history_data where car_id = car_id)";
		//List<Object> params = new ArrayList<Object>();
		//params.add(time);
		//String sql = "select * from car_history_data where send_time =( select max(send_time) from car_history_data )";
				
		List<CarState> list =findMoreRefResult(sql,null,CarState.class);
//		super.releaseConn();
		return list;
	}

	public long getMaxHallById(long carId) throws SQLException {//得到该车最大的霍尔计数
		// TODO Auto-generated method stub
	//	super.getConnection();
		String sql = "select max(history_hall_count) from car_history_data where car_id =?";
		List<Object> params = new ArrayList<Object>();
		params.add(carId);
		
		long maxHall;
		List<Map<String, Object>> list = findMoreResult(sql,params);
		maxHall = (Long) list.get(0).get("max");
	//	super.releaseConn();
		return maxHall;
	}

	public void clearOldData(long carId,long time) throws SQLException {//清楚该小车的上一圈数据
		// TODO Auto-generated method stub
	//	super.getConnection();
		String sql = "delete from car_history_data where car_id =? and send_time< ?";
		
		List<Object> params = new ArrayList<Object>();
		params.add(carId);
		params.add(time);
		boolean confirm = updateByPreparedStatement(sql, params);
	//	super.releaseConn();
		
		if(confirm)
			System.out.println(carId+"小车上圈数据已清除");
	}

	public List<String> getMacAddress(long groupId) throws Exception {
		// TODO Auto-generated method stub
	//	super.getConnection();
		String sql = "select mac_adress from car_basic_info where group_id =? and shunt = flase group by id";
		List<Object> params = new ArrayList<Object>();
		params.add(groupId);
		List<String> list = new ArrayList<String>();
		list = findMoreRefResult(sql,params,String.class);
	//	super.releaseConn();
		return list;
	}

	public List<CarWarnInfo> getAllCongestionCar() throws Exception{
		// TODO Auto-generated method stub
	//	super.getConnection();
		String sql = "select a.*,b.road_id from car_warning_info a,car_basic_info b where a.congestion = true and a.car_id = b.id order by car_id";
		//List<Object> params = new ArrayList<Object>();
		//params.add(time);
		//String sql = "select * from car_history_data where send_time =( select max(send_time) from car_history_data )";
				
		List<CarWarnInfo> list = findMoreRefResult(sql,null,CarWarnInfo.class);
	//	super.releaseConn();
		return list;
	}

	public void mergeUpdateBasicInfo() throws Exception{
		// TODO Auto-generated method stub
	//	super.getConnection();
		//回到原来的车队，车道恢复  car_id = ? and 
		String sql = "update car_basic_info set shunt = false,road_id = 2 where shunt = true";
		boolean confirm = updateByPreparedStatement(sql, null);//小车都是指定的
		if(confirm){
			System.out.println("小车合流成功");
		}
	//	super.releaseConn();
	}

	public void shuntUpdateBasicInfo(long carId) throws Exception{
		// TODO Auto-generated method stub
	//	super.getConnection();
		//车队开始分流,假设让车1，车2 行驶
		List<Object> para = new ArrayList<Object>();
		para.add(carId);
		String sql = "update car_basic_info set shunt = true,road_id = 3 where id = ?";
		boolean confirm = updateByPreparedStatement(sql, para);//小车都是指定的
		if(confirm){
			System.out.println("小车分流成功");
		}
	//	super.releaseConn();
	}

	public List<CarState> getCarInfoByGroupId(long groupId) throws Exception{
		// TODO Auto-generated method stub
	//	super.getConnection();
		//已经得到验证，可取的最大的值
		//String sql = "select * from car_history_data as a where send_time = (select max(b.send_time) from car_history_data as b where a.car_id = b.car_id ) and group_id=?";
		//不模拟多小队时去掉carid!=5
	    	//String sql = "select id,speed,history_hall_count,car_id,send_time,update_time,create_time,group_id,change_success,drive_distance,heading_degree,channel, road_id from (SELECT *,ROW_NUMBER()OVER(PARTITION BY car_id ORDER BY drive_distance DESC ) AS rn FROM ( select distinct a.*,b.road_id from car_history_data a,car_basic_info b where send_time = (select max(send_time) from car_history_data where a.car_id = car_id ) and a.group_id = ? and b.shunt = false and a.car_id != 5 and a.car_id = b.id )as f ) AS t WHERE t.rn=1";
		String sql = "select id,speed,history_hall_count,car_id,send_time,update_time,create_time,group_id,change_success,drive_distance,heading_degree,channel, road_id from (SELECT *,ROW_NUMBER()OVER(PARTITION BY car_id ORDER BY drive_distance DESC ) AS rn FROM ( select distinct a.*,b.road_id from car_history_data a,car_basic_info b where send_time = (select max(send_time) from car_history_data where a.car_id = car_id ) and a.group_id = ? and b.shunt = false and a.car_id = b.id )as f ) AS t WHERE t.rn=1";
		List<Object> params = new ArrayList<Object>();
		params.add(groupId);
		//params.add(groupId);
		List<CarState> list = findMoreRefResult(sql,params,CarState.class);		
		
	//	super.releaseConn();
		return list;
	}
	
	public List<CarState> getCarInfoByGroupId1(long groupId , long roadId) throws Exception{
		// TODO Auto-generated method stub
	//	super.getConnection();
		//已经得到验证，可取的最大的值
		//String sql = "select * from car_history_data as a where send_time = (select max(b.send_time) from car_history_data as b where a.car_id = b.car_id ) and group_id=?";
		//不模拟多小队时去掉carid!=5
	    	//	String sql = "select id,speed,history_hall_count,car_id,send_time,update_time,create_time,group_id,change_success,drive_distance,heading_degree,channel, road_id from (SELECT *,ROW_NUMBER()OVER(PARTITION BY car_id ORDER BY drive_distance DESC ) AS rn FROM ( select distinct a.*,b.road_id from car_history_data a,car_basic_info b where send_time = (select max(send_time) from car_history_data where a.car_id = car_id and a.group_id = ?) and b.shunt = false and a.car_id != 5 and a.car_id = b.id and b.road_id = ? ) as f ) AS t WHERE t.rn=1";
		String sql = "select id,speed,history_hall_count,car_id,send_time,update_time,create_time,group_id,change_success,drive_distance,heading_degree,channel, road_id from (SELECT *,ROW_NUMBER()OVER(PARTITION BY car_id ORDER BY drive_distance DESC ) AS rn FROM ( select distinct a.*,b.road_id from car_history_data a,car_basic_info b where send_time = (select max(send_time) from car_history_data where a.car_id = car_id and a.group_id = ?) and b.shunt = false and a.car_id = b.id and b.road_id = ? ) as f ) AS t WHERE t.rn=1";
		List<Object> params = new ArrayList<Object>();
		params.add(groupId);
		params.add(roadId);
		//params.add(groupId);
		List<CarState> list = findMoreRefResult(sql,params,CarState.class);		
		
	//	super.releaseConn();
		return list;
	}
	
	//这个是为了把
	public List<CarState> getCarInfoByGroupId2(long groupId , long roadId) throws Exception{
		// TODO Auto-generated method stub
	//	super.getConnection();
		//已经得到验证，可取的最大的值
		//String sql = "select * from car_history_data as a where send_time = (select max(b.send_time) from car_history_data as b where a.car_id = b.car_id ) and group_id=?";
		//不模拟多小队时去掉carid!=5
	    	//	String sql = "select id,speed,history_hall_count,car_id,send_time,update_time,create_time,group_id,change_success,drive_distance,heading_degree,channel, road_id from (SELECT *,ROW_NUMBER()OVER(PARTITION BY car_id ORDER BY drive_distance DESC ) AS rn FROM (  select distinct a.*,b.road_id from car_history_data a,car_basic_info b where send_time = (select max(send_time) from car_history_data where a.car_id = car_id and a.group_id = ?) and b.shunt = true and a.car_id != 5 and a.car_id = b.id and b.road_id = ?  ) as f ) AS t WHERE t.rn=1";
		String sql = "select id,speed,history_hall_count,car_id,send_time,update_time,create_time,group_id,change_success,drive_distance,heading_degree,channel, road_id from (SELECT *,ROW_NUMBER()OVER(PARTITION BY car_id ORDER BY drive_distance DESC ) AS rn FROM (  select distinct a.*,b.road_id from car_history_data a,car_basic_info b where send_time = (select max(send_time) from car_history_data where a.car_id = car_id and a.group_id = ?) and b.shunt = true and a.car_id = b.id and b.road_id = ?  ) as f ) AS t WHERE t.rn=1";
		List<Object> params = new ArrayList<Object>();
		params.add(groupId);
		params.add(roadId);
		//params.add(groupId);
		List<CarState> list = findMoreRefResult(sql,params,CarState.class);		
		
	//	super.releaseConn();
		return list;
	}

	public List<CarState> getDistanceByCarId(long carId) throws Exception{
		// TODO Auto-generated method stub
	//	super.getConnection();
		
		//List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		//List<CarState> list = new ArrayList<CarState>();
		String sql = "select distinct a.*,b.road_id from car_history_data a,car_basic_info b where a.car_id = b.id  and send_time = (select max(c.send_time) from car_history_data as c where a.car_id = c.car_id ) and car_id = ?";
		List<Object> params = new ArrayList<Object>();
		params.add(carId);
		List<CarState> list = findMoreRefResult(sql,params,CarState.class);
		
	//	super.releaseConn();
		return list;
	}

	public long getHallByDistance(long roadId, String channel, double distance) throws Exception{
		// TODO Auto-generated method stub
		//super.getConnection();
		
		List<Object> params = new ArrayList<Object>();
		params.add(roadId);
		params.add(channel);
		params.add(distance);
		String sql = "select Max(history_hall_count) from car_history_data a ,car_basic_info b where a.car_id = b.id and road_id =? and channel =? and dirive_distance = ?";
		//
		//
		Map<String,Object> result = new HashMap<String,Object>();
		result = findSimpleResult(sql,params);
		
		long hall = (Long) result.get("history_hall_count");
		
 		//super.releaseConn();
		return hall;
	}

	public long getCarIdByCarNum(long groupId, int carNum) throws Exception{
		// TODO Auto-generated method stub
		//super.getConnection();
		
		String sql = "select id from car_basic_info where group_id = ? and car_num = ?";
		List<Object> params = new ArrayList<Object>();
		params.add(groupId);
		params.add(carNum);
		Map<String,Object> result = findSimpleResult(sql,params);
		long carId = (Long) result.get("id"); 
		
		//super.releaseConn();
		return carId;
	}
	
	public long getGroupIdById(long carId) throws Exception{
		
		String sql = "select group_id from car_basic_info where id = ?";
		List<Object> params = new ArrayList<Object>();
		params.add(carId);
		Map<String,Object> result = findSimpleResult(sql,params);
		long groupId = (Long) result.get("group_id");
		
		return groupId;
		
	}

	public long getGroupIdbyGroupNum(int groupNum) throws Exception{
		
		//Connection conn = super.getConnection();	
		String sql = "select id from t_car_group where group_num = ?";
		List<Object> params = new ArrayList<Object>();
		params.add(groupNum);
		int c=0;
		Map<String,Object> map = findSimpleResult(sql, params);
		for (Entry<String, Object> entry : map.entrySet()) {  
	           Object a=entry.getValue();  
	           String b = a.toString();
	           c = Integer.valueOf(b).intValue();	          
	        }     
		//super.releaseConn();
		return c;
	}
	
	public long getCarNumbyGroupId(long l , long roadId) throws Exception{
		
		//Connection conn = super.getConnection();	
		String sql = "select count(*) from car_basic_info where group_id = ? and road_id = ? and id != 5 ";
		List<Object> params = new ArrayList<Object>();
		params.add(l);
		params.add(roadId);
		long c=0;
		Map<String,Object> map = findSimpleResult(sql, params);
		for (Entry<String, Object> entry : map.entrySet()) {  
	           Object a=entry.getValue();  
	           String b = a.toString();
	           c = Integer.valueOf(b).intValue();	          
	        }     
		//super.releaseConn();
		return c;
	}
	
	
	public void updateOrderInfo(String steamNum, String sSpeed,String sCarNum,
			String sDistance, String sAngle, String sDirect) throws Exception {
		//保存的都是最新的命令信息
		// TODO Auto-generated method stub
		int speed = Integer.valueOf(sSpeed).intValue();
		double distance = Double.valueOf(sDistance).doubleValue();
		int angle = 0;
		String direction = "0";
		
		//OrderInfo order = new OrderInfo();
		List<Object> params = new ArrayList<Object>();
		String sql ="";
		if(steamNum.equals("All")){
			sql = "update t_order_data set distance = ?,speed=?,angle=?,direction=?";
			params.add(distance);
			params.add(speed);
			params.add(angle);
			params.add(direction);
		}else{
			long groupId = Long.valueOf(steamNum).intValue();
			sql = "update t_order_data set distance = ?,speed=?,angle=?,direction=? where group_id=?";
			params.add(distance);
			params.add(speed);
			params.add(angle);
			params.add(direction);
			params.add(groupId);
		}
		
		boolean confirm = updateByPreparedStatement(sql, params);//小车都是指定的
		if(confirm){
			System.out.println("小车命令更新成功");
		}
		
	}
	
	public List<OrderInfo> getOrderInfoByGroupId(long groupId) throws Exception{
		//获得小车的命令信息，判断车间距离需要
		String sql = "select * from t_order_data where group_id=?";
		List<Object> params = new ArrayList<Object>();
		params.add(groupId);
		List<OrderInfo> list = findMoreRefResult(sql,params,OrderInfo.class);
		
	//	super.releaseConn();
		return list;
		
	}

	public String getMacById(long carId) throws Exception{
		//根据小车id得到小车的mac地址
		
		String sql = "select mac_adress from car_basic_info where id =?";
		List<Object> params = new ArrayList<Object>();
		params.add(carId);
		Map<String,Object> result = findSimpleResult(sql, params);
				
		String mac = (String) result.get("mac_adress");
		return mac;	
	}

	public List<CarBasicInfo> getBasicInfoById(int carId) throws Exception{
		// TODO Auto-generated method stub
		
		String sql = "select * from car_basic_info where id =?";
		List<Object> params = new ArrayList<Object>();
		params.add(carId);
		List<CarBasicInfo> list = findMoreRefResult(sql,params,CarBasicInfo.class);
					
		return list;
	}

	public List<CarBasicInfo> getBasicInfoByGroupId(long groupId) throws Exception{
		// TODO Auto-generated method stub
		String sql ="select * from car_basic_info where group_id = ?";
		List<Object> params = new ArrayList<Object>();
		params.add(groupId);
		
		List<CarBasicInfo> list = findMoreRefResult(sql,params,CarBasicInfo.class);
		return list;
	}

	public List<Integer> getAllCarId() throws Exception{
		// TODO Auto-generated method stub
		
		String sql = "select id from car_basic_info";
		List<Map<String,Object>> list = findMoreResult(sql,null);
		
		if(list.size() == 0){//理论不存在
			return null;
		}
		
		List<Integer> allCarId = new ArrayList<Integer>();
		for(int i=0;i<list.size();i++){
			int carId = ((Long) list.get(i).get("id")).intValue(); 
			allCarId.add(carId);
		}
		
		return allCarId;
	}

	public Map<String, Object> getMacIdByCarNum(String teamNum, String carNum) throws Exception{
		// TODO Auto-generated method stub
		long groupId = Long.valueOf(teamNum).longValue();
		int carN = Integer.valueOf(carNum).intValue();
		
		List<Object> para = new ArrayList<Object>();
		para.add(carN);
		para.add(groupId);
		
		String sql = "select id,mac_adress from car_basic_info where car_num = ? and group_id = ?";
		Map<String, Object> result = findSimpleResult(sql, para);
		return result;
	}

	public void updateCongestion1(long carId) throws Exception{
		// TODO Auto-generated method stub
		//将拥堵小车设为不拥堵
		String sql = "update car_warning_info set congestion = false where car_id = ? ";
		List<Object> para = new ArrayList<Object>();
		para.add(carId);
		
		boolean confirm = updateByPreparedStatement(sql, para);
		if(confirm){
			System.out.println("拥堵小车已经疏通...");
		}
	}

	public List<CarState> getOldCarState(long carId, long time) throws Exception{
		// TODO Auto-generated method stub
		long timeMax = time + 200;
		long timeMin = time - 200;
		String sql = "select * from car_history_data where car_id = ? and send_time > ? and send_time < ?";
		List<Object> para = new ArrayList<Object>();
		para.add(carId);
		para.add(timeMin);
		para.add(timeMax);
		
		List<CarState> list = findMoreRefResult(sql,para,CarState.class);
		return list;
	}

	public void updateCongestion2(long carId) throws Exception{
		// TODO Auto-generated method stub
		//将拥堵小车设为拥堵
		String sql = "update car_warning_info set congestion = true where car_id = ? ";
		List<Object> para = new ArrayList<Object>();
		para.add(carId);
		
		boolean confirm = updateByPreparedStatement(sql, para);
		if(confirm){
			System.out.println("小车发生拥堵...");
		}
	}

	public List<OrderInfo> getOrderInfoByShunt() throws Exception{
		// TODO Auto-generated method stub 分流后恢复速度使用
		String sql = "select * from t_order_data where group_id = (select group_id from car_basic_info where shunt = true) ";
		
		List<OrderInfo> list = findMoreRefResult(sql,null,OrderInfo.class);;
		return list;
	}

	public List<CarBasicInfo> getBasicInfoByShunt()throws Exception {
		// TODO Auto-generated method stub
		String sql = "select * from car_basic_info where group_id = (select group_id from car_basic_info where shunt = true) ";
		
		List<CarBasicInfo> list = findMoreRefResult(sql,null,CarBasicInfo.class);;
		return list;
	}

	public ArrayList<Long> getAllGroupId() throws Exception{
		// TODO Auto-generated method stub
		ArrayList<Long> list = new ArrayList<Long>();
		String sql = "select distinct group_id from car_basic_info order by group_id";
		
		List<Map<String, Object>> list1 = findMoreResult(sql,null);
		
		for(int i = 0;i < list1.size();i++){
			long groupId = (Long) list1.get(i).get("group_id");
			list.add(groupId);
		}
		
		return list;

	}
	
	public List<CarCoordinateInfo> getCarCoordinateInfos(long road_id, String channel, int beginHall, int endHall) throws Exception{
    	String sql = "select * from car_coordinate_info where road_id = ? and channel = ? and hall >= ? and hall <= ? order by hall ";
    	List<Object> para = new ArrayList<Object>();
    	para.add(road_id);
		para.add(channel);
		para.add(beginHall);
		para.add(endHall);
		List<CarCoordinateInfo> list = findMoreRefResult(sql,para,CarCoordinateInfo.class);
		return list;
}

	public void saveStopOrder(String steamNum, String sCarNum,
			String sType) throws Exception{//保存小车急停命令，在小车距离判断中，不让小车再次启动
		// TODO Auto-generated method stub
		List<Object> para = new ArrayList<Object>();
		
		long groupId = 0;
		int carNum = 0;
		
		boolean confirm = false;
		String sql ="";
		
		if(steamNum.equals("All") && sType.equals("team")){//全部都停了
			sql = "update car_warning_info set stop = true";
			confirm = updateByPreparedStatement(sql, null);
		}else if(!steamNum.equals("All") && sType.equals("team")){//某个车队停了
			groupId = Long.valueOf(steamNum).longValue();
			para.add(groupId);
			sql = "update car_warning_info set stop = true where car_id in (select id from car_basic_info where group_id = ?)";
			confirm = updateByPreparedStatement(sql, para);
		}else if(sType.equals("single")){//某个小车停了
			groupId = Long.valueOf(steamNum).longValue();
			carNum = Integer.valueOf(sCarNum).intValue();
			//long carId = 0;
			List<Object> para1 = new ArrayList<Object>();
			String sql1 = "select * from car_warning_info where car_id = (select id from car_basic_info where group_id = ? and car_num = ?)";
			para1.add(groupId);
			para1.add(carNum);
			
			List<CarWarnInfo> list = findMoreRefResult(sql1,para1,CarWarnInfo.class);
			
			if(list.size() > 0){
				sql = "update car_warning_info set stop = true where car_id = (select id from car_basic_info where group_id = ? and car_num = ?)";
			}
			para.add(groupId);
			para.add(carNum);
			confirm = updateByPreparedStatement(sql, para);
		}
		
		if(confirm){
			System.out.println("保存急停命令成功！！！");
		}
	}

	public void updateStopOrder(String sTeam, String sCar, String sType) throws Exception{
		// TODO Auto-generated method stub
		List<Object> para = new ArrayList<Object>();
		String sql = "";
		boolean confirm = false;
		long groupId = 0;
		int carNum = 0;
		if(sType.equals("all")){
			sql = "update car_warning_info set stop = false";
			confirm = updateByPreparedStatement(sql, null);
		}else if(sType.equals("team")){
			groupId = Long.valueOf(sTeam).longValue();
			para.add(groupId);
			sql = "update car_warning_info set stop = false where car_id in (select id from car_basic_info where group_id = ?)";
			confirm = updateByPreparedStatement(sql, para);
		}else if(sType.equals("single")){
			groupId = Long.valueOf(sTeam).longValue();
			carNum = Integer.valueOf(sCar).intValue();
			para.add(groupId);
			para.add(carNum);
			sql = "update car_warning_info set stop = false where car_id = (select id from car_basic_info where group_id = ? and car_num = ?)";
			confirm = updateByPreparedStatement(sql, para);
		}
		
		if(confirm){
			System.out.println("消除急停命令成功！！！");
		}
	}

	public List<CarWarnInfo> getStopInfo(int carId) throws Exception{
		// TODO Auto-generated method stub
		List<Object> para = new ArrayList<Object>();
		para.add(carId);
		
		String sql = "select * from car_warning_info where car_id =?";
		List<CarWarnInfo> list = findMoreRefResult(sql,para,CarWarnInfo.class);
		return list;
	}
	
	public int getMaxHall(long road_id, String channel) throws Exception{
		String sql = "select max(hall) from car_coordinate_info where road_id = ? and channel = ?";
		long maxHall;
		List<Object> para = new ArrayList<Object>();
		para.add(road_id);
		para.add(channel);
		Map<String,Object> result = findSimpleResult(sql, para);
		maxHall = (Long) result.get("max");
		int MaxHall = (int) maxHall;
		return MaxHall;
		
	}

	public List<CarState> getSimulateGroup(int carId) throws Exception{//模拟多队使用
		// TODO Auto-generated method stub
		DaoImpl dao = new DaoImpl();
		long groupId = dao.getGroupIdById(carId);
		//		String sql = "select distinct a.*,b.road_id from car_history_data a,car_basic_info b where send_time = (select max(send_time) from car_history_data where a.car_id = car_id and a.group_id = ?) and b.shunt = false and a.car_id != 5 and a.car_id = b.id";

		String sql = "select distinct a.*,b.road_id from car_history_data a,car_basic_info b where send_time = (select max(send_time) from car_history_data where a.car_id = car_id and a.group_id = ?) and b.shunt = false and a.car_id = b.id";
		List<Object> params = new ArrayList<Object>();
		params.add(groupId);
		List<CarState> list = findMoreRefResult(sql,params,CarState.class);		

		return list;
	}
	
	
	public List<CarState> getCarInfoByDist(long carId, double safeDist) throws Exception{//获取不是该组车的  后距离内的车id
	
		DaoImpl dao = new DaoImpl();
		Object ob = dao.getCarDistById(carId);
		double dist = Double.parseDouble(String.valueOf(ob));

		
		
		String sql = "select distinct a.*,b.road_id " +
				"from car_history_data a,car_basic_info b, car_basic_info c " +
				"where send_time = (select max(send_time) from car_history_data where a.car_id = car_id ) " +
				"and b.shunt = false and a.car_id = b.id " +
				"and c.id = ? and c.road_id = b.road_id and a.channel = ( select distinct (s.channel) from car_history_data s where s.send_time = (select max(t.send_time) from car_history_data t where s.car_id = t.car_id ) and s.car_id = ? )  " +  //同一条
				//"and a.car_id = 5 "+ //
				"and a.group_id != c.group_id " +
				
				"and a.drive_distance - ? <= 0 " +
				"and ? - a.drive_distance <= ? order by history_hall_count DESC";//(!=5)？
		List<Object> params = new ArrayList<Object>();
		params.add(carId);
		params.add(carId);
		params.add(dist);
		params.add(dist);
		params.add(safeDist);
		//params.add(groupId);
		List<CarState> list = findMoreRefResult(sql,params,CarState.class);		
		
		
	return list;
	
	}
	
	public List<CarState> getCarInfoByDist1(long carId, double DeSafeDist , double RoadLenghth) throws Exception{
		//过终点
		DaoImpl dao = new DaoImpl();
		Object ob = dao.getCarDistById(carId);
		

		String sql = "select distinct a.*,b.road_id " +
				"from car_history_data a,car_basic_info b, car_basic_info c " +
				"where send_time = (select max(send_time) from car_history_data where a.car_id = car_id ) " +
				"and b.shunt = false and a.car_id = b.id " +
				"and c.id = ? and c.road_id = b.road_id and a.channel = ( select distinct (s.channel) from car_history_data s where s.send_time = (select max(t.send_time) from car_history_data t where s.car_id = t.car_id ) and s.car_id = ? )  " +  //同一条
				//"and a.car_id = 5 " +//
				"and a.group_id != c.group_id " +
				"and a.drive_distance  <= ? " +
				"and ? - a.drive_distance <= ? order by history_hall_count DESC";//(!=5)？
		List<Object> params = new ArrayList<Object>();
		params.add(carId);
		params.add(carId);
		params.add(RoadLenghth);
		params.add(RoadLenghth);
		params.add(DeSafeDist);
		//params.add(groupId);
		List<CarState> list = findMoreRefResult(sql,params,CarState.class);		
		
		
	return list;
	}
		
	
	
	public List<CarState> getLastCongestionCar() throws Exception{
		
		//String sql = "SELECT id,speed,history_hall_count,car_id,send_time,update_time,create_time,group_id,change_success,drive_distance,heading_degree,channel FROM ( SELECT *,ROW_NUMBER()OVER(PARTITION BY group_id ,road_id,channel ORDER BY drive_distance ASC ) AS rn FROM (select c.*,b.road_id from car_warning_info a,car_basic_info b ,car_history_data c  where send_time = (select max(send_time) from car_history_data where c.car_id = car_id ) and a.congestion = true and a.car_id = b.id and c.car_id = b.id and b.shunt = false order by car_id ) as f ) AS t WHERE t.rn=1";
		String sql ="select id,speed,history_hall_count,car_id,send_time,update_time,create_time,group_id,change_success,drive_distance,heading_degree,channel, road_id from (SELECT *,ROW_NUMBER()OVER(PARTITION BY car_id ORDER BY drive_distance DESC ) AS rn FROM ( select distinct a.*,b.road_id from car_history_data a,car_basic_info b where send_time = (select max(send_time) from car_history_data where a.car_id = car_id and a.group_id = ? ) and b.shunt = false and a.car_id = b.id and b.road_id = ? )as f ) AS t WHERE t.rn=1";
	    
	    	//List<Object> params = new ArrayList<Object>();
		//params.add(time);
		//String sql = "select * from car_history_data where send_time =( select max(send_time) from car_history_data )";
				
		List<CarState> list = findMoreRefResult(sql,null,CarState.class);
		System.out.println("Database尾车信息:"+list.toArray().toString());
		
		return list;
		
	}
	
	public long getHallByCarId(long carId) throws Exception{
	    
	    String sql = "select max(history_hall_count) from car_history_data where car_id = ? and send_time = (select max(send_time) from car_history_data where car_id = ?)";
	    List<Object> params = new ArrayList<Object>();
	    params.add(carId);
	    params.add(carId);
	    int c=0;
		Map<String,Object> map = findSimpleResult(sql, params);
		System.out.println(map.size());
		for (Entry<String, Object> entry : map.entrySet()) {  
	           Object a=entry.getValue();  
	           String b = a.toString();
	           if(b != ""){
	               c = Integer.valueOf(b).intValue();
	           }
	           	          
	        }     
		//super.releaseConn();
	    return c;
	}

	public int getMinHall(long road_id, String channel) throws Exception {
	    // TODO Auto-generated method stub
	    String sql = "select min(hall) from car_coordinate_info where road_id = ? and channel = ?";
		long maxHall;
		List<Object> para = new ArrayList<Object>();
		para.add(road_id);
		para.add(channel);
		Map<String,Object> result = findSimpleResult(sql, para);
		maxHall = (Long) result.get("min");
		int MaxHall = (int) maxHall;
		return MaxHall;
	    
	}
	
	public List<Integer> getShuntCarId() throws Exception{
	    ArrayList<Integer> list = new ArrayList<Integer>();
	    String sql = "select distinct car_num from car_basic_info where shunt = true";
		
	    List<Map<String, Object>> list1 = findMoreResult(sql,null);
		
	    for(int i = 0;i < list1.size();i++){
		int carId = (Integer) list1.get(i).get("car_num");
		list.add(carId);
	    }
		
	    return list;
	}
	
	public CarCoordinateInfo getCarCoordinateInfo(long road_id, String channel, long hall_count) throws Exception{

	    String sql = "select * from car_coordinate_info where road_id = ? and channel = ? and hall =?";
	    List<Object> para = new ArrayList<Object>();
	    para.add(road_id);
	    para.add(channel);
	    para.add(hall_count);
	    List<CarCoordinateInfo> list = findMoreRefResult(sql,para,CarCoordinateInfo.class);
		
	    return list.get(0);
	}
	
	public double getHallMinDistance(long carId,long Hall) throws Exception{
		
		String sql = "select min(drive_distance) from car_history_data	where car_id = ? and history_hall_count = ?";
		
		List<Object> params = new ArrayList<Object>();
		params.add(carId);
		params.add(Hall);
		Map<String,Object> result = new HashMap<String,Object>();
		result = findSimpleResult(sql,params);
		
		double dist = Double.parseDouble(String.valueOf(result.get("min")));
		
		return dist;
	}

	public void initSequenceFalse() throws Exception{
	    String sql = "update car_basic_info set sequence_flag = 'false'";
	    updateByPreparedStatement(sql, null);
	}
	
	
	public List<Long> getSequnceFalseCar() throws Exception{
	    List<Long> carIdList = new ArrayList<>();
	    
	    String sql = "select id from car_basic_info where is_used = 'true' and sequence_flag = 'false'";
	    
	    List<Map<String, Object>> list = findMoreResult(sql,null);
	    if(list.size() == 0){
		return null;
	    }
	    
	    for(int i = 0;i < list.size();i++){
		long carId = (long) list.get(i).get("id");
		carIdList.add(carId);
	    }
	    
	    return carIdList;
	}

	public int getSite(long carId) throws Exception{
	    long groupId = getGroupIdById(carId);
	    String sql = "select car_id, max(drive_distance) from car_history_data where group_id = ? group by car_id";
	    List<Object> params = new ArrayList<Object>();
	    params.add(groupId);
	    List<Map<String, Object>> list = findMoreResult(sql,params);
	 
	    int index = 1;
	    list = findMoreResult(sql,params);
	    double distance = 0;
	    for(int i = 0; i < list.size(); i++){
		if((long)(list.get(i).get("car_id")) == carId){
		    distance = Double.parseDouble(String.valueOf(list.get(i).get("max")));
		}
	    }
	    
	    if(distance == 0.0){
		return 0;
	    }
	    for(int i = 0; i < list.size(); i++){
		if(Double.parseDouble(String.valueOf(list.get(i).get("max"))) > distance){
		    index = index + 1;
		}
	    }
	    return index;
	}
	
	public void updateSiteOfCar(long carId, long sequenceNum) throws Exception{
	    String sql = "update car_basic_info set sequence_num = ? where id = ?";
	    List<Object> params = new ArrayList<Object>();
	    params.add(sequenceNum);
	    params.add(carId);
	    updateByPreparedStatement(sql, params);
	    
	    String sql2 = "update car_basic_info set sequence_flag = 'true' where id = ?";
	    List<Object> params2 = new ArrayList<Object>();
	    params2.add(carId);
	    updateByPreparedStatement(sql2, params2);
	}
	

	public Long getHeadCarId(long groupId) throws Exception{
		
		Long HeadCarId;
		List<Object> params = new ArrayList<Object>();
		String sql = "select id from car_basic_info where sequence_num = (select min(sequence_num) from car_basic_info where group_id = ? and shunt = 'false' and sequence_flag = 'true' ) and group_id = ? and shunt = 'false' and sequence_flag = 'true' ";
		params.add(groupId);
		params.add(groupId);
		
		Map<String,Object> result = findSimpleResult(sql, params);
		HeadCarId = (Long) result.get("id");
		
		return HeadCarId;
	
	}
	
	public Long getTailCarId(long groupId) throws Exception{
		
		Long HeadCarId;
		List<Object> params = new ArrayList<Object>();
		String sql = "select id from car_basic_info where sequence_num = (select max(sequence_num) from car_basic_info where group_id = ? and shunt = 'false' and sequence_flag = 'true' ) and group_id = ? and shunt = 'false' and sequence_flag = 'true' ";
		params.add(groupId);
		params.add(groupId);
		
		Map<String,Object> result = findSimpleResult(sql, params);
		HeadCarId = (Long) result.get("id");
		
		return HeadCarId;
	
	}
	
	
	public ArrayList<Long> getAllHeadCarId(long roadId) throws Exception{
		
		ArrayList<Long> list = new ArrayList<Long>();
		List<Object> params = new ArrayList<Object>();
		String sql = "select id from ( SELECT *,ROW_NUMBER()OVER(PARTITION BY group_id ORDER BY sequence_num ASC ) AS rn FROM ( select * from car_basic_info where sequence_flag = 'true' and road_id = ? and shunt = 'false' )as f ) AS t WHERE t.rn=1";
		params.add(roadId);
		
		List<Map<String, Object>> list1 = findMoreResult(sql,params);
		for(int i = 0;i < list1.size();i++){
			long id = (Long) list1.get(i).get("id");
			list.add(id);
		}
		return list;
		
	}
	
	
	public ArrayList<Long> getAllTailCarId(long roadId) throws Exception{
		
		ArrayList<Long> list = new ArrayList<Long>();
		List<Object> params = new ArrayList<Object>();
		String sql = "select id from ( SELECT *,ROW_NUMBER()OVER(PARTITION BY group_id ORDER BY sequence_num DESC ) AS rn FROM ( select * from car_basic_info where sequence_flag = 'true' and road_id = ? and shunt = 'false' )as f ) AS t WHERE t.rn=1";
		params.add(roadId);
		
		List<Map<String, Object>> list1 = findMoreResult(sql,params);
		for(int i = 0;i < list1.size();i++){
			long id = (Long) list1.get(i).get("id");
			list.add(id);
		}
		return list;
		
	}

	public ArrayList<Long> getAllRoadHeadCarId() throws Exception{
		
		ArrayList<Long> list = new ArrayList<Long>();
		String sql = "select id from ( SELECT *,ROW_NUMBER()OVER(PARTITION BY group_id ORDER BY sequence_num ASC ) AS rn FROM ( select * from car_basic_info where sequence_flag = 'true' and shunt = 'false' )as f ) AS t WHERE t.rn=1";

		List<Map<String, Object>> list1 = findMoreResult(sql,null);
		for(int i = 0;i < list1.size();i++){
			long id = (Long) list1.get(i).get("id");
			list.add(id);
		}
		return list;
		
	}
	
	public ArrayList<Long> getAllRoadTailCarId() throws Exception{
		
		ArrayList<Long> list = new ArrayList<Long>();
		String sql = "select id from ( SELECT *,ROW_NUMBER()OVER(PARTITION BY group_id ORDER BY sequence_num DESC ) AS rn FROM ( select * from car_basic_info where sequence_flag = 'true' and shunt = 'false' )as f ) AS t WHERE t.rn=1";
		
		List<Map<String, Object>> list1 = findMoreResult(sql,null);
		for(int i = 0;i < list1.size();i++){
			long id = (Long) list1.get(i).get("id");
			list.add(id);
		}
		return list;
	}
}
