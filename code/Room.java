// Αρχικοποιήση των βιβλιοθηκών που θα χρειαστούμε
import java.io.*;

class Room implements Serializable{
    // Ορισμός των μεταβλητών που θα χρησιμοποιηθούν
    String type;
    int cost;
    int availability;
    
    // Constructor
    Room(String type, int cost, int availability) {
        this.type = type;
        this.cost = cost;
        this.availability = availability;
    }

    // Getters και Setters
    public String getType() {
        return type;
    }

    public int getCost() {
        return cost;
    }

    public int getAvailability() {
        return availability;
    }

    public void setAvailability(int availability) {
        this.availability = availability;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

}