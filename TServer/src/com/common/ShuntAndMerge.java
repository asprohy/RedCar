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
		//long groupId = 0; 得到之前的小车命令，将速度恢复
		DaoImpl dao = new DaoImpl();
		
	//	List<SendEndPointPara> outputlist = new ArrayList<SendEndPointPara>();
		
		//List<OrderInfo> list = dao.getOrderInfoByShunt();//得到保存的命令信息
		//String speed = String.valueOf(list.get(0).getSpeed());
	//	CarNumber carN = new CarNumber();
		
		//List<CarBasicInfo> list1 = dao.getBasicInfoByShunt();//得到小车的基本信息
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
		dao.mergeUpdateBasicInfo();//合流
		
		//发送命令
		//OrderQueue orderQueue = new OrderQueue();
		//orderQueue.joinCar1(outputlist);
		//sendToCarGroup(outputlist);
	}
	
	//单独做成延迟函数，防止对发送小车命令造成干扰
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
	 * 分流指南：
	 * 
	 * 这个函数有两个目的
	 * 1.将小车的道路ID从2改到3
	 * 2.头车进行分流后，对车队的小车进行恢复速度的操作
	 * 
	 * 更改道路ID的操作进行延迟，有两个目的
	 * 目的1：当小车到达27号霍尔后再进行修改，避免出现霍尔ID大于27时，
	 * 出现小车在显示APP中定位出错的问题（因为小车并未完成分流，但数据库
	 * 中的数据已经篡改）
	 * 目的2：当小车还未到达27号霍尔时，后面的小车还不能恢复速度，不然会发送
	 * 追尾事故
	 * 
	 * 恢复速度：是指由于头车的离开，头车抑制作用消失，后面的车会按照正常的速度行驶
	 * 
	 * 
	 * setConctrolOrder(true)这个字段主要是区别通过服务器向小车发送命令和
	 * 通过控制APP向小车发送命令的字段。服务器发送的命令是指JudgeDistance这
	 * 种服务器动态调控车队代码中向小车发送的命令
	 * 
	 * TRUE是控制APP发送的
	 * FALSE是服务器发送的
	 * 
	 * */
	public void shunt(long carId) throws Exception {//分流操作，一个车队分流成两个车队
		// TODO Auto-generated method stub
		//发送命令给小车
	    	//1.将小车的道路ID从2改到3
		DaoImpl dao = new DaoImpl();
		
		long hall = dao.getHallByCarId(carId);
		while (hall != 27) {
		    hall = dao.getHallByCarId(carId);
		    Thread.sleep(200);
		    System.out.println("111 " + hall);
		}
		dao.shuntUpdateBasicInfo(carId);
		
		//2.头车进行分流后，对车队的小车进行恢复速度的操作
		List<SendEndPointPara> outputlist = new ArrayList<SendEndPointPara>();
		List<OrderInfo> list = dao.getOrderInfoByShunt();//一条命令
		String speed = String.valueOf(list.get(0).getSpeed());
		
		CarNumber carN = new CarNumber();
		
		//
		List<CarBasicInfo> list1 = dao.getBasicInfoByShunt();//得到车队小车的基本信息
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
