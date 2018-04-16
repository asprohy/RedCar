package com.common;

import java.util.List;

import com.car.dao.DaoImpl;

public class SortCar {
    
    private static boolean isFirst = true;
    private static DaoImpl daoImpl = new DaoImpl();
    //0.��һ������ʱ��������sequence_flag
    
    //1.��groupȡ�����ݱ���û���Ź����С��
    //2.��group��history_data�в�ѯ����С����Ϣ�����ݾ�������
    //3.��������Ϣ����basic����
    
    public static void main(String[] args) {
	SortCar.initSequceFlage();
	SortCar.sortedCars();
    }
    
    public static void initSequceFlage() {
	// TODO Auto-generated method stub
	if(isFirst){
	    isFirst = false;
	    try {
		daoImpl.initSequenceFalse();
		System.out.println("��ʼ��С������......");
		
		
	    } catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		System.out.println("��ʼ��С�������������");
	    }
	}
	sortedCars();
    }
    
    private static void sortedCars() {
	List<Long> carIdList;
	try {
	    carIdList = daoImpl.getSequnceFalseCar();
	    if(carIdList != null){
		for(int i = 0; i < carIdList.size(); i++){
		    findSiteOfCar(carIdList.get(i));
		}
	    }
	} catch (Exception e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	
    }

    private static void findSiteOfCar(Long carId) {
	// TODO Auto-generated method stub
	
	try {
	    long index = daoImpl.getSite(carId);
	    if(index != 0){
		updateSiteOfCar(carId, index);
	    }
	    
	} catch (Exception e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }
    
    private static void updateSiteOfCar(Long carId, Long index){
	try {
	    daoImpl.updateSiteOfCar(carId, index);
	    System.out.println(carId +" С���������");
	} catch (Exception e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }
    
}
