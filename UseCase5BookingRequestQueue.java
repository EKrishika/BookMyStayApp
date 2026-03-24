import java.util.LinkedList;
import java.util.Queue;

/**
 * Book My Stay Application - Booking Request Queue
 * This class demonstrates handling booking requests using FIFO queue.
 *
 * @author Shivam
 * @version 5.0
 */

// Reservation class
class Reservation {
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public void displayReservation() {
        System.out.println("Guest: " + guestName + ", Room Type: " + roomType);
    }
}

// Main class
public class UseCase5BookingRequestQueue {

    public static void main(String[] args) {

        System.out.println("========== Book My Stay App ==========");
        System.out.println("Version: 5.0");
        System.out.println("======================================");

        // Create booking request queue
        Queue<Reservation> bookingQueue = new LinkedList<>();

        // Add booking requests (FIFO order)
        bookingQueue.add(new Reservation("Alice", "Single Room"));
        bookingQueue.add(new Reservation("Bob", "Double Room"));
        bookingQueue.add(new Reservation("Charlie", "Suite Room"));

        // Display queued requests
        System.out.println("\n--- Booking Requests Queue ---");

        for (Reservation r : bookingQueue) {
            r.displayReservation();
        }
    }
}
