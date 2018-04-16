package com.orderSend;

import java.util.*;

public class Main{
    public static void main(){
        Scanner input = new Scanner(System.in);
        int n = input.nextInt();
        int max = 0;
        int myDeep = 1;
        HashMap<Integer,Integer> deep = new HashMap<>();
        HashMap<Integer, Integer> childNum = new HashMap<>();
        deep.put(0,1);//£¬Ä¬ÈÏÊ÷¸ß1\
        childNum.put(0,0);
        for(int i = 0;i<n-1;i++){
            int a = input.nextInt();
            int b = input.nextInt();
            
          if(!deep.containsKey(a) || deep.get(a) > 2){
              continue;
       	 }
         myDeep = deep.get(a) + 1;
         deep.put(a,myDeep);
         childNum.put(a,childNum.get(a)+1);
         childNum.put(b,0);
           
         if(myDeep > max){
             max = myDeep;
    	}
      }
    }
}