package org.sid.gatewaymicroservice;

import com.netflix.discovery.DiscoveryClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.ReactiveDiscoveryClient;
import org.springframework.cloud.gateway.discovery.DiscoveryClientRouteDefinitionLocator;
import org.springframework.cloud.gateway.discovery.DiscoveryLocatorProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GatewayMicroserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayMicroserviceApplication.class, args);
    }


    //create dynamic routes
    @Bean
    DiscoveryClientRouteDefinitionLocator locator(ReactiveDiscoveryClient rdc, DiscoveryLocatorProperties dlp) {
        return new DiscoveryClientRouteDefinitionLocator(rdc,dlp);

    }
}
