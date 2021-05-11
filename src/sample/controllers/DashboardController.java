package sample.controllers;

import javafx.collections.ObservableList;
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
import sample.CaloriesGoals;
import sample.Exercise;
import sample.ExerciseGoals;
import sample.Goals;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static javafx.scene.control.TableView.CONSTRAINED_RESIZE_POLICY;

public class DashboardController implements Initializable {
    public Label currentWeightLabel;
    public Label exerciseTodayLabel;
    public Button submitCalorieGoal;
    public TextField dailyCaloriesInput;
    public Label calorieGoalLabel;
    public TableView<ExerciseGoals> exerciseGoalTable;
    public Button removeButton;
    public TextField durationInput;
    public TextField otherInput;
    public DatePicker exerciseGoalDatePicker;
    public ChoiceBox<Exercise.exerciseType> typePicker;
    public Button submitExerciseGoal;
    public TableView<ExerciseGoals> completedExerciseGoalTable;
    public TableView<CaloriesGoals> completedCalorieGoalTable;

    private double currentWeight;
    private double totalExerciseToday;
    private CaloriesGoals currentCalorieGoal;
    private DateTimeFormatter dateFormat;
    private DateTimeFormatter timeFormat;


    private ArrayList<ExerciseGoals> currentExerciseGoals;
    private ArrayList<ExerciseGoals> completedExerciseGoals;
    private ArrayList<CaloriesGoals> oldCalorieGoals;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        currentWeight = 0.0; //TODO get all 3 values from database
        totalExerciseToday = 0.0;
        currentCalorieGoal = new CaloriesGoals(Goals.goalType.CALORIES, LocalDate.now(), LocalDate.now().plusDays(1), 0);

        typePicker.getItems().setAll(Exercise.exerciseType.values());

        //TODO get lists from database
        currentExerciseGoals = new ArrayList<>();
        completedExerciseGoals = new ArrayList<>();
        oldCalorieGoals = new ArrayList<>();

        currentWeightLabel.setText(String.valueOf(currentWeight));
        exerciseTodayLabel.setText(String.valueOf(totalExerciseToday));
        calorieGoalLabel.setText(String.valueOf(currentCalorieGoal.getCalToBurn()));

        dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        timeFormat = DateTimeFormatter.ofPattern("HH:mm");

        otherInput.setDisable(true);
        typePicker.setOnAction(event -> {
            if(typePicker.getValue() != null) {
                otherInput.setDisable(!typePicker.getValue().equals(Exercise.exerciseType.OTHER));
            }
        });

        durationInput.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue.matches("[0-9]*") || newValue.matches("0")) {
                durationInput.setText(oldValue);
            }
        });

        dailyCaloriesInput.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue.matches("[0-9]*") || newValue.matches("0") || newValue.length() > 4) {
                dailyCaloriesInput.setText(oldValue);
            }
        });

        initializeExerciseGoalTable();
        initializeCompletedExerciseGoalTable();
        initializeOldCalorieGoalTable();
    }

    private void initializeExerciseGoalTable() {
        exerciseGoalTable.setPlaceholder(new Label("No exercise goals to display"));
        exerciseGoalTable.columnResizePolicyProperty().set(CONSTRAINED_RESIZE_POLICY);
        exerciseGoalTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        TableColumn<ExerciseGoals, String> exerciseTypeColumn = new TableColumn<>("Type");
        exerciseTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        exerciseTypeColumn.resizableProperty().setValue(true);
        exerciseGoalTable.getColumns().add(exerciseTypeColumn);

        TableColumn<ExerciseGoals, LocalTime> durationColumn = new TableColumn<>("Duration");
        durationColumn.setCellValueFactory(new PropertyValueFactory<>("exerciseToDo"));
        durationColumn.resizableProperty().setValue(true);
        exerciseGoalTable.getColumns().add(durationColumn);

        TableColumn<ExerciseGoals, LocalDate> exerciseDateColumn = new TableColumn<>("End Date");
        exerciseDateColumn.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        exerciseDateColumn.resizableProperty().setValue(true);
        exerciseGoalTable.getColumns().add(exerciseDateColumn);
    }

    private void initializeCompletedExerciseGoalTable() {
        completedExerciseGoalTable.setPlaceholder(new Label("No completed exercise goals to display"));
        completedExerciseGoalTable.columnResizePolicyProperty().set(CONSTRAINED_RESIZE_POLICY);

        TableColumn<ExerciseGoals, String> exerciseTypeColumn = new TableColumn<>("Type");
        exerciseTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        exerciseTypeColumn.resizableProperty().setValue(true);
        completedExerciseGoalTable.getColumns().add(exerciseTypeColumn);

        TableColumn<ExerciseGoals, LocalTime> durationColumn = new TableColumn<>("Duration");
        durationColumn.setCellValueFactory(new PropertyValueFactory<>("exerciseToDo"));
        durationColumn.resizableProperty().setValue(true);
        completedExerciseGoalTable.getColumns().add(durationColumn);

        TableColumn<ExerciseGoals, LocalDate> exerciseEndDateColumn = new TableColumn<>("Start Date");
        exerciseEndDateColumn.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        exerciseEndDateColumn.resizableProperty().setValue(true);
        completedExerciseGoalTable.getColumns().add(exerciseEndDateColumn);

        TableColumn<ExerciseGoals, LocalDate> exerciseCompletionDateColumn = new TableColumn<>("End Date");
        exerciseCompletionDateColumn.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        exerciseCompletionDateColumn.resizableProperty().setValue(true);
        completedExerciseGoalTable.getColumns().add(exerciseCompletionDateColumn);
    }

    private void initializeOldCalorieGoalTable() {
        completedCalorieGoalTable.setPlaceholder(new Label("No completed exercise goals to display"));
        completedCalorieGoalTable.columnResizePolicyProperty().set(CONSTRAINED_RESIZE_POLICY);

        TableColumn<CaloriesGoals, Double> dailyCaloriesColumn = new TableColumn<>("Daily Calories");
        dailyCaloriesColumn.setCellValueFactory(new PropertyValueFactory<>("calToBurn"));
        dailyCaloriesColumn.resizableProperty().setValue(true);
        completedCalorieGoalTable.getColumns().add(dailyCaloriesColumn);

        TableColumn<CaloriesGoals, LocalDate> caloriesGoalsEndDateColumn = new TableColumn<>("End Date");
        caloriesGoalsEndDateColumn.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        caloriesGoalsEndDateColumn.resizableProperty().setValue(true);
        completedCalorieGoalTable.getColumns().add(caloriesGoalsEndDateColumn);
    }

    public void completeExerciseGoal(MouseEvent event) {
        ObservableList<ExerciseGoals> selectedItems = exerciseGoalTable.getSelectionModel().getSelectedItems();

        int size = selectedItems.size();

        if (size > 0) {
            completedExerciseGoals.addAll(selectedItems);
            exerciseGoalTable.getItems().removeAll(selectedItems); //removes all selected items
            exerciseGoalTable.refresh();
            completedExerciseGoalTable.getItems().clear();
            completedExerciseGoalTable.getItems().setAll(completedExerciseGoals);
            completedExerciseGoalTable.refresh();
        }
    }

    public void removeExerciseGoal(MouseEvent event) {
        ObservableList<ExerciseGoals> selectedItems = exerciseGoalTable.getSelectionModel().getSelectedItems();

        int size = selectedItems.size();

        if (size > 0) {
            exerciseGoalTable.getItems().removeAll(selectedItems); //removes all selected items
            exerciseGoalTable.refresh();
        }
    }

    public void addToExerciseGoals(MouseEvent event) {
        Exercise.exerciseType type = null;
        String other = null;
        LocalTime time = null;
        LocalDate endDate = null;

        boolean completed = true;

        if(typePicker.getSelectionModel().isEmpty()){
            typePicker.setEffect(new DropShadow(2, Color.RED));
            completed = false;
        }
        else {
            typePicker.setEffect(null);
            if(typePicker.getValue().equals(Exercise.exerciseType.OTHER)){
                if(otherInput.getText().isEmpty()){
                    otherInput.setEffect(new DropShadow(2, Color.RED));
                    completed = false;
                }
                else{
                    otherInput.setEffect(null);
                    other = otherInput.getText();
                }
            }
            type = typePicker.getValue();
        }

        if(durationInput.getText().isEmpty()){
            durationInput.setEffect(new DropShadow(2, Color.RED));
            completed = false;
        }
        else {
            durationInput.setEffect(null);
            int duration = Integer.parseInt(durationInput.getText());
            int mins;
            if(duration > 60){
                mins = duration % 60;
                duration = duration / 60;
            }
            else{
                mins = duration;
                duration = 0;
            }
            String timeToParse;
            if(mins >= 10){
                if(duration >= 10){
                    timeToParse = duration + ":" + mins;
                }
                else {
                    timeToParse = "0" + duration + ":" + mins;
                }
            }
            else{
                if(duration >= 10){
                    timeToParse = duration + ":" + "0" + mins;
                }
                else {
                    timeToParse = "0" + duration + ":" + "0"+ mins;
                }
            }
            time = LocalTime.parse(timeToParse, timeFormat);

        }

        if(exerciseGoalDatePicker.getValue() == null){
            exerciseGoalDatePicker.setEffect(new DropShadow(2, Color.RED));
            completed = false;
        }
        else{
            exerciseGoalDatePicker.setEffect(null);
            endDate = exerciseGoalDatePicker.getValue();
        }

        if(completed){
            ExerciseGoals newExerciseGoal;
            if(type.equals(Exercise.exerciseType.OTHER)){
                newExerciseGoal = new ExerciseGoals(Goals.goalType.EXERCISE, other, time, LocalDate.now(), endDate);
                otherInput.clear();
            }
            else {
                newExerciseGoal = new ExerciseGoals(Goals.goalType.EXERCISE, type.toString(), time, LocalDate.now(), endDate);
            }
            typePicker.setValue(null);
            durationInput.clear();
            exerciseGoalDatePicker.setValue(null);
            //TODO make it interface with the database here instead of adding to an arraylist.
            currentExerciseGoals.add(newExerciseGoal);
            exerciseGoalTable.getItems().clear();
            exerciseGoalTable.getItems().setAll(currentExerciseGoals);
            exerciseGoalTable.refresh();
        }
    }

    public void updateCalorieGoal(MouseEvent event) {
        currentCalorieGoal.setEndDate(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), LocalDate.now().getDayOfMonth());
        oldCalorieGoals.add(currentCalorieGoal);
        currentCalorieGoal = new CaloriesGoals(Goals.goalType.CALORIES, LocalDate.now(), null, Integer.parseInt(dailyCaloriesInput.getText()));
        dailyCaloriesInput.clear();
        calorieGoalLabel.setText(String.valueOf(currentCalorieGoal.getCalToBurn()));
        completedCalorieGoalTable.getItems().clear();
        completedCalorieGoalTable.getItems().setAll(oldCalorieGoals);
        completedCalorieGoalTable.refresh();
    }

    public void switchToMeal(MouseEvent event) throws IOException {
        Parent dashboardRoot = FXMLLoader.load(getClass().getResource("../resources/views/MealView.fxml"));
        Scene dashboardScene = new Scene(dashboardRoot);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(dashboardScene);
        window.show();
    }

    public void switchToExercise(MouseEvent event) throws IOException {
        Parent dashboardRoot = FXMLLoader.load(getClass().getResource("../resources/views/ExerciseView.fxml"));
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
}
