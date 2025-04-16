package product;

import java.time.LocalDate;

public class FoodProduct extends Product implements Expirable {
    private LocalDate expireDate;
    private String foodType;

    public FoodProduct(String name, int stock, float sellPrice, int expireInDays, String foodType) {
        super(name, stock, sellPrice);
        this.expireDate = LocalDate.now().plusDays(expireInDays);
        this.foodType = foodType;
    }

    @Override
    public LocalDate getExpireDate() {
        return expireDate;
    }

    public String getFoodType() {
        return foodType;
    }

    @Override
    public String toString() {
        return getName() + " - Type: " + foodType + ", Expires: " + expireDate +
                ", Stock: " + getStock();
    }
}
