package com.car.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.car.dao.DaoImpl;
import com.car.tool.JsonTools;
import com.common.Avoid;
import com.common.ClearOldData;
import com.common.CollisionAvoidance;
import com.common.JudgeDistance;
import com.common.JudgeGroupDistance;
import com.common.RoadPlanning;
import com.common.SortCar;
import com.entity.CarBasicInfo;
import com.entity.ShuntId;
import com.map.CarHelper;
import com.map.CongestionCoordinate;
import com.map.Coordinate;
import com.map.GetTrafficList;
import com.map.MapDaoImpl;
import com.map.PredictiveCongestion;
import com.map.RoadPlan;
import com.map.TrafficJam;

public class GetStateInfo extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public GetStateInfo() {
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

		response.setContentType("text/html");
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

		long spendTimeStart = System.currentTimeMillis();
		System.out.println("spendTimeStart: " + spendTimeStart);
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		String id = request.getParameter("carId");
		String congestion = request.getParameter("congestion");
		String preCongestion = request.getParameter("preCongestion");
		String plan = request.getParameter("plan");
		String collision = request.getParameter("collision");
		//System.out.println("Serverlet run...    ");
		long tagx = System.currentTimeMillis();
		MapDaoImpl dao = new MapDaoImpl();
		DaoImpl daoD = new DaoImpl();
		long tagy = System.currentTimeMillis();
		System.out.println("tagxy "+(tagy-tagx));
		try{
			if(id != null && !id.equals("")){//
				//List<Object> listobj = new ArrayList<Object>();
				JSONObject jsonObject=new JSONObject();
				//System.out.println(id);
				if(id.equals("all")){
					//传递全局坐标
					//System.out.println("111");
					long tag1 = System.currentTimeMillis();
					System.out.println("tag1: " + tag1);
					List<CarBasicInfo> list1 = new ArrayList<CarBasicInfo>();				
					List<Coordinate> list = new ArrayList<Coordinate>();
					list1 = dao.getCarBasicInfo();
					
					long tag2 = System.currentTimeMillis();
					System.out.println("tag2: " + tag2);
					//System.out.println("list1.size()"+list1.size());
					if(list1.size() == 0){
						out.print("数据为空！");
					}else{
						long tag3 = System.currentTimeMillis();
						System.out.println("tag3: " + tag3);
						for(int i = 0;i<list1.size();i++){
							long carId = list1.get(i).getId();
							//System.out.println("carId");
							Coordinate coordinate= CarHelper.getCarSiteInMap(carId);
							//System.out.println(coordinate.getSpeed());
							coordinate.setCarId(carId);
							list.add(coordinate);
						}
						long tag4 = System.currentTimeMillis();
						System.out.println("tag4: " + tag4);
						//String jsonString = JsonTools.createJsonString("Coordinate",list);
						//JSONArray jsonArray = JSONArray.fromObject(list);
						out = response.getWriter();	
						//System.out.println(jsonString);
						//listobj.add(jsonString);
						//out.print(jsonString);
						jsonObject.put("Coordinate",list);
						long tag5 = System.currentTimeMillis();
						System.out.println("tag5: " + tag5);
					}			
					//传递拥堵信息
					if(congestion.equals("congestion")){
						//传递拥堵信息
						long tag6 = System.currentTimeMillis();
						System.out.println("tag6: " + tag6);
						GetTrafficList trafficInfo = new GetTrafficList();
						List<CongestionCoordinate> list0 = trafficInfo.JudgeCongestion();//按单个车来得到坐标对，放到一个list
						//List<CongestionCoordinate> list0 = TrafficJam.hallToJamInfo(1, "inside", 0 ,87);
						//String jsonString0 = JsonTools.createJsonString("Congestion", list0);
						//listobj.add(jsonString0);
						//out.print(jsonString0);
						jsonObject.put("Congestion",list0);
						long tag7 = System.currentTimeMillis();
						System.out.println("tag7: " + tag7);
					}
					
					if(!preCongestion.isEmpty() && preCongestion != null && preCongestion.equals("preCongestion")){
						PredictiveCongestion trafficInfo = new PredictiveCongestion();
						List<CongestionCoordinate> list3 = trafficInfo.PreCongestion();//按单车队来得到坐标对，放到一个list
						
						jsonObject.put("PreCongestion",list3);
						
					}
					
					long tag8 = System.currentTimeMillis();
					System.out.println("tag8: " + tag8);
					
					//添加需要显示的分流小车
					List<ShuntId> shuntIdList = dao.getShuntCarId();
					
					jsonObject.put("Shunt",shuntIdList);
					//long tag9 = System.currentTimeMillis();
					//System.out.println("tag9: " + tag9);
					//调用车距调控
				}else{//单车显示
					long carId = Long.valueOf(id).longValue();
					//传递坐标信息
					//System.out.println("carId"+carId);
					Coordinate coordinate = CarHelper.getCarSiteInMap(carId);					
					List<Coordinate> list = new ArrayList<Coordinate>();
					list.add(coordinate);
					//String jsonString = JsonTools.createJsonString("Coordinate", list);					
					//listobj.add(jsonString);
					//out.println(jsonString);
					jsonObject.put("Coordinate",list);
					long tag8 = System.currentTimeMillis();
					System.out.println("tag8: " + tag8);
					if(!congestion.isEmpty()&&congestion != null&&congestion.equals("congestion")){
						//传递拥堵信息
						GetTrafficList trafficInfo = new GetTrafficList();
						List<CongestionCoordinate> list1 = trafficInfo.JudgeCongestion();//按车队来得到坐标对，放到一个list
						//String jsonString1 = JsonTools.createJsonString("Congestion", list1);
						//listobj.add(jsonString1);
						//out.print(jsonString1);
						jsonObject.put("Congestion",list1);
					}
					
					if(!preCongestion.isEmpty() && preCongestion != null && preCongestion.equals("preCongestion")){
						PredictiveCongestion trafficInfo = new PredictiveCongestion();
						List<CongestionCoordinate> list3 = trafficInfo.PreCongestion();//按单车队来得到坐标对，放到一个list
						
						jsonObject.put("PreCongestion",list3);
						
					}
					
					long tag9 = System.currentTimeMillis();
					System.out.println("tag9: " + tag9);
					//System.out.println("plan"+plan);
					if(plan != null&&plan.equals("plan")){
						//传递规划信息
						RoadPlanning roadP = new RoadPlanning();
						List<RoadPlan> road = roadP.roadPlan();
						//String jsonString2 = JsonTools.createJsonString("RoadPlan", road);
						//listobj.add(jsonString2);
						//out.print(jsonString2);
						jsonObject.put("RoadPlan",road);
						
					}
					
					//System.out.println("collision"+collision);
					if(collision != null&&collision.equals("collision")){
						//传递碰撞信息
						CollisionAvoidance collA = new CollisionAvoidance();
						int collInfo = collA.judge(carId);
						//String jsonString3 = JsonTools.createJsonString("Collision", collInfo);
						//listobj.add(jsonString3);
						//out.print(jsonString3);
						if(collInfo == 0){//如果小车没有风险，判断小车前后是否有风险
							collInfo = collA.alert(carId);
						}
						jsonObject.put("Collision",collInfo);
						
					}	
					System.out.println("111:"+System.currentTimeMillis());
					
					JudgeDistance jd = new  JudgeDistance();
					JudgeGroupDistance jgd = new JudgeGroupDistance();
					
					int distanceState = jd.allDistance((int)carId);
					int groupDistanceState = jgd.allGroupDistance((int)daoD.getGroupIdById(carId));
					
					System.out.println("111:"+System.currentTimeMillis());
					jsonObject.put("Distance", distanceState);//返回小车间距状态
				}
				//JSONArray jsonArray = JSONArray.fromObject(listobj);
				//jsonObject.toString();
				long spendTimeEnd = System.currentTimeMillis();
			    System.out.println("SpendTime:"+String.valueOf((spendTimeEnd-spendTimeStart)));
				System.out.println(jsonObject.toString());
				out.print(jsonObject.toString());
			}
		}catch(Exception e){
			System.out.println("没能传递信息");
			e.printStackTrace();
		}
		
		out.flush();
		out.close();
		
		System.out.println("Sort Cars.....");
		SortCar.initSequceFlage();
		
		
		System.out.println("车队路口避让启动......");
		Avoid avoid = new Avoid();
		 try {
			avoid.JudgeAvoid();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("避让出错");
			e.printStackTrace();
		}
		 
		System.out.println("距离监控启动......");
		 JudgeDistance jd = new  JudgeDistance();
		 try {
			jd.allDistance(1);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		 
		System.out.println("检测清除旧数据.....");
		ClearOldData clear = new ClearOldData();
		try {
			clear.clearData();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("清除数据异常");
			e.printStackTrace();
			
		}
		
		
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
		
	}

}
