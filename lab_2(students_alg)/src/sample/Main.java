package sample;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("Students algorithms");
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        /*primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                ButtonType buttonType =((Controller) loader.getController()).
                        askForSaveChangedText("Would you like to save file first?");
                if(buttonType != null && buttonType == ButtonType.CLOSE) {
                    event.consume();
                }
            }
        });*/
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}