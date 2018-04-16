package com.common;

import java.util.ArrayList;
import java.util.List;

import com.car.dao.DaoImpl;
import com.entity.CarBasicInfo;
import com.entity.CarState;
import com.entity.CarWarnInfo;
import com.entity.OrderInfo;
import com.orderSend.OrderQueue;

import connect.para.SendEndPointPara;



public class JudgeGroupDistance{
	
	private static final double[] THEWHOLELENGTH = {15.7 , 1000 , 1000 , 1000 , 1000 , 1000 };
	private static final double TIMES = 3.0;
	
	public static void main(String[] args) throws Exception {
		
		
		
	}
	
	public int allGroupDistance(int groupId)throws Exception{
		//state 0���䶯;
		//		*1β���������(�󳵶ӵ�ͷ��)
		//		*2β�������Զ
		
		int state = 0;
		if(groupId==1)
		{
			return 0;//һ�ų��Ӳ�����
		}
		
		JudgeGroupDistance judge = new JudgeGroupDistance();
		List<Long> allGroupId = new ArrayList<Long>();
		DaoImpl dao = new DaoImpl();
		
		List<CarWarnInfo> listStop = new ArrayList<CarWarnInfo>();
		List<Long> listHead = new ArrayList<Long>();
		allGroupId = dao.getAllGroupId();
		listHead = dao.getAllRoadHeadCarId();//iΪroad_id
		int headId = 0;
		
		headId=dao.getHeadCarId(groupId).intValue();
		
			listStop = dao.getStopInfo(headId);
			if (listStop.size() == 0 || !listStop.get(0).isStop()) {
				state = judge.judge(groupId,headId);
			}

		
		return state;
	}
	
	public int judge(int groupId, int headCarId) throws Exception{
		
		DaoImpl dao = new DaoImpl();
		List<Long> listTail = new ArrayList<Long>();
		//int roadId=0;
		
		
		listTail = dao.getAllTailCarId(dao.getBasicInfoById(headCarId).get(0).getRoad_id());//ͬһ��road_id
		CarState tailCar = new CarState();
		CarState headCar = new CarState();
		headCar = dao.getCarInfoById(headCarId).get(0);
		
		double min = 0;
		long hallDex = 0;
		
		for(int i =0;i<listTail.size();i++)
		{
			tailCar = dao.getCarInfoById(listTail.get(i)).get(0);
			if(tailCar.getGroup_id() != groupId && tailCar.getChannel().equals(headCar.getChannel())){
				min = tailCar.getDrive_distance() - headCar.getDrive_distance();
				hallDex = tailCar.getHistory_hall_count() - headCar.getHistory_hall_count();
				
				if(min > 0 && hallDex > 0)
				{
					break;
				}
				
				if(hallDex <-30)
				{
					min = tailCar.getDrive_distance() - headCar.getDrive_distance() + THEWHOLELENGTH[0];//��·2
				}
				
			}
		}
		
		if(min<=0)
		{
			return 0;
		}
		else
		{
			for (int k = 0; k < listTail.size(); k++) {// ���������ǰ�����ҵ��Լ�����������ĳ�
				tailCar = dao.getCarInfoById(listTail.get(k)).get(0);
					double index = tailCar.getDrive_distance() - headCar.getDrive_distance();
					if(hallDex <-30)
					{
						index = tailCar.getDrive_distance() - headCar.getDrive_distance() + THEWHOLELENGTH[0];//��·2
					}
					
					if (tailCar.getCar_id() != headCarId && index > 0 && index < min) {
						min = index;
					}
			}
		}
		
		List<OrderInfo> list1 = dao.getOrderInfoByGroupId(groupId);
		
		if (list1.size() == 0) {
			return 0;
		}
		
		double distance = list1.get(0).getDistance();// С���������õľ���
		int speed = list1.get(0).getSpeed();
		List<CarBasicInfo> list2 = dao.getBasicInfoById(headCarId);

		if (list2.size() == 0) {
			return 0;
		}
		
		long roadId2 = list2.get(0).getRoad_id();
		String macAddress = list2.get(0).getMac_adress();
		int carNum = list2.get(0).getCar_num();// ���ڱ��
		String carNumber = "0" + Long.valueOf(groupId).toString() + "0" + Integer.valueOf(carNum).toString();// �Ӻ�+���ڱ��

		SendEndPointPara singleOutput = new SendEndPointPara();
		
		if ((min - 0.32 <= distance * 2 * TIMES)) {
			double speed1 = 0;
			boolean needJudgeDistance = false;
			if (carNumber.equals("0101")) {
				if (headCar.getHistory_hall_count() == 25 || headCar.getHistory_hall_count() == 26) {
					needJudgeDistance = true;
					if ((min - 0.32) < distance * 1) {
						speed1 = speed * 0;
					}
				}
			}
			if (!needJudgeDistance) {
				if ((min - 0.32) < distance * 2 && (min - 0.32) >= distance * 1 * TIMES ) {
					speed1 = speed * 0.9;
				} else if ((min - 0.32) < distance * 1 && (min - 0.32) > distance * 0.5 * TIMES ) {
					speed1 = speed * 0.85;
				} else if ((min - 0.32) <= distance * 0.5 * TIMES ) {
					speed1 = speed * 0.8;
				}
			}
			// �������С��������80%
			System.out.println("������������ʼ����");
			System.out.println("---------------------------------");
			System.out.println("carNumber:" + carNumber);
			System.out.println("carSpeed:" + speed1);
			System.out.println("hoare:" + headCar.getHistory_hall_count());
			System.out.println("---------------------------------");
			singleOutput.setMacAddress(macAddress);
			singleOutput.setCarNumber(carNumber);

			singleOutput.setSpeed(Double.valueOf(speed1).toString());
			singleOutput.setSwerveAngle("0");
			singleOutput.setSwervePara("0");
			singleOutput.setSwerveFlow("0");
			singleOutput.setRoadId(Long.valueOf(roadId2).toString());
			singleOutput.setConctrolOrder(false);

			// ��������
			OrderQueue orderQueue = new OrderQueue();
			orderQueue.joinCar1(singleOutput);
			// sendToCar(singleOutput);
			if (carNumber.equals("0102"))
				System.out.println("carNumber---------------------------------------" + carNumber);
			return 1;
			// return 1;//�������,����20%�ٶ�

		} else if ((min - 0.32 > distance * 3 * TIMES)) {
			double speed1 = 0;
			if ((min - 0.32) <= distance * 4 * TIMES && (min - 0.32) > distance * 3 * TIMES) {
				speed1 = speed * 1.05;
			} else if ((min - 0.32) > distance * 4 * TIMES) {
				speed1 = speed * 1.1;
			}
			// ����������20%������20%����
			System.out.println("������Զ����ʼ����");
			System.out.println("---------------------------------");
			System.out.println("carNumber:" + carNumber);
			System.out.println("carSpeed:" + speed1);
			System.out.println("---------------------------------");
			singleOutput.setMacAddress(macAddress);
			singleOutput.setCarNumber(carNumber);

			singleOutput.setSpeed(Double.valueOf(speed1).toString());
			singleOutput.setSwerveAngle("0");
			singleOutput.setSwervePara("0");
			singleOutput.setSwerveFlow("0");
			singleOutput.setRoadId(Long.valueOf(roadId2).toString());
			singleOutput.setConctrolOrder(false);

			// ��������
			OrderQueue orderQueue = new OrderQueue();
			orderQueue.joinCar1(singleOutput);
			// sendToCar(singleOutput);
			if (carNumber.equals("0102"))
				System.out.println("carNumber-------------------------------------" + carNumber);
			return 2;
		} else {
			return 0;
		}
		
		
	}
	/*
	public int getHeadCar(int groupId , List<Long> list)
	{
		DaoImpl dao = new DaoImpl();
		dao.getGroupIdById()
	}
	
	
	public int getTailCar(int group_id)
	{
		
	}
	*/
	
}