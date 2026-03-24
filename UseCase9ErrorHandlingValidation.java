import java.util.*;

/**
 * Book My Stay Application - Error Handling & Validation
 * This class demonstrates validation and custom exception handling.
 *
 * @author Shivam
 * @version 9.0
 */

// Custom Exception
class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

// Inventory class
class RoomInventory {
    private Map<String, Integer> inventory = new HashMap<>();

    public RoomInventory() {
        inventory.put("Single Room", 1);
        inventory.put("Double Room", 0); // No availability
        inventory.put("Suite Room", 2);
    }

    public boolean isValidRoomType(String roomType) {
        return inventory.containsKey(roomType);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public void bookRoom(String roomType) throws InvalidBookingException {

        // Validate room type
        if (!isValidRoomType(roomType)) {
            throw new InvalidBookingException("Invalid room type: " + roomType);
        }

        // Validate availability
        if (getAvailability(roomType) <= 0) {
            throw new InvalidBookingException("No rooms available for: " + roomType);
        }

        // Safe update
        inventory.put(roomType, inventory.get(roomType) - 1);
    }
}

// Main class
public class UseCase9ErrorHandlingValidation {

    public static void main(String[] args) {

        System.out.println("========== Book My Stay App ==========");
        System.out.println("Version: 9.0");
        System.out.println("======================================");

        RoomInventory inventory = new RoomInventory();

        // Test inputs
        String[] testRequests = {
            "Single Room",   // valid
            "Double Room",   // no availability
            "Deluxe Room"    // invalid type
        };

        for (String request : testRequests) {
            try {
                System.out.println("\nProcessing booking for: " + request);
                inventory.bookRoom(request);
                System.out.println("Booking successful for: " + request);

            } catch (InvalidBookingException e) {
                System.out.println("Booking failed: " + e.getMessage());
            }
        }

        System.out.println("\nApplication continues running safely.");
    }
}
