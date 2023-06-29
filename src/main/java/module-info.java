module it.polimi.ingsw {

    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.fxml;
    requires json.simple;
    requires java.rmi;
    requires java.datatransfer;
    requires java.desktop;
    requires java.sql;
    requires openpojo;
    requires javatuples;



    opens it.polimi.ingsw.app to javafx.fxml, javafx.controls, javafx.graphics;
    opens it.polimi.ingsw.client to java.rmi, javafx.controls, javafx.fxml, javafx.graphics;
    opens it.polimi.ingsw.client.View.GUI.Scene to javafx.controls, javafx.fxml, javafx.graphics;
    opens it.polimi.ingsw.server to java.rmi;
    opens it.polimi.ingsw.server.CommonObjective to openpojo;
    opens it.polimi.ingsw.utils.Messages to java.rmi;



    exports it.polimi.ingsw.app;
    exports it.polimi.ingsw.client;
    exports it.polimi.ingsw.server;
    exports it.polimi.ingsw.utils;
    opens it.polimi.ingsw.utils.Timer to java.rmi;

}