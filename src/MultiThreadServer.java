import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class MultiThreadServer {
    private Socket dataSocket;
    private ServerSocket connectionSocket;

    public void connect(int port) {
        try {
            connectionSocket = new ServerSocket(port);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void execute() {
        try {
            while (true) {
                dataSocket = connectionSocket.accept();
                System.out.println("CONNESSIONE ACCETTATA");
                ServerThread thread = new ServerThread(dataSocket);
                thread.start();
                System.out.println("CONNESSIONE IN GESTIONE");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static void main(String[] args) {
        MultiThreadServer server = new MultiThreadServer();
        server.connect(15700);
        server.execute();
    }

}
