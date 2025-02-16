// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) JAVA-FULLSTACK.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.javafullstack.accounts.infrastructure;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import tech.javafullstack.accounts.domain.PersistContactMessageRepository;
import tech.javafullstack.accounts.domain.PersistContactMessageRepository.PersistContactMessageResult.ContactMessagePersisted;
import tech.javafullstack.accounts.domain.PersistContactMessageRepository.PersistContactMessageResult.ContactMessagePersistenceFailed;

/**
 * Persist contact message repository adapter implementation.
 */
@Slf4j
@Component
@RequiredArgsConstructor
class PersistContactMessageRepositoryAdapter implements PersistContactMessageRepository {
    private final ContactMessagesJpaRepository contactMessagesJpaRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public PersistContactMessageResult execute(
          PersistContactMessageRequest request) {

        try {

            var entity = contactMessagesJpaRepository.save(entity(request));
            return new ContactMessagePersisted(entity.getId());

        } catch (Throwable cause) {
            log.error("Error", cause);
            return new ContactMessagePersistenceFailed(cause);
        }

    }


    // -----------------------------------------------------------------------------------------------------------------
    // Private Section
    // -----------------------------------------------------------------------------------------------------------------

    ContactMessageEntity entity(
          PersistContactMessageRequest request) {

        return ContactMessageEntity.builder()
              .subject(request.subject())
              .email(request.email())
              .message(request.message())
              .build();

    }

}
