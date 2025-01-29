// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.subscriptions.infrastructure;

import org.springframework.stereotype.Service;
import tech.softhlon.learning.subscriptions.domain.LoadCheckoutRepository;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Service
class LoadCheckoutRepositoryAdapter implements LoadCheckoutRepository {

    @Override
    public LoadCheckoutResult execute(LoadCheckoutRequest request) {
        return null;
    }

}
