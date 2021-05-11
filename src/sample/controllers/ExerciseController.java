package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import sample.Exercise;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static javafx.scene.control.TableView.CONSTRAINED_RESIZE_POLICY;

public class ExerciseController implements Initializable {

    public Label durationTotalLabel;
    public Label durationGoalLabel;
    public TableView<Exercise> exerciseTable;
    public Button prevDatesBtn;
    public Button leftDateBtn;
    public Button middleDateBtn;
    public Button rightDateBtn;
    public Button nextDatesBtn;
    public ChoiceBox<Exercise.exerciseType> exerciseTypeChoice;
    public TextField durationInput;
    public TextField distanceInput;
    public TextField timeInput;
    public DatePicker dateInput;
    public TextField otherInput;

    private LocalDate historyDate;
    private DateTimeFormatter dateFormat;
    private DateTimeFormatter timeFormat;

    private ArrayList<Exercise> exerciseList;

    private double currentDurationGoal;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        historyDate = LocalDate.now();
        dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        timeFormat = DateTimeFormatter.ofPattern("HH:mm");
        exerciseTypeChoice.getItems().setAll(Exercise.exerciseType.values());
        exerciseList = new ArrayList<>();
        currentDurationGoal = 0.0;

        otherInput.setDisable(true);

        exerciseTypeChoice.setOnAction(event -> {
            if(exerciseTypeChoice.getValue() != null) {
                otherInput.setDisable(!exerciseTypeChoice.getValue().equals(Exercise.exerciseType.OTHER));
            }
        });

        distanceInput.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue.matches("[0-9]*")) {
                distanceInput.setText(oldValue);
            }
        });

        durationInput.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue.matches("[0-9]*") || newValue.matches("0")) {
                durationInput.setText(oldValue);
            }
        });

        initializeExerciseTable();
    }

    private void initializeExerciseTable() {
        exerciseTable.setPlaceholder(new Label("No meal items to display"));
        exerciseTable.columnResizePolicyProperty().set(CONSTRAINED_RESIZE_POLICY);
        exerciseTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        TableColumn<Exercise, String> exerciseTypeColumn = new TableColumn<>("Type");
        exerciseTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        exerciseTypeColumn.resizableProperty().setValue(true);
        exerciseTable.getColumns().add(exerciseTypeColumn);

        TableColumn<Exercise, Double> distanceColumn = new TableColumn<>("Distance (km)");
        distanceColumn.setCellValueFactory(new PropertyValueFactory<>("distance"));
        distanceColumn.resizableProperty().setValue(true);
        exerciseTable.getColumns().add(distanceColumn);

        TableColumn<Exercise, Double> durationColumn = new TableColumn<>("Duration (minutes)");
        durationColumn.setCellValueFactory(new PropertyValueFactory<>("duration"));
        durationColumn.resizableProperty().setValue(true);
        exerciseTable.getColumns().add(durationColumn);

        TableColumn<Exercise, LocalTime> exerciseTimeColumn = new TableColumn<>("Time");
        exerciseTimeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
        exerciseTimeColumn.resizableProperty().setValue(true);
        exerciseTable.getColumns().add(exerciseTimeColumn);
    }

    public void switchToDashboard(MouseEvent event) throws IOException {
        Parent dashboardRoot = FXMLLoader.load(getClass().getResource("../resources/views/DashboardView.fxml"));
        Scene dashboardScene = new Scene(dashboardRoot);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(dashboardScene);
        window.show();
    }
    public void switchToMeal(MouseEvent event) throws IOException {
        Parent dashboardRoot = FXMLLoader.load(getClass().getResource("../resources/views/MealView.fxml"));
        Scene dashboardScene = new Scene(dashboardRoot);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(dashboardScene);
        window.show();
    }

    public void switchToGroup(MouseEvent event) throws IOException {
        Parent dashboardRoot = FXMLLoader.load(getClass().getResource("../resources/views/UserGroupView.fxml"));
        Scene dashboardScene = new Scene(dashboardRoot);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(dashboardScene);
        window.show();
    }

    public void switchToAccount(MouseEvent event) throws IOException {
        //TODO add file path for account view
        /*Parent dashboardRoot = FXMLLoader.load(getClass().getResource("../resources/views/.fxml"));
        Scene dashboardScene = new Scene(dashboardRoot);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(dashboardScene);
        window.show();*/
    }

    //TODO interface with database in this function to set exerciseList to values of currect date
    public void datesOnClick(ActionEvent event) {
        if(event.getSource().equals(prevDatesBtn)){
            historyDate = historyDate.minusDays(3);
        }
        else if(event.getSource().equals(nextDatesBtn)){
            historyDate = historyDate.plusDays(3);
        }
        else if(event.getSource().equals(leftDateBtn)) {

        }
        else if(event.getSource().equals(middleDateBtn)) {

        }
        else if(event.getSource().equals(rightDateBtn)) {

        }

        if (!historyDate.equals(LocalDate.now())) {
            leftDateBtn.setText(historyDate.minusDays(1).format(dateFormat));
            middleDateBtn.setText(historyDate.format(dateFormat));
            rightDateBtn.setText(historyDate.plusDays(1).format(dateFormat));
        } else {
            leftDateBtn.setText("Yesterday");
            middleDateBtn.setText("Today");
            rightDateBtn.setText("Tomorrow");
        }

        updateDurationInformation();
        updateExerciseHistoryTable();
    }


    private void updateExerciseHistoryTable() {
        exerciseTable.getItems().clear();
        exerciseTable.getItems().setAll(exerciseList);
    }

    private void updateDurationInformation() {
        double total = 0.0;

        if (!exerciseTable.getItems().isEmpty()) {
            for (Exercise exercise : exerciseTable.getItems()) {
                total = total + exercise.getDuration();
            }
        }

        durationTotalLabel.setText(Double.toString(total));
        durationGoalLabel.setText(Double.toString(currentDurationGoal));
    }

    public void addExerciseEntry(MouseEvent event) {
        Exercise.exerciseType type = null;
        String other = null;
        double distance = 0;
        double duration = 0;
        LocalTime time = null;
        LocalDate date = null;

        boolean completed = true;

        if(exerciseTypeChoice.getSelectionModel().isEmpty()){
            exerciseTypeChoice.setEffect(new DropShadow(2, Color.RED));
            completed = false;
        }
        else {
            exerciseTypeChoice.setEffect(null);
            if(exerciseTypeChoice.getValue().equals(Exercise.exerciseType.OTHER)){
                if(otherInput.getText().isEmpty()){
                    otherInput.setEffect(new DropShadow(2, Color.RED));
                    completed = false;
                }
                else{
                    otherInput.setEffect(null);
                    other = otherInput.getText();
                }
            }
            type = exerciseTypeChoice.getValue();
        }

        if(distanceInput.getText().isEmpty()){
            distanceInput.setEffect(new DropShadow(2, Color.RED));
            completed = false;
        }
        else {
            distanceInput.setEffect(null);
            distance = Double.parseDouble(distanceInput.getText());
        }

        if(durationInput.getText().isEmpty()){
            durationInput.setEffect(new DropShadow(2, Color.RED));
            completed = false;
        }
        else {
            durationInput.setEffect(null);
            duration = Double.parseDouble(durationInput.getText());
        }

        if(timeInput.getText().isEmpty()){
            timeInput.setEffect(new DropShadow(2, Color.RED));
            completed = false;
        }
        else{
            timeInput.setEffect(null);
            time = LocalTime.parse(timeInput.getText(), timeFormat);
        }

        if(dateInput.getValue() == null){
            dateInput.setEffect(new DropShadow(2, Color.RED));
            completed = false;
        }
        else{
            dateInput.setEffect(null);
            date = dateInput.getValue();
        }

        if(completed){
            Exercise exerciseEntry;
            if(type.equals(Exercise.exerciseType.OTHER)){
                exerciseEntry = new Exercise(distance, duration, type, other, date, time);
            }
            else {
                exerciseEntry = new Exercise(distance, duration, type, date, time);
            }
            //TODO make it interface with the database here instead of adding to an arraylist.
            exerciseList.add(exerciseEntry);
        }
    }
}
