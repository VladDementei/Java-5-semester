package sample;

import java.util.Locale;
import java.util.ResourceBundle;

public class LanguageBundle {
    private static ResourceBundle lastSelectedBundle;
    static {
        lastSelectedBundle = ResourceBundle.getBundle("lang", Locale.ROOT);
    }
    private LanguageBundle(){}

    public static String getString(String key){
        return lastSelectedBundle.getString(key);
    }

    public static void setBundle(Locale locale){
        lastSelectedBundle = ResourceBundle.getBundle("lang", locale);
    }
}
