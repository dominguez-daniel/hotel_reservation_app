package api;

import Service.CustomerService;
import Service.ReservationService;
import model.Customer;
import model.IRoom;
import model.Room;
import model.RoomType;

import java.util.Collection;

public class AdminResource {
    public static AdminResource adminResource = null;
    private final CustomerService customerService = CustomerService.getInstance();
    private final ReservationService reservationService = ReservationService.getInstance();

    public static AdminResource getInstance() {
        if (adminResource == null) {
            adminResource = new AdminResource();
        }
        return adminResource;
    }

    public Customer getCustomer(String email) {
        return customerService.getCustomer(email);
    }

    public void addRoom(String roomNumber, Double roomPrice, RoomType roomType) {
        try {
            reservationService.addRoom(new Room(roomNumber, roomPrice, roomType));
        } catch (Exception ex) {
            System.out.println(ex.getLocalizedMessage());
        }
    }

    public Collection<IRoom> getAllRooms() {
        return reservationService.getAllRooms();
    }

    public Collection<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    public void displayAllReservations() {
        reservationService.printAllReservations();
    }
}

