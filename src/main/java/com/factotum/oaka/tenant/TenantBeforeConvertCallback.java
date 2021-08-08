package com.factotum.oaka.tenant;

import com.factotum.oaka.model.Transaction;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.data.r2dbc.mapping.event.BeforeConvertCallback;
import org.springframework.data.relational.core.sql.SqlIdentifier;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class TenantBeforeConvertCallback implements BeforeConvertCallback<Transaction> {

    @Override
    @NonNull
    public Publisher<Transaction> onBeforeConvert(@NonNull Transaction entity, @NonNull SqlIdentifier table) {
        return ReactiveSecurityContextHolder.getContext()
                .map(SecurityContext::getAuthentication)
                .map(Authentication::getPrincipal)
                .cast(Jwt.class)
                .flatMap(jwt -> {
                    String tenantId = (String)jwt.getClaims().get("sub");

                    if (entity.getTenantId() != null && !entity.getTenantId().equals(tenantId)) {
                        return Mono.empty();
                    }

                    entity.setTenantId(tenantId);
                    return Mono.just(entity);
                });
    }
}