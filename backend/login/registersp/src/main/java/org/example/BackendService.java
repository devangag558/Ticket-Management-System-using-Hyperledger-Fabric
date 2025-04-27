package org.example;



import java.io.IOException;
import java.net.ServerSocket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;


@SuppressWarnings("unused")
@EnableFeignClients
@EnableDiscoveryClient
@OpenAPIDefinition
@SpringBootApplication
@EnableScheduling
//@LoadBalanced

public class BackendService {
	public static void main(String[] args) {
        int startingPort = 9000;  // Initial port
        int port = getAvailablePort(startingPort);
        
        // Set the server port dynamically via environment variables
        System.setProperty("server.port", String.valueOf(port));
        
        // Launch Spring Boot application
        SpringApplication.run(BackendService.class, args);

        System.out.println("Application started on port: " + port);
    }

    private static int getAvailablePort(int startingPort) {
        int port = startingPort;
        while (!isPortAvailable(port)) {
            port++;  // Increment port number if it's already in use
        }
        return port;
    }

    private static boolean isPortAvailable(int port) {
        try (ServerSocket socket = new ServerSocket(port)) {
            socket.setReuseAddress(true);
            return true;
        } catch (IOException e) {
            return false;  // Port is in use
        }
    }

}