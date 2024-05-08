module org.juanmariiaa {
    exports org.juanmariiaa;
    exports org.juanmariiaa.model.DAO;
    exports org.juanmariiaa.model.domain;
    exports org.juanmariiaa.model.enums;
    exports org.juanmariiaa.model.connection;

    requires java.sql;
    requires java.xml;
    requires java.xml.bind;
    requires javafx.base;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;


    opens org.juanmariiaa to javafx.fxml;
    opens org.juanmariiaa.model.DAO to java.sql;
    opens org.juanmariiaa.model.domain to javafx.base;
    opens org.juanmariiaa.model.connection to java.xml.bind;
    exports org.juanmariiaa.view;
    opens org.juanmariiaa.view to javafx.fxml;
}
