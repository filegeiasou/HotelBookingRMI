// Αρχικοποίηση των βιβλιοθηκών
import java.rmi.*;
import java.rmi.server.*;
import java.util.*;

public class BookRoomsImpl extends UnicastRemoteObject implements BookRooms {
    // Αρχικοποίηση των μεταβλητών
    private ArrayList<Room> rooms;
    private ArrayList<Customer> customers;
    private Map<String,ArrayList<BookRoomsClientInter>> clients ;

    // Constructor
    public BookRoomsImpl() throws RemoteException {
        super();
        // Δεσμεύω μνήμη για τις λίστες
        rooms = new ArrayList<>();
        customers = new ArrayList<>();
        clients = new HashMap<String,ArrayList<BookRoomsClientInter>>();
        // Αρχικοποίηση των δωματίων
        rooms.add(new Room("A", 75, 40));
        rooms.add(new Room("B", 110, 35));
        rooms.add(new Room("C", 120, 25));
        rooms.add(new Room("D", 150, 30));
        rooms.add(new Room("E", 200, 20));
    }

    // Υλοποίηση των μεθόδων του interface BookRooms
    // Επιστρέφει τη λίστα των δωματίων
    public ArrayList<Room> list() throws RemoteException {
        return rooms;
    }

    // Κάνει κράτηση δωματίων
    public double book(String type, int num, String name) throws RemoteException {
        // Έλεγχος για το αν υπάρχουν δωμάτια
        for (Room room : rooms) {
            // Έλεγχος για το αν το δωμάτιο είναι του επιθυμητού τύπου
            if (room.type.equals(type)) {
                // Έλεγχος για το αν υπάρχουν δωμάτια διαθέσιμα
                if(room.getAvailability()>=num)
                {
                    room.setAvailability(room.getAvailability() - num);
                    double cost = num * room.cost;
                    customers.add(new Customer(name, type, num, cost));
                    return cost;
                }   
                // Έλεγχος για το αν δεν υπάρχουν καθόλου δωμάτια
                else if(room.getAvailability() == 0)
                    return 0;
                // Έλεγχος για το αν υπάρχουν λιγότερα δωμάτια απο τα επιθυμητά
                else if(room.getAvailability() < num)
                    return room.getAvailability();
            }
        }
        return 0;
    }

    // Επιστρέφει τη λίστα των πελατών
    public ArrayList<Customer> guests() throws RemoteException {
        return customers;
    }

    // Ακύρωση κράτησης και επιστροφή των δωματίων
    public ArrayList<Room> cancel(String type, int num, String name) throws RemoteException {
        // Αναζήτηση του πελάτη
        for (Room room : rooms) {
            // Έλεγχος για το αν το δωμάτιο είναι του επιθυμητού τύπου
            if (room.type.equals(type)){
                // Αναζήτηση του πελάτη
                for (Customer customer : customers) {
                    // Έλεγχος για το αν το όνομα του πελάτη ,ο τύπος δωματίου του και ο αριθμός του είναι αυτός που έχει δώσει
                    if (customer.name.equals(name) && customer.roomType.equals(type)&& customer.getNum() >= num){
                        // Αυξάνω τη διαθεσιμότητα των δωματίων
                        room.setAvailability(room.getAvailability() + num);
                        // Βάζω τον ίδιο πελάτη στην λίστα με αλλαγμένες τις τιμές του
                        customers.add(new Customer(name, type, customer.getNum()- num, customer.getCost()- (num * room.cost)));
                        // Αφαιρώ τον πελάτη απο τη λίστα
                        customers.remove(customer);
                        // Ενημέρωση των clients
                        notf(type);
                        // Διακοπή της επανάληψης
                        break;
                    }
                }
            }
        }
        return rooms;
    }

    // Εγγραφή για callback
    public void registerForCallback(BookRoomsClientInter callbackObj, String type) throws RemoteException {
        // Έλεγχος για το αν υπάρχει ήδη ο τύπος δωματίου
        ArrayList<BookRoomsClientInter> clientList = clients.get(type);
        // Αν δεν υπάρχει ο τύπος δωματίου
        if (clientList == null) {
            // Δημιουργία λίστας
            clientList = new ArrayList<>();
            // Προσθήκη του τύπου δωματίου στο map
            clients.put(type, clientList);
        }
        // Προσθήκη του client στη λίστα
        clientList.add(callbackObj);
    }
    
    // Ενημέρωση των clients
    public void notf(String type) throws RemoteException
    {
        // Έλεγχος για το αν υπάρχει ο τύπος δωματίου
        if(clients.containsKey(type))
        {
            // Ενημέρωση των clients
            ArrayList<BookRoomsClientInter> clientList = clients.get(type);
            // Ενημέρωση των clients
            for (BookRoomsClientInter client : clientList) {
                client.notifyA(type);
            }
            // Αφαίρεση του τύπου δωματίου απο το map
            clients.remove(type);
        }
    }

}