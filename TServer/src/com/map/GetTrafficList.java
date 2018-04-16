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
		//�жϳ����Ƿ�ӵ��
		DaoImpl dao = new DaoImpl();
		List<CarState> list = dao.getCarInfoByGroupId(groupId);//�õ�����ÿ��С������������
		//��������ֿ�
		List<CarState> list1 = new ArrayList<CarState>();//װ�ڵ���
		List<CarState> list2 = new ArrayList<CarState>();//װ�����
		List<CongestionCoordinate> coordinateList = new ArrayList<CongestionCoordinate>();//�������
		
		
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
			if(x == list.size() - 1){//���һ�������ж�
				break;
			}
			if(list.get(x).getSpeed() < 1){//���С�����ٶ�С��ĳ��ֵ�����ҳ����С��ĳ��ֵ����ΪС����Ҫ�³�
				if((list.get(x).getHistory_hall_count() - list.get(x+1).getHistory_hall_count()) > 20){	
					//���С���ĳ�������5m����Ϊ���Ӻ�����յ㣬�Ƚ��������������������˵	
					
					
				}else if(Math.abs((list.get(x).getDrive_distance() - list.get(x+1).getDrive_distance())) < 0.5
						&& list.get(x).getCar_id() != list.get(x+1).getCar_id()){
					//1m??
					System.out.println("1carId��"+list.get(x).getCar_id());
					System.out.println("2carId��"+list.get(x+1).getCar_id());
					in = in+1;
					System.out.println("distance1:"+list.get(x).getDrive_distance());
					System.out.println("distance1:"+list.get(x+1).getDrive_distance());
					System.out.println("in��"+in);
				}
			}
		}
		
		for(int y = 0;y < list2.size();y++){
			if(y == list.size() - 1){//���һ�������ж�
				break;
			}
			if(list.get(y).getSpeed() < 1){//���С�����ٶ�С��ĳ��ֵ�����ҳ����С��ĳ��ֵ����ΪС����Ҫ�³�
				if((list.get(y).getHistory_hall_count() - list.get(y+1).getHistory_hall_count()) > 20){	
					//���С���ĳ�������5m����Ϊ���Ӻ�����յ㣬�Ƚ��������������������˵	
					
					
				}else if(Math.abs((list.get(y).getDrive_distance() - list.get(y+1).getDrive_distance())) < 0.5 
						&& list.get(y).getCar_id() != list.get(y+1).getCar_id()){
					out = out+1;
				}
			}
		}
		
		//System.out.println("in"+in+"out"+out);
		
		int beginHall=0,endHall = 0;
		
		
		System.out.println("---------------------ӵ��?---------------"+in+"    "+list1.size());
		//������������ת��
		if(list1.size() != 0 && in >= list1.size() - 1 ){
			System.out.println("---------------------ӵ��---------------"+in);
			if(Math.abs(list1.get(0).getHistory_hall_count() - list1.get(list.size()-1).getHistory_hall_count()) > 20){
				//����ֵ�����˵��������
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
				//����ֵ�����˵�������յ�
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
		
		//��inside
		
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
				//THEWHOLELENGTH[0] �� 0Ϊ�������
				
				if(list1.get(x).getSpeed() < 0.35){//���С�����ٶ�С��ĳ��ֵ�����ҳ����С��ĳ��ֵ����ΪС����Ҫ�³�
					/*if((list1.get(x).getHistory_hall_count() - list1.get(y).getHistory_hall_count()) > 20){	
						//���С���ĳ�������5m����Ϊ���Ӻ�����յ㣬�Ƚ��������������������˵	
						//y�ѹ��յ�  xδ���յ�
						double detaDistance = list1.get(y).getDrive_distance() - list1.get(x).getDrive_distance() +THEWHOLELENGTH[0];
						if((detaDistance) < TRAFFICDISTANCE + 0.32 && detaDistance > 0)
								{
									in = in+1;
									System.out.println("car y ��"+list1.get(y).getCar_id()+" �ѹ��յ�  " + " distance : "+list1.get(y).getDrive_distance());
									System.out.println("car x ��"+list1.get(x).getCar_id()+" δ���յ�  " + " distance : "+list1.get(x).getDrive_distance());
									break;
								}
					}	
					else if(list1.get(y).getHistory_hall_count() - list1.get(x).getHistory_hall_count() > 20)
					{
						//x�ѹ��յ�  yδ���յ�
						double detaDistance2 = list1.get(x).getDrive_distance() - list1.get(y).getDrive_distance() +THEWHOLELENGTH[0] ;
						if( detaDistance2 < TRAFFICDISTANCE + 0.32 && detaDistance2 > 0)
								{
									in = in+1;
									System.out.println("car x ��"+list1.get(x).getCar_id()+" �ѹ��յ�  " + " distance : "+list1.get(x).getDrive_distance());
									System.out.println("car y ��"+list1.get(y).getCar_id()+" δ���յ�  " + " distance : "+list1.get(y).getDrive_distance());
									break;
								}
						
					}
					else 
					*/
					if(Math.abs((list1.get(x).getDrive_distance() - list1.get(y).getDrive_distance())) < TRAFFICDISTANCE
							&& list1.get(x).getCar_id() != list1.get(y).getCar_id()){
						//1m??
						System.out.println("1carId��"+list1.get(x).getCar_id());
						System.out.println("2carId��"+list1.get(y).getCar_id());
						in = in+1;
						System.out.println("distance1:"+list1.get(x).getDrive_distance());
						System.out.println("distance1:"+list1.get(y).getDrive_distance());
						System.out.println("in��"+in);
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
				//���С���ĳ�������5m����Ϊ���Ӻ�����յ㣬�Ƚ��������������������˵	
			    	l1n[x]=list1.get(x).getDrive_distance()+1000.0;
			    	System.out.println("l1n:x : "+x+"dist: "+l1n[x]);
			    	break;
				
			    }
			}//��β��  �����յ㣩
			
			
		}
		
		for(int x=0;x< list2.size();x++)
		{
			for(int y=0;y< list2.size();y++)
			{
				if(x==y)
				{
					continue;
				}
				
				
				if(list2.get(x).getSpeed() < 0.35){//���С�����ٶ�С��ĳ��ֵ�����ҳ����С��ĳ��ֵ����ΪС����Ҫ�³�
					/*if((list2.get(x).getHistory_hall_count() - list2.get(y).getHistory_hall_count()) > 20){	
						//���С���ĳ�������5m����Ϊ���Ӻ�����յ㣬�Ƚ��������������������˵	
						
						
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
					//���С���ĳ�������5m����Ϊ���Ӻ�����յ㣬�Ƚ��������������������˵	
					l2n[x]=list2.get(x).getDrive_distance()+1000;
					break;
				}
			    
			}
			
		}
		
		
		int beginHall=0,endHall = 0;
		
		
		System.out.println("---------------------ӵ��?---------------"+in+"    "+list1.size());
		//������������ת��
		
		double minn = 2000, minm = 2000;
		double maxn = 0, maxm = 0;
		if(list1.size() != 0 && in >= list1.size()  ){
			System.out.println("---------------------ӵ��---------------"+in);
			/*if(Math.abs(list1.get(0).getHistory_hall_count() - list1.get(list.size()-1).getHistory_hall_count()) > 20){
				//����ֵ�����˵��������
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
				//����ֵ�����˵�������յ�
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
			System.out.println("---------------------ӵ��---------------"+out);
		
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
				//System.out.println("maxlist.get(i).getHistory_hall_count()��"+list.get(i).getHistory_hall_count());
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
				//System.out.println("minlist.get(i).getHistory_hall_count()��"+list.get(i).getHistory_hall_count());
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
		//��ͼ����
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
//		List<CongestionCoordinate> list = new ArrayList<CongestionCoordinate>();//װ�����
//		List<Map<String,Object>> list1 = new ArrayList<Map<String,Object>>();//װ������
//		//List<CarBendInfo> list2 = new ArrayList<CarBendInfo>();//�����ж�С���Ƿ���ת�䴦�Ļ���
//		List<Map<String,Object>> list3 = new ArrayList<Map<String,Object>>();//�����ж�С���Ƿ��������յ����֮��
//		//List<CarWarnInfo> list4 = new ArrayList<CarWarnInfo>();//�õ�С�����������Ϣ����list3һ���Դ����ж�С�������Ƿ��������յ����֮��
//		
//		MapDaoImpl dao = new MapDaoImpl();//ÿ��С���õ������ԣ��ٽ�������ת��������
//		
//		list1 = dao.getCongestionHall();//�õ�ӵ��С���Ļ���
//		//list2 = dao.getAllBendInfo();
//		list3 = dao.getCongestionCarMaxHall();
//		//list4 = dao.getCongestionCarInfo();
//		//boolean isSpecial =false;
//		boolean isMaxHall = false;
//		long carId;
//		String channel ="";
//		long roadId = 0;
//		long Maxhall,hall;
//		for(int i = 0;i<list1.size();i++){//ͨ��С��id���õ�С����CarInfo�����ж�С���ǲ��Ǹպ�λ�������յ�Ļ���֮��
//			carId = (Long) list1.get(i).get("car_id");
//			hall = (Long) list1.get(i).get("history_hall_count");
//			channel = (String) list1.get(i).get("channel");
//			roadId = (Long) list1.get(i).get("road_id");
////			for(int j = 0;j<list4.size();j++){
////				if(carId == list4.get(j).getCar_id()){
////					//channel = list4.get(j).getChannel();
////					roadId = list4.get(j).getRoad_id();
////				}				
////			}//�õ�С����channel��roadId
//									
//			//�ж�С���Ƿ���λ������յ�֮��Ļ���
//			for(int m = 0;m<list3.size();m++){//��������Ļ���
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
//			//����ǹ��䴦���õ��������Ϣ
//			
//			
//			if(!isMaxHall){//����������
//				List<CarBendInfo> list2 = dao.getSpecialCarBend((int)roadId, channel, hall);
//				if(list2.size() != 0){//���������					
//					//isSpecial = true;
//					for(int k = 0;k<list.size();k++){
//						CongestionCoordinate cc = new CongestionCoordinate ();
//						if(k+1 == list.size()){
//							break;//�������һ�����ٽ������
//						}else{
//							cc.setX1(list2.get(k).getX());
//							cc.setY1(list2.get(k).getY());
//							cc.setX2(list2.get(k+1).getX());
//							cc.setY2(list2.get(k+1).getY());
//						}
//					}
//				}else{//�����������
//					
//					CongestionCoordinate cc = hallToCoordinate(hall,isMaxHall,roadId,channel);
//					list.add(cc);
//				}
//			}else{//��������
//				CongestionCoordinate cc = hallToCoordinate(hall,isMaxHall,roadId,channel);
//				list.add(cc);
//			}
//			
//			
//				
//			
////			if(!isMaxHall){//����������������ж��Ƿ�Ϊ�������
////				for(int k = 0;k<list2.size();k++){
////					if(channel.equals(list2.get(k).getChannel()) && hall == list2.get(k).getHall_count()
////							&& roadId == list2.get(k).getRoad_id()){
////						isSpecial = true;
////						break;
////					}
////				}
////			}else{//�����������
////				CongestionCoordinate cc = hallToCoordinate(hall,isMaxHall,roadId,channel);
////				list.add(cc);
////			}
//			
//			
////			if(isSpecial){//����Ļ��������Ҳ�������������Ҫ������ӹ���������
////				for(int t=0;t<list2.size();t++){
////					if(roadId == list2.get(i).getRoad_id() && channel.equals(list2.get(t).getChannel())){
////						CongestionCoordinate cc = new CongestionCoordinate();
////						
////					}
////				}
////			}else{//�����������������������ֱ��ת��hallΪ���꣬��������
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
//		//�ǹ�����x,y�����ȡ
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
