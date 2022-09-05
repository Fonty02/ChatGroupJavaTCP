import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ServerThread extends Thread {


    private Socket dataSocket;
    private DataInputStream in;
    private DataOutputStream out;

    String usernameUtente;
    private static ArrayList<ServerThread> clienti = new ArrayList<>();
    public ServerThread(Socket dataSocket) {
        try {
            this.dataSocket = dataSocket;
            in = new DataInputStream(dataSocket.getInputStream());
            out = new DataOutputStream(dataSocket.getOutputStream());
            this.usernameUtente = in.readLine();
            clienti.add(this);
            mandaATutti(usernameUtente + " si e' unito alla chat");
            System.out.println(usernameUtente+" joined the chat");
        } catch (IOException e) {
            System.out.println(e);
        }
    }


    public void mandaATutti(String mes) {
        System.out.println("STO MANDANDO "+mes);
        for (ServerThread s : clienti) {
            if (!s.usernameUtente.equals(this.usernameUtente)) {
                try {
                    s.out.writeBytes(mes + "\n");
                    System.out.println("HO MANDATO "+mes+" a "+s.usernameUtente);
                } catch (IOException e) {
                    System.out.println(e);
                }
            }
        }
    }


    @Override
    public void run() {
        System.out.println("SERVER: in ascolto");
        String mes;
        while (true) {
            try {
                mes = in.readLine();
                System.out.println(mes);
                mandaATutti(this.usernameUtente + ":" + mes);
                System.out.println("MANDATO");
            } catch (IOException e) {
               System.out.println(e);
            }
        }
    }
}
