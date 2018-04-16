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
		clear.clearData();//С����ʻ�����У�ÿ��ʻһȦ����С���ľ��������
	}

	public void clearData() throws Exception {
		// TODO Auto-generated method stub
		DaoImpl dao = new DaoImpl();
		List<CarState> list = new ArrayList<CarState>();
		list = dao.getNewestCarInfo();//���С��3sǰ������
	
		long maxHall;
		for(int i=0;i<list.size();i++){
			if(list.get(i).getHistory_hall_count() == 1){//���»�����1
				maxHall = dao.getMaxHallById(list.get(i).getCar_id());//�õ��ó����������������ж�С���Ƿ���ʻһȦ
				if(maxHall - 1 > 20){
					dao.clearOldData(list.get(i).getCar_id(),list.get(i).getSend_time());
				}
			}else{
				dao.clearOldData(list.get(i).getCar_id(),list.get(i).getSend_time() - 5000);
			}
		}
	}

}
