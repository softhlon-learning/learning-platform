// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.accounts.domain;

import com.google.api.client.auth.openidconnect.IdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tech.softhlon.learning.accounts.domain.CheckAccountByEmailRepository.CheckAccountByEmailResult.AccountExists;
import tech.softhlon.learning.accounts.domain.CheckAccountByEmailRepository.CheckAccountByEmailResult.AccountIsDeleted;
import tech.softhlon.learning.accounts.domain.CheckAccountByEmailRepository.CheckAccountByEmailResult.AccountNotFound;
import tech.softhlon.learning.accounts.domain.CheckAccountByEmailRepository.CheckAccountByEmailResult.CheckAccountFailed;
import tech.softhlon.learning.accounts.domain.CreateAccountRepository.CreateAccountResult.AccountPersisted;
import tech.softhlon.learning.accounts.domain.CreateAccountRepository.CreateAccountResult.AccountPersistenceFailed;
import tech.softhlon.learning.accounts.domain.GoogleSignInService.Result.AccountIsDeletedFailed;
import tech.softhlon.learning.accounts.domain.GoogleSignInService.Result.Failed;
import tech.softhlon.learning.accounts.domain.GoogleSignInService.Result.InvalidCredentialsFailed;
import tech.softhlon.learning.accounts.domain.GoogleSignInService.Result.Succeeded;

import java.util.Collections;
import java.util.UUID;

import static tech.softhlon.learning.accounts.domain.AccountType.GOOGLE;

// --------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Slf4j
@Service
class GoogleSignInServiceImpl implements GoogleSignInService {

    private static final String ACCOUNT_TS_DELETED = "Account has been deleted before";
    private static final String INVALID_CREDENTIALS = "Invalid token/credentials";
    private static final String EMAIL = "email";
    private static final String GIVEN_NAME = "given_name";
    private final GoogleIdTokenVerifier verifier;
    private final CheckAccountByEmailRepository checkAccountByEmailRepository;
    private final CreateAccountRepository createAccountRepository;
    private final JwtService jwtService;

    public GoogleSignInServiceImpl(
          @Value("${google-client-id}") String clientId,
          CheckAccountByEmailRepository checkAccountByEmailRepository,
          CreateAccountRepository createAccountRepository,
          JwtService jwtService) {

        var transport = new NetHttpTransport();
        var jsonFactory = GsonFactory.getDefaultInstance();
        this.checkAccountByEmailRepository = checkAccountByEmailRepository;
        this.createAccountRepository = createAccountRepository;
        this.jwtService = jwtService;
        verifier =
              new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                    .setAudience(Collections.singletonList(clientId))
                    .build();

    }

    @Override
    public Result execute(
          String credential) {

        try {
            var idToken = verifier.verify(credential);
            if (idToken != null) {
                IdToken.Payload payload = idToken.getPayload();
                var email = (String) payload.get(EMAIL);
                var name = (String) payload.get(GIVEN_NAME);
                var exists = checkAccountByEmailRepository.execute(email);

                return switch (exists) {
                    case AccountExists(UUID id) -> new Succeeded(token(id, email));
                    case AccountNotFound() -> persistAccount(name, email);
                    case CheckAccountFailed(Throwable cause) -> new Failed(cause);
                    case AccountIsDeleted() -> new AccountIsDeletedFailed(ACCOUNT_TS_DELETED);
                };
            } else {
                return new InvalidCredentialsFailed(INVALID_CREDENTIALS);
            }
        } catch (Throwable cause) {
            log.error("Error", cause);
            return new Failed(cause);
        }

    }

    // -----------------------------------------------------------------------------------------------------------------
    // Private Section
    // -----------------------------------------------------------------------------------------------------------------

    private Result persistAccount(
          String name,
          String email) {

        var result = createAccountRepository.execute(
              GOOGLE,
              name,
              email,
              null);

        return switch (result) {
            case AccountPersisted(UUID id) -> new Succeeded(token(id, email));
            case AccountPersistenceFailed(Throwable cause) -> new Failed(cause);
        };

    }

    private String token(
          UUID accountId,
          String email) {

        return jwtService.generateToken(
              accountId,
              email);

    }

}
