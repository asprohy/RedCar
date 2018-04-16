//package com.common;
//import java.util.ArrayList;
//import java.util.List;
//
//import com.car.dao.DaoImpl;
//import com.entity.CarState;
//import com.entity.CarWarnInfo;
//
//弃用
//public class JudgeChannel {
//
//	/**
//	 * @param args
//	 * @throws Exception 
//	 */
//	public static void main(String[] args) throws Exception {
//		// TODO Auto-generated method stub
//
//		JudgeChannel judge = new JudgeChannel();
//		int result = judge.judgeC();//判定内外道，在小车更进入车道的前期进行小车内外道的判定，所有小车上路后，线程可以回收
//	
//		System.out.println("result:"+result);
//	}
//
//	public int judgeC() throws Exception {
//		// TODO Auto-generated method stub
//		DaoImpl dao = new DaoImpl();
//		List<CarWarnInfo> list = new ArrayList<CarWarnInfo>();
//		
//		list = dao.getCarWarnInfo();//得到小车预警信息
//		//System.out.println(list.get(0).getCar_id());
//		if(list.size() != 0){
//			for(int i=0;i<list.size();i++){
//				long id = list.get(i).getCar_id();
//				List<CarState> list1 = new ArrayList<CarState>();
//				list1 = dao.getCarAllInfoById(id);
//				//System.out.println(id);
//				if(list1.get(list1.size()-1).getHistory_hall_count()<2)
//					continue;
//				else{
//					//System.out.println(111);
//					for(int j =1;j<list1.size();j++){
//						//System.out.println(list1.get(j).getDirive_distance());
//						//System.out.println(list1.get(0).getDirive_distance());
//						double distance =list1.get(j).getDrive_distance() - list1.get(0).getDrive_distance();
//						long hall = list1.get(j).getHistory_hall_count() - list1.get(0).getHistory_hall_count();
//						//System.out.println(distance);
//						if((distance>=0.13 && distance<=0.16) && hall==1){//内道
////							CarWarnInfo carWarnInfo = new CarWarnInfo();
////							carWarnInfo.setChannel("inside");
////							list.set(i, carWarnInfo);
//							//System.out.println(list1.get(j).getId());
//							String channel = "inside";
//							//System.out.println(channel);
//							dao.updateCarChannel(id,channel);
//							break;
//						}else if((distance>=0.18 && distance<=0.22) && hall==1){//外道
////							CarWarnInfo carWarnInfo = new CarWarnInfo();
////							carWarnInfo.setChannel("outside");
////							list.set(i, carWarnInfo);
//							//System.out.println(list1.get(j).getHistory_hall_count());
//							String channel = "outside";
//							//System.out.println(channel);
//							dao.updateCarChannel(id,channel);
//							break;
//						}				
//							
//					}
//					
//				}
//			
//			}
//			return 1;
//		}else{
//			return 0;
//		}
//		
//	}
//
//}
