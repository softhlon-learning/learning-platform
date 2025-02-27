module subscriptions {
    exports tech.javafullstack.subscriptions.gateway.operator;
    requires common;
    requires accounts;

    requires static lombok;

    requires spring.web;
    requires spring.data.commons;
    requires spring.data.jpa;
    requires spring.tx;
    requires spring.context;
    requires spring.beans;

    requires com.google.gson;
    requires jakarta.persistence;
    requires org.slf4j;
    requires jakarta.servlet;
    requires org.checkerframework.checker.qual;
    requires stripe.java;
    requires spring.boot.autoconfigure;
    requires com.fasterxml.jackson.datatype.jsr310;
    requires com.fasterxml.jackson.databind;
}
