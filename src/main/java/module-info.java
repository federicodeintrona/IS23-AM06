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
    requires java.sql;

    opens it.polimi.ingsw.app to javafx.fxml, javafx.controls, javafx.graphics;
    exports it.polimi.ingsw.app;

    opens it.polimi.ingsw.client.View.GUI to javafx.fxml, javafx.controls, javafx.graphics, javafx.base;
    exports it.polimi.ingsw.client.View.GUI;

    opens it.polimi.ingsw.server.CommonObjective to openpojo;
    opens it.polimi.ingsw.client to java.rmi, javafx.controls, javafx.fxml, javafx.graphics;
    opens it.polimi.ingsw.utils.Messages to java.rmi;
    opens it.polimi.ingsw.server to java.rmi;
    exports it.polimi.ingsw.client.View.GUI.Scene;
    opens it.polimi.ingsw.client.View.GUI.Scene to javafx.controls, javafx.fxml, javafx.graphics;
}