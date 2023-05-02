package it.polimi.ingsw.server.VirtualView;

import it.polimi.ingsw.server.Messages.*;
import it.polimi.ingsw.server.Model.Board;
import it.polimi.ingsw.server.Model.Bookshelf;

import java.beans.PropertyChangeEvent;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class TCPVirtualView extends VirtualView{

    Socket socket;
    ObjectOutputStream oos;

    public TCPVirtualView(Socket socket) throws IOException {
        this.socket = socket;
        oos = new ObjectOutputStream(socket.getOutputStream());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

        switch (evt.getPropertyName()) {
            case "start" ->
            {
                ViewMessage viewmsg = new ViewMessage();
                //Board
                //Obj
                //Usernames


            }
            case "board" -> {
                MatrixMessage boardmsg = new MatrixMessage();
                boardmsg.setMatrix(((Board) evt.getSource()).getGamesBoard());
                boardmsg.setType(MessageTypes.VIEW);
                try {
                    oos.writeObject(boardmsg);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            case "bookshelf" -> {
                MatrixMessage bookmsg = new MatrixMessage();
                bookmsg.setMatrix(((Bookshelf) evt.getSource()).getTiles());
                bookmsg.setType(MessageTypes.VIEW);
                try {
                    oos.writeObject(bookmsg);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            case "points" -> {
                IntArrayMessage pointsmsg = new IntArrayMessage();
                pointsmsg.setIntegers(((ArrayList<Integer>) evt.getNewValue()));
                pointsmsg.setType(MessageTypes.VIEW);
                try {
                    oos.writeObject(pointsmsg);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            case "player" -> {
                Message playersmsg = new Message();
                playersmsg.setUsername(((String) evt.getNewValue()));
                playersmsg.setType(MessageTypes.VIEW);
                try {
                    oos.writeObject(playersmsg);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

}
