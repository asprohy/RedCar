package com.common;
import java.util.ArrayList;
import java.util.List;



import com.car.dao.DaoImpl;
import com.entity.CarBasicInfo;
import com.entity.CarState;
import com.orderSend.OrderQueue;

import connect.IXbeeMessageConnect;
import connect.impl.XbeeMessageConnect;
import connect.para.SendEndPointPara;



public class Avoid{

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		Avoid avoid = new Avoid();
		avoid.JudgeAvoid();//小车车队行驶过程中如果在交叉路口遇见，进行避让处理，返回避让的小车队编号
		//avoid.avoidCarGruop(fleetId);
	}

	
	public void JudgeAvoid() throws Exception{
		DaoImpl dao = new DaoImpl();
		List<CarState> list = new ArrayList<CarState>();
		list = dao.getFirstInfo();//得到所有车队的车头
		
		if(list.size() == 0){
			return;
		}
		
		for(int i = 0;i < list.size() - 1;i++){
			if(list.get(i).getRoad_id() == 1){
				if(list.get(i).getChannel().equals("inside")){
					if(list.get(i).getHistory_hall_count() == 22){//1车道内道，判断是否到第一个十字路口
						for(int j = i+1;j<list.size();j++){
							//判断是否2车道的小车（可以使内道也可以是外道）也到了这个路口
							if(list.get(j).getRoad_id() == 2){
								if(list.get(j).getChannel().equals("inside") && list.get(j).getHistory_hall_count() == 27){
									//调用车队避让程序
									avoidCarGruop(list.get(j).getGroup_id());
								}else if(list.get(j).getChannel().equals("outside") && list.get(j).getHistory_hall_count() == 22){
									avoidCarGruop(list.get(j).getGroup_id());
								}
							}
							
						}
					}else if(list.get(i).getHistory_hall_count() == 45){//1车道内道的第二个路口
						for(int j = i+1;j<list.size();j++){//判断是否2车道的小车也到了这个路口
							//判断是否2车道的小车（可以使内道也可以是外道）也到了这个路口
							if(list.get(j).getRoad_id() == 2){
								if(list.get(j).getChannel().equals("inside") && list.get(j).getHistory_hall_count() == 61){
									//调用车队避让程序
									avoidCarGruop(list.get(j).getGroup_id());
								}else if(list.get(j).getChannel().equals("outside") && list.get(j).getHistory_hall_count() == 50){
									avoidCarGruop(list.get(j).getGroup_id());
								}
							}
						}
					}
					
				}else if(list.get(i).getChannel().equals("outside")){
					if(list.get(i).getHistory_hall_count() == 18){//1车道外道，判断是否到第一个十字路口
						for(int j = i+1;j<list.size();j++){
							//判断是否2车道的小车（可以使内道也可以是外道）也到了这个路口
							if(list.get(j).getRoad_id() == 2){
								if(list.get(j).getChannel().equals("inside") && list.get(j).getHistory_hall_count() == 27){
									//调用车队避让程序
									avoidCarGruop(list.get(j).getGroup_id());
								}else if(list.get(j).getChannel().equals("outside") && list.get(j).getHistory_hall_count() == 22){
									avoidCarGruop(list.get(j).getGroup_id());
								}
							}
							
						}
					}else if(list.get(i).getHistory_hall_count() == 37){//1车道内道的第二个路口
						for(int j = i+1;j<list.size();j++){//判断是否2车道的小车也到了这个路口
							//判断是否2车道的小车（可以使内道也可以是外道）也到了这个路口
							if(list.get(j).getRoad_id() == 2){
								if(list.get(j).getChannel().equals("inside") && list.get(j).getHistory_hall_count() == 61){
									//调用车队避让程序
									avoidCarGruop(list.get(j).getGroup_id());
								}else if(list.get(j).getChannel().equals("outside") && list.get(j).getHistory_hall_count() == 50){
									avoidCarGruop(list.get(j).getGroup_id());
								}
							}
						}
					}
					
				}
				
			}else if(list.get(i).getRoad_id() == 2){
				if(list.get(i).getChannel().equals("inside")){
					if(list.get(i).getHistory_hall_count() == 27){//2车道内道，判断是否到第一个十字路口
						for(int j = i+1;j<list.size();j++){
							//判断是否1车道的小车（可以使内道也可以是外道）也到了这个路口
							if(list.get(j).getRoad_id() == 1){
								if(list.get(j).getChannel().equals("inside") && list.get(j).getHistory_hall_count() == 22){
									//调用车队避让程序
									avoidCarGruop(list.get(j).getGroup_id());
								}else if(list.get(j).getChannel().equals("outside") && list.get(j).getHistory_hall_count() == 18){
									avoidCarGruop(list.get(j).getGroup_id());
								}
							}
							
						}
					}else if(list.get(i).getHistory_hall_count() == 61){//2车道内道的第二个路口
						for(int j = i+1;j<list.size();j++){//判断是否1车道的小车也到了这个路口
							//判断是否1车道的小车（可以使内道也可以是外道）也到了这个路口
							if(list.get(j).getRoad_id() == 1){
								if(list.get(j).getChannel().equals("inside") && list.get(j).getHistory_hall_count() == 45){
									//调用车队避让程序
									avoidCarGruop(list.get(j).getGroup_id());
								}else if(list.get(j).getChannel().equals("outside") && list.get(j).getHistory_hall_count() == 37){
									avoidCarGruop(list.get(j).getGroup_id());
								}
							}
						}
					}
					
				}
					
				}else if(list.get(i).getChannel().equals("outside")){
					if(list.get(i).getHistory_hall_count() == 22){//2车道内道，判断是否到第一个十字路口
						for(int j = i+1;j<list.size();j++){
							//判断是否1车道的小车（可以使内道也可以是外道）也到了这个路口
							if(list.get(j).getRoad_id() == 1){
								if(list.get(j).getChannel().equals("inside") && list.get(j).getHistory_hall_count() == 22){
									//调用车队避让程序
									avoidCarGruop(list.get(j).getGroup_id());
								}else if(list.get(j).getChannel().equals("outside") && list.get(j).getHistory_hall_count() == 18){
									avoidCarGruop(list.get(j).getGroup_id());
								}
							}
							
						}
					}else if(list.get(i).getHistory_hall_count() == 50){//2车道内道的第二个路口
						for(int j = i+1;j<list.size();j++){//判断是否1车道的小车也到了这个路口
							//判断是否1车道的小车（可以使内道也可以是外道）也到了这个路口
							if(list.get(j).getRoad_id() == 1){
								if(list.get(j).getChannel().equals("inside") && list.get(j).getHistory_hall_count() == 45){
									//调用车队避让程序
									avoidCarGruop(list.get(j).getGroup_id());
								}else if(list.get(j).getChannel().equals("outside") && list.get(j).getHistory_hall_count() == 37){
									avoidCarGruop(list.get(j).getGroup_id());
								}
							}
						}
					}
					
				}
				
			}
		}
	
	
//	public void judge() throws Exception {
//		// TODO Auto-generated method stub
//		//int fleetId = 0;
//		DaoImpl dao = new DaoImpl();
//		List<CarState> list = new ArrayList<CarState>();
//		//List<SendEndPointPara> send = new ArrayList<SendEndPointPara>();
//		//List<CarState> list2 = new ArrayList<CarState>();//先假设只有两个车队
//		//List<CarState> list3 = new ArrayList<CarState>();
//		list = dao.getFirstInfo();//根据每个车队的头车来判断小车是否到达十字路口
//		//System.out.println(list.size());
//	
//		
//		for(int i = 0;i<list.size();i++){
//			if(list.get(i).getGroup_id() ==1){//如果在第一个车道，一车队在一号车道
//				if(list.get(i).getHistory_hall_count() == 10){//第一个路口
//					for(int j = i+1;j<list.size();j++){
//						if((list.get(j).getGroup_id()==2||list.get(j).getGroup_id()==3)
//								&& list.get(j).getHistory_hall_count() ==13){//不能是同一个车道的车队,看另一个车道是否到达
//							//System.out.println(list.get(j).getCar_id());//此处调用车队暂停命令
////							SendEndPointPara single = new SendEndPointPara();
////							single.setMacAddress(list.get(j)) 停整个车队
//							avoidCarGruop(list.get(i).getGroup_id());
//						}else
//							continue;
//						
//					}
//				}else if(list.get(i).getHistory_hall_count() == 15){//第二个路口
//					for(int j = i+1;j<list.size();j++){
//						if((list.get(j).getGroup_id()==2||list.get(j).getGroup_id()==3)
//								&& list.get(j).getHistory_hall_count() ==19){//不能是同一个车道的车队,看另一个车道是否到达
//							//System.out.println(list.get(j).getCar_id());//此处调用车队暂停命令
//						}else
//							continue;
//						
//					}
//				}
//				
//			}else if(list.get(i).getGroup_id()==2||list.get(i).getGroup_id()==3){//如果在第二个车道
//				
//				if(list.get(i).getHistory_hall_count() == 13){//第一个路口
//					for(int j = i+1;j<list.size();j++){//是否有第一车队的车到此路口
//						if(list.get(j).getGroup_id()==1
//								&& list.get(j).getHistory_hall_count() ==10){//不能是同一个车道的车队,看另一个车道是否到达
//							System.out.println(list.get(j).getCar_id());//此处调用车队暂停命令
//							
//						}else
//							continue;
//						
//					}
//				}else if(list.get(i).getHistory_hall_count() == 19){//第二个路口
//					for(int j = i+1;j<list.size();j++){
//						if(list.get(j).getGroup_id()==1
//								&& list.get(j).getHistory_hall_count() ==15){//不能是同一个车道的车队,看另一个车道是否到达
//							System.out.println(list.get(j).getCar_id());//此处调用车队暂停命令
//						}else
//							continue;
//						
//					}
//				}
//				
//			}
//		
//		}
//		//return fleetId;
//		avoidCarGruop(fleetId);
//	}

	public void avoidCarGruop(long groupId) throws Exception {//车队避让停车操作
		// TODO Auto-generated method stub
		List<SendEndPointPara> list = new ArrayList<SendEndPointPara>();
		SendEndPointPara send = new SendEndPointPara();
		//List<String> macAdr = new ArrayList<String>();
		List<CarBasicInfo> list1 = new ArrayList<CarBasicInfo>();
		
		
		DaoImpl dao = new DaoImpl();
		list1 = dao.getBasicInfoByGroupId(groupId);
		
		//macAdr = dao.getMacAddress(groupId);
		
		
		for(int i=0;i<list1.size();i++){
			send.setMacAddress(list1.get(i).getMac_adress());
			String carNumber = "0"+Long.valueOf(list1.get(i).getGroup_id()).toString() + "0" + Integer.valueOf(list1.get(i).getCar_num()).toString();
			send.setCarNumber(carNumber);
			send.setRoadId(Long.valueOf(list1.get(i).getRoad_id()).toString());
			send.setSpeed("0");
			send.setSwerveFlow("0");
			send.setSwerveAngle("0");
			send.setSwervePara("0");
			list.add(send);
			
		}
		OrderQueue orderQueue = new OrderQueue();
		orderQueue.joinCar1(list);
		//sendStop(list);
		
	}
	
//	public void sendStop(List<SendEndPointPara> list) {
//		IXbeeMessageConnect conn = new XbeeMessageConnect();
//		conn.openComPort("COM7", 9600);
//		conn.sendMessages(list);
//		conn.closeComPort();		
//	}

}
