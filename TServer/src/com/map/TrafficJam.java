package com.map;

import java.util.LinkedList;
import java.util.List;

import com.car.dao.DaoImpl;
import com.entity.CarCoordinateInfo;
import com.map.CongestionCoordinate;

public class TrafficJam {

	private static final int[] BENDHALL2INSIDE = {2 , 13 , 33 , 49 , 66 };//
	
    public static void main(String[] args) {
	// TODO Auto-generated method stub
	/*
	List<CongestionCoordinate> congestionCoordinates = TrafficJam.hallToJamInfo(2, "outside", 3, 27);
	for (CongestionCoordinate congestionCoordinate : congestionCoordinates) {
	    System.out.println(congestionCoordinate.drawString());
	}
	
	List<CongestionCoordinate> congestionCoordinates2 = TrafficJam.hallToJamInfo(2, "outside", 28, 40);
	for (CongestionCoordinate congestionCoordinate : congestionCoordinates2) {
	    System.out.println(congestionCoordinate.drawString());  
	}
	
	List<CongestionCoordinate> congestionCoordinates3 = TrafficJam.hallToJamInfo(2, "outside", 41, 54);
	for (CongestionCoordinate congestionCoordinate : congestionCoordinates3) {
	    System.out.println(congestionCoordinate.drawString());
	}   
	*/
	
	TrafficJam tJam = new TrafficJam();
	List<CongestionCoordinate> CJI = tJam.getJamInfo(2, "inside", 33, 60,20,20);
		for(int i=0;i<CJI.size();i++)
		{
			System.out.println("CJI i: "+i+" ( "+ CJI.get(i).getX1()+" , "+ CJI.get(i).getY1()+" ) "+CJI.get(i).getSpecial());
		}
    }
    
    //地图比例
    private static double rate = 820.0 / 700.0; //1105.0 / 700.0;

    public static List<CongestionCoordinate> getJamInfo(int roadId, String channel, int beginHall, int endHall) {
    	
    	int deltaAngle=0,MdeltaAngle=0;
    	
    	TrafficJam tj = new TrafficJam();  	
    	
    	return tj.getJamInfo(roadId, channel, beginHall, endHall, MdeltaAngle, deltaAngle);
    }
    
    public static List<CongestionCoordinate> getJamInfo(int roadId, String channel, int beginHall, int endHall,int MdeltaAngle, int deltaAngle) {
    	DaoImpl dao = new DaoImpl();
    	List<CongestionCoordinate> ccList = new LinkedList<CongestionCoordinate>();
    
    	if(beginHall < endHall){//begin 在后
    	    //System.out.println("111");
    		if(getSpecial(roadId, channel, beginHall) != 0 && getSpecial(roadId, channel, endHall) != 0)
    		{
    			ccList.add(hallToBendJamInfo(roadId, channel,beginHall,deltaAngle,false));
    			ccList.addAll(hallToJamInfo(roadId, channel, beginHall+1, endHall));
    			ccList.add(hallToBendJamInfo(roadId, channel,endHall,MdeltaAngle,true));
    		}
    		else if(getSpecial(roadId, channel, beginHall)!=0)
    		{
    			ccList.add(hallToBendJamInfo(roadId, channel,beginHall,deltaAngle,false));
    			ccList.addAll(hallToJamInfo(roadId, channel, beginHall+1, endHall));
    		}
    		else if(getSpecial(roadId, channel, endHall)!=0)
    		{
    			ccList.addAll(hallToJamInfo(roadId, channel, beginHall, endHall));
    			ccList.add(hallToBendJamInfo(roadId, channel,endHall,MdeltaAngle,true));
    		}
    		else
    		{
    			ccList.addAll(hallToJamInfo(roadId, channel, beginHall, endHall));
    		}
    		
    		
    	    //ccList = hallToJamInfo(roadId, channel, beginHall, endHall);
    	}else if (beginHall == endHall) {
    		//可加头尾长度控制的弯道
    	}else{
    		if(getSpecial(roadId, channel, endHall)!=0)
    		{
    			ccList.addAll(hallToJamInfo(roadId, channel, 1, endHall));
    			ccList.add(hallToBendJamInfo(roadId, channel,endHall,MdeltaAngle,true));
    		}
    		else
    		{
    			ccList = hallToJamInfo(roadId,channel,1,endHall);
    		}try {
    	    	if(getSpecial(roadId, channel, beginHall)!=0)
    	    	{
    	    		ccList.add(hallToBendJamInfo(roadId, channel,beginHall,deltaAngle,false));
    	    		for(CongestionCoordinate cc : hallToJamInfo(roadId, channel,  beginHall+1, dao.getMaxHall(roadId, channel))){
        	            ccList.add(cc);
        	        }
    	    	}
    	    	else
    	    	{
    	    		for(CongestionCoordinate cc : hallToJamInfo(roadId, channel,  beginHall, dao.getMaxHall(roadId, channel))){
    	    			ccList.add(cc);
    	        	}
    	    	}
    	        
    	        //获取过终点拥堵信息
    	        
    	        List<CarCoordinateInfo> carCoordinateInfos = new LinkedList<CarCoordinateInfo>();
    	        CongestionCoordinate  ccCongestionCoordinate = new CongestionCoordinate();
    	        carCoordinateInfos = dao.getCarCoordinateInfos(roadId, channel, dao.getMaxHall(roadId, channel), dao.getMaxHall(roadId, channel));
    	        CarCoordinateInfo c1 = carCoordinateInfos.get(0);
    	        carCoordinateInfos = dao.getCarCoordinateInfos(roadId, channel, dao.getMinHall(roadId, channel), dao.getMinHall(roadId, channel));
    	        CarCoordinateInfo c2 = carCoordinateInfos.get(0);
    	        int x1 = (int) (c1.getCoordinate_x() * rate);
    	        int y1 = (int) (c1.getCoordinate_y() * rate);
    	        int x2 = (int) (c2.getCoordinate_x() * rate);
    	        int y2 = (int) (c2.getCoordinate_y() * rate);
    	        int special = 0;
    	        special = getSpecial(roadId, channel, dao.getMaxHall(roadId, channel));
	    
    	        ccCongestionCoordinate.setSpecial(special);
    	        ccCongestionCoordinate.setX1(x1);
    	        ccCongestionCoordinate.setY1(y1);
    	        ccCongestionCoordinate.setX2(x2);
    	        ccCongestionCoordinate.setY2(y2);
    	        ccList.add(ccCongestionCoordinate);
    	    } catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
    	    }
    	    
	    
    	}
    	
    
	
	
	/*
	if (beginHall > endHall) {
	    int temp = beginHall;
	    beginHall = endHall;
	    endHall = temp;
	}
	*/

    	return ccList;
    }
    
    
    //将一头一尾两个Hall值信息传入，返回坐标对
    public static List<CongestionCoordinate> hallToJamInfo(int roadId, String channel, int beginHall, int endHall ) {
	List<CongestionCoordinate> ccList = new LinkedList<CongestionCoordinate>();
	List<CarCoordinateInfo> carCoordinateInfos = new LinkedList<CarCoordinateInfo>();
	//1.从数据库中顺序取出所有用到的坐标的值，两两建值，插入链表
	//System.out.println("roadId:"+roadId);
	DaoImpl dao = new DaoImpl();
	try {
	    carCoordinateInfos = dao.getCarCoordinateInfos(roadId, channel, beginHall, endHall);
	    //System.out.println(carCoordinateInfos.size());
	} catch (Exception e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	if(carCoordinateInfos.size()<=1)
	{
		return ccList;
	}//即beginHall==endHall
	//System.out.println("carCoordinateInfos:"+carCoordinateInfos.size());
	for (int i = 0; i < carCoordinateInfos.size() - 1; i++) {
	    CarCoordinateInfo c1 = carCoordinateInfos.get(i);
	    CarCoordinateInfo c2 = carCoordinateInfos.get(i+1);
	    CongestionCoordinate  ccCongestionCoordinate = new CongestionCoordinate();
	    int x1 = (int) (c1.getCoordinate_x() * rate);
	    int y1 = (int) (c1.getCoordinate_y() * rate);
	    int x2 = (int) (c2.getCoordinate_x() * rate);
	    int y2 = (int) (c2.getCoordinate_y() * rate);
	    int special = 0;
	    special = getSpecial(roadId, channel, beginHall+i);
	    
	    ccCongestionCoordinate.setSpecial(special);
	    ccCongestionCoordinate.setX1(x1);
	    ccCongestionCoordinate.setY1(y1);
	    ccCongestionCoordinate.setX2(x2);
	    ccCongestionCoordinate.setY2(y2);
	    ccList.add(ccCongestionCoordinate);
	}
	return ccList;
    }
    
    public static CongestionCoordinate hallToBendJamInfo(int roadId, String channel, int Hall, int MdeltaAngle , boolean isFront) {
    	//弯道hall
    	CongestionCoordinate cc = new CongestionCoordinate();
    	List<CarCoordinateInfo> carCoordinateInfos = new LinkedList<CarCoordinateInfo>();
    	DaoImpl dao = new DaoImpl();
    	try {
    	    carCoordinateInfos = dao.getCarCoordinateInfos(roadId, channel, Hall, Hall+1);//起点非弯道
    	    //System.out.println(carCoordinateInfos.size());
    	} catch (Exception e) {
    	    // TODO Auto-generated catch block
    	    e.printStackTrace();
    	}
    	CarCoordinateInfo c1 = carCoordinateInfos.get(0);
	    CarCoordinateInfo c2 = carCoordinateInfos.get(1);

	    int x1 = (int) (c1.getCoordinate_x() * rate);
	    int y1 = (int) (c1.getCoordinate_y() * rate);
	    int x2 = (int) (c2.getCoordinate_x() * rate);
	    int y2 = (int) (c2.getCoordinate_y() * rate);
	    int special = 0;
	    special = getSpecial(roadId, channel, Hall);
	    
	    if(isFront)
	    {
	    	x2=-x2;
	    	y2=-y2;
	    }
	    else
	    {
	    	x1=-x1;
	    	y1=-y1;
	    }
	    
	    cc.setSpecial(special);
	    cc.setX1(x1);
	    cc.setY1(y1);
	    cc.setX2(x2);
	    cc.setY2(y2);
	    cc.setDeltaAngle(MdeltaAngle);
    	
	    return cc;
    }
    
    
    public static int getSpecial(int roadId, String channel, int hallCount){
    	int special = 0;
    	
    	if(roadId == 1 && channel.equals("inside") && hallCount == 16){
    		special = 111;
    	} else if (roadId == 1 && channel.equals("inside") && hallCount == 52){
    		special = 112;
    	} else if (roadId == 1 && channel.equals("inside") && hallCount == 85){
    		special = 113;
    	} else if (roadId == 1 && channel.equals("outside") && hallCount == 13){ //标记时出过错
    		special = 121;
    	} else if (roadId == 1 && channel.equals("outside") && hallCount == 42){
    		special = 122;
    	} else if (roadId == 1 && channel.equals("outside") && hallCount == 68){
    		special = 123;
    	} else if (roadId == 2 && channel.equals("inside") && hallCount == 2){
    		special = 211;
    	} else if (roadId == 2 && channel.equals("inside") && hallCount == 33){
    		special = 212;
    	} else if (roadId == 2 && channel.equals("inside") && hallCount == 49){
    		special = 213;
    	} else if (roadId == 2 && channel.equals("inside") && hallCount == 66){
    		special = 214;
    	} else if (roadId == 2 && channel.equals("outside") && hallCount == 2){
    		special = 221;
    	} else if (roadId == 2 && channel.equals("outside") && hallCount == 27){
    		special = 222;
    	} else if (roadId == 2 && channel.equals("outside") && hallCount == 40){
    		special = 223;
    	} else if (roadId == 2 && channel.equals("outside") && hallCount == 54){
    		special = 224;
    	}  else if (roadId == 3 && channel.equals("inside") && hallCount == 2){
    		special = 311;
    	} else if (roadId == 3 && channel.equals("inside") && hallCount == 27){
    		special = 312;
    	} else if (roadId == 3 && channel.equals("inside") && hallCount == 46){
    		special = 313;
    	} else if (roadId == 3 && channel.equals("inside") && hallCount == 50){
    		special = 314;
    	} else if (roadId == 3 && channel.equals("outside") && hallCount == 2){
    		special = 321;
    	} else if (roadId == 3 && channel.equals("outside") && hallCount == 22){
    		special = 322;
    	} else if (roadId == 3 && channel.equals("outside") && hallCount == 46){
    		special = 323;
    	} else if (roadId == 3 && channel.equals("outside") && hallCount == 50){
    		special = 324;
    	}
    	
    	//System.out.println("getSpecial: roadId: "+roadId+ " channel: "+channel+" hallCount: " +hallCount+" special: "+special);
    	return special;
}


    public static boolean isEndHall(int roadId, String channel, int hallCount){
	boolean isEnd = false;
	if(roadId == 1 && channel.equals("inside") && hallCount == 87){
		isEnd = true;
	} else if(roadId == 1 && channel.equals("outside") && hallCount == 70){
		isEnd = true;
	} else if(roadId == 2 && channel.equals("inside") && hallCount == 67){
		isEnd = true;
	} else if(roadId == 2 && channel.equals("outside") && hallCount == 55){
		isEnd = true;
	} else if(roadId == 3 && channel.equals("inside") && hallCount == 56){
		isEnd = true;
	} else if(roadId == 3 && channel.equals("outside") && hallCount == 51){
		isEnd = true;
	} 
	
	return isEnd;
    
    }
    public static boolean isBend(int Hall)
	{
    	for(int i = 0 ; i < BENDHALL2INSIDE.length ; i++)
    	{
    		if(Hall == BENDHALL2INSIDE[i])
    		{
    			return true;
    		}
    	}
	
    	return false;
	}
}
