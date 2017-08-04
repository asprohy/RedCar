package com.car.basic;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.car.dao.MyDaoImpl;

public class Fleet {

    static List<Car> fleet = new ArrayList<Car>();
    public Fleet() {
	// TODO Auto-generated constructor stub
	fleet = MyDaoImpl.getFleet();
    }
    
    //�����Ӳ��Ϊ����С�������ν�С�����Ƶ�ͼƬ�Ϻϳ�Ϊһ������ͼƬ
    public static BufferedImage drawFleet() {
	fleet = MyDaoImpl.getFleet();
	BufferedImage map = Map.getMapImg();
	for(int i = 0; i < fleet.size(); i++)
	{
	    Car c = fleet.get(i);
	    try {
		map = DealImg.combinImg(map, c.getCarImage(), c.getCenterOfCarPoint().x, c.getCenterOfCarPoint().y);
	    } catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	}
	return map;
    }
}
