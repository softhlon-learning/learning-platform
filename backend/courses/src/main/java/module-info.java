module courses {
    requires common;
    requires static lombok;
    requires spring.context;
    requires spring.web;
    requires org.apache.tomcat.embed.core;
    requires org.slf4j;
    requires spring.data.jpa;
    requires spring.boot.autoconfigure;
    requires jakarta.persistence;
    requires spring.data.commons;
    requires spring.tx;
    requires spring.beans;
    requires spring.core;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.dataformat.yaml;
    requires org.hibernate.orm.core;
    requires org.yaml.snakeyaml;
}
