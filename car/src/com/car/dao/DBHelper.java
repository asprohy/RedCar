package com.car.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBHelper {

//	public static final String url = "jdbc:postgresql://localhost:5432/daao";  //���ݿ���
	public static final String url = "jdbc:postgresql://localhost:5432/test";  //���ݿ���
	
    public static final String name = "org.postgresql.Driver";  //driver
    public static final String user = "daaouser";  //�û���
//    public static final String password = "1234";
    public static final String password = "123";
  
    public Connection conn = null;  
    public PreparedStatement pst = null;  
  
    public DBHelper(String sql) {  
        try {  
            Class.forName(name);//ָ����������  
            conn = DriverManager.getConnection(url, user, password);//��ȡ����  
            pst = conn.prepareStatement(sql);//׼��ִ�����  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
  
    public void close() {  
        try {  
            this.conn.close();  
            this.pst.close();  
        } catch (SQLException e) {  
            e.printStackTrace();  
        }  
   
}  
}
