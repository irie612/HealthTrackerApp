package sample.controllers;

import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
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
import java.util.ResourceBundle;

import static javafx.scene.control.TableView.CONSTRAINED_RESIZE_POLICY;

public class DashboardController implements Initializable {
    public Label currentWeightLabel;
    public Label exerciseTodayLabel;
    public Button submitCalorieGoal;
    public TextField dailyCaloriesInput;
    public Label calorieGoalLabel;
    public TableView<ExerciseGoal> exerciseGoalTable;
    public Button removeButton;
    public TextField durationInput;
    public TextField otherInput;
    public DatePicker exerciseGoalDatePicker;
    public ChoiceBox<Exercise.exerciseType> typePicker;
    public Button submitExerciseGoal;
    public TableView<ExerciseGoal> completedExerciseGoalTable;
    public TableView<CaloriesGoal> completedCalorieGoalTable;

    private double currentWeight;
    private double totalExerciseToday;
    private CaloriesGoal currentCalorieGoal;
    private DateTimeFormatter dateFormat;
    private DateTimeFormatter timeFormat;

    private CalorieGoalDatabase calorieGoalDatabase;
    private ExerciseGoalDatabase exerciseGoalDatabase;
    private UserDatabase userDatabase;
    private ExerciseDatabase exerciseDatabase;

    public static final int DEFAULT_CALORIE_GOAL = 2000;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try{
            calorieGoalDatabase = new CalorieGoalDatabase("src/sample/data/calorieGoals.csv");
            calorieGoalDatabase.loadElements();

            exerciseGoalDatabase = new ExerciseGoalDatabase("src/sample/data/exerciseGoals.csv");
            exerciseGoalDatabase.loadElements();

            exerciseDatabase = new ExerciseDatabase("src/sample/data/exercise.csv");
            exerciseDatabase.loadElements();

            userDatabase = new UserDatabase("src/sample/data/users.csv");
            userDatabase.loadElements();

        } catch (IOException e){
            e.printStackTrace();
        }

        dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        timeFormat = DateTimeFormatter.ofPattern("HH:mm");
        typePicker.getItems().setAll(Exercise.exerciseType.values());


        currentWeight = userDatabase.getUserByUsername(Main.currentUser.getUsername()).getWeight();
        for(Exercise e : exerciseDatabase.getAllByUserNameAndDate(Main.currentUser.getUsername(), LocalDate.now())){
            totalExerciseToday += e.getDuration();
        }

        currentCalorieGoal = calorieGoalDatabase.getByUsername(Main.currentUser.getUsername());

        currentWeightLabel.setText(String.valueOf(currentWeight));
        exerciseTodayLabel.setText(String.valueOf(totalExerciseToday));
        if(currentCalorieGoal == null){
            calorieGoalLabel.setText(String.valueOf(DEFAULT_CALORIE_GOAL) + " kCal");
        }
        else{
            calorieGoalLabel.setText(String.valueOf(currentCalorieGoal.getCalToBurn()) + " kCal");
        }

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

        TableColumn<ExerciseGoal, String> exerciseTypeColumn = new TableColumn<>("Type");
        exerciseTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        exerciseTypeColumn.resizableProperty().setValue(true);
        exerciseGoalTable.getColumns().add(exerciseTypeColumn);

        TableColumn<ExerciseGoal, LocalTime> durationColumn = new TableColumn<>("Duration");
        durationColumn.setCellValueFactory(new PropertyValueFactory<>("exerciseToDo"));
        durationColumn.resizableProperty().setValue(true);
        exerciseGoalTable.getColumns().add(durationColumn);

        TableColumn<ExerciseGoal, LocalDate> exerciseDateColumn = new TableColumn<>("End Date");
        exerciseDateColumn.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        exerciseDateColumn.resizableProperty().setValue(true);
        exerciseGoalTable.getColumns().add(exerciseDateColumn);

        updateExerciseGoalTable();
    }

    private void initializeCompletedExerciseGoalTable() {
        completedExerciseGoalTable.setPlaceholder(new Label("No completed exercise goals to display"));
        completedExerciseGoalTable.columnResizePolicyProperty().set(CONSTRAINED_RESIZE_POLICY);

        TableColumn<ExerciseGoal, String> exerciseTypeColumn = new TableColumn<>("Type");
        exerciseTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        exerciseTypeColumn.resizableProperty().setValue(true);
        completedExerciseGoalTable.getColumns().add(exerciseTypeColumn);

        TableColumn<ExerciseGoal, LocalTime> durationColumn = new TableColumn<>("Duration");
        durationColumn.setCellValueFactory(new PropertyValueFactory<>("exerciseToDo"));
        durationColumn.resizableProperty().setValue(true);
        completedExerciseGoalTable.getColumns().add(durationColumn);

        TableColumn<ExerciseGoal, LocalDate> exerciseEndDateColumn = new TableColumn<>("Start Date");
        exerciseEndDateColumn.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        exerciseEndDateColumn.resizableProperty().setValue(true);
        completedExerciseGoalTable.getColumns().add(exerciseEndDateColumn);

        TableColumn<ExerciseGoal, LocalDate> exerciseCompletionDateColumn = new TableColumn<>("End Date");
        exerciseCompletionDateColumn.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        exerciseCompletionDateColumn.resizableProperty().setValue(true);
        completedExerciseGoalTable.getColumns().add(exerciseCompletionDateColumn);

        updateCompletedExerciseGoalTable();
    }

    private void initializeOldCalorieGoalTable() {
        completedCalorieGoalTable.setPlaceholder(new Label("No old calorie goals to display"));
        completedCalorieGoalTable.columnResizePolicyProperty().set(CONSTRAINED_RESIZE_POLICY);

        TableColumn<CaloriesGoal, Double> dailyCaloriesColumn = new TableColumn<>("Daily Calories");
        dailyCaloriesColumn.setCellValueFactory(new PropertyValueFactory<>("calToBurn"));
        dailyCaloriesColumn.resizableProperty().setValue(true);
        completedCalorieGoalTable.getColumns().add(dailyCaloriesColumn);

        TableColumn<CaloriesGoal, LocalDate> caloriesGoalsEndDateColumn = new TableColumn<>("End Date");
        caloriesGoalsEndDateColumn.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        caloriesGoalsEndDateColumn.resizableProperty().setValue(true);
        completedCalorieGoalTable.getColumns().add(caloriesGoalsEndDateColumn);

        updateOldCalorieGoalTable();
    }

    private void updateExerciseGoalTable(){
        exerciseGoalTable.getItems().clear();
        exerciseGoalTable.getItems().setAll(exerciseGoalDatabase.getByUsernameAndCompleteness(Main.currentUser.getUsername(), false));
        exerciseGoalTable.refresh();
    }

    private void updateCompletedExerciseGoalTable(){
        completedExerciseGoalTable.getItems().clear();
        completedExerciseGoalTable.getItems().setAll(exerciseGoalDatabase.getByUsernameAndCompleteness(Main.currentUser.getUsername(), true));
        completedExerciseGoalTable.refresh();
    }

    private void updateOldCalorieGoalTable() {
        completedCalorieGoalTable.getItems().clear();
        completedCalorieGoalTable.getItems().setAll(calorieGoalDatabase.getByUsernameAndOld(Main.currentUser.getUsername()));
        completedCalorieGoalTable.refresh();
    }

    public void completeExerciseGoal(MouseEvent event) throws IOException {
        ObservableList<ExerciseGoal> selectedItems = exerciseGoalTable.getSelectionModel().getSelectedItems();

        int size = selectedItems.size();

        if (size > 0) {
            ExerciseGoal newGoal;
            for(ExerciseGoal e : selectedItems) {
                newGoal = e;
                newGoal.setCompleted(true);
                exerciseGoalDatabase.update(e, newGoal);
            }
            updateExerciseGoalTable();
            updateCompletedExerciseGoalTable();
        }
    }

    public void removeExerciseGoal(MouseEvent event) throws IOException {
        ObservableList<ExerciseGoal> selectedItems = exerciseGoalTable.getSelectionModel().getSelectedItems();

        int size = selectedItems.size();

        if (size > 0) {
            for(ExerciseGoal e : selectedItems){
                exerciseGoalDatabase.delete(e);
            }
            updateExerciseGoalTable();
        }
    }

    public void addToExerciseGoals(MouseEvent event) throws IOException {
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
            if(duration >= 60){
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
            ExerciseGoal newExerciseGoal;
            if(type.equals(Exercise.exerciseType.OTHER)){
                newExerciseGoal = new ExerciseGoal(Goal.goalType.EXERCISE, other, time, LocalDate.now(), endDate, Main.currentUser.getUsername(), false);
                otherInput.clear();
            }
            else {
                newExerciseGoal = new ExerciseGoal(Goal.goalType.EXERCISE, type.toString(), time, LocalDate.now(), endDate, Main.currentUser.getUsername(), false);
            }
            typePicker.setValue(null);
            durationInput.clear();
            exerciseGoalDatePicker.setValue(null);
            exerciseGoalDatabase.insert(newExerciseGoal);
            updateExerciseGoalTable();
        }
    }

    public void updateCalorieGoal(MouseEvent event) throws IOException {
        LocalDate endDate = LocalDate.now();
        CaloriesGoal oldGoal = currentCalorieGoal;
        oldGoal.setOld(true);
        oldGoal.setEndDate(endDate.getYear(), endDate.getMonthValue(), endDate.getDayOfMonth());
        calorieGoalDatabase.update(currentCalorieGoal, oldGoal);
        currentCalorieGoal = new CaloriesGoal(Goal.goalType.CALORIES, LocalDate.now(), LocalDate.now().plusMonths(1), Integer.parseInt(dailyCaloriesInput.getText()), Main.currentUser.getUsername(), false);
        calorieGoalDatabase.insert(currentCalorieGoal);
        calorieGoalLabel.setText(String.valueOf(currentCalorieGoal.getCalToBurn()));
        updateOldCalorieGoalTable();
        dailyCaloriesInput.clear();
    }


    public void navExerciseBtnOnClick(MouseEvent mouseEvent) throws IOException {
        Main.switchView("../resources/views/ExerciseView.fxml", mouseEvent, getClass());
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
