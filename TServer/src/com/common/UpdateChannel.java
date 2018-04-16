package com.common;
import java.util.ArrayList;
import java.util.List;

import com.car.dao.DaoImpl;
import com.entity.CarState;


public class UpdateChannel {

	public static void main(String[] args) throws Exception{
		UpdateChannel update = new UpdateChannel();
		long hall = update.getChangeHall(1);
		System.out.println(hall);
		//update.updateHall();//更新车道，当检测到小车变道成功之后，只需要更新接收hall的霍尔
	}

	
	public int getChangeHall(long carId) throws Exception{
		
		
		DaoImpl dao = new DaoImpl();		
		//根据mac找小车id的distance，处理distance对应变道后的distance，然后根据处理后的distance去找hall,此时的hall,是变道的hall，还没有更新，channel也还没有更新
		List<CarState> list = dao.getDistanceByCarId(carId);
		
		double distance = list.get(0).getDrive_distance();
		long hall = list.get(0).getHistory_hall_count();
		if(list.get(0).getRoad_id() == 1){
			if(list.get(0).getChannel().equals("inside")){//只路过一个路口
				if(list.get(0).getHistory_hall_count()>16 && list.get(0).getHistory_hall_count()<=51){
					distance = distance + (148/180)*Math.PI*25;//只有一个路口
				}else if(list.get(0).getHistory_hall_count()>51 && list.get(0).getHistory_hall_count()<=84){
					//有两个路口
					distance = distance + (148/180)*Math.PI*25 + (135/180)*Math.PI*25;
				}else if(list.get(0).getHistory_hall_count()>84){
					//三个路口
					distance = distance + (148/180)*Math.PI*25 + (135/180)*Math.PI*25 + (90/180)*Math.PI*25;
				}
				//根据distance查霍尔
				distance = distance + list.get(0).getSpeed()*0.2;
				hall = dao.getHallByDistance(1,"outside",distance);
			}else if(list.get(0).getChannel().equals("outside")){
				if(list.get(0).getHistory_hall_count()>12 && list.get(0).getHistory_hall_count()<=39){
					distance = distance - (148/180)*Math.PI*25;//只有一个路口
				}else if(list.get(0).getHistory_hall_count()>39 && list.get(0).getHistory_hall_count()<=74){
					//有两个路口
					distance = distance - (148/180)*Math.PI*25 - (135/180)*Math.PI*25;
				}else if(list.get(0).getHistory_hall_count()>74){
					//三个路口
					distance = distance - (148/180)*Math.PI*25 - (135/180)*Math.PI*25 - (90/180)*Math.PI*25;
				}
			}
			//根据distance查霍尔
			distance = distance + list.get(0).getSpeed()*0.2;
			hall = dao.getHallByDistance(1,"inside",distance);
		}else if(list.get(0).getRoad_id() == 2){
			if(list.get(0).getChannel().equals("inside")){//只路过一个路口
				if(list.get(0).getHistory_hall_count()>2 && list.get(0).getHistory_hall_count()<=30){
					distance = distance + (90/180)*Math.PI*25;//只有一个路口
				}else if(list.get(0).getHistory_hall_count()>30 && list.get(0).getHistory_hall_count()<=47){
					//有两个路口
					distance = distance + (90/180)*Math.PI*25 + (103/180)*Math.PI*25;
				}else if(list.get(0).getHistory_hall_count()>47 && list.get(0).getHistory_hall_count()<=62){
					//三个路口
					distance = distance + (90/180)*Math.PI*25 + (103/180)*Math.PI*25 + (79/180)*Math.PI*25;
				}else if(list.get(0).getHistory_hall_count()>62){
					distance = distance + (90/180)*Math.PI*25 + (103/180)*Math.PI*25 + (79/180)*Math.PI*25 + (55/180)*Math.PI*25;
				}
				//根据distance查霍尔
				distance = distance + list.get(0).getSpeed()*0.2;
				hall = dao.getHallByDistance(2,"outside",distance);
			}else if(list.get(0).getChannel().equals("outside")){
				if(list.get(0).getHistory_hall_count()>1 && list.get(0).getHistory_hall_count()<=25){
					distance = distance - (90/180)*Math.PI*25;//只有一个路口
				}else if(list.get(0).getHistory_hall_count()>25 && list.get(0).getHistory_hall_count()<=38){
					//有两个路口
					distance = distance - (90/180)*Math.PI*25 - (103/180)*Math.PI*25;
				}else if(list.get(0).getHistory_hall_count()>38 && list.get(0).getHistory_hall_count()<=52){
					//三个路口
					distance = distance - (90/180)*Math.PI*25 - (103/180)*Math.PI*25 - (79/180)*Math.PI*25;
				}else if(list.get(0).getHistory_hall_count()>52){
					distance = distance - (90/180)*Math.PI*25 - (103/180)*Math.PI*25 - (79/180)*Math.PI*25 - (55/180)*Math.PI*25;
				}
			}
			//根据distance查霍尔
			distance = distance + list.get(0).getSpeed()*0.2;//这是上一次的hall，所以加个时间差
			hall = dao.getHallByDistance(2,"inside",distance);
		}
		
		
		return (int) hall;
		
	}
	
	
//	public void updateHall() throws Exception {
//		// TODO Auto-generated method stub
//		DaoImpl dao = new DaoImpl();
//	    List<CarState> list = new ArrayList<CarState>();
//	    
//	   // int time =517481;
//	    list = dao.getCarInfo();
//	    
//	    for(int i = 0;i<list.size();i++){
//	    	if(list.get(i).isChange_success()){
//	    		//System.out.println(list.get(i).getChannel());
//	    		//需要一个霍尔判断函数
//	    		
//	    		long hall = list.get(i).getHistory_hall_count()/2 +1;
//    			hall = judgeLocation(list.get(i).getCar_id(),list.get(i).getGroup_id());
//    			dao.updateHall(list.get(i).getCar_id(),list.get(i).getId(),hall);
//	    		
////	    		if(list.get(i).getChannel().equals("inside")){
////	    			
////	    			long hall = list.get(i).getHistory_hall_count()/2 +1;
////	    			hall = judgeLocation(list.get(i).getCar_id(),list.get(i).getGroup_id());
////	    			dao.updateHall(list.get(i).getCar_id(),list.get(i).getId(),hall);
////	    			//,"outside"
////	    		}
////	    		else{
////	    			long hall = list.get(i).getHistory_hall_count()*2;
////	    			dao.updateHall(list.get(i).getCar_id(),list.get(i).getId(),hall);	
////	    			//,"inside"
////	    		}
//	    	}
//	    }
//	}
//
//	private long judgeLocation(long carId,long groupId) throws Exception {
//		// TODO Auto-generated method stub
//		//通过小车id来判断小车变道后的位置
//		long hall=0;
//		DaoImpl dao = new DaoImpl();
//		List<CarState> list = new ArrayList<CarState>();
//		list = dao.getCarInfoByGroupId(groupId);
//		List<CarState> list1 = new ArrayList<CarState>();
//		list1 = dao.getCarInfoById(carId);
//		
//		double distance=0;
//		
//		if(list1.get(0).getChannel().equals("inside")){
//			for(int j = 0;j<list.size();j++){
//				if(list.get(j).getChannel().equals("outside")){
//					distance = Math.abs(list.get(0).getDrive_distance() - list1.get(0).getDrive_distance());
//					break;
//				}
//			}
//		}else if(list1.get(0).getChannel().equals("outside")){
//			for(int j = 0;j<list.size();j++){
//				if(list.get(j).getChannel().equals("inside")){
//					distance = Math.abs(list.get(0).getDrive_distance() - list1.get(0).getDrive_distance());
//					break;
//				}
//			}
//		}
//		
//		
//		//第一个值作为起点，找到最小值
//		for(int i = 0;i<list.size();i++){
//			if(list.get(i).getCar_id() != carId && !list.get(i).getChannel().equals(list1.get(0).getChannel())){
//				double index = list.get(i).getDrive_distance() - list1.get(0).getDrive_distance();
//				if(distance < Math.abs(index)){
//					distance = index;
//					hall = list.get(i).getHistory_hall_count();
//				}
//			}
//		}
//		
//		return hall;
//	}
}
