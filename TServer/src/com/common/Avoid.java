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
		avoid.JudgeAvoid();//С��������ʻ����������ڽ���·�����������б��ô������ر��õ�С���ӱ��
		//avoid.avoidCarGruop(fleetId);
	}

	
	public void JudgeAvoid() throws Exception{
		DaoImpl dao = new DaoImpl();
		List<CarState> list = new ArrayList<CarState>();
		list = dao.getFirstInfo();//�õ����г��ӵĳ�ͷ
		
		if(list.size() == 0){
			return;
		}
		
		for(int i = 0;i < list.size() - 1;i++){
			if(list.get(i).getRoad_id() == 1){
				if(list.get(i).getChannel().equals("inside")){
					if(list.get(i).getHistory_hall_count() == 22){//1�����ڵ����ж��Ƿ񵽵�һ��ʮ��·��
						for(int j = i+1;j<list.size();j++){
							//�ж��Ƿ�2������С��������ʹ�ڵ�Ҳ�����������Ҳ�������·��
							if(list.get(j).getRoad_id() == 2){
								if(list.get(j).getChannel().equals("inside") && list.get(j).getHistory_hall_count() == 27){
									//���ó��ӱ��ó���
									avoidCarGruop(list.get(j).getGroup_id());
								}else if(list.get(j).getChannel().equals("outside") && list.get(j).getHistory_hall_count() == 22){
									avoidCarGruop(list.get(j).getGroup_id());
								}
							}
							
						}
					}else if(list.get(i).getHistory_hall_count() == 45){//1�����ڵ��ĵڶ���·��
						for(int j = i+1;j<list.size();j++){//�ж��Ƿ�2������С��Ҳ�������·��
							//�ж��Ƿ�2������С��������ʹ�ڵ�Ҳ�����������Ҳ�������·��
							if(list.get(j).getRoad_id() == 2){
								if(list.get(j).getChannel().equals("inside") && list.get(j).getHistory_hall_count() == 61){
									//���ó��ӱ��ó���
									avoidCarGruop(list.get(j).getGroup_id());
								}else if(list.get(j).getChannel().equals("outside") && list.get(j).getHistory_hall_count() == 50){
									avoidCarGruop(list.get(j).getGroup_id());
								}
							}
						}
					}
					
				}else if(list.get(i).getChannel().equals("outside")){
					if(list.get(i).getHistory_hall_count() == 18){//1����������ж��Ƿ񵽵�һ��ʮ��·��
						for(int j = i+1;j<list.size();j++){
							//�ж��Ƿ�2������С��������ʹ�ڵ�Ҳ�����������Ҳ�������·��
							if(list.get(j).getRoad_id() == 2){
								if(list.get(j).getChannel().equals("inside") && list.get(j).getHistory_hall_count() == 27){
									//���ó��ӱ��ó���
									avoidCarGruop(list.get(j).getGroup_id());
								}else if(list.get(j).getChannel().equals("outside") && list.get(j).getHistory_hall_count() == 22){
									avoidCarGruop(list.get(j).getGroup_id());
								}
							}
							
						}
					}else if(list.get(i).getHistory_hall_count() == 37){//1�����ڵ��ĵڶ���·��
						for(int j = i+1;j<list.size();j++){//�ж��Ƿ�2������С��Ҳ�������·��
							//�ж��Ƿ�2������С��������ʹ�ڵ�Ҳ�����������Ҳ�������·��
							if(list.get(j).getRoad_id() == 2){
								if(list.get(j).getChannel().equals("inside") && list.get(j).getHistory_hall_count() == 61){
									//���ó��ӱ��ó���
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
					if(list.get(i).getHistory_hall_count() == 27){//2�����ڵ����ж��Ƿ񵽵�һ��ʮ��·��
						for(int j = i+1;j<list.size();j++){
							//�ж��Ƿ�1������С��������ʹ�ڵ�Ҳ�����������Ҳ�������·��
							if(list.get(j).getRoad_id() == 1){
								if(list.get(j).getChannel().equals("inside") && list.get(j).getHistory_hall_count() == 22){
									//���ó��ӱ��ó���
									avoidCarGruop(list.get(j).getGroup_id());
								}else if(list.get(j).getChannel().equals("outside") && list.get(j).getHistory_hall_count() == 18){
									avoidCarGruop(list.get(j).getGroup_id());
								}
							}
							
						}
					}else if(list.get(i).getHistory_hall_count() == 61){//2�����ڵ��ĵڶ���·��
						for(int j = i+1;j<list.size();j++){//�ж��Ƿ�1������С��Ҳ�������·��
							//�ж��Ƿ�1������С��������ʹ�ڵ�Ҳ�����������Ҳ�������·��
							if(list.get(j).getRoad_id() == 1){
								if(list.get(j).getChannel().equals("inside") && list.get(j).getHistory_hall_count() == 45){
									//���ó��ӱ��ó���
									avoidCarGruop(list.get(j).getGroup_id());
								}else if(list.get(j).getChannel().equals("outside") && list.get(j).getHistory_hall_count() == 37){
									avoidCarGruop(list.get(j).getGroup_id());
								}
							}
						}
					}
					
				}
					
				}else if(list.get(i).getChannel().equals("outside")){
					if(list.get(i).getHistory_hall_count() == 22){//2�����ڵ����ж��Ƿ񵽵�һ��ʮ��·��
						for(int j = i+1;j<list.size();j++){
							//�ж��Ƿ�1������С��������ʹ�ڵ�Ҳ�����������Ҳ�������·��
							if(list.get(j).getRoad_id() == 1){
								if(list.get(j).getChannel().equals("inside") && list.get(j).getHistory_hall_count() == 22){
									//���ó��ӱ��ó���
									avoidCarGruop(list.get(j).getGroup_id());
								}else if(list.get(j).getChannel().equals("outside") && list.get(j).getHistory_hall_count() == 18){
									avoidCarGruop(list.get(j).getGroup_id());
								}
							}
							
						}
					}else if(list.get(i).getHistory_hall_count() == 50){//2�����ڵ��ĵڶ���·��
						for(int j = i+1;j<list.size();j++){//�ж��Ƿ�1������С��Ҳ�������·��
							//�ж��Ƿ�1������С��������ʹ�ڵ�Ҳ�����������Ҳ�������·��
							if(list.get(j).getRoad_id() == 1){
								if(list.get(j).getChannel().equals("inside") && list.get(j).getHistory_hall_count() == 45){
									//���ó��ӱ��ó���
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
//		//List<CarState> list2 = new ArrayList<CarState>();//�ȼ���ֻ����������
//		//List<CarState> list3 = new ArrayList<CarState>();
//		list = dao.getFirstInfo();//����ÿ�����ӵ�ͷ�����ж�С���Ƿ񵽴�ʮ��·��
//		//System.out.println(list.size());
//	
//		
//		for(int i = 0;i<list.size();i++){
//			if(list.get(i).getGroup_id() ==1){//����ڵ�һ��������һ������һ�ų���
//				if(list.get(i).getHistory_hall_count() == 10){//��һ��·��
//					for(int j = i+1;j<list.size();j++){
//						if((list.get(j).getGroup_id()==2||list.get(j).getGroup_id()==3)
//								&& list.get(j).getHistory_hall_count() ==13){//������ͬһ�������ĳ���,����һ�������Ƿ񵽴�
//							//System.out.println(list.get(j).getCar_id());//�˴����ó�����ͣ����
////							SendEndPointPara single = new SendEndPointPara();
////							single.setMacAddress(list.get(j)) ͣ��������
//							avoidCarGruop(list.get(i).getGroup_id());
//						}else
//							continue;
//						
//					}
//				}else if(list.get(i).getHistory_hall_count() == 15){//�ڶ���·��
//					for(int j = i+1;j<list.size();j++){
//						if((list.get(j).getGroup_id()==2||list.get(j).getGroup_id()==3)
//								&& list.get(j).getHistory_hall_count() ==19){//������ͬһ�������ĳ���,����һ�������Ƿ񵽴�
//							//System.out.println(list.get(j).getCar_id());//�˴����ó�����ͣ����
//						}else
//							continue;
//						
//					}
//				}
//				
//			}else if(list.get(i).getGroup_id()==2||list.get(i).getGroup_id()==3){//����ڵڶ�������
//				
//				if(list.get(i).getHistory_hall_count() == 13){//��һ��·��
//					for(int j = i+1;j<list.size();j++){//�Ƿ��е�һ���ӵĳ�����·��
//						if(list.get(j).getGroup_id()==1
//								&& list.get(j).getHistory_hall_count() ==10){//������ͬһ�������ĳ���,����һ�������Ƿ񵽴�
//							System.out.println(list.get(j).getCar_id());//�˴����ó�����ͣ����
//							
//						}else
//							continue;
//						
//					}
//				}else if(list.get(i).getHistory_hall_count() == 19){//�ڶ���·��
//					for(int j = i+1;j<list.size();j++){
//						if(list.get(j).getGroup_id()==1
//								&& list.get(j).getHistory_hall_count() ==15){//������ͬһ�������ĳ���,����һ�������Ƿ񵽴�
//							System.out.println(list.get(j).getCar_id());//�˴����ó�����ͣ����
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

	public void avoidCarGruop(long groupId) throws Exception {//���ӱ���ͣ������
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
