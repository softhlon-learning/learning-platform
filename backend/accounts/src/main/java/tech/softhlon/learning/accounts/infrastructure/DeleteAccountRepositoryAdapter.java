// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.accounts.infrastructure;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tech.softhlon.learning.accounts.domain.DeleteAccountRepository;

@Component
@RequiredArgsConstructor
class DeleteAccountRepositoryAdapter implements DeleteAccountRepository {
    @Override
    public DeleteAccountRequest execute(DeleteAccountRequest request) {
        return null;
    }
}
