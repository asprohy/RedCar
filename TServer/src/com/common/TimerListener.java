//package com.common;
//
//import java.util.Timer;
//
//import javax.servlet.ServletContextEvent;
//import javax.servlet.ServletContextListener;
//
//public class TimerListener implements ServletContextListener {  
//	  
//    private Timer timer = null;  
//  
//    /**
//     * TestTask()��ִ�о����������
//     */
//    public void contextInitialized(ServletContextEvent event) {  
//        timer = new Timer(true);  
//        event.getServletContext().log("��ʱ��������");  
//        timer.scheduleAtFixedRate(new DistanceBetweenTeam(), 7 * 1000, 60 * 1000); // ÿСʱִ��һ��run����    ����5��5 * 1000;  
//    }  
//  
//    public void contextDestroyed(ServletContextEvent event) {  
//        if (timer != null) {  
//            timer.cancel();  
//            event.getServletContext().log("��ʱ������");  
//        }  
//    }  
//}  