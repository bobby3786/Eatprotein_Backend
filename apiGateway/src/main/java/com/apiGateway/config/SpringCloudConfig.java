package com.apiGateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringCloudConfig {
	

    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("AppUtil",r -> r.path("/apputil/**")
                        .uri("lb://APPUTIL")
                        )
                .route("authentication",r -> r.path("/auth/**")
                        .uri("lb://AUTHENTICATION")
                        )
                .route("catalog",r -> r.path("/catalog/**")
                        .uri("lb://CATALOG")
                        )
                .route("orders",r -> r.path("/orders/**")
                        .uri("lb://ORDERS")
                        )
                .route("payments",r -> r.path("/payments/**")
                        .uri("lb://PAYMENTS")
                        )
                .route("store",r -> r.path("/store/**")
                        .uri("lb://STORE")
                        )
                .route("UserCart",r -> r.path("/usercart/**")
                        .uri("lb://USERCART")
                        )
            
                .build();
    }

}
