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
}
