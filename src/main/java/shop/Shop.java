package shop;

import order.CartProduct;
import order.CustomerOrder;
import product.Product;
import java.util.ArrayList;

public class Shop {
    private ShopInfo shopInfo;
    private ArrayList<Product> products;
    private ArrayList<CustomerOrder> orders;

    public Shop(String shopName, String ownerName, long contactNumber, String shopAddress) {
        this.shopInfo = new ShopInfo(shopName, ownerName, contactNumber, shopAddress);
        this.products = new ArrayList<>();
        this.orders = new ArrayList<>();
    }

    public ShopInfo getShopInfo() {
        return shopInfo;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void addProduct(Product product) {
        products.add(product);
    }
    public void placeOrder(CustomerOrder customerOrder) {
        boolean allAvailable = true;

        for (CartProduct cartProduct : customerOrder.getProducts()) {
            Product product = cartProduct.getProduct();
            int quantity = cartProduct.getQuantity();

            if (product.getStock() < quantity) {
                System.out.println(product.getName() + " has insufficient stock.");
                allAvailable = false;
            }
        }

        if (allAvailable) {
            // Reduce stock
            for (CartProduct cartProduct : customerOrder.getProducts()) {
                Product product = cartProduct.getProduct();
                int quantity = cartProduct.getQuantity();

                product.reduceStock(quantity);
            }

            // Add order
            orders.add(customerOrder);
            System.out.println("Order placed successfully.");
            customerOrder.beginShipping();
        } else {
            System.out.println("Order failed due to insufficient stock.");
        }
    }

    public boolean inStock(Product product) {
        if (product.getStock() > 0) {
            return true;
        } else {
            System.out.println(product.getName() + " is out of stock.");
            return false;
        }
    }

    public void displayProducts() {
        System.out.println("Products in " + shopInfo.getName() + ":");
        for (Product product : products) {
            System.out.println(product);
        }
    }
}
