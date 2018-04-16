//package com.Main;
//
//import java.util.Timer;
//
//import javax.servlet.ServletContextEvent;
//import javax.servlet.ServletContextListener;
//弃用
//public class JudgeChannelTimerListener implements ServletContextListener {
//
//	private Timer timer = null; 
//	public void contextDestroyed(ServletContextEvent event) {
//		// TODO Auto-generated method stub
//
//		timer = new Timer(true);
//		event.getServletContext().log("车道判断定时器启动");
//		timer.scheduleAtFixedRate(new JudgeChannelThread(), 3*60*1000, 2*100);		
//		
//	}
//
//	public void contextInitialized(ServletContextEvent event) {
//		// TODO Auto-generated method stub
//
//		if (timer != null) {  
//            timer.cancel();  
//            event.getServletContext().log("车道判断定时器销毁");  
//        }  
//	}
//
//}
