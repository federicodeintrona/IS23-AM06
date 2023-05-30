package it.polimi.ingsw.server.VirtualView;

import it.polimi.ingsw.server.ServerClientHandler;
import it.polimi.ingsw.utils.Messages.MessageTypes;
import it.polimi.ingsw.utils.Messages.ViewMessage;

import java.beans.PropertyChangeEvent;

public class TCPVirtualView extends VirtualView{

    private final ServerClientHandler client;


    public TCPVirtualView(String username,ServerClientHandler handler) {
        setUsername(username);
        this.client = handler;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

        //objectName           Object

        //board                Matrix
        //bookshelf w/ name    Matrix
        //currPlayer           String
        //playerNames          List<String>
        //commonObj            List<Integer>
        //publicPoints w/ name List<Integer>
        //selectedTiles        List<Tiles>
        //personalObj          HashMap
        //privatePoints        int
        //Chat                 Message??

        //w/name vuol dire che nel campo username del messaggio scrivo
        //lo username del proprietario

        System.out.println(getUsername()+" disconnesso TCP virtual view is on: " + evt.getNewValue());

        if(!isDisconnected()) {
            System.out.println(getUsername()+" TCP virtual view is on: " + evt.getNewValue());

            ViewMessage viewMsg = new ViewMessage();
            viewMsg.setType(MessageTypes.VIEW);
            viewMsg.setContent(evt.getSource());
            viewMsg.setObjName((String)evt.getNewValue());
            viewMsg.setUsername((String) evt.getOldValue());

            client.sendMessage(viewMsg);
        }
    }

}
