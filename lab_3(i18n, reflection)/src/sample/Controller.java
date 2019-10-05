package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.*;

public class Controller implements Initializable {

    @FXML
    private ListView<String> listView;
    @FXML
    private ImageView BelFlag;
    @FXML
    private ImageView UKFlag;

    private Map<ImageView, Locale> langMap;
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
        langMap = new HashMap<>();
        langMap.put(UKFlag, Locale.UK);
        langMap.put(BelFlag, new Locale("be", "BY"));
        setFlagParentsTransparent();
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

    private void setFlagParentsTransparent(){
        for(ImageView elem: langMap.keySet()){
            elem.getParent().setStyle("-fx-padding: 5;");
        }
    }

    private void initFunctionDialog(){
        String signature = listView.getSelectionModel().getSelectedItem();
        try {
            CalculateFunctionDialog dialog = new CalculateFunctionDialog(
                    Class.forName(ParseUtils.getClassName(signature)).getMethod(ParseUtils.getFunctionName(signature),
                            ParseUtils.getParamsTypes(signature).toArray(new Class[0])));
            dialog.show();
        } catch (ClassNotFoundException e) {
            Dialogs.showErrorDialog(LanguageBundle.getString("classNotFound"));
        } catch (NoSuchMethodException e) {
            Dialogs.showErrorDialog(LanguageBundle.getString("methodNotFound"));
        }
    }

    public void flagClicked(MouseEvent mouseEvent) {
        setFlagParentsTransparent();
        ((ImageView)mouseEvent.getSource()).getParent().setStyle("-fx-padding: 5; -fx-background-color: lightskyblue;");
        LanguageBundle.setBundle(langMap.get((ImageView)mouseEvent.getSource()));
        ((Stage)((ImageView) mouseEvent.getSource()).getScene().getWindow()).setTitle(LanguageBundle.getString("windowTitle"));

    }
}