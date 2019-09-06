package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Controller {

    @FXML
    private TextArea inputArea;
    @FXML
    private MenuItem menuOpenFile;

    private File sourceFile;
    private String lastSavedText;

    public File getTextFileToRead(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory((sourceFile == null) ? new File("\\IDEA\\3 курс") : sourceFile.getParentFile());
        List<String> extensions = new ArrayList<>();
        extensions.add("*.txt");
        extensions.add("*.cf");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Files",extensions));
        return fileChooser.showOpenDialog(menuOpenFile.getParentPopup().getScene().getWindow());
    }

    public void openFile(ActionEvent event) {

        //Font f = Font.loadFont(getClass().getResourceAsStream("/resources/unifont-12.1.03.ttf"), 16);
        //inputArea.setFont(f);
        this.askForSaveChangedText("Would you like first to save current text before open new file");
        sourceFile = getTextFileToRead();
        if (sourceFile != null) {
            try{
                String text = FileUtils.readTextFile(sourceFile);
                if(sourceFile.getName().substring(sourceFile.getName().lastIndexOf(".")).equals(".cf")){
                    String password = CodeUtils.showPasswordDialog("Enter password fo file", "");
                    if(password != null){
                        lastSavedText = CodeUtils.encodeText(text, password);
                    }else {
                        CodeUtils.showErrorDialog("File can't be shown without password");
                    }
                }else {
                    lastSavedText = text;
                }
                inputArea.setText(lastSavedText);
            } catch (IOException ex) {
                CodeUtils.showErrorDialog("File problem: " + ex.getMessage());
            }
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public File getTextFileToWrite(String fileType, String extension){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory((sourceFile == null) ? new File("\\IDEA\\3 курс") : sourceFile.getParentFile());
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(fileType, "*." + extension));
        return  fileChooser.showSaveDialog(menuOpenFile.getParentPopup().getScene().getWindow());
    }

    public void saveTextFile(ActionEvent event){
        File file = getTextFileToWrite("Text Files", "txt");
        if (file != null) {
            try {
                FileUtils.writeTextFile(file, inputArea.getText());
                lastSavedText = inputArea.getText();
            } catch (IOException ex) {
                CodeUtils.showErrorDialog("File problem: " + ex.getMessage());
            }
        }
    }

    public void encodeText(ActionEvent event){
        String password = CodeUtils.showPasswordDialog("Enter password to encode text", "password");
        if(password != null) {
            File file = getTextFileToWrite("Encoded files", "cf");
            if (file != null) {
                try {
                    FileUtils.writeTextFile(file, CodeUtils.encodeText(inputArea.getText(), password));
                    lastSavedText = inputArea.getText();
                } catch (IOException ex) {
                    CodeUtils.showErrorDialog("File problem: " + ex.getMessage());
                }
            }
        }
    }

    public void askForSaveChangedText(String question){
        if(isTextChanged()) {
            ButtonType answer = CodeUtils.showConfirmationDialog(question);
            if (answer != null && answer.getButtonData() == ButtonBar.ButtonData.YES) {
                saveTextFile(null);
            }
        }
    }

    private boolean isTextChanged(){
        return (!inputArea.getText().equals("") || (lastSavedText != null) ) && ((lastSavedText == null) || !lastSavedText.equals(inputArea.getText()));
    }
}
