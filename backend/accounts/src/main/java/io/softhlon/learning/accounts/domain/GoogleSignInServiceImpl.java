// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package io.softhlon.learning.accounts.domain;

import com.google.api.client.auth.openidconnect.IdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import io.softhlon.learning.accounts.domain.GoogleSignInService.Result.Failed;
import io.softhlon.learning.accounts.domain.GoogleSignInService.Result.InvalidCredentialsFailed;
import io.softhlon.learning.accounts.domain.GoogleSignInService.Result.Succeeded;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;

import static io.softhlon.learning.accounts.domain.CheckAccountByEmailRepository.CheckAccountByEmailResult.*;

// --------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Service
class GoogleSignInServiceImpl implements GoogleSignInService {
    private final GoogleIdTokenVerifier verifier;
    private final CheckAccountByEmailRepository checkAccountByEmailRepository;

    public GoogleSignInServiceImpl(
          @Value("${google-client-id}")
          String clientId, CheckAccountByEmailRepository checkAccountByEmailRepository) {
        var transport = new NetHttpTransport();
        var jsonFactory = GsonFactory.getDefaultInstance();
        verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
              .setAudience(Collections.singletonList(clientId))
              .build();
        this.checkAccountByEmailRepository = checkAccountByEmailRepository;
    }

    @Override
    public Result signIn(Request request) {
        try {
            var idToken = verifier.verify(request.credential());
            if (idToken != null) {
                IdToken.Payload payload = idToken.getPayload();
                var email = (String) payload.get("email");
                var name = (String) payload.get("given_name");

                var exists = checkAccountByEmailRepository.execute(new CheckAccountByEmailRepository.CheckAccountByEmailRequest(email));
                return switch (exists) {
                    case AccountExists() -> new Succeeded();
                    case AccountNotFound() -> null;
                    case CheckAccountFailed(Throwable cause) -> new Result.Failed(cause);
                };
            } else {
                return new InvalidCredentialsFailed("Invalid token/credentials");
            }
        } catch (Throwable cause) {
            return new Failed(cause);
        }
    }
}
