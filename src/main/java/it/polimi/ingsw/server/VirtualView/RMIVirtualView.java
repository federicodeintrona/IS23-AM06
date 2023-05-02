package it.polimi.ingsw.server.VirtualView;

import it.polimi.ingsw.server.Messages.IntArrayMessage;
import it.polimi.ingsw.server.Messages.MatrixMessage;
import it.polimi.ingsw.server.Messages.Message;
import it.polimi.ingsw.server.Messages.MessageTypes;
import it.polimi.ingsw.server.Model.Board;
import it.polimi.ingsw.server.Model.Bookshelf;

import java.beans.PropertyChangeEvent;
import java.io.IOException;
import java.util.ArrayList;

public class RMIVirtualView extends VirtualView{




    @Override
    public void propertyChange(PropertyChangeEvent evt) {

        switch (evt.getPropertyName()) {
            case "start" ->
            {



            }
            case "board" -> {
                MatrixMessage boardmsg = new MatrixMessage();
                boardmsg.setMatrix(((Board) evt.getSource()).getGamesBoard());
                boardmsg.setType(MessageTypes.VIEW);
            }
            case "bookshelf" -> {
                MatrixMessage bookmsg = new MatrixMessage();
                bookmsg.setMatrix(((Bookshelf) evt.getSource()).getTiles());
                bookmsg.setType(MessageTypes.VIEW);

            }
            case "points" -> {
                IntArrayMessage pointsmsg = new IntArrayMessage();
                pointsmsg.setIntegers(((ArrayList<Integer>) evt.getNewValue()));
                pointsmsg.setType(MessageTypes.VIEW);
            }
            case "player" -> {
                Message playersmsg = new Message();
                playersmsg.setUsername(((String) evt.getNewValue()));
                playersmsg.setType(MessageTypes.VIEW);

            }
        }
    }



}
