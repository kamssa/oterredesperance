package com.operantic.gatewayservice;

import java.util.ArrayList;
import java.util.List;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.ReactiveDiscoveryClient;
import org.springframework.cloud.gateway.discovery.DiscoveryClientRouteDefinitionLocator;
import org.springframework.cloud.gateway.discovery.DiscoveryLocatorProperties;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import lombok.ToString;

@SpringBootApplication
@ToString
public class GatewayServiceApplication {

    @Autowired
    DiscoveryClientRouteDefinitionLocator locator;

    public static void main(String[] args) {
        SpringApplication.run(GatewayServiceApplication.class, args);
    }

    @Bean
    DiscoveryClientRouteDefinitionLocator dynamicRoutes(ReactiveDiscoveryClient rdc, DiscoveryLocatorProperties dlp) {
        DiscoveryClientRouteDefinitionLocator dcd = new DiscoveryClientRouteDefinitionLocator(rdc, dlp);
        return new DiscoveryClientRouteDefinitionLocator(rdc, dlp);
    }

    @Bean
    public List<GroupedOpenApi> apis() {
        List<GroupedOpenApi> groups = new ArrayList<>();
        List<RouteDefinition> definitions = locator.getRouteDefinitions().collectList().block();

        assert definitions != null;
        definitions.stream().filter(routeDefinition -> routeDefinition.getId().matches(".*-service"))
                .forEach(routeDefinition -> {
                    String name = routeDefinition.getId().replaceAll("-service", "");
                    groups.add(GroupedOpenApi.builder().pathsToMatch("/" + name + "/**").group(name).build());
                });

        return groups;

    }

    @Bean
    public CorsWebFilter corsWebFilter() {
        System.out.println("STARTING....");
        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.setAllowCredentials(true);
        corsConfig.addAllowedOrigin("http://localhost:3000"); // Adresse spécifique autorisée
        corsConfig.addAllowedOrigin("https://dev.oterredesperance.com");
        corsConfig.addAllowedOrigin("https://oterredesperance.com");
        corsConfig.addAllowedHeader("*");
        corsConfig.addAllowedMethod("*");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);

        return new CorsWebFilter(source);

    }

}
