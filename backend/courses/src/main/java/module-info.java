module courses {
    requires common;
    requires subscriptions;
    requires spring.tx;
    requires spring.core;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.dataformat.yaml;
    requires org.hibernate.orm.core;
    requires org.yaml.snakeyaml;
    requires static lombok;
    requires spring.context;
    requires org.slf4j;
    requires com.google.gson;
    requires org.apache.tomcat.embed.core;
    requires jakarta.persistence;
}
