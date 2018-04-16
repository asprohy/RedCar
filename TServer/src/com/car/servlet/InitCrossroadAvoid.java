package com.car.servlet;


import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.common.Avoid;

public class InitCrossroadAvoid extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public InitCrossroadAvoid() {
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
		timer.schedule(new CrossroadAvoid(), 1000, 200);
	}
	
	class CrossroadAvoid extends TimerTask{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			System.out.println("车队路口避让启动......");
			Avoid avoid = new Avoid();
			 try {
				avoid.JudgeAvoid();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println("避让出错");
				e.printStackTrace();
			}
		}
		
	}
}
