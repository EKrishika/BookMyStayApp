import java.util.*;

/**
 * Book My Stay Application - Room Allocation Service
 * This class demonstrates reservation confirmation and safe room allocation.
 *
 * @author Shivam
 * @version 6.0
 */

// Reservation class
class Reservation {
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}

// Inventory Service
class RoomInventory {
    private Map<String, Integer> inventory = new HashMap<>();

    public RoomInventory() {
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 1);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public void decrement(String roomType) {
        inventory.put(roomType, inventory.get(roomType) - 1);
    }
}

// Booking Service
class BookingService {

    private Map<String, Set<String>> allocatedRooms = new HashMap<>();
    private Set<String> allRoomIds = new HashSet<>();
    private int idCounter = 1;

    public void processBookings(Queue<Reservation> queue, RoomInventory inventory) {

        System.out.println("\n--- Processing Bookings ---");

        while (!queue.isEmpty()) {
            Reservation r = queue.poll();
            String type = r.getRoomType();

            if (inventory.getAvailability(type) > 0) {

                // Generate unique room ID
                String roomId;
                do {
                    roomId = type.replace(" ", "") + "-" + idCounter++;
                } while (allRoomIds.contains(roomId));

                // Store in global set
                allRoomIds.add(roomId);

                // Map room type to allocated IDs
                allocatedRooms.putIfAbsent(type, new HashSet<>());
                allocatedRooms.get(type).add(roomId);

                // Update inventory
                inventory.decrement(type);

                // Confirm reservation
                System.out.println("Booking Confirmed for " + r.getGuestName()
                        + " | Room Type: " + type
                        + " | Room ID: " + roomId);

            } else {
                System.out.println("Booking Failed for " + r.getGuestName()
                        + " | Room Type: " + type + " (Not Available)");
            }
        }
    }
}

// Main class
public class UseCase6RoomAllocationService {

    public static void main(String[] args) {

        System.out.println("========== Book My Stay App ==========");
        System.out.println("Version: 6.0");
        System.out.println("======================================");

        // Create booking queue (FIFO)
        Queue<Reservation> queue = new LinkedList<>();
        queue.add(new Reservation("Alice", "Single Room"));
        queue.add(new Reservation("Bob", "Single Room"));
        queue.add(new Reservation("Charlie", "Single Room")); // should fail
        queue.add(new Reservation("David", "Double Room"));

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Process bookings
        BookingService service = new BookingService();
        service.processBookings(queue, inventory);
    }
}
