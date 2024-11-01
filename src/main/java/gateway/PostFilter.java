package gateway;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.builder.Buildable;
import org.springframework.cloud.gateway.route.builder.PredicateSpec;
import reactor.core.publisher.Mono;

@GatewayFilter
public class PostFilter extends FilterBase {

    private final String httpUri;

    public PostFilter(UriConfiguration uriConfiguration) {
        httpUri = uriConfiguration.getHttpbin();
    }


    @Override
    public Buildable<Route> getRouteBuildable(PredicateSpec p) {
        return p
                .path("/headers")
                .filters(f -> f.modifyResponseBody(JsonNode.class, JsonNode.class, (exchange, s) -> {
                            setResponse(s);
                            return Mono.just(s);
                        })
                )
                .uri(httpUri);
    }

    private void setResponse(JsonNode response) {
        System.out.println(response.toPrettyString());
    }
}
