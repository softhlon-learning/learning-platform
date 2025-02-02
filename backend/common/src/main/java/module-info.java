module common {
    exports tech.softhlon.learning.common.domain;
    exports tech.softhlon.learning.common.event;
    exports tech.softhlon.learning.common.hexagonal;
    exports tech.softhlon.learning.common.controller;
    exports tech.softhlon.learning.common.security;
    exports tech.softhlon.learning.common.text;

    requires static lombok;

    requires spring.context;
    requires spring.web;
    requires spring.security.core;
    requires spring.beans;
    requires org.apache.tomcat.embed.core;
    requires org.slf4j;
}
