package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;

import java.io.*;

public class Controller {

    @FXML
    private TextArea inputArea;
    @FXML
    private MenuItem menuOpenFile;

    private File file;

    public void openFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("\\IDEA\\3 курс"));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        file = fileChooser.showOpenDialog(menuOpenFile.getParentPopup().getScene().getWindow());
        if (file != null) {
            inputArea.clear();
            char[] buf = new char[1024];
            try(Reader reader = new InputStreamReader(new FileInputStream(file), "Cp1251")){
                int flag;
                while ((flag = reader.read(buf))>= 0){
                    inputArea.appendText(String.valueOf(buf, 0, flag));
                }
            } catch (IOException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("File problems");
                alert.showAndWait();
            }
        }
    }

    public void saveFile(ActionEvent event){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("\\IDEA\\3 курс"));
        //fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        file = fileChooser.showSaveDialog(menuOpenFile.getParentPopup().getScene().getWindow());
        if (file != null) {
            try (Writer writer = new OutputStreamWriter(new FileOutputStream(file), "Cp1251")){
                writer.write(inputArea.getText());
            } catch (IOException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("File problems");
                alert.showAndWait();
            }
        }
    }
}
