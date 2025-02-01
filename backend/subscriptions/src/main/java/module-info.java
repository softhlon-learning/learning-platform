module subscriptions {
    exports tech.softhlon.learning.subscriptions.gateway;
    requires common;
    requires stripe.java;
    requires com.google.gson;
    requires accounts;
    requires static lombok;
    requires spring.context;
    requires spring.beans;
    requires org.slf4j;
    requires jakarta.persistence;
    requires org.apache.tomcat.embed.core;
}
