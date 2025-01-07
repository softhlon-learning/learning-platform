module common {
    requires static lombok;
    requires spring.context;
    requires spring.web;
    requires org.apache.tomcat.embed.core;
    requires org.slf4j;
    requires spring.security.core;
    requires spring.beans;

    exports io.softhlon.learning.common.domain;
    exports io.softhlon.learning.common.event;
    exports io.softhlon.learning.common.hexagonal;
    exports io.softhlon.learning.common.controller;
    exports io.softhlon.learning.common.security;
}
