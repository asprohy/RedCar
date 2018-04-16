package com.Main;

import java.util.Timer;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ClearDataTimerListener implements ServletContextListener {


	private Timer timer = null; 
	public void contextDestroyed(ServletContextEvent event) {
		// TODO Auto-generated method stub

		timer = new Timer(true);
		event.getServletContext().log("清理旧数据定时器启动");
		timer.scheduleAtFixedRate(new ClearDataThread(), 5*60*1000, 2*100);
		
	}

	public void contextInitialized(ServletContextEvent event) {
		// TODO Auto-generated method stub

		if (timer != null) {  
            timer.cancel();  
            event.getServletContext().log("车队定时器销毁");  
        }  
	}

}
