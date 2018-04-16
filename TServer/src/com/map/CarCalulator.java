package com.map;

import java.util.List;

import com.entity.CarState;

public class CarCalulator {
	public static Coordinate getCarSiteInMap(Long id){
		MapDaoImpl dao = new MapDaoImpl();
		try {
			List<CarState> carStateList = dao.getCarState(id);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
}
