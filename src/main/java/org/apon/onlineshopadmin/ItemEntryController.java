package org.apon.onlineshopadmin;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import product.*;
import shop.Shop;

import java.util.ArrayList;
import java.util.List;

public class ItemEntryController {
    private final Stage popupStage;
    private final TextField nameField, quantityField, costField;
    private final ComboBox<String> categoryBox;
    private final Button doneButton, cancelButton;
    Shop shop;

    public ItemEntryController() {
        popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle("Product Entry");

        nameField = new TextField();
        quantityField = new TextField();
        costField = new TextField();
        categoryBox = new ComboBox<>();

        nameField.setPromptText("Product Name");
        quantityField.setPromptText("Quantity");
        costField.setPromptText("Cost");

        categoryBox.getItems().addAll("General", "Electronics", "Clothing", "Food");
        categoryBox.setPromptText("Select Category");

        doneButton = new Button("Done");
        cancelButton = new Button("Cancel");
        cancelButton.setOnAction(e -> popupStage.close());
        HBox buttonBox = new HBox(10, doneButton, cancelButton);
        buttonBox.setAlignment(Pos.CENTER);

        VBox layout = new VBox(10, nameField, quantityField, costField, categoryBox, buttonBox);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);

        popupStage.setScene(new Scene(layout, 300, 300));
    }

    public void ShowNewEntry() {
        nameField.clear();
        quantityField.clear();
        costField.clear();
        categoryBox.setValue("General");
        popupStage.show();
        doneButton.setOnAction(e -> handleMainFormSubmit());
    }
    public void ShowNewEntry(Product product) {
        nameField.setText(product.getName());
        quantityField.setText(String.valueOf(product.getStock()));
        costField.setText(String.valueOf(product.getSellPrice()));
        switch (product) {
            case ElectronicProduct electronicProduct -> categoryBox.setValue("Electronics");
            case ClothingProduct clothingProduct -> categoryBox.setValue("Clothing");
            case FoodProduct foodProduct -> categoryBox.setValue("Food");
            default -> {
                categoryBox.setValue("General");
            }
        }
        doneButton.setOnAction(e -> handleMainFormSubmit());
        popupStage.show();
    }

    private void handleMainFormSubmit() {
        try {
            String name = nameField.getText();
            int quantity = Integer.parseInt(quantityField.getText());
            float cost = Float.parseFloat(costField.getText());
            String category = categoryBox.getValue();
            if (category == null) {
                showAlert("Error", "Please select a category.");
                return;
            }

            if (category.equals("General")) {
                Product product = new GeneralProduct(name,quantity,cost);
                Shop.Singleton.addProduct(product);
                System.out.println("General product added.");
                popupStage.close();
                return;
            }

            popupStage.close();
            showCategorySpecificPopup(category, name, quantity, cost);
        } catch (NumberFormatException ex) {
            showAlert("Input Error", "Please enter valid numbers for quantity and cost.");
        }
    }

    private void showCategorySpecificPopup(String category, String name, int quantity, double cost) {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Additional Details - " + category);

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);

        switch (category) {
            case "Electronics" -> setupElectronicsForm(stage, layout, name, quantity, cost);
            case "Clothing" -> setupClothingForm(stage, layout, name, quantity, cost);
            case "Food" -> setupFoodForm(stage, layout, name, quantity, cost);
            default -> showAlert("Invalid Category", "This category is not handled.");
        }

        stage.setScene(new Scene(layout, 350, 300));
        stage.show();
    }

    private void setupElectronicsForm(Stage stage, VBox layout, String name, int quantity, double cost) {
        TextField brandField = createTextField("Brand");
        TextField warrantyField = createTextField("Warranty in Months");
        Button save = createSaveButton(() -> {
            try {
                String brand = brandField.getText();
                int warranty = Integer.parseInt(warrantyField.getText());
                float resalePrice = (float) cost * 0.7f;
                Product product = new ElectronicProduct(name, quantity, (float) cost, brand, warranty, resalePrice);
                Shop.Singleton.addProduct(product);
                System.out.println("Created: " + product);
                stage.close();
            } catch (Exception e) {
                showAlert("Error", "Invalid input in electronics form.");
            }
        });

        layout.getChildren().addAll(new Label("Brand:"), brandField, new Label("Warranty (months):"), warrantyField, save);
    }

    private void setupClothingForm(Stage stage, VBox layout, String name, int quantity, double cost) {
        Label sizeLabel = new Label("Sizes:");
        HBox sizeBox = new HBox(10);
        sizeBox.setAlignment(Pos.CENTER);
        CheckBox[] sizeChecks = {
                new CheckBox("S"), new CheckBox("M"), new CheckBox("L"),
                new CheckBox("XL"), new CheckBox("XXL")
        };
        sizeBox.getChildren().addAll(sizeChecks);

        TextField materialField = createTextField("Material");
        Button save = createSaveButton(() -> {
            try {
                List<String> selectedSizes = new ArrayList<>();
                for (CheckBox cb : sizeChecks) {
                    if (cb.isSelected()) selectedSizes.add(cb.getText());
                }

                if (selectedSizes.isEmpty()) {
                    showAlert("Validation Error", "Select at least one size.");
                    return;
                }

                String size = String.join(",", selectedSizes);
                String material = materialField.getText();
                float resalePrice = (float) cost * 0.5f;
                Product product = new ClothingProduct(name, quantity, (float) cost, size, material, resalePrice);
                Shop.Singleton.addProduct(product);
                System.out.println("Created: " + product);
                stage.close();
            } catch (Exception e) {
                showAlert("Error", "Invalid input in clothing form.");
            }
        });

        layout.getChildren().addAll(sizeLabel, sizeBox, new Label("Material:"), materialField, save);
    }

    private void setupFoodForm(Stage stage, VBox layout, String name, int quantity, double cost) {
        TextField expiryField = createTextField("Expiry (days)");
        TextField foodTypeField = createTextField("Food Type");
        Button save = createSaveButton(() -> {
            try {
                int expiry = Integer.parseInt(expiryField.getText());
                String type = foodTypeField.getText();
                Product product = new FoodProduct(name, quantity, (float) cost, expiry, type);
                Shop.Singleton.addProduct(product);
                System.out.println("Created: " + product);
                stage.close();
            } catch (Exception e) {
                showAlert("Error", "Invalid input in food form.");
            }
        });

        layout.getChildren().addAll(new Label("Expiry (days):"), expiryField, new Label("Food Type:"), foodTypeField, save);
    }

    // --- Utility Methods ---
    private TextField createTextField(String prompt) {
        TextField tf = new TextField();
        tf.setPromptText(prompt);
        return tf;
    }

    private Button createSaveButton(Runnable onSave) {
        Button save = new Button("Save");
        save.setOnAction(e -> onSave.run());
        return save;
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
