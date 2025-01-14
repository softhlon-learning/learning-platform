module accounts {
    requires common;
    requires static lombok;
    requires spring.context;
    requires spring.web;
    requires org.apache.tomcat.embed.core;
    requires org.slf4j;
    requires jakarta.persistence;
    requires spring.data.jpa;
    requires spring.data.commons;
    requires spring.boot.autoconfigure;
    requires spring.security.crypto;
    requires spring.core;
    requires spring.webmvc;
    requires spring.beans;
    requires google.api.client;
    requires com.google.api.client;
    requires com.google.api.client.json.gson;
    requires com.google.api.client.auth;
}
