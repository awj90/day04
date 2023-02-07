// java -cp target/workshop4-1.0-SNAPSHOT.jar sg.edu.nus.iss.app.client.ClientApp localhost:3000
package sg.edu.nus.iss.app.client;

import java.io.Console;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientApp {
    public static void main( String[] args ) {

        String[] arrSplit = args[0].split(":");
        System.out.println(arrSplit[0]);
        System.out.println(arrSplit[1]);

        try {
            Socket sock = new Socket(arrSplit[0], Integer.parseInt(arrSplit[1]));

            InputStream is = sock.getInputStream();
            DataInputStream dis = new DataInputStream(is);

            OutputStream os = sock.getOutputStream();
            DataOutputStream dos = new DataOutputStream(os);

            Console cons = System.console();
            String input = cons.readLine("Send command to the random cookie server: ");

            dos.writeUTF(input);
            dos.flush();

            String response = dis.readUTF();
            if (response.contains("cookie-text")) {
                String[] cookieValArr = response.split("_");
                System.out.printf("Cookie from the server: %s", cookieValArr[1]);
            }

            is.close();
            os.flush();
            os.close();
            sock.close();
            
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } 
    }
}
