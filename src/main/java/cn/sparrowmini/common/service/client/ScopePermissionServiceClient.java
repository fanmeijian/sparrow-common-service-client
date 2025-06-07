package cn.sparrowmini.common.service.client;

import cn.sparrowmini.common.service.ScopePermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ScopePermissionServiceClient {
    private final PermissionWebClientConfig permissionWebClientConfig;
//    private WebClient webClient;

//    public ScopePermissionServiceClient(){
//        this.webClient = permissionWebClientConfig.webClient();
//    }

    @WithReactiveSecurityContext
    public Mono<Boolean> hasPermission(String scope) {
        WebClient webClient = permissionWebClientConfig.webClient();
        return webClient.get()
                .uri("/scopes/" + scope + "/has-permission")
                .retrieve()
                .bodyToMono(Boolean.class);

    }
}
