package com.lyc.test;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import domain.CarInfo;

public class JdbcUtils 
{

	//�������ݿ���û���
	private final String USERNAME = "daaouser";
	//�������ݿ������
	private final String PASSWORD = "123";
	//�������ݿ��������Ϣ
	private final String DRIVER = "org.postgresql.Driver";
	//�������ݿ�ķ��ʵ�ַ
	private final String URL = "jdbc:postgresql://localhost:5432/test";
	//�������ݿ������
	private Connection connection;
	//����sql����ִ�ж���
	private PreparedStatement pstmt;
	//�����ѯ���صĽ������
	private ResultSet resultSet;
	
	public JdbcUtils() 
	{
		// 
		try
		{
//			Class.forName(DRIVER);
//			System.out.println("ע�������ɹ�����");
		}
		catch(Exception e)
		{
			//TODO:handle exception
		    System.out.println("ע������ʧ�ܣ���");
		}

	}
	//���������ݿ������
	public Connection getConnection()
	{
		try{
			connection = DriverManager.getConnection(URL,USERNAME,PASSWORD);
		}catch(Exception e){
			//TODO:handle exception
			e.printStackTrace();
			}
		return connection;
	}
	/**
	 * ��ɶ����ݿ�ı����ӡ�ɾ�����޸ĵĲ���
	 * @param sql
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	
	public boolean updateByPreparedStatement(String sql, List<Object>params) throws SQLException
	{
		boolean flag = false;
		int result = -1; //���û�ִ����ӡ��޸ġ�ɾ��������ʱ����Ӱ�����ݿ������
		pstmt = connection.prepareStatement(sql);
		int index = 1; //��ʾռλ���ĵ�һ��λ��
		if(params != null && !params.isEmpty())
		{
			for(int i=0;i<params.size();i++)
			{
				pstmt.setObject(index++, params.get(i));
			}
		}
		result = pstmt.executeUpdate();
		flag = result > 0 ? true : false;//result����0�������Ϊtrue,����Ϊfalse
		return flag;		
	}
	
	/**
	 * 
	 * ��ѯ���ص�����¼
	 * 
	 * @param sql
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	public Map<String, Object> findSimpleResult(String sql, List<Object>params) 
			throws SQLException{
		
		Map<String, Object> map = new HashMap<String,Object>();
		int index = 1;
		
		pstmt = connection.prepareStatement(sql);
		if(params != null && !params.isEmpty()){
			for(int i = 0; i<params.size(); i++){
				pstmt.setObject(index, params.get(i));
			}
		}
		resultSet = pstmt.executeQuery();//���ز�ѯ���
		ResultSetMetaData metaData = resultSet.getMetaData();
		int col_len = metaData.getColumnCount();//����еĳ���
		while(resultSet.next()){
			for(int i=0;i<col_len;i++){
				String cols_name = metaData.getColumnName(i+1);//��õ�ǰ�е�����
				Object cols_value = resultSet.getObject(cols_name);
				if(cols_value == null){
					cols_value = "";
				}
				map.put(cols_name, cols_value);
			}
		}
		return map;		
	}
	
	/**
	 * 
	 * ��ѯ���ض��м�¼
	 * 
	 * @param sql
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	public List<Map<String,Object>> findMoreResult(String sql,
			List<Object>params) throws SQLException {
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		int index = 1;
		pstmt = connection.prepareStatement(sql);
		if(params != null && !params.isEmpty()){
			for(int i = 0; i<params.size(); i++){
				pstmt.setObject(index++, params.get(i));
			}
		}
		resultSet = pstmt.executeQuery(); //���ز�ѯ���
		ResultSetMetaData metaData = resultSet.getMetaData();
		int cols_len = metaData.getColumnCount();//����г���
		while (resultSet.next()) {
			Map<String,Object> map = new HashMap<String, Object>();
			for(int i=0;i<cols_len;i++){
				String cols_name = metaData.getColumnName(i+1);
				Object cols_value = resultSet.getObject(cols_name);
				if(cols_value == null){
					cols_value = "";
				}
				map.put(cols_name, cols_value);
			}
			list.add(map);
		}
		return list;		
	}
	
	//jdbc�ķ�װ�����÷����������װ
	public <T> T findSimpleRefResult(String sql, List<Object> params, Class<T> cls) throws Exception{
		
		T resultObject = null;
		int index = 1;
		pstmt = connection.prepareStatement(sql);
		if(params != null && !params.isEmpty()){
			for(int i = 0; i<params.size(); i++){
				pstmt.setObject(index, params.get(i));
			}
		}
		resultSet = pstmt.executeQuery();
		ResultSetMetaData metaData = resultSet.getMetaData();
		int cols_len = metaData.getColumnCount();//��������
		while(resultSet.next()){
			//ͨ��������ƴ���ʵ��
			resultObject = cls.newInstance();
			for(int i=0;i<cols_len;i++){
				String cols_name = metaData.getColumnName(i+1);
				Object cols_value = resultSet.getObject(cols_name);//ȡ��������Ӧ������
				if(cols_value == null){
					cols_value = "";
				}
				//ͨ����������ȡjava BEAN���е������ֶ�
				Field field = cls.getDeclaredField(cols_name);
				field.setAccessible(true);//��javabean �ķ���privateȨ��
				field.set(resultObject, cols_value);
			}
		}
		return resultObject;
		
	}
	
	/**
	 * 
	 * ͨ��������Ʒ������ݿ�
	 * 
	 * @param sql
	 * @param params
	 * @param cls
	 * @return
	 * @throws Exception
	 */
	public <T> List<T> findMoreRefResult(String sql, List<Object> params, 
			Class<T> cls) throws Exception {
		List<T> list = new ArrayList<T>();
		int index = 1;
		//System.out.println(connection);
		pstmt = connection.prepareStatement(sql);
		if (params != null && !params.isEmpty()) {
			for (int i=0; i<params.size(); i++) {
				pstmt.setObject(index++,  params.get(i));
			}
		}
		resultSet = pstmt.executeQuery();
		ResultSetMetaData metaData = resultSet.getMetaData();
		int cols_len = metaData.getColumnCount();
		while (resultSet.next()) {
			T resultObject = cls.newInstance();
			for (int i=0; i<cols_len; i++) {
				String cols_name = metaData.getColumnName(i+1);
				Object cols_value = resultSet.getObject(cols_name);
				if (cols_value == null) {
					cols_value = "";
				}
				Field field = cls.getDeclaredField(cols_name);
				field.setAccessible(true);
				field.set(resultObject, cols_value);
			}
			list.add(resultObject);
		}
		return list;		
	}
	
	/**
	 * �ر����ݿ������
	 */
	public void releaseConn() {
		if (resultSet != null) {
			try {
				resultSet.close();
			} catch (SQLException e) {
				//TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				//TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				//TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
//	public void updateCarInfo(CarInfo carInfo)
//	{		
//		String sql = "update car_info set ";
//	}
	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args)
	{
		//TODO Auto-generated method stub
		JdbcUtils jdbcUtils = new JdbcUtils();
		jdbcUtils.getConnection();
		String sql = "update car_basic_info set update_time=? where id=?";
		List<Object> params = new ArrayList<Object>();		
		
		 long currTime = System.currentTimeMillis();
	     Timestamp timeObj = new Timestamp(currTime);            //yyyy-MM-dd HH:mm:ss:mis     
	     String createTime = timeObj.toString().substring(0,19); 
	        
		params.add(Timestamp.valueOf(createTime));		
		params.add(1);
//		params.add(1);
		
		try{			 
			boolean flag = jdbcUtils.updateByPreparedStatement(sql, params);
//			System.out.println(flag);
		} catch (SQLException e) {
			e.printStackTrace();
		}		
//		String sql = "select * from car_basic_info ";		
////		List<Object> params = new ArrayList<Object>();
////		params.add(1);	
//		try {		
//			List<UserInfo> list = jdbcUtils.findMoreRefResult(sql, null, CarInfo.class);
//			System.out.println(list);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} finally {
//			jdbcUtils.releaseConn();
//		}
		
		
	}

}
