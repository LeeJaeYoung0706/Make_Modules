package com.keti.iam.idthub.util.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;


import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * 유저 권한 맵핑
 *
 */
@Slf4j
public class CustomJwtAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    private final JwtGrantedAuthoritiesConverter defaultGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
    @Value("${keycloak.resource}")
    private String CLIENT_ID;


    private Collection<? extends GrantedAuthority> extractResourceRoles(final Jwt jwt) {
        Map<String , Map<String , ArrayList<String>>> resourceAccess = jwt.getClaim("resource_access");
        if(resourceAccess.containsKey(CLIENT_ID)){
            Map<String, ArrayList<String>> rolesJSON = resourceAccess.get(CLIENT_ID);
            if (rolesJSON.containsKey("roles")){
                return rolesJSON.get("roles").stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
            }
        }
        return Collections.emptySet();

    }

    @Override
    public AbstractAuthenticationToken convert(Jwt token) {
        Collection<GrantedAuthority> authorities = Stream.concat(defaultGrantedAuthoritiesConverter.convert(token)
                                .stream(), extractResourceRoles(token).stream()).collect(Collectors.toSet());
        return new JwtAuthenticationToken(token, authorities);
    }
}
