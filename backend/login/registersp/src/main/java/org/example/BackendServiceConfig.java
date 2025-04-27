package org.example;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class BackendServiceConfig {
	
//	@Bean @LoadBalanced
	RestTemplate restTemplate() {
		return new RestTemplate();
	}

}

