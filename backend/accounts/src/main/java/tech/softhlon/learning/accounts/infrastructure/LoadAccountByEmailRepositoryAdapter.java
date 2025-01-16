// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) IO.PMARAT - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.accounts.infrastructure;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tech.softhlon.learning.accounts.domain.LoadAccountByEmailRepository;
import tech.softhlon.learning.common.hexagonal.PersistenceAdapter;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Slf4j
@Service
@PersistenceAdapter
@RequiredArgsConstructor
class LoadAccountByEmailRepositoryAdapter implements LoadAccountByEmailRepository {
    @Override
    public LoadAccountByEmailResult execute(LoadAccountByEmailRequest request) {
        return null;
    }
}
