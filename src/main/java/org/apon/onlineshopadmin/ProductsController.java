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

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductsController {

    private final ItemEntryController itemEntryController;
    private final Map<Product, HBox> productUiMap = new HashMap<>();

    public ProductsController(ItemEntryController itemEntryController) {
        this.itemEntryController = itemEntryController;
    }

    public void showProductList(List<Product> products) {
        Stage productStage = new Stage();
        productStage.setTitle("All Products");

        VBox productListVBox = new VBox(10);
        productListVBox.setPadding(new Insets(10));
        productListVBox.setStyle("-fx-background-color: #f9f9f9;");

        productUiMap.clear();

        int index = 0;
        for (Product product : products) {
            HBox productItem = createProductItem(product, index, productListVBox);
            productListVBox.getChildren().add(productItem);
            index++;
        }

        ScrollPane scrollPane = new ScrollPane(productListVBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefViewportHeight(600);
        scrollPane.setPrefViewportWidth(400);

        VBox layout = new VBox(scrollPane);
        layout.setPadding(new Insets(10));
        layout.setStyle("-fx-background-color: #FFFFFF;");

        Scene scene = new Scene(layout, 450, 600);

        productStage.setScene(scene);
        productStage.initModality(Modality.APPLICATION_MODAL);
        productStage.show();
    }

    private HBox createProductItem(Product product, int index, VBox productListVBox) {
        HBox productItem = new HBox(20);
        productItem.setPadding(new Insets(10));
        productItem.setAlignment(Pos.CENTER_LEFT);

        Label nameLabel = new Label(product.getName());
        Label stockLabel = new Label("Stock: " + product.getStock());
        Label priceLabel = new Label("Price: Tk " + product.getSellPrice());

        productItem.getChildren().addAll(nameLabel, stockLabel, priceLabel);

        String backgroundColor = (index % 2 == 0) ? "#e6f7ff" : "#ffffff";
        productItem.setStyle("-fx-background-color: " + backgroundColor + "; -fx-background-radius: 8;");

        productUiMap.put(product, productItem);

        productItem.setOnMouseClicked(event -> showProductDetails(product, productListVBox));
        productItem.setOnMouseEntered(event -> productItem.setStyle("-fx-background-color: #d0ebff; -fx-background-radius: 8;"));
        productItem.setOnMouseExited(event -> productItem.setStyle("-fx-background-color: " + backgroundColor + "; -fx-background-radius: 8;"));

        return productItem;
    }

    private void showProductDetails(Product product, VBox productListVBox) {
        Stage productDetailsStage = new Stage();
        productDetailsStage.setTitle(product.getName());

        VBox detailsVBox = new VBox(10);
        detailsVBox.setPadding(new Insets(10));

        StringBuilder detailsInfo = new StringBuilder();
        detailsInfo.append("Stock: ").append(product.getStock()).append("\n");
        detailsInfo.append("Price: Tk ").append(product.getSellPrice());

        if (product instanceof ElectronicProduct) {
            ElectronicProduct electric = (ElectronicProduct) product;
            detailsInfo.append("\nBrand: ").append(electric.getBrand());
            detailsInfo.append("\nWarranty: ").append(electric.getWarrantyInMonths()).append(" months");
        } else if (product instanceof ClothingProduct) {
            ClothingProduct cloth = (ClothingProduct) product;
            detailsInfo.append("\nSize: ").append(cloth.getSize());
            detailsInfo.append("\nMaterial: ").append(cloth.getMaterial());
        } else if (product instanceof FoodProduct) {
            FoodProduct food = (FoodProduct) product;
            detailsInfo.append("\nType: ").append(food.getFoodType());
            detailsInfo.append("\nExpire Date: ").append(food.getExpireDate());
        }

        detailsVBox.getChildren().addAll(new Label(detailsInfo.toString()));

        HBox buttonBox = new HBox(20);
        buttonBox.setAlignment(Pos.CENTER);

        Button editButton = new Button("Edit");
        Button deleteButton = new Button("Delete");

        editButton.setOnAction(event -> {
            Product clonedProduct = cloneProduct(product);
            RemoveProduct(product,productListVBox,productDetailsStage);
            Stage listStage = (Stage) productListVBox.getScene().getWindow();
            listStage.close();
            itemEntryController.ShowNewEntry(clonedProduct);
        });

        deleteButton.setOnAction(event -> {
            RemoveProduct(product,productListVBox,productDetailsStage);
        });

        buttonBox.getChildren().addAll(editButton, deleteButton);

        detailsVBox.getChildren().add(buttonBox);

        Scene scene = new Scene(detailsVBox, 400, 200);
        productDetailsStage.setScene(scene);
        productDetailsStage.initModality(Modality.APPLICATION_MODAL);
        productDetailsStage.show();
    }

    private void RemoveProduct(Product product,VBox productListVBox,Stage productDetailsStage){
        Shop.Singleton.removeProduct(product);

        HBox hboxToRemove = productUiMap.get(product);
        if (hboxToRemove != null) {
            productListVBox.getChildren().remove(hboxToRemove);
            productUiMap.remove(product);

            if (productUiMap.isEmpty()) {
                Stage listStage = (Stage) productListVBox.getScene().getWindow();
                listStage.close();
            }
        }
        productDetailsStage.close();
    }
    private Product cloneProduct(Product product) {
        if (product instanceof ElectronicProduct) {
            ElectronicProduct original = (ElectronicProduct) product;
            return new ElectronicProduct(
                    original.getName(),
                    original.getStock(),
                    original.getSellPrice(),
                    original.getBrand(),
                    original.getWarrantyInMonths(),
                    original.getResalePrice()
            );
        } else if (product instanceof ClothingProduct) {
            ClothingProduct original = (ClothingProduct) product;
            return new ClothingProduct(
                    original.getName(),
                    original.getStock(),
                    original.getSellPrice(),
                    original.getSize(),
                    original.getMaterial(),
                    original.getResalePrice()
            );
        } else if (product instanceof FoodProduct) {
            FoodProduct original = (FoodProduct) product;
            return new FoodProduct(
                    original.getName(),
                    original.getStock(),
                    original.getSellPrice(),
                    (int) ChronoUnit.DAYS.between(LocalDate.now(), original.getExpireDate()),
                    original.getFoodType()
            );
        } else {
            return new GeneralProduct(
                    product.getName(),
                    product.getStock(),
                    product.getSellPrice()
            );
        }
    }
}
