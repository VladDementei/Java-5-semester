package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.util.Optional;

public class AddStudentDialog extends Dialog<Student> {

    public AddStudentDialog() {
        this.setTitle("Add student");
        this.setHeaderText("Fill student information");

        ButtonType addButtonType = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        this.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 50, 10, 10));

        TextField surname = new TextField();
        surname.setPromptText("Surname");
        TextField group = new TextField();
        group.setPromptText("Group");
        ObservableList<Integer> marks = FXCollections.observableArrayList(1,2,3,4,5,6,7,8,9,10);
        ComboBox<Integer> markMath = new ComboBox<>(marks);
        ComboBox<Integer> markEP = new ComboBox<>(marks);

        grid.add(new Label("Surname:"), 0, 0);
        grid.add(surname, 1, 0);
        grid.add(new Label("Group:"), 0, 1);
        grid.add(group, 1, 1);
        grid.add(new Label("Math mark:"), 0, 2);
        grid.add(markMath, 1, 2);
        grid.add(new Label("Math educational practise:"), 0, 3);
        grid.add(markEP, 1, 3);

        Node addButton = this.getDialogPane().lookupButton(addButtonType);
        addButton.setDisable(true);

        surname.textProperty().addListener((observable, oldValue, newValue) -> {
            addButton.setDisable(newValue.trim().isEmpty() || group.getText().trim().isEmpty());
        });

        group.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                group.setText(newValue.replaceAll("[^\\d]", ""));
            }
            addButton.setDisable(group.getText().trim().isEmpty() || surname.getText().trim().isEmpty());
        });

        this.getDialogPane().setContent(grid);
        surname.requestFocus();
        markMath.setValue(1);
        markEP.setValue(1);

        this.setResultConverter(dialogButton -> {
            if (dialogButton == addButtonType) {
                return new Student(surname.getText(), Integer.parseInt(group.getText()), markMath.getValue(), markEP.getValue());
            }
            return null;
        });
    }

    public Student startDialog(){
        Optional<Student> result = this.showAndWait();
        if(result.isPresent()) {
            return result.get();
        }
        return null;
    }
}