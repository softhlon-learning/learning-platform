module common {
    exports tech.javafullstack.common.domain;
    exports tech.javafullstack.common.event;
    exports tech.javafullstack.common.hexagonal;
    exports tech.javafullstack.common.controller;
    exports tech.javafullstack.common.security;
    exports tech.javafullstack.common.text;

    requires static lombok;

    requires spring.context;
    requires spring.web;
    requires spring.security.core;
    requires spring.beans;

    requires org.slf4j;
    requires org.apache.commons.lang3;
    requires jakarta.servlet;
}
