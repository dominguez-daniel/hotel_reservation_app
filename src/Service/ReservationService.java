package Service;

import model.Customer;
import model.IRoom;
import model.Reservation;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class ReservationService {
    static Map<String, IRoom> rooms = new HashMap<>();
    static Map<String, List<Reservation>> reservations = new HashMap<>();

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
        Collection<IRoom> availableRooms = this.searchRooms(checkInDate, checkOutDate);

        if (availableRooms.isEmpty()) {
            return this.getRecommendedRooms(checkInDate, checkOutDate);
        }

        return availableRooms;
    }

    public void printAllReservations() {
        Collection<Reservation> reservationCollection = reservations
                .values()
                .stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        for (Reservation reservation : reservationCollection) {
            System.out.println("Reservation: " + reservation.toString(this));
        }
    }

    /**
     * Gets all reservations and performs a search based on room availability.
     * @return - clone of rooms minus unavailable rooms.
     */
    private Collection<IRoom> searchRooms(Date checkInDate, Date checkOutDate) {
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

    private Collection<IRoom> getRecommendedRooms(Date checkInDate, Date checkOutDate) {
        Calendar cal = Calendar.getInstance();
        Date[] dates = {checkInDate, checkOutDate};

       for (int i = 0; i < dates.length; i++) {
           cal.setTime(dates[i]);
           cal.add(Calendar.DAY_OF_MONTH, 7);
           dates[i] = cal.getTime();
       }

        Collection<IRoom> recommendedRooms = this.searchRooms(dates[0], dates[1]);
        if (!recommendedRooms.isEmpty()) {
            System.out.println("RECOMMENDED ROOMS available " + this.formatDate(dates[0]) + " & " + this.formatDate(dates[1]));
            return recommendedRooms;
        }

        return recommendedRooms;
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

    public String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyy");
         return sdf.format(date);
    }
}
