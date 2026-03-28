package tcpsockett;

import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientHandler implements Runnable {

    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private int clientID;

    public ClientHandler(Socket socket, int id) {
        this.socket = socket;
        this.clientID = id;

        try {
            in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            TCPServer.clientOutputs.add(out);

        } catch (IOException e) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @Override
    public void run() {
        String message;

        try {
            while ((message = in.readLine()) != null) {

                System.out.println("Client #" + clientID + " mesaj gönderdi: " + message);

                // Herkese gönder
                TCPServer.broadcast("Client #" + clientID + ": " + message);

                if (message.equalsIgnoreCase("BYE"))
                    break;
            }

            socket.close();
            System.out.println("Client #" + clientID + " bağlantısı kapatıldı.");

        } catch (IOException e) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}