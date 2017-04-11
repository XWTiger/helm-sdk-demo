package cn.com.chinacloud.paas.grpc.client;

import java.util.Date;
import java.util.concurrent.CountDownLatch;

public class Test implements Runnable {

	
	  
	
	    private static final CountDownLatch startLatch =  
	            new CountDownLatch(1);  
	    private static final CountDownLatch stopLatch =  
	        new CountDownLatch(100);  
	  
	    @Override  
	    public void run() {  
	        try {  
	        	System.out.println("==== tiger ===");
	            startLatch.await();  
	            System.out.println("do something!");  
	        } catch (InterruptedException e) {  
	            e.printStackTrace();  
	        } finally {  
	            stopLatch.countDown();  
	        }  
	    }  
	      
	    public static void main(String[] args) throws InterruptedException {  
	        for(int i = 0 ; i < 10 ; i++)  
	            new Thread(new Test()).start();  
	        System.out.println(new Date().toString());  
	        startLatch.countDown();  
	        stopLatch.await();  
	        System.out.println(new Date().toString());  
	    }  
}  


