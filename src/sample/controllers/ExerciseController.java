package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import sample.*;

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
    public Label goalTypeLabel;
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

    public BarChart<String, Number> exerciseGraph;
    public CategoryAxis xAxis;
    public NumberAxis yAxis;

    private LocalDate historyDate;
    private DateTimeFormatter dateFormat;
    private DateTimeFormatter timeFormat;

    private LocalTime topDurationGoal;
    private String topDurationGoalType;
    public static final LocalTime DEFAULT_DURATION_GOAL = LocalTime.parse("01:00", DateTimeFormatter.ofPattern("HH:mm"));
    public static final String DEFAULT_DURATION_GOAL_TYPE = "WALKING";

    private ArrayList<Exercise> exerciseList;
    private ExerciseDatabase exerciseDatabase;
    private ExerciseGoalDatabase exerciseGoalDatabase;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            exerciseDatabase = new ExerciseDatabase("src/sample/data/exercise.csv");
            exerciseDatabase.loadElements();

            exerciseGoalDatabase = new ExerciseGoalDatabase("src/sample/data/exerciseGoals.csv");
            exerciseGoalDatabase.loadElements();

        } catch (IOException e) {
            e.printStackTrace();
        }

        historyDate = LocalDate.now();
        dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        timeFormat = DateTimeFormatter.ofPattern("HH:mm");
        exerciseTypeChoice.getItems().setAll(Exercise.exerciseType.values());

        ExerciseGoal exerciseGoal = exerciseGoalDatabase.getTopGoalByUsername(Main.currentUser.getUsername());
        if(exerciseGoal != null){
            topDurationGoal = exerciseGoal.getExerciseToDo();
            topDurationGoalType = exerciseGoal.getType();
        }
        else {
            try {
                exerciseGoalDatabase.insert(new ExerciseGoal(Goal.goalType.EXERCISE, DEFAULT_DURATION_GOAL_TYPE, DEFAULT_DURATION_GOAL, LocalDate.now(), LocalDate.now().plusMonths(1), Main.currentUser.getUsername(), false));
                topDurationGoal = DEFAULT_DURATION_GOAL;
                topDurationGoalType = DEFAULT_DURATION_GOAL_TYPE;
            } catch (IOException e){
                e.printStackTrace();
            }
        }

        durationGoalLabel.setText(String.valueOf(topDurationGoal));
        goalTypeLabel.setText(topDurationGoalType);

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
        initializeExerciseGraph();
    }

    private void initializeExerciseTable() {
        exerciseTable.setPlaceholder(new Label("No exercise items to display"));
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

        updateExerciseHistoryTable(historyDate);
        updateDurationInformation();
    }

    private void initializeExerciseGraph() {

        xAxis.setLabel("Days");
        yAxis.setLabel("Time (Mins)");
        exerciseGraph.setTitle("Time spent exercising this week");
        updateExerciseGraph();
    }

    private void updateExerciseGraph() {
        XYChart.Series<String, Number> series = new XYChart.Series<>();

        LocalDate currentDate = historyDate.minusDays(6);
        for (int i = 1; i <= 7; i++) {

            System.out.println(currentDate);
            ArrayList<Exercise> exercises = exerciseDatabase.getAllByUserNameAndDate(Main.currentUser.getUsername(), currentDate);
            double totalDuration = 0.0;
            for (Exercise e : exercises) {
                totalDuration = totalDuration + e.getDuration();
            }
            series.getData().add(new XYChart.Data<>(currentDate.getDayOfWeek().toString(), totalDuration));
            currentDate = currentDate.plusDays(1);
        }
        exerciseGraph.getData().add(series);
    }


    public void nextDatesOnClick(ActionEvent event) {
        if(event.getSource().equals(prevDatesBtn)){
            historyDate = historyDate.minusDays(3);
        }
        else if(event.getSource().equals(nextDatesBtn)){
            historyDate = historyDate.plusDays(3);
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
        updateExerciseHistoryTable(historyDate);
    }

    public void datesOnClick(ActionEvent event) {
        LocalDate newDate = historyDate;

        if(event.getSource().equals(leftDateBtn)) {
            newDate = historyDate.minusDays(1);
        }
        else if(event.getSource().equals(rightDateBtn)) {
            newDate = historyDate.plusDays(1);
        }

        updateExerciseHistoryTable(newDate);
        updateDurationInformation();
    }


    private void updateExerciseHistoryTable(LocalDate date) {
        exerciseTable.getItems().clear();
        exerciseTable.getItems().setAll(exerciseDatabase.getAllByUserNameAndDate(Main.currentUser.getUsername(), date));
        exerciseTable.refresh();
    }

    private void updateDurationInformation() {
        double total = 0.0;

        if (!exerciseTable.getItems().isEmpty()) {
            for (Exercise exercise : exerciseTable.getItems()) {
                total = total + exercise.getDuration();
            }
        }

        durationTotalLabel.setText(Double.toString(total));
        durationGoalLabel.setText(String.valueOf(topDurationGoal));
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
                exerciseEntry = new Exercise(distance, duration, type, other, date, time, Main.currentUser.getUsername());
            }
            else {
                exerciseEntry = new Exercise(distance, duration, type, date, time, Main.currentUser.getUsername());
            }
            try {
                exerciseDatabase.insert(exerciseEntry);
            } catch (IOException e) {
                e.printStackTrace();
            }
            updateExerciseHistoryTable(historyDate);
            updateExerciseGraph();
        }
    }

    public void navHomeBtnOnClick(MouseEvent mouseEvent) throws IOException {
        Main.switchView("../resources/views/DashboardView.fxml", mouseEvent, getClass());
    }

    public void navMealBtnOnClick(MouseEvent mouseEvent) throws IOException {
        Main.switchView("../resources/views/mealView.fxml", mouseEvent, getClass());
    }

    public void navGroupsBtnOnClick(MouseEvent mouseEvent) throws IOException {
        Main.switchView("../resources/views/groupsView.fxml", mouseEvent, getClass());
    }

    public void navAccountBtnOnClick(MouseEvent MouseEvent) throws IOException {
        Main.switchView("../resources/views/Account.fxml", MouseEvent, getClass());
    }
}
