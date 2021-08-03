package api;

import Service.CustomerService;
import Service.ReservationService;
import model.Customer;
import model.IRoom;
import model.Room;
import model.RoomType;

import java.util.Collection;

public class AdminResource {
    static CustomerService customerService = new CustomerService();
    static ReservationService reservationService = new ReservationService();

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

