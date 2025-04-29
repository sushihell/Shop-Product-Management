package org.apon.onlineshopadmin;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import order.CustomerInfo;
import order.CustomerOrder;
import product.ClothingProduct;
import product.ElectronicProduct;
import product.FoodProduct;
import product.Product;
import shop.Shop;
import order.CartProduct;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderController {
    private final Map<Product, HBox> productUiMap = new HashMap<>();
    private final List<CartProduct> cartProducts = new ArrayList<>();
    Stage productStage;
    Stage orderStage;
    public void ShowOrders() {
        List<CustomerOrder> orders = Shop.Singleton.getOrders();
        orderStage = new Stage();
        orderStage.setTitle("All Orders");

        VBox mainVBox = new VBox(10);
        mainVBox.setPadding(new Insets(10));
        mainVBox.setStyle("-fx-background-color: #f9f9f9;");

        VBox orderListVBox = new VBox(10);
        orderListVBox.setPadding(new Insets(10));

        int index = 0;
        for (CustomerOrder order : orders) {
            HBox orderItem = createOrderItem(order, index, orderListVBox);
            orderListVBox.getChildren().add(orderItem);
            index++;
        }

        ScrollPane scrollPane = new ScrollPane(orderListVBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefViewportHeight(600);
        scrollPane.setPrefViewportWidth(400);

        mainVBox.getChildren().add(scrollPane);

        Scene scene = new Scene(mainVBox, 450, 600);
        orderStage.setScene(scene);
        orderStage.initModality(Modality.APPLICATION_MODAL);
        orderStage.show();
    }

    private HBox createOrderItem(CustomerOrder order, int index, VBox orderListVBox) {
        HBox orderItem = new HBox(20);
        orderItem.setPadding(new Insets(10));
        orderItem.setAlignment(Pos.CENTER_LEFT);

        Label nameLabel = new Label("Name: " + order.getCustomerInfo().getName());
        Label locationLabel = new Label("Location: " + order.getCustomerInfo().getLocation());
        Label dateLable = new Label("Date: " + order.getOrderDate().toString());

        orderItem.getChildren().addAll(nameLabel, locationLabel, dateLable);

        String backgroundColor = (index % 2 == 0) ? "#e6f7ff" : "#ffffff";
        orderItem.setStyle("-fx-background-color: " + backgroundColor + "; -fx-background-radius: 8;");

        orderItem.setOnMouseClicked(event -> showOrderDetails(order));
        orderItem.setOnMouseEntered(event -> orderItem.setStyle("-fx-background-color: #d0ebff; -fx-background-radius: 8;"));
        orderItem.setOnMouseExited(event -> orderItem.setStyle("-fx-background-color: " + backgroundColor + "; -fx-background-radius: 8;"));

        return orderItem;
    }
    private void showOrderDetails(CustomerOrder order) {
        Stage orderDetailsStage = new Stage();
        orderDetailsStage.setTitle("Order Details");

        VBox detailsVBox = new VBox(10);
        detailsVBox.setPadding(new Insets(10));

        CustomerInfo customerInfo = order.getCustomerInfo();
        detailsVBox.getChildren().addAll(
                new Label("Name: " + customerInfo.getName()),
                new Label("Location: " + customerInfo.getLocation()),
                new Label("Phone: " + customerInfo.getPhoneNumber()),
                new Label("Email: " + customerInfo.getEmail()),
                new Label("Total: Tk " + String.format("%.2f", order.getTotalCost())),
                new Label("Products:")
        );

        // List ordered products
        VBox productsVBox = new VBox(5);
        for (CartProduct cp : order.getProducts()) {
            Label productLabel = new Label("- " + cp.getProduct().getName() + " x" + cp.getQuantity());
            productsVBox.getChildren().add(productLabel);
        }

        ScrollPane productScroll = new ScrollPane(productsVBox);
        productScroll.setFitToWidth(true);

        // Button Section
        HBox buttonBox = new HBox(20);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(10));

        Button shipButton = new Button("Ship Order");
        shipButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold;");
        Button cancelButton = new Button("Cancel Order");
        cancelButton.setStyle("-fx-background-color: #F44336; -fx-text-fill: white; -fx-font-weight: bold;");

        buttonBox.getChildren().addAll(shipButton, cancelButton);

        // Button Actions
        shipButton.setOnAction(e -> {
            Shop.Singleton.removeOrder(order);
            orderDetailsStage.close();
            orderStage.close();
        });

        cancelButton.setOnAction(e -> {
            Shop.Singleton.removeOrder(order);
            orderDetailsStage.close();
            orderStage.close();
        });

        detailsVBox.getChildren().addAll(productScroll, buttonBox);

        Scene scene = new Scene(detailsVBox, 400, 550);
        orderDetailsStage.setScene(scene);
        orderDetailsStage.initModality(Modality.APPLICATION_MODAL);
        orderDetailsStage.show();
    }


    public void showProductList() {
        List<Product> products = Shop.Singleton.getProducts();
        productStage = new Stage();
        productStage.setTitle("All Products");

        VBox mainVBox = new VBox(10);
        mainVBox.setPadding(new Insets(10));
        mainVBox.setStyle("-fx-background-color: #f9f9f9;");

        // Top HBox with "My Cart" button
        HBox topBar = new HBox();
        topBar.setAlignment(Pos.CENTER_RIGHT);

        Button myCartButton = new Button("My Cart");
        myCartButton.setOnAction(event -> showCartWindow());

        topBar.getChildren().add(myCartButton);

        VBox productListVBox = new VBox(10);
        productListVBox.setPadding(new Insets(10));

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

        mainVBox.getChildren().addAll(topBar, scrollPane);

        Scene scene = new Scene(mainVBox, 450, 600);

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
        Label priceLabel = new Label("Price: TK " + product.getSellPrice());

        productItem.getChildren().addAll(nameLabel, stockLabel, priceLabel);

        String backgroundColor = (index % 2 == 0) ? "#e6f7ff" : "#ffffff";
        productItem.setStyle("-fx-background-color: " + backgroundColor + "; -fx-background-radius: 8;");

        productUiMap.put(product, productItem);

        productItem.setOnMouseClicked(event -> showProductDetails(product, productListVBox));
        productItem.setOnMouseEntered(event -> productItem.setStyle("-fx-background-color: #d0ebff; -fx-background-radius: 8;"));
        productItem.setOnMouseExited(event -> productItem.setStyle("-fx-background-color: " + backgroundColor + "; -fx-background-radius: 8;"));

        return productItem;
    }
    Stage productDetailsStage;
    private void showProductDetails(Product product, VBox productListVBox) {
        productDetailsStage = new Stage();
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

        Button orderButton = orderButton(product);
        buttonBox.getChildren().add(orderButton);

        detailsVBox.getChildren().add(buttonBox);

        // 👉 Add this: show how many of this product are already in cart
        Label cartQuantityLabel = new Label("In Cart: " + getQuantityInCart(product));
        cartQuantityLabel.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
        detailsVBox.getChildren().add(cartQuantityLabel);

        // Update cartQuantityLabel dynamically
        orderButton.setOnAction(event -> {
            if (product.getStock() > 0) {
                addToCart(product);
                // Update the quantity in cart label dynamically
                cartQuantityLabel.setText("In Cart: " + getQuantityInCart(product));
            }
        });

        Scene scene = new Scene(detailsVBox, 400, 300); // increased height
        productDetailsStage.setScene(scene);
        productDetailsStage.initModality(Modality.APPLICATION_MODAL);
        productDetailsStage.show();
    }
    // Method to get the current quantity of the product in the cart
    private int getQuantityInCart(Product product) {
        for (CartProduct cp : cartProducts) {
            if (cp.getProduct().equals(product)) {
                return cp.getQuantity();
            }
        }
        return 0; // Product is not in the cart
    }

    // Method to add the product to the cart or update the quantity
    private void addToCart(Product product) {
        boolean found = false;
        for (CartProduct cp : cartProducts) {
            if (cp.getProduct().equals(product)) {
                cp.setQuantity(cp.getQuantity() + 1); // Increase quantity
                found = true;
                break;
            }
        }
        if (!found) {
            cartProducts.add(new CartProduct(product, 1)); // Add new if not found
        }
    }



    private Button orderButton(Product product) {
        String buttonName = "Add To Cart";
        if (product.getStock() <= 0) {
            buttonName = "Out Of Stock";
        }
        Button orderButton = new Button(buttonName);

        orderButton.setOnAction(event -> {
            if (product.getStock() > 0) {
                boolean found = false;
                for (CartProduct cp : cartProducts) {
                    if (cp.getProduct().equals(product)) {
                        // Check if current quantity is less than stock before adding
                        if (cp.getQuantity() < product.getStock()) {
                            cp.setQuantity(cp.getQuantity() + 1); // Increase quantity if it's within stock limit
                            found = true;
                        } else {
                            // Optionally show a message if the user tries to add more than stock
                            showAlert("Stock Limit Reached", "You cannot add more than the available stock.");
                        }
                        break;
                    }
                }
                if (!found) {
                    // Only add a new product if the quantity is within stock
                    if (product.getStock() > 0) {
                        cartProducts.add(new CartProduct(product, 1)); // Add new if not found
                    }
                }
            }
        });
        return orderButton;
    }


    private void showCartWindow() {
        Stage cartStage = new Stage();
        cartStage.setTitle("My Cart");

        VBox cartVBox = new VBox(10);
        cartVBox.setPadding(new Insets(10));
        cartVBox.setAlignment(Pos.TOP_CENTER);

        VBox cartItemsVBox = new VBox(10);
        cartItemsVBox.setPadding(new Insets(10));
        cartItemsVBox.setAlignment(Pos.TOP_CENTER);

        // List all cart products
        for (CartProduct cartProduct : cartProducts) {
            HBox cartItemBox = new HBox(10);
            cartItemBox.setAlignment(Pos.CENTER_LEFT);

            Product product = cartProduct.getProduct();
            Label productLabel = new Label(product.getName() + " - Quantity: " + cartProduct.getQuantity() + " - Price: Tk " + product.getSellPrice());

            Button decreaseButton = new Button("-");
            Button removeButton = new Button("Remove");

            decreaseButton.setOnAction(event -> {
                cartProduct.setQuantity(cartProduct.getQuantity() - 1);
                if (cartProduct.getQuantity() <= 0) {
                    cartProducts.remove(cartProduct);
                }
                cartStage.close();
                showCartWindow(); // Refresh cart window
            });

            removeButton.setOnAction(event -> {
                cartProducts.remove(cartProduct);
                cartStage.close();
                showCartWindow(); // Refresh cart window
            });

            cartItemBox.getChildren().addAll(productLabel, decreaseButton, removeButton);
            cartItemsVBox.getChildren().add(cartItemBox);
        }

        // Billing Address Button (Place Order)
        Button placeOrderButton = new Button("Place Order");
        placeOrderButton.setOnAction(event -> {
            cartStage.close();
            showCustomerInfoWindow();
        });

        HBox buttonBox = new HBox(placeOrderButton);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(10));

        ScrollPane scrollPane = new ScrollPane(cartItemsVBox);
        scrollPane.setFitToWidth(true);

        cartVBox.getChildren().addAll(scrollPane, buttonBox);

        Scene scene = new Scene(cartVBox, 450, 500);
        cartStage.setScene(scene);
        cartStage.initModality(Modality.APPLICATION_MODAL);
        cartStage.show();
    }
    private void showCustomerInfoWindow() {
        Stage customerInfoStage = new Stage();
        customerInfoStage.setTitle("Enter Customer Info");

        VBox formVBox = new VBox(15);
        formVBox.setPadding(new Insets(20));
        formVBox.setAlignment(Pos.TOP_CENTER);

        TextField nameField = new TextField();
        nameField.setPromptText("Name");

        TextField locationField = new TextField();
        locationField.setPromptText("Location");

        TextField phoneField = new TextField();
        phoneField.setPromptText("Phone Number");

        TextField emailField = new TextField();
        emailField.setPromptText("Email (optional)");

        // 👉 Add total cost label
        double totalCost = 0;
        for (CartProduct cartProduct : cartProducts) {
            totalCost += cartProduct.getProduct().getSellPrice() * cartProduct.getQuantity();
        }
        Label totalCostLabel = new Label("Total: Tk " + String.format("%.2f", totalCost));
        totalCostLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        Button confirmButton = new Button("Confirm");
        confirmButton.setOnAction(event -> {
            String name = nameField.getText().trim();
            String location = locationField.getText().trim();
            String phoneText = phoneField.getText().trim();
            String email = emailField.getText().trim();

            // Validate fields
            if (name.isEmpty() || location.isEmpty() || phoneText.isEmpty()) {
                showAlert("Invalid Input", "Name, Location, and Phone fields cannot be empty!");
                return;
            }

            int phone;
            try {
                phone = Integer.parseInt(phoneText);
            } catch (NumberFormatException e) {
                showAlert("Invalid Input", "Phone number must be numeric!");
                return;
            }

            CustomerInfo customer = new CustomerInfo(name, location, phone, email);
            System.out.println("Order confirmed for " + customer.getName());
            CustomerOrder customerOrder = new CustomerOrder(customer,cartProducts);
            Shop.Singleton.placeOrder(customerOrder);
            cartProducts.clear(); // Clear cart
            productDetailsStage.close();
            productStage.close();
            customerInfoStage.close();
            showAlert("Order Placed", "Thank you, " + customer.getName() + "! Your order has been placed successfully.");
        });

        HBox buttonBox = new HBox(confirmButton);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(10));

        formVBox.getChildren().addAll(nameField, locationField, phoneField, emailField, totalCostLabel, buttonBox);

        Scene scene = new Scene(formVBox, 400, 450); // Adjusted height
        customerInfoStage.setScene(scene);
        customerInfoStage.initModality(Modality.APPLICATION_MODAL);
        customerInfoStage.show();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
