package product;

import java.io.Serializable;

public abstract class Product implements Serializable {
    private String name;
    private int stock;
    private float sellPrice;

    public Product(String name, int stock,float sellPrice) {
        this.name = name;
        this.stock = stock;
        this.sellPrice = sellPrice;
    }

    public String getName() {
        return name;
    }

    public int getStock() {
        return stock;
    }

    public float getSellPrice() { return sellPrice;}
    public void reduceStock(int quantity) {
        if (stock >= quantity) {
            stock -= quantity;
        }
    }
    @Override
    public String toString() {
        return name + " - Stock: " + stock;
    }
}
