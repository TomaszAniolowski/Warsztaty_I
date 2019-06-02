package pl.coderslab.searchEngine;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.regex.PatternSyntaxException;

public class Main {

    public static void main(String[] args) {
        String fileNameWP = loadWords("https://www.wp.pl/");
        String fileNameONET = loadWords("http://www.onet.pl/");
        String fileNameO2 = loadWords("https://www.o2.pl/");

    }

    private static String loadWords(String websiteURL) {

        // connect
        Connection connect = Jsoup.connect(websiteURL);

        // get name of the website
        String websiteName = getName(websiteURL);

        // create a file name
        String fileName = new String("popular_words_" + websiteName + ".txt");

        try {
            // loading data
            Document doc = connect.get();
            Elements links = doc.select("div");
            File file = new File(fileName);
            deleteExistingFile(fileName);

            // signs for StringTokenizer
            String signs = new String(" .,/:;'\"[]{}-_+=-*0123456789");

            // step by step
            for (Element element : links) {
                // variable for information if word is without everything witch is not a letter
                boolean clean;
                do{
                    // at the begining clean is true
                    clean = true;
                    // divide the line into words
                    StringTokenizer sToken = new StringTokenizer(element.text(), signs);

                    // while is there some word
                    while (sToken.hasMoreTokens()) {
                        // filewriter
                        FileWriter fw = new FileWriter(file, true);

                        // take this word to currentWord variable
                        String currentWord = sToken.nextToken().toLowerCase();

                        // check if there's no character not being letter
                        for (int j = 0; j < currentWord.length(); j++) {

                            // variable for readable
                            char currentChar = currentWord.charAt(j);

                            /* if this character is not a letter,
                               add him to signs fo Tokenizer
                               and give the information about cleaning*/
                            if (!Character.isLetter(currentChar)) {
                                signs = signs.concat(String.valueOf(currentChar));
                                clean = false;
                                break;
                            }
                        }

                        // check if it is clean, if it is at least 3-character word and if it is already in a file
                        if (clean && currentWord.length() >= 3 && !filerRepeat(fileName, currentWord)){
                            fw.append(currentWord + "\n");
                            // save changes
                            fw.close();
                        }
                        else
                            break;


                    }
                    // if it is clean keep going. if not repeat splitting this line with new sign-list
                }while(!clean);
            }


        } catch (IOException e) {
            System.out.println(e);
        }

        return fileName;
    }

    private static void deleteExistingFile(String fileName){
        Path filePath = Paths.get(fileName);
        if (Files.exists(filePath))
            try {
                Files.delete(filePath);
            } catch (IOException e) {
                System.out.println(e);
            }
    }

    private static boolean filerRepeat(String fileName, String word){
        File file = new File(fileName);
        try {
            Scanner inputFile = new Scanner(file);
            while (inputFile.hasNext()){
                String line = inputFile.next();
                if (line.equals(word))
                    return true;
            }
            return false;
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }
        return true;
    }

    private static String getName(String websiteURL) {
        String[] parts = websiteURL.split("\\.");
        for (int i = 0; i < parts.length; i++) {
            if (parts[i].contains("www"))
                return parts[i + 1];
        }
        return "unnamedSite";
    }
}
