package com.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.car.dao.DaoImpl;
import com.common.CarFollowing;
import com.common.CarNumber;
import com.orderSend.OrderQueue;
import com.sql.JdbcUtilsImp;


import connect.IXbeeMessageConnect;
import connect.impl.XbeeMessageConnect;
import connect.para.SendEndPointPara;

public class getText extends HttpServlet{

	/**
	 * Constructor of the object.
	 */
	public getText() {
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
		String sCarNum = request.getParameter("carNumber");
		String sSpeed = request.getParameter("speed");
		String sDistance = request.getParameter("distance");
		String sAngle = request.getParameter("angle");
		String sDirect = request.getParameter("direction");
		String carNumber = "";
		

		System.out.println("方向是："+sDirect);
		
					
		
		JdbcUtilsImp imp = new JdbcUtilsImp();
		CarNumber carN = new CarNumber();
		
		//判断是对单个小车进行操作还是对车队进行操作
		if(!steamNum.equals("") && steamNum != null && !steamNum.equals("All") && !sCarNum.equals("") 
				&& sCarNum != null &&  sDistance.equals("no")  && !sAngle.equals("") && sAngle != null){			
			//对单个小车发送数据
			if( !sSpeed.equals("") && sSpeed != null && !sDirect.equals("") && sDirect != null){
				int car = Integer.valueOf(sCarNum).intValue();
				int angle = Integer.valueOf(sAngle).intValue();
				int team = Integer.valueOf(steamNum).intValue();
				
				SendEndPointPara singleOutput = new SendEndPointPara();
				singleOutput.setSpeed(sSpeed);
				singleOutput.setSwerveAngle(sAngle);
				carNumber = carN.getCarNumber(steamNum, sCarNum);
				singleOutput.setCarNumber(carNumber);
				singleOutput.setSwerveFlow("0");
				
				if(sDirect.equals("up"))
				{
					singleOutput.setSwervePara("0");//0 直行，1 左转，2 右转
				} else if(sDirect.equals("left")){
					singleOutput.setSwervePara("1");
				} else if(sDirect.equals("right")){
					singleOutput.setSwervePara("2");
				}
				
				try {
					singleOutput.setMacAddress(imp.getMacAddress(team,car));
					singleOutput.setRoadId(imp.getRoadIdByTeamAndCar(team, car));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					out.print("cannot get data from database");
					e.printStackTrace();
				}				
				
//				//此时对该辆小车后的车辆进行跟随操作
//				CarFollowing carfollow = new CarFollowing();
//				carfollow.setSpeed(sSpeed);
//				carfollow.setAngle(sAngle);
//				carfollow.setDirection(sDirect);
//				carfollow.startFollowing(team,car);
		
				/**
				 * 将当前的小车与小车所处车队前后车的车间距进行判断，根据判断结果来设置小车的最后速度
				 * 等待郭卫卫的文件  ---------在判断车间距的同时进行了速度设置
				 */
				singleOutput.setConctrolOrder(true);
				OrderQueue orderQueue = new OrderQueue();
				orderQueue.joinCar1(singleOutput);			
//				sendToCar(singleOutput);//调用接口向小车发送数据
				
				out.print("single success");
				
			} else if((sSpeed.equals("") || sSpeed == null) && !sDirect.equals("") && sDirect != null){
				out.print("未从APP获得速度");
			} else if(!sSpeed.equals("") && sSpeed != null && (sDirect.equals("") || sDirect == null)){
				out.print("未从APP获得方向");
			} else {
				out.print("未从APP获得速度和方向");
			}		
			
		} else if(!steamNum.equals("") && steamNum != null && steamNum.equals("All") 
				&& sCarNum.equals("no") && sAngle.equals("no") && !sDistance.equals("") 
				&& sDistance != null && !sSpeed.equals("") && sSpeed != null){
			//对所有小车进行操作
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
					output.setSpeed(sSpeed);
					output.setCarNumber(carN.getCarNumber(st, sc));
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
//			sendToCarGroup(outputlist);
			
			out.print("complex success");			
			
		}else if(!steamNum.equals("") && steamNum != null && !steamNum.equals("All") 
				&& sCarNum.equals("no") && sAngle.equals("no") && !sDistance.equals("") 
				&& sDistance != null && !sSpeed.equals("") && sSpeed != null){
			//对一个车队的小车进行操作
			List<SendEndPointPara> outputlist = new ArrayList<SendEndPointPara>();
			
			
			int team = Integer.valueOf(steamNum).intValue();
			List<String> list = new ArrayList<String>();			
			try {
				Long groupId = imp.getGroupIdbyGroupNum(team);
				list = imp.listMacByGroupId(groupId.intValue());
				
				
				String sc ="";
				for(int i=0;i<list.size();i++){
					SendEndPointPara output = new SendEndPointPara();
					int a = imp.getCarNumByMacAddress(list.get(i).toString());
					sc = String.valueOf(a);
					output.setMacAddress(list.get(i).toString());
					output.setRoadId(imp.getRoadIdByTeamAndCar(team,a));
					output.setSpeed(sSpeed);
					output.setCarNumber(carN.getCarNumber(steamNum, sc));
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
//			sendToCarGroup(outputlist);			
			out.print("complex success");
			
		} else {
			out.print("未从APP获得任何数据");;
		}
	
		
		out.flush();
		out.close();
		if(steamNum != null && !steamNum.equals("") && sCarNum.equals("no")){
			DaoImpl dao = new DaoImpl();
			try {//需要知道是不是每个小车都需要保存
				System.out.println("小车数据库");
				dao.updateOrderInfo(steamNum,sSpeed,sCarNum,sDistance,sAngle,sDirect);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println("小车数据库命令更新失败");
				e.printStackTrace();
			}
		}
	}
	
//	public void sendToCar(SendEndPointPara outputEndPointPara){
//		IXbeeMessageConnect conn = new XbeeMessageConnect();
//		conn.openComPort("COM7", 9600);
//		conn.sendMessage(outputEndPointPara);
//		conn.closeComPort();
//	}
//	
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
