package sample;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

public class StudentCellFactory implements Callback<ListView<BasicInfo>, ListCell<BasicInfo>> {

    @Override
    public ListCell<BasicInfo> call(ListView<BasicInfo> param) {
        return new StudentListViewCell();
    }
}
