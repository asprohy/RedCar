package com.orderSend;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import connect.IXbeeMessageConnect;
import connect.impl.XbeeMessageConnect;
import connect.para.SendEndPointPara;

public class OrderQueue {

	// static Queue<SendEndPointPara> queue = new
	// LinkedList<SendEndPointPara>();
	// static PriorityQueue pq = new PriorityQueue();
	static Queue<Object> queue1 = new LinkedList<Object>();

	// public static

	public void joinCar1(Object outputEndPointPara) {
		// IXbeeMessageConnect conn = new XbeeMessageConnect();
		queue1.offer(outputEndPointPara);
		// conn.openComPort("COM7", 9600);
		while (queue1.size() != 0) {
			if (queue1.peek().getClass().getName().equals("connect.para.SendEndPointPara")) {

				// conn.sendMessage((SendEndPointPara) outputEndPointPara);
				sendToCar((SendEndPointPara) queue1.peek());
				System.out.println("单个小车");
			} else {
				List<SendEndPointPara> outputEndPointParaList1 = (List<SendEndPointPara>) queue1.peek();
				// conn.sendMessages(outputEndPointParaList1);
				sendToCarGroup(outputEndPointParaList1);
				System.out.println("一队小车");
			}
			System.out.println(queue1.peek().getClass().getName());
			System.out.println(queue1.size());
			queue1.poll();
			System.out.println(queue1.size());
		}

		// conn.closeComPort();
	}

	// public void joinCar(SendEndPointPara outputEndPointPara) {
	// queue.offer(outputEndPointPara);
	// //pq.add(outputEndPointPara);
	// System.out.println(queue.peek());
	//// conn.openComPort("COM7", 9600);
	//// conn.sendMessage(outputEndPointPara1);
	//// conn.closeComPort();
	// System.out.println(queue.size());
	// queue.poll();
	//
	// }
	//
	// public void joinCarGroup(List<SendEndPointPara> outputEndPointParaList) {
	// //List<SendEndPointPara> outputEndPointParaList1 =
	// (List<SendEndPointPara>) queue.peek();
	// //queue.offer(outputEndPointParaList);
	// System.out.println(queue.peek().getClass().getName());
	//// conn.openComPort("COM7", 9600);
	//// conn.sendMessages(outputEndPointParaList1);
	//// conn.closeComPort();
	// System.out.println(queue.size());
	// queue.poll();
	// }

	public void sendToCar(SendEndPointPara outputEndPointPara) {
		// try{
		// CommPortIdentifier com;
		// com = CommPortIdentifier.getPortIdentifier("COM6");
		IXbeeMessageConnect conn = new XbeeMessageConnect();
		//
		// while(!com.isCurrentlyOwned()){
		// System.out.println("接口占用中，请等待");
		// try {
		// Thread.sleep(200);
		// } catch (InterruptedException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// };
		// }
		System.out.println("---------BeginSendToCar---------");
		    System.out.println("merge " +outputEndPointPara.getMacAddress() + " " + outputEndPointPara.getRoadId() + " "
				+ outputEndPointPara.getSpeed() + " " + outputEndPointPara.getCarNumber() + " " + outputEndPointPara.getSwerveFlow() + " "
				+ outputEndPointPara.getSwerveAngle());
		    System.out.println("---------EndSendToCar---------");
		if (!outputEndPointPara.isConctrolOrder()) {
			// if (!outputEndPointPara.getSwerveFlow().equals("0")) {
			// int count = 0;
			// while (count < 3) {
			// conn.openComPort("COM10", 9600);
			// conn.sendMessage(outputEndPointPara);
			// conn.closeComPort();
			// count++;
			// try {
			// Thread.sleep(200);
			// } catch (InterruptedException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }
			// }
			// } else {
		    
			int count = 0;
			while (count < 2) {
				//conn.openComPort("COM10", 9600);
				//conn.sendMessage(outputEndPointPara);
				//conn.closeComPort();
				count++;
			}
			// }
		} else {
			int count = 0;
			while (count < 2) {
				//conn.openComPort("COM6", 9600);
				//conn.sendMessage(outputEndPointPara);
				//conn.closeComPort();
				count++;
			}
		}

		// } catch (NoSuchPortException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
	}

	public void sendToCarGroup(List<SendEndPointPara> outputEndPointParaList) {
		// try {
		// CommPortIdentifier com;
		// com = CommPortIdentifier.getPortIdentifier("COM6");
		IXbeeMessageConnect conn = new XbeeMessageConnect();
		// while(!com.isCurrentlyOwned()){
		// System.out.println("接口占用中，请等待");
		// try {
		// Thread.sleep(200);
		// } catch (InterruptedException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// }
		System.out.println("---------BeginSendToGroup---------");
		for (SendEndPointPara outputEndPointPara : outputEndPointParaList) {
		    
		    System.out.println("merge " +outputEndPointPara.getMacAddress() + " " + outputEndPointPara.getRoadId() + " "
				+ outputEndPointPara.getSpeed() + " " + outputEndPointPara.getCarNumber() + " " + outputEndPointPara.getSwerveFlow() + " "
				+ outputEndPointPara.getSwerveAngle());
			if (outputEndPointPara.isConctrolOrder()) {
				//conn.openComPort("COM10", 9600);
				//conn.sendMessage(outputEndPointPara);
				//conn.closeComPort();
			} else {
				//conn.openComPort("COM6", 9600);
				//conn.sendMessage(outputEndPointPara);
				//conn.closeComPort();
			}
		}
		System.out.println("---------EndSendToGroup---------");
		// conn.openComPort("COM6", 9600);
		// conn.sendMessages(outputEndPointParaList);
		// conn.closeComPort();
		// } catch (NoSuchPortException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
	}
}
