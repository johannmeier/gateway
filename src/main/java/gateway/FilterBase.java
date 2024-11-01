package gateway;

import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.builder.Buildable;
import org.springframework.cloud.gateway.route.builder.PredicateSpec;

public abstract class FilterBase {
    abstract Buildable<Route> getRouteBuildable(PredicateSpec p);
}
