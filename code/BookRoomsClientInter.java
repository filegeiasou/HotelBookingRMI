// Αρχικοποίηση των βιβλικοθηκών
import java.rmi.*;
public interface BookRoomsClientInter extends Remote {
    // Ορισμός των μεθόδων που θα υλοποιηθούν στον client
    void notifyA(String type) throws RemoteException;   
}
