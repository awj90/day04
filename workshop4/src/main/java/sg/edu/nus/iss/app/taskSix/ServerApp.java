package sg.edu.nus.iss.app.taskSix;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import static sg.edu.nus.iss.app.taskSix.Cookie.*;

public class ServerApp {

    private static final String ARG_MESSAGE = "usage: java sg.edu.nus.iss.app.server.ServerApp <port no> <cookie file> <output file> <mailmerge file>";
    public static void main( String[] args ) {

        // Validate arguments array must not be empty
        try {
            // Get server port from java cli first argument
            String serverPort = args[0];
            System.out.println(serverPort);

            // Get the cookie file from the second arg
            String cookieFile = args[1];
            System.out.println(cookieFile);

            // Get the cookie file from the third arg (if it exists) for task 6
            String outputFile = args[2];
            System.out.println(outputFile);

            // Get the mailmerge file from the fourth arg for task 7
            String mailMergeFile = args[3];
            System.out.println(mailMergeFile);

            System.out.printf("Cookie Server started at %s\n", serverPort);

            // Instantiate ServerSocket object
            ServerSocket server = new ServerSocket(Integer.parseInt(serverPort));

            // Client connects to the server... this is the line where the server accepts a connection from the client
            Socket sock = server.accept();

            // Get the input data from the client program in byte
            InputStream is = sock.getInputStream(); 
            DataInputStream dis = new DataInputStream(is);
            
            // Write and response
            OutputStream os = sock.getOutputStream();
            DataOutputStream dos = new DataOutputStream(os);

            // reading data stream and assigning it to a String variable
            String dataFromClient = dis.readUTF(); 
            if (dataFromClient.equals("get-cookie")) {
                // To Do: Randomize from a cookie class
                String randomCookie = getRandomCookie(cookieFile, outputFile, mailMergeFile);
                dos.writeUTF("cookie-text_" + randomCookie);
            } else {
                dos.writeUTF("Invalid command");
            }

            // Release resources from I/O and socket connection
            is.close();
            os.flush(); // .close() has .flush() included
            os.close(); // same as dos.close()
            sock.close();

        } catch(ArrayIndexOutOfBoundsException e) {
            // System.out.println("java sg.edu.nus.iss.app.server.ServerApp <port no> <cookie file>");
            System.out.println(ARG_MESSAGE);
            System.exit(1);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
