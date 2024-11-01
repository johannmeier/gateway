package gateway;

import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.builder.Buildable;
import org.springframework.cloud.gateway.route.builder.GatewayFilterSpec;
import org.springframework.cloud.gateway.route.builder.PredicateSpec;

@GatewayFilter
public class PreFilter extends FilterBase {

    private final String httpUri;

    public PreFilter(UriConfiguration uriConfiguration) {
        httpUri = uriConfiguration.getHttpbin();
    }

    @Override
    public Buildable<Route> getRouteBuildable(PredicateSpec p) {
        return p
                .path("/get")
                .filters(this::getGatewayFilterSpec)
                .uri(httpUri);
    }

    private GatewayFilterSpec getGatewayFilterSpec(GatewayFilterSpec f) {
        return f.addRequestHeader("Hello", "World");
    }
}
