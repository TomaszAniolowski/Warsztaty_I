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

public class Main {

    public static void main(String[] args) {
        // set the path which You want to use for creating files
        String filePath = new String("src/pl/coderslab/searchEngine/");

        // load words from websites
        String fileNameWP = loadWords("https://www.wp.pl/", filePath);
        String fileNameONET = loadWords("http://www.onet.pl/", filePath);
        String fileNameO2 = loadWords("https://www.o2.pl/", filePath);

        // set the table of words which You want to use for filtering
        String[] censorship = { "oraz", "ponieważ", "się", "lub", "albo", "więc",
                                "dlatego", "aby", "być", "skąd", "był", "lecz",
                                "było", "będę", "mam", "twój", "twoje", "temu",
                                "była", "dla", "jakie", "nad", "nas", "jak", "tego",
                                "jaką", "jakiego", "ale", "jaki", "cię"};

        // filter the words
        censureWords(filePath, fileNameO2, censorship);
        censureWords(filePath, fileNameONET, censorship);
        censureWords(filePath, fileNameWP, censorship);
    }

    // loading words
    private static String loadWords(String websiteURL, String filePath) {

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
            File file = new File(filePath+fileName);
            deleteExistingFile(filePath+fileName);

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
                        if (clean && currentWord.length() >= 3 && !filerRepeat(filePath+fileName, currentWord)){
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

    // getting the name of website
    private static String getName(String websiteURL) {
        String[] parts = websiteURL.split("\\.");
        for (int i = 0; i < parts.length; i++) {
            if (parts[i].contains("www"))
                return parts[i + 1];
        }
        return "unnamedSite";
    }

    // file deletion if exist
    private static void deleteExistingFile(String fileName){
        Path filePath = Paths.get(fileName);
        if (Files.exists(filePath))
            try {
                Files.delete(filePath);
            } catch (IOException e) {
                System.out.println(e);
            }
    }

    // filter for repeated words
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

    // filter the forbidden words
    private static void censureWords(String filePath, String fileName, String[] censorship){
        // bufer for list of words
        StringBuilder list = new StringBuilder();
        File file = new File(filePath+fileName);

        try {
            // input
            Scanner inputFile = new Scanner(file);

            // loading words from source file
            while (inputFile.hasNextLine()){
                String currentWord = inputFile.nextLine();
                boolean censor = true;
                // checking if current word is located in the table of forbidden words
                for (String forbiddenWord: censorship)
                    if (currentWord.equals(forbiddenWord)){
                        censor = false;
                        break;
                    }

                // add
                if (censor)
                    list.append(currentWord).append("\n");

            }
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }

        // target file name
        String FiltredFileName = new String(filePath + "filtred_" + fileName);
        File filtredFile = new File(FiltredFileName);

        // inserting filter words to target file
        try {
            PrintWriter pw = new PrintWriter(filtredFile);
            pw.print(list);
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}
