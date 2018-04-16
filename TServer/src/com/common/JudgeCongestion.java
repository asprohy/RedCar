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
//////						//如果检测到拥堵状态的小车的速度大于某个值，说明小车不在堵车，将小车状态不拥堵，并且此小车也不会拥堵
//////							dao.updateCongestion1(list.get(i).getCar_id());
//////							continue;
//////						}
////					//如果小车速度过小，并且小车一段时间内行驶距离很短，判定拥堵
////					long sendTime = list.get(i).getSend_time();
////					long carId = list.get(i).getCar_id();
////					
////					List<CarState> list1 = dao.getOldCarState(carId,sendTime-1000);//1s之前附件的数据，1s前没有数据则直接循环下一辆小车
////					if(list1.size() == 0){
////						continue;
////					}else{
////						if(list.get(i).getDrive_distance() - list1.get(0).getDrive_distance() < 2){//1s内小车行驶距离小于1m，判定堵车
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
////			System.out.println("从数据库获取数据失败");
////			e.printStackTrace();
////		}    	
////	}
//
//}
