package sample;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;

public class Dialogs {

    public static String showPasswordDialog(String headerText, String defaultValue){
        TextInputDialog passwordDialog = new TextInputDialog(defaultValue);
        passwordDialog.setHeaderText(headerText);
        do{
            passwordDialog.showAndWait();
            if(passwordDialog.getResult() != null && passwordDialog.getResult().trim().equals("")){
                showErrorDialog("Password field can't be empty");
            }
        } while (passwordDialog.getResult() != null && passwordDialog.getResult().trim().equals(""));
        return passwordDialog.getResult();
    }

    public static void showErrorDialog(String contentText){
        Alert alert = new Alert(Alert.AlertType.ERROR, contentText, ButtonType.CLOSE);
        alert.showAndWait();
    }

    public static ButtonType showConfirmationDialog(String question){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, question, ButtonType.YES, ButtonType.NO, ButtonType.CLOSE);
        alert.showAndWait();
        return alert.getResult();
    }
}