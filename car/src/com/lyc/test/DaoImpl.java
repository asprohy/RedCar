package com.lyc.test;

import java.util.ArrayList;
import java.util.List;

import com.lyc.test.CarState;


public class DaoImpl{

	public List<CarState> getCarInfoById(long id,long time) throws Exception {
		// TODO Auto-generated method stub
		JdbcUtils jdbcUtils = new JdbcUtils();
		jdbcUtils.getConnection();//select * from car_history_data where send_time =( select max(send_time) from car_history_data )
		//测试使用
		String sql = "select * from car_history_data where car_id=? and send_time =?";
		
		//String sql = "select * from car_history_data where car_id=? and send_time =( select max(send_time) from car_history_data where car_id=?)";
		List<Object> params = new ArrayList<Object>();
		params.add(id);
		params.add(time);
		//params.add(id);
		
		//System.out.println(params.get(0));
		
		List<CarState> list = jdbcUtils.findMoreRefResult(sql,params,CarState.class);
		jdbcUtils.releaseConn();
		return list;
	}

	public List<CarState> getCarInfo(long time ) throws Exception {//真实的时候去掉时间
		// TODO Auto-generated method stub
		JdbcUtils jdbcUtils = new JdbcUtils();
		jdbcUtils.getConnection();
		
		//测试使用
		String sql = "select * from car_history_data where send_time =?";
		List<Object> params = new ArrayList<Object>();
		params.add(time);
		//String sql = "select * from car_history_data where send_time =( select max(send_time) from car_history_data )";
		
		List<CarState> list = jdbcUtils.findMoreRefResult(sql,params,CarState.class);
		jdbcUtils.releaseConn();
		return list;
	}
}
