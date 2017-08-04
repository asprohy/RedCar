package com.lyc.test;

import java.util.ArrayList;
import java.util.List;

import com.lyc.test.DaoImpl;
import com.lyc.test.CarState;


public class CollisionAvoidance {

	//private static boolean ture;

//	private double l = 19.6;//前后轴距
//	public static double turn_angle = 20;//车轮转向角度
//	public static double angle = Math.toRadians(90-turn_angle);
//	public static double width = 25;//单道路宽
//	public static double central_angle=0;//圆心角
//	public static double arc_length;//弧长
//	public static double time;//变道预计时间
//	public static double chord_length;
//	private double r = l/Math.sin(Math.toRadians(turn_angle));
	//double r = 57;
	//public static double v = 20;
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		System.out.println(getTime(20));
		System.out.println(getTime(60));
		long id = 3;
		long time=514081;
		CollisionAvoidance coll = new CollisionAvoidance();
		int i = coll.judge(id,time);

		if(i==1){
			System.out.println("请减速，您有追尾风险！！！");
		}else if(i==2){
			System.out.println("您的操作有碰撞风险！！！");
		}
		
		
	}
	
	public static int judge(long id,long time) throws Exception {//真实的时候去掉时间
		// TODO Auto-generated method stub

		boolean isCollision = false;//是否会碰撞
		//long id = 2;//根据小车id去查询小车数据
		List<CarState> list = new ArrayList<CarState>();//得到当前小车的两条行驶数据
		DaoImpl dao = new DaoImpl();
		list = dao.getCarInfoById(id,time);
		System.out.println(list.size());
		
		List<CarState> list1 = new ArrayList<CarState>();//得到所以小车的行驶数据
		list1 = dao.getCarInfo(time);

		//***保留住list1原始数据
		
//		int len = 0;
//		len = list1.size(); 
//		double index[] = new double[len];
//		for(int i=0;i<len;i++){
//			index[i] = list1.get(i).getDirive_distance1();
//		}
//		
	    //判断哪个是新数据
		//int new_data = judgeNewData(list);
		
		//System.out.println(new_data);
		//System.out.println(list1.size());
		
			
	//	System.out.println(list1.get(0).getDirive_distance1());
		//估计变道后小车的distance
		
		//System.out.println(list1.get(0).getDirive_distance1());
		
		   //***保留住list原始数据
//		double self_distance[] = new double[2];
//		String self_channnel[] = new String[2];
//		self_distance[0] = list.get(0).getDirive_distance();
//		self_channnel[0] = list.get(0).getChannel();
//		self_distance[1] = list.get(1).getDirive_distance();
//		self_channnel[1] = list.get(1).getChannel();
		
		//****************小车行驶过程中碰撞追尾估计
		if(list.get(0).getHeading_gree() == 20){
			//System.out.println(11);
			update(list1);//估计变道时间后车队坐标
			changeRoad(list);//估计变道后的小车坐标	
			isCollision = judgeCollision(list,list1);
			if(isCollision){
				return 2;//返回2说明有碰撞风险
			}
			//变成接口后这里可以返回碰撞字符串提示
			System.out.println(isCollision);
		}else{//防追尾估计
			
			isCollision = judgeRearEnd(list,list1);
			if(isCollision){
				return 1;//返回1说明是有追尾风险
			}
			//System.out.println(isCollision);
		}
		return 0;
           
      //****************  
		
	}


	


	private static int judgeNewData(List<CarState> list) {//判断哪个数据是最新数据,弃用
		// TODO Auto-generated method stub
		int newData;
		if(list.get(0).getSend_time() > list.get(1).getSend_time()){
			newData = 0;
		}else
			newData = 1;
		return newData;
	}


	public static boolean judgeCollision(List<CarState> list, List<CarState> list1) {
		// TODO Auto-generated method stub  
		//System.out.println(list.get(newData).getChannel());
		for(int i = 0;i<list1.size();i++){//估计小车变道后是否会发生碰撞
			if(list1.get(i).getCar_id() != list.get(0).getCar_id() &&
					list1.get(i).getChannel().equals(list.get(0).getChannel())
					){//如果发送时间一样和id不等并且是一条道
				double dex = 10;
				//System.out.println(dex);
				dex = list1.get(i).getDirive_distance1() - list.get(0).getDirive_distance1();
				//System.out.println(dex);
				if(Math.abs(dex) < 0.5){
					return true;
				}
			}
		}
		    return false;
	}
	
	private static boolean judgeRearEnd(List<CarState> list,
			List<CarState> list1) {
		// TODO Auto-generated method stub
		for(int i = 0;i<list1.size();i++){
			//System.out.println(new_data);
			if(list1.get(i).getCar_id() != list.get(0).getCar_id() &&
				 list1.get(i).getChannel().equals(list.get(0).getChannel())
				 ){//不同小车，同一条道，同一个时间的数据才会追尾
				//System.out.println(111);
				if(list1.get(i).getDirive_distance() > list.get(0).getDirive_distance() &&
					  list1.get(i).getSpeed() < list.get(0).getSpeed() ){//有其他车在当前小车前面，并且小车速度比前面的快
					double distance = list1.get(i).getDirive_distance() - list.get(0).getDirive_distance();
					double t = distance/(list.get(0).getSpeed() - list1.get(i).getSpeed());
					//System.out.println(t);
					if(t < 5){//如果时间小于5s，给出可能追尾提示
						return true;
					}
				}
			}
		}
		return false;
	}


	public static void changeRoad(List<CarState> list) {
		
		//String dir;//小车行驶方向
		//double[] result = new double[2];
		double l = 19.6;//前后轴距
		double turn_angle = 20;//车轮转向角度
		double angle = Math.toRadians(90-turn_angle);
		double width = 25;//单道路宽
		double central_angle=0;//圆心角
		double arc_length;//弧长
		double time;//变道预计时间
		double chord_length;
		double r = l/Math.sin(Math.toRadians(turn_angle));
		//double r = 57;
		double v = 20;//单位厘米
		
		for(int i=0;i<90;i++){//计算圆心角度
			
			double front = r*Math.sqrt(2*(1-Math.cos(Math.toRadians(i))))*Math.cos(angle-Math.toRadians(i)/2);
			double back = r*Math.sqrt(2*(1-Math.cos(Math.toRadians(i+1))))*Math.cos(angle-Math.toRadians(i+1)/2);
			//System.out.println(central_angle);
			//System.out.println(front);
			//System.out.println(back);
			if((front-width)*(back-width) <=0 && i != 0){
				central_angle = i+0.5;
				break;
			}
			
		}
		
		//System.out.println(central_angle);
		arc_length = Math.toRadians(central_angle)*r;
		//System.out.println(arc_length);
		//time = getTime(list.get(0).getSpeed());
		
		time = arc_length/v;
		//System.out.println(time);
		//chord_length = getChordLength();
		chord_length = width/(Math.tan(Math.toRadians(90-turn_angle-central_angle/2)));
		//System.out.println(chord_length);
		
		double distance = chord_length + list.get(0).getDirive_distance();
    	CarState car = list.get(0);
		car.setDirive_distance1(distance);
		
        if(list.get(0).getChannel() == "inside"){//如果前一个       			    
 		    car.setChannel("outside");		 		    
        }else
        	car.setChannel("inside");
        
        list.set(0, car);//改变目标
        
		
		
		//return result[2];
	}

	private static double getTime(double v) {
		// TODO Auto-generated method stub
		double l = 19.6;//前后轴距
		double turn_angle = 20;//车轮转向角度
		double angle = Math.toRadians(90-turn_angle);
		double width = 25;//单道路宽
		double central_angle=0;//圆心角
		double arc_length;//弧长
		double time =0;//变道预计时间
		//double chord_length;
		double r = l/Math.sin(Math.toRadians(turn_angle));
		//double r = 57;
		//double v = 20;//单位厘米
		
		for(int i=0;i<90;i++){//计算圆心角度
			
			double front = r*Math.sqrt(2*(1-Math.cos(Math.toRadians(i))))*Math.cos(angle-Math.toRadians(i)/2);
			double back = r*Math.sqrt(2*(1-Math.cos(Math.toRadians(i+1))))*Math.cos(angle-Math.toRadians(i+1)/2);
			//System.out.println(central_angle);
			//System.out.println(front);
			//System.out.println(back);
			if((front-width)*(back-width) <=0 && i != 0){
				central_angle = i+0.5;
				break;
			}
			
		}
		arc_length = Math.toRadians(central_angle)*r;
		time = arc_length/v;
		return time;
	}

//	private static double getChordLength() {
//		// TODO Auto-generated method stub
//		//return width/(Math.tan(Math.toRadians(angle)));
//	}
//	
	

	public static void update(List<CarState> list1) {//把小车的坐标
		// TODO Auto-generated method stub
		//List<carState> list = new ArrayList<carState>(list1);
		//list.addAll(list1);
		
		int len = list1.size();
		for(int i=0;i<len;i++){//
			
			double t = 3.6;//变道所需时间
			double speed = list1.get(i).getSpeed();
		    double distance = 0;
		    distance =	speed*t + list1.get(i).getDirive_distance();
		    
		 //   System.out.println(distance);
		    
		    CarState car = list1.get(i);
		    car.setDirive_distance1(distance);
		    list1.set(i, car);//改变目标
			
		    //System.out.println(list.get(i).getDirive_distance1());
		   // System.out.println(list1.get(i).getDirive_distance1());
		}
		
		//return list;
	}


}
