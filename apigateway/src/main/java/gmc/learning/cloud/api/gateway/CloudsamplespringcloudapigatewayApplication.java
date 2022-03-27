package gmc.learning.cloud.api.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class CloudsamplespringcloudapigatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(CloudsamplespringcloudapigatewayApplication.class, args);
	}

}
