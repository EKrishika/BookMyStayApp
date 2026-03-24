import java.util.*;

/**
 * Book My Stay Application - Booking Cancellation & Inventory Rollback
 * This class demonstrates safe cancellation and rollback using Stack.
 *
 * @author Shivam
 * @version 10.0
 */

// Reservation class
class Reservation {
    private String reservationId;
    private String roomType;
    private String roomId;

    public Reservation(String reservationId, String roomType, String roomId) {
        this.reservationId = reservationId;
        this.roomType = roomType;
        this.roomId = roomId;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getRoomType() {
        return roomType;
    }

    public String getRoomId() {
        return roomId;
    }
}

// Inventory class
class RoomInventory {
    private Map<String, Integer> inventory = new HashMap<>();

    public RoomInventory() {
        inventory.put("Single Room", 1);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 0);
    }

    public void increment(String roomType) {
        inventory.put(roomType, inventory.getOrDefault(roomType, 0) + 1);
    }

    public void display() {
        System.out.println("\n--- Current Inventory ---");
        for (Map.Entry<String, Integer> e : inventory.entrySet()) {
            System.out.println(e.getKey() + " : " + e.getValue());
        }
    }
}

// Cancellation Service
class CancellationService {

    private Map<String, Reservation> confirmedBookings = new HashMap<>();
    private Stack<String> rollbackStack = new Stack<>();

    // Add confirmed booking (setup)
    public void addBooking(Reservation r) {
        confirmedBookings.put(r.getReservationId(), r);
    }

    // Cancel booking
    public void cancel(String reservationId, RoomInventory inventory) {

        if (!confirmedBookings.containsKey(reservationId)) {
            System.out.println("Cancellation Failed: Invalid Reservation ID " + reservationId);
            return;
        }

        Reservation r = confirmedBookings.get(reservationId);

        // Push room ID to rollback stack
        rollbackStack.push(r.getRoomId());

        // Restore inventory
        inventory.increment(r.getRoomType());

        // Remove booking
        confirmedBookings.remove(reservationId);

        System.out.println("Cancellation Successful for Reservation ID: " + reservationId +
                " | Released Room ID: " + r.getRoomId());
    }

    public void showRollbackStack() {
        System.out.println("\nRollback Stack (Recently Released Rooms): " + rollbackStack);
    }
}

// Main class
public class UseCase10BookingCancellation {

    public static void main(String[] args) {

        System.out.println("========== Book My Stay App ==========");
        System.out.println("Version: 10.0");
        System.out.println("======================================");

        // Setup inventory
        RoomInventory inventory = new RoomInventory();

        // Setup cancellation service
        CancellationService service = new CancellationService();

        // Add confirmed bookings
        service.addBooking(new Reservation("RES-101", "Single Room", "SR-1"));
        service.addBooking(new Reservation("RES-102", "Double Room", "DR-1"));

        // Perform cancellations
        service.cancel("RES-101", inventory); // valid
        service.cancel("RES-999", inventory); // invalid

        // Display results
        inventory.display();
        service.showRollbackStack();
    }
}
