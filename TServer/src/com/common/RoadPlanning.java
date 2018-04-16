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
	 * 全部小车无论走那里，堵车后就有规划，然后显示出来
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		long carId = 1;
		RoadPlanning roadP = new RoadPlanning();
		List<RoadPlan> road = roadP.roadPlan();//检查小车是否堵车，如果小车堵车在指定位置，可以给小车进行重新规划路线
		System.out.println(road.size());
		System.out.println(road.get(0));
		System.out.println(road.get(1));
	}

	public List<RoadPlan> roadPlan() throws Exception {
		// TODO Auto-generated method stub
		List<CarWarnInfo> list = new ArrayList<CarWarnInfo>();
		DaoImpl dao = new DaoImpl();
		//list = dao.getAllCarWarnInfo();
		list = dao.getAllCongestionCar();//得到所有拥堵的小车
//		List<CarState> list1 = dao.getCarInfoById(carId);//得到小车的行驶信息
//		//List<CarState> list2 = dao.getCarInfo();
//		for(int i = 0;i<list.size();i++){
//			long carId1 = list.get(i).getCar_id();
//			list1 = dao.getCarInfoById(carId1);//得到堵车行驶信息
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
				
				if(list1.get(0).getChannel().equals("inside") && list.get(i).getRoad_id()==1){//内道，一车道
					if(list1.get(0).getHistory_hall_count() >= 19 && list1.get(0).getHistory_hall_count() <= 32){
						//return 1;//第一个可能堵车
						for(int m = 0;m<roadList.size();m++){
							if(roadList.get(m).getPlanId() == 1){
								break;
							}
						}
						
						road.setPlanId(1);
						roadList.add(road);
						
					}else if(list1.get(0).getHistory_hall_count() >= 33 && list1.get(0).getHistory_hall_count() <= 39){
						//return 2;//第二个可能堵车
						for(int m = 0;m<roadList.size();m++){
							if(roadList.get(m).getPlanId() == 2){
								break;
							}
						}
						road.setPlanId(2);
						roadList.add(road);
					}
				}else if(list1.get(0).getChannel().equals("outside") && list.get(i).getRoad_id()==1){//外道，一车道
					if(list1.get(0).getHistory_hall_count() >= 11 && list1.get(0).getHistory_hall_count() <= 17){
						//return 1;//第一个可能堵车
						for(int m = 0;m<roadList.size();m++){
							if(roadList.get(m).getPlanId() == 1){
								break;
							}
						}
						road.setPlanId(1);
						roadList.add(road);
					}else if(list1.get(0).getHistory_hall_count() >= 30 && list1.get(0).getHistory_hall_count() <= 40){
						//return 2;//第二个可能堵车
						for(int m = 0;m<roadList.size();m++){
							if(roadList.get(m).getPlanId() == 2){
								break;
							}
						}
						//roadList.add(2);
						road.setPlanId(2);
						roadList.add(road);
					}
				}else if(list1.get(0).getChannel().equals("inside") && list.get(i).getRoad_id()==2){//内道，二车道
					if(list1.get(0).getHistory_hall_count() >= 25 && list1.get(0).getHistory_hall_count() <= 35){
						//return 3;//第三个可能堵车
						for(int m = 0;m<roadList.size();m++){
							if(roadList.get(m).getPlanId() == 3){
								break;
							}
						}
						//roadList.add(3);
						road.setPlanId(3);
						roadList.add(road);
					}else if(list1.get(0).getHistory_hall_count() >= 43 && list1.get(0).getHistory_hall_count() <= 60){
						//return 4;//第四个可能堵车
						for(int m = 0;m<roadList.size();m++){
							if(roadList.get(m).getPlanId() == 4){
								break;
							}
						}
						//roadList.add(4);
						road.setPlanId(4);
						roadList.add(road);
					}
				}else if(list1.get(0).getChannel().equals("outside") && list.get(i).getRoad_id()==2){//内道，二车道
					if(list1.get(0).getHistory_hall_count() >= 15 && list1.get(0).getHistory_hall_count() <= 28){
						//return 3;//第三个可能堵车
						for(int m = 0;m<roadList.size();m++){
							if(roadList.get(m).getPlanId() == 3){
								break;
							}
						}
						//roadList.add(3);
						road.setPlanId(3);
						roadList.add(road);
					}else if(list1.get(0).getHistory_hall_count() >= 33 && list1.get(0).getHistory_hall_count() <= 45){
						//return 4;//第四个可能堵车
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
//				if(list.get(i).getRoad()!=0){//如果没有堵车，把规划还原
//					dao.restoreRoad(list.get(i).getCar_id());//舍弃
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
