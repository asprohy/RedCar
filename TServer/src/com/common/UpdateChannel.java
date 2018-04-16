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
		//update.updateHall();//���³���������⵽С������ɹ�֮��ֻ��Ҫ���½���hall�Ļ���
	}

	
	public int getChangeHall(long carId) throws Exception{
		
		
		DaoImpl dao = new DaoImpl();		
		//����mac��С��id��distance������distance��Ӧ������distance��Ȼ����ݴ�����distanceȥ��hall,��ʱ��hall,�Ǳ����hall����û�и��£�channelҲ��û�и���
		List<CarState> list = dao.getDistanceByCarId(carId);
		
		double distance = list.get(0).getDrive_distance();
		long hall = list.get(0).getHistory_hall_count();
		if(list.get(0).getRoad_id() == 1){
			if(list.get(0).getChannel().equals("inside")){//ֻ·��һ��·��
				if(list.get(0).getHistory_hall_count()>16 && list.get(0).getHistory_hall_count()<=51){
					distance = distance + (148/180)*Math.PI*25;//ֻ��һ��·��
				}else if(list.get(0).getHistory_hall_count()>51 && list.get(0).getHistory_hall_count()<=84){
					//������·��
					distance = distance + (148/180)*Math.PI*25 + (135/180)*Math.PI*25;
				}else if(list.get(0).getHistory_hall_count()>84){
					//����·��
					distance = distance + (148/180)*Math.PI*25 + (135/180)*Math.PI*25 + (90/180)*Math.PI*25;
				}
				//����distance�����
				distance = distance + list.get(0).getSpeed()*0.2;
				hall = dao.getHallByDistance(1,"outside",distance);
			}else if(list.get(0).getChannel().equals("outside")){
				if(list.get(0).getHistory_hall_count()>12 && list.get(0).getHistory_hall_count()<=39){
					distance = distance - (148/180)*Math.PI*25;//ֻ��һ��·��
				}else if(list.get(0).getHistory_hall_count()>39 && list.get(0).getHistory_hall_count()<=74){
					//������·��
					distance = distance - (148/180)*Math.PI*25 - (135/180)*Math.PI*25;
				}else if(list.get(0).getHistory_hall_count()>74){
					//����·��
					distance = distance - (148/180)*Math.PI*25 - (135/180)*Math.PI*25 - (90/180)*Math.PI*25;
				}
			}
			//����distance�����
			distance = distance + list.get(0).getSpeed()*0.2;
			hall = dao.getHallByDistance(1,"inside",distance);
		}else if(list.get(0).getRoad_id() == 2){
			if(list.get(0).getChannel().equals("inside")){//ֻ·��һ��·��
				if(list.get(0).getHistory_hall_count()>2 && list.get(0).getHistory_hall_count()<=30){
					distance = distance + (90/180)*Math.PI*25;//ֻ��һ��·��
				}else if(list.get(0).getHistory_hall_count()>30 && list.get(0).getHistory_hall_count()<=47){
					//������·��
					distance = distance + (90/180)*Math.PI*25 + (103/180)*Math.PI*25;
				}else if(list.get(0).getHistory_hall_count()>47 && list.get(0).getHistory_hall_count()<=62){
					//����·��
					distance = distance + (90/180)*Math.PI*25 + (103/180)*Math.PI*25 + (79/180)*Math.PI*25;
				}else if(list.get(0).getHistory_hall_count()>62){
					distance = distance + (90/180)*Math.PI*25 + (103/180)*Math.PI*25 + (79/180)*Math.PI*25 + (55/180)*Math.PI*25;
				}
				//����distance�����
				distance = distance + list.get(0).getSpeed()*0.2;
				hall = dao.getHallByDistance(2,"outside",distance);
			}else if(list.get(0).getChannel().equals("outside")){
				if(list.get(0).getHistory_hall_count()>1 && list.get(0).getHistory_hall_count()<=25){
					distance = distance - (90/180)*Math.PI*25;//ֻ��һ��·��
				}else if(list.get(0).getHistory_hall_count()>25 && list.get(0).getHistory_hall_count()<=38){
					//������·��
					distance = distance - (90/180)*Math.PI*25 - (103/180)*Math.PI*25;
				}else if(list.get(0).getHistory_hall_count()>38 && list.get(0).getHistory_hall_count()<=52){
					//����·��
					distance = distance - (90/180)*Math.PI*25 - (103/180)*Math.PI*25 - (79/180)*Math.PI*25;
				}else if(list.get(0).getHistory_hall_count()>52){
					distance = distance - (90/180)*Math.PI*25 - (103/180)*Math.PI*25 - (79/180)*Math.PI*25 - (55/180)*Math.PI*25;
				}
			}
			//����distance�����
			distance = distance + list.get(0).getSpeed()*0.2;//������һ�ε�hall�����ԼӸ�ʱ���
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
//	    		//��Ҫһ�������жϺ���
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
//		//ͨ��С��id���ж�С��������λ��
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
//		//��һ��ֵ��Ϊ��㣬�ҵ���Сֵ
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
