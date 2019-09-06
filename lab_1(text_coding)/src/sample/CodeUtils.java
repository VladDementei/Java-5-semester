package sample;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;

public class CodeUtils {

    public static String encodeText(String text, String password){
        char[] passwordArray = password.toCharArray();
        char[] textArray = text.toCharArray();
        for (int i = 0; i < textArray.length; i++) {
            textArray[i] = (char) (textArray[i] ^ passwordArray[0]);
        }
        return new String(textArray, 0, textArray.length);
    }

    public static String showPasswordDialog(String headerText, String defaultValue){
        TextInputDialog textInputDialog = new TextInputDialog(defaultValue);
        textInputDialog.setHeaderText(headerText);
        do{
            textInputDialog.showAndWait();
            if(textInputDialog.getResult() != null && textInputDialog.getResult().trim().equals("")){
                showErrorDialog("Password field can't be empty");
            }
            System.out.println(textInputDialog.getResult());
        } while (textInputDialog.getResult() != null && textInputDialog.getResult().trim().equals(""));
        return textInputDialog.getResult();
    }

    public static void showErrorDialog(String contentText){
        Alert alert = new Alert(Alert.AlertType.ERROR, contentText, ButtonType.CLOSE);
        alert.showAndWait();
    }

    public static ButtonType showConfirmationDialog(String question){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, question, ButtonType.YES, ButtonType.NO);
        alert.showAndWait();
        return alert.getResult();
    }
}
