// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.javafullstack.accounts.infrastructure;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * Account JPA entity.
 */
@Entity
@Getter
@Setter
@Builder
@Table(name = "accounts", schema = "_accounts")
@AllArgsConstructor
@NoArgsConstructor
class AccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String type;
    private String name;
    private String email;
    private boolean isDeleted;
    private String password;

}
