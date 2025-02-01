// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.subscriptions.domain;

import com.google.gson.Gson;
import com.stripe.model.Event;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

public class StripeEventUtil {

    static String customerId(Event event) {

        var object = new Gson()
              .fromJson(
                    event.getData().toJson(),
                    DataObject.class)
              .object();

        return object.customer() != null
              ? object.customer()
              : object.id();

    }

    static String email(Event event) {

        return new Gson()
              .fromJson(
                    event.getData().toJson(),
                    DataObject.class)
              .object()
              .email();

    }

    static String sessionId(Event event) {

        return new Gson()
              .fromJson(
                    event.getData().toJson(),
                    DataObject.class)
              .object()
              .id();

    }

    static String subscriptionId(Event event) {

        return new Gson()
              .fromJson(
                    event.getData().toJson(),
                    DataObject.class)
              .object()
              .id();

    }

    private record DataObject(
          String type,
          Object object) {}

    private record Object(
          String id,
          String customer,
          String email) {}

}
