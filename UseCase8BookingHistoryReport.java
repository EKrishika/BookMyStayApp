import java.util.*;

/**
 * Book My Stay Application - Booking History & Reporting
 * This class demonstrates storing confirmed bookings and generating reports.
 *
 * @author Shivam
 * @version 8.0
 */

// Reservation class
class Reservation {
    private String reservationId;
    private String guestName;
    private String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public void display() {
        System.out.println("Reservation ID: " + reservationId +
                " | Guest: " + guestName +
                " | Room Type: " + roomType);
    }
}

// Booking History class
class BookingHistory {
    private List<Reservation> history = new ArrayList<>();

    // Add confirmed reservation
    public void addReservation(Reservation reservation) {
        history.add(reservation);
    }

    // Get all reservations
    public List<Reservation> getAllReservations() {
        return history;
    }
}

// Reporting Service
class BookingReportService {

    public void generateReport(List<Reservation> reservations) {

        System.out.println("\n--- Booking History Report ---");

        if (reservations.isEmpty()) {
            System.out.println("No bookings available.");
            return;
        }

        for (Reservation r : reservations) {
            r.display();
        }

        // Summary count
        System.out.println("\nTotal Bookings: " + reservations.size());
    }
}

// Main class
public class UseCase8BookingHistoryReport {

    public static void main(String[] args) {

        System.out.println("========== Book My Stay App ==========");
        System.out.println("Version: 8.0");
        System.out.println("======================================");

        // Initialize booking history
        BookingHistory history = new BookingHistory();

        // Add confirmed bookings
        history.addReservation(new Reservation("RES-101", "Alice", "Single Room"));
        history.addReservation(new Reservation("RES-102", "Bob", "Double Room"));
        history.addReservation(new Reservation("RES-103", "Charlie", "Suite Room"));

        // Generate report
        BookingReportService reportService = new BookingReportService();
        reportService.generateReport(history.getAllReservations());
    }
}
