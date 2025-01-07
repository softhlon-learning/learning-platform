// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) IO.SOFTHLON - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package io.softhlon.learning.subscriptions.infrastructure;

import io.softhlon.learning.common.hexagonal.PersistenceAdapter;
import io.softhlon.learning.subscriptions.domain.CheckSubscriptionByAccountRepository;
import io.softhlon.learning.subscriptions.domain.CheckSubscriptionByAccountRepository.CheckSubscriptionByAccountResult.CheckSubscriptionFailure;
import io.softhlon.learning.subscriptions.domain.CheckSubscriptionByAccountRepository.CheckSubscriptionByAccountResult.SubscriptionExists;
import io.softhlon.learning.subscriptions.domain.CheckSubscriptionByAccountRepository.CheckSubscriptionByAccountResult.SubscriptionNotFound;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Service
@PersistenceAdapter
@RequiredArgsConstructor
class CheckSubscriptionByAccountRepositoryAdapter implements CheckSubscriptionByAccountRepository {
    private final SubscriptionsJpaRepository subscriptionsRepo;

    @Override
    public CheckSubscriptionByAccountResult execute(CheckSubscriptionByAccountRequest request) {
        try {
            return subscriptionsRepo.existsByAccount(request.accountId())
                  ? new SubscriptionExists()
                  : new SubscriptionNotFound();
        } catch (Throwable cause) {
            return new CheckSubscriptionFailure(cause);
        }
    }
}
