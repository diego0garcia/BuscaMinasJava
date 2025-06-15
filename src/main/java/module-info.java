module es.diego.buscaminas {
    requires javafx.controls;
    requires javafx.media;
    requires javafx.fxml;
         requires java.base;
         requires javafx.base;
         requires javafx.graphics;

    opens es.diego.buscaminas to javafx.fxml;
    exports es.diego.buscaminas;
}
