package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private MenuItem menuOpenData;
    @FXML
    private ListView<String> listView;
    private ObservableList<String> functionsList;

    public Controller() throws IOException {
        functionsList = FXCollections.observableArrayList();
        functionsList.addAll(FileUtils.readFile(new File("src\\calc.properties")));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listView.setItems(functionsList);
    }

    public void openFile(ActionEvent event) {
    }

}