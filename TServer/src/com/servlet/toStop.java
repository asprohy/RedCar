package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.car.dao.DaoImpl;
import com.common.CarNumber;
import com.orderSend.OrderQueue;
import com.sql.JdbcUtilsImp;

import connect.IXbeeMessageConnect;
import connect.impl.XbeeMessageConnect;
import connect.para.SendEndPointPara;

public class toStop extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public toStop() {
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

		response.setContentType("text/html;charset=utf-8");
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

		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		
		String steamNum = request.getParameter("teamNumber");
		String sType = request.getParameter("type");
		String sCarNum = request.getParameter("carNumber");
		String sSpeed = request.getParameter("speed");
		String sAngle = request.getParameter("angle");
		String sDirection = request.getParameter("direction");
		String sDistance = request.getParameter("distance");
		
		JdbcUtilsImp imp = new JdbcUtilsImp();
		CarNumber carNumber = new CarNumber();
		
		if(!steamNum.equals("") && steamNum != null && !sCarNum.equals("") && sCarNum != null){
			
			//对单个小车进行操作
			if(sType.equals("single") && !sSpeed.equals("") && sSpeed != null && !sAngle.equals("")
					&& sAngle != null){
				int team = Integer.valueOf(steamNum).intValue();
				int car = Integer.valueOf(sCarNum).intValue();
//				System.out.println("这是single");
					
				SendEndPointPara singleOutput = new SendEndPointPara();		
				try {
					imp.setStopData(sSpeed, "0", sAngle, sDirection,"single");
					
					singleOutput.setMacAddress(imp.getMacAddress(team,car));
					singleOutput.setRoadId(imp.getRoadIdByTeamAndCar(team,car));
					singleOutput.setCarNumber(carNumber.getCarNumber(steamNum, sCarNum));
					singleOutput.setSpeed("0");
					singleOutput.setSwerveAngle("0");
					singleOutput.setSwerveFlow("0");
					singleOutput.setSwervePara("0");
					singleOutput.setConctrolOrder(true);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					out.print("fail to get macAddress from DB");
					e.printStackTrace();
				}	
				
				OrderQueue orderQueue = new OrderQueue();
				orderQueue.joinCar1(singleOutput);
//				sendStop(singleOutput);
				out.print("single success");
			} else if(!steamNum.equals("All") && sType.equals("team") && !sSpeed.equals("") && sSpeed != null 
					&& !sDistance.equals("") && sDistance != null){
//				System.out.println("这是team");
				int team = Integer.valueOf(steamNum).intValue();	
				List<SendEndPointPara> outputlist = new ArrayList<SendEndPointPara>();
				
				try {
					imp.setStopData(sSpeed, sDistance, "0", "0", "team");
					
					long groupId = imp.getGroupIdbyGroupNum(team);
					List<String> listM = imp.listMacByGroupId(new Long(groupId).intValue());
					
					String sc ="";
					for(int i=0;i<listM.size();i++){
						SendEndPointPara output = new SendEndPointPara();
						int a = imp.getCarNumByMacAddress(listM.get(i).toString());
						sc = String.valueOf(a);
						output.setMacAddress(listM.get(i).toString());
						output.setRoadId(imp.getRoadIdByTeamAndCar(team,a));
						output.setSpeed("0");
						output.setCarNumber(carNumber.getCarNumber(steamNum, sc));
						output.setSwerveFlow("0");
						output.setSwerveAngle("0");
						output.setSwervePara("0");
						output.setConctrolOrder(true);
						outputlist.add(output);
					}
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				OrderQueue orderQueue = new OrderQueue();
				orderQueue.joinCar1(outputlist);
//				sendToCarGroup(outputlist);				
				out.print("complex success");	
				
			} else if(steamNum.equals("All") && sType.equals("team") && !sSpeed.equals("") && sSpeed != null 
					&& !sDistance.equals("") && sDistance != null){				
//				System.out.println("这是All");
				List<SendEndPointPara> outputlist = new ArrayList<SendEndPointPara>();				
				
				List<String> listM = new ArrayList<String>();
				List<String> list = new ArrayList<String>();
				List<String> list1 = new ArrayList<String>();
				try {
					imp.setStopData(sSpeed, sDistance, "0", "0", "all");
					List<Map<String,Object>> listG = imp.listCarGroupId();
					for(Map<String, Object> i : listG){//将json数据中的value取出来放在list1中
						for(Entry entry : i.entrySet()){
							Object a = entry.getValue();
							list1.add(a.toString());//车队编号
						}
					}
					
					for(int j=0; j<list1.size(); j++){		
						listM = imp.listMacByGroupId(Integer.valueOf(list1.get(j)).intValue());	//得到所有的车队中小车的mac地址
						
						for(int k=0; k<listM.size();k++) {
							list.add(k,listM.get(k));
						}
						
					}				
					
					String st = "";
					String sc = "";
					for(int i=0;i<list.size();i++){
						SendEndPointPara output = new SendEndPointPara();
						
						long gId = imp.getGroupIdByMacAddress(list.get(i).toString());
						st = String.valueOf(imp.getGroupNumByGroupId(gId));
						
						int groupNum = Integer.valueOf(st).intValue();					
						int a = imp.getCarNumByCarId(imp.getCarIdByMac(list.get(i).toString()));
						sc = String.valueOf(a);
						output.setMacAddress(list.get(i).toString());
						output.setRoadId(imp.getRoadIdByTeamAndCar(groupNum, a));
						output.setSpeed("0");
						output.setCarNumber(carNumber.getCarNumber(st, sc));
						output.setSwerveFlow("0");
						output.setSwerveAngle("0");
						output.setSwervePara("0");
						output.setConctrolOrder(true);
						outputlist.add(output);
					}	
				} catch (Exception e) {
					// TODO Auto-generated catch block
					out.print("cannot get data from DB");
					e.printStackTrace();
				}
				OrderQueue orderQueue = new OrderQueue();
				orderQueue.joinCar1(outputlist);
//				sendToCarGroup(outputlist);
				
				out.print("complex success");					
			} else {
				out.print("get nothing from App");
			} 		
			
		} else if(steamNum.equals("") || steamNum == null || sCarNum.equals("") || sCarNum == null){
			out.print("teamNumber or carNumber is null");
		} else {
			out.print("fail to stop");
		}
		out.flush();
		out.close();
		
		//保存停车命令
		if(!steamNum.equals("") && steamNum != null && !sCarNum.equals("") && sCarNum != null
			   && !sSpeed.equals("") && sSpeed != null && sType!=null){
			DaoImpl dao = new DaoImpl();
			try {
				dao.saveStopOrder(steamNum,sCarNum,sType);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}

//	public void sendStop(SendEndPointPara outputEndPointPara) {
//		IXbeeMessageConnect conn = new XbeeMessageConnect();
//		conn.openComPort("COM7", 9600);
//		conn.sendMessage(outputEndPointPara);
//		conn.closeComPort();		
//	}
//	public void sendToCarGroup(List<SendEndPointPara> outputEndPointParaList){
//	IXbeeMessageConnect conn = new XbeeMessageConnect();
//	conn.openComPort("COM7", 9600);
//	conn.sendMessages(outputEndPointParaList);
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
