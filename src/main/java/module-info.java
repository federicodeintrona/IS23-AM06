module it.polimi.ingsw {
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.fxml;
    requires json.simple;
    requires java.rmi;
    requires java.datatransfer;
    requires java.desktop;
    requires openpojo;
    requires javatuples;

    opens it.polimi.ingsw.app to javafx.fxml, javafx.controls, javafx.graphics;
    exports it.polimi.ingsw.app;

    opens it.polimi.ingsw.server.CommonObjective to openpojo;
}