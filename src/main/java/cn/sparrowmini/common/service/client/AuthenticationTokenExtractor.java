package cn.sparrowmini.common.service.client;

import org.springframework.lang.Nullable;
import org.springframework.security.core.Authentication;

public interface AuthenticationTokenExtractor {
    boolean supports(Authentication authentication);
    @Nullable
    String extractToken(Authentication authentication);
}
