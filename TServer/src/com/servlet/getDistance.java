package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

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

public class getDistance extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public getDistance() {
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
		final PrintWriter out = response.getWriter();
		final String steamNumber = request.getParameter("teamNumber");
		final String sspeed = request.getParameter("speed");
		String sdistance = request.getParameter("distance");
		
		
		final SendEndPointPara sendEnd = new SendEndPointPara();
		final CarNumber carNumber = new CarNumber();
		
		
		if(!steamNumber.equals("") && steamNumber != null && !sspeed.equals("") && sspeed != null
				&& !sdistance.equals("") && sdistance != null) {
			
			double speed = Double.valueOf(sspeed).doubleValue();
			double distance = Double.valueOf(sdistance).doubleValue();
			
			if(steamNumber.equals("AllCar")) {				
				
				final long timeInterval = (long)(distance/speed)*1000;// 单位转化为：ms
				
				Runnable runnable = new Runnable(){

					public void run() {
						// TODO Auto-generated method stub
						try {
							JdbcUtilsImp imp = new JdbcUtilsImp();
							List<String> list = new ArrayList<String>();
							list = imp.listMacAdress();
								
							String st = "";
							String sc = "";
							for(int i=0;i<list.size();i++){
								st = String.valueOf(imp.getGroupNumByGroupId(imp.getGroupIdByMacAddress(list.get(i)))).toString();
								sc = String.valueOf(imp.getCarNumByMacAddress(list.get(i))).toString();
								sendEnd.setMacAddress(list.get(i));
								sendEnd.setRoadId(imp.getRoadIdByTeamAndCar(new Integer(st).intValue(), new Integer(sc).intValue()));
								sendEnd.setSpeed(sspeed);
								sendEnd.setCarNumber(carNumber.getCarNumber(st, sc));
								sendEnd.setSwerveFlow("0");
								sendEnd.setSwerveAngle("0");
								sendEnd.setSwervePara("0");
								sendEnd.setConctrolOrder(true);
								OrderQueue orderQueue = new OrderQueue();
								orderQueue.joinCar1(sendEnd);
								//SendDistance(sendEnd);
								Thread.sleep(timeInterval);
								
							}
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							out.print("Wrong");
						}
					}
					
					
				};
				
				Thread thread = new Thread(runnable);
				thread.start();
				
				
				out.print("complex success");
			} else {
				final long timeInterval = (long)(distance/speed)*1000;// 单位转化为：ms
				final int groupId = Integer.valueOf(steamNumber).intValue();
				
				Runnable runnable = new Runnable(){

					public void run() {
						// TODO Auto-generated method stub
						try {
							JdbcUtilsImp imp = new JdbcUtilsImp();
							List<String> list = new ArrayList<String>();
							list = imp.listMacByGroupId(groupId);
								
							String sc = "";
							for(int i=0;i<list.size();i++){
								sc = String.valueOf(imp.getCarNumByMacAddress(list.get(i))).toString();
								sendEnd.setMacAddress(list.get(i));
								sendEnd.setRoadId(imp.getRoadIdByTeamAndCar(new Integer(sc).intValue(), 
										imp.getCarNumByMacAddress(list.get(i).toString())));
								sendEnd.setSpeed(sspeed);
								sendEnd.setCarNumber(carNumber.getCarNumber(steamNumber, sc));
								sendEnd.setSwerveFlow("0");
								sendEnd.setSwerveAngle("0");
								sendEnd.setSwervePara("0");
								sendEnd.setConctrolOrder(true);
								OrderQueue orderQueue = new OrderQueue();
								orderQueue.joinCar1(sendEnd);
								//SendDistance(sendEnd);
								Thread.sleep(timeInterval);
								
							}
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							out.print("Wrong");
						}
					}
					
				};
				
				Thread thread = new Thread(runnable);
				thread.start();
				
				out.print("complex success");				
			}
			
		} else {
			out.print("get nothing from APP");
		}
		out.flush();
		out.close();
	}
	
//	public void SendDistance(SendEndPointPara outputEndPointPara){
//		IXbeeMessageConnect conn = new XbeeMessageConnect();
//		conn.openComPort("COM7", 9600);
//		conn.sendMessage(outputEndPointPara);
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
