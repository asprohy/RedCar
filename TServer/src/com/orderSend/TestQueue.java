package com.orderSend;

import java.util.ArrayList;
import java.util.List;

import com.entity.CarState;

import connect.para.SendEndPointPara;

public class TestQueue {

	/**
	 * @param args
	 * 
	 */
	
	public static void main(String[] args) {
		 
		// TODO Auto-generated method stub
		SendEndPointPara outputEndPointPara = new SendEndPointPara();
		OrderQueue orderQueue = new OrderQueue();
		orderQueue.joinCar1(outputEndPointPara);
//		orderQueue.queue.add(outputEndPointPara);
		OrderQueue orderQueue2 = new OrderQueue();
		orderQueue2.joinCar1(outputEndPointPara);
//		orderQueue2.joinCar(outputEndPointPara);
		
		
		List<SendEndPointPara> outputEndPointParaList = new ArrayList<SendEndPointPara>();
		
		orderQueue.joinCar1(outputEndPointParaList);
		
	}

}
