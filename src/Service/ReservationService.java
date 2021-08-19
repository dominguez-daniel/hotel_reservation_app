package Service;

import Helper.Helper;
import model.Customer;
import model.IRoom;
import model.Reservation;

import java.util.*;
import java.util.stream.Collectors;

public class ReservationService {
    public static ReservationService reservationService = null;
    private final Map<String, IRoom> rooms = new HashMap<>();
    private final Map<String, List<Reservation>> reservations = new HashMap<>();
    Helper helper = Helper.getInstance();


    public static ReservationService getInstance() {
        if (reservationService == null) {
            reservationService = new ReservationService();
        }
        return reservationService;
    }

    public void addRoom(IRoom room) throws Exception {
        if (rooms.containsKey(room.getRoomNumber())) {
            throw new Exception("Room already exists");
        }
        rooms.put(room.getRoomNumber(), room);
    }

    public IRoom getARoom(String roomId) {
        return rooms.get(roomId);
    }

    public Collection<IRoom> getAllRooms() {
        return rooms.values();
    }

    public Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
        Reservation reservation = new Reservation(customer, room, (checkInDate), checkOutDate);

        if (reservations.containsKey(customer.getEmail())) {
            reservations.get(customer.getEmail()).add(reservation);
        } else {
            List<Reservation> customerReservations = new ArrayList<>();
            customerReservations.add(reservation);
            reservations.put(customer.getEmail(), customerReservations);
        }

        return reservation;
    }

    public Collection<Reservation> getCustomersReservations(Customer customer) {
        return reservations.get(customer.getEmail());
    }

    public Collection<IRoom> findRooms(Date checkInDate, Date checkOutDate) {
        return this.searchRooms(checkInDate, checkOutDate);
    }

    public void printAllReservations() {
        Collection<Reservation> reservationCollection = reservations
                .values()
                .stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        if (!reservationCollection.isEmpty()) {
            System.out.println("*Reservations");
            for (Reservation reservation : reservationCollection) {
                System.out.println(reservation.toString(this.helper));
            }
        } else {
            System.out.println("There are currently no reservations");
        }
    }

    /**
     * Gets all reservations and performs a search based on room availability.
     * @return - clone of rooms minus unavailable rooms.
     */
        Collection<IRoom> searchRooms(Date checkInDate, Date checkOutDate) {
        HashMap<String, IRoom> roomsCloneMap = (HashMap<String, IRoom>) ((HashMap<String, IRoom>) rooms).clone();

        reservations
                .values()
                .stream()
                .flatMap(Collection::stream)
                .forEach(reservation -> {
                    boolean isAvailable = this.isAvailable(
                            checkInDate, checkOutDate, reservation.getCheckInDate(), reservation.getCheckOutDate()
                    );

                    if (!isAvailable) {
                        roomsCloneMap.remove(reservation.getRoom().getRoomNumber());
                    }
                });
        return roomsCloneMap.values();
    }

    /**
     * Checks if a date is in range of two other dates.
     */
    private boolean isDateInRange(Date proposedDate, Date resCheckIn, Date resCheckOut) {
        return (proposedDate.after(resCheckIn) || proposedDate.equals(resCheckIn)) && proposedDate.before(resCheckOut) || proposedDate.equals(resCheckIn);
    }

    /**
     * Check if the proposed dates are in range of the reservation dates AND checks if the
     * reservation dates are in range of the proposed.
     */
    private boolean isAvailable(Date proposedCheckIn, Date proposedCheckOut, Date resCheckIn, Date resCheckOut) {
        return !((isDateInRange(proposedCheckIn, resCheckIn, resCheckOut) || isDateInRange(proposedCheckOut, resCheckIn, resCheckOut)) || (isDateInRange(resCheckIn, proposedCheckIn, proposedCheckOut) || isDateInRange(resCheckOut, proposedCheckIn, proposedCheckOut)));
    }
}
