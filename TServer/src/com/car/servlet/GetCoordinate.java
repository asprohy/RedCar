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

import com.car.dao.DaoImpl;
import com.car.tool.JsonTools;
import com.map.CarHelper;
import com.map.Coordinate;
import com.map.MapDaoImpl;

public class GetCoordinate extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public GetCoordinate() {
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
		
		System.out.println("这是运行doGET");
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
	
		String id = request.getParameter("carId");
		//MapDaoImpl dao = new MapDaoImpl();
		try{
			if(!id.equals("") && id!=null){
				long carId = Long.valueOf(id).longValue();
				Coordinate coordinate = CarHelper.getCarSiteInMap(carId);
				
				List<Coordinate> list = new ArrayList<Coordinate>();
				list.add(coordinate);
				String jsonString = JsonTools.createJsonString("Coordinate", list);
				//JSONArray jsonArray = JSONArray.fromObject(list);	
				
				out.println(jsonString);
			}else{
				System.out.println("fail get id from client");
			}
		}catch(Exception e){			
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
