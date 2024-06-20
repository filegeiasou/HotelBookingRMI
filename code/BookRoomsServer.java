// Αρχικοποίηση των Βιβλιοθηκών
import java.rmi.*;
import java.rmi.registry.*;


public class BookRoomsServer {
    // Constructor
    public BookRoomsServer() {
        try {
          // Δημιουργία ενός αντικειμένου της κλάσης BookRoomsImpl
            BookRooms c = new BookRoomsImpl();
            // Δημιουργία του RMI registry με πόρτα 1099 
            Naming.rebind("rmi://localhost:1099/BookRooms", c);
        } catch (Exception e) {
          System.out.println("Trouble: " + e);
        }
      }
   
      // Κώδικας για την εκκίνηση του server
      public static void main(String args[]) {
             System.out.println("RMI server started");
              try { //special exception handler for registry creation
                //Δημιουργία του RMI registry με πόρτα 1099
                          LocateRegistry.createRegistry(1099);
                          System.out.println("java RMI registry created.");
                  } catch (RemoteException e) {
                  //do nothing, error means registry already exists
                             System.out.println("java RMI registry already exists.");
                   }
        // Δημιουργία ενός αντικειμένου της κλάσης BookRoomsServer
        new BookRoomsServer();
      }
}
