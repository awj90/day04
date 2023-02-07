// java -cp target/workshop4-1.0-SNAPSHOT.jar sg.edu.nus.iss.app.client.ClientApp localhost:3000
package sg.edu.nus.iss.app.solution;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import static sg.edu.nus.iss.app.solution.Cookie.*;

public class ServerApp {
    private static final String ARG_MESSAGE = 
        "usage: java sg.edu.nus.iss.app.server.ServerApp " 
        + " <port no>" + " <cookie file>";
    public static void main( String[] args )
    {
        Socket sock = null;
        InputStream is = null;
        OutputStream os = null;

        try{
            // Get server port from java cli first argument
            String serverPort = args[0];
            System.out.println(serverPort);
            // Get the cookie file from the second arg
            String cookieFile = args[1];
            System.out.println(cookieFile);

            //Get the cookie result file
            String cookieResultFile = args[2];
            System.out.println(cookieResultFile);


            //Get the mailmerge file
            String mailmergeFile = args[3];
            System.out.println(mailmergeFile);

            System.out.printf("Cookie Server started at %s\n"
                    , serverPort);
            // Instantiate server socket object pass in the server
            ServerSocket server = 
                    new ServerSocket(Integer.parseInt(serverPort)); 
            while(true){
                // Client connect to the server ... this is the line where 
                // the server accept a connection from the client
                sock = server.accept(); 
                
                // Get the input data from the client program in byte
                is = sock.getInputStream();
                DataInputStream dis= new DataInputStream(is);

                // Write and response
                os = sock.getOutputStream();
                DataOutputStream dos = new DataOutputStream(os);

                // reading data stream assign to a variable where data is String
                while(true){
                    try{
                        String dataFromClient = dis.readUTF();
                        
                        if(dataFromClient.equals("get-cookie")){
                            String randomCookie = getRandomCookie(cookieFile, 
                                        cookieResultFile,
                                        mailmergeFile);
                            dos.writeUTF("cookie-text_" + randomCookie);
                        }else{
                            dos.writeUTF("Invalid command !");
                        }
                    }catch (EOFException e) {
                        System.out.println("Client disconnected");
                        sock.close();
                        break;
                    }   
                }
            }
        
        }catch(ArrayIndexOutOfBoundsException e){
            // validate arguments array must be more than 1 arg value
            System.out.println(ARG_MESSAGE);
            System.exit(1);
        }catch(IOException e){
            e.printStackTrace();
        }finally {
            try {
                sock.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
    }
}