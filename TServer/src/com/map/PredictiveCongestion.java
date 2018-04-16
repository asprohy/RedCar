package com.map;

import java.util.ArrayList;
import java.util.List;

import com.car.dao.DaoImpl;
import com.entity.CarCoordinateInfo;
import com.entity.CarState;
import com.entity.ShuntId;

public class PredictiveCongestion {

	/**
	 * @param args
	 * @throws Exception 
	 */
	private static final double TRAFFICDISTANCE = 1;
	
	private static final double[] THEWHOLELENGTH = {15.7 , 1000 , 1000 , 1000 , 1000 , 1000 };
	
	private static final double[] RADIUS = {0.4917 , 0.4915 , 0.4917 , 0.4917 };
	private static final int[] BENDHALL2INSIDE = {2  , 33 , 49 , 66 };
	
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		PredictiveCongestion gt = new PredictiveCongestion();
		List<CongestionCoordinate> list = gt.PreCongestion();
		if(list.size() != 0)
			System.out.println(list.get(0).getX1());
	}
	
	public ArrayList<CongestionCoordinate> PreCongestion() throws Exception{
		ArrayList<CongestionCoordinate> list = new ArrayList<CongestionCoordinate>();
		
		DaoImpl dao = new DaoImpl();
		ArrayList<Long> groupIdList = new ArrayList<Long>();
		
		MapDaoImpl mDao = new MapDaoImpl();
		
		List<ShuntId> shuntIdList = mDao.getAllShuntCarId();
		
		List<CarState> CarSt = new ArrayList<CarState>();
		CarSt = dao.getCarInfoByGroupId2(1,3);
		
		if(CarSt.size()>0 && shuntIdList.size() > 0)
		{
			return list;
		}
		
		groupIdList = dao.getAllGroupId();
		
		/*
		for(int i = 0;i < groupIdList.size();i++){
			List<CongestionCoordinate> list1 = new ArrayList<CongestionCoordinate>();
			//System.out.println(groupIdList.get(i));
			list1 = Judge1(groupIdList.get(i));
			//
			//System.out.println(oneList.isEmpty());
			if(!list1.isEmpty() || list1.size() != 0)
				list.addAll(list1);
		}
		*///原先的预测
		
		//33333
				System.out.println("---------------------预测拥堵---------------  in  ");
				
				//ArrayList<CongestionCoordinate> list4 = new ArrayList<CongestionCoordinate>();
				
				//DaoImpl dao = new DaoImpl();
				/*ArrayList<Long> groupIdList = new ArrayList<Long>();
				groupIdList = dao.getAllGroupId();
			
				
				for(int i = 0;i < groupIdList.size();i++){
					  //System.out.println(groupIdList.get(i));
					list1 = Judge(groupIdList.get(i));
					//
					//System.out.println(oneList.isEmpty());
					if(!list1.isEmpty() || list1.size() != 0)
						list.addAll(list1);
				}
				return list;*/
				
				List<CarState> CongestionLastCarIdList = new ArrayList<CarState>();//装尾车（以roadid,channel,groupid区分）信息
				
				for(int i = 0;i < groupIdList.size();i++){
					List<CarState> list3 = new ArrayList<CarState>();
					//System.out.println(groupIdList.get(i));
					list3 = Judge2(groupIdList.get(i));
					//
					//System.out.println(oneList.isEmpty());
					if(!list3.isEmpty() || list3.size() != 0)
					{	CongestionLastCarIdList.addAll(list3);
						System.out.println("尾车信息:"+list3.get(0).getCar_id());
						}
				}
	
				//System.out.println("尾车信息:"+CongestionLastCarIdList.get(0).getCar_id());
				
				for(int i=0;i < CongestionLastCarIdList.size();i++)
				{
					
					//List<CarState> listSt = new ArrayList<CarState>();
					//list1.addAll(dao.getCarInfoByDist(CongestionLastCarIdList.get(i).getCar_id(), safeDist));//以安全距离去找车
					//List<CongestionCoordinate> coordinateList1 = TrafficJam.getJamInfo((int)list1.get(0).getRoad_id(), list1.get(0).getChannel(), beginHall, endHall);
					//
					//System.out.println(oneList.isEmpty());
					List<CongestionCoordinate> list2 = new ArrayList<CongestionCoordinate>();
					System.out.print((i+1)+":");
					list2 = Judge3(CongestionLastCarIdList.get(i));
					
					//System.out.println("预测拥塞:"+list2.get(0).getX1()+" "+list2.get(0).getY1());
					
					if(!list2.isEmpty() || list2.size() != 0)
					{
						list.addAll(list2);System.out.println("预测拥塞:"+list2.get(0).getX1()+" "+list2.get(0).getY1());
					}
				}

		return list;
	}
	
	public List<CongestionCoordinate> Judge1(long groupId) throws Exception{
		//预测车队是否拥堵
		DaoImpl dao = new DaoImpl();
		List<CarState> list = dao.getCarInfoByGroupId(groupId);//得到车队每个小车的最新数据
		//把内外道分开
		List<CarState> list1 = new ArrayList<CarState>();//装内道车
		List<CarState> list2 = new ArrayList<CarState>();//装外道车
		List<CongestionCoordinate> coordinateList = new ArrayList<CongestionCoordinate>();//坐标接收
		
		
		if(list.size() <= 1){
			return coordinateList;
		}
		
		//System.out.println(list.get(0).getCar_id());
		//System.out.println(list.get(1).getCar_id());
		
		for(int i = 0;i < list.size();i++){
			if(list.get(i).getChannel().equals("inside")){
				list1.add(list.get(i));
				//System.out.println("channel:"+list1.get(0).getChannel()+"roadId:"+list1.get(0).getRoad_id());
			}else if(list.get(i).getChannel().equals("outside")){
				list2.add(list.get(i));
			}
		}
		
		int in = 0;
		int out = 0;   ////2nd
		
		
		/*
		for(int x = 0;x < list1.size();x++){
			if(x == list.size() - 1){//最后一辆车不判断
				break;
			}
			if(list.get(x).getSpeed() < 1){//如果小车的速度小于某个值，并且车间距小于某个值，认为小车将要堵车
				if((list.get(x).getHistory_hall_count() - list.get(x+1).getHistory_hall_count()) > 20){	
					//如果小车的车间距大于5m，认为车队横跨了终点，等将来测出整个车道距离再说	
					
					
				}else if(Math.abs((list.get(x).getDrive_distance() - list.get(x+1).getDrive_distance())) < 20.5 
						&& Math.abs((list.get(x).getDrive_distance() - list.get(x+1).getDrive_distance())) >= 0.3
						&& list.get(x).getCar_id() != list.get(x+1).getCar_id()){
					in = in+1;
				}
			}
		}
		
		for(int y = 0;y < list2.size();y++){
			if(y == list.size() - 1){//最后一辆车不判断
				break;
			}
			if(list.get(y).getSpeed() < 1){//如果小车的速度小于某个值，并且车间距小于某个值，认为小车将要堵车
				if((list.get(y).getHistory_hall_count() - list.get(y+1).getHistory_hall_count()) > 20){	
					//如果小车的车间距大于5m，认为车队横跨了终点，等将来测出整个车道距离再说	
					
					
				}else if(Math.abs((list.get(y).getDrive_distance() - list.get(y+1).getDrive_distance())) < 0.5 
						&& Math.abs((list.get(y).getDrive_distance() - list.get(y+1).getDrive_distance())) >= 0.3
						&& list.get(y).getCar_id() != list.get(y+1).getCar_id()){
					//大于某个值，小于某个值设为可能拥堵
					out = out+1;
				}
			}
		}
		
		//System.out.println("in"+in+"out"+out);
		
		int beginHall=0,endHall = 0;
		//查找最大hall，最小hall
		//这里调用坐标对转化
		System.out.println("---------------------预测拥堵?---------------"+in+"...."+list1.size());
		if(in >= list1.size() - 1 && list1.size() != 0){
			System.out.println("---------------------预测拥堵---------------"+in);
			if(Math.abs(list1.get(0).getHistory_hall_count() - list1.get(list.size()-1).getHistory_hall_count()) > 20){
				//霍尔值差过大，说明跨了终点
				int maxHall = MaxHall(list1);
				int minHall = MinHall(list1);
				beginHall = minHall;
				endHall = maxHall;
				
			}else{
				int maxHall = MaxHall(list1);
				int minHall = MinHall(list1);
				beginHall = maxHall;
				endHall = minHall;
			}
			//System.out.println("inbegin:"+beginHall+" end:"+endHall);
			//System.out.println("roadId:"+list1.get(0).getRoad_id());
			List<CongestionCoordinate> coordinateList1 = TrafficJam.getJamInfo((int)list1.get(0).getRoad_id(), "inside", beginHall, endHall);
			//System.out.println("coordinateList1:"+coordinateList1.size());
			coordinateList.addAll(coordinateList1);
			//System.out.println("coordinateList:"+coordinateList.size());
		}
		
		if(out >= list2.size() - 1 && list2.size() != 0){
			if(Math.abs(list2.get(0).getHistory_hall_count() - list2.get(list.size()-1).getHistory_hall_count()) > 20){
				//霍尔值差过大，说明跨了终点
				int maxHall = MaxHall(list2);
				int minHall = MinHall(list2);
				beginHall = minHall;
				endHall = maxHall;
				
			}else{
				int maxHall = MaxHall(list2);
				int minHall = MinHall(list2);
				beginHall = maxHall;
				endHall = minHall;
			}
			//System.out.println("begin"+beginHall+" end"+endHall);
			List<CongestionCoordinate> coordinateList2 = TrafficJam.getJamInfo((int)list2.get(0).getRoad_id(), "outside", beginHall, endHall);
			coordinateList.addAll(coordinateList2);
		}*/
		
		//改inside
				for(int x=0;x< list1.size();x++)
				{
					for(int y=0;y< list1.size();y++)
					{
						if(x==y)
						{
							continue;
						}
						
						
						if(list1.get(x).getSpeed() < 1){//如果小车的速度小于某个值，并且车间距小于某个值，认为小车将要堵车
							if((list1.get(x).getHistory_hall_count() - list1.get(y).getHistory_hall_count()) > 20){	
								//如果小车的车间距大于5m，认为车队横跨了终点，等将来测出整个车道距离再说	
								
								
							}else if(Math.abs((list1.get(x).getDrive_distance() - list1.get(y).getDrive_distance())) < 20.5 
									&& 
									Math.abs((list1.get(x).getDrive_distance() - list1.get(y).getDrive_distance())) >= 0.8
									&& list1.get(x).getCar_id() != list1.get(y).getCar_id()){
								//1m??
								System.out.println("1carId："+list1.get(x).getCar_id());
								System.out.println("2carId："+list1.get(y).getCar_id());
								in = in+1;
								System.out.println("distance1:"+list1.get(x).getDrive_distance());
								System.out.println("distance1:"+list1.get(y).getDrive_distance());
								System.out.println("in："+in);
								break;
							}
						}
					}
					
				}
				
				for(int x=0;x< list2.size();x++)
				{
					for(int y=0;y< list2.size();y++)
					{
						if(x==y)
						{
							continue;
						}
						
						
						if(list2.get(x).getSpeed() < 1){//如果小车的速度小于某个值，并且车间距小于某个值，认为小车将要堵车
							if((list2.get(x).getHistory_hall_count() - list2.get(y).getHistory_hall_count()) > 20){	
								//如果小车的车间距大于5m，认为车队横跨了终点，等将来测出整个车道距离再说	
								
								
							}else if(Math.abs((list2.get(x).getDrive_distance() - list2.get(y).getDrive_distance())) < 20.5 
									&& Math.abs((list2.get(x).getDrive_distance() - list2.get(y).getDrive_distance())) >= 0.8
									&& list2.get(x).getCar_id() != list2.get(y).getCar_id()){
								//1m??
								out = out+1;
								break;
							}
						}
					}
					
				}
				
				
				int beginHall=0,endHall = 0;
				
				
				System.out.println("---------------------预测拥堵?---------------"+in+"    "+list1.size());
				//这里调用坐标对转化
				if(list1.size() != 0 && in >= list1.size()  ){
					System.out.println("---------------------预测拥堵---------------"+in);
					if(Math.abs(list1.get(0).getHistory_hall_count() - list1.get(list.size()-1).getHistory_hall_count()) > 20){
						//霍尔值差过大，说明跨了终
						int maxHall = MaxHall(list1);
						int minHall = MinHall(list1);
						beginHall = minHall;
						endHall = maxHall;

					}else{
						int maxHall = MaxHall(list1);
						int minHall = MinHall(list1);
						beginHall = maxHall;
						endHall = minHall;
					}
					//System.out.println("inbegin:"+beginHall+" end:"+endHall);
					//System.out.println("roadId:"+list1.get(0).getRoad_id());
					List<CongestionCoordinate> coordinateList1 = TrafficJam.getJamInfo((int)list1.get(0).getRoad_id(), "inside", endHall, beginHall+1);
					//System.out.println("coordinateList1:"+coordinateList1.size());
					
					coordinateList.addAll(coordinateList1);
					//System.out.println("coordinateList:"+coordinateList.size());
				}
				
				if(list2.size() != 0 && out >= list2.size()   ){
					if(Math.abs(list2.get(0).getHistory_hall_count() - list2.get(list.size()-1).getHistory_hall_count()) > 20){
						//霍尔值差过大，说明跨了终点
						int maxHall = MaxHall(list2);
						int minHall = MinHall(list2);
						beginHall = minHall;
						endHall = maxHall;
						
					}else{
						int maxHall = MaxHall(list2);
						int minHall = MinHall(list2);
						beginHall = maxHall;
						endHall = minHall;
					}
					
					//System.out.println("begin"+beginHall+" end"+endHall);
					List<CongestionCoordinate> coordinateList2 = TrafficJam.getJamInfo((int)list2.get(0).getRoad_id(), "outside", beginHall, endHall);
					
					coordinateList.addAll(coordinateList2);
				}
				
		
		
		
		
		//System.out.println(coordinateList.get(0).getX1());
		return coordinateList;		
	}
	
	public static CongestionCoordinate getPreCongestionCoordinate(CarState carState){
	    	DaoImpl dao = new DaoImpl();
		long carId = carState.getCar_id();
		int roadId = (int) carState.getRoad_id();
		String channel = carState.getChannel();
		int hall = (int) carState.getHistory_hall_count();
		if(TrafficJam.isEndHall(roadId, channel, hall)){
		    hall = 1;
		}else {
		    hall = hall + 1;
		}
		System.out.println("getPreCongestion......");
		CongestionCoordinate  ccCongestionCoordinate = new CongestionCoordinate();
		//地图比例
		double rate = 820.0 / 700.0; //1105.0 / 700.0;
		CarCoordinateInfo carCoordinate = null;
		try {
		    carCoordinate = dao.getCarCoordinateInfo(roadId, channel, hall);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
		    e1.printStackTrace();
		}
		    
		    int x1 = (int) (carCoordinate.getCoordinate_x() * rate);
		    int y1 = (int) (carCoordinate.getCoordinate_y() * rate);
		    Coordinate c2 = null;
		    try {
			c2 = CarHelper.getCarSiteInMap(carId);
		    } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		    }
		    
		    int x2 = (int) (c2.getX());
		    int y2 = (int) (c2.getY());
		    int special = 0;
		    ccCongestionCoordinate.setSpecial(special);
	    	    ccCongestionCoordinate.setX1(x1);
	    	    ccCongestionCoordinate.setY1(y1);
	    	    ccCongestionCoordinate.setX2(x2);
	    	    ccCongestionCoordinate.setY2(y2);
		    //coordinateList.add(ccCongestionCoordinate);
		
		return ccCongestionCoordinate;
	}
//2rd
		
		
		/*
		
		//33333
		System.out.println("---------------------预测拥堵---------------  in  ");
		
		ArrayList<CongestionCoordinate> list = new ArrayList<CongestionCoordinate>();
		
		DaoImpl dao = new DaoImpl();
		/*ArrayList<Long> groupIdList = new ArrayList<Long>();
		groupIdList = dao.getAllGroupId();
	
		
		for(int i = 0;i < groupIdList.size();i++){
			  //System.out.println(groupIdList.get(i));
			list1 = Judge(groupIdList.get(i));
			//
			//System.out.println(oneList.isEmpty());
			if(!list1.isEmpty() || list1.size() != 0)
				list.addAll(list1);
		}
		return list;*/
		
		
		/*
		
		List<CarState> CongestionLastCarIdList = new ArrayList<CarState>();
		
		CongestionLastCarIdList =dao.getLastCongestionCar();//装尾车（以roadid,channel,groupid区分）信息
		System.out.println("尾车信息:"+dao.getLastCongestionCar().toString());
		
		for(int i=0;i < CongestionLastCarIdList.size();i++)
		{
			
			//List<CarState> listSt = new ArrayList<CarState>();
			//list1.addAll(dao.getCarInfoByDist(CongestionLastCarIdList.get(i).getCar_id(), safeDist));//以安全距离去找车
			//List<CongestionCoordinate> coordinateList1 = TrafficJam.getJamInfo((int)list1.get(0).getRoad_id(), list1.get(0).getChannel(), beginHall, endHall);
			//
			//System.out.println(oneList.isEmpty());
			List<CongestionCoordinate> list1 = new ArrayList<CongestionCoordinate>();
			System.out.print((i+1)+":");
			list1 = Judge(CongestionLastCarIdList.get(i));
			
			if(!list1.isEmpty() || list1.size() != 0)
				list.addAll(list1);
		}
		
		
		return list;
		
	}*/
	
	
	public List<CarState> Judge2(long groupId) throws Exception{
	//查询拥堵的车队的尾车
			DaoImpl dao = new DaoImpl();
			List<CarState> listlastall = new ArrayList<CarState>();//
			System.out.println("in judge 2");
			
			for(int s=1;s<3;s++)
			{
			
				List<CarState> list = dao.getCarInfoByGroupId1(groupId,s);//得到车队每个小车的最新数据
				//把内外道分开
				List<CarState> list1 = new ArrayList<CarState>();//装内道车
				List<CarState> list2 = new ArrayList<CarState>();//装外道车
		
				if(list.size() <= 1){
					continue;
				}
			
			//System.out.println(list.get(0).getCar_id());
			//System.out.println(list.get(1).getCar_id());
			
				for(int i = 0;i < list.size();i++){
					if(list.get(i).getChannel().equals("inside")){
						list1.add(list.get(i));
					//System.out.println("channel:"+list1.get(0).getChannel()+"roadId:"+list1.get(0).getRoad_id());
					}else if(list.get(i).getChannel().equals("outside")){
						list2.add(list.get(i));
					}
				}
			
				int in = 0;
				int out = 0;   ////2nd
			
			
			/*
			for(int x = 0;x < list1.size();x++){
				if(x == list.size() - 1){//最后一辆车不判断
					break;
				}
				if(list.get(x).getSpeed() < 1){//如果小车的速度小于某个值，并且车间距小于某个值，认为小车将要堵车
					if((list.get(x).getHistory_hall_count() - list.get(x+1).getHistory_hall_count()) > 20){	
						//如果小车的车间距大于5m，认为车队横跨了终点，等将来测出整个车道距离再说	
						
						
					}else if(Math.abs((list.get(x).getDrive_distance() - list.get(x+1).getDrive_distance())) < 20.5 
							&& Math.abs((list.get(x).getDrive_distance() - list.get(x+1).getDrive_distance())) >= 0.3
							&& list.get(x).getCar_id() != list.get(x+1).getCar_id()){
						in = in+1;
					}
				}
			}
			
			for(int y = 0;y < list2.size();y++){
				if(y == list.size() - 1){//最后一辆车不判断
					break;
				}
				if(list.get(y).getSpeed() < 1){//如果小车的速度小于某个值，并且车间距小于某个值，认为小车将要堵车
					if((list.get(y).getHistory_hall_count() - list.get(y+1).getHistory_hall_count()) > 20){	
						//如果小车的车间距大于5m，认为车队横跨了终点，等将来测出整个车道距离再说	
						
						
					}else if(Math.abs((list.get(y).getDrive_distance() - list.get(y+1).getDrive_distance())) < 0.5 
							&& Math.abs((list.get(y).getDrive_distance() - list.get(y+1).getDrive_distance())) >= 0.3
							&& list.get(y).getCar_id() != list.get(y+1).getCar_id()){
						//大于某个值，小于某个值设为可能拥堵
						out = out+1;
					}
				}
			}
			
			//System.out.println("in"+in+"out"+out);
			
			int beginHall=0,endHall = 0;
			//查找最大hall，最小hall
			//这里调用坐标对转化
			System.out.println("---------------------预测拥堵?---------------"+in+"...."+list1.size());
			if(in >= list1.size() - 1 && list1.size() != 0){
				System.out.println("---------------------预测拥堵---------------"+in);
				if(Math.abs(list1.get(0).getHistory_hall_count() - list1.get(list.size()-1).getHistory_hall_count()) > 20){
					//霍尔值差过大，说明跨了终点
					int maxHall = MaxHall(list1);
					int minHall = MinHall(list1);
					beginHall = minHall;
					endHall = maxHall;
					
				}else{
					int maxHall = MaxHall(list1);
					int minHall = MinHall(list1);
					beginHall = maxHall;
					endHall = minHall;
				}
				//System.out.println("inbegin:"+beginHall+" end:"+endHall);
				//System.out.println("roadId:"+list1.get(0).getRoad_id());
				List<CongestionCoordinate> coordinateList1 = TrafficJam.getJamInfo((int)list1.get(0).getRoad_id(), "inside", beginHall, endHall);
				//System.out.println("coordinateList1:"+coordinateList1.size());
				coordinateList.addAll(coordinateList1);
				//System.out.println("coordinateList:"+coordinateList.size());
			}
			
			if(out >= list2.size() - 1 && list2.size() != 0){
				if(Math.abs(list2.get(0).getHistory_hall_count() - list2.get(list.size()-1).getHistory_hall_count()) > 20){
					//霍尔值差过大，说明跨了终点
					int maxHall = MaxHall(list2);
					int minHall = MinHall(list2);
					beginHall = minHall;
					endHall = maxHall;
					
				}else{
					int maxHall = MaxHall(list2);
					int minHall = MinHall(list2);
					beginHall = maxHall;
					endHall = minHall;
				}
				//System.out.println("begin"+beginHall+" end"+endHall);
				List<CongestionCoordinate> coordinateList2 = TrafficJam.getJamInfo((int)list2.get(0).getRoad_id(), "outside", beginHall, endHall);
				coordinateList.addAll(coordinateList2);
			}*/
			
			//改inside
				int n = 0, m = 0;
				double[] l1n = new double[list1.size()];
				double[] l2n = new double[list2.size()];
				
				
				for(int x=0;x< list1.size();x++ )
				{
					l1n[x]=list1.get(x).getDrive_distance();
				}
				for(int x=0;x< list2.size();x++ )
				{
					l2n[x]=list2.get(x).getDrive_distance();
				}
				
				
				for(int x=0;x< list1.size();x++)
				{
					for(int y=0;y< list1.size();y++)
					{
						if(x==y)
						{
							continue;
						}
						
						
						if(list1.get(x).getSpeed() < 0.35){//如果小车的速度小于某个值，并且车间距小于某个值，认为小车将要堵车		
							
							if(Math.abs((list1.get(x).getDrive_distance() - list1.get(y).getDrive_distance())) < TRAFFICDISTANCE
									&& list1.get(x).getCar_id() != list1.get(y).getCar_id()){
								//1m??
								System.out.println("1carId："+list1.get(x).getCar_id());
								System.out.println("2carId："+list1.get(y).getCar_id());
								in = in+1;
								System.out.println("distance1:"+list1.get(x).getDrive_distance());
								System.out.println("distance1:"+list1.get(y).getDrive_distance());
								System.out.println("in："+in);
								break;
							}
							
							if(Math.abs(list1.get(x).getHistory_hall_count()-list1.get(y).getHistory_hall_count())>30)
							{
								
								double crossdist = list1.get(x).getDrive_distance()-list1.get(y).getDrive_distance() ;

								
								if( ( crossdist+THEWHOLELENGTH[0] <  TRAFFICDISTANCE+0.32 && crossdist+THEWHOLELENGTH[0] > 0)
										||( -crossdist+THEWHOLELENGTH[0] <  TRAFFICDISTANCE+0.32 && -crossdist+THEWHOLELENGTH[0] > 0))
								{
									in = in+1;
									
									System.out.println("car x:"+list1.get(x).getCar_id() +"car distance"+list1.get(x).getDrive_distance());
									System.out.println("car y:"+list1.get(y).getCar_id() +"car distance"+list1.get(y).getDrive_distance());
									
									break;
								}
							}
							
						}
					}//寻找符合堵塞的车
					
					for(int y1=0;y1< list1.size();y1++)
					{
					    if(x==y1)
						{
							continue;
						}
					    
					    if((list1.get(y1).getHistory_hall_count() - list1.get(x).getHistory_hall_count()) > 40){	
						//如果小车的车间距大于5m，认为车队横跨了终点，等将来测出整个车道距离再说	
					    	l1n[x]=list1.get(x).getDrive_distance()+1000.0;
					    	System.out.println("l1n:x : "+x+"dist: "+l1n[x]);
					    	break;
						
					    }
					}//找尾车  （过终点）
					
					
				}
								
				
				for(int x=0;x< list2.size();x++)
				{
					for(int y=0;y< list2.size();y++)
					{
						if(x==y)
						{
							continue;
						}
						
						
						if(list2.get(x).getSpeed() < 0.35){//如果小车的速度小于某个值，并且车间距小于某个值，认为小车将要堵车
							
							
							if(Math.abs((list2.get(x).getDrive_distance() - list2.get(y).getDrive_distance())) < TRAFFICDISTANCE
									&& list2.get(x).getCar_id() != list2.get(y).getCar_id()){
								//1m??
								out = out+1;
								break;
							}
							
							if(Math.abs(list2.get(x).getHistory_hall_count()-list2.get(y).getHistory_hall_count())>30)
							{
							
								double crossdist = list2.get(x).getDrive_distance()-list2.get(y).getDrive_distance() ;
							
								if( ( crossdist + THEWHOLELENGTH[1] <  TRAFFICDISTANCE+0.32 && crossdist + THEWHOLELENGTH[1] > 0 )
										|| ( -crossdist+THEWHOLELENGTH[1] <  TRAFFICDISTANCE+0.32 && -crossdist+THEWHOLELENGTH[1] > 0 ) )
								{
									out = out+1;
									break;
								}
								
							}	
								
							
							
						}
					}
					
					for(int y1=0;y1< list2.size();y1++)
					{
						if((list2.get(y1).getHistory_hall_count() - list2.get(x).getHistory_hall_count()) > 40){	
							//如果小车的车间距大于5m，认为车队横跨了终点，等将来测出整个车道距离再说	
							l2n[x]=list2.get(x).getDrive_distance()+1000;
							break;
						}
					    
					}
					
				}
				double minn = 2000, minm = 2000;
				for(int x=0;x< list1.size();x++ )
				{
					if(l1n[x] < minn)
					{
						n=x;
						minn=l1n[x];
					}
				}
				
				for(int x=0;x< list2.size();x++ )
				{
					if(l2n[x] < minm)
					{
						m=x;
						minm=l2n[x];
					}
				}
				
				
					
				//if((in+out)>=dao.getCarNumbyGroupId(list.get(0).getGroup_id(), s))
				//{
					if(in>= list1.size() && list1.size()!=0)
					{
						listlastall.add(list1.get(n));
						System.out.println("内道1号:"+list1.get(0).getCar_id()+"内道2号:"+list1.get(1).getCar_id()+"n:"+n);
					}
					if(out>= list2.size() && list2.size()!=0)
					{
						listlastall.add(list2.get(m));
					}
				//}
						
						
			}
			if(listlastall.size()>0)		
			{System.out.println("车尾车列表listlastall:"+listlastall.get(0).getDrive_distance());}
					return listlastall;
	}
	
	
	public List<CongestionCoordinate> Judge3(CarState carst) throws Exception{
		List<CongestionCoordinate> coordinateList1 = new ArrayList<CongestionCoordinate>();
		DaoImpl daoJ = new DaoImpl();
		int beginHall = 0 , endHall = 0;
		double safeDist = 20.0;//预测拥堵安全距离
		
		List<CarState> CPcarSt = new ArrayList<CarState>();
		
		System.out.println("-------------------in judge3 --");
		
		CPcarSt = daoJ.getCarInfoByDist(carst.getCar_id(), safeDist);//若出现临界起点 这里加if
		if( carst.getDrive_distance()<safeDist )
		{
			CPcarSt.addAll(daoJ.getCarInfoByDist1(carst.getCar_id(),safeDist-carst.getDrive_distance(),THEWHOLELENGTH[0]));//THEWHOLELENGTH[0]留着之后改  按road和channel改
		}
			
			CPcarSt.add(carst);
			System.out.println("-----预测拥堵-----CPcarSt.size():"+CPcarSt.size());
		
		
		if(CPcarSt.size() > 1 ){ //&& in >= CPcarSt.size()  ){
			System.out.println("---------------------预测拥堵---------------");//+in);
			/*if(Math.abs(CPcarSt.get(CPcarSt.size()-2).getHistory_hall_count() - CPcarSt.get(CPcarSt.size()-1).getHistory_hall_count()) > 40){
				//霍尔值差过大，说明跨了终
				int maxHall = MaxHall(CPcarSt);
				int minHall = MinHall(CPcarSt);
				beginHall = minHall;
				endHall = maxHall;

			}else{
				int maxHall = MaxHall(CPcarSt);
				int minHall = MinHall(CPcarSt);
				beginHall = maxHall;
				endHall = minHall;
				
			}*/
			
			
			//System.out.println("inbegin:"+beginHall+" end:"+endHall);
			//System.out.println("roadId:"+list1.get(0).getRoad_id());
			
			
			
			for(int i=0;i<CPcarSt.size()-1;i++)
			{
				System.out.println("lastCongestionCarId："+CPcarSt.get(CPcarSt.size()-1).getCar_id());
				System.out.println("lastCongestionDrive_Distance："+CPcarSt.get(CPcarSt.size()-1).getDrive_distance());
				System.out.println("congestionPredictCarId："+CPcarSt.get(i).getCar_id());
				System.out.println("congestionPredictCarId："+CPcarSt.get(i).getDrive_distance());
				
				int Mhall = (int)CPcarSt.get(CPcarSt.size()-1).getHistory_hall_count();
				int mhall = (int)CPcarSt.get(i).getHistory_hall_count();
				boolean Min = false,min = false;
				int MdeltaAngle=-1,deltaAngle=-1;
				
				for(int j=0;j<BENDHALL2INSIDE.length;j++)
				{
					if(Mhall == BENDHALL2INSIDE[j])
					{
						Min = true;
					}
					if(mhall == BENDHALL2INSIDE[j])
					{
						min = true;
					}
				}
				
				if(Min)
				{
					MdeltaAngle =  (int) (180.0*(( CPcarSt.get(CPcarSt.size()-1).getDrive_distance()-daoJ.getHallMinDistance(CPcarSt.get(CPcarSt.size()-1).getCar_id(),CPcarSt.get(CPcarSt.size()-1).getHistory_hall_count()) )/RADIUS[1]/Math.PI));
					System.out.println("--------------------------deltaAngel----:"+MdeltaAngle);
				}
				if(min)
				{

					deltaAngle = (int) (180.0*(( CPcarSt.get(i).getDrive_distance()-daoJ.getHallMinDistance(CPcarSt.get(i).getCar_id(),CPcarSt.get(i).getHistory_hall_count()) )/RADIUS[1]/Math.PI));
					
				}
				
				List<CongestionCoordinate> coordinateList11 = TrafficJam.getJamInfo((int)CPcarSt.get(i).getRoad_id(), carst.getChannel(), (int)CPcarSt.get(i).getHistory_hall_count(),  (int)CPcarSt.get(CPcarSt.size()-1).getHistory_hall_count() , MdeltaAngle , deltaAngle);
				//System.out.println("coordinateList1:"+coordinateList1.size());
				//补尾
				CarState carState = CPcarSt.get(i);
				//System.out.println("carState"+ carState.getHistory_hall_count());
				CongestionCoordinate congestionCoordinate = new CongestionCoordinate();
				if(TrafficJam.getSpecial((int)carState.getRoad_id(), carState.getChannel(), (int)carState.getHistory_hall_count()) == 0
					&& TrafficJam.getSpecial((int)carState.getRoad_id(), carState.getChannel(), (int)carState.getHistory_hall_count()+1) == 0){
				    coordinateList11 = TrafficJam.getJamInfo((int)CPcarSt.get(i).getRoad_id(), carst.getChannel(), (int)CPcarSt.get(i).getHistory_hall_count() + 1,  (int)CPcarSt.get(CPcarSt.size()-1).getHistory_hall_count(), MdeltaAngle , deltaAngle);
				    congestionCoordinate = getPreCongestionCoordinate(carState);
				}
				if(TrafficJam.isEndHall((int)carState.getRoad_id(), carState.getChannel(), (int)carState.getHistory_hall_count())){
				    coordinateList11 = TrafficJam.getJamInfo((int)CPcarSt.get(i).getRoad_id(), carst.getChannel(), 1,  (int)CPcarSt.get(CPcarSt.size()-1).getHistory_hall_count(), MdeltaAngle , deltaAngle);
				}
				//补头
				CarState carState2 = CPcarSt.get(CPcarSt.size()-1);
				CongestionCoordinate congestionCoordinate2 = new CongestionCoordinate();
				if(TrafficJam.getSpecial((int)carState2.getRoad_id(), carState2.getChannel(), (int)carState2.getHistory_hall_count()) == 0){
				    System.out.println(""+carState2.getCar_id() + " " + carState2.getHistory_hall_count());
				    
				    congestionCoordinate2 = GetTrafficList.getCongestionCoordinate(carState2);
				}
				
				coordinateList1.add(congestionCoordinate2);
				coordinateList1.add(congestionCoordinate);
				coordinateList1.addAll(coordinateList11);
				//System.out.println("coordinateList:"+coordinateList.size());
			}
			
		}
		
		return coordinateList1;
		
		
		
	}
	
	public static int MaxHall(List<CarState> list){
		if(list.size() == 0){
			return 0;
		}
		int max = 0;
		for(int i = 0;i<list.size();i++){
			if(list.get(i).getHistory_hall_count() > max){
				max = (int) list.get(i).getHistory_hall_count();
			}
		}
		return max;
		
	}
	
	public static int MinHall(List<CarState> list){
		if(list.size() == 0){
			return 0;
		}
		int min = 100;
		for(int i = 0;i<list.size();i++){
			if(list.get(i).getHistory_hall_count() < min){
				min = (int) list.get(i).getHistory_hall_count();
			}
		}
		return min;
	}	
	
}
