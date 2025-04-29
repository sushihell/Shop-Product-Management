package order;
import javafx.scene.control.Label;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class CustomerOrder implements Shipping , Serializable {
    private ArrayList<CartProduct> products;
    private CustomerInfo customerInfo;
    private LocalDate orderDate;
    private LocalTime orderTime;
    public CustomerOrder(CustomerInfo customerInfo, List<CartProduct> products){
        this.customerInfo = customerInfo;
        this.products = new ArrayList<>(products);
        orderDate = LocalDate.now();
        orderTime = LocalTime.now();
    }
    public double getTotalCost(){
        double totalCost = 0;
        for (CartProduct cartProduct : products) {
            totalCost += cartProduct.getProduct().getSellPrice() * cartProduct.getQuantity();
        }
        return totalCost;
    }
    @Override
    public void beginShipping() {
        System.out.println("Shipping Started");
    }

    @Override
    public void cancelShipping() {
        System.out.println("Shipping Canceled");
    }
    public CustomerInfo getCustomerInfo(){
        return customerInfo;
    }
    @Override
    public void ShipmentDelivered() {
        System.out.println("Shipmen tDelivered");
    }
    public ArrayList<CartProduct> getProducts() {
        return products;
    }
    public void AddCartProduct(CartProduct product){
        products.add(product);
    }
    public LocalDate getOrderDate(){
        return orderDate;
    }
}
