package cn.com.chinacloud.paas.grpc.client;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import io.fabric8.kubernetes.api.model.Pod;
import io.fabric8.kubernetes.api.model.ReplicationController;
import io.fabric8.kubernetes.client.Config;
import io.fabric8.kubernetes.client.ConfigBuilder;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClientException;
import io.fabric8.kubernetes.client.Watch;
import io.fabric8.kubernetes.client.Watcher;
import io.fabric8.kubernetes.client.Watcher.Action;

public class K8SClientWatch {

	
	public static void main(String[] args) {
		 String namespace = "kube-system";
	        String master = "http://172.16.80.151:8080/";
		final CountDownLatch closeLatch = new CountDownLatch(1); 
		 Config config = new ConfigBuilder().withMasterUrl(master)
		            .withTrustCerts(true)
		                .withNamespace(namespace).build();
		 final KubernetesClient client = new DefaultKubernetesClient(config);
		 
		 //this is for logs 
		/*
         LogWatch watch = client.pods().inNamespace(namespace).withName("kube-apiserver-172.16.80.151").tailingLines(10).watchLog(System.out);
		 */
		      try{ 
		    	   Watch watch = client.replicationControllers().withName("test").watch(new Watcher<ReplicationController>() { 
				         @Override 
				         public void eventReceived(Action action, ReplicationController resource) { 
				           System.out.println("action: "+action +"== resource version: " + resource.getMetadata().getResourceVersion());
			         } 
				 
				 
				         @Override 
				         public void onClose(KubernetesClientException e) { 
				           System.out.println("Watcher onClose"); 
				           if (e != null) { 
				        	   e.printStackTrace();
				             closeLatch.countDown(); 
				           } 
			         } 
				       });
		    	   
		         
		    	   client.pods().inNamespace("default").withName("mysql-009-test3-mysql1-1005051555-jg627").watch(new Watcher<Pod>(){

					@Override
					public void eventReceived(io.fabric8.kubernetes.client.Watcher.Action arg0, Pod arg1) {
						System.out.println("action: "+arg0 +"== pod status: " + arg1.getStatus().getPhase());
					}

					@Override
					public void onClose(KubernetesClientException arg0) {
						 System.out.println("Watcher onClose"); 
				           if (arg0 != null) { 
				        	   arg0.printStackTrace();
				             closeLatch.countDown(); 
				           } 
					}
		    		   
		    	   });
		    	   
		    	   closeLatch.await(10, TimeUnit.SECONDS); 
		         System.out.println("============= tiger =============");
		         
		       } catch (Exception e) { 
		         e.printStackTrace();
		       } 
		     
		     try {
				Thread.sleep(60000l);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} 
	}
}
