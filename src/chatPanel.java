import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class chatPanel extends JFrame {
    private JPanel mainPanel;


    private String USRN;
    public  Socket dataSocket;
    public  DataInputStream in;
    public   DataOutputStream out;
    DefaultListModel<String> listModel;
    private JList chatList;
    private JButton button1;
    private JTextField msgTxt;


    public chatPanel(String username) {
        this.listModel = new DefaultListModel<>();
        this.chatList.setModel(this.listModel);
        this.USRN = username;
        this.connect("127.0.0.1", 15700,USRN);
        listen();
        this.setContentPane(mainPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension screenSize = tk.getScreenSize();
        this.setSize(screenSize.width / 2, screenSize.height / 2);
        this.setVisible(true);


        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String msg = msgTxt.getText();
                if (msg.length() > 0) {
                    try {
                        listModel.addElement("YOU: " + msg);
                        msgTxt.setText(null);
                        out.writeBytes(msg+"\n");
                    } catch (Exception ex) {
                        System.out.println(ex);
                    }
                }
            }
        });
    }


    public void connect(String hostname, int port, String usr) {
        try {
            dataSocket = new Socket(hostname, port);
            in = new DataInputStream(dataSocket.getInputStream());
            out=new DataOutputStream(dataSocket.getOutputStream());
            out.writeBytes(usr + "\n");
        } catch (UnknownHostException e) {
            System.out.println(e);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public void listen() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    String msg;
                    try {
                        msg=in.readLine();
                        listModel.addElement(msg);
                    } catch (IOException e) {
                        System.out.println(e);
                    }
                }
            }
        }).start();
    }

}
