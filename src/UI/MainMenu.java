package UI;

import Shared.SharedResource;
import api.HotelResource;
import model.Reservation;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

public class MainMenu {
    HotelResource hotelResource = new HotelResource();
    String emailRegex = "^(.+)@(.+).com$";
    Pattern emailPattern = Pattern.compile(emailRegex);
    Scanner hotelScanner = new Scanner(System.in);
    SharedResource sharedResource = new SharedResource();
    SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd, yyyy");

    static Map<String, String> mainMenuOptionsMap = new HashMap<>() {{
        put("1", "Find and reserve a room");
        put("2", "See my reservations");
        put("3", "Create an Account");
        put("4", "Admin");
        put("5", "Exit");
    }};

    public void getMainMenu() {
        System.out.println("--------------------------------------");
        for (int i = 0; i < mainMenuOptionsMap.values().size(); i++) {
            String key = (i + 1) + "";
            System.out.println(key +". " + mainMenuOptionsMap.get(key));
        }
        System.out.println("--------------------------------------");
        System.out.println("Please select a number for the menu option");
    }

    public String selectOption(String option) {
        String output = option;
        if (!mainMenuOptionsMap.containsKey(option)) {
            output = "-1";
        }
        return output;
    }

    public void createAnAccount() {
        sharedResource.createUserAccount();
    }

    public void findAndReserveRoom() {
        sharedResource.findAndReserveRoom();
    }

    public void seeAllMyReservations() {
        boolean askQuestion = false;

        askQuestion = true;
        while(askQuestion) {
            System.out.println("Please enter account email. Press 1 to exit.");
            String userInput = hotelScanner.nextLine();
            if (userInput.equals("1")) {
                askQuestion = false;
            } else {
                if (emailPattern.matcher(userInput).matches() && hotelResource.getCustomer(userInput) != null) {
                    askQuestion = false;
                    Collection<Reservation> customerReservations = hotelResource.getCustomerReservations(userInput);
                    if (customerReservations == null || customerReservations.isEmpty()) {
                        System.out.println("You currently have no reservations");
                    } else {
                        System.out.println("*Your Reservations*");
                        for (Reservation reservation : customerReservations) {
                            System.out.println(
                                    "Room #" + reservation.getRoom().getRoomNumber() + ", First Name: " + reservation.getCustomer().getFirstName() + ", Last Name: " + reservation.getCustomer().getLastName() +
                                            ", Check In: " + sdf.format(reservation.getCheckInDate()) + ", Check Out: " + sdf.format(reservation.getCheckOutDate()) + ", Price: " +
                                            reservation.getRoom().getRoomPrice()
                            );
                        }
                    }
                } else {
                    System.out.println("*Email not found*");
                }
            }
        }
    }
}
