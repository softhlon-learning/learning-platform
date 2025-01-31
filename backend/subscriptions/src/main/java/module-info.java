module subscriptions {
    exports tech.softhlon.learning.subscriptions.gateway;
    requires common;
    requires static lombok;
    requires spring.context;
    requires spring.web;
    requires org.apache.tomcat.embed.core;
    requires org.slf4j;
    requires jakarta.persistence;
    requires spring.data.commons;
    requires spring.boot.autoconfigure;
    requires spring.data.jpa;
    requires spring.beans;
    requires stripe.java;
    requires com.google.gson;
}
