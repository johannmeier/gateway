package gateway;

import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Mono;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableConfigurationProperties(UriConfiguration.class)
@RestController
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder builder, UriConfiguration uriConfiguration, ApplicationContext applicationContext) {

        RouteLocatorBuilder.Builder routes = builder.routes();
        addFilters(routes, applicationContext);

        String httpUri = uriConfiguration.getHttpbin();
        return routes
                .route(p -> p
                        .host("*.circuitbreaker.com")
                        .filters(f -> f
                                .circuitBreaker(config -> config
                                        .setName("mycmd")
                                        .setFallbackUri("forward:/fallback")))
                        .uri(httpUri))
                .route(p -> p
                        .path("/*")
                        .filters(f -> f.addRequestHeader("Holla", "secret"))
                        .uri(httpUri))
                .build();
    }

    private void addFilters(RouteLocatorBuilder.Builder routes, ApplicationContext applicationContext) {
        for (Object bean : applicationContext.getBeansWithAnnotation(GatewayFilter.class).values()) {
            FilterBase filter = (FilterBase) bean;
            routes.route(filter::getRouteBuildable);
        }
    }

    @RequestMapping("/fallback")
    public Mono<String> fallback() {
        return Mono.just("fallback");
    }
}

