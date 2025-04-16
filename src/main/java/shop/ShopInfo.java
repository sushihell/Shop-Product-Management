package shop;

public class ShopInfo {
    private String name;
    private String ownerName;
    private long contactNumber;
    private String shopAddress;

    public ShopInfo(String name, String ownerName, long contactNumber, String shopAddress) {
        this.name = name;
        this.ownerName = ownerName;
        this.contactNumber = contactNumber;
        this.shopAddress = shopAddress;
    }

    public String getName() {
        return name;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public long getContactNumber() {
        return contactNumber;
    }

    public String getShopAddress() {
        return shopAddress;
    }

    @Override
    public String toString() {
        return "Shop Name: " + name + "\n"
                + "Owner: " + ownerName + "\n"
                + "Contact: " + contactNumber + "\n"
                + "Address: " + shopAddress;
    }
}
