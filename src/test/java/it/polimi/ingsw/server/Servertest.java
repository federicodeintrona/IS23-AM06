package it.polimi.ingsw.server;

//import junit.framework.Test;
//import junit.framework.TestCase;
//import junit.framework.TestSuite;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.rmi.RemoteException;

import static org.junit.jupiter.api.Assertions.*;
public class Servertest {
    Server server;

    {
        try {
            server = new Server();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void Porttest(){
        assertEquals(server.getPort(), 9876,"La porta è"+server.getPort() );
    }
}
