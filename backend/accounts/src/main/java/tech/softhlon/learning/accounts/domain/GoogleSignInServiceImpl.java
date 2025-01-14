// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.accounts.domain;

import com.google.api.client.auth.openidconnect.IdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tech.softhlon.learning.accounts.domain.GoogleSignInService.Result.Failed;
import tech.softhlon.learning.accounts.domain.GoogleSignInService.Result.InvalidCredentialsFailed;
import tech.softhlon.learning.accounts.domain.GoogleSignInService.Result.Succeeded;

import java.util.Collections;
import java.util.UUID;

import static tech.softhlon.learning.accounts.domain.CheckAccountByEmailRepository.CheckAccountByEmailRequest;
import static tech.softhlon.learning.accounts.domain.CheckAccountByEmailRepository.CheckAccountByEmailResult.*;
import static tech.softhlon.learning.accounts.domain.CreateAccountRepository.CreateAccountRequest;
import static tech.softhlon.learning.accounts.domain.CreateAccountRepository.CreateAccountResult.AccountPersisted;
import static tech.softhlon.learning.accounts.domain.CreateAccountRepository.CreateAccountResult.AccountPersistenceFailed;

// --------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Service
class GoogleSignInServiceImpl implements GoogleSignInService {
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
        verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
              .setAudience(Collections.singletonList(clientId))
              .build();
        this.checkAccountByEmailRepository = checkAccountByEmailRepository;
        this.createAccountRepository = createAccountRepository;
        this.jwtService = jwtService;
    }

    @Override
    public Result execute(Request request) {
        try {
            var idToken = verifier.verify(request.credential());
            if (idToken != null) {
                IdToken.Payload payload = idToken.getPayload();
                var email = (String) payload.get("email");
                var name = (String) payload.get("given_name");
                var exists = checkAccountByEmailRepository.execute(new CheckAccountByEmailRequest(email));
                return switch (exists) {
                    case AccountExists(UUID id) -> new Succeeded(token(id, email));
                    case AccountNotFound() -> persistAccount(name, email);
                    case CheckAccountFailed(Throwable cause) -> new Result.Failed(cause);
                };
            } else {
                return new InvalidCredentialsFailed("Invalid token/credentials");
            }
        } catch (Throwable cause) {
            return new Failed(cause);
        }
    }

    // -----------------------------------------------------------------------------------------------------------------
    // Private Section
    // -----------------------------------------------------------------------------------------------------------------

    private Result persistAccount(String name, String email) {
        var result = createAccountRepository.execute(prepareRequest(name, email));
        return switch (result) {
            case AccountPersisted(UUID id) -> new Succeeded(token(id, email));
            case AccountPersistenceFailed(Throwable cause) -> new Failed(cause);
        };
    }

    private CreateAccountRequest prepareRequest(String name, String email) {
        return new CreateAccountRequest(
              AccountType.GOOGLE.name(),
              name, email, null,
              AccountStatus.ACTIVE.name());
    }

    private String token(UUID accountId, String email) {
        return jwtService.generateToken(accountId, email);
    }
}
