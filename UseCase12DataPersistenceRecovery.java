import java.io.*;
import java.util.*;

/**
 * Book My Stay Application - Data Persistence & System Recovery
 * This class demonstrates saving and restoring system state using serialization.
 *
 * @author Shivam
 * @version 12.0
 */

// Reservation class (Serializable)
class Reservation implements Serializable {
    private String reservationId;
    private String guestName;
    private String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public void display() {
        System.out.println(reservationId + " | " + guestName + " | " + roomType);
    }
}

// Inventory class (Serializable)
class RoomInventory implements Serializable {
    private Map<String, Integer> inventory = new HashMap<>();

    public RoomInventory() {
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 1);
    }

    public void display() {
        System.out.println("\nInventory State:");
        for (Map.Entry<String, Integer> e : inventory.entrySet()) {
            System.out.println(e.getKey() + " : " + e.getValue());
        }
    }
}

// Persistence Service
class PersistenceService {

    private static final String FILE_NAME = "hotel_data.ser";

    // Save data
    public void save(RoomInventory inventory, List<Reservation> history) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(inventory);
            oos.writeObject(history);
            System.out.println("\nData saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    // Load data
    public Object[] load() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            RoomInventory inventory = (RoomInventory) ois.readObject();
            List<Reservation> history = (List<Reservation>) ois.readObject();
            System.out.println("\nData loaded successfully.");
            return new Object[]{inventory, history};
        } catch (Exception e) {
            System.out.println("\nNo previous data found. Starting fresh.");
            return null;
        }
    }
}

// Main class
public class UseCase12DataPersistenceRecovery {

    public static void main(String[] args) {

        System.out.println("========== Book My Stay App ==========");
        System.out.println("Version: 12.0");
        System.out.println("======================================");

        PersistenceService service = new PersistenceService();

        RoomInventory inventory;
        List<Reservation> history;

        // Try loading existing data
        Object[] data = service.load();

        if (data != null) {
            inventory = (RoomInventory) data[0];
            history = (List<Reservation>) data[1];
        } else {
            // Fresh start
            inventory = new RoomInventory();
            history = new ArrayList<>();

            // Add sample data
            history.add(new Reservation("RES-101", "Alice", "Single Room"));
            history.add(new Reservation("RES-102", "Bob", "Double Room"));
        }

        // Display recovered state
        inventory.display();

        System.out.println("\nBooking History:");
        for (Reservation r : history) {
            r.display();
        }

        // Save state before exit
        service.save(inventory, history);
    }
}
