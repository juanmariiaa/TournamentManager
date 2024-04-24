module org.juanmariiaa {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.xml.bind;
    requires java.sql;

    opens org.juanmariiaa to javafx.fxml,java.xml.bind;
    opens org.juanmariiaa.model.connection to java.xml.bind;
    exports org.juanmariiaa;
}
