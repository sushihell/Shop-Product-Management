package order;

public class CustomerInfo {
    private String name;
    private String location;
    private int phoneNumber;
    private String email;
    public CustomerInfo(String name,String location,int phoneNumber,String email){
        this.name = name;
        this.location = location;
        this.email = email;
        try {
            if (String.valueOf(phoneNumber).length() != 11) {
                throw new IllegalArgumentException("Phone number must be 11 digits long.");
            }
            this.phoneNumber = phoneNumber;
        } catch (IllegalArgumentException e) {
            System.out.println("Error with phone number: " + e.getMessage());
        }
    }
    public CustomerInfo(String name,String location,int phoneNumber){
        this.name = name;
        this.location = location;
        try {
            if (String.valueOf(phoneNumber).length() != 11) {
                throw new IllegalArgumentException("Phone number must be 11 digits long.");
            }
            this.phoneNumber = phoneNumber;
        } catch (IllegalArgumentException e) {
            System.out.println("Error with phone number: " + e.getMessage());
        }
    }
    public void UpdateInfo(String name,String location,int phoneNumber,String email){
        this.name = name;
        this.location = location;
        this.email = email;
        try {
            if (String.valueOf(phoneNumber).length() != 11) {
                throw new IllegalArgumentException("Phone number must be 11 digits long.");
            }
            this.phoneNumber = phoneNumber;
        } catch (IllegalArgumentException e) {
            System.out.println("Error with phone number: " + e.getMessage());
        }
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }
    public String getLocation() {
        return location;
    }
    public String getName() {
        return name;
    }
}
