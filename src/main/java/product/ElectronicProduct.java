package product;

public class ElectronicProduct extends Product implements Resellable ,Warranty{
    private String brand;
    private int warrantyInMonths;
    private float resalePrice;

    public ElectronicProduct(String name, int stock, float sellPrice, String brand,
                             int warrantyInMonths, float resalePrice) {
        super(name, stock, sellPrice);
        this.brand = brand;
        this.warrantyInMonths = warrantyInMonths;
        this.resalePrice = resalePrice;
    }

    public String getBrand() {
        return brand;
    }

    public int getWarrantyInMonths() {
        return warrantyInMonths;
    }
    @Override
    public float getResalePrice() {
        return resalePrice;
    }

    @Override
    public String toString() {
        return getName() + " (" + brand + ") - Stock: " + getStock() +
                ", Resale: $" + resalePrice;
    }
}
