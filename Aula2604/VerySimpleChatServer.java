package Aula2604;

import java.io.*;
import java.net.*;
import java.util.*;

public class VerySimpleChatServer {
    ArrayList clientOutpurStreams;

    public class ClientHandler implements Runnable {
        BufferedReader reader;
        Socket sock;

        public ClientHandler (Socket clientSocket) {
            try{
                sock = clientSocket;
                InputStreamReader isReader = new InputStreamReader(sock.getInputStream());
                reader = new BufferedReader(isReader);
            } catch(Exception ex){
                ex.printStackTrace();
            }
        }

        public void run(){
            String message;
            try{
                while((message = reader.readLine()) != null){
                    System.out.println("read " + message);
                    tellEveryone(message);
                }
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }

        public static void Main(String[] args)
        {
            new VerySimpleChatServer().go();
        }

        public void go(){
            clientOutpurStreams = new ArrayList<>();
            try{
                ServerSocket serverSock = new ServerSocket(5000);
                while(true){
                    Socket clientSocket = serverSock.accept();
                    PrintWriter writer = new PrintWriter(clientSocket.getOutputStream());
                    clientOutpurStreams.add(writer);
                    Thread t = new Thread(new ClientHandler(clientSocket));
                    t.start();
                    System.out.println("got a connection");
                }
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }

        public void tellEveryone(String message){
            Iterator it = clientOutpurStreams.iterator();
            while(it.hasNext()){
                try{
                    PrintWriter writer = (PrintWriter) it.next();
                    writer.println(message);
                    writer.flush();
                }catch(Exception ex){
                    ex.printStackTrace();
                
                }
        }
    }
}
