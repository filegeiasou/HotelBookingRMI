// Αρχικοποίηση των βιβλιοθηκών
import java.rmi.*;
import java.util.ArrayList;

public interface BookRooms extends Remote {
    // Ορισμός των μεθόδων που θα υλοποιηθούν στον server
    // Επιστρέφει τη λίστα των δωματίων
    public ArrayList<Room> list() throws RemoteException; 
    // Κάνει κράτηση δωματίων
    public double book(String type, int num, String name) throws RemoteException; 
    // Επιστρέφει τη λίστα των πελατών
    public ArrayList<Customer> guests() throws RemoteException; 
    // Ακύρωση κράτησης και επιστροφή των δωματίων
    public ArrayList<Room> cancel(String type, int num, String name) throws RemoteException;
    // Εγγραφή για callback
    public void registerForCallback (BookRoomsClientInter callbackObj, String type) throws RemoteException;
}
     