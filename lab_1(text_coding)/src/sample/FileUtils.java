package sample;

import java.io.*;

public class FileUtils {
    public static String readFile(File file) throws IOException{
        char[] buf = new char[1024];
        StringBuffer answer = new StringBuffer();
        Reader reader = new InputStreamReader(new FileInputStream(file), "Cp1251");
        int flag;
        while ((flag = reader.read(buf))>= 0){
            answer.append(String.valueOf(buf, 0, flag));
        }
        reader.close();
        return answer.toString();
    }

    public static void writeFile(File file, String text) throws IOException{
        Writer writer = new OutputStreamWriter(new FileOutputStream(file), "Cp1251");
        writer.write(text);
        writer.close();
    }
}
