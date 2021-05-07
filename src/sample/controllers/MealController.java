package sample.controllers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import sample.Meal;
import sample.utilities.Trie;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.ResourceBundle;

import static javafx.scene.control.TableView.CONSTRAINED_RESIZE_POLICY;

public class MealController implements Initializable {

    @FXML
    public Button prevDatesBtn;

    @FXML
    public Button nextDatesBtn;

    @FXML
    public Button leftDateBtn;

    @FXML
    public Button middleDateBtn;

    @FXML
    public Button rightDateBtn;

    @FXML
    private ChoiceBox<String> mealTypeChoice;

    @FXML
    private ComboBox<String> foodChoice;

    @FXML
    private TextField otherInput;

    @FXML
    private TextField quantityInput;

    @FXML
    private TextField caloriesInput;

    @FXML
    private DatePicker mealDatePicker;

    @FXML
    private TextField mealTimeField;

    @FXML
    private TableView<Meal> mealItemsTable;

    @FXML
    private CheckBox selectAllCheckbox;

    @FXML
    private TextField calorieGoalInput;

    @FXML
    private Label calorieTotalLabel;
    @FXML
    private Label calorieGoalLabel;

    @FXML
    private TableView<Meal> mealsTable;

    @FXML
    private Button saveBtn;

    private static final String URL = "src/sample/data/meal_items.csv";

    private Trie foodData;

    private LocalDate historyDate;
    private DateTimeFormatter dateFormat;
    private DateTimeFormatter timeFormat;

    private double totalCalories;
    private double currentCaloriesGoal;

    private ArrayList<Meal> meals;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        foodData = new Trie();
        try {
            loadFoodData();
            foodChoice.getItems().setAll(foodData);
        } catch (IOException e) {
            e.printStackTrace();
        }

        historyDate = LocalDate.now();
        dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        timeFormat = DateTimeFormatter.ofPattern("HH:mm");

        totalCalories = 0.0;
        currentCaloriesGoal = 0.0;
        meals = new ArrayList<>();

        otherInput.setOnKeyTyped(keyEvent -> foodChoice.setValue(null));

        foodChoice.setEditable(true);
        foodChoice.setOnAction(event -> {
            if (foodChoice.getEditor().getText().equals("")) {
                foodChoice.getItems().setAll(foodData);
            }
            otherInput.setText("");
        });

        //auto complete
        foodChoice.setOnKeyReleased(keyEvent -> {

            if (keyEvent.getText().matches("[A-Za-z]|\\s") || keyEvent.getCode() == KeyCode.BACK_SPACE) {


                String query = foodChoice.getEditor().getText().toLowerCase();

                if (query.equals("")) {
                    foodChoice.getItems().setAll(foodData);
                } else {
                    ArrayList<String> filteredResults = new ArrayList<>();
                    try {
                        filteredResults = foodData.getKeysFromPrefix(query); //gets all items that start with the prefix
                    } catch (NoSuchElementException e) {
                        //no items found with prefix
//                            filteredResults.add("No item found");
//                            filteredResults.add("");
                    }
                    foodChoice.getItems().setAll(filteredResults);
                    foodChoice.show();
                }

            }

        });

        //calorie validation
        caloriesInput.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue.matches("\\d{0,7}([\\.]\\d{0,4})?") || newValue.length() > 4) {
                caloriesInput.setText(oldValue);
            }
        });

        //quantity validation
        quantityInput.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue.matches("[0-9]*") || newValue.length() > 2 || newValue.matches("0")) {
                quantityInput.setText(oldValue);
            }
        });

        //calorie goal validation
        calorieGoalInput.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue.matches("[0-9]*") || newValue.length() > 5 || newValue.matches("0")) {
                calorieGoalInput.setText(oldValue);
            }
        });


        //Tables

        initializeMealItemsTable();

        initializeMealsTable();

    }

    private void initializeMealItemsTable() {

        mealItemsTable.setPlaceholder(new Label("No meal items to display"));
        mealItemsTable.columnResizePolicyProperty().set(CONSTRAINED_RESIZE_POLICY);
        mealItemsTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        selectAllCheckbox.setOnAction(event -> {

            if (selectAllCheckbox.isSelected()) {
                mealItemsTable.getSelectionModel().selectAll();
            } else {
                mealItemsTable.getSelectionModel().clearSelection();
            }

        });

        TableColumn<Meal, Meal.MealType> mealTypeColumn = new TableColumn<>("Meal Type");
        mealTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        mealTypeColumn.resizableProperty().setValue(true);
        mealItemsTable.getColumns().add(mealTypeColumn);

        TableColumn<Meal, String> mealNameColumn = new TableColumn<>("Food");
        mealNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        mealItemsTable.getColumns().add(mealNameColumn);

        TableColumn<Meal, Double> mealCaloriesColumn = new TableColumn<>("Calories");
        mealCaloriesColumn.setCellValueFactory(new PropertyValueFactory<>("calories"));
        mealCaloriesColumn.resizableProperty().setValue(true);
        mealItemsTable.getColumns().add(mealCaloriesColumn);
    }

    private void initializeMealsTable() {

        mealsTable.setPlaceholder(new Label("No meal items to display"));
        mealsTable.columnResizePolicyProperty().set(CONSTRAINED_RESIZE_POLICY);
        mealsTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
//        selectAllCheckbox.setOnAction(event -> {
//
//            if (selectAllCheckbox.isSelected()) {
//                mealItemsTable.getSelectionModel().selectAll();
//         s   } else {
//                mealItemsTable.getSelectionModel().clearSelection();
//            }
//
//        });

        TableColumn<Meal, Meal.MealType> mealTypeColumn = new TableColumn<>("Meal Type");
        mealTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        mealTypeColumn.resizableProperty().setValue(true);
        mealsTable.getColumns().add(mealTypeColumn);

        TableColumn<Meal, String> mealNameColumn = new TableColumn<>("Food");
        mealNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        mealsTable.getColumns().add(mealNameColumn);

        TableColumn<Meal, Double> mealCaloriesColumn = new TableColumn<>("Calories");
        mealCaloriesColumn.setCellValueFactory(new PropertyValueFactory<>("calories"));
        mealCaloriesColumn.resizableProperty().setValue(true);
        mealsTable.getColumns().add(mealCaloriesColumn);

        TableColumn<Meal, LocalTime> mealTimeColumn = new TableColumn<>("Time");
        mealTimeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
        mealTimeColumn.resizableProperty().setValue(true);
        mealsTable.getColumns().add(mealTimeColumn);

    }

    @FXML
    private void removeSelectedMealItems() {
        ObservableList<Meal> selectedItems = mealItemsTable.getSelectionModel().getSelectedItems();

        int size = selectedItems.size();

        if (size > 0) {

            mealItemsTable.getItems().removeAll(selectedItems); //removes all selected items
            mealItemsTable.refresh();
            selectAllCheckbox.setSelected(false); //unselects all
        }

    }

    @FXML
    private void saveMeal() {

        if (!mealItemsTable.getItems().isEmpty()) {
            LocalTime time = LocalTime.parse(mealTimeField.getText(), timeFormat);
            LocalDate date = mealDatePicker.getValue();
            for (Meal m : mealItemsTable.getItems()) {
                m.setTime(time);
                m.setDate(date);

                meals.add(m); //TODO just testing, add to database instead
            }
            System.out.println("Saved meal");
        }
    }

    @FXML
    private void addMealItem() {

        String mealType = "";
        String food = "";
        String other = "";
        double calories = 0.0;
        int quantity = 0;

        boolean completed = true;
        //validation
        if (mealTypeChoice.getSelectionModel().isEmpty()) {
            mealTypeChoice.setEffect(new DropShadow(2, Color.RED));
            completed = false;
        } else {
            mealTypeChoice.setEffect(null);
            mealType = mealTypeChoice.getValue();
        }

        if (foodChoice.getEditor().getText().equals("")) {

            if (otherInput.getText().equals("")) {

                foodChoice.setEffect(new DropShadow(2, Color.RED));
                otherInput.setEffect(new DropShadow(2, Color.RED));
                completed = false;
            } else {
                otherInput.setEffect(null);
                foodChoice.setEffect(null);
                other = otherInput.getText();
            }


        } else {
            foodChoice.setEffect(null);
            otherInput.setEffect(null);
            food = foodChoice.getValue();
        }

        if (caloriesInput.getText().equals("")) {
            caloriesInput.setEffect(new DropShadow(2, Color.RED));
            completed = false;
        } else {
            caloriesInput.setEffect(null);
            calories = Double.parseDouble(caloriesInput.getText());
        }

        if (quantityInput.getText().equals("")) {
            quantityInput.setEffect(new DropShadow(2, Color.RED));
            completed = false;
        } else {
            quantityInput.setEffect(null);
            quantity = Integer.parseInt(quantityInput.getText());
        }


        if (completed) {

            if (food.equals("") && !other.equals("")) {
                food = other;
            }

            //adds meals
            Meal m;
            for (int i = 0; i < quantity; i++) {

                m = new Meal(1, food, Meal.MealType.valueOf(mealType.toUpperCase()), calories);
                mealItemsTable.getItems().add(m);
                System.out.println(m);
            }
        }

    }


    private void loadFoodData() throws IOException {
        BufferedReader fileReader = new BufferedReader(new FileReader(URL));
        String line;
        while ((line = fileReader.readLine()) != null) {
            System.out.println(line);
            foodData.add(line);
        }
    }

    public void switchToDashboard(ActionEvent event) throws IOException {
        Parent dashboardRoot = FXMLLoader.load(getClass().getResource("../resources/views/sample.fxml"));
        Scene dashboardScene = new Scene(dashboardRoot);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(dashboardScene);
        window.show();
    }

    public void updateCalorieGoal(ActionEvent event) {

        //TODO implement update calorie goal
    }

    public void prevDatesOnClick(ActionEvent event) {
        historyDate = historyDate.minusDays(3);

        if (!historyDate.equals(LocalDate.now())) {
            leftDateBtn.setText(historyDate.minusDays(1).format(dateFormat));
            middleDateBtn.setText(historyDate.format(dateFormat));
            rightDateBtn.setText(historyDate.plusDays(1).format(dateFormat));
        } else {
            leftDateBtn.setText("Yesterday");
            middleDateBtn.setText("Today");
            rightDateBtn.setText("Tomorrow");
        }

        updateCaloriesInformation();
        updateMealHistoryTable();
    }

    public void nextDatesOnClick(ActionEvent event) {
        historyDate = historyDate.plusDays(3);

        if (!historyDate.equals(LocalDate.now())) {
            leftDateBtn.setText(historyDate.minusDays(1).format(dateFormat));
            middleDateBtn.setText(historyDate.format(dateFormat));
            rightDateBtn.setText(historyDate.plusDays(1).format(dateFormat));
        } else {
            leftDateBtn.setText("Yesterday");
            middleDateBtn.setText("Today");
            rightDateBtn.setText("Tomorrow");
        }

        updateCaloriesInformation();
        updateMealHistoryTable();
    }

    private void updateCaloriesInformation() {
        double total = 0.0;

        if (!mealsTable.getItems().isEmpty()) {
            for (Meal meal : mealsTable.getItems()) {

                total = total + meal.getCalories();
            }
        }

        calorieTotalLabel.setText(Integer.toString((int) total));
        calorieGoalLabel.setText(Integer.toString((int) currentCaloriesGoal));
    }

    private void updateMealHistoryTable() {
        mealsTable.getItems().clear();
        System.out.println(meals);
        mealsTable.getItems().setAll(meals);
    }

}
