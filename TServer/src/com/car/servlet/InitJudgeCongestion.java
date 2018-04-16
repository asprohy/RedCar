//package com.car.servlet;
//
//
//import java.util.Timer;
//import java.util.TimerTask;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServlet;
//
//import com.car.servlet.LoadClearData.Clear;
//import com.map.GetTrafficList;
//
//public class InitJudgeCongestion extends HttpServlet {
//
//	/**
//	 * Constructor of the object.
//	 */
//	public InitJudgeCongestion() {
//		super();
//	}
//
//	/**
//	 * Destruction of the servlet. <br>
//	 */
//	public void destroy() {
//		super.destroy(); // Just puts "destroy" string in log
//		// Put your code here
//	}
//
//	/**
//	 * Initialization of the servlet. <br>
//	 *
//	 * @throws ServletException if an error occurs
//	 */
//	public void init() throws ServletException {
//		// Put your code here
//		Timer timer = new Timer(); 
//		timer.schedule(new JCongestion(), 1000, 500);
//	}
//	class JCongestion extends TimerTask{
//
//		@Override
//		public void run() {
//			// TODO Auto-generated method stub
//			System.out.println("ÅÐ¶ÏÓµ¶ÂÆô¶¯......");
//			GetTrafficList jc = new GetTrafficList();
//			try {
//				jc.JudgeCongestion();
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//		
//	}
//
//}
