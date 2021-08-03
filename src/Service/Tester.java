package Service;

import jdk.swing.interop.SwingInterOpUtils;
import model.*;

import java.util.Date;

public class Tester {

    public static void main(String args[]) throws Exception {

        ReservationService rs = new ReservationService();
        CustomerService cs = new CustomerService();

        rs.addRoom(new Room("101", 100.0, RoomType.SINGLE));
        rs.addRoom(new Room("102", 100.0, RoomType.DOUBLE));
        rs.addRoom(new Room("103", 100.0, RoomType.SINGLE));
        rs.addRoom(new Room("104", 100.0, RoomType.DOUBLE));
        rs.addRoom(new Room("105", 100.0, RoomType.SINGLE));



        Customer c1 = new Customer("Daniel", "Dom", "dd@email.com");
        Customer c2 = new Customer("Sara", "Sol", "ss@email.com");
        Customer c3 = new Customer("Frank", "Lopez", "fl@email.com");

        rs.reserveARoom(c1, rs.getARoom("101"), new Date("7/2/2020"),  new Date("7/6/2020"));
        rs.reserveARoom(c2, rs.getARoom("102"), new Date("8/5/2020"),  new Date("8/15/2020"));
        rs.reserveARoom(c3, rs.getARoom("103"), new Date("11/1/2020"),  new Date("11/30/2020"));

        Date proposedCheckIn = new Date("8/5/2020");
        Date proposedCheckOut = new Date("12/15/2020");

//        System.out.println("AVAILABLE ROOMS");
//       for (IRoom room : rs.findRooms(proposedCheckIn, proposedCheckOut)) {
//           System.out.println(room.getRoomNumber());
//       }
//
//
//        System.out.println("ALL ROOMS");
//       for (IRoom room : ReservationService.rooms.values()) {
//           System.out.println(room.getRoomNumber());
//       }

    }
}
