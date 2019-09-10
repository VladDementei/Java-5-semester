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

    private File lastOpenedFile;
    private String lastSavedText;

    public File getTextFileToRead(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory((lastOpenedFile == null) ? new File("\\") : lastOpenedFile.getParentFile());
        List<String> extensions = new ArrayList<>();
        extensions.add("*.txt");
        extensions.add("*.cf");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Files",extensions));
        return fileChooser.showOpenDialog(menuOpenFile.getParentPopup().getScene().getWindow());
    }

    public void openFile(ActionEvent event) {
        //Font f = Font.loadFont(getClass().getResourceAsStream("/resources/unifont-12.1.03.ttf"), 16);
        //inputArea.setFont(f);
        ButtonType answerTextChangedDialog = this.askForSaveChangedText("Would you like first to save current text before open new file");
        if(answerTextChangedDialog != null && answerTextChangedDialog == ButtonType.CLOSE) {
            return;
        }
        File file = getTextFileToRead();
        if (file != null) {
            try{
                String fileText = FileUtils.readTextFile(file);
                if(file.getName().substring(file.getName().lastIndexOf(".")).equals(".cf")){
                    String password = Dialogs.showPasswordDialog("Enter password fo file", "");
                    if(password != null){
                        lastSavedText = CodeUtils.encodeText(fileText, password);
                    }else {
                        Dialogs.showErrorDialog("File can't be shown without password");
                    }
                }else {
                    lastSavedText = fileText;
                }
                inputArea.setText(lastSavedText);
                lastOpenedFile = file;
            } catch (IOException ex) {
                Dialogs.showErrorDialog("File problem: " + ex.getMessage());
            }
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public File getTextFileToWrite(String fileType, String extension){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory((lastOpenedFile == null) ? new File("\\") : lastOpenedFile.getParentFile());
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(fileType, "*." + extension));
        return  fileChooser.showSaveDialog(menuOpenFile.getParentPopup().getScene().getWindow());
    }

    public void saveTextFile(ActionEvent event){
        File file = getTextFileToWrite("Text Files", "txt");
        if (file != null) {
            try {
                FileUtils.writeTextFile(file, inputArea.getText());
                lastSavedText = inputArea.getText();
                lastOpenedFile = file;
            } catch (IOException ex) {
                Dialogs.showErrorDialog("File problem: " + ex.getMessage());
            }
        }
    }

    public void encodeTextAndSave(ActionEvent event){
        String password = Dialogs.showPasswordDialog("Enter password to encode text", "password");
        if(password != null) {
            File file = getTextFileToWrite("Encoded files", "cf");
            if (file != null) {
                try {
                    FileUtils.writeTextFile(file, CodeUtils.encodeText(inputArea.getText(), password));
                    lastSavedText = inputArea.getText();
                    lastOpenedFile = file;
                } catch (IOException ex) {
                    Dialogs.showErrorDialog("File problem: " + ex.getMessage());
                }
            }
        }
    }

    public ButtonType askForSaveChangedText(String question){
        if(isTextChanged()) {
            ButtonType answer = Dialogs.showConfirmationDialog(question);
            if (answer != null && answer == ButtonType.YES) {
                saveTextFile(null);
            }
            return answer;
        }
        return null;
    }

    private boolean isTextChanged(){
        return (!inputArea.getText().equals("") || (lastSavedText != null) )
                && ((lastSavedText == null) || !lastSavedText.equals(inputArea.getText()));
    }
}