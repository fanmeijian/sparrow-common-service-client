package cn.sparrowmini.common.service.client;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import reactor.core.publisher.Mono;

import java.util.Map;

public class WebClientOAuth2Filter2 {

    public static ExchangeFilterFunction oauth2TokenFilter() {
        return ExchangeFilterFunction.ofRequestProcessor(request -> {
            return Mono.deferContextual(ctx -> {
                var auth = SecurityContextHolder.getContext().getAuthentication();

                if (auth == null || !auth.isAuthenticated()) {
                    return Mono.just(request);
                }

                String token = extractAccessToken(auth);
                if (token == null) {
                    return Mono.just(request);
                }

                ClientRequest newRequest = ClientRequest.from(request)
                        .header("Authorization", "Bearer " + token)
                        .build();
                return Mono.just(newRequest);
            });
        });
    }

    private static String extractAccessToken(Authentication auth) {
        // 1. JwtAuthenticationToken
        if (auth instanceof org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken jwtAuth) {
            return jwtAuth.getToken().getTokenValue();
        }

        // 2. BearerTokenAuthentication（更常用于 opaque token）
        if (auth instanceof org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthentication bearerAuth) {
            return bearerAuth.getToken().getTokenValue();
        }


        // 4. 其他实现：从 details 中取 token？
        Object details = auth.getDetails();
        if (details instanceof Map<?, ?> map && map.containsKey("tokenValue")) {
            return (String) map.get("tokenValue");
        }

        return null;
    }
}