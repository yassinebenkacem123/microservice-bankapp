package com.bankApp.gatewayserver.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import reactor.core.publisher.Flux;

import java.util.*;
import java.util.stream.Collectors;

public class KeyClockRoleConvert implements Converter<Jwt, Flux<GrantedAuthority>> {

    @Override
    public Flux<GrantedAuthority> convert(Jwt jwt) {
        Map<String, Object> realmeAccess = (Map<String, Object>) jwt.getClaims().get("realm_access");

        if(realmeAccess == null || realmeAccess.isEmpty()){
            return Flux.empty();
        }

        List<GrantedAuthority> authorities = ((List<String>) realmeAccess.get("roles"))
                .stream().map(roleName -> "ROLE_" + roleName)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        return Flux.fromIterable(authorities);
    }
}
