//package com.Main;
//
//import java.util.Timer;
//
//import javax.servlet.ServletContextEvent;
//import javax.servlet.ServletContextListener;
//
//public class JudgeCongestionTimerListener implements ServletContextListener {
//
//	private Timer timer = null; 
//	public void contextDestroyed(ServletContextEvent event) {
//		// TODO Auto-generated method stub
//
//		timer = new Timer(true);
//		event.getServletContext().log("Óµ¶ÂÅÐ¶Ï¶¨Ê±Æ÷Æô¶¯");
//		timer.scheduleAtFixedRate(new JudgeCongestionThread(), 60*1000, 2*100);		
//		
//	}
//
//	public void contextInitialized(ServletContextEvent event) {
//		// TODO Auto-generated method stub
//
//		if (timer != null) {  
//            timer.cancel();  
//            event.getServletContext().log("Óµ¶Â¶¨Ê±Æ÷Ïú»Ù");  
//        }  
//	}
//}
