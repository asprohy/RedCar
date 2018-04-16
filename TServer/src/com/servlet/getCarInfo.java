package com.servlet;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.car.dao.DaoImpl;
import com.common.UpdateChannel;
import com.entity.CarState;
import com.sql.JdbcUtilsImp;

import connect.impl.XbeeMessageConnect;
import connect.para.ReceiveEndPointPara;

public class getCarInfo extends HttpServlet {

	private static final long serialVersionUID = 1L;

	JdbcUtilsImp dao = new JdbcUtilsImp();

	/**
	 * Constructor of the object.
	 */
	public getCarInfo() {
		super();
	}

	/**
	 * Initialization of the servlet. <br>
	 * 
	 * @throws ServletException
	 *             if an error occurs
	 */
	public void init() throws ServletException {
		System.out.println("running");
		// XbeeMessageConnect xbeeMessageConnect1 = new XbeeMessageConnect();
		// XbeeMessageConnect xbeeMessageConnect2 = new XbeeMessageConnect();
		// xbeeMessageConnect1.openComPort("COM6", 9600);
		// xbeeMessageConnect2.openComPort("COM7", 9600);
		try {
			// xbeeMessageConnect1.start();
			// ThreadTest threadTest = new ThreadTest();
			// threadTest.start();
			List<ReceiveEndPointPara> list = new ArrayList<ReceiveEndPointPara>();
			BufferedReader br = new BufferedReader(new InputStreamReader(
					new FileInputStream("E:/carData.txt")));
			String dataLine;
			while ((dataLine = br.readLine()) != null) {
				int carNumberIndex = dataLine.indexOf("carNumber");
				// System.out.println(carNumberIndex);
				int speedIndex = dataLine.indexOf("speed");
				// System.out.println(speedIndex);
				int distanceIndex = dataLine.indexOf("distance");
				// System.out.println(distanceIndex);
				int hoareIndex = dataLine.indexOf("hoare");
				// System.out.println(hoareIndex);
				int swerveAngleIndex = dataLine.indexOf("swerveAngle");
				// System.out.println(swerveAngleIndex);
				int inOutSide = dataLine.indexOf("inOutSide");
				// System.out.println(inOutSide);
				int timeIndex = dataLine.indexOf("time");
				// System.out.println(timeIndex);
				ReceiveEndPointPara para = new ReceiveEndPointPara();
				para.setCarNumber(dataLine.substring(carNumberIndex
						+ "carNumber:".length(), speedIndex - 1));
				para.setSpeed(dataLine.substring(
						speedIndex + "speed:".length(), distanceIndex - 1));
				para.setDistance(dataLine.substring(
						distanceIndex + "distance:".length(), hoareIndex - 1));
				para.setHoare(Integer.parseInt(dataLine.substring(hoareIndex
						+ "hoare:".length(), swerveAngleIndex - 1)));
				para.setInOutSide("1");
				para.setSwerveAngle("0");
				para.setChangeSuccess(false);
				para.setTime(Long.parseLong(dataLine.substring(timeIndex
						+ "time:".length(), dataLine.length())));

				list.add(para);
			}
			// Double distance = 0.31;
			// int hoareCount = 2;
			// for(int i=0; i< 400; i++) {
			// ReceiveEndPointPara para = new ReceiveEndPointPara();
			// para.setSpeed("0.33");
			// para.setCarNumber("0101");
			// para.setChangeSuccess(false);
			// para.setDistance(String.valueOf(distance));
			// para.setHoare(hoareCount);
			// para.setInOutSide("1");
			// para.setSwerveAngle("0");
			// para.setTime(System.currentTimeMillis());
			// list.add(para);
			// hoareCount++;
			// distance = distance + 0.1;
			// }
			
			/******************/
			List<CarState> carStateList = new ArrayList<CarState>();
			for (int i = 0; i < list.size() - 2; i++) {
				if (list.get(i).getHoare() == 0) {
					continue;
				}
				DaoImpl dao1 = new DaoImpl();
				String carNumber = list.get(i).getCarNumber();
				String groupId1 = carNumber.substring(0, 2);
				String carNum1 = carNumber.substring(2);
				long groupId = Long.valueOf(groupId1).longValue();
				int carNum = Integer.valueOf(carNum1).intValue();
				long carId = dao1.getCarIdByCarNum(groupId, carNum);

				if (list.get(i).isChangeSuccess()) {
					UpdateChannel update = new UpdateChannel();
					long hall = update.getChangeHall(carId);
					list.get(i).setHoare((int) hall);
				}

				CarState carState = new CarState();
				carState.setCar_id(carId);
				// float speed = Float.parseFloat(para.getSpeed());
				carState.setSpeed1(Float.parseFloat(list.get(i).getSpeed()));
				carState.setDrive_distance1(Float.parseFloat(list.get(i).getDistance()));
				carState.setHistory_hall_count(list.get(i).getHoare());
				carState.setSend_time(list.get(i).getTime());
				carState.setHeading_degree(Integer.parseInt(list.get(i).getSwerveAngle()));
				carState.setGroup_id(groupId);
				carState.setChange_success(list.get(i).isChangeSuccess());
				carState.setChannel(list.get(i).getInOutSide());
				// long spendTimeStart = System.currentTimeMillis();
				// //dao.addCarHistory(carId, para);
				// long spendTimeEnd = System.currentTimeMillis();
				// System.out.println(String.valueOf((spendTimeEnd-spendTimeStart)));
				
				
				if (list.get(i+1).getTime() -list.get(i).getTime() < 100){
					carStateList.add(carState);
				}else{
					//System.out.println("i:"+i);
					dao.addCarHistory(carStateList);
					carStateList.clear();
					Thread.sleep(350);
				}
				
			}  
			/***********************/
			/***********            
			 for (ReceiveEndPointPara para : list) {
			 if (para.getHoare() == 0){
			 continue;
			 }
			 DaoImpl dao1 = new DaoImpl();
			 String carNumber = para.getCarNumber();
			 String groupId1 = carNumber.substring(0, 2);
			 System.out.println("carNum: " + carNumber + " groubId: " +
			 groupId1);
			 String carNum1 = carNumber.substring(2);
			 long groupId = Long.valueOf(groupId1).longValue();
			 int carNum = Integer.valueOf(carNum1).intValue();
			 long carId = dao1.getCarIdByCarNum(groupId,carNum);
			 System.out.println(carId);
			 if(para.isChangeSuccess()){//变道成功，把小车hall数据更新，插入的即是更新后的hall
			 UpdateChannel update = new UpdateChannel();
			 long hall = update.getChangeHall(carId);
			 para.setHoare((int) hall);
			 }
			 // long spendTimeStart = System.currentTimeMillis();
			
			 dao.addCarHistory(carId, para);
			 //long spendTimeEnd = System.currentTimeMillis();
			 //System.out.println(String.valueOf((spendTimeEnd-spendTimeStart)));
			 Thread.sleep(200);
			 }*********************/
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void receiveXbeeMessages(List<ReceiveEndPointPara> list)
			throws Exception {

		List<CarState> carStateList = new ArrayList<CarState>();
		for (ReceiveEndPointPara para : list) {
			if (para.getHoare() == 0) {
				continue;
			}

			DaoImpl dao1 = new DaoImpl();
			String carNumber = para.getCarNumber();
			String groupId1 = carNumber.substring(0, 2);
			String carNum1 = carNumber.substring(2);
			long groupId = Long.valueOf(groupId1).longValue();
			int carNum = Integer.valueOf(carNum1).intValue();
			long carId = dao1.getCarIdByCarNum(groupId, carNum);

			if (para.isChangeSuccess()) {
				UpdateChannel update = new UpdateChannel();
				long hall = update.getChangeHall(carId);
				para.setHoare((int) hall);
			}

			CarState carState = new CarState();
			carState.setCar_id(carId);
			// float speed = Float.parseFloat(para.getSpeed());
			carState.setSpeed1(Float.parseFloat(para.getSpeed()));
			carState.setDrive_distance1(Float.parseFloat(para.getDistance()));
			carState.setHistory_hall_count(para.getHoare());
			carState.setSend_time(para.getTime());
			carState.setHeading_degree(Integer.parseInt(para.getSwerveAngle()));
			carState.setGroup_id(groupId);
			carState.setChange_success(para.isChangeSuccess());
			carState.setChannel(para.getInOutSide());
			// long spendTimeStart = System.currentTimeMillis();
			// //dao.addCarHistory(carId, para);
			// long spendTimeEnd = System.currentTimeMillis();
			// System.out.println(String.valueOf((spendTimeEnd-spendTimeStart)));
			carStateList.add(carState);
		}

		dao.addCarHistory(carStateList);
		// for (ReceiveEndPointPara para : list) {
		// Date time = new Date();
		// DateFormat timeFormat = new
		// SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		// String timeReal = timeFormat.format(time);
		//
		// String readContext = "";
		// readContext =
		// readContext.concat("carNumber:").concat(para.getCarNumber()).concat(" speed:")
		// .concat(para.getSpeed().concat(" distance:").concat(para.getDistance()).concat(" hoare:")
		// .concat(String.valueOf(para.getHoare())
		// .concat(" swerveAngle:").concat(para.getSwerveAngle()).concat(" time:").concat(String.valueOf(para.getTime()))));
		// System.out.println(readContext);
		// }
	}

}