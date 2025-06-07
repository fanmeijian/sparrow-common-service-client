package cn.sparrowmini.common.service.client;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface WithSecurityContextBlocking {
}