package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;

import javax.print.attribute.standard.DialogTypeSelection;
import java.io.*;

public class Controller {

    @FXML
    private TextArea inputArea;
    @FXML
    private MenuItem menuOpenFile;

    private File sourceFile;
    private String lastSavedText;

    public void openFile(ActionEvent event) {

        //Font f = Font.loadFont(getClass().getResourceAsStream("/resources/unifont-12.1.03.ttf"), 16);
        //inputArea.setFont(f);
        sourceFile = getTextFileToRead();
        if (sourceFile != null) {
            //inputArea.clear();
            try{
                lastSavedText = FileUtils.readFile(sourceFile);
                inputArea.setText(lastSavedText);
            } catch (IOException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("File problems");
                alert.showAndWait();
            }
        }
    }

    public File getTextFileToRead(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("\\IDEA\\3 курс"));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        return fileChooser.showOpenDialog(menuOpenFile.getParentPopup().getScene().getWindow());
    }

    public void saveFile(ActionEvent event){
        File file = getTextFileToWrite();
        if (file != null) {
            try {
                FileUtils.writeFile(file, inputArea.getText());
            } catch (IOException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("File problems");
                alert.showAndWait();
            }
        }
    }

    public File getTextFileToWrite(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("\\IDEA\\3 курс"));
        //fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        return  fileChooser.showSaveDialog(menuOpenFile.getParentPopup().getScene().getWindow());
    }

    public void encodeText(ActionEvent event){

        TextInputDialog textInputDialog = new TextInputDialog("password");
        textInputDialog.setHeaderText("Enter password to encode text");
        do{
            textInputDialog.showAndWait();
            //textInputDialog.getEditor().clear();
            if(textInputDialog.getResult() != null && textInputDialog.getResult().trim().equals("")){
                Alert alert= new Alert(Alert.AlertType.ERROR, "Password field can't be empty", ButtonType.CLOSE);
                alert.showAndWait();
            }
            System.out.println(textInputDialog.getResult());
        } while (textInputDialog.getResult() != null && textInputDialog.getResult().trim().equals(""));
        if(textInputDialog.getResult() != null) {

            char[] password = textInputDialog.getResult().toCharArray();
            char[] textArray = inputArea.getText().toCharArray();
            for (int i = 0; i < textArray.length; i++) {
                textArray[i] = (char) (textArray[i] ^ password[0]);
            }

            String str = new String(textArray, 0, textArray.length);
            File file = getTextFileToWrite();
            if (file != null) {
                try {
                    FileUtils.writeFile(file, str);
                } catch (IOException ex) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText("File problems");
                    alert.showAndWait();
                }
            }
            //inputArea.setText(str);
        }


    }
}
