// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.javafullstack.subscriptions.infrastructure;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tech.javafullstack.common.hexagonal.PersistenceAdapter;
import tech.javafullstack.subscriptions.domain.CheckSubscriptionByAccountRepository;
import tech.javafullstack.subscriptions.domain.CheckSubscriptionByAccountRepository.CheckSubscriptionByAccountResult.CheckSubscriptionFailed;
import tech.javafullstack.subscriptions.domain.CheckSubscriptionByAccountRepository.CheckSubscriptionByAccountResult.SubscriptionExists;
import tech.javafullstack.subscriptions.domain.CheckSubscriptionByAccountRepository.CheckSubscriptionByAccountResult.SubscriptionNotFound;

import java.util.UUID;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * CheckSubscriptionByAccountRepository adapter implementation.
 */
@Slf4j
@Service
@PersistenceAdapter
@RequiredArgsConstructor
class CheckSubscriptionByAccountRepositoryAdapter implements CheckSubscriptionByAccountRepository {

    private final SubscriptionsJpaRepository subscriptionsRepo;

    /**
     * {@inheritDoc}
     */
    @Override
    public CheckSubscriptionByAccountResult execute(
          UUID accountId) {

        try {

            var subsription = subscriptionsRepo.findByAccountId(accountId);
            return subsription.isPresent()
                  ? new SubscriptionExists()
                  : new SubscriptionNotFound();

        } catch (Throwable cause) {
            log.error("Error", cause);
            return new CheckSubscriptionFailed(cause);
        }

    }

}
