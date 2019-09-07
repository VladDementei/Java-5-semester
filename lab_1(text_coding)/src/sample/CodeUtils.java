package sample;

public class CodeUtils {

    public static String encodeText(String text, String password){
        char[] passwordArray = password.toCharArray();
        char[] textArray = text.toCharArray();
        for (int i = 0; i < textArray.length; i++) {
            textArray[i] = (char) (textArray[i] ^ passwordArray[i%passwordArray.length]);
        }
        return new String(textArray, 0, textArray.length);
    }
}
