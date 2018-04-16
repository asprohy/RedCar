package com.car.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.entity.CarBasicInfo;
import com.entity.CarCoordinateInfo;
import com.entity.CarState;
import com.entity.CarWarnInfo;
import com.entity.OrderInfo;

public interface Dao {
	public List<CarState> getCarInfoById(long carId) throws Exception;
	public List<CarState> getCarInfo() throws Exception;
	public List<CarState> getFirstInfo() throws Exception;
	//public List<CarWarnInfo> getCarWarnInfo() throws Exception;
	public List<CarState> getCarAllInfoById(long id) throws Exception;
	//public void updateCarChannel(long id,String channel) throws SQLException;
	//public void updateHall(long carId,long id,long hall) throws SQLException;
	//public List<CarWarnInfo> getAllCarWarnInfo() throws Exception;
	//public void restoreRoad(long carId) throws Exception;
	public List<CarState> getNewestCarInfo() throws Exception;
	public long getMaxHallById(long carId) throws SQLException;
	public void clearOldData(long carId,long time) throws SQLException;
	public List<String> getMacAddress(long groupId) throws Exception;
	public List<CarWarnInfo> getAllCongestionCar() throws Exception;
	public void mergeUpdateBasicInfo() throws Exception;
	public void shuntUpdateBasicInfo(long carId) throws Exception;
	public List<CarState> getCarInfoByGroupId(long groupId) throws Exception;
	
	public List<CarState> getCarInfoByGroupId1(long groupId , long roadId) throws Exception;
	public List<CarState> getCarInfoByGroupId2(long groupId , long roadId) throws Exception;
	
	public List<CarState> getDistanceByCarId(long carId) throws Exception;
	public long getHallByDistance(long road_id, String string, double distance) throws Exception;
	public long getCarIdByCarNum(long groupId, int carNum) throws Exception;
	public long getGroupIdById(long carId) throws Exception;
	public long getGroupIdbyGroupNum(int groupNum) throws Exception;
	public void updateOrderInfo(String steamNum, String sSpeed,String sCarNum,
			String sDistance, String sAngle, String sDirect)throws Exception ;
	public List<OrderInfo> getOrderInfoByGroupId(long groupId) throws Exception;
	public List<CarBasicInfo> getBasicInfoById(int carId) throws Exception;
	public List<CarBasicInfo> getBasicInfoByGroupId(long groupId) throws Exception;
	public List<Integer> getAllCarId() throws Exception;
	public Map<String, Object> getMacIdByCarNum(String teamNum, String carNum) throws Exception;
	public void updateCongestion1(long carId) throws Exception;
	public List<CarState> getOldCarState(long carId, long time) throws Exception;
	public void updateCongestion2(long carId) throws Exception;
	public List<OrderInfo> getOrderInfoByShunt() throws Exception;
	public List<CarBasicInfo> getBasicInfoByShunt()throws Exception;
	public ArrayList<Long> getAllGroupId() throws Exception;
	public List<CarCoordinateInfo> getCarCoordinateInfos(long road_id, String channel, int beginHall, int endHall) throws Exception;
	public void saveStopOrder(String steamNum, String sCarNum,String sType) throws Exception;
	public void updateStopOrder(String sTeam, String sCar, String sType) throws Exception;
	public List<CarWarnInfo> getStopInfo(int carId) throws Exception;
	public int getMaxHall(long road_id, String channel) throws Exception;
	public int getMinHall(long road_id, String channel) throws Exception;
	public List<CarState> getSimulateGroup(int carId) throws Exception;
	
	public List<CarState> getCarInfoByDist(long carId, double safeDist) throws Exception;//获取不是该组车的  后距离内的车id
	public double getCarDistById(long carId) throws Exception;
	public String getCarChannelById(long carId) throws Exception;
	public List<CarState> getLastCongestionCar() throws Exception;
	public long getCarNumbyGroupId(long groupId , long roadId) throws Exception;
	public List<CarState> getCarInfoByDist1(long carId, double DeSafeDist , double RoadLenghth) throws Exception;
	
	public long getHallByCarId(long carId) throws Exception;
	
	public List<Integer> getShuntCarId() throws Exception;
	public CarCoordinateInfo getCarCoordinateInfo(long road_id, String channel, long hall_count)throws Exception;
	public double getHallMinDistance(long carId,long Hall) throws Exception;
	
	public void initSequenceFalse() throws Exception;
	public List<Long> getSequnceFalseCar() throws Exception;
	public int getSite(long carId) throws Exception;
	public void updateSiteOfCar(long carId, long sequenceNum) throws Exception;
	
	public ArrayList<Long> getAllHeadCarId(long roadId) throws Exception;
	public ArrayList<Long> getAllTailCarId(long roadId) throws Exception;
	public ArrayList<Long> getAllRoadHeadCarId() throws Exception;
	public ArrayList<Long> getAllRoadTailCarId() throws Exception;
	public Long getHeadCarId(long groupId) throws Exception;
	public Long getTailCarId(long groupId) throws Exception;
}
