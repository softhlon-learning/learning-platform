// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.subscriptions.infrastructure;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tech.softhlon.learning.common.hexagonal.PersistenceAdapter;
import tech.softhlon.learning.subscriptions.domain.CheckSubscriptionByAccountRepository;
import tech.softhlon.learning.subscriptions.domain.CheckSubscriptionByAccountRepository.CheckSubscriptionByAccountResult.CheckSubscriptionFailed;
import tech.softhlon.learning.subscriptions.domain.CheckSubscriptionByAccountRepository.CheckSubscriptionByAccountResult.SubscriptionExists;
import tech.softhlon.learning.subscriptions.domain.CheckSubscriptionByAccountRepository.CheckSubscriptionByAccountResult.SubscriptionNotFound;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Slf4j
@Service
@PersistenceAdapter
@RequiredArgsConstructor
class CheckSubscriptionByAccountRepositoryAdapter implements CheckSubscriptionByAccountRepository {

    private final SubscriptionsJpaRepository subscriptionsRepo;

    @Override
    public CheckSubscriptionByAccountResult execute(
          CheckSubscriptionByAccountRequest request) {

        try {
            var subsription = subscriptionsRepo.findByAccountId(
                  request.accountId());

            return subsription.isPresent()
                  ? new SubscriptionExists()
                  : new SubscriptionNotFound();

        } catch (Throwable cause) {

            log.error("Error", cause);
            return new CheckSubscriptionFailed(cause);

        }

    }

}
