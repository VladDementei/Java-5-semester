package sample;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyCode;
import javafx.util.Pair;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private ListView<String> listView;
    @FXML
    private ObservableList<String> fileNames;
    private Socket socket;
    private ObjectInputStream is;
    private ObjectOutputStream os;


    public Controller()  {
        fileNames = FXCollections.observableArrayList();
        makeConnection();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listView.setItems(fileNames);
        listView.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.ENTER) {
                String send = listView.getSelectionModel().getSelectedItem();
                readWriteDialog("cd", send);
            }
        });
    }

    private void makeConnection(){
        try {
            socket = new Socket("localhost",9999);
            is = new ObjectInputStream(socket.getInputStream());
            os = new ObjectOutputStream(socket.getOutputStream());
            readWriteDialog("ls", "");
        } catch (IOException e) {
            System.out.println("Connection error");
        }
    }

    private void readWriteDialog(String command, String fileName){
        Platform.runLater(() -> {
            try {
                os.writeObject(new Pair<>(command, fileName));
                os.flush();
            } catch (IOException e) {
                System.out.println("Server error");
            }
        });
        Platform.runLater(() -> {
            try {
                List<String> fileNames = (ArrayList<String>)is.readObject();
                Controller.this.fileNames.setAll(fileNames);
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Server error");
            }
        });
    }

    public void buttonReconnect(ActionEvent event) {
        makeConnection();
    }
}
