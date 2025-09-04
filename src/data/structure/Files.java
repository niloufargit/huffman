package data.structure;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Files {

    List<String> allFiles = new ArrayList<>();
    HuffmanCode huff = new HuffmanCode();
    String filePath = "/textfiles/"+"multiples" + "/page.txt";
    String filePathAbsolue =  System.getProperty("user.dir") + File.separator + filePath;
    String encodedPath = System.getProperty("user.dir") + File.separator +  "textfiles/"+"multiples" +"/encoded.txt"; ;

    String decodedPath = System.getProperty("user.dir") + File.separator + "textfiles/"+"multiples"  +"/decoded.txt";

    public Files (List<String> p_allFiles){
        allFiles = p_allFiles;
    }

    public static String fileToString(String filepath) {
        try {
            File file = new File(System.getProperty("user.dir") + filepath);
            Scanner scanner = new Scanner(file);
            StringBuilder s = new StringBuilder();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                s.append(line);
            }
            scanner.close();
            return s.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return"";
        }
    }

    public void decodeMultiplesFiles() throws IOException {
        System.out.println("1-html to txt ");
        String currentDir = System.getProperty("user.dir");
        String outputFilePath = currentDir + "/textfiles/" + "multiples" + "/page.txt";

        File oldfile = new File(outputFilePath);
        oldfile.delete();

        String extractedText;
        FileWriter fileWriter = new FileWriter(outputFilePath, true);

        System.out.println(allFiles.size());

        for(int i=0; i < allFiles.size();i++) {
            System.out.println(allFiles.get(i));
            String inputFilePath = currentDir + "/textfiles/" + allFiles.get(i) + "/page.html";
            File inputFile = new File(inputFilePath);
            Document doc = Jsoup.parse(inputFile, "UTF-8");
            extractedText = doc.body().text();
            //FileWriter fileWriter = new FileWriter(outputFilePath);
            fileWriter.write("");
            fileWriter.append(extractedText + "\n");
            //fileWriter.write(extractedText);


        }
        fileWriter.close();
        System.out.println("Text has been successfully extracted and saved to: " + outputFilePath);
        System.out.println("\n");
    }


    public String readTextFile(String filepath){
        System.out.println("2-reading txt file : ");
        String filePath = "/textfiles/"+"multiples" + "/page.txt";
        String phrase = fileToString( filePath) ;
        System.out.println("text is " + phrase);
        System.out.println("\n");
        return phrase;
    }


    public Liste createArbreFiles(String phrase){
        System.out.println("3-creating Arbrefinale ");
        huff.setPhrase(phrase);
        huff.createOccuranceMap();
        System.out.println(huff.getPhrase());
        System.out.println(huff.getListInitial());
        Liste listInitial = huff.getListInitial();
        Liste ArbrefinaleList = huff.createHuffmanArbre(listInitial);
        ArbrefinaleList.printList();
        System.out.println("\n");
        return ArbrefinaleList ;
    }

    public void encode(){
        System.out.println("4-encoding ");
        huff.encodeFromFileToFile(filePathAbsolue, encodedPath);
        System.out.println("Encoded phrase saved to " + encodedPath);
        System.out.println("\n");
    }

    public void decode(){
        System.out.println("5-decoding ");
        huff.decodeFromFile(encodedPath, decodedPath);
        System.out.println("Decoded phrase saved to " + decodedPath);
        System.out.println("\n");
    }

}
