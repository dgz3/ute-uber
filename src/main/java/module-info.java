module com.dgz3 {
    requires java.net.http;
    requires org.jsoup;
    requires com.fasterxml.jackson.databind;
    requires java.desktop;
    requires javafx.controls;
    requires javafx.base;
    requires javafx.graphics;
    requires com.fasterxml.jackson.core;
    requires javafx.media;
    exports com.dgz3.component.jsontab.model to com.fasterxml.jackson.databind;
    opens com.dgz3.component.jsontab.model to javafx.base;
    exports com.dgz3;
}
