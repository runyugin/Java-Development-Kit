package hometask_2.client;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import hometask_2.Logger;

import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class ClientWindow implements ClientRecieve{
    /*
     * Создать окно клиента чата. Окно должно содержать JtextField
     * для ввода логина, пароля, IP-адреса сервера, порта подключения
     * к серверу, область ввода сообщений, JTextArea область просмотра
     * сообщений чата и JButton подключения к серверу и отправки сообщения
     * в чат. Желательно сразу сгруппировать компоненты, относящиеся
     * к серверу сгруппировать на JPanel сверху экрана, а компоненты,
     * относящиеся к отправке сообщения – на JPanel снизу
     */
    private static final int WIDTH = 300;
    private static final int HEIGHT = 300;

    public boolean closed = false;
    public boolean loginIsPressed = false;
    private boolean connected=false;

    private static String[] randomnames = { "Alfred", "Bill", "Brandon", "Calvin", "Dean", "Dustin", "Ethan", "Harold",
    "Henry", "Irving", "Jason", "Jenssen", "Josh", "Martin", "Nick", "Norm", "Orin", "Pat", "Perry",
    "Ron", "Shawn", "Tim", "Will", "Wyatt" };

    //private final String[] data1 = { "Юки", "Дуглас", "Оникс", "Симба", "Норман" };
    JList<String> users = new JList<String>(); //поле участников

    private static JFrame frame = new JFrame("Chat Client");
    private static JButton login = new JButton("Login");
    private static JButton send = new JButton("Send");

    private static JTextField serverIP = new JTextField("127.0.0.1");
    private static JTextField serverPort = new JTextField("1201");
    private static JTextField nickname = new JTextField(randomnames[new Random().nextInt(randomnames.length)]);
    private static JTextField passwd = new JTextField("123456");

    private static JTextField sendText = new JTextField();
    private JTextArea chatHistory = new JTextArea(4, 1);

    private static JPanel topPanel = new JPanel(new GridBagLayout());
    private static JPanel centerPanel = new JPanel(new BorderLayout());
    private static JPanel bottomPanel = new JPanel(new BorderLayout());

    GridBagConstraints c = new GridBagConstraints();

    JScrollPane slog = new JScrollPane(chatHistory,
            JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
            JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);


    private void fillPanel() {
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        // c.gridwidth = 3;
        c.gridx = 0;
        c.gridy = 0;
        topPanel.add(serverIP, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        // c.gridwidth = 3;
        c.gridx = 1;
        c.gridy = 0;
        topPanel.add(serverPort, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        // c.gridwidth = 3;
        c.gridx = 2;
        c.gridy = 0;
        topPanel.add(login, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        // c.gridwidth = 3;
        c.gridx = 0;
        c.gridy = 1;
        topPanel.add(nickname, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        // c.gridwidth = 3;
        c.gridx = 1;
        c.gridy = 1;
        topPanel.add(passwd, c);
        chatHistory.setEditable(false);
        bottomPanel.add(sendText, BorderLayout.CENTER);
        bottomPanel.add(send, BorderLayout.EAST);
        centerPanel.add(slog, BorderLayout.CENTER);
        centerPanel.add(users, BorderLayout.EAST);
        //users.setListData(data1);
    }
    
    public ClientRecieve getInterface(){
        return this;
    }
    private ClientSend clientSend;

    public ClientWindow(Client newClient) {
        this.clientSend=newClient;
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WIDTH, HEIGHT);
        frame.setLocationRelativeTo(null);
        // frame.setResizable(false);
        fillPanel();
        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(centerPanel, BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.SOUTH);
        frame.setVisible(true);

        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("presed");
                loginIsPressed = true;
            }
        });

        send.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!sendText.getText().isEmpty()) {
                    sendString();
                }
            }
        });

        sendText.addKeyListener(new KeyListener() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent e) {
                if (e.getKeyCode() == 10) {
                    sendString();
                }
                if (e.getKeyCode() == 27) {
                    sendText.setText(null);
                }
            }

            @Override
            public void keyTyped(java.awt.event.KeyEvent e) {
            }

            @Override
            public void keyPressed(java.awt.event.KeyEvent e) {
            }

        });

    }

    private void sendString() {
        if (!sendText.getText().equals("")) {
            String out = Logger.getTime() + nickname.getText() + " : " + sendText.getText() + "\n";
            chatHistory.setCaretPosition(chatHistory.getDocument().getLength());
            clientSend.sendtoServer(out);
            if(connected){
                chatHistory.append(out);
            }
            sendText.setText(null);
            Logger.writelog(getNick(),out);
        }
    }
    
    @Override
    public void resieveMsg(String r) {
        chatHistory.append(r+"\n");
        Logger.writelog(getNick(),r);
        chatHistory.setCaretPosition(chatHistory.getDocument().getLength());
    }

    @Override
    public void checkConnection(String msg,boolean state) {
       chatHistory.append(msg+"\n");
       connected=state;
    }
    
    public int getServerPort() {
        return Integer.parseInt(serverPort.getText());
    }

    public String getServerIP() {
        return serverIP.getText();
    }

    public String getNick() {
        return nickname.getText();
    }

    public String getPass() {
        return passwd.getText();
    }

    @Override
    public void nameListUpdate(String[] data) {
        users.setListData(data);
    }

}
