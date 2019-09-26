package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;

public class Controller implements Initializable {

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
        listView.setOnMouseClicked(event -> {
            if(event.getClickCount() == 2) {
                StringTokenizer stringTokenizer = new StringTokenizer(listView.getSelectionModel().getSelectedItem(), "(,) ");
                String func = stringTokenizer.nextToken();
                List<Class<?>> classList = new ArrayList<>();
                while (stringTokenizer.hasMoreTokens()){
                    String token = stringTokenizer.nextToken();
                    if(Type.isPrimitiveType(token)) {
                        classList.add(Type.getPrimitiveClass(token));
                    }else{
                        try {
                            classList.add(Class.forName(token));
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                }
                try {
                    Class myCl = Class.forName(func.substring(0, func.lastIndexOf(".")));
                    CalculateFunctionDialog dialog = new CalculateFunctionDialog(myCl.getMethod(func.substring(func.lastIndexOf(".")+1), classList.toArray(new Class[classList.size()])));
                    dialog.startDialog();

                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void openFile(ActionEvent event) {
    }

}