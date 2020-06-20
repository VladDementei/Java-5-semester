package sample;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class Dialogs {

    public static void showErrorDialog(String contentText){
        Alert alert = new Alert(Alert.AlertType.ERROR, contentText, ButtonType.CLOSE);
        alert.showAndWait();
    }

    public static void showConfirmDialog(String contentText){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, contentText, ButtonType.CLOSE);
        alert.showAndWait();
    }
}
