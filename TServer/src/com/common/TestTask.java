package com.common;

import java.util.TimerTask;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TestTask extends TimerTask {  
    protected final Log logger = LogFactory.getLog(getClass());  
  
    private static boolean isRunning = false;  
  
    @Override  
    public void run() {   
  
        if (!isRunning) {  
  
            isRunning = true;  
            logger.info("��ʼִ������");  
  
            int i = 0;  
            while (i++ < 10) {  
                logger.info("����������" + i + "/" + 10);  
            }  
  
            isRunning = false;  
            logger.info("����ִ�н�����");  
  
        } else {  
            logger.info("��һ������ִ�л�δ����������������ִ�С�");  
        }  
  
    }  
}
