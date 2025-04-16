package order;
import java.util.ArrayList;

public class CustomerOrder implements Shipping {
    private ArrayList<CartProduct> products;
    private CustomerInfo customerInfo;

    public CustomerOrder(CustomerInfo customerInfo, CartProduct cartProduct){
        this.customerInfo = customerInfo;
        this.products = new ArrayList<>();
        products.add(cartProduct);
    }
    @Override
    public void beginShipping() {
        System.out.println("Shipping Started");
    }

    @Override
    public void cancelShipping() {
        System.out.println("Shipping Canceled");
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
}
