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
        stage.setTitle("Shop Admin");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        //launch();
        Shop myShop = new Shop("Worst Buy","OOP Project",123456789,"DIU 65_B");

        ElectronicProduct phoneCharger = new ElectronicProduct("Nokia 2020",20,520f,"Nokia",12,200f,"New");
        myShop.addProduct((phoneCharger));

        ElectronicProduct TV = new ElectronicProduct("Sony E65",8,40000f,"Sony",36,28000,"New");
        myShop.addProduct((TV));

        ClothingProduct whiteTShirt = new ClothingProduct("White Cotton T-Shirt",60,120f,"M","Cotton",40f,"New");
        myShop.addProduct((whiteTShirt));

        ClothingProduct jacket = new ClothingProduct("Leather Black Jacket",20,3600,"L","Leather",2800,"New");
        myShop.addProduct((jacket));

        FoodProduct milkBread = new FoodProduct("Pran Milk Bread",120,60f,7,"Bread");
        myShop.addProduct((milkBread));

        FoodProduct honey = new FoodProduct("Pran Honey",40,360,180,"Honey");
        myShop.addProduct((honey));

        myShop.displayProducts();


        CustomerInfo customerInfo = new CustomerInfo("Apon","Savar",300900);
        CartProduct cartHoney = new CartProduct(honey,4);
        CartProduct cartTV = new CartProduct(TV,1);

        CustomerOrder customerOrder = new CustomerOrder(customerInfo,cartHoney);
        customerOrder.AddCartProduct(cartTV);
        myShop.placeOrder(customerOrder);

        myShop.displayProducts();
    }
}