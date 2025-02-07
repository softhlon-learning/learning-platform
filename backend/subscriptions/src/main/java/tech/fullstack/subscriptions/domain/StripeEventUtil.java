// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.fullstack.subscriptions.domain;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.stripe.model.Event;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * Stripe event utility.
 */
public class StripeEventUtil {

    /**
     * Extract customer Id from Stripe event.
     * @param event Stripe event
     * @return Customer Id
     */
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

    /**
     * Extract email from Stripe event.
     * @param event Stripe event
     * @return Email
     */
    static String email(Event event) {

        return new Gson()
              .fromJson(
                    event.getData().toJson(),
                    DataObject.class)
              .object()
              .email();

    }

    /**
     * Extract session Id from Stripe event.
     * @param event Stripe event
     * @return Session Id
     */
    static String sessionId(Event event) {

        return new Gson()
              .fromJson(
                    event.getData().toJson(),
                    DataObject.class)
              .object()
              .id();

    }

    /**
     * Extract subscription Id from Stripe event.
     * @param event Stripe event
     * @return Subscription Id
     */
    static String subscriptionId(Event event) {

        return new Gson()
              .fromJson(
                    event.getData().toJson(),
                    DataObject.class)
              .object()
              .id();

    }

    /**
     * Extract invoice Id from Stripe event.
     * @param event Stripe event
     * @return Invoice Id
     */
    static String invoiceId(Event event) {

        return new Gson()
              .fromJson(
                    event.getData().toJson(),
                    DataObject.class)
              .object()
              .id();

    }

    /**
     * Extract status from Stripe event.
     * @param event Stripe event
     * @return statuc
     */
    static String status(Event event) {

        return new Gson()
              .fromJson(
                    event.getData().toJson(),
                    DataObject.class)
              .object()
              .status();

    }

    /**
     * Extract Stripe object from Stripe event.
     * @param event Stripe event
     * @return Stripe object
     */
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
