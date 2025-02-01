// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.subscriptions.domain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Slf4j
@Service
@EnableAsync
@RequiredArgsConstructor
class CancelExpiredSubscriptionServiceImpl implements CancelExpiredSubscriptionService {

    private static final String ACCOUNT = "account";
    private static final String SCHEDULER = "scheduler";

    @Override
    @Scheduled(fixedRate = 1800_000)
    public void execute() {
        MDC.put(ACCOUNT, SCHEDULER);
        log.info("service | Run cancel expired subscriptions");
    }

}
