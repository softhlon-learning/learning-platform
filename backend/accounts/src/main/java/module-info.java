module accounts {
    exports tech.javafullstack.accounts.gateway.operator;
    requires common;

    requires static lombok;

    requires spring.context;
    requires spring.web;
    requires spring.data.jpa;
    requires spring.data.commons;
    requires spring.boot.autoconfigure;
    requires spring.security.crypto;
    requires spring.core;
    requires spring.webmvc;
    requires spring.beans;
    requires spring.security.core;
    requires spring.security.web;
    requires spring.security.config;
    requires spring.context.support;

    requires java.xml.bind;
    requires jjwt.api;
    requires jakarta.persistence;
    requires com.fasterxml.jackson.annotation;
    requires jakarta.servlet;
    requires org.slf4j;
    requires google.api.client;
    requires com.google.api.client;
    requires com.google.api.client.json.gson;
    requires com.google.api.client.auth;
    requires org.checkerframework.checker.qual;
    requires spring.tx;
    requires jakarta.annotation;
}
