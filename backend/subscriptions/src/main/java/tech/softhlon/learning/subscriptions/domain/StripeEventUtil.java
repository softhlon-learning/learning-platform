// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.subscriptions.domain;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
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

    static String invoiceId(Event event) {

        return new Gson()
              .fromJson(
                    event.getData().toJson(),
                    DataObject.class)
              .object()
              .id();

    }

    static String status(Event event) {

        return new Gson()
              .fromJson(
                    event.getData().toJson(),
                    DataObject.class)
              .object()
              .status();

    }

    static StripeEventObject stripeObject(Event event) {

        return new Gson()
              .fromJson(
                    event.getData().toJson(),
                    DataObject.class)
              .object();

    }

    record DataObject(
          String type,
          StripeEventObject object) {}

    record StripeEventObject(
          String id,
          String customer,
          String email,
          String status,
          @SerializedName("current_period_start")
          String periodStartAt,
          @SerializedName("current_period_end")
          String periodEndAt,
          @SerializedName("latest_invoice")
          String invoiceId,
          @SerializedName("cancel_at")
          String cancelAt,
          @SerializedName("canceled_at")
          String canceledAt,
          @SerializedName("cancellation_details")
          CancelationDetails cancelationDetails) {}

    record CancelationDetails(
          String feedback) {
    }

}
