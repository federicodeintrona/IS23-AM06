package it.polimi.ingsw;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ServerClientHandler implements Runnable{

    Socket socket;

    public ServerClientHandler(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        Scanner ois;
        PrintWriter oos;
        try {
            while (true) {
                ois = new Scanner(socket.getInputStream());
                //convert ObjectInputStream object to String
                String message = ois.nextLine();
                System.out.println("Message Received: " + message);
                //create ObjectOutputStream object
                oos = new PrintWriter(socket.getOutputStream());
                oos.println("Hi Client " + message);
                oos.flush();

                //close resources
                if(message.equalsIgnoreCase("end game")) break;
            }
            socket.close();
            ois.close();
            oos.close();
        }
        catch (IOException e){
            System.err.println(e.getMessage());
        }
    }
}
