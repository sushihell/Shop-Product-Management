package shop;

import order.CartProduct;
import order.CustomerOrder;
import org.apon.onlineshopadmin.HomeController;
import product.Product;

import java.io.*;
import java.util.ArrayList;

public class Shop {
    private static final String PRODUCTS_FILE = "products.dat";
    private static final String ORDERS_FILE = "orders.dat";
    private ShopInfo shopInfo;
    private ArrayList<Product> products;
    private ArrayList<CustomerOrder> orders;
    public HomeController homeController;
    public static Shop Singleton;
    public Shop(String shopName, String ownerName, long contactNumber, String shopAddress) {
        this.shopInfo = new ShopInfo(shopName, ownerName, contactNumber, shopAddress);
        this.products = new ArrayList<>();
        this.orders = new ArrayList<>();
        if(Singleton == null){
            Singleton = this;
        }
        products = loadProducts();
        orders = loadOrders();
    }

    public ShopInfo getShopInfo() {
        return shopInfo;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }
    public ArrayList<CustomerOrder> getOrders() {
        return orders;
    }

    public void addProduct(Product product) {
        products.add(product);
        saveProducts();
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
            System.out.println("Order placed successfully."+orders.size());
            customerOrder.beginShipping();
            saveProducts();
            saveOrders();
        } else {
            System.out.println("Order failed due to insufficient stock.");
        }
    }
    public void removeOrder(CustomerOrder order) {
        orders.remove(order);
        saveOrders();
    }
    public boolean inStock(Product product) {
        if (product.getStock() > 0) {
            return true;
        } else {
            System.out.println(product.getName() + " is out of stock.");
            return false;
        }
    }
    public void removeProduct(Product product){
        products.remove(product);
        saveProducts();
    }
    public void displayProducts() {
        System.out.println("Products in " + shopInfo.getName() + ":");
        for (Product product : products) {
            System.out.println(product);
        }
    }
    private void saveProducts() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(PRODUCTS_FILE))) {
            oos.writeObject(products);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveOrders() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ORDERS_FILE))) {
            oos.writeObject(orders);
        } catch (IOException e) {
            e.printStackTrace();
        }
        homeController.ShowOrdersCount(orders.size());
    }

    private ArrayList<Product> loadProducts() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(PRODUCTS_FILE))) {
            return (ArrayList<Product>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList<>();
        }
    }

    private ArrayList<CustomerOrder> loadOrders() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ORDERS_FILE))) {
            return (ArrayList<CustomerOrder>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList<>();
        }
    }
}
