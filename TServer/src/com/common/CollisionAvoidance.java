package com.common;

import java.util.ArrayList;
import java.util.List;

import com.car.dao.DaoImpl;
import com.entity.CarState;
import com.map.CarHelper;
import com.map.Coordinate;


public class CollisionAvoidance {
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		System.out.println(111);
		long id = 1;
		//long time=514081;
		CollisionAvoidance coll = new CollisionAvoidance();
		int i = coll.judge(id);//С����ʻ������ʱ�̼��С���Ƿ�ᷢ����ײ����׷β��ʵ�ʵ�ʱ��ʱ��ȥ��
		
		if(i==1){
			System.out.println("����٣�����׷β���գ�����");
		}else if(i==2){
			System.out.println("���Ĳ�������ײ���գ�����");
		}else if(i == 0){
			System.out.println("wu���գ�����");
		}
		
		
	}
	
	public static int judge(long id) throws Exception {//��ʵ��ʱ��ȥ��ʱ��
		// TODO Auto-generated method stub

		boolean isCollision = false;//�Ƿ����ײ
		//long id = 2;//����С��idȥ��ѯС������
		List<CarState> list = new ArrayList<CarState>();//�õ���ǰС����������ʻ����
		DaoImpl dao = new DaoImpl();
		list = dao.getCarInfoById(id);
		
		if(list.size()==0){
			return 0;
		}
		//System.out.println(list.size());
		
		List<CarState> list1 = new ArrayList<CarState>();//�õ�����С������ʻ����
		list1 = dao.getCarInfo();
		
		if(list1.size()==0){
			return 0;
		}
		
		//***����סlist1ԭʼ����
		
//		int len = 0;
//		len = list1.size(); 
//		double index[] = new double[len];
//		for(int i=0;i<len;i++){
//			index[i] = list1.get(i).getDirive_distance1();
//		}
//		
	    //�ж��ĸ���������
		//int new_data = judgeNewData(list);
		
		//System.out.println(new_data);
		//System.out.println(list1.size());
		
			
	//	System.out.println(list1.get(0).getDirive_distance1());
		//���Ʊ����С����distance
		
		//System.out.println(list1.get(0).getDirive_distance1());
		
		   //***����סlistԭʼ����
//		double self_distance[] = new double[2];
//		String self_channnel[] = new String[2];
//		self_distance[0] = list.get(0).getDirive_distance();
//		self_channnel[0] = list.get(0).getChannel();
//		self_distance[1] = list.get(1).getDirive_distance();
//		self_channnel[1] = list.get(1).getChannel();
		
		//****************С����ʻ��������ײ׷β����
		if(list.get(0).getHeading_degree() == 20){
			//System.out.println(11);
			update(list1);//���Ʊ��ʱ��󳵶�����
			changeRoad(list);//���Ʊ�����С������	
			isCollision = judgeCollision(list,list1);
			if(isCollision){
				return 2;//����2˵������ײ����
			}
			//��ɽӿں�������Է�����ײ�ַ�����ʾ
			System.out.println(isCollision);
		}else{//��׷β����
			
			isCollision = judgeRearEnd(list,list1);
			if(isCollision){
				return 1;//����1˵������׷β����
			}
			//System.out.println(isCollision);
		}
		return 0;
           
      //****************  
		
	}


	


	private static int judgeNewData(List<CarState> list) {//�ж��ĸ���������������,����
		// TODO Auto-generated method stub
		int newData;
		if(list.get(0).getSend_time() > list.get(1).getSend_time()){
			newData = 0;
		}else
			newData = 1;
		return newData;
	}


	public static boolean judgeCollision(List<CarState> list, List<CarState> list1) throws Exception {
		// TODO Auto-generated method stub  
		//System.out.println(list.get(newData).getChannel());
		
		//�з�����Ԥ��  �ظ��ĳ������֣��ڵ��������������ڵ�
		if(list.get(0).getRoad_id() == 3 && list.get(0).getChannel().equals("inside") &&
				list.get(0).getHistory_hall_count() > 18 && list.get(0).getHistory_hall_count() < 26
				){
			Coordinate coordinate1 = CarHelper.getCarSiteInMap(list.get(0).getCar_id());
			for(int i = 0;i<list1.size();i++){
				if(list1.get(i).getCar_id() != list.get(0).getCar_id() &&
						list1.get(i).getChannel().equals("outside") &&list1.get(i).getRoad_id() ==2
						){//���ȿ�����û������С�����ص���λ��
					if(list1.get(i).getHistory_hall_count() > 20 && list1.get(i).getHistory_hall_count() < 29){
						Coordinate coordinate2 = CarHelper.getCarSiteInMap(list1.get(0).getCar_id());
						double distance = Math.sqrt(Math.pow((coordinate1.getX()-coordinate2.getX()), 2) + Math.pow((coordinate1.getY()-coordinate2.getY()), 2));
						if(distance < 20){
							return true;
						}
					}					
				}
			}//			
		}else if(list.get(0).getRoad_id() == 3 && list.get(0).getChannel().equals("outside") &&
				list.get(0).getHistory_hall_count() > 10 && list.get(0).getHistory_hall_count() < 25){
			Coordinate coordinate1 = CarHelper.getCarSiteInMap(list.get(0).getCar_id());
			for(int i = 0;i<list1.size();i++){
				if(list1.get(i).getCar_id() != list.get(0).getCar_id() &&
						list1.get(i).getChannel().equals("inside") &&list1.get(i).getRoad_id() ==2
						){//���ȿ�����û������С�����ص���λ��
					if(list1.get(i).getHistory_hall_count() > 36 && list1.get(i).getHistory_hall_count() < 51){
						Coordinate coordinate2 = CarHelper.getCarSiteInMap(list1.get(0).getCar_id());
						double distance = Math.sqrt(Math.pow((coordinate1.getX()-coordinate2.getX()), 2) + Math.pow((coordinate1.getY()-coordinate2.getY()), 2));
						if(distance < 20){
							return true;
						}
					}					
				}
			}//		
		}
		
		
		
		for(int i = 0;i<list1.size();i++){//����С��������Ƿ�ᷢ����ײ
								
			//������ʻ��
			if(list1.get(i).getCar_id() != list.get(0).getCar_id() &&
					list1.get(i).getChannel().equals(list.get(0).getChannel())
					){//���id���Ȳ�����һ����
				double dex = 10;
				//System.out.println(dex);
				dex = list1.get(i).getDrive_distance1() - list.get(0).getDrive_distance1();
				//System.out.println(dex);//���������ж�
				if(Math.abs(dex) < 0.5){
					return true;
				}
			}
		}
		    return false;
	}
	
	private static boolean judgeRearEnd(List<CarState> list,
			List<CarState> list1) throws Exception {
		// TODO Auto-generated method stub
		//�з�����Ԥ��
		if(list.get(0).getRoad_id() == 3 && list.get(0).getChannel().equals("inside") &&
				list.get(0).getHistory_hall_count() > 18 && list.get(0).getHistory_hall_count() < 26
				){
			Coordinate coordinate1 = CarHelper.getCarSiteInMap(list.get(0).getCar_id());
			for(int i = 0;i<list1.size();i++){
				if(list1.get(i).getCar_id() != list.get(0).getCar_id() &&
						list1.get(i).getChannel().equals("outside") &&list1.get(i).getRoad_id() ==2
						){//���ȿ�����û������С�����ص���λ��
					if(list1.get(i).getHistory_hall_count() > 20 && list1.get(i).getHistory_hall_count() < 29){
						Coordinate coordinate2 = CarHelper.getCarSiteInMap(list1.get(0).getCar_id());
						double distance = Math.sqrt(Math.pow((coordinate1.getX()-coordinate2.getX()), 2) + Math.pow((coordinate1.getY()-coordinate2.getY()), 2));
						double t = distance/(list.get(0).getSpeed() - list1.get(i).getSpeed());
						if(t < 5){//���ʱ��С��5s����������׷β��ʾ
							return true;
						}
					}					
				}
			}//			
		}else if(list.get(0).getRoad_id() == 3 && list.get(0).getChannel().equals("outside") &&
				list.get(0).getHistory_hall_count() > 10 && list.get(0).getHistory_hall_count() < 25){
			Coordinate coordinate1 = CarHelper.getCarSiteInMap(list.get(0).getCar_id());
			for(int i = 0;i<list1.size();i++){
				if(list1.get(i).getCar_id() != list.get(0).getCar_id() &&
						list1.get(i).getChannel().equals("inside") &&list1.get(i).getRoad_id() ==2
						){//���ȿ�����û������С�����ص���λ��
					if(list1.get(i).getHistory_hall_count() > 36 && list1.get(i).getHistory_hall_count() < 51){
						Coordinate coordinate2 = CarHelper.getCarSiteInMap(list1.get(0).getCar_id());
						double distance = Math.sqrt(Math.pow((coordinate1.getX()-coordinate2.getX()), 2) + Math.pow((coordinate1.getY()-coordinate2.getY()), 2));
						double t = distance/(list.get(0).getSpeed() - list1.get(i).getSpeed());
						if(t < 5){//���ʱ��С��5s����������׷β��ʾ
							return true;
						}
					}					
				}
			}//		
		}
		
		
		
		for(int i = 0;i<list1.size();i++){
			//System.out.println(new_data);
			if(list1.get(i).getCar_id() != list.get(0).getCar_id() &&
				 list1.get(i).getChannel().equals(list.get(0).getChannel())
				 ){//��ͬС����ͬһ�����������ݲŻ�׷β
				//System.out.println(111);
				if(list1.get(i).getDrive_distance() > list.get(0).getDrive_distance() &&
					  list1.get(i).getSpeed() < list.get(0).getSpeed() ){//���������ڵ�ǰС��ǰ�棬����С���ٶȱ�ǰ��Ŀ�
					double distance = list1.get(i).getDrive_distance() - list.get(0).getDrive_distance();
					double t = distance/(list.get(0).getSpeed() - list1.get(i).getSpeed());
					//System.out.println(t);
					if(t < 5){//���ʱ��С��5s����������׷β��ʾ
						return true;
					}
				}
			}
		}
		return false;
	}


	public static void changeRoad(List<CarState> list) {
		
		//String dir;//С����ʻ����
		//double[] result = new double[2];
		double l = 19.6;//ǰ�����
		double turn_angle = 20;//����ת��Ƕ�
		double angle = Math.toRadians(90-turn_angle);
		double width = 25;//����·��
		double central_angle=0;//Բ�Ľ�
		double arc_length;//����
		double time;//���Ԥ��ʱ��
		double chord_length;
		double r = l/Math.sin(Math.toRadians(turn_angle));
		//double r = 57;
		double v = 20;//��λ����
		
		for(int i=0;i<90;i++){//����Բ�ĽǶ�
			
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
		System.out.println(chord_length);
		
		double distance = chord_length + list.get(0).getDrive_distance();
    	CarState car = list.get(0);
		car.setDrive_distance1(distance);
		
        if(list.get(0).getChannel() == "inside"){//���ǰһ��       			    
 		    car.setChannel("outside");		 		    
        }else
        	car.setChannel("inside");
        
        list.set(0, car);//�ı�Ŀ��
        
		
		
		//return result[2];
	}

	private static double getTime(double v) {
		// TODO Auto-generated method stub
		double l = 19.6;//ǰ�����
		double turn_angle = 20;//����ת��Ƕ�
		double angle = Math.toRadians(90-turn_angle);
		double width = 25;//����·��
		double central_angle=0;//Բ�Ľ�
		double arc_length;//����
		double time =0;//���Ԥ��ʱ��
		//double chord_length;
		double r = l/Math.sin(Math.toRadians(turn_angle));
		//double r = 57;
		//double v = 20;//��λ����
		
		for(int i=0;i<90;i++){//����Բ�ĽǶ�
			
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
	

	public static void update(List<CarState> list1) {//��С��������
		// TODO Auto-generated method stub
		//List<carState> list = new ArrayList<carState>(list1);
		//list.addAll(list1);
		
		int len = list1.size();
		for(int i=0;i<len;i++){//
			
			double t = 3.6;//�������ʱ��
			double speed = list1.get(i).getSpeed();
		    double distance = 0;
		    distance =	speed*t + list1.get(i).getDrive_distance();
		    
		 //   System.out.println(distance);
		    
		    CarState car = list1.get(i);
		    car.setDrive_distance1(distance);
		    list1.set(i, car);//�ı�Ŀ��
			
		    //System.out.println(list.get(i).getDirive_distance1());
		   // System.out.println(list1.get(i).getDirive_distance1());
		}
		
		//return list;
	}

	public int alert(long carId) throws Exception {
		// TODO Auto-generated method stub
		
		DaoImpl dao = new DaoImpl();
		List<CarState> list = dao.getCarInfoById(carId);
		if(list.isEmpty()){
			return 0;
		}
		long groupId = list.get(0).getGroup_id();
		List<CarState> list1 = dao.getCarInfoByGroupId(groupId);
		if(list1.isEmpty()){
			return 0;
		}
		//3Ϊ���з���   4ǰ������
		CollisionAvoidance coll = new CollisionAvoidance();
		int alert = 0;
		//int alert = coll.judge(carId);
		if(alert == 0){
			for(int i=0;i<list1.size();i++){
				long carId1 = list1.get(i).getCar_id();
				if(carId1 != carId){
					int j = coll.judge(carId1);
					if(j ==1 || j == 2){
						if(list.get(0).getDrive_distance() > list1.get(i).getDrive_distance()){
							return 3;//��Σ��
						}else{
							return 4;//ǰ��Σ��
						}
					}
				}
			
			}

		}
		
		return 0;
	}	  

}

