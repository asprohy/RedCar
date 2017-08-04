package com.car.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBHelper {

//	public static final String url = "jdbc:postgresql://localhost:5432/daao";  //数据库名
	public static final String url = "jdbc:postgresql://localhost:5432/test";  //数据库名
	
    public static final String name = "org.postgresql.Driver";  //driver
    public static final String user = "daaouser";  //用户名
//    public static final String password = "1234";
    public static final String password = "123";
  
    public Connection conn = null;  
    public PreparedStatement pst = null;  
  
    public DBHelper(String sql) {  
        try {  
            Class.forName(name);//指定连接类型  
            conn = DriverManager.getConnection(url, user, password);//获取连接  
            pst = conn.prepareStatement(sql);//准备执行语句  
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
