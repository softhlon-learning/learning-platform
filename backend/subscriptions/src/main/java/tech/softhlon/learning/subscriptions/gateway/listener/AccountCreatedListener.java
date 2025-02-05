// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.subscriptions.gateway.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;
import tech.softhlon.learning.common.event.AccountCreated;
import tech.softhlon.learning.subscriptions.domain.PersistFreeTrialRepository;
import tech.softhlon.learning.subscriptions.domain.PersistFreeTrialRepository.PersistFreeTrialResult.FreeTrialPersisted;
import tech.softhlon.learning.subscriptions.domain.PersistFreeTrialRepository.PersistFreeTrialResult.FreeTrialPersistenceFailed;

import java.time.OffsetDateTime;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * AccountCreated event listener.
 */
@Slf4j
@Service
@RequiredArgsConstructor
class AccountCreatedListener implements ApplicationListener<AccountCreated> {

    private static final int FREE_TRIAL_DAYS = 1;
    private final PersistFreeTrialRepository persistFreeTrialRepository;

    /**
     * Consumer AccountCreated event.
     * @param event AccountCreated event
     */
    @Override
    public void onApplicationEvent(AccountCreated event) {
        var result = persistFreeTrialRepository.execute(
              null,
              event.getAcountId(),
              OffsetDateTime.now().plusDays(FREE_TRIAL_DAYS)
        );

        switch (result) {
            case FreeTrialPersisted() -> log.info("Free trial created");
            case FreeTrialPersistenceFailed(Throwable cause) -> log.info("Free trial not created");
        }
    }

}
