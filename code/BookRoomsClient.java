// Αρχικοποιώ τις βιβλιοθήκες
import java.rmi.*;
import java.rmi.server.*;
import java.net.*;
import java.util.*;

public class BookRoomsClient extends UnicastRemoteObject implements BookRoomsClientInter{

    // Constructor
    protected BookRoomsClient() throws RemoteException {
        super();
    }
    private static boolean flag = false;

    // Κώδικας για την εκκίνηση του client
    public static void main(String[] args) 
    {
        try {
            // Γίνεται η σύνδεση με τον server
            BookRooms c = (BookRooms)Naming.lookup("rmi://"+args[1]+":1099/BookRooms");
            // Δημιουργία ενός αντικειμένου της κλάσης BookRoomsClient
            BookRoomsClient client = new BookRoomsClient();
            // Παίρνω την επιλογή του χρήστη
            String epil = args[0].toString();
            // Έλεγχος για το αν έχει δώσει παραπάνω απο 2 ορίσματα
            if(args.length < 2)
            {
                System.out.println("Usage: java BookRoomsClient <list|book|guest|cancel> <hostname>");
                System.exit(0);
            }
            // Έλεγχος για την επιλογή list του χρήστη
            if (epil.equals("list")) 
            {
                // Εμφάνιση των δωματίων
                ArrayList<Room> list = c.list();
                for (Room room : list) {
                    System.out.println(room.availability + " δωμάτια " + room.type + " - τιμή: " + room.cost + " Ευρώ την βραδιά");
                }
                System.exit(0);
            } 
            // Έλεγχος για την επιλογή book του χρήστη
            else if (epil.equals("book")) 
            {
                // Έλεγχος για το αν έχει δώσει παραπάνω απο 5 ορίσματα
                if(args.length < 5)
                {
                    System.out.println("Usage: java BookRoomsClient <book> <hostname> <type> <number> <name>");
                    System.exit(0);
                }
                // Αρχικοποίηση των μεταβλητών
                double cost ;
                String type = args[2].toString();
                int num = Integer.parseInt(args[3]);
                String name = args[4].toString();
                // Κάνω κράτηση δωματίων
                cost = c.book(type, num, name);
                int num1 = (int)cost;
                // Έλεγχος για το αν δεν υπάρχουν καθόλου δωμάτια
                if(cost == 0)
                {
                    // Διαβάζω την απάντηση του χρήστη
                    System.out.println("Δεν υπάρχουν διαθέσιμα δωμάτια τύπου " + type);
                    System.out.println("Θες να περιμενεις (Υ/Ν)");
                    Scanner sc = new Scanner(System.in);
                    String ans = sc.nextLine();
                    if(ans.equals("Y"))
                    {
                        // Εγγραφή για callback, περιμένει σε λίστα αναμονής
                        c.registerForCallback(client, type);
                        System.out.println("Περιμένεις για δωμάτια τύπου " + type);
                    }
                    else
                    {
                        System.out.println("Κράτηση ακυρώθηκε");
                        System.exit(0);
                    }
                    sc.close();

                }
                // Έλεγχος για το αν υπάρχουν δωμάτια αλλά όχι όσα ζήτησε ο χρήστης
                else if(cost < num)
                {
                    // Διαβάζω την απάντηση του χρήστη
                    System.out.println("Δεν υπάρχουν αρκετά δωμάτια τύπου " + type);
                    System.out.println("Υπάρχουν μόνο " + num1 + " δωμάτια διαθέσιμα");
                    System.out.println("Θες να κάνεις κράτηση για " + num1 + " δωμάτια(Υ/Ν)");
                    Scanner sc = new Scanner(System.in);
                    String ans = sc.nextLine();
                    if(ans.equals("Y"))
                    {
                        // Κάνω κράτηση για τον αριθμό των δωματίων που υπάρχουν
                        cost = c.book(type, num1, name);
                        System.out.println("Κράτηση " + num1 + " δωματίων τύπου " + type + " για τον/την " + name + " με συνολικό κόστος " + cost);
                        System.exit(0);
                    }
                    else
                    {
                        // Διαβάζω την απάντηση του χρήστη
                        System.out.println("Θες να περιμενεις (Υ/Ν)");
                        Scanner sca = new Scanner(System.in);
                        String anse = sca.nextLine();
                        if(anse.equals("Y"))
                        {
                            // Εγγραφή για callback, περιμένει σε λίστα αναμονής
                            c.registerForCallback(client, type);
                            System.out.println("Περιμένεις για δωμάτια τύπου " + type);
                        }
                        else
                        {
                            System.out.println("Κράτηση ακυρώθηκε");
                            System.exit(0);
                        }
                        sca.close();
                    }   
                    sc.close();
                }
                // Έλεγχος για το αν υπάρχουν τα δωμάτια που ζήτησε ο χρήστης
                else if(cost>num)
                {
                    System.out.println("Κράτηση " + num + " δωματίων τύπου " + type + " για τον/την " + name + " με συνολικό κόστος " + cost);
                    System.exit(0);
                }
            }
            // Έλεγχος για την επιλογή guest του χρήστη
            else if(epil.equals("guests"))
            {
                // Εμφάνιση των πελατών που έχουν κάνει κράτηση
                ArrayList<Customer> guests = c.guests();
                for (Customer guest : guests) {
                    System.out.println("Ο/Η " +guest.name + " έκλεισε " + guest.numberOfRooms + " δωμάτια τύπου " + guest.roomType + " με συνολικό κόστος " + guest.totalCost);
                }
                System.exit(0);
            }
            // Έλεγχος για την επιλογή cancel του χρήστη
            else if(epil.equals("cancel"))
            {
                // Έλεγχος για το αν έχει δώσει παραπάνω απο 5 ορίσματα
                if(args.length < 5)
                {
                    System.out.println("Usage: java BookRoomsClient <cancel> <hostname> <type> <number> <name>");
                    System.exit(0);
                }
                // Αρχικοποίηση των μεταβλητών
                String type = args[2].toString();
                int num = Integer.parseInt(args[3]);
                String name = args[4].toString();
                // Ακύρωση κράτησης
                ArrayList<Room> a = c.cancel(type, num, name);
                // Εμφάνιση των δωματίων
                for (Room room : a) {
                    System.out.println(room.availability + " δωμάτια " + room.type + " - τιμή: " + room.cost + " Ευρώ την βραδιά");
                } 
                System.exit(0);
            }
            
            // Έλεγχος για το αν έχει δώσει λάθος επιλογή
            while(!flag)
            {
                // Περιμένει για δωμάτια
                try{
                    Thread.sleep(1000);
                }
                // Έλεγχος για το αν έχει δώσει λάθος επιλογή
                catch(InterruptedException e)
                {
                    Thread.currentThread().interrupt();
                }
            }
            System.exit(0);
        }
        // Έλεγχος για το αν δεν υπάρχει server στο registry
        catch (NotBoundException nbe) {
            System.out.println("No server in the registry");
        }
        // Έλεγχος για το αν δεν υπάρχει server στο hostname που έδωσε ο χρήστης
        catch (RemoteException re) {
            System.out.println("Remote exception");
        }
        // Έλεγχος για το αν έχει δώσει λάθος hostname
        catch (MalformedURLException mfe) {
            System.out.println("Malformed URL");
        }
    }

    // Μέθοδος για την ενημέρωση του χρήστη
    @Override
    public void notifyA(String type) throws RemoteException {
        // Ενημέρωση για διαθέσιμα δωμάτια
        System.out.println("Ενημέρωση για διαθέσιμα δωμάτια τύπου " + type);
        // Αλλαγή της τιμής της μεταβλητής flag
        flag = true;
    }

}
