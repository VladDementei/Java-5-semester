package sample;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class Dialogs {

    public static void showErrorDialog(String contentText){
        Alert alert = new Alert(Alert.AlertType.ERROR, contentText, ButtonType.CLOSE);
        alert.setHeaderText(LanguageBundle.getString("error"));
        alert.setTitle(LanguageBundle.getString("error"));
        alert.showAndWait();
    }
}