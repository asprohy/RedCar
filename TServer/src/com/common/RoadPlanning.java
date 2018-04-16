package com.common;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.car.dao.DaoImpl;
import com.entity.CarState;
import com.entity.CarWarnInfo;
import com.map.RoadPlan;


public class RoadPlanning {

	/**
	 * @param args
	 * @throws Exception 
	 * ȫ��С������������³�����й滮��Ȼ����ʾ����
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		long carId = 1;
		RoadPlanning roadP = new RoadPlanning();
		List<RoadPlan> road = roadP.roadPlan();//���С���Ƿ�³������С���³���ָ��λ�ã����Ը�С���������¹滮·��
		System.out.println(road.size());
		System.out.println(road.get(0));
		System.out.println(road.get(1));
	}

	public List<RoadPlan> roadPlan() throws Exception {
		// TODO Auto-generated method stub
		List<CarWarnInfo> list = new ArrayList<CarWarnInfo>();
		DaoImpl dao = new DaoImpl();
		//list = dao.getAllCarWarnInfo();
		list = dao.getAllCongestionCar();//�õ�����ӵ�µ�С��
//		List<CarState> list1 = dao.getCarInfoById(carId);//�õ�С������ʻ��Ϣ
//		//List<CarState> list2 = dao.getCarInfo();
//		for(int i = 0;i<list.size();i++){
//			long carId1 = list.get(i).getCar_id();
//			list1 = dao.getCarInfoById(carId1);//�õ��³���ʻ��Ϣ
//			
//		}
//		if(list1.get(0).getChannel().equals("inside") && list1.get(0).getRoad_id() == 1){
//			
//		}
		List<RoadPlan> roadList = new ArrayList<RoadPlan>();
		RoadPlan road = new RoadPlan();
		for(int i =0;i<list.size();i++){
			if(list.get(i).isCongestion()){
				long carId = list.get(i).getCar_id();
				List<CarState> list1 = new ArrayList<CarState>();
				list1 = dao.getCarInfoById(carId);
				
				if(list1.size()==0){
					break;
				}
				
				if(list1.get(0).getChannel().equals("inside") && list.get(i).getRoad_id()==1){//�ڵ���һ����
					if(list1.get(0).getHistory_hall_count() >= 19 && list1.get(0).getHistory_hall_count() <= 32){
						//return 1;//��һ�����ܶ³�
						for(int m = 0;m<roadList.size();m++){
							if(roadList.get(m).getPlanId() == 1){
								break;
							}
						}
						
						road.setPlanId(1);
						roadList.add(road);
						
					}else if(list1.get(0).getHistory_hall_count() >= 33 && list1.get(0).getHistory_hall_count() <= 39){
						//return 2;//�ڶ������ܶ³�
						for(int m = 0;m<roadList.size();m++){
							if(roadList.get(m).getPlanId() == 2){
								break;
							}
						}
						road.setPlanId(2);
						roadList.add(road);
					}
				}else if(list1.get(0).getChannel().equals("outside") && list.get(i).getRoad_id()==1){//�����һ����
					if(list1.get(0).getHistory_hall_count() >= 11 && list1.get(0).getHistory_hall_count() <= 17){
						//return 1;//��һ�����ܶ³�
						for(int m = 0;m<roadList.size();m++){
							if(roadList.get(m).getPlanId() == 1){
								break;
							}
						}
						road.setPlanId(1);
						roadList.add(road);
					}else if(list1.get(0).getHistory_hall_count() >= 30 && list1.get(0).getHistory_hall_count() <= 40){
						//return 2;//�ڶ������ܶ³�
						for(int m = 0;m<roadList.size();m++){
							if(roadList.get(m).getPlanId() == 2){
								break;
							}
						}
						//roadList.add(2);
						road.setPlanId(2);
						roadList.add(road);
					}
				}else if(list1.get(0).getChannel().equals("inside") && list.get(i).getRoad_id()==2){//�ڵ���������
					if(list1.get(0).getHistory_hall_count() >= 25 && list1.get(0).getHistory_hall_count() <= 35){
						//return 3;//���������ܶ³�
						for(int m = 0;m<roadList.size();m++){
							if(roadList.get(m).getPlanId() == 3){
								break;
							}
						}
						//roadList.add(3);
						road.setPlanId(3);
						roadList.add(road);
					}else if(list1.get(0).getHistory_hall_count() >= 43 && list1.get(0).getHistory_hall_count() <= 60){
						//return 4;//���ĸ����ܶ³�
						for(int m = 0;m<roadList.size();m++){
							if(roadList.get(m).getPlanId() == 4){
								break;
							}
						}
						//roadList.add(4);
						road.setPlanId(4);
						roadList.add(road);
					}
				}else if(list1.get(0).getChannel().equals("outside") && list.get(i).getRoad_id()==2){//�ڵ���������
					if(list1.get(0).getHistory_hall_count() >= 15 && list1.get(0).getHistory_hall_count() <= 28){
						//return 3;//���������ܶ³�
						for(int m = 0;m<roadList.size();m++){
							if(roadList.get(m).getPlanId() == 3){
								break;
							}
						}
						//roadList.add(3);
						road.setPlanId(3);
						roadList.add(road);
					}else if(list1.get(0).getHistory_hall_count() >= 33 && list1.get(0).getHistory_hall_count() <= 45){
						//return 4;//���ĸ����ܶ³�
						for(int m = 0;m<roadList.size();m++){
							if(roadList.get(m).getPlanId() == 4){
								break;
							}
						}
						//roadList.add(4);
						road.setPlanId(4);
						roadList.add(road);
					}
				}
				
			}
//			else{
//				if(list.get(i).getRoad()!=0){//���û�ж³����ѹ滮��ԭ
//					dao.restoreRoad(list.get(i).getCar_id());//����
//				}
//			}
				
				
		}
		if(roadList.size()==0){
			//roadList.add(0);
			road.setPlanId(0);
			roadList.add(road);
		}
		
		return roadList;
	}

}
