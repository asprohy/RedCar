package com.Main;

import java.util.TimerTask;

import com.common.ClearOldData;

public class ClearDataThread extends TimerTask {

	@Override
	public void run() {
		// TODO Auto-generated method stub

		ClearOldData clear = new ClearOldData();
		try {
			clear.clearData();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("旧数据清理出错！");
		}
	}

}
