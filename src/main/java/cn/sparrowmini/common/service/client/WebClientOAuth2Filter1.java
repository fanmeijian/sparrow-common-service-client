package cn.sparrowmini.common.service.client;//package cn.liyuan.dengbo.replenishment.planner.client;
import org.springframework.http.HttpHeaders;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;

public class WebClientOAuth2Filter1 {

    public static ExchangeFilterFunction oauth2TokenFilter() {
        return ExchangeFilterFunction.ofRequestProcessor(clientRequest ->
                ReactiveSecurityContextHolder.getContext()
                        .map(securityContext -> {
                            var auth = securityContext.getAuthentication();
                            if (auth instanceof JwtAuthenticationToken jwtToken) {
                                return ClientRequest.from(clientRequest)
                                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken.getToken().getTokenValue())
                                        .build();
                            }
                            return clientRequest;
                        })
        );
    }

}