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

		// 如果分流后再合流判断距离,hall就会出错
		int carId = 1;
		// System.out.println("carId:"+carId);
		JudgeDistance judge = new JudgeDistance();
		// System.out.println("carId:"+carId);
		int state = judge.judge(carId);
		System.out.println("state:" + state);
		// 0:不做操作
		// 1:距离过近
		// 2:距离过远
	}

	// public int JudgeDistance(int carId){
	//
	// int state = 0;//0:不做操作,1:距离过近,2:距离过远
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

		// 看看小车是否正在急停中
		List<CarWarnInfo> listStop = new ArrayList<CarWarnInfo>();

		allCarId = dao.getAllCarId();

		int state = 0;
		for (int i = 0; i < allCarId.size(); i++) {
			if (allCarId.get(i) == carId) {
				listStop = dao.getStopInfo(carId);
				if (listStop.size() == 0 || !listStop.get(0).isStop()) {
					state = judge.judge(carId);
				}
			} /*else if (allCarId.get(i) == 5) {// 模拟的假队，一个单车
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

	private void simulateGroup(int carId) throws Exception {// 模拟多个队
		// TODO Auto-generated method stub
		DaoImpl dao = new DaoImpl();
		List<CarState> list = new ArrayList<CarState>();// 整个小队的，bu算上被模拟的
		List<CarState> car = new ArrayList<CarState>();// 模拟另一个车队
		List<CarBasicInfo> list2 = dao.getBasicInfoById(carId);

		list = dao.getSimulateGroup(carId);
		car = dao.getCarInfoById(carId);

		if (car.size() == 0 || list.size() == 0) {
			return;
		}
		String channel = car.get(0).getChannel();

		double min = 100;
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getChannel().equals(channel)) {// 先是同一个道内外
				// 找出和模拟小车最近的
				if (Math.abs(list.get(i).getDrive_distance() - car.get(0).getDrive_distance()) < min) {
					min = list.get(i).getDrive_distance() - car.get(0).getDrive_distance();
				}
			}
		}

		SendEndPointPara singleOutput = new SendEndPointPara();
		double speed = car.get(0).getSpeed();
		double speed1 = 0;
		double distance = 1;// 模拟固定的车间距
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

		// 发送命令
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
		long groupId = dao.getGroupIdById(carId);// 得到车队id

		// System.out.println("groupId:"+groupId);
		list = dao.getCarInfoByGroupId(groupId);// 模拟的小队。。。不模拟小队时去掉carid!=5
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
			// 先判断小车是不是最前面的，只判断和前车的距离
			// System.out.println("list.get(j).getCar_id():"+list.get(j).getCar_id());
			// System.out.println("carId:"+carId);
			// System.out.println("");
			if (list.get(j).getCar_id() != carId && list.get(j).getChannel().equals(car.get(0).getChannel())) {
				// System.out.println("j:"+j);
				min = list.get(j).getDrive_distance() - car.get(0).getDrive_distance();
				hallDex = list.get(j).getHistory_hall_count() - car.get(0).getHistory_hall_count();
				if (min > 0 && hallDex >= 0) {// 找到在其前面，距离最近的小车
					break;
				}
				
				if(car.get(0).getCar_id()==1)//car.get(0)为1车
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
		if (min <= 0) {// 小车是最前面的车，直接结束
			return 0;
		} else if (hallDex > 27) {
			// System.out.println("hallDex"+hallDex);
			return 0;// 小车比其他车多跑一圈
		} else {
			for (int k = 0; k < list.size(); k++) {// 如果不是最前车，找到自己车后面最近的车
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

		double distance = list1.get(0).getDistance();// 小车命令设置的距离
		int speed = list1.get(0).getSpeed();
		List<CarBasicInfo> list2 = dao.getBasicInfoById(carId);

		if (list2.size() == 0) {
			return 0;
		}

		long roadId = list2.get(0).getRoad_id();
		String macAddress = list2.get(0).getMac_adress();
		int carNum = list2.get(0).getCar_num();// 队内编号
		String carNumber = "0" + Long.valueOf(groupId).toString() + "0" + Integer.valueOf(carNum).toString();// 队号+队内编号

		SendEndPointPara singleOutput = new SendEndPointPara();

		System.out.println("min:" + min);
		// 用现在的距离对比小车命令设置的距离
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
			// 如果车距小于命令车距的80%
			System.out.println("车间距过近，开始调控");
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

			// 发送命令
			OrderQueue orderQueue = new OrderQueue();
			orderQueue.joinCar1(singleOutput);
			// sendToCar(singleOutput);
			if (carNumber.equals("0102"))
				System.out.println("carNumber---------------------------------------" + carNumber);
			return 1;
			// return 1;//距离过近,降低20%速度

		} else if ((min - 0.32 > distance * 3)) {
			double speed1 = 0;
			if ((min - 0.32) <= distance * 4 && (min - 0.32) > distance * 3) {
				speed1 = speed * 1.05;
			} else if ((min - 0.32) > distance * 4) {
				speed1 = speed * 1.1;
			}
			// 如果车距大于20%，加速20%车速
			System.out.println("车间距过远，开始调控");
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

			// 发送命令
			System.out.println("加速： " +singleOutput.getMacAddress() + " " + singleOutput.getRoadId() + " "
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
