package gateway;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("uri")
public class UriConfiguration {

    private String httpbin = "https://httpbin.org";

    public String getHttpbin() {
        return httpbin;
    }

    public void setHttpbin(String httpbin) {
        this.httpbin = httpbin;
    }
}
