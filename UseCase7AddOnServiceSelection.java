import java.util.*;

/**
 * Book My Stay Application - Add-On Service Selection
 * This class demonstrates attaching optional services to a reservation.
 *
 * @author Shivam
 * @version 7.0
 */

// Add-On Service class
class Service {
    private String name;
    private double cost;

    public Service(String name, double cost) {
        this.name = name;
        this.cost = cost;
    }

    public double getCost() {
        return cost;
    }

    public String getName() {
        return name;
    }
}

// Add-On Service Manager
class AddOnServiceManager {

    // Map: Reservation ID -> List of Services
    private Map<String, List<Service>> serviceMap = new HashMap<>();

    // Add service to reservation
    public void addService(String reservationId, Service service) {
        serviceMap.putIfAbsent(reservationId, new ArrayList<>());
        serviceMap.get(reservationId).add(service);
    }

    // Calculate total cost
    public double calculateTotalCost(String reservationId) {
        double total = 0;
        List<Service> services = serviceMap.get(reservationId);

        if (services != null) {
            for (Service s : services) {
                total += s.getCost();
            }
        }
        return total;
    }

    // Display services
    public void displayServices(String reservationId) {
        System.out.println("\nServices for Reservation ID: " + reservationId);

        List<Service> services = serviceMap.get(reservationId);

        if (services == null || services.isEmpty()) {
            System.out.println("No services selected.");
            return;
        }

        for (Service s : services) {
            System.out.println("- " + s.getName() + " : " + s.getCost());
        }

        System.out.println("Total Add-On Cost: " + calculateTotalCost(reservationId));
    }
}

// Main class
public class UseCase7AddOnServiceSelection {

    public static void main(String[] args) {

        System.out.println("========== Book My Stay App ==========");
        System.out.println("Version: 7.0");
        System.out.println("======================================");

        // Sample reservation ID
        String reservationId = "RES-101";

        // Create service manager
        AddOnServiceManager manager = new AddOnServiceManager();

        // Add services
        manager.addService(reservationId, new Service("Breakfast", 500));
        manager.addService(reservationId, new Service("Airport Pickup", 1000));
        manager.addService(reservationId, new Service("Extra Bed", 800));

        // Display selected services and total cost
        manager.displayServices(reservationId);
    }
}
