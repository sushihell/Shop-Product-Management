package org.apon.onlineshopadmin;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import order.CartProduct;
import order.CustomerInfo;
import order.CustomerOrder;
import product.ClothingProduct;
import product.ElectronicProduct;
import product.FoodProduct;
import product.Product;
import shop.Shop;

import java.io.IOException;
import java.util.ArrayList;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("home-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1080, 720);
        stage.setTitle("Goodies");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        Shop myShop = new Shop("Goodies","OOP Project",123456789,"DIU 65_B");
        launch();
    }
}