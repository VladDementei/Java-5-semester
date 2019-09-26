package sample;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;

public class CalculateFunctionDialog extends Dialog<String> {

    public CalculateFunctionDialog(Method method) {
        super();
        this.setTitle("Function executing");
        this.setHeaderText("Fill params text fields and press execute ");

        //ButtonType executeButtonType = new ButtonType("Execute");
        //this.getDialogPane().getButtonTypes().addAll(executeButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 50, 10, 10));

        TextField[] textFieldsParams = new TextField[method.getParameterCount()];

        for(int i = 0; i < method.getParameterCount(); i++) {
            grid.add(new Label(method.getParameters()[i].toString()), 0, i);
            textFieldsParams[i] = new TextField();
            grid.add(textFieldsParams[i], 1, i);
        }

        grid.add(new Label("Result"), 0, method.getParameterCount());
        TextField answer = new TextField();
        grid.add(answer, 1, method.getParameterCount());


        /*Node addButton = this.getDialogPane().lookupButton(addButtonType);
        addButton.setDisable(true);*/

        /*surname.textProperty().addListener((observable, oldValue, newValue) -> {
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
        markEP.setValue(1);*/

        Button execute = new Button("Execute");
        execute.setOnMouseClicked(event -> {
            System.out.println("Click");
            try {
                Object[] funcParams = new Object[method.getParameterCount()];
                for(int i = 0; i < method.getParameterCount(); i++){
                    try {
                        if (method.getParameterTypes()[i].isPrimitive()) {
                            funcParams[i] = Type.getWrapperClass(method.getParameterTypes()[i].getName())
                                    .getMethod("valueOf", String.class)
                                    .invoke(null, textFieldsParams[i].getText());
                        } else {
                            funcParams[i] = method.getParameterTypes()[i].getConstructor(String.class).newInstance(textFieldsParams[i].getText());
                        }
                    } catch (NoSuchMethodException e) {
                        Dialogs.showErrorDialog("Not found constructor from String to " + method.getParameterTypes()[i]);
                    } catch (InstantiationException e) { e.printStackTrace(); }//impossible
                }
                answer.setText(method.invoke(null, funcParams).toString());
            } catch (IllegalAccessException e) {//try to get/set private fields, impossible
                e.printStackTrace();
            } catch (InvocationTargetException e) {//wrong params
                Dialogs.showErrorDialog(e.getCause().toString());
            }catch (IllegalArgumentException e){}
        });
        grid.add(execute, 1, method.getParameterCount()+1);


        this.getDialogPane().setContent(grid);
        this.getDialogPane().getScene().getWindow().setOnCloseRequest(event -> {});



        /*this.setResultConverter(dialogButton -> {
            if (dialogButton == addButtonType) {
                return new Student(surname.getText(), Integer.parseInt(group.getText()), markMath.getValue(), markEP.getValue());
            }
            return null;
        });*/
    }

    public void startDialog(){
        this.show();
    }
}
