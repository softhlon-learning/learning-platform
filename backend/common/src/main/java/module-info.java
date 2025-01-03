module common {
    requires static lombok;
    requires spring.context;
    requires org.slf4j;

    exports io.softhlon.learning.common.domain;
    exports io.softhlon.learning.common.event;
    exports io.softhlon.learning.common.hexagonal;
}