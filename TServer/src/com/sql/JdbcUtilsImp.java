package com.sql;


import java.sql.Connection;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.entity.CarState;

import connect.para.ReceiveEndPointPara;

import Bean.PostgreSQL;



public class JdbcUtilsImp extends JdbcUtils implements JdbcUtilsDao,PostgreSQL
{		
	public List<Map<String,Object>> listCarGroupId() throws Exception {
		// TODO Auto-generated method stub
		//Connection conn = super.getConnection();
		String sql ="select group_num from t_car_group ";
		List<Map<String,Object>> list = findMoreResult(sql, null);		
		//super.releaseConn();
		return list;
	}	
	
	public List<Map<String,Object>> listCarNumByGroupId(int groupNum) throws Exception{
		
		//Connection conn = super.getConnection();	 
		//System.out.println(groupNum); 
		String sql ="select car_num from car_basic_info where group_id =? ";
		List<Object> params = new ArrayList<Object>();
		int num = groupNum;
		
		String sql1 = "select id from t_car_group where group_num = ?";
		List<Object> params1 = new ArrayList<Object>();
		params1.add(num);
		int c=0;
		Map<String,Object> map = findSimpleResult(sql1, params1);
		for (Entry<String, Object> entry : map.entrySet()) {  
	           Object a=entry.getValue();  
	           String b = a.toString();
	           c = Integer.valueOf(b).intValue();	          
	        }     
		
		
		
		params.add(c);	
		List<Map<String,Object>> list = findMoreResult(sql, params);
		
		//super.releaseConn();
		return list;
		
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
	
	public String getMacAddress(int teamNum, int carNum) throws Exception{
		//Connection conn = super.getConnection();
		
		List<Object> params = new ArrayList<Object>();
		
		String sql1 = "select id from t_car_group where group_num = ?";
		List<Object> params1 = new ArrayList<Object>();
		params1.add(teamNum);
		long groupId=0;
		Map<String,Object> map1 = findSimpleResult(sql1, params1);
		for (Entry<String, Object> entry : map1.entrySet()) {  
	           Object a=entry.getValue();  
	           String b = a.toString();
	           groupId = Integer.valueOf(b).intValue();	          
	        }     
		
		//long groupId = getGroupIdbyGroupNum(teamNum);	
		
		
		String sql = "select mac_adress from car_basic_info where car_num=? and group_id =?";
		params.add(carNum);
		params.add(groupId);
		
		Map<String,Object> map = findSimpleResult(sql, params);
		String macAddress = null;
		for (Entry<String, Object> entry : map.entrySet()) {  			
	           Object a=entry.getValue();  
	           macAddress = a.toString();
	        }     
		//super.releaseConn();
		return macAddress;
	}
	
	public List<String> listMacByGroupId(int groupId) throws Exception{
		//Connection conn = super.getConnection();
		
		List<Object> params = new ArrayList<Object>();
		String sql = "select mac_adress from car_basic_info where group_id =?";
		params.add(groupId);
		List<Map<String,Object>> list = findMoreResult(sql, params);
		
		List<String> list1 = new ArrayList<String>();
		
		for(int i =0; i<list.size(); i++) {
			Map<String,Object> map = list.get(i);
			for(Entry<String, Object> entry : map.entrySet()) {
				Object a = entry.getValue();
				list1.add(i,a.toString());
			}
		}
		
		//super.releaseConn();
		return list1;
		
	}
	
//	public boolean JudgeCongestionByTime() throws Exception{
//		jdbcUtils.getConnection();
//		
//		String sql = "select group_id from car_history_data where create_time";
//		boolean flag = false;
//		return flag;
//	}
	
	
	
	public void addCarHistory(List<CarState> list) throws Exception{
		//Connection conn = super.getConnection();
		
		List<Object> params = new ArrayList<Object>();
		
		String sql = "insert into car_history_data (speed,drive_distance,history_hall_count,car_id,send_time,"
				+ "create_time,heading_degree,group_id,change_success,update_time,channel) values(?,?,?,?,?,?,?,?,?,?,?)";
		
		for (CarState para : list) {
            if (para.getHistory_hall_count() == 0){
        	continue;
            }
            long currTime = System.currentTimeMillis();
            Timestamp timeObj = new Timestamp(currTime);            //yyyy-MM-dd HH:mm:ss:mis     
            String createTime = timeObj.toString().substring(0,19);       //yyyy-MM-dd HH:mm:ss    
    		String updateTime = createTime;	

    		//long groupId = para.getGroup_id();
            params.clear();
    		params.add(para.getSpeed1());
    		params.add(para.getDrive_distance1());
    		params.add(para.getHistory_hall_count());
    		params.add(para.getCar_id());
    		params.add(para.getSend_time());
    		params.add(timeObj);//createTime
    		params.add(para.getHeading_degree());
    		params.add(para.getGroup_id());
    		params.add(para.isChange_success());
    		params.add(timeObj);//updateTime
    		//System.out.println("para.getChannel()"+para.getChannel());
    		String channel="";
    		if(para.getChannel().equals("1")){
    			channel = "inside";
    		}else if(para.getChannel().equals("2")){
    			channel = "ouside";
    		}
    		//System.out.println("channel"+channel);
    		params.add(channel);
    		boolean flag = updateByPreparedStatement(sql, params);
    		if(flag){
    			System.out.println("小车历史数据添加成功！");  
    			
    		} else{
    			System.out.println("小车历史数据添加失败！");
    		}		    		    		    
		  
		}
		
		//super.releaseConn();
		//return flag;
		
	}
	
	public boolean addCarHistory(long carId, ReceiveEndPointPara input) throws Exception{
		//Connection conn = super.getConnection();
		
		List<Object> params = new ArrayList<Object>();
		String sql = "insert into car_history_data (speed,drive_distance,history_hall_count,car_id,send_time,"
				+ "create_time,heading_degree,group_id,change_success,update_time,channel) values(?,?,?,?,?,?,?,?,?,?,?)";
		
		long currTime = System.currentTimeMillis();
        Timestamp timeObj = new Timestamp(currTime);            //yyyy-MM-dd HH:mm:ss:mis     
        String createTime = timeObj.toString().substring(0,19);       //yyyy-MM-dd HH:mm:ss    
		String updateTime = createTime;
		
		String carNumber = input.getCarNumber();
		String groupId1 = carNumber.substring(0, 2);
		long groupId = Long.valueOf(groupId1).longValue();
//        long groupId = 0;
//        String sql1 = "select group_id from car_basic_info where id=?";
//		List<Object> params1 = new ArrayList<Object>();
//		params.add(carId);
//		Map<String, Object> map = findSimpleResult(sql1,params1);
//		for(Entry<String, Object> entry : map.entrySet()) {
//			Object a = entry.getValue();
//			groupId = Long.valueOf(a.toString()).longValue();
//		}
        
        
        
		params.add(Float.parseFloat(input.getSpeed()));
		params.add(Float.parseFloat(input.getDistance()));
		params.add(input.getHoare());
		params.add(carId);
		params.add(input.getTime());
		params.add(timeObj);
		params.add(Integer.parseInt(input.getSwerveAngle()));
		params.add(groupId);
		params.add(input.isChangeSuccess());
		params.add(timeObj);
		System.out.println("InOutSide()"+input.getInOutSide());
		String channel="";
		if(input.getInOutSide().equals("1")){
			channel = "inside";
		}else if(input.getInOutSide().equals("2")){
			channel = "ouside";
		}
		//System.out.println("channel"+channel);
		params.add(channel);
		boolean flag = updateByPreparedStatement(sql, params);
		if(flag){
			System.out.println("小车历史数据添加成功！");  
			
		} else{
			System.out.println("小车历史数据添加失败！");
		}
		
		//super.releaseConn();
		return flag;
	}
	
	public long getCarIdByMac(String macAddress) throws Exception {
		//Connection conn = super.getConnection();
		
		List<Object> params = new ArrayList<Object>();
		String sql = "select id from car_basic_info where mac_adress=?";
		params.add(macAddress);
		
		long carId=0;
		Map<String, Object> map = findSimpleResult(sql,params);
		for (Entry<String, Object> entry : map.entrySet()) {  	
			Object a=entry.getValue();  
			String b = a.toString();   
			carId = Integer.valueOf(b).intValue();
		}
		//super.releaseConn();
		return carId;
	}
	
	public double getSingleCarDistance(int teamNumber, int carNumber) throws Exception {
		
		//Connection conn = super.getConnection();
		
		List<Object> params = new ArrayList<Object>();
		String sql = "select drive_distance from car_history_data where group_id=? and car_id=?";
		params.add(teamNumber);
		params.add(carNumber);
		
		double distance = 0;
		Map<String, Object> map = findSimpleResult(sql, params);
		for(Entry<String, Object> entry : map.entrySet()) {
			Object a = entry.getValue();
			String b = a.toString();
			distance = Double.valueOf(b).doubleValue();
		}
		
		//super.releaseConn();
		return distance;
	}
	
	public List<Map<String,Object>> listCarIdForFollowing(int teamNumber, double distance) throws Exception {
		//Connection conn = super.getConnection();
		
		List<Object> params = new ArrayList<Object>();
		String sql = "select car_id from car_history_data where group_id=? and drive_distance<?";
		
		String sql1 = "select id from t_car_group where group_num = ?";
		List<Object> params1 = new ArrayList<Object>();
		params1.add(teamNumber);
		long groupId =0;
		Map<String,Object> map = findSimpleResult(sql1, params1);
		for (Entry<String, Object> entry : map.entrySet()) {  
	           Object a=entry.getValue();  
	           String b = a.toString();
	           groupId = Integer.valueOf(b).intValue();	          
	        }     
		
		params.add(groupId);
		params.add(distance);
		
		List<Map<String, Object>> list = findMoreResult(sql, params);
		
		//super.releaseConn();
		return list;	
		
	}
	
	public String getMacByCarId(Map<String, Object> map) throws Exception{
		//super.getConnection();
		
		List<Object> params = new ArrayList<Object>();
		long id = (Long) map.get("car_id");
		String sql = "select mac_adress from car_basic_info where id=?";
		params.add(id);
		String macAddress ="";
		Map<String, Object> map1 = findSimpleResult(sql, params);
		
		for(Entry<String, Object> entry : map1.entrySet()) {
			Object a  = entry.getValue();
			macAddress = a.toString();
		}
		
		
		//super.releaseConn();
		return macAddress;
	}
	
	public List<Map<String,Object>> listCoorByCarId(long carId) throws Exception {
		//super.getConnection();
		
		List<Object> params = new ArrayList<Object>();
		String sql = "select coordinate_x,coordinate_y,direction from car_coordinate_info where car_id=?";
		params.add(carId);
		List<Map<String,Object>> list = findMoreResult(sql, params);
		
		//super.releaseConn();
		return list;
	}
	
	public List<Map<String,Object>> listCoorGroup() throws Exception {
		//super.getConnection();
		
		String sql ="select coordinate_x,coordinate_y,direction from car_coordinate_info";		
		List<Map<String,Object>> list = findMoreResult(sql, null);
		
		//super.releaseConn();
		return list;
		
	}
	
//	public List<Map<String, Object>> listCarIdByGroupId(long groupId) throws Exception {
//		super.getConnection();
//		
//		List<Object> params = new ArrayList<Object>();
//		String sql = "select car_id from";
//		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
//		return list;
//	}
	
	public long getGroupIdByCarId(long carId) throws Exception {
		//super.getConnection();
		
		String sql = "select group_id from car_basic_info where id=?";
		List<Object> params = new ArrayList<Object>();
		params.add(carId);
		
		long groupId = 0;
		Map<String, Object> map = findSimpleResult(sql,params);
		for(Entry<String, Object> entry : map.entrySet()) {
			Object a = entry.getValue();
			groupId = Long.valueOf(a.toString()).longValue();
		}
		//super.releaseConn();
		return groupId;
	}
	
	public List<String> listMacAdress() throws Exception {
		//super.getConnection();
		
		String sql = "select mac_adress from car_basic_info";
		
		List<String> list1 = new ArrayList();
		List<Map<String, Object>> list = findMoreResult(sql, null);
		for(int i=0;i<list.size();i++) {
			Map<String,Object> map = list.get(i);
			for(Entry<String, Object> entry : map.entrySet()) {
				Object a = entry.getValue();
				list1.add(a.toString());
			}
		}
		
		//super.releaseConn();
		return list1;
	}
	
	public Map<String, Object> getCarDistanceMax(long groupId, long carId) throws Exception{
		//super.getConnection();
		
		List<Object> params = new ArrayList<Object>();
		String sql = "select speed,drive_distance from car_history_data where id= ("
				+ "select max(id) from car_history_data where group_id=? and car_id=?)";
		params.add(groupId);
		params.add(carId);
		Map<String, Object> map = findSimpleResult(sql, params);
		
		//super.releaseConn();
		return map;
	}
	public long getCarIdByCarNum(long groupId, int carNum) throws Exception{
		//super.getConnection();
		
		String sql = "select id from car_basic_info where group_id=? and car_num =?";
		List<Object> params = new ArrayList<Object>();
		params.add(groupId);
		params.add(carNum);
		Map<String, Object> map = findSimpleResult(sql,params);
		
		String scarId = "";
		for(Entry<String, Object> entry : map.entrySet()){
			Object a = entry.getValue();
			scarId = a.toString();			
		}
		long carId = Long.valueOf(scarId).longValue();
		
		//super.releaseConn();
		return carId;
	}
	
	public int getCarNumByCarId(long carId) throws Exception{
		//super.getConnection();
		
		String sql = "select car_num from car_basic_info where id=?";
		List<Object> params = new ArrayList<Object>();
		params.add(carId);
		Map<String, Object> map = findSimpleResult(sql, params);
		int carNumber = 0;
		for(Entry<String, Object> entry : map.entrySet()){
			Object a = entry.getValue();
			carNumber = Integer.valueOf(a.toString()).intValue();			
		}
		
		//super.releaseConn();
		return carNumber;
	}
	
	public int getCarNumByMacAddress(String macAddress) throws Exception{
		//super.getConnection();
		
		String sql = "select car_num from car_basic_info where mac_adress=?";
		List<Object> params = new ArrayList<Object>(0);
		params.add(macAddress);
		Map<String, Object> map = findSimpleResult(sql, params);
		int carNumber = 0;
		for(Entry<String, Object> entry : map.entrySet()){
			Object a = entry.getValue();
			carNumber = Integer.valueOf(a.toString()).intValue();
		}
		
		//super.releaseConn();
		return carNumber;
	}
	
	public long getGroupIdByMacAddress(String macAddress) throws Exception{
	//	super.getConnection();
		
		String sql = "select group_id from car_basic_info where mac_adress=?";
		List<Object> params = new ArrayList<Object>();
		params.add(macAddress);
		Map<String, Object> map = findSimpleResult(sql, params);
		long groupId = 0;
		for(Entry<String, Object> entry : map.entrySet()){
			Object a = entry.getValue();
			groupId = Long.valueOf(a.toString());
		}
		
	//	super.releaseConn();
		return groupId;
	}
	
	public int getGroupNumByGroupId(long groupId) throws Exception{
		//super.getConnection();
		
		String sql = "select group_num from t_car_group where id=?";
		List<Object> params = new ArrayList<Object>();
		params.add(groupId);
		Map<String, Object> map = findSimpleResult(sql, params);
		int groupNum = 0;
		for(Entry<String,Object> entry : map.entrySet()){
			Object a = entry.getValue();
			groupNum = Integer.valueOf(a.toString());
		}
		
		//super.releaseConn();
		return groupNum;
	}
	
	public String getRoadIdByTeamAndCar(int teamNumber, int carNumber) throws Exception{
	//	super.getConnection();		
		
		//long groupId = getGroupIdbyGroupNum(teamNumber); 
		
		String sql1 = "select id from t_car_group where group_num = ?";
		List<Object> params1 = new ArrayList<Object>();
		params1.add(teamNumber);
		long groupId=0;
		Map<String,Object> map1 = findSimpleResult(sql1, params1);
		for (Entry<String, Object> entry : map1.entrySet()) {  
	           Object a=entry.getValue();  
	           String b = a.toString();
	           groupId = Integer.valueOf(b).intValue();	          
	        } 
		
		//long carId = getCarIdByCarNum(groupId, carNumber);
		
		String sql2 = "select id from car_basic_info where group_id=? and car_num =?";
		List<Object> params2 = new ArrayList<Object>();
		params2.add(groupId);
		params2.add(carNumber);
		Map<String, Object> map2 = findSimpleResult(sql2,params2);
		
		String scarId = "";
		for(Entry<String, Object> entry : map2.entrySet()){
			Object a = entry.getValue();
			scarId = a.toString();			
		}
		long carId = Long.valueOf(scarId).longValue();
		
		
		String roadId = "";
		
		String sql = "select road_id from car_basic_info where id=?";
		List<Object> params = new ArrayList<Object>();
		params.add(carId);
		
		Map<String, Object> map = findSimpleResult(sql, params);
		for(Entry<String, Object> entry : map.entrySet()){
			Object a = entry.getValue();
			roadId = a.toString();
		}
		//super.releaseConn();
		return roadId;
		
	}
	
	public boolean setStopData(String speed, String distance, String angle, String direction, String type) throws Exception{
		//super.getConnection();
		
		String sql = "insert into t_stop_data (speed, angle, distance, direction, type) values(?,?,?,?,?)";
		List<Object> params = new ArrayList<Object>(); 
		params.add(speed);
		params.add(angle);
		params.add(distance);
		params.add(direction);
		params.add(type);
		
		return updateByPreparedStatement(sql, params);
		
	}
	
	public Map<String, Object> getStopData(String type) throws Exception{
		//super.getConnection();
		
		String sql = "select * from t_stop_data where type=?";
		String sql2 = "delete from t_stop_data where type = ?";
		
		List<Object> params = new ArrayList<Object>();
		params.add(type);
		Map<String, Object> map = findSimpleResult(sql, params);
		
		if(!map.isEmpty()){
			updateByPreparedStatement(sql2, params);
		}
		
		return map;
		
	}
	
	public Map<String,Object> getGroupPlan(String planName) throws Exception{
		//super.getConnection();
		
		System.out.println("sql_planName======"+planName);
		String sql = "select * from t_group_plan where plan_name=?";
		List<Object> params = new ArrayList<Object>();
		params.add(planName);
		Map<String, Object> map = findSimpleResult(sql, params);
		
		return map;
	}
}
