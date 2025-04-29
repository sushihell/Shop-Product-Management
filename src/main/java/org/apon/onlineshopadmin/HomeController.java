package org.apon.onlineshopadmin;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import product.Product;
import shop.Shop;

import java.util.List;

public class HomeController {

    ItemEntryController itemEntryController = new ItemEntryController();
    ProductsController productsShowController = new ProductsController(itemEntryController);
    OrderController orderController = new OrderController();
    @FXML
    private Label pendingOrderCountLabel;

    @FXML
    private Label outOfStockCountLabel;

    @FXML
    public void initialize() {
        Shop.Singleton.homeController = this;
        ShowOrdersCount(Shop.Singleton.getOrders().size());
    }
    public void ShowOrdersCount(int orders){
        pendingOrderCountLabel.setText("Pending Orders: "+ orders);
    }
    @FXML
    protected void onAddProductClicked() {
        itemEntryController.ShowNewEntry();
    }

    @FXML
    protected void onViewProductsClicked() {
        List<Product> products = Shop.Singleton.getProducts();
        if (products == null || products.isEmpty()) {
            showInfo("No products available!");
            return;
        }
        productsShowController.showProductList(products);
    }

    @FXML
    protected void onViewOrdersClicked() {
        orderController.ShowOrders();
    }

    @FXML
    protected void onCustomerOrderClicked() {
        orderController.showProductList();
    }

    private void showInfo(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Notification");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
