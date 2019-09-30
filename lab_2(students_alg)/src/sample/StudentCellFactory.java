package sample;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

public class StudentCellFactory implements Callback<ListView<GroupInfo>, ListCell<GroupInfo>> {

    @Override
    public ListCell<GroupInfo> call(ListView<GroupInfo> param) {
        return new StudentListViewCell();
    }
}
