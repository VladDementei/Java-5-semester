package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.io.InvalidClassException;
import java.net.URL;
import java.util.*;

public class Controller implements Initializable {

    @FXML
    private ListView<Student> listView;
    @FXML
    private MenuItem menuOpenData;

    private ObservableList<Student> studentObservableList;
    File lastOpenedFile;

    public Controller()  {
        studentObservableList = FXCollections.observableArrayList();
//        studentObservableList.addAll(
//                new Student("John", 1, 6, 10),
//                new Student("Sam", 1, 5, 3),
//                new Student("David", 1, 7, 8),
//                new Student("Alex", 1, 4, 6),
//                new Student("Sue", 1, 8, 9)
//        );
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listView.setItems(studentObservableList);
        listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        listView.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.DELETE){
                System.out.println(Arrays.toString(listView.getSelectionModel().getSelectedIndices().toArray()));
                studentObservableList.removeAll(listView.getSelectionModel().getSelectedItems());
            }
        });
    }

    public File getSerFileToRead(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory((lastOpenedFile == null) ? new File("\\") : lastOpenedFile.getParentFile());
        List<String> extensions = new ArrayList<>();
        extensions.add("*.ser");
        extensions.add("*.dat");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Files",extensions));
        return fileChooser.showOpenDialog(menuOpenData.getParentPopup().getScene().getWindow());
    }

    public File getSerFileToWrite(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory((lastOpenedFile == null) ? new File("\\") : lastOpenedFile.getParentFile());
        List<String> extensions = new ArrayList<>();
        extensions.add("*.ser");
        extensions.add("*.dat");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Files", extensions));
        return  fileChooser.showSaveDialog(menuOpenData.getParentPopup().getScene().getWindow());
    }

    public void openFile(ActionEvent event){
        File file = getSerFileToRead();
        if(file != null){
            try {
                studentObservableList.setAll((ArrayList<Student>)FileUtils.deserialize(file));
                lastOpenedFile = file;

            } catch (InvalidClassException | ClassNotFoundException | ClassCastException e){
                System.out.println(e.getMessage());
            }
            catch (IOException e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            }
        }
    }

    public void saveFile(ActionEvent event){
        File file = getSerFileToWrite();
        if(file != null){
            try {
                FileUtils.serialize(new ArrayList<>(studentObservableList.subList(0, studentObservableList.size())), file);
                lastOpenedFile = file;
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            }
        }
    }

    public void addStudent(ActionEvent event){
        AddStudentDialog dialog = new AddStudentDialog();
        Student newStudent = dialog.startDialog();
        if(newStudent != null){
            studentObservableList.add(newStudent);
        }
    }
}