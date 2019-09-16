package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;

import java.io.IOException;

public class StudentListViewCell extends ListCell<BasicInfo> {

    @FXML
    private Label title;

    @FXML
    private Label mark;

    public StudentListViewCell() {
        loadFXML();
    }

    private void loadFXML() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("list_cell.fxml"));
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void updateItem(BasicInfo student, boolean empty) {
        super.updateItem(student, empty);

        if (empty) {
            setText(null);
            setContentDisplay(ContentDisplay.TEXT_ONLY);
        } else {
            if(student instanceof Student){
                title.setText(((Student)student).getSurname());
            }else {
                title.setText("Student of " + student.getGroup() + " group");
            }
            mark.setText(String.valueOf(student.getAverageMark()));
            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        }
    }
}