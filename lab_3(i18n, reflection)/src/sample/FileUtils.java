package sample;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {

    public static List<String> readFile(File file) throws IOException{
        BufferedReader reader = new BufferedReader(new FileReader(file));
        List<String> fileLines = new ArrayList<>();
        String line;
        while ((line = reader.readLine()) != null) {
            fileLines.add(line);
        }
        return fileLines;
    }

    public static void writeTextFile(File file, String text) throws IOException{
        Writer writer = new FileWriter(file);
        writer.write(text);
        writer.close();
    }
}