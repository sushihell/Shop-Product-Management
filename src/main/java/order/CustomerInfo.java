package order;

public class CustomerInfo {
    private String name;
    private String location;
    private int phoneNumber;
    private String email;
    public CustomerInfo(String name,String location,int phoneNumber,String email){
        this.name = name;
        this.location = location;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }
    public CustomerInfo(String name,String location,int phoneNumber){
        this.name = name;
        this.location = location;
        this.phoneNumber = phoneNumber;
    }
    public void UpdateInfo(String name,String location,int phoneNumber,String email){
        this.name = name;
        this.location = location;
        this.phoneNumber = phoneNumber;
        this.email = email;
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
