package com.map;

import java.util.List;
import java.util.Map;

import com.entity.CarBasicInfo;
import com.entity.CarBendInfo;
import com.entity.CarCoordinateInfo;
import com.entity.CarState;
import com.entity.CarWarnInfo;
import com.entity.ShuntId;

public interface MapDao {

	public List<Long> getCarId() throws Exception;
	public List<CarState> getLeastCarInfoById(Long carId) throws Exception;
	public double getMinDistanceByIdAndHall(long hallCount, long carId) throws Exception;
	public String getChannelByCarId(long carId) throws Exception;
	public List<CarCoordinateInfo> getCarCoordinateInfo() throws Exception;
	public List<CarBendInfo> getSpecialCarBend(int roadId, String ch,
			long hallCount) throws Exception;
	public List<CarBendInfo> getSpecialCarBend1(int roadId, int ch,
			long hallCount, int iDistance)throws Exception;
	public List<CarBasicInfo> getCarBasicInfo() throws Exception;
	public  List<CarBendInfo> getAllBendInfo() throws Exception;
	public List<Map<String, Object>> getCongestionHall() throws Exception;
	//public List<CarWarnInfo> getCongestionCarInfo() throws Exception;
	public List<Map<String,Object>> getCongestionCarMaxHall() throws Exception;
	public int getDiection(int road_id, int hall_count, String channel) throws Exception;
	public Point getPoint(int road_id, int hall_count, String channel) throws Exception;
	public long getRoadIdByCarId(long grouyp_id) throws Exception;
	public List<CarState> getCarState(long id) throws Exception;
	public List<ShuntId> getShuntCarId() throws Exception;
	
	public List<ShuntId> getAllShuntCarId() throws Exception;
}
