package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class Controller implements Initializable {

    @FXML
    private ListView<String> listView;
    private ObservableList<String> functionsList;

    public Controller() throws IOException {
        functionsList = FXCollections.observableArrayList();
        try(BufferedReader reader = new BufferedReader(new FileReader(new File("src\\calc.properties")))){
            String line;
            while ((line = reader.readLine()) != null) {
                functionsList.add(line);
            }
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listView.setItems(functionsList);
        listView.setOnMouseClicked(event -> {
            if(event.getClickCount() == 2) {
                initFunctionDialog();
            }
        });
        listView.setOnKeyPressed(e-> {
            if(e.getCode() == KeyCode.ENTER){
                initFunctionDialog();
            }
        });
    }

    private void initFunctionDialog(){
        String signature = listView.getSelectionModel().getSelectedItem();
        try {
            CalculateFunctionDialog dialog = new CalculateFunctionDialog(
                    Class.forName(ParseUtils.getClassName(signature)).getMethod(ParseUtils.getFunctionName(signature),
                            ParseUtils.getParamsTypes(signature).toArray(new Class[0])));
            dialog.show();
        } catch (ClassNotFoundException e) {
            Dialogs.showErrorDialog("Class not found");
        } catch (NoSuchMethodException e) {
            Dialogs.showErrorDialog("Method not found");
        }
    }

    public void openFile(ActionEvent event) {}
}