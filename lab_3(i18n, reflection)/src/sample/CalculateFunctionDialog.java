package sample;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class CalculateFunctionDialog extends Dialog<String> {

    public CalculateFunctionDialog(Method method) {
        this.setTitle(LanguageBundle.getString("calculateFunctionDialogTitle"));
        this.setHeaderText(LanguageBundle.getString("calculateFunctionDialogHeader"));

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

        grid.add(new Label(LanguageBundle.getString("result")), 0, method.getParameterCount());
        TextField answer = new TextField();
        grid.add(answer, 1, method.getParameterCount());

        Button execute = new Button(LanguageBundle.getString("execute"));
        execute.setOnMouseClicked(event -> {
            int i = 0;
            try {
                Object[] funcParams = new Object[method.getParameterCount()];
                for(i = 0; i < method.getParameterCount(); i++){
                    if (method.getParameterTypes()[i].isPrimitive()) {
                        funcParams[i] = PrimitiveTypeHelper.getWrapperClass(method.getParameterTypes()[i].getName())
                                .getMethod("valueOf", String.class)
                                .invoke(null, textFieldsParams[i].getText());
                    } else {
                         funcParams[i] = method.getParameterTypes()[i].getConstructor(String.class).newInstance(textFieldsParams[i].getText());
                    }
                }
                answer.setText(method.invoke(null, funcParams).toString());
            } catch (IllegalAccessException | InstantiationException e) {//try to get/set private fields | forbidden to create an instance, impossible
                e.printStackTrace();
            } catch (InvocationTargetException e) {//wrong params
                Dialogs.showErrorDialog(e.getCause().toString());
            }catch (NoSuchMethodException e) {
                Dialogs.showErrorDialog(LanguageBundle.getString("notFountConstructorFromStringTo") + method.getParameterTypes()[i]);
            }
        });
        grid.add(execute, 1, method.getParameterCount()+1);

        this.getDialogPane().setContent(grid);
        this.getDialogPane().getScene().getWindow().setOnCloseRequest(event -> {});
    }
}