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

import net.sf.json.JSONArray;


import com.car.dao.DaoImpl;
import com.common.CarNumber;
import com.orderSend.OrderQueue;
import com.sql.JdbcUtilsImp;

import connect.IXbeeMessageConnect;
import connect.impl.XbeeMessageConnect;
import connect.para.SendEndPointPara;


public class toContinue extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public toContinue() {
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
		System.out.println("这是运行toContinue");
		String sType = request.getParameter("type");
		String sTeam = request.getParameter("teamNumber");
		String sCar = request.getParameter("carNumber");
	
		JdbcUtilsImp imp = new JdbcUtilsImp();
		CarNumber carNum = new CarNumber();
		
		if(!sType.equals("") && sType != null && !sTeam.equals("") && sTeam != null 
				&& !sCar.equals("") && sCar != null){
			
			if(sType.equals("single")){
//				System.out.println("这是single");
				SendEndPointPara sendEnd = new SendEndPointPara();
				int team = Integer.valueOf(sTeam).intValue();
				int car =  Integer.valueOf(sCar).intValue();
				try {
					Map<String, Object> map = imp.getStopData(sType);
					
					sendEnd.setMacAddress(imp.getMacAddress(team, car));
					sendEnd.setSpeed(map.get("speed").toString());
					sendEnd.setRoadId(imp.getRoadIdByTeamAndCar(team, car)); 
					sendEnd.setCarNumber(carNum.getCarNumber(sTeam, sCar));
					sendEnd.setSwerveAngle(map.get("angle").toString());
					sendEnd.setSwerveFlow("0");
					sendEnd.setSwervePara(map.get("direction").toString());
					sendEnd.setConctrolOrder(true);
//					System.out.println("单个小车地址：" +sendEnd.getMacAddress());
//					System.out.println("单个小车速度："+sendEnd.getSpeed());
					
					OrderQueue orderQueue = new OrderQueue();
					orderQueue.joinCar1(sendEnd);
					//sendToCar(sendEnd);
					out.print("single success");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			} else if(sType.equals("team")){
//				System.out.println("这是team");
				int team = Integer.valueOf(sTeam).intValue();
				List<SendEndPointPara> outputlist = new ArrayList<SendEndPointPara>();
				
				try {
					Map<String,Object> map = imp.getStopData(sType);
//					System.out.println("map是=====" + map.size());
					long groupId = imp.getGroupIdbyGroupNum(team);
					List<String> listM = imp.listMacByGroupId(new Long(groupId).intValue());
					
					String sc ="";
					for(int i=0; i<listM.size(); i++){
						SendEndPointPara sendEnd = new SendEndPointPara();
						int a = imp.getCarNumByMacAddress(listM.get(i));
						sc = String.valueOf(a);
						
						sendEnd.setMacAddress(listM.get(i));
						sendEnd.setCarNumber(carNum.getCarNumber(sTeam, sc));
						sendEnd.setRoadId(imp.getRoadIdByTeamAndCar(team, a));
						sendEnd.setSpeed(map.get("speed").toString());
						sendEnd.setSwerveAngle("0");
						sendEnd.setSwerveFlow("0");
						sendEnd.setSwervePara("0");
						sendEnd.setConctrolOrder(true);
						outputlist.add(sendEnd);
					}
//					System.out.println("小车速度(team)："+outputlist.get(0).getSpeed());
					
					OrderQueue orderQueue = new OrderQueue();
					orderQueue.joinCar1(outputlist);
					//sendToCarGroup(outputlist);
					out.print("complex success");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if(sType.equals("all")){
				
//				System.out.println("这是All");
				List<SendEndPointPara> outputlist = new ArrayList<SendEndPointPara>();			
				
				List<String> listM = new ArrayList<String>();
				List<String> list = new ArrayList<String>();
				List<String> list1 = new ArrayList<String>();
				try {
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
					
					Map<String, Object> map = imp.getStopData(sType);
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
						output.setSpeed(map.get("speed").toString());
						output.setCarNumber(carNum.getCarNumber(st, sc));
						output.setSwerveFlow("0");
						output.setSwerveAngle("0");
						output.setSwervePara("0");
						output.setConctrolOrder(true);
						outputlist.add(output);
						
					}
//					System.out.println("小车速度(all)："+outputlist.get(0).getSpeed());
					OrderQueue orderQueue = new OrderQueue();
					orderQueue.joinCar1(outputlist);
					//sendToCarGroup(outputlist);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					out.print("cannot get data from DB");
					e.printStackTrace();
				}	
			}
				
		} else {
			out.print("get nothing form APP");
		}
	
		out.flush();
		out.close();
		
		if(!sType.equals("") && sType != null && !sTeam.equals("") && sTeam != null 
				&& !sCar.equals("") && sCar != null){
			DaoImpl dao = new DaoImpl();
			try {
				//System.out.println("");
				dao.updateStopOrder(sTeam,sCar,sType);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

//	public void sendToCar(SendEndPointPara outputEndPointPara) {
//	IXbeeMessageConnect conn = new XbeeMessageConnect();
//	conn.openComPort("COM7", 9600);
//	conn.sendMessage(outputEndPointPara);
//	conn.closeComPort();		
//	}
//	public void sendToCarGroup(List<SendEndPointPara> outputEndPointParaList){
//		IXbeeMessageConnect conn = new XbeeMessageConnect();
//		conn.openComPort("COM7", 9600);
//		conn.sendMessages(outputEndPointParaList);
//		conn.closeComPort();		
//		
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
