// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.subscriptions.infrastructure;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tech.softhlon.learning.subscriptions.domain.DeactivateSubscriptionsRepository;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Slf4j
@Service
@RequiredArgsConstructor
class DeactivateSubscriptionsRepositoryAdapter implements DeactivateSubscriptionsRepository {

    private final SubscriptionsJpaRepository subscriptionsJpaRepository;

    @Override
    public void execute() {
         subscriptionsJpaRepository.deactivateCanceledSubscriptions();
    }

}
