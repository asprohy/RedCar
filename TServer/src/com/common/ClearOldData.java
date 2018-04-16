package com.common;
import java.util.ArrayList;
import java.util.List;

import com.car.dao.DaoImpl;
import com.entity.CarState;


public class ClearOldData {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		ClearOldData clear = new ClearOldData();
		clear.clearData();//小车行驶过程中，每行驶一圈，给小车的旧数据清除
	}

	public void clearData() throws Exception {
		// TODO Auto-generated method stub
		DaoImpl dao = new DaoImpl();
		List<CarState> list = new ArrayList<CarState>();
		list = dao.getNewestCarInfo();//清除小车3s前的数据
	
		long maxHall;
		for(int i=0;i<list.size();i++){
			if(list.get(i).getHistory_hall_count() == 1){//最新霍尔是1
				maxHall = dao.getMaxHallById(list.get(i).getCar_id());//得到该车的最大霍尔计数，判断小车是否行驶一圈
				if(maxHall - 1 > 20){
					dao.clearOldData(list.get(i).getCar_id(),list.get(i).getSend_time());
				}
			}else{
				dao.clearOldData(list.get(i).getCar_id(),list.get(i).getSend_time() - 5000);
			}
		}
	}

}
