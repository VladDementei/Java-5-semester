package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.FileChooser;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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
    }

    public void openFile(ActionEvent event){

    }

    public void saveFile(ActionEvent event){

    }

    public void encodeTextAndSave(ActionEvent event){

    }


}