package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Controller {

    @FXML
    private TextArea inputArea;

    @FXML
    private MenuItem menuOpenFile;

    public void openFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("D:\\IDEA"));
        File file = fileChooser.showOpenDialog(menuOpenFile.getParentPopup().getScene().getWindow());
        if (file != null) {
            try {
                Scanner sc = new Scanner(file);
                inputArea.clear();
                while (sc.hasNextLine()) {
                    inputArea.appendText(sc.nextLine());
                    inputArea.appendText("\n");
                }
            } catch (IOException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("File problems");
                alert.showAndWait();
            }
        }
    }
}
