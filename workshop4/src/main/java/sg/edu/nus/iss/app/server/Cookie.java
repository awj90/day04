package sg.edu.nus.iss.app.server;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Cookie {

    public static String getRandomCookie(String path) {
        String randomCookie = "";

        // Instantiate file using the fully qualified path
        File cookieFile = new File(path);
        
        // This data structure is used to hold all the cookies read from the cookie file
        List<String> cookies = new LinkedList<>();
        Scanner scanner;
        try{
            // Read the cookie file
            scanner = new Scanner(cookieFile);
            // Append every cookie into the linked list
            while (scanner.hasNextLine()) {
                cookies.add(scanner.nextLine());
            }
            scanner.close();
            Random rand = new Random();
            randomCookie = cookies.get(rand.nextInt(cookies.size()));
            System.out.println("RANDOM COOKIE >> " + randomCookie);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return randomCookie;
    }
}
