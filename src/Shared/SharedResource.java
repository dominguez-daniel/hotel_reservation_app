package Shared;

import Helper.Helper;
import Service.ReservationService;
import api.HotelResource;
import model.Customer;
import model.IRoom;
import model.Reservation;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Pattern;

public class SharedResource {
    HotelResource hotelResource = new HotelResource();
    ReservationService reservationService = new ReservationService();
    Scanner scanner = new Scanner(System.in);

    String emailRegex = "^(.+)@(.+).com$";
    Pattern emailPattern = Pattern.compile(emailRegex);
    String nameRegex = "^[A-Za-z, ]++$";
    private final String numbersOnlyRegex = "^(\\d+)";
    private final Pattern numberPattern = Pattern.compile(numbersOnlyRegex);
    String dateRegex = "^[0-3]?[0-9]/[0-3]?[0-9]/[0-9]{4}$";
    Pattern datePattern = Pattern.compile(dateRegex);
    Pattern namePattern = Pattern.compile(nameRegex);
    Collection<IRoom> availableRooms = null;
    Helper helper = new Helper();


    public void findAndReserveRoom() {
        String checkInStr = null;
        String checkOutStr = null;
        boolean bookARoom = false;
        boolean hasAccount = false;
        boolean askQuestion;

        askQuestion = true;
        while (askQuestion) {
            System.out.println("Enter check-in date mm/dd/yyy");
            checkInStr = scanner.nextLine();

            if (datePattern.matcher(checkInStr).matches()) {
                askQuestion = false;
            }
        }

        askQuestion = true;
        while (askQuestion) {
            System.out.println("Enter check-out date mm/dd/yyy");
            checkOutStr = scanner.nextLine();

            if (datePattern.matcher(checkOutStr).matches()) {
                askQuestion = false;
            }
        }

        availableRooms = hotelResource.findARoom(new Date(checkInStr), new Date(checkOutStr));

        if (availableRooms.isEmpty()) {
            Calendar cal = Calendar.getInstance();
            Date[] dates = {new Date(checkInStr), new Date(checkOutStr)};

            for (int i = 0; i < dates.length; i++) {
                cal.setTime(dates[i]);
                cal.add(Calendar.DAY_OF_MONTH, 7);
                dates[i] = cal.getTime();
            }

            Collection<IRoom> recommendedRooms = this.reservationService.searchRooms(dates[0], dates[1]);

            if (!recommendedRooms.isEmpty()) {
                checkInStr = this.helper.formatDate(dates[0]);
                checkOutStr = this.helper.formatDate(dates[1]);
                availableRooms = recommendedRooms;
                System.out.println("Recommended rooms based on new date range: " + checkInStr + " - " + checkOutStr);
            }
        }

        for (IRoom room : availableRooms) {
            System.out.println("Room #" + room.getRoomNumber() + ", Type: " + room.getRoomType() + ", Price: $" + room.getRoomPrice());
        }

        askQuestion = true;
        while (askQuestion) {
            System.out.println("Would you like to book a room? (y/n)");
            String answer = scanner.nextLine();
            if (answer.equalsIgnoreCase("y") || answer.equalsIgnoreCase("n")) {
                askQuestion = false;
                bookARoom = helper.handleYesOrNoOption(answer);
            }
        }

        if (bookARoom) {
            askQuestion = true;
            while (askQuestion) {
                System.out.println("Do you have an account with us? (y/n)");
                String answer = scanner.nextLine();
                if (answer.equalsIgnoreCase("y") || answer.equalsIgnoreCase("n")) {
                    askQuestion = false;
                    hasAccount = helper.handleYesOrNoOption(answer);
                }
            }

            if (hasAccount) {
                askQuestion = true;
                String email = null;

                while (askQuestion) {
                    System.out.println("Enter email in format: name@email.com");
                    email = scanner.nextLine();
                    if (emailPattern.matcher(email).matches()) {
                        askQuestion = false;
                    }
                }

                Customer customer = hotelResource.getCustomer(email);

                if (customer != null) {
                    askQuestion = true;
                    String roomNumber = null;

                    while (askQuestion) {
                        System.out.println("What room would you like to reserve?");
                        roomNumber = scanner.nextLine();

                        if (numberPattern.matcher(roomNumber).matches()) {
                            IRoom availableRoom = hotelResource.getRoom(roomNumber);
                            if (availableRoom != null && availableRooms.contains(availableRoom)) {
                                askQuestion = false;
                                System.out.println("proceed");
                                Reservation reservation = hotelResource.bookARoom(customer.getEmail(), availableRoom, new Date(checkInStr), new Date(checkOutStr));
                                printReservationReceipt(reservation);
                            }
                        } else {
                            System.out.println("*Please enter valid room number*");
                        }
                    }
                } else {
                    System.out.println("*Customer not found*");
                }
            } else {
                System.out.println("*Account is required to book a room. Please create an account.*");
                return;
            }
        } else {
            // end experience with no booking
            return;
        }
    }

    private void printReservationReceipt(Reservation reservation) {
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd, yyyy");
        System.out.println("**Reservation**");
        System.out.println("Name: " + reservation.getCustomer().getLastName() + " " + reservation.getCustomer().getLastName());
        System.out.println("Room: " + reservation.getRoom().getRoomNumber());
        System.out.println("Price: $" + reservation.getRoom().getRoomPrice());
        System.out.println("Check In: " + sdf.format(reservation.getCheckInDate()));
        System.out.println("Check Out: " + sdf.format(reservation.getCheckOutDate()));
    }

    public void createUserAccount() {
        String email = null;
        String firstName = null;
        String lastName = null;
        boolean askQuestion;

        askQuestion = true;
        while (askQuestion) {
            System.out.println("Enter email in format name@email.com");
            email = scanner.nextLine();
            // UI validation as well as backend.
            if (emailPattern.matcher(email).matches()) {
                askQuestion = false;
            }
        }

        askQuestion = true;
        while (askQuestion) {
            System.out.println("First Name");
            firstName = scanner.nextLine();
            if (namePattern.matcher(firstName).matches()) {
                askQuestion = false;
            }
        }

        askQuestion = true;
        while (askQuestion) {
            System.out.println("Last Name");
            lastName = scanner.nextLine();
            if (namePattern.matcher(lastName).matches()) {
                askQuestion = false;
            }
        }
        try {
            hotelResource.createACustomer(email, firstName, lastName);
        } catch (Exception ex) {
            System.out.println(ex.getLocalizedMessage() + ", please try again with different email.");
        }
    }
}
