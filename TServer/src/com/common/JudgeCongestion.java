//package com.common;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//
//
//
//
//import java.util.Map.Entry;
//
//import com.car.dao.DaoImpl;
//import com.entity.CarState;
//import com.sql.JdbcUtilsImp;
//
//public class JudgeCongestion {
//
//	private int teamNum;
//	private int carNum;
//	
//	public static void main(String[] args) throws Exception {
//		// TODO Auto-generated method stub
//
//		JudgeCongestion JC = new JudgeCongestion();
//		
//	}
//	public JudgeCongestion() {
//		// TODO Auto-generated constructor stub
//	}
//	
//	public void Judge(){
//		DaoImpl dao = new DaoImpl();
//		List<CarState> list = new ArrayList<CarState>();
//	}
//	
////	public void Judge(){
////		try {
////			DaoImpl dao = new DaoImpl();
////			List<CarState> list = new ArrayList<CarState>();
////			
////			list = dao.getNewestCarInfo();
////			if(list.size() == 0){
////				return;
////			}else{
////				for(int i = 0;i < list.size();i++){
////					
//////					if(list.get(i).getSpeed() > 0.25){
//////						//�����⵽ӵ��״̬��С�����ٶȴ���ĳ��ֵ��˵��С�����ڶ³�����С��״̬��ӵ�£����Ҵ�С��Ҳ����ӵ��
//////							dao.updateCongestion1(list.get(i).getCar_id());
//////							continue;
//////						}
////					//���С���ٶȹ�С������С��һ��ʱ������ʻ����̣ܶ��ж�ӵ��
////					long sendTime = list.get(i).getSend_time();
////					long carId = list.get(i).getCar_id();
////					
////					List<CarState> list1 = dao.getOldCarState(carId,sendTime-1000);//1s֮ǰ���������ݣ�1sǰû��������ֱ��ѭ����һ��С��
////					if(list1.size() == 0){
////						continue;
////					}else{
////						if(list.get(i).getDrive_distance() - list1.get(0).getDrive_distance() < 2){//1s��С����ʻ����С��1m���ж��³�
////							dao.updateCongestion2(carId);
////						}else{
////							dao.updateCongestion1(list.get(i).getCar_id());
////						}
////					}
////					
////					
////				}
////			}
////			
////			
////		} catch (Exception e) {
////			// TODO Auto-generated catch block
////			System.out.println("�����ݿ��ȡ����ʧ��");
////			e.printStackTrace();
////		}    	
////	}
//
//}
