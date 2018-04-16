package com.car.servlet;


import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.common.ClearOldData;

public class LoadClearData extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public LoadClearData() {
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
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
		Timer timer = new Timer(); 
		timer.schedule(new Clear(), 20000, 500);
	}
	
	class Clear extends TimerTask{ 
        public void run() {
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
    } 

}
