//package com.Main;
//
//import java.net.ServerSocket;
//import java.net.Socket;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//import java.util.concurrent.Executors;
//import java.util.concurrent.ScheduledExecutorService;
//import java.util.concurrent.TimeUnit;
//
//import serverSocket.ReadThread;
//
//import com.common.JudgeCongestion;
//import com.sql.JdbcUtils;
//import com.sql.JdbcUtilsImp;
//
//public class MainControl {
//
//	private String flag="";
//	
//	public MainControl() {
//		// TODO Auto-generated constructor stub
//	}
//
//	public static void main(String[] args) throws Exception {
//////		JudgeCongestion jc = new JudgeCongestion();
//////		jc.Judge();
////	
////		JdbcUtilsImp jdbc = new JdbcUtilsImp();
////		int a =1;
//////		List<Map<String, Object>> list  =  jdbc.getMacAddress(1,1);
////		System.out.println(jdbc.listCarGroupId());
////		
////		JudgeCongestion judge = new JudgeCongestion();
////		JudgeChannel judgec = new JudgeChannle();//�ж�������ӿ�
//		
////		Runnable runnable = new Runnable() {  
////            public void run() {  
////                // task to run goes here
////            	try {
//////                	judgec.judgeC(); //�����ж�������ӿڷ���
//////            		judge.Judge();
////                    System.out.println("Hello !!");  
////            	} catch(Throwable t) {
////            		
////            	}
////            }  
////        };  
////        ScheduledExecutorService service = Executors  
////                .newSingleThreadScheduledExecutor();  
////        // �ڶ�������Ϊ�״�ִ�е���ʱʱ�䣬����������Ϊ��ʱִ�еļ��ʱ��  
////        service.scheduleAtFixedRate(runnable, 10, 60, TimeUnit.SECONDS);
//		
////		ServerSocket server = new ServerSocket(9999);
////		System.out.println("����������OK��");
////		Socket socket = null;
////		
////		while(true) {
////			socket = server.accept();
////			System.out.println("�ͻ���������");
////			//��
////			new ReadThread(socket).start();
////		}
//	}
//	
//
//		
//}
