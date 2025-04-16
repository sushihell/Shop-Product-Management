package product;

public class ClothingProduct extends Product implements Resellable {
    private String size;
    private String material;
    private float resalePrice;
    private String condition;

    public ClothingProduct(String name, int stock, float sellPrice, String size,
                           String material, float resalePrice, String condition) {
        super(name, stock, sellPrice);
        this.size = size;
        this.material = material;
        this.resalePrice = resalePrice;
        this.condition = condition;
    }

    public String getSize() {
        return size;
    }

    public String getMaterial() {
        return material;
    }

    @Override
    public float getResalePrice() {
        return resalePrice;
    }

    @Override
    public String toString() {
        return getName() + " - Size: " + size + ", Material: " + material +
                ", Condition: " + condition + ", Resale: $" + resalePrice;
    }
}
