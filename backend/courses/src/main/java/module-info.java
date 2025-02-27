module courses {
    requires common;
    requires subscriptions;

    requires static lombok;

    requires spring.tx;
    requires spring.core;
    requires spring.web;
    requires spring.data.commons;
    requires spring.beans;
    requires spring.context;

    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.dataformat.yaml;
    requires jakarta.persistence;
    requires java.rmi;
    requires org.hibernate.orm.core;
    requires org.yaml.snakeyaml;
    requires org.slf4j;
    requires com.google.gson;
    requires jakarta.servlet;
    requires org.checkerframework.checker.qual;
    requires spring.data.jpa;
    requires spring.boot.autoconfigure;
}
