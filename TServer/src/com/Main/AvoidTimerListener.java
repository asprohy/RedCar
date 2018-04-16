package com.Main;

import java.util.Timer;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class AvoidTimerListener implements ServletContextListener{

	private Timer timer = null; 
	public void contextDestroyed(ServletContextEvent event) {
		// TODO Auto-generated method stub

		timer = new Timer(true);
		event.getServletContext().log("���ӱ��ö�ʱ������");
		timer.scheduleAtFixedRate(new AvoidThread(), 5*60*1000, 2*100);
		
	}

	public void contextInitialized(ServletContextEvent event) {
		// TODO Auto-generated method stub

		if (timer != null) {  
            timer.cancel();  
            event.getServletContext().log("���ӱ��ö�ʱ������");  
        }  
	}
}
