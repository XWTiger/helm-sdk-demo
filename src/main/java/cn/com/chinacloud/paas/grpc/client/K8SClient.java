package cn.com.chinacloud.paas.grpc.client;



import io.fabric8.kubernetes.api.model.Pod;
import io.fabric8.kubernetes.client.Config;
import io.fabric8.kubernetes.client.ConfigBuilder;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;

public class K8SClient {

	public static void main(String[] args) {
		  String namespace = "default";
	        String master = "http://172.16.80.151:8080/";
	        KubernetesClient client=null;
	        Config config = new ConfigBuilder().withMasterUrl(master)
	            .withTrustCerts(true)
	                .withNamespace(namespace).build();

	        client = new DefaultKubernetesClient(config);
	       java.util.List<Pod> list =  client.pods().list().getItems();
	       System.out.println("============ start===============");
	       for (Pod pod : list) {
	    	   System.out.println(pod.getStatus().getPhase());
			System.out.println(pod.getStatus().getContainerStatuses().get(0).getContainerID());
		}
	       System.out.println("============ end===============");
	        client.close();
	}
}
