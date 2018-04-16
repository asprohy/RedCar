package com.map;

import gnu.io.RXTXVersion;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.entity.CarBendInfo;
import com.entity.CarState;
import com.entity.CoordinateXY;

public class CarHelper {
	public static void main(String[] args) throws Exception {
		Coordinate ci = getCarSiteInMap((long) 2);		
		System.out.println(" X : "+ci.getX()+" Y : "+ci.getY()+" DIRECTION : "+ci.getDirection());
		
		
	}
	// 获取所有小车坐标信息
	public static List<Coordinate> getCarsSiteInMap() throws Exception {
		
		long begin = System.currentTimeMillis();
		//System.out.println("begin: " + begin);
		List<Coordinate> coordinateList = new LinkedList<Coordinate>();

		MapDaoImpl dao = new MapDaoImpl();
		
		long tag1 = System.currentTimeMillis();
		//System.out.println("tag1: " + tag1);
		List<Long> list = dao.getCarId();
//		System.out.println("i:" + list.size());
		long tag2 = System.currentTimeMillis();
		//System.out.println("tag2: " + tag1);
		for (int i = 0; i < list.size(); i++) {
			Coordinate coordinate = getCarSiteInMap(list.get(i));
			coordinateList.add(coordinate);
		}
		long end = System.currentTimeMillis();
		//System.out.println("end: " + tag1);
		return coordinateList;
	}

	// 根据小车id获取小车坐标信息
	public static Coordinate getCarSiteInMap(Long id) throws Exception{

		// 找到最近一条数据
		MapDaoImpl dao = new MapDaoImpl();
		List<CarState> list = new ArrayList<CarState>();
		//System.out.println(id);
//		long CarHelpTag1 = System.currentTimeMillis();
//		System.out.println(id + "CarHelpTag1: " + CarHelpTag1);
		list = dao.getLeastCarInfoById(id);
		long useTime = 0;
//		long CarHelpTag2 = System.currentTimeMillis();
//		System.out.println(id + "CarHelpTag2: " + CarHelpTag2);
		//System.out.println(",,,");
		//System.out.println("listSize " + list.size());		
		
		if(list.isEmpty()){
			Coordinate carInfo = new Coordinate(-50, -50, 0, 90);
			//System.out.println("id: " + id + "Site" + carInfo.getX() + " " + carInfo.getY()+ ' ' + carInfo.getSpeed());
			//carInfo.setSpeed(speed);
//			System.out.println("not find:" + (System.currentTimeMillis() - CarHelpTag2));
			return carInfo;
		}
		ResultSet ret;
		long carId = id;
		long groupId = list.get(0).getGroup_id();
		long hallCount = list.get(0).getHistory_hall_count();
		double driveDistance = list.get(0).getDrive_distance();
		double speed = list.get(0).getSpeed();
		
		double nearDistance = 0;
		double detaDistance = 0;
		int detaX = 0;
		int detaY = 0;
		String channel = "";
		Point thisPoint = new Point();
		Point nextPoint = new Point();

		int roadId = 0;
		int direction = 0;

		// 真实地图与图片地图比例
		double rate = 820.0 / 700.0;//1105.0 / 700.0

		roadId = (int) dao.getRoadIdByCarId(id);

		// 取当前小车，当前霍尔值，distance最小的数据出来
		long CarHelpTag3 = System.currentTimeMillis();
//		useTime = useTime + CarHelpTag3 - CarHelpTag2;
//		System.out.println(id + "CarHelpTag3: " + CarHelpTag3);
		nearDistance = dao.getMinDistanceByIdAndHall(hallCount,carId);
		long CarHelpTag4 = System.currentTimeMillis();
//		System.out.println(id + "CarHelpTag4: " + CarHelpTag4);
		// 从状态表中获取内外道信息
		channel = dao.getChannelByCarId(carId);
//		Hall hall = new Hall();
		//System.out.println(hall.hallMap.size());
//		direction = hall.getDirection((int)roadId, (int)hallCount, channel);
//		thisPoint = hall.getPoint((int)roadId, (int)hallCount, channel);
		
		direction = dao.getDiection((int)roadId, (int)hallCount, channel);
		thisPoint = dao.getPoint((int)roadId, (int)hallCount, channel);
		//System.out.println("roadId:" + roadId + "hallCount: " + hallCount + "channel: " + channel);
		//System.out.println("x: " + thisPoint.x + "y: " + thisPoint.y);
		//System.out.println("roadId: " +roadId + "hallCount: " +hallCount + "channel: " +channel);
		//System.out.println("info:" + direction + " " + thisPoint.x + " " + thisPoint.y);
		//System.out.println("driveDistance: " + driveDistance + " nearDistance: " + nearDistance);
		// nextPoint = Hall.getPoint(roadId, hallCount + 1, channel);
//		long CarHelpTag5 = System.currentTimeMillis();
//		System.out.println(id + "CarHelpTag5: " + CarHelpTag5);
		detaDistance = (driveDistance - nearDistance) * 100;
		//System.out.print("test");
		
		boolean isSpecial = isSpecial(roadId, channel, hallCount, detaDistance);
		boolean isChangeRoad = isChangeRoad();
//		long CarHelpTag6 = System.currentTimeMillis();
//		System.out.println(id + "CarHelpTag6: " + CarHelpTag6);
		// 不是特殊点就直接运算
		if (!isSpecial) {

		    	//if(detaDistance > )
			detaX = (int) (Math.cos(Math.PI / 180 * direction) * detaDistance);
			detaY = (int) (-Math.sin(Math.PI / 180 * direction) * detaDistance);

			//System.out.println("deta:" + detaX + " " + detaY);
			//System.out.println("detaDistance" + detaDistance);
			thisPoint.x = (int) ((thisPoint.x + detaX) * rate);
			thisPoint.y = (int) ((thisPoint.y + detaY) * rate);
			//System.out.println("X: " + thisPoint.x + " Y:" + thisPoint.y);
			int changeDirection = 0;
			int changeX = 0;
			int changeY = 0;
			
			// 是否在变道
			if (!isChangeRoad) {
				
				
				
				Coordinate carInfo = new Coordinate(thisPoint.x, thisPoint.y,
						direction,speed);
				//carInfo.setSpeed(speed);
				

				return carInfo;
			} else {
				// 变道的时候加一个增量
				
				if((channel == "outside" && roadId == 1)
						|| (channel == "inside" && roadId == 2)){
					changeDirection = (direction + 270) % 360;
					changeX = (int)(Math.cos(Math.PI / 180 * changeDirection) * 5);
					changeY = (int)(-Math.sin(Math.PI / 180 * changeDirection) * 5);
					thisPoint.x = (int)(thisPoint.x +(changeX * rate));
					thisPoint.y = (int)(thisPoint.y +(changeY * rate));
				}else{
					changeDirection = (direction + 90) % 360;
					changeX = (int)(Math.cos(Math.PI / 180 * changeDirection) * 5);
					changeY = (int)(-Math.sin(Math.PI / 180 * changeDirection) * 5);
					thisPoint.x = (int)(thisPoint.x +(changeX * rate));
					thisPoint.y = (int)(thisPoint.y +(changeY * rate));
				}
				Coordinate carInfo = new Coordinate(thisPoint.x, thisPoint.y,
						direction,speed);
				
				//carInfo.setSpeed(speed);
				return carInfo;
			}
			
		} else {
			
			int jam = TrafficJam.getSpecial(roadId, channel, (int)hallCount);
			Point roundDot = getRoundDot(jam);
			Coordinate carInfo = getSpecialCarInfo(roadId, channel, hallCount,
				detaDistance);
			boolean isPositive = true;
			if(roadId == 1){
			     isPositive = false;
			}
			Coordinate coordinate = new Coordinate((int)(thisPoint.x),(int)(thisPoint.y),direction,speed);
			coordinate.setCarId(carId);
			if(roadId != 3){
			    carInfo = getBendCarPosition(roundDot ,detaDistance , isPositive , coordinate);
			}
			
//			detaDistance = (int) ((double)detaDistance * 1.7);
			
			//carInfo.setSpeed(speed);
			return carInfo;
		}
	}

	public static boolean isChangeRoad() {

		return false;
	}

	public static boolean isSpecial(int roadId, String channel, long hallCount) throws Exception{
		MapDaoImpl dao = new MapDaoImpl();
		List<CarBendInfo> list = new ArrayList<CarBendInfo>();
		list = dao.getSpecialCarBend(roadId,channel,hallCount);
		
		if(list.size()!=0){
			return true;
		}
		
		
		return false;
	}
	
	// 判断这个霍尔点是不是转弯点
	public static boolean isSpecial(int roadId, String channel, long hallCount,
			double distance) throws Exception {
		int ch;
		// 内道1， 外道2
//		if (channel.equals("inside")) {
//			ch = 1;
//		} else {
//			ch = 2;
//		}

		MapDaoImpl dao = new MapDaoImpl();
		List<CarBendInfo> list = new ArrayList<CarBendInfo>();
		list = dao.getSpecialCarBend(roadId,channel,hallCount);
		
		if(list.size()!=0){
			return true;
		}
		
		return false;
	}

	public static Coordinate getSpecialCarInfo(int roadId, String channel,
			long hallCount, double distance) throws Exception {
		Coordinate coordinate = null;
		int ch;
		// 内道1， 外道2
		if (channel.equals("inside")) {
			ch = 1;
		} else {
			ch = 2;
		}
		int iDistance = (int) (distance / 10) * 10;
		//System.out.println("iDistance:" + iDistance);
		MapDaoImpl dao = new MapDaoImpl();
		List<CarBendInfo> list = new ArrayList<CarBendInfo>();
		list = dao.getSpecialCarBend1(roadId,ch,hallCount,iDistance);
		while(list.size() == 0){
			iDistance -= 10;
			list = dao.getSpecialCarBend1(roadId,ch,hallCount,iDistance);
		}
		if(list.size()!=0){
			int x = (int) list.get(0).getX();
			int y = (int) list.get(0).getY();
			int direction = (int) list.get(0).getDirection();
			double speed = 0;
			coordinate = new Coordinate(x,y,direction,speed);
			//System.out.println("Coordinate: " + coordinate.toString());
		}
		
//		String sql = "select x, y, direction from car_bend_info where road_id = "
//				+ roadId
//				+ " and channel = "
//				+ ch
//				+ " and hall_count = "
//				+ hallCount + "and distance = " + iDistance;
//		DBHelper db = new DBHelper(sql);
//		ResultSet ret = null;
//		try {
//			ret = db.pst.executeQuery();
//			int x = ret.getInt(1);
//			int y = ret.getInt(1);
//			int direction = ret.getInt(1);
//			coordinate = new Coordinate(x,y,direction);
//			
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		db.close();

		return coordinate;
	}

	// 用于存储转弯时候小车坐标信息
	// 道路id 内外道id 霍尔值num distance X Y direction
	static int special[][] = { {} };

	// 将车队ID转化为道路ID
	public static int groupIdToroadId(long groupId) {
		if (groupId == 1 || groupId == 2)
			return 1;
		return 2;
	}
	
	public static Point getRoundDot(int jam){
	    double rate = 820/720.0;
	    Point roundDot = new Point();
	    if(jam == 211){
		roundDot.x = (int) (594);
		roundDot.y = (int) (113);
	    }else if(jam == 212){
		roundDot.x = (int) (153);
		roundDot.y = (int) (276);
	    }else if(jam == 213){
		roundDot.x = (int) (330);
		roundDot.y = (int) (450);
	    }else if(jam == 214){
		roundDot.x = (int) (594);
		roundDot.y = (int) (264);
	    }
	    return roundDot;
	}
	
	public static Coordinate getBendCarPosition(Point RoundDot , double Dist , boolean isPositive , Coordinate car)//2 3 true 1 false
	{
		Coordinate carBendInfo = new Coordinate();
		Point StartDot = new Point(car.getX(), car.getY());
		double rate = 820 / 700.0;
		double StartTheta = 0.0;
		double deltax = ( double )(StartDot.x - RoundDot.x);
		double deltay = ( double )(StartDot.y - RoundDot.y);//(0,0)在左上角
		double r = Math.hypot( deltax , deltay );
		if(StartDot.x != RoundDot.x)
		{
			StartTheta = Math.atan2( -deltay , deltax );
		}
		else
		{
			if(StartDot.y < RoundDot.y )
			{
				StartTheta = Math.PI/2.0;
			}
			else
			{
				StartTheta = 3.0*Math.PI/2.0;
			}
		}
		
		double deltaTheta = Dist/r ;
		double aimTheta = 0.0;
		if(!isPositive)//2号道为标准（逆时针）   因y颠倒 顺时针计算theta     deltatheta minus
		{
			aimTheta = StartTheta - deltaTheta;
		}
		else
		{
			aimTheta = StartTheta + deltaTheta;
		}
		
		carBendInfo.setCarId(car.getCarId());
		carBendInfo.setSpeed(car.getSpeed());
		carBendInfo.setX((int)(  ((double)(RoundDot.x)+r*Math.cos(aimTheta)) * rate + 0.5) );
		carBendInfo.setY((int)(  ((double)(RoundDot.y)-r*Math.sin(aimTheta)) * rate + 0.5) );
		
		if(!isPositive)
		{
			int D = (int) ( (180.0*aimTheta/Math.PI) -90.0 );
			
			carBendInfo.setDirection( (D>=0)?(int)D:(int)D+360  );
		}
		else
		{
			int D = (int) ( (180.0*aimTheta/Math.PI) +90.0 );
			carBendInfo.setDirection( (D>=0)?(int)D:(int)D+360  );
		}
		
		return carBendInfo;	
	}

}
