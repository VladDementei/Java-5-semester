package sample;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class FileUtils {

    public static String readTextFile(File file) throws IOException{
        char[] buf = new char[1024];
        StringBuffer answer = new StringBuffer();
        Reader reader = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8);
        //Reader reader = new FileReader(file);
        int flag;
        while ((flag = reader.read(buf))>= 0){
            answer.append(String.valueOf(buf, 0, flag));
        }
        reader.close();
        return answer.toString();
    }

    public static void writeTextFile(File file, String text) throws IOException{
        Writer writer = new FileWriter(file);
        writer.write(text);
        writer.close();
    }

    /*public static void writeByteFile(File file, String text) throws IOException{
        OutputStream writer = new FileOutputStream(file);
        writer.write(text.getBytes());
        writer.close();
    }*/
}