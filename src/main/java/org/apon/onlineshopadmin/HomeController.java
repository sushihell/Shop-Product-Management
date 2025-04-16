package org.apon.onlineshopadmin;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class HomeController {

    @FXML
    private Label pendingOrderCountLabel;

    @FXML
    private Label outOfStockCountLabel;

    @FXML
    protected void onAddProductClicked() {
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle("Add New Product");

        // Input fields
        TextField nameField = new TextField();
        nameField.setPromptText("Product Name");

        TextField quantityField = new TextField();
        quantityField.setPromptText("Quantity");

        TextField costField = new TextField();
        costField.setPromptText("Cost");

        // Buttons
        Button doneButton = new Button("Done");
        Button cancelButton = new Button("Cancel");

        HBox buttonBox = new HBox(10, doneButton, cancelButton);
        buttonBox.setAlignment(Pos.CENTER);

        VBox layout = new VBox(10, nameField, quantityField, costField, buttonBox);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout, 300, 250);
        popupStage.setScene(scene);
        popupStage.show();

        // Handle buttons
        doneButton.setOnAction(e -> {
            String name = nameField.getText();
            int quantity = Integer.parseInt(quantityField.getText());
            double cost = Double.parseDouble(costField.getText());

            // Save product (you can improve this by adding validation & saving logic)
            //Product newProduct = new Product(name, quantity, cost);
            // ProductManager.getInstance().addProduct(newProduct);

            popupStage.close();
        });

        cancelButton.setOnAction(e -> popupStage.close());
    }

    @FXML
    protected void onViewProductsClicked() {
        showInfo("View Product List button clicked!");
        // You can load the product list scene here
    }

    @FXML
    protected void onViewOrdersClicked() {
        showInfo("View Orders button clicked!");
        // You can load the orders list scene here
    }
    @FXML
    protected void onCustomerOrderClicked() {
        showInfo("View Orders button clicked!");
        // You can load the orders list scene here
    }

    private void showInfo(String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Button Clicked");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
