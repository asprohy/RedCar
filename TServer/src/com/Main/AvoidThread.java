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
	            logger.info("��ʼִ������");  
	  
	            Avoid avoid = new Avoid();
	    		//int fleetId;
				try {
					avoid.JudgeAvoid();
					//avoid.avoidCarGruop(fleetId);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("���õ��ó���");
				}//С��������ʻ����������ڽ���·�����������б��ô������ر��õ�С���ӱ��	    		        
	  
	            isRunning = false;  
	            logger.info("����ִ�н�����");  
	  
	        } else {  
	            logger.info("��һ�α��û�δ����������������ִ�С�");  
	        }  
	  
	}

}
