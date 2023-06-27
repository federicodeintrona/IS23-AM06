package it.polimi.ingsw.server;

import it.polimi.ingsw.server.Exceptions.UsernameAlreadyTaken;
import it.polimi.ingsw.server.VirtualView.RMIVirtualView;
import it.polimi.ingsw.utils.Messages.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LobbyTest {

    Lobby lobby;
    @BeforeEach
    void setUp() {
        try {
            lobby = new Lobby();
            lobby.setController(new Controller(lobby));
            RMIVirtualView view = new RMIVirtualView("ciao");
            view.setDisconnected(true);

            lobby.handleClient("ciao","ciao",view);

            lobby.newLobby("ciao",2);

            RMIVirtualView view1 = new RMIVirtualView("1");
            view1.setDisconnected(true);
            lobby.handleClient("1","1",view1);

        } catch (UsernameAlreadyTaken e) {
            System.out.println("scommettiamo che non succederà mai qui???");
        }
    }

    @Test
    void closeGame() {
        lobby.closeGame(1);
    }
}