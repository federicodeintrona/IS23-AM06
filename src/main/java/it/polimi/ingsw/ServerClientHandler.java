package it.polimi.ingsw;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.lang.InterruptedException;
public class ServerClientHandler implements Runnable  {

    Socket socket;
    Scanner ois;
    PrintWriter oos;

    public ServerClientHandler(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            while (true) {
                ois = new Scanner(socket.getInputStream());
                //convert ObjectInputStream object to String
                String message = ois.nextLine();
                this.wait();
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
        catch (IOException | InterruptedException e){
            System.err.println(e.getMessage());
        }
    }
}
