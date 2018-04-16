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

public class JudgeDistance {

	/**
	 * @param args
	 * @throws Exception
	 */
    
    private static final double[] THEWHOLELENGTH = {15.7 , 1000 , 1000 , 1000 , 1000 , 1000 };
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		// ����������ٺ����жϾ���,hall�ͻ����
		int carId = 1;
		// System.out.println("carId:"+carId);
		JudgeDistance judge = new JudgeDistance();
		// System.out.println("carId:"+carId);
		int state = judge.judge(carId);
		System.out.println("state:" + state);
		// 0:��������
		// 1:�������
		// 2:�����Զ
	}

	// public int JudgeDistance(int carId){
	//
	// int state = 0;//0:��������,1:�������,2:�����Զ
	//
	//
	//
	//
	// return state;
	//
	// }

	public int allDistance(int carId) throws Exception {//
		JudgeDistance judge = new JudgeDistance();
		List<Integer> allCarId = new ArrayList<Integer>();
		DaoImpl dao = new DaoImpl();

		// ����С���Ƿ����ڼ�ͣ��
		List<CarWarnInfo> listStop = new ArrayList<CarWarnInfo>();

		allCarId = dao.getAllCarId();

		int state = 0;
		for (int i = 0; i < allCarId.size(); i++) {
			if (allCarId.get(i) == carId) {
				listStop = dao.getStopInfo(carId);
				if (listStop.size() == 0 || !listStop.get(0).isStop()) {
					state = judge.judge(carId);
				}
			} /*else if (allCarId.get(i) == 5) {// ģ��ļٶӣ�һ������
				simulateGroup(carId);
			} */
			else {
				listStop = dao.getStopInfo(allCarId.get(i));
				if (listStop.size() == 0 || !listStop.get(0).isStop()) {
					judge(allCarId.get(i));
				}

			}

		}

		return state;

	}

	private void simulateGroup(int carId) throws Exception {// ģ������
		// TODO Auto-generated method stub
		DaoImpl dao = new DaoImpl();
		List<CarState> list = new ArrayList<CarState>();// ����С�ӵģ�bu���ϱ�ģ���
		List<CarState> car = new ArrayList<CarState>();// ģ����һ������
		List<CarBasicInfo> list2 = dao.getBasicInfoById(carId);

		list = dao.getSimulateGroup(carId);
		car = dao.getCarInfoById(carId);

		if (car.size() == 0 || list.size() == 0) {
			return;
		}
		String channel = car.get(0).getChannel();

		double min = 100;
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getChannel().equals(channel)) {// ����ͬһ��������
				// �ҳ���ģ��С�������
				if (Math.abs(list.get(i).getDrive_distance() - car.get(0).getDrive_distance()) < min) {
					min = list.get(i).getDrive_distance() - car.get(0).getDrive_distance();
				}
			}
		}

		SendEndPointPara singleOutput = new SendEndPointPara();
		double speed = car.get(0).getSpeed();
		double speed1 = 0;
		double distance = 1;// ģ��̶��ĳ����
		if ((min - 0.35) >= (distance * 1.5)) {
			speed1 = speed * 1.1;
		} else if ((min - 0.35) < (distance * 1.5) && (min - 0.35) > (distance * 0.8)) {
			speed1 = speed * 1.08;
		} else if ((min - 0.35) <= (distance * 0.8) && (min - 0.35) > (distance * 0.3)) {
			speed1 = speed * 0.86;
		} else if ((min - 0.35) <= (distance * 0.3)) {
			speed1 = speed * 0.8;
		}
		singleOutput.setMacAddress(list2.get(0).getMac_adress());
		singleOutput.setCarNumber("0104");

		singleOutput.setSpeed(Double.valueOf(speed1).toString());
		singleOutput.setSwerveAngle("0");
		singleOutput.setSwervePara("0");
		singleOutput.setSwerveFlow("0");
		singleOutput.setRoadId(Long.valueOf(car.get(0).getRoad_id()).toString());

		// ��������
		OrderQueue orderQueue = new OrderQueue();
		//orderQueue.joinCar1(singleOutput);
	}

	public int judge(int carId) throws Exception {

		DaoImpl dao = new DaoImpl();

		if(carId == 2){
		    return 0;
		}
		// int carId = 3;
		List<CarState> list = new ArrayList<CarState>();
		long groupId = dao.getGroupIdById(carId);// �õ�����id

		// System.out.println("groupId:"+groupId);
		list = dao.getCarInfoByGroupId(groupId);// ģ���С�ӡ�������ģ��С��ʱȥ��carid!=5
		// System.out.println("list:"+list.size());

		List<CarState> car = new ArrayList<CarState>();
		car = dao.getCarInfoById(carId);

		System.out.println(list.size());

		if (list.size() == 0) {
			return 0;
		}

		if (car.size() == 0) {
			return 0;
		}

		// System.out.println("carState:"+ list.get(0).getClass().getName());
		// if(list.get(0).getClass().getName().equals("com.entity.CarState")){
		// System.out.println("Yes");
		// }

		double min = 0;
		long hallDex = 0;
		for (int j = 0; j < list.size(); j++) {
			// ���ж�С���ǲ�����ǰ��ģ�ֻ�жϺ�ǰ���ľ���
			// System.out.println("list.get(j).getCar_id():"+list.get(j).getCar_id());
			// System.out.println("carId:"+carId);
			// System.out.println("");
			if (list.get(j).getCar_id() != carId && list.get(j).getChannel().equals(car.get(0).getChannel())) {
				// System.out.println("j:"+j);
				min = list.get(j).getDrive_distance() - car.get(0).getDrive_distance();
				hallDex = list.get(j).getHistory_hall_count() - car.get(0).getHistory_hall_count();
				if (min > 0 && hallDex >= 0) {// �ҵ�����ǰ�棬���������С��
					break;
				}
				
				if(car.get(0).getCar_id()==1)//car.get(0)Ϊ1��
				{
					if(list.get(j).getCar_id()==2){
					    
					    if(car.get(0).getHistory_hall_count()>list.get(j).getHistory_hall_count())
					    {
						min=list.get(j).getDrive_distance() + THEWHOLELENGTH[0] - car.get(0).getDrive_distance();
						break;
					    }
					}
				}
	
			}
		}

		// System.out.println("min:"+min);
		if (min <= 0) {// С������ǰ��ĳ���ֱ�ӽ���
			return 0;
		} else if (hallDex > 27) {
			// System.out.println("hallDex"+hallDex);
			return 0;// С��������������һȦ
		} else {
			for (int k = 0; k < list.size(); k++) {// ���������ǰ�����ҵ��Լ�����������ĳ�
				double index = list.get(k).getDrive_distance() - car.get(0).getDrive_distance();

				if (list.get(k).getCar_id() != carId && index > 0 && index < min) {
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
		List<CarBasicInfo> list2 = dao.getBasicInfoById(carId);

		if (list2.size() == 0) {
			return 0;
		}

		long roadId = list2.get(0).getRoad_id();
		String macAddress = list2.get(0).getMac_adress();
		int carNum = list2.get(0).getCar_num();// ���ڱ��
		String carNumber = "0" + Long.valueOf(groupId).toString() + "0" + Integer.valueOf(carNum).toString();// �Ӻ�+���ڱ��

		SendEndPointPara singleOutput = new SendEndPointPara();

		System.out.println("min:" + min);
		// �����ڵľ���Ա�С���������õľ���
		if ((min - 0.32 <= distance * 2)) {
			double speed1 = 0;
			boolean needJudgeDistance = false;
			if (carNumber.equals("0101")) {
				if (car.get(0).getHistory_hall_count() == 25 || car.get(0).getHistory_hall_count() == 26) {
					needJudgeDistance = true;
					if ((min - 0.32) < distance * 1) {
						speed1 = speed * 0;
					}
				}
			}
			if (!needJudgeDistance) {
				if ((min - 0.32) < distance * 2 && (min - 0.32) >= distance * 1) {
					speed1 = speed * 0.9;
				} else if ((min - 0.32) < distance * 1 && (min - 0.32) > distance * 0.5) {
					speed1 = speed * 0.85;
				} else if ((min - 0.32) <= distance * 0.5) {
					speed1 = speed * 0.8;
				}
			}
			// �������С��������80%
			System.out.println("������������ʼ����");
			System.out.println("---------------------------------");
			System.out.println("carNumber:" + carNumber);
			System.out.println("carSpeed:" + speed1);
			System.out.println("hoare:" + car.get(0).getHistory_hall_count());
			System.out.println("---------------------------------");
			singleOutput.setMacAddress(macAddress);
			singleOutput.setCarNumber(carNumber);

			singleOutput.setSpeed(Double.valueOf(speed1).toString());
			singleOutput.setSwerveAngle("0");
			singleOutput.setSwervePara("0");
			singleOutput.setSwerveFlow("0");
			singleOutput.setRoadId(Long.valueOf(roadId).toString());
			singleOutput.setConctrolOrder(false);

			// ��������
			OrderQueue orderQueue = new OrderQueue();
			orderQueue.joinCar1(singleOutput);
			// sendToCar(singleOutput);
			if (carNumber.equals("0102"))
				System.out.println("carNumber---------------------------------------" + carNumber);
			return 1;
			// return 1;//�������,����20%�ٶ�

		} else if ((min - 0.32 > distance * 3)) {
			double speed1 = 0;
			if ((min - 0.32) <= distance * 4 && (min - 0.32) > distance * 3) {
				speed1 = speed * 1.05;
			} else if ((min - 0.32) > distance * 4) {
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
			singleOutput.setRoadId(Long.valueOf(roadId).toString());
			singleOutput.setConctrolOrder(false);

			// ��������
			System.out.println("���٣� " +singleOutput.getMacAddress() + " " + singleOutput.getRoadId() + " "
				+ singleOutput.getSpeed() + " " + singleOutput.getCarNumber() + " " + singleOutput.getSwerveFlow() + " "
				+ singleOutput.getSwerveAngle());
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

	// public void sendToCar(SendEndPointPara outputEndPointPara){
	// IXbeeMessageConnect conn = new XbeeMessageConnect();
	// conn.openComPort("COM7", 9600);
	// conn.sendMessage(outputEndPointPara);
	// conn.closeComPort();
	// }

}
