package tcpsockett;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TCPServer {

    // Tüm client’ların çıkışları
    static ArrayList<PrintWriter> clientOutputs = new ArrayList<>();
    static int clientCounter = 0; // client ID sayacı

    public static void main(String[] args) {

        try {
            ServerSocket server = new ServerSocket(4242);
            System.out.println("Server başlatıldı...");
            System.out.println("Bağlantı bekleniyor...");

            while (true) {
                Socket client = server.accept();
                clientCounter++; // yeni client için ID
                System.out.println("Yeni istemci bağlandı: " + client.getInetAddress() + " ID=" + clientCounter);

                ClientHandler handler = new ClientHandler(client, clientCounter);
                new Thread(handler).start();
            }

        } catch (IOException e) {
            Logger.getLogger(TCPServer.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    // Mesajı tüm client’lara gönder
    public static void broadcast(String message) {
        for (PrintWriter writer : clientOutputs) {
            writer.println(message);
        }
    }
}