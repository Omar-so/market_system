package Models;

public class Product {
    private String number;
    private String name;
    private String price;

    // Constructor
    public Product(String Number, String Name, String price) {
        this.name = Name;
        this.number = Number;
        this.price = price;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
