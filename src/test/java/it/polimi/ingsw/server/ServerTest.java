package it.polimi.ingsw.server;

import org.junit.jupiter.api.Test;
import org.json.simple.parser.ParseException;
import java.io.IOException;
import java.rmi.RemoteException;

import static org.junit.jupiter.api.Assertions.*;

class ServerTest {

    Server server;

    {
        try {
            server = new Server();
        }
        catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void Porttest(){
        //assertEquals(server.getPort(), 9876,"La porta è"+server.getPort() );
    }
}