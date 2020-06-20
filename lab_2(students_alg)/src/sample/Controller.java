package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.io.InvalidClassException;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class Controller implements Initializable {

    @FXML
    private ListView<GroupInfo> listView;
    @FXML
    private MenuItem menuOpenData;

    private ObservableList<GroupInfo> studentObservableList;
    private File lastOpenedFile;

    public Controller()  {
        studentObservableList = FXCollections.observableArrayList();
//        studentObservableList.addAll(
//                new GroupInfo(1),
//                new Student("John", 1, 6, 10),
//                new Student("Sam", 1, 5, 3),
//                new Student("David", 1, 7, 8),
//                new GroupInfo(2),
//                new Student("Alex", 2, 4, 6),
//                new Student("Sue", 2, 8, 9)
//        );
//        countAllGroupsMarks();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listView.setItems(studentObservableList);
        listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        listView.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.DELETE){
                //System.out.println(Arrays.toString(listView.getSelectionModel().getSelectedIndices().toArray()));
                List<GroupInfo> toDelete  = listView.getSelectionModel().getSelectedItems().stream().sorted((o1, o2) -> {
                    if(o2 instanceof Student){
                            return 1;
                    }
                    return -1;
                }).collect(Collectors.toList());

                for(GroupInfo elem: toDelete){
                    if(elem instanceof Student){
                        studentObservableList.remove(elem);
                    }else {
                       studentObservableList.setAll(studentObservableList.stream()
                                                    .filter(s -> s.getGroup() != elem.getGroup())
                                                    .collect(Collectors.toList()));
                    }
                }
                countAllGroupsMarks();
            }
        });
    }

    public File getSerFileToRead(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory((lastOpenedFile == null) ? new File(".").getAbsoluteFile() : lastOpenedFile.getParentFile());
        List<String> extensions = new ArrayList<>();
        extensions.add("*.ser");
        extensions.add("*.dat");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Files",extensions));
        return fileChooser.showOpenDialog(menuOpenData.getParentPopup().getScene().getWindow());
    }

    public File getSerFileToWrite(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory((lastOpenedFile == null) ? new File(".").getAbsoluteFile() : lastOpenedFile.getParentFile());
        List<String> extensions = new ArrayList<>();
        extensions.add("*.ser");
        extensions.add("*.dat");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Files", extensions));
        return  fileChooser.showSaveDialog(menuOpenData.getParentPopup().getScene().getWindow());
    }

    public void openFile(ActionEvent event){
        File file = getSerFileToRead();
        if(file != null){
            try {
                studentObservableList.setAll((ArrayList<GroupInfo>)FileUtils.deserialize(file));
                lastOpenedFile = file;
            } catch (InvalidClassException | ClassNotFoundException | ClassCastException e){
                e.printStackTrace();
            } catch (IOException e) {
                Dialogs.showErrorDialog(e.getMessage());
            }
        }
    }

    public void saveFile(ActionEvent event){
        File file = getSerFileToWrite();
        if(file != null){
            try {
                FileUtils.serialize(new ArrayList<>(studentObservableList), file);
                lastOpenedFile = file;
            } catch (IOException e) {
                Dialogs.showErrorDialog(e.getMessage());
            }
        }
    }

    public void addStudent(ActionEvent event){
        AddStudentDialog dialog = new AddStudentDialog();
        Student newStudent = dialog.startDialog();
        addStudentToList(newStudent);
    }

    private void addStudentToList(Student newStudent){
        if(newStudent != null){
            if(studentObservableList.stream().anyMatch(elem -> elem.getGroup() == newStudent.getGroup())){
                long index = studentObservableList.stream()
                        .takeWhile(elem -> elem.getGroup() != newStudent.getGroup())
                        .count();
                studentObservableList.add((int)index + 1, newStudent);
            }else {
                studentObservableList.add(new GroupInfo(newStudent.getGroup()));
                studentObservableList.add(newStudent);
            }
            countAllGroupsMarks();
        }
    }

    public void analyze(ActionEvent event){
        List<Map.Entry<Integer, List<GroupInfo>>> list = studentObservableList.stream()
                .collect(Collectors.groupingBy(GroupInfo::getGroup))
                .entrySet()
                .stream()
                .sorted((o1,o2)->Double.compare(o2.getValue().get(0).getAverageMark(), o1.getValue().get(0).getAverageMark()))
                .collect(Collectors.toList());
        studentObservableList.clear();
        list.forEach((map)-> studentObservableList.addAll(map.getValue()));
    }

    private void countAllGroupsMarks(){
        List<GroupInfo> groups = studentObservableList.stream()
                .filter(elem -> !(elem instanceof Student))
                .collect(Collectors.toList());
        groups.forEach(elem -> elem.setAverageMark(countGroupMark(elem.getGroup())));
    }

    private double countGroupMark(int group){
        List<GroupInfo> groupList = studentObservableList.stream()
                .filter(student -> (student instanceof Student) && (student.getGroup() == group))
                .collect(Collectors.toList());
        return groupList.stream()
                .reduce(0.0, (acc, student) -> acc + student.getAverageMark(), Double::sum)/Math.max(1,groupList.size());// что бы не было Nan
    }

    public void readDataBase(ActionEvent event) {
        try {
            StudentDataBase db = new StudentDataBase();
            ArrayList<Student> newStudents = db.readStudents();
            studentObservableList.clear();
            for(Student student: newStudents){
                addStudentToList(student);
            }
            db.close();
            Dialogs.showConfirmDialog("Success read from db");
        } catch (ClassNotFoundException e) {
            Dialogs.showErrorDialog("JDBC not found");
        } catch (SQLException e) {
            Dialogs.showErrorDialog("SQL error " + e.getMessage());
        }
    }

    public void saveDataBase(ActionEvent event) {
        try {
            StudentDataBase db = new StudentDataBase();
            db.writeStudents(new ArrayList<>(studentObservableList));
            db.close();
            Dialogs.showConfirmDialog("Success write to db");
        } catch (ClassNotFoundException e) {
            Dialogs.showErrorDialog("JDBC not found");
        } catch (SQLException e) {
            Dialogs.showErrorDialog("SQL error " + e.getMessage());
        }
    }

    public void recreateDataBaseTable(ActionEvent event) {
        try {
            StudentDataBase db = new StudentDataBase();
            db.createTable();
            db.close();
            Dialogs.showConfirmDialog("Success recreate table in db");
        } catch (ClassNotFoundException e) {
            Dialogs.showErrorDialog("JDBC not found");
        } catch (SQLException e) {
            Dialogs.showErrorDialog("SQL error " + e.getMessage());
        }
    }
}
