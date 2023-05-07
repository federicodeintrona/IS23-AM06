package it.polimi.ingsw.client.View.CLI;

import it.polimi.ingsw.client.ClientState;
import it.polimi.ingsw.client.Networker;
import it.polimi.ingsw.client.NetworkerRmi;
import it.polimi.ingsw.server.CommonObjective.CommonObjective;
import it.polimi.ingsw.server.CommonObjective.CommonObjective1;
import it.polimi.ingsw.server.CommonObjective.CommonObjective2;
import it.polimi.ingsw.server.Model.Board;
import it.polimi.ingsw.server.Model.Bookshelf;
import it.polimi.ingsw.server.Model.Sachet;
import it.polimi.ingsw.server.Model.Tiles;
import it.polimi.ingsw.server.PersonalObjective.PersonalObjective;
import it.polimi.ingsw.utils.Matrix;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CLIPrintTest {

    @DisplayName("Print Board")
    @Test
    public void printBoard(){
        Object obj=new Object();
        ClientState clientState=new ClientState();
        Networker networker=new NetworkerRmi();
        CLIMain cliMain=new CLIMain(obj, clientState, networker);
        CLIPrint cliPrint=new CLIPrint(cliMain);

        Board board=new Board(2, new Sachet());
        board.BoardInitialization();

        cliPrint.printBoard(board.getGamesBoard());
    }

    @DisplayName("Print Bookshelf")
    @Test
    public void printBookshelf(){
        Object obj=new Object();
        ClientState clientState=new ClientState();
        Networker networker=new NetworkerRmi();
        CLIMain cliMain=new CLIMain(obj, clientState, networker);
        CLIPrint cliPrint=new CLIPrint(cliMain);

        Bookshelf bookshelf1=new Bookshelf();
        ArrayList<Tiles> arrayList1=new ArrayList<>();
        arrayList1.add(Tiles.GREEN);
        arrayList1.add(Tiles.PINK);
        bookshelf1.addTile(arrayList1, 1);

        Bookshelf bookshelf2=new Bookshelf();
        ArrayList<Tiles> arrayList2=new ArrayList<>();
        arrayList2.add(Tiles.GREEN);
        arrayList2.add(Tiles.PINK);
        bookshelf2.addTile(arrayList2, 2);

        HashMap<String, Matrix> hashMap=new HashMap<>();
        hashMap.put("Ale", bookshelf1.getTiles());
        hashMap.put("Bob", bookshelf2.getTiles());

        cliPrint.printAllBookshelf(hashMap);
    }

    @DisplayName("Print Personal Objective")
    @Test
    public void printPersonalObjective(){
        Object obj=new Object();
        ClientState clientState=new ClientState();
        Networker networker=new NetworkerRmi();
        CLIMain cliMain=new CLIMain(obj, clientState, networker);
        CLIPrint cliPrint=new CLIPrint(cliMain);

        PersonalObjective personalObjective=new PersonalObjective(1);

        cliPrint.printPersonalObjective(personalObjective.getCard());

    }

    @DisplayName("Print Bookshelf & Personal Objective")
    @Test
    public void printBookshelfPersonalObjective(){
        Object obj=new Object();
        ClientState clientState=new ClientState();
        Networker networker=new NetworkerRmi();
        CLIMain cliMain=new CLIMain(obj, clientState, networker);
        CLIPrint cliPrint=new CLIPrint(cliMain);

        Bookshelf bookshelf1=new Bookshelf();
        ArrayList<Tiles> arrayList1=new ArrayList<>();
        arrayList1.add(Tiles.GREEN);
        arrayList1.add(Tiles.PINK);
        bookshelf1.addTile(arrayList1, 1);

        PersonalObjective personalObjective=new PersonalObjective(1);

        cliPrint.printBookshelfPersonalObjective(bookshelf1.getTiles(), personalObjective.getCard());
    }

    @DisplayName("Print Help")
    @Test
    public void printHelp(){
        Object obj=new Object();
        ClientState clientState=new ClientState();
        Networker networker=new NetworkerRmi();
        CLIMain cliMain=new CLIMain(obj, clientState, networker);
        CLIPrint cliPrint=new CLIPrint(cliMain);

        cliPrint.help();
    }

    @DisplayName("Print Common Objcective")
    @Test
    public void printCommonObjective(){
        Object obj=new Object();
        ClientState clientState=new ClientState();
        Networker networker=new NetworkerRmi();
        CLIMain cliMain=new CLIMain(obj, clientState, networker);
        CLIPrint cliPrint=new CLIPrint(cliMain);

        CommonObjective commonObjective1=new CommonObjective1();
        CommonObjective commonObjective2=new CommonObjective2();

        ArrayList<Integer> arrayList=new ArrayList<>();
        arrayList.add(1);
        arrayList.add(2);

        cliPrint.printCommonObjective(arrayList);

    }





}