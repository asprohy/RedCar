package com.Main;

import java.util.TimerTask;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.common.Avoid;

public class AvoidThread extends TimerTask{

	 protected final Log logger = LogFactory.getLog(getClass());
	 private static boolean isRunning = false; 
	@Override
	public void run() {
		// TODO Auto-generated method stub
		 if (!isRunning) {  
			  
	            isRunning = true;  
	            logger.info("开始执行任务。");  
	  
	            Avoid avoid = new Avoid();
	    		//int fleetId;
				try {
					avoid.JudgeAvoid();
					//avoid.avoidCarGruop(fleetId);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("避让调用出错");
				}//小车车队行驶过程中如果在交叉路口遇见，进行避让处理，返回避让的小车队编号	    		        
	  
	            isRunning = false;  
	            logger.info("避让执行结束。");  
	  
	        } else {  
	            logger.info("上一次避让还未结束，本次任务不能执行。");  
	        }  
	  
	}

}
