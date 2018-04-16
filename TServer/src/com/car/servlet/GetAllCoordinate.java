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

import com.car.tool.JsonTools;
import com.entity.CarBasicInfo;
import com.map.CarHelper;
import com.map.Coordinate;
import com.map.MapDaoImpl;

public class GetAllCoordinate extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public GetAllCoordinate() {
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
		PrintWriter out = null;
		System.out.println("这是GetAllCoordinate");
		
		MapDaoImpl dao = new MapDaoImpl();
		List<CarBasicInfo> list1 = new ArrayList<CarBasicInfo>();
		
		List<Coordinate> list = new ArrayList<Coordinate>();
		try{
			list1 = dao.getCarBasicInfo();
			if(list1.size() == 0){
				out.print("数据为空！");
			}else{
				for(int i = 0;i<list1.size();i++){
					long carId = list1.get(i).getId();
					Coordinate coordinate=null;					
					coordinate = CarHelper.getCarSiteInMap(carId);
					list.add(coordinate);
				}
				String jsonString;
				jsonString = JsonTools.createJsonString("Coordinate",list);
				//JSONArray jsonArray = JSONArray.fromObject(list);
				out = response.getWriter();	
				System.out.println(jsonString);
				out.print(jsonString);
			}			
		}catch(Exception e){
			e.printStackTrace();
		}
					
		out.flush();
		out.close();
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
