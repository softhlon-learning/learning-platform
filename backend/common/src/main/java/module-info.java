module common {
    requires static lombok;
    requires spring.context;
    requires spring.web;
    requires org.apache.tomcat.embed.core;
    requires org.slf4j;
    requires spring.security.core;
    requires spring.beans;

    exports tech.softhlon.learning.common.domain;
    exports tech.softhlon.learning.common.event;
    exports tech.softhlon.learning.common.hexagonal;
    exports tech.softhlon.learning.common.controller;
    exports tech.softhlon.learning.common.security;
}
