package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



import com.car.dao.DaoImpl;
import com.common.CarNumber;
import com.common.ShuntAndMerge;
import com.orderSend.OrderQueue;

import connect.IXbeeMessageConnect;
import connect.impl.XbeeMessageConnect;
import connect.para.SendEndPointPara;

public class toSeperate extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public toSeperate() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		this.doPost(request, response);
		out.flush();
		out.close();
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		String teamNum = request.getParameter("teamNumber");
		String carNum = request.getParameter("carNumber");
		String sOrder = request.getParameter("order");
		System.out.println("分流/合流："+sOrder);
		
		SendEndPointPara sendEnd = new SendEndPointPara();
		CarNumber carNumber = new CarNumber();
		if(sOrder != null && teamNum != null && !sOrder.equals("") && !teamNum.equals("") 
				&& !carNum.equals("") && carNum != null){
			
			ShuntAndMerge sam = new ShuntAndMerge();
			
			DaoImpl dao = new DaoImpl();
			long carId=0;
			String Mac= "";
			try {
				Map<String,Object> result = dao.getMacIdByCarNum(teamNum, carNum);
				
				carId = (Long) result.get("id");
				System.out.println("分流carId:"+carId);
				Mac =  (String) result.get("mac_adress");
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				System.out.println("获取小车id和mac失败！");
				e1.printStackTrace();
			}
			
			int order = Integer.valueOf(sOrder).intValue();
			if(order == 1){   //0：正常行驶， 1：分流; 2:合流
				sendEnd.setSwerveFlow(sOrder);
				sendEnd.setCarNumber(carNumber.getCarNumber(teamNum, carNum));
				sendEnd.setMacAddress(Mac);
				sendEnd.setRoadId("0");
				sendEnd.setSpeed("0");
				sendEnd.setSwerveAngle("0");
				sendEnd.setSwervePara("0");
				sendEnd.setConctrolOrder(true);
				try {
				    	OrderQueue orderQueue = new OrderQueue();
					orderQueue.joinCar1(sendEnd);
					sam.shunt(carId);//分流，修改数据库\
					//sam.shuntCar(carId);
					out.print("seperate success");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					out.print("Faile to seperate");
					System.out.println("分流失败！");
				}
				
			} else if(order == 2) {
				sendEnd.setSwerveFlow(sOrder);
				sendEnd.setCarNumber(carNumber.getCarNumber(teamNum, carNum));
				sendEnd.setMacAddress(Mac);
				sendEnd.setRoadId("0");
				sendEnd.setSpeed("0");
				sendEnd.setSwerveAngle("0");
				sendEnd.setSwervePara("0");
				sendEnd.setConctrolOrder(true);
				try {
					//long carId=0;
				    
				    	
				    	OrderQueue orderQueue = new OrderQueue();
					orderQueue.joinCar1(sendEnd);
					sam.merge();//合流修改数据库
					out.print("interflow success");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					out.print("Faile to interflow");
					System.out.println("合流失败！");
				}
				
			} else {
				
			}
			
			
		} else {
			out.print("fail to seperate");
		}
		
		out.flush();
		out.close();
	}
	
//	public void sendSperate(SendEndPointPara sendEndPointPara) {
//		IXbeeMessageConnect conn = new XbeeMessageConnect();
//		conn.openComPort("COM7", 9600);
//		conn.sendMessage(sendEndPointPara);
//		conn.closeComPort();
//	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
