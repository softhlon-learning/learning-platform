// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package io.softhlon.learning.accounts.domain;

import com.google.api.client.auth.openidconnect.IdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import io.softhlon.learning.accounts.domain.GoogleSignInService.Result.Succeeded;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Service
class GoogleSignInServiceImpl implements GoogleSignInService {
    private final GoogleIdTokenVerifier verifier;

    public GoogleSignInServiceImpl(@Value("${google-client-id}") String clientId) {
        var transport = new NetHttpTransport();
        var jsonFactory = GsonFactory.getDefaultInstance();

        System.out.println("Client ID: " + clientId);

        verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
              .setAudience(Collections.singletonList(clientId))
              .build();
    }

    @Override
    public Result signIn(Request request) {
        try {
            System.out.println(request.credential());
            var idToken = verifier.verify(request.credential());

            if (idToken != null) {
                IdToken.Payload payload = idToken.getPayload();
                String email = (String) payload.get("email");
                String givenName = (String) payload.get("given_name");
                System.out.println("name: " + givenName + ", email: " + email);
            } else {
                System.out.println("Invalid ID token.");
            }
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return new Succeeded();
    }
}
