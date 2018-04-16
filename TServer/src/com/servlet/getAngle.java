package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.common.CarNumber;
import com.orderSend.OrderQueue;
import com.sql.JdbcUtilsImp;

import connect.IXbeeMessageConnect;
import connect.impl.XbeeMessageConnect;
import connect.para.SendEndPointPara;

public class getAngle extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public getAngle() {
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
		this.doDelete(request, response);
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
		String steamNumber = request.getParameter("teamNumber");
		String scarNumber = request.getParameter("carNumber");
		String sangle = request.getParameter("angle");
		String sDirection = request.getParameter("direction");
		
		SendEndPointPara sendEnd = new SendEndPointPara();
		JdbcUtilsImp imp = new JdbcUtilsImp();
		CarNumber carN = new CarNumber();
		
		if(!steamNumber.equals("") && steamNumber != null && !scarNumber.equals("") 
				&& scarNumber != null && !sangle.equals("") && sangle != null 
				&& !sDirection.equals("") && sDirection != null) {
			int teamNumber = Integer.valueOf(steamNumber).intValue();
			int carNumber = Integer.valueOf(scarNumber);
			try {
				sendEnd.setMacAddress(imp.getMacAddress(teamNumber, carNumber));
				sendEnd.setRoadId(imp.getRoadIdByTeamAndCar(teamNumber, carNumber));
				sendEnd.setSwerveAngle(sangle);
				sendEnd.setSwerveFlow("0");
				sendEnd.setCarNumber(carN.getCarNumber(steamNumber, scarNumber));
				
				if(sDirection.equals("LEFT")){
					sendEnd.setSwervePara("1");
				} else if(sDirection.equals("RIGHT")){
					sendEnd.setSwervePara("2");
				} else {
					sendEnd.setSwervePara("0");
				}
				sendEnd.setConctrolOrder(true);
				OrderQueue orderQueue = new OrderQueue();
				orderQueue.joinCar1(sendEnd);
				//SendAngle(sendEnd);
				out.print("single success");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} else {
			out.print("get nothing from APP");
		}
		out.flush();
		out.close();
	}
	
//	public void SendAngle(SendEndPointPara outputEndPointPara){
//	IXbeeMessageConnect conn = new XbeeMessageConnect();
//	conn.openComPort("COM7", 9600);
//	conn.sendMessage(outputEndPointPara);
//	conn.closeComPort();
//}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
