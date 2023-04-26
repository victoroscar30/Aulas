package Aula2604;
import java.io.*;
import java.net.*;
import java.util.*;

import javax.crypto.MacSpi;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SimpleChatClient {
    JTextArea incoming;
    JTextField outgoing;
    BufferedReader reader;
    PrintWriter writer;
    Socket client;

    public static void main(String[] args) {
        SimpleChatClient client = new SimpleChatClient();
        client.go();
    }

    public void go(){
        JFrame frame = new JFrame("Luidcrusly Simple Chat Client");
        JPanel mainPanel = new JPanel();
        incoming = new JTextArea( 15, 50);
        incoming.setLineWrap(true);
        incoming.setWrapStyleWord(true);
        incoming.setEditable(true);
        JScrollPane qScroller = new JScrollPane(incoming);
        qScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        qScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        outgoing = new JTextField(20);
        JButton sendButton = new JButton("send");
        sendButton.addActionListener(new SendButtonListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        });

        mainPanel.add(qScroller);
        mainPanel.add(outgoing);
        mainPanel.add(sendButton);
        setUpNetworking();

        Thread readerThread = new Thread(new IncomingReader());
        readerThread.start();

        frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
        frame.setSize(480,500);
        frame.setVisible(true);
    }

    private void setUpNetworking()
    {
        try{
            sock = new Socket("localhost",5000);
            InputStreamReader StreamReader = InputStreamReader(sock.getInputStream());
            Reader streamReader = null;
            reader = new BufferedReader(streamReader);
            writer = new PrintWriter(sock.GetOutPutStream());
            System.out.println("networkinf establised");

        }catch(IOException ex){
            ex.printStackTrace();
        }
    }

    public abstract class SendButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent ev)
        {
            try{
                writer.println(outgoing.getText());
                writer.flush();
            }catch(Exception ex){
                ex.printStackTrace();
            }
            outgoing.setText("");
            outgoing.requestFocus();
        }
    }

    public class IncomingReader implements Runnable
    {
        public void run() {
            String message;
            try{
                while((message = reader.readLine()) != null) {
                    System.out.println("read " + message);
                    incoming.append(message + "/n");
                }
            }catch(Exception ex){

            }
        }
        
        
    }
}
