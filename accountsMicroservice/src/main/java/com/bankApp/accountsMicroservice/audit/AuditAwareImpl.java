package com.bankApp.accountsMicroservice.audit;


import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("auditAwareImpl")
public class AuditAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of("YASSINOSS"); // so when ever i use createdBy or modifiedBy it will be set to this value
    }
}
