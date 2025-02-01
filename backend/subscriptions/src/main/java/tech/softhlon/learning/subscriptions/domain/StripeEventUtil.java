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
              .stripeEventObject();

        return object.customer() != null
              ? object.customer()
              : object.id();

    }

    static String email(Event event) {

        return new Gson()
              .fromJson(
                    event.getData().toJson(),
                    DataObject.class)
              .stripeEventObject()
              .email();

    }

    static String sessionId(Event event) {

        return new Gson()
              .fromJson(
                    event.getData().toJson(),
                    DataObject.class)
              .stripeEventObject()
              .id();

    }

    static String subscriptionId(Event event) {

        return new Gson()
              .fromJson(
                    event.getData().toJson(),
                    DataObject.class)
              .stripeEventObject()
              .id();

    }

    static StripeEventObject stripeObject(Event event) {

        return new Gson()
              .fromJson(
                    event.getData().toJson(),
                    DataObject.class)
              .stripeEventObject();

    }

    record DataObject(
          String type,
          StripeEventObject stripeEventObject) {}

    record StripeEventObject(
          String id,
          String customer,
          String email,
          int cancelAt,
          int canceledAt,
          CancelationDetails cancelationDetails) {}

    record CancelationDetails(
          String feedback) {
    }

}
