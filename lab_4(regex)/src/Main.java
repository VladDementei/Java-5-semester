import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    private static String text;

    public static void main(String[] args) throws IOException {
        StringBuffer sb = new StringBuffer();
        try (BufferedReader reader = new BufferedReader(new FileReader(new File("code.pas")))) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                sb.append('\n');
            }
        }
        text = sb.toString();

        text = PascalUtils.removeOneLineComments(text);
        text = PascalUtils.removeBlockComments(text);
        text = PascalUtils.removeMultiLinesComments(text);
    }
}
