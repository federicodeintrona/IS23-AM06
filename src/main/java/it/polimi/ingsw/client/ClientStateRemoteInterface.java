package it.polimi.ingsw.client;

import it.polimi.ingsw.server.CommonObjective.CommonObjective;
import it.polimi.ingsw.server.PersonalObjective.PersonalObjective;
import it.polimi.ingsw.utils.Matrix;

import java.rmi.RemoteException;
import java.util.ArrayList;

public interface ClientStateRemoteInterface {
    public void setUsername(String username) throws RemoteException;

    public void setMyBookshelf(Matrix myBookshelf) throws RemoteException;

    public void setAllBookshelf(ArrayList<Matrix> allBookshelf) throws RemoteException;

    public void setBoard(Matrix board) throws RemoteException;

    public void setAllUsername(ArrayList<String> allUsername) throws RemoteException;

    public void setMyPO(PersonalObjective myPO) throws RemoteException;

    public void setCommonObjectives(ArrayList<CommonObjective> commonObjectives) throws RemoteException;
}
