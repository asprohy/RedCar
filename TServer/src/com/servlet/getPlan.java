package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Timer;
import java.util.TimerTask;

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

public class getPlan extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public getPlan() {
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

		response.setContentType("text/html;charset = UTF-8");
		PrintWriter out = response.getWriter();
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

		response.setContentType("text/html;charset = UTF-8");
		PrintWriter out = response.getWriter();
		
		String sTeam = request.getParameter("teamNumber");
		String sPlan = request.getParameter("plan");
		
		JdbcUtilsImp imp = new JdbcUtilsImp();
		CarNumber carN = new CarNumber();		
		
		if(!sTeam.equals("") && sTeam != null && !sPlan.equals("") && sPlan != null) {
			 List<SendEndPointPara> outputlist = new ArrayList<SendEndPointPara>();
			
			
			int team = Integer.valueOf(sTeam).intValue();
			List<String> list = new ArrayList<String>();			
			try {
				Long groupId = imp.getGroupIdbyGroupNum(team);
				list = imp.listMacByGroupId(groupId.intValue());
				Map<String, Object> map = imp.getGroupPlan(sPlan);
				
				String sc ="";
				for(int i=0;i<list.size();i++){
					SendEndPointPara output = new SendEndPointPara();
					int a = imp.getCarNumByMacAddress(list.get(i).toString());
					sc = String.valueOf(a);
					output.setMacAddress(list.get(i).toString());
					output.setRoadId(imp.getRoadIdByTeamAndCar(team,a));
					output.setSpeed(map.get("speed").toString());
					output.setCarNumber(carN.getCarNumber(sTeam, sc));
					output.setSwerveFlow("0");
					output.setSwerveAngle("0");
					output.setSwervePara("0");
					
					outputlist.add(output);
				}		 
				 JudgeDistance(outputlist, map);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}					
			out.print("complex success");
		
		} else {
			out.print(" Get nothing from APP !");
		}
				
		out.flush();
		out.close();
	}

	public void JudgeDistance(List<SendEndPointPara> outputlist, Map<String, Object> map){
		
		double speed = Double.valueOf(map.get("speed").toString()).doubleValue();
		double specialDistance = Double.valueOf(map.get("special_distance").toString()).doubleValue();
		double normalDistance = Double.valueOf(map.get("normal_distance").toString()).doubleValue();
		long specialTime = Math.round(specialDistance/speed * 1000);
		long normalTime = Math.round(normalDistance/speed * 1000);
//		System.out.println(outputlist.size()+"======"+specialTime);
		for(int i=0; i<outputlist.size(); i++){
//			Date now = new Date(); 
//			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");//可以方便地修改日期格式
//			String hehe = dateFormat.format( now ); 
//			System.out.println("这是第"+i+"个====="+hehe);
			outputlist.get(i).setConctrolOrder(true);
			OrderQueue orderQueue = new OrderQueue();
			orderQueue.joinCar1(outputlist.get(i));
			//sendToCar(outputlist.get(i));
			try {
				if(i==0){
					Thread.sleep(specialTime);
				} else{
					Thread.sleep(normalTime);
				}
	            
	        } catch (InterruptedException e) {
	            e.printStackTrace(); 
	        }
		}
	}
	
	
//	public void sendToCar(SendEndPointPara outputEndPointPara){
//	IXbeeMessageConnect conn = new XbeeMessageConnect();
//	conn.openComPort("COM7", 9600);
//	conn.sendMessage(outputEndPointPara);
//	conn.closeComPort();
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
