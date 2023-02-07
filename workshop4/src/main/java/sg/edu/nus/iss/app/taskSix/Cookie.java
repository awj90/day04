package sg.edu.nus.iss.app.taskSix;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Cookie {

    public static String getRandomCookie(String inputPath, String outputPath, String mailMergePath) {
        String randomCookie = "";

        // Instantiate file using the fully qualified path
        File cookieFile = new File(inputPath);
        
        // This data structure is used to hold all the cookies read from the cookie file
        List<String> cookies = new LinkedList<>();
        String lineBuffer = "";
        int lineCounter = 0;
        int randIndex;
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
            randIndex = rand.nextInt(cookies.size());
            randomCookie = cookies.get(randIndex);
            System.out.println("RANDOM COOKIE >> " + randomCookie);

            // task 6
            File outputFile = new File(outputPath);
            if (!outputFile.exists()) { // create the output file if the file does not exist yet
                FileWriter myWriter = new FileWriter(outputPath);
                System.out.println("New file created: " + outputFile.getName());
                myWriter.write("\n".repeat(randIndex) + randomCookie + "\n".repeat(cookies.size() - randIndex)); // and write the random cookie in the correct line
                myWriter.close();
            } else { // if file already exists
                System.out.println("File already exists, updating existing file.");
                InputStream is = new FileInputStream(outputFile);
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                String line = br.readLine(); // read first line of existing file
                while (line != null) { // continue reading the existing file line by line till the end
                    if (lineCounter == randIndex) {
                        lineBuffer += randomCookie; // populate file with randomCookie at the correct row
                        lineBuffer += "\n";
                    } else {
                        lineBuffer += line; // keep the other rows of the existing file as is
                        lineBuffer += "\n";
                    }
                    line = br.readLine();
                    lineCounter++;
                }
                FileWriter myWriter = new FileWriter(outputPath); // write the updated content back to the file
                myWriter.write(lineBuffer); 
                myWriter.close();
                br.close();
                isr.close();
                is.close();
            }

            // task 7
            File mailMergeFile = new File(mailMergePath);
            InputStream is = new FileInputStream(mailMergeFile);
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line = br.readLine(); // read first line of existing file
            lineBuffer = ""; // re-initialize
            while (line != null) { // continue reading the existing file line by line till the end
                lineBuffer += line.replaceAll("\\$count", String.format("%d", randIndex)); // double backslash required in regex
                lineBuffer += "\n";
                line = br.readLine();
            }
            FileWriter myWriter = new FileWriter(mailMergePath); // write the updated content back to the file
            myWriter.write(lineBuffer); 
            myWriter.close();
            br.close();
            isr.close();
            is.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return randomCookie;
    }
}
