package com.map;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.car.dao.DaoImpl;
import com.entity.CarBendInfo;
import com.entity.CarCoordinateInfo;
import com.entity.CarState;
import com.entity.CarWarnInfo;
import com.entity.ShuntId;

public class GetTrafficList {

	/**
	 * @param args
	 * @throws Exception 
	 */
	private static final double TRAFFICDISTANCE = 1;
	
	
	private static final double[] THEWHOLELENGTH = {15.7 , 1000 , 1000 , 1000 , 1000 , 1000 };
	private static final double[] RADIUS = {0.4917 , 0.4915 , 0.4917 , 0.4917 };
	private static final int[] BENDHALL2INSIDE = {2 , 33 , 49 , 66 };
	private static final int[] BENDHALL2OUTSIDE = { };
	
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		GetTrafficList gt = new GetTrafficList();
		List<CongestionCoordinate> list = gt.JudgeCongestion();
		if(list.size() != 0)
			System.out.println(list.get(0).getX1());
	}
	
	public ArrayList<CongestionCoordinate> JudgeCongestion() throws Exception{
		ArrayList<CongestionCoordinate> list = new ArrayList<CongestionCoordinate>();
		
		DaoImpl dao = new DaoImpl();
		ArrayList<Long> groupIdList = new ArrayList<Long>();
		
		groupIdList = dao.getAllGroupId();
		MapDaoImpl mDao = new MapDaoImpl();
		
		List<ShuntId> shuntIdList = mDao.getAllShuntCarId();
		
		List<CarState> CarSt = new ArrayList<CarState>();
		CarSt = dao.getCarInfoByGroupId2(1,3);
		System.out.println("CarSt " + CarSt.size());
		if(CarSt.size()>0 && shuntIdList.size() >0)
		{
			return list;
		}
		
		for(int i = 0;i < groupIdList.size();i++){
			List<CongestionCoordinate> list1 = new ArrayList<CongestionCoordinate>();
			//System.out.println(groupIdList.get(i));
			list1 = Judge(groupIdList.get(i));
			//
			//System.out.println(oneList.isEmpty());
			if(!list1.isEmpty() || list1.size() != 0)
				list.addAll(list1);
		}
		return list;
	}
	
	public List<CongestionCoordinate> Judge(long groupId) throws Exception{
		//判断车队是否拥堵
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
				if(list1.contains(list.get(i).getCar_id())){
					continue;
				}
				list1.add(list.get(i));
				//System.out.println("channel:"+list1.get(0).getChannel()+"roadId:"+list1.get(0).getRoad_id());
			}else if(list.get(i).getChannel().equals("outside")){
				if(list2.contains(list.get(i).getCar_id())){
					continue;
				}
				list2.add(list.get(i));
			}
		}
		
	
		
		int in = 0;
		int out = 0;
		/*
		for(int x = 0;x < list1.size();x++){
			if(x == list.size() - 1){//最后一辆车不判断
				break;
			}
			if(list.get(x).getSpeed() < 1){//如果小车的速度小于某个值，并且车间距小于某个值，认为小车将要堵车
				if((list.get(x).getHistory_hall_count() - list.get(x+1).getHistory_hall_count()) > 20){	
					//如果小车的车间距大于5m，认为车队横跨了终点，等将来测出整个车道距离再说	
					
					
				}else if(Math.abs((list.get(x).getDrive_distance() - list.get(x+1).getDrive_distance())) < 0.5
						&& list.get(x).getCar_id() != list.get(x+1).getCar_id()){
					//1m??
					System.out.println("1carId："+list.get(x).getCar_id());
					System.out.println("2carId："+list.get(x+1).getCar_id());
					in = in+1;
					System.out.println("distance1:"+list.get(x).getDrive_distance());
					System.out.println("distance1:"+list.get(x+1).getDrive_distance());
					System.out.println("in："+in);
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
						&& list.get(y).getCar_id() != list.get(y+1).getCar_id()){
					out = out+1;
				}
			}
		}
		
		//System.out.println("in"+in+"out"+out);
		
		int beginHall=0,endHall = 0;
		
		
		System.out.println("---------------------拥堵?---------------"+in+"    "+list1.size());
		//这里调用坐标对转化
		if(list1.size() != 0 && in >= list1.size() - 1 ){
			System.out.println("---------------------拥堵---------------"+in);
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
			List<CongestionCoordinate> coordinateList1 = TrafficJam.getJamInfo((int)list1.get(0).getRoad_id(), "inside", beginHall, endHall);
			//System.out.println("coordinateList1:"+coordinateList1.size());
			coordinateList.addAll(coordinateList1);
			//System.out.println("coordinateList:"+coordinateList.size());
		}
		
		if(list2.size() != 0 && out >= list2.size() - 1  ){
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
		*/
		
		//改inside
		
		int n = 0, m = 0, Mn = 0,Mm = 0;
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
				//THEWHOLELENGTH[0] 中 0为线性组合
				
				if(list1.get(x).getSpeed() < 0.35){//如果小车的速度小于某个值，并且车间距小于某个值，认为小车将要堵车
					/*if((list1.get(x).getHistory_hall_count() - list1.get(y).getHistory_hall_count()) > 20){	
						//如果小车的车间距大于5m，认为车队横跨了终点，等将来测出整个车道距离再说	
						//y已过终点  x未过终点
						double detaDistance = list1.get(y).getDrive_distance() - list1.get(x).getDrive_distance() +THEWHOLELENGTH[0];
						if((detaDistance) < TRAFFICDISTANCE + 0.32 && detaDistance > 0)
								{
									in = in+1;
									System.out.println("car y ："+list1.get(y).getCar_id()+" 已过终点  " + " distance : "+list1.get(y).getDrive_distance());
									System.out.println("car x ："+list1.get(x).getCar_id()+" 未过终点  " + " distance : "+list1.get(x).getDrive_distance());
									break;
								}
					}	
					else if(list1.get(y).getHistory_hall_count() - list1.get(x).getHistory_hall_count() > 20)
					{
						//x已过终点  y未过终点
						double detaDistance2 = list1.get(x).getDrive_distance() - list1.get(y).getDrive_distance() +THEWHOLELENGTH[0] ;
						if( detaDistance2 < TRAFFICDISTANCE + 0.32 && detaDistance2 > 0)
								{
									in = in+1;
									System.out.println("car x ："+list1.get(x).getCar_id()+" 已过终点  " + " distance : "+list1.get(x).getDrive_distance());
									System.out.println("car y ："+list1.get(y).getCar_id()+" 未过终点  " + " distance : "+list1.get(y).getDrive_distance());
									break;
								}
						
					}
					else 
					*/
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

						
						if( ( crossdist+THEWHOLELENGTH[0] <  TRAFFICDISTANCE && crossdist+THEWHOLELENGTH[0] > 0)
								||( -crossdist+THEWHOLELENGTH[0] <  TRAFFICDISTANCE && -crossdist+THEWHOLELENGTH[0] > 0))
						{
							in = in+1;
							
							System.out.println("car x:"+list1.get(x).getCar_id() +"car distance"+list1.get(x).getDrive_distance());
							System.out.println("car y:"+list1.get(y).getCar_id() +"car distance"+list1.get(y).getDrive_distance());
							
							break;
						}
					}
					
					
				}
			}
			
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
					/*if((list2.get(x).getHistory_hall_count() - list2.get(y).getHistory_hall_count()) > 20){	
						//如果小车的车间距大于5m，认为车队横跨了终点，等将来测出整个车道距离再说	
						
						
					}else */
					if(Math.abs((list2.get(x).getDrive_distance() - list2.get(y).getDrive_distance())) < TRAFFICDISTANCE
							&& list2.get(x).getCar_id() != list2.get(y).getCar_id()){
						//1m??
						out = out+1;
						break;
					}
					if(Math.abs(list2.get(x).getHistory_hall_count()-list2.get(y).getHistory_hall_count())>30)
					{
					
						double crossdist = list2.get(x).getDrive_distance()-list2.get(y).getDrive_distance() ;
					
						if( ( crossdist + THEWHOLELENGTH[1] <  TRAFFICDISTANCE && crossdist + THEWHOLELENGTH[1] > 0 )
								|| ( -crossdist+THEWHOLELENGTH[1] <  TRAFFICDISTANCE && -crossdist+THEWHOLELENGTH[1] > 0 ) )
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
		
		
		int beginHall=0,endHall = 0;
		
		
		System.out.println("---------------------拥堵?---------------"+in+"    "+list1.size());
		//这里调用坐标对转化
		
		double minn = 2000, minm = 2000;
		double maxn = 0, maxm = 0;
		if(list1.size() != 0 && in >= list1.size()  ){
			System.out.println("---------------------拥堵---------------"+in);
			/*if(Math.abs(list1.get(0).getHistory_hall_count() - list1.get(list.size()-1).getHistory_hall_count()) > 20){
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
			List<CongestionCoordinate> coordinateList1 = TrafficJam.getJamInfo((int)list1.get(0).getRoad_id(), "inside", beginHall, endHall);
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
		*/
			

			for(int x=0;x< list1.size();x++ )
			{
				if(l1n[x] < minn)
				{
					n=x;
					minn=l1n[x];
				}
				
				if(l1n[x] > maxn)
				{
					Mn=x;
					maxn=l1n[x];
				}
				
			}
			int Mhall = (int)list1.get(Mn).getHistory_hall_count();
			int mhall = (int)list1.get(n).getHistory_hall_count();
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
				MdeltaAngle =  (int) (180.0*(( list1.get(Mn).getDrive_distance()-dao.getHallMinDistance(list1.get(Mn).getCar_id(),list1.get(Mn).getHistory_hall_count()) )/RADIUS[1]/Math.PI));
				System.out.println("--------------------------deltaAngel----:"+MdeltaAngle);
			}
			if(min)
			{

				deltaAngle = (int) (180.0*(( list1.get(n).getDrive_distance()-dao.getHallMinDistance(list1.get(n).getCar_id(),list1.get(n).getHistory_hall_count()) )/RADIUS[1]/Math.PI));
				
			}
			
			List<CongestionCoordinate> coordinateList1 = TrafficJam.getJamInfo((int)list1.get(0).getRoad_id(), "inside", (int)list1.get(n).getHistory_hall_count(), (int)list1.get(Mn).getHistory_hall_count(),MdeltaAngle,deltaAngle);			
			CarState carState = list1.get(Mn);
			
			System.out.println("begincar id+hall:"+list1.get(Mn).getCar_id()+"    "+list1.get(Mn).getHistory_hall_count());
			System.out.println("endcar id+hall:"+list1.get(n).getCar_id()+"    "+list1.get(n).getHistory_hall_count());
			if(TrafficJam.getSpecial((int)carState.getRoad_id(), carState.getChannel(), (int)carState.getHistory_hall_count()) == 0){
			    CongestionCoordinate congestionCoordinate = getCongestionCoordinate(carState);
			    coordinateList.add(congestionCoordinate);
			}
			coordinateList.addAll(coordinateList1);
		
		
		}
		
		if(list2.size() != 0 && out >= list2.size()  ){
			System.out.println("---------------------拥堵---------------"+out);
		
			for(int x=0;x< list2.size();x++ )
			{
				if(l2n[x] < minm)
				{
					m=x;
					minm=l2n[x];
				}
			
				if(l2n[x] > maxm)
				{
					Mm=x;
					maxm=l2n[x];
				}
			}
			int Mhall = (int)list2.get(Mm).getHistory_hall_count();
			int mhall = (int)list2.get(m).getHistory_hall_count();
			boolean Min = false,min = false;
			int MdeltaAngle=-1,deltaAngle=-1;
			
			for(int j=0;j<BENDHALL2OUTSIDE.length;j++)
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
				MdeltaAngle =  (int) (180.0*(( list2.get(Mm).getDrive_distance()-dao.getHallMinDistance(list2.get(Mm).getCar_id(),list2.get(Mm).getHistory_hall_count()) )/RADIUS[1]/Math.PI));
				System.out.println("--------------------------deltaAngel----:"+MdeltaAngle);
			}
			if(min)
			{

				deltaAngle = (int) (180.0*(( list2.get(m).getDrive_distance()-dao.getHallMinDistance(list2.get(m).getCar_id(),list2.get(m).getHistory_hall_count()) )/RADIUS[1]/Math.PI));
				
			}
			
			List<CongestionCoordinate> coordinateList2 = TrafficJam.getJamInfo((int)list2.get(0).getRoad_id(), "outside", (int)list2.get(m).getHistory_hall_count(), (int)list2.get(Mm).getHistory_hall_count(),MdeltaAngle,deltaAngle);
			CarState carState = list1.get(Mm);
			if(TrafficJam.getSpecial((int)carState.getRoad_id(), carState.getChannel(), (int)carState.getHistory_hall_count()) == 0){
			    CongestionCoordinate congestionCoordinate = getCongestionCoordinate(carState);
			    coordinateList.add(congestionCoordinate);
			}
			
			coordinateList.addAll(coordinateList2);
		}
		//System.out.println(coordinateList.get(0).getX1());
		return coordinateList;		
	}
	
	
	public int MaxHall(List<CarState> list){
		if(list.size() == 0){
			//System.out.println("list.size()"+list.size());
			return 0;
		}
		int max = 0;
		for(int i = 0;i<list.size();i++){
			//System.out.println("list.size()"+list.size());
			if(list.get(i).getHistory_hall_count() > max){
				//System.out.println("maxlist.get(i).getHistory_hall_count()："+list.get(i).getHistory_hall_count());
				max = (int) list.get(i).getHistory_hall_count();
			}
		}
		return max;
		
	}
	
	public int MinHall(List<CarState> list){
		if(list.size() == 0){
			//System.out.println("list.size()"+list.size());
			return 0;
		}
		int min = 100;
		for(int i = 0;i<list.size();i++){
			if(list.get(i).getHistory_hall_count() < min){
				//System.out.println("minlist.get(i).getHistory_hall_count()："+list.get(i).getHistory_hall_count());
				min = (int) list.get(i).getHistory_hall_count();
			}
		}
		return min;
		
	}

	public static CongestionCoordinate getCongestionCoordinate(CarState carState){
	    	DaoImpl dao = new DaoImpl();
		long carId = carState.getCar_id();
		int roadId = (int) carState.getRoad_id();
		String channel = carState.getChannel();
		int hall = (int) carState.getHistory_hall_count();
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
		    System.out.println("x1: "+x1);
		    int special = 0;
		    ccCongestionCoordinate.setSpecial(special);
	    	    ccCongestionCoordinate.setX1(x1);
	    	    ccCongestionCoordinate.setY1(y1);
	    	    ccCongestionCoordinate.setX2(x2);
	    	    ccCongestionCoordinate.setY2(y2);
		    //coordinateList.add(ccCongestionCoordinate);
		
		return ccCongestionCoordinate;
	}
	
	
	

//	public List<CongestionCoordinate> getTrafficList() throws Exception{
//		
//		List<CongestionCoordinate> list = new ArrayList<CongestionCoordinate>();//装坐标对
//		List<Map<String,Object>> list1 = new ArrayList<Map<String,Object>>();//装霍尔对
//		//List<CarBendInfo> list2 = new ArrayList<CarBendInfo>();//用于判断小车是否是转弯处的霍尔
//		List<Map<String,Object>> list3 = new ArrayList<Map<String,Object>>();//用于判断小车是否在起点和终点霍尔之间
//		//List<CarWarnInfo> list4 = new ArrayList<CarWarnInfo>();//得到小车的内外道信息，和list3一起以此来判断小车霍尔是否在起点和终点霍尔之间
//		
//		MapDaoImpl dao = new MapDaoImpl();//每个小车得到霍尔对，再将霍尔对转化成坐标
//		
//		list1 = dao.getCongestionHall();//得到拥堵小车的霍尔
//		//list2 = dao.getAllBendInfo();
//		list3 = dao.getCongestionCarMaxHall();
//		//list4 = dao.getCongestionCarInfo();
//		//boolean isSpecial =false;
//		boolean isMaxHall = false;
//		long carId;
//		String channel ="";
//		long roadId = 0;
//		long Maxhall,hall;
//		for(int i = 0;i<list1.size();i++){//通过小车id，得到小车的CarInfo，来判断小车是不是刚好位于起点和终点的霍尔之间
//			carId = (Long) list1.get(i).get("car_id");
//			hall = (Long) list1.get(i).get("history_hall_count");
//			channel = (String) list1.get(i).get("channel");
//			roadId = (Long) list1.get(i).get("road_id");
////			for(int j = 0;j<list4.size();j++){
////				if(carId == list4.get(j).getCar_id()){
////					//channel = list4.get(j).getChannel();
////					roadId = list4.get(j).getRoad_id();
////				}				
////			}//得到小车的channel和roadId
//									
//			//判断小车是否是位于起点终点之间的霍尔
//			for(int m = 0;m<list3.size();m++){//如果是最大的霍尔
//				if(hall == (Long)list3.get(m).get("max") && 
//						channel.equals(list3.get(m).get("channel")) &&
//						roadId == (Long)list3.get(m).get("road_id")){
//					isMaxHall = true;
//					
//					break;
//				}
//			}						
//			
//	
//			//如果是拐弯处，得到其拐弯信息
//			
//			
//			if(!isMaxHall){//不是最大霍尔
//				List<CarBendInfo> list2 = dao.getSpecialCarBend((int)roadId, channel, hall);
//				if(list2.size() != 0){//是特殊霍尔					
//					//isSpecial = true;
//					for(int k = 0;k<list.size();k++){
//						CongestionCoordinate cc = new CongestionCoordinate ();
//						if(k+1 == list.size()){
//							break;//到了最后一个不再进行添加
//						}else{
//							cc.setX1(list2.get(k).getX());
//							cc.setY1(list2.get(k).getY());
//							cc.setX2(list2.get(k+1).getX());
//							cc.setY2(list2.get(k+1).getY());
//						}
//					}
//				}else{//不是特殊霍尔
//					
//					CongestionCoordinate cc = hallToCoordinate(hall,isMaxHall,roadId,channel);
//					list.add(cc);
//				}
//			}else{//是最大霍尔
//				CongestionCoordinate cc = hallToCoordinate(hall,isMaxHall,roadId,channel);
//				list.add(cc);
//			}
//			
//			
//				
//			
////			if(!isMaxHall){//如果不是最大霍尔，判断是否为特殊霍尔
////				for(int k = 0;k<list2.size();k++){
////					if(channel.equals(list2.get(k).getChannel()) && hall == list2.get(k).getHall_count()
////							&& roadId == list2.get(k).getRoad_id()){
////						isSpecial = true;
////						break;
////					}
////				}
////			}else{//如果是最大霍尔
////				CongestionCoordinate cc = hallToCoordinate(hall,isMaxHall,roadId,channel);
////				list.add(cc);
////			}
//			
//			
////			if(isSpecial){//特殊的霍尔，并且不是最大霍尔，需要操作添加拐弯的坐标对
////				for(int t=0;t<list2.size();t++){
////					if(roadId == list2.get(i).getRoad_id() && channel.equals(list2.get(t).getChannel())){
////						CongestionCoordinate cc = new CongestionCoordinate();
////						
////					}
////				}
////			}else{//不是特殊霍尔，不是最大霍尔直接转化hall为坐标，添加坐标对
////				CongestionCoordinate cc = hallToCoordinate(hall,isMaxHall,roadId,channel);
////				list.add(cc);
////			}
//				
//		}							
//		
//		return list;
//		
//	}
//	public CongestionCoordinate hallToCoordinate(long hall, boolean isMaxHall, long roadId, String channel) throws Exception {
//		// TODO Auto-generated method stub
//		//非拐弯点的x,y坐标获取
//		double rate = 1105.0 / 700.0;
//		double x1,y1,x2,y2;
//		MapDaoImpl dao = new MapDaoImpl();
//		List<CarCoordinateInfo> list = dao.getAllHallCoordinate();
//		
//		long hall1;
//		if(isMaxHall){
//			hall1 = 0;
//		}else
//			hall1 = hall+2;
//		
//		CongestionCoordinate cc = new CongestionCoordinate();
//		
//		for(int i = 0;i<list.size();i++){
//			if(roadId == list.get(i).getRoad_id() &&
//					channel.equals(list.get(i).getChannel()) &&
//					hall == list.get(i).getHall()){
//				x1 = list.get(i).getCoordinate_x() * rate;
//				y1 = list.get(i).getCoordinate_y() * rate;
//				cc.setX1((long) x1);
//				cc.setY1((long) y1);
//			}else if(roadId == list.get(i).getRoad_id() &&
//					channel.equals(list.get(i).getChannel()) &&
//					hall1 == list.get(i).getHall()){
//				x2 = list.get(i).getCoordinate_x() * rate;
//				y2 = list.get(i).getCoordinate_y() * rate;
//				cc.setX2((long) x2);
//				cc.setY2((long) y2);
//			}
//		}
//		
//		return cc;
//	}

}
