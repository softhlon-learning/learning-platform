module subscriptions {
    exports tech.softhlon.learning.subscriptions.gateway;
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
    requires org.apache.tomcat.embed.core;
    requires org.checkerframework.checker.qual;
    requires stripe.java;
}
