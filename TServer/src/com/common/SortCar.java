package com.common;

import java.util.List;

import com.car.dao.DaoImpl;

public class SortCar {
    
    private static boolean isFirst = true;
    private static DaoImpl daoImpl = new DaoImpl();
    //0.第一次运行时重置所有sequence_flag
    
    //1.按group取出数据表中没有排过序的小车
    //2.按group在history_data中查询所有小车信息，根据距离排序
    //3.将排序信息加入basic表中
    
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
		System.out.println("初始化小车排序......");
		
		
	    } catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		System.out.println("初始化小车排序出错！！！");
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
	    System.out.println(carId +" 小车排序完成");
	} catch (Exception e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }
    
}
