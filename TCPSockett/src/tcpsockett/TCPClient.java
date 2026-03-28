package tcpsockett;

import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TCPClient {

    public static void main(String[] args) {

        try {

            Socket socket = new Socket("localhost", 4242);
            System.out.println("Server'a bağlanıldı.");

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader userInput = new BufferedReader(
                    new InputStreamReader(System.in));
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));

            // Server’dan gelen mesajları dinle
            new Thread(() -> {
                String msg;
                try {
                    while ((msg = in.readLine()) != null) {
                        System.out.println("Mesaj: " + msg);
                    }
                } catch (IOException e) {
                    Logger.getLogger(TCPClient.class.getName()).log(Level.SEVERE, null, e);
                }
            }).start();

            String line;
            while (true) {
                System.out.print("Gönder: ");
                line = userInput.readLine();

                out.println(line);

                if (line.equalsIgnoreCase("BYE"))
                    break;
            }

            socket.close();
            System.out.println("Bağlantı kapatıldı.");

        } catch (IOException e) {
            Logger.getLogger(TCPClient.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}