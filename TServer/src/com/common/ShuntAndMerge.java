package com.common;

import java.util.ArrayList;
import java.util.List;


import com.car.dao.DaoImpl;
import com.entity.CarBasicInfo;
import com.entity.OrderInfo;
import com.orderSend.OrderQueue;

import connect.IXbeeMessageConnect;
import connect.impl.XbeeMessageConnect;
import connect.para.SendEndPointPara;

public class ShuntAndMerge {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
	    	/*
		ShuntAndMerge sam = new ShuntAndMerge();
		long carId = 0;
		sam.shunt(carId);
		sam.merge();
		*/
	    DaoImpl daoImpl = new DaoImpl();
	    System.out.println(daoImpl.getHallByCarId(2));
	}

	public void merge() throws Exception {
		// TODO Auto-generated method stub
		//long groupId = 0; �õ�֮ǰ��С��������ٶȻָ�
		DaoImpl dao = new DaoImpl();
		
	//	List<SendEndPointPara> outputlist = new ArrayList<SendEndPointPara>();
		
		//List<OrderInfo> list = dao.getOrderInfoByShunt();//�õ������������Ϣ
		//String speed = String.valueOf(list.get(0).getSpeed());
	//	CarNumber carN = new CarNumber();
		
		//List<CarBasicInfo> list1 = dao.getBasicInfoByShunt();//�õ�С���Ļ�����Ϣ
//		for(int i = 0;i<list1.size();i++){
//			SendEndPointPara output = new SendEndPointPara();
//			output.setMacAddress(list1.get(i).getMac_adress());
//			
//			output.setRoadId("2");
//			
//			output.setSpeed(speed);
//			String steamNum = String.valueOf(list1.get(i).getGroup_id());
//			String carNum = String.valueOf(list1.get(i).getId());
//			output.setCarNumber(carN.getCarNumber(steamNum, carNum));
//			output.setSwerveFlow("0");
//			output.setSwerveAngle("0");
//			output.setSwervePara("0");
//			
//			outputlist.add(output);
//		}
		List<Integer> shuntCarList = dao.getShuntCarId();
		long carId = shuntCarList.get(0);
		long hall = dao.getHallByCarId(carId);
		while (hall > 27) {
		    System.out.println("111: " + hall);
		    Thread.sleep(200);
		    hall = dao.getHallByCarId(carId);
		}
		dao.mergeUpdateBasicInfo();//����
		
		//��������
		//OrderQueue orderQueue = new OrderQueue();
		//orderQueue.joinCar1(outputlist);
		//sendToCarGroup(outputlist);
	}
	
	//���������ӳٺ�������ֹ�Է���С��������ɸ���
	public void shuntCar(long carId) throws Exception{
	    DaoImpl dao = new DaoImpl();
		
	    long hall = dao.getHallByCarId(carId);
	    while (hall != 27) {
		hall = dao.getHallByCarId(carId);
		Thread.sleep(200);
		System.out.println("111 " + hall);
	    }
	    dao.shuntUpdateBasicInfo(carId);
	}
	
	/*
	 * ����ָ�ϣ�
	 * 
	 * �������������Ŀ��
	 * 1.��С���ĵ�·ID��2�ĵ�3
	 * 2.ͷ�����з����󣬶Գ��ӵ�С�����лָ��ٶȵĲ���
	 * 
	 * ���ĵ�·ID�Ĳ��������ӳ٣�������Ŀ��
	 * Ŀ��1����С������27�Ż������ٽ����޸ģ�������ֻ���ID����27ʱ��
	 * ����С������ʾAPP�ж�λ��������⣨��ΪС����δ��ɷ����������ݿ�
	 * �е������Ѿ��۸ģ�
	 * Ŀ��2����С����δ����27�Ż���ʱ�������С�������ָܻ��ٶȣ���Ȼ�ᷢ��
	 * ׷β�¹�
	 * 
	 * �ָ��ٶȣ���ָ����ͷ�����뿪��ͷ������������ʧ������ĳ��ᰴ���������ٶ���ʻ
	 * 
	 * 
	 * setConctrolOrder(true)����ֶ���Ҫ������ͨ����������С�����������
	 * ͨ������APP��С������������ֶΡ����������͵�������ָJudgeDistance��
	 * �ַ�������̬���س��Ӵ�������С�����͵�����
	 * 
	 * TRUE�ǿ���APP���͵�
	 * FALSE�Ƿ��������͵�
	 * 
	 * */
	public void shunt(long carId) throws Exception {//����������һ�����ӷ�������������
		// TODO Auto-generated method stub
		//���������С��
	    	//1.��С���ĵ�·ID��2�ĵ�3
		DaoImpl dao = new DaoImpl();
		
		long hall = dao.getHallByCarId(carId);
		while (hall != 27) {
		    hall = dao.getHallByCarId(carId);
		    Thread.sleep(200);
		    System.out.println("111 " + hall);
		}
		dao.shuntUpdateBasicInfo(carId);
		
		//2.ͷ�����з����󣬶Գ��ӵ�С�����лָ��ٶȵĲ���
		List<SendEndPointPara> outputlist = new ArrayList<SendEndPointPara>();
		List<OrderInfo> list = dao.getOrderInfoByShunt();//һ������
		String speed = String.valueOf(list.get(0).getSpeed());
		
		CarNumber carN = new CarNumber();
		
		//
		List<CarBasicInfo> list1 = dao.getBasicInfoByShunt();//�õ�����С���Ļ�����Ϣ
		for(int i =0;i<list1.size();i++){
			
			SendEndPointPara output = new SendEndPointPara();
			output.setMacAddress(list1.get(i).getMac_adress());
				
			output.setRoadId("2");
				
			output.setSpeed(speed);
			String steamNum = String.valueOf(list1.get(i).getGroup_id());
			String carNum = String.valueOf(list1.get(i).getId());
			output.setCarNumber(carN.getCarNumber(steamNum, carNum));
			output.setSwerveFlow("0");
			output.setSwerveAngle("0");
			output.setSwervePara("0");
			output.setConctrolOrder(true);
			outputlist.add(output);
		}
		OrderQueue orderQueue = new OrderQueue();
		orderQueue.joinCar1(outputlist);
	}
	 

//	public void sendToCarGroup(List<SendEndPointPara> outputEndPointParaList){
//		IXbeeMessageConnect conn = new XbeeMessageConnect();
//		conn.openComPort("COM7", 9600);
//		conn.sendMessages(outputEndPointParaList);
//		conn.closeComPort();		
//		
//	}
}
