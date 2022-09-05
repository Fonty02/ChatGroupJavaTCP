import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;


public class ClientGUI extends JFrame{

    private String username;
    private JPanel mainPanel;

    private JLabel  usrLbl;
    private JTextField usrTxt;
    private JButton cnfBtn;

    public ClientGUI()
    {
        this.setContentPane(mainPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Toolkit tk=Toolkit.getDefaultToolkit();
        Dimension screenSize = tk.getScreenSize();
        this.setSize(screenSize.width/3,screenSize.height/3);
        this.setVisible(true);
        cnfBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (usrTxt.getText().length() == 0) {
                    JOptionPane.showMessageDialog(null, "There are incomplete fields", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    username = usrTxt.getText();
                    mainPanel.setVisible(false);
                    chatPanel c=new chatPanel(username);
                    dispose();

                }
            }
        });
    }



    public static void main(String[] args) {
        JFrame c=new ClientGUI();

    }

}
