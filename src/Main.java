import data.structure.Files;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * The type Main.
 */
public class Main {

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     * @throws IOException the io exception
     */
    public static void main(String[] args) throws IOException {

        List<String> allFilesPage = new ArrayList<>();
        allFilesPage.add("releve1");
        allFilesPage.add("releve2");
        allFilesPage.add("releve3");



        Files allFiles = new Files(allFilesPage);

        allFiles.decodeMultiplesFiles();
        String filesText = allFiles.readTextFile("page.txt");

        allFiles.createArbreFiles(filesText);

        allFiles.encode();
        allFiles.decode();

    }
}