// Αρχικοποιήση των βιβλιοθηκών
import java.io.*;
class Customer implements Serializable {
    // Ορισμός των μεταβλητών που θα χρησιμοποιηθούν
    String name;
    String roomType;
    int numberOfRooms;
    double totalCost;

    // Constructor
    Customer(String name, String roomType, int numberOfRooms, double totalCost) {
        this.name = name;
        this.roomType = roomType;
        this.numberOfRooms = numberOfRooms;
        this.totalCost = totalCost;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getType() {
        return roomType;
    }

    public int getNum() {
        return numberOfRooms;
    }

    public double getCost() {
        return totalCost;
    }

    // Setters
    public void setCost(double cost) {
        this.totalCost = cost;
    }
    public void setNum(int num) {
        this.numberOfRooms = num;
    }

    public void setType(String type) {
        this.roomType = type;
    }

    public void setName(String name) {
        this.name = name;
    }

}
