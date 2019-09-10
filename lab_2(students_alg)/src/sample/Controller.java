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

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private ListView<Student> listView;

    private ObservableList<Student> studentObservableList;

    public Controller()  {
        studentObservableList = FXCollections.observableArrayList();
        studentObservableList.addAll(
                new Student("John", 1, 6, 10),
                new Student("Sam", 1, 5, 3),
                new Student("David", 1, 7, 8),
                new Student("Alex", 1, 4, 6),
                new Student("Sue", 1, 8, 9)
        );
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listView.setItems(studentObservableList);
        listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        listView.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode() == KeyCode.DELETE){
                    System.out.println(Arrays.toString(listView.getSelectionModel().getSelectedIndices().toArray()));
                }
            }
        });
    }

    public void openFile(ActionEvent event){

    }

    public void saveFile(ActionEvent event){

    }

    public void addStudent(ActionEvent event){
        AddStudentDialog dialog = new AddStudentDialog();
        Student newStudent = dialog.startDialog();
        if(newStudent != null){
            studentObservableList.add(newStudent);
        }
    }
}