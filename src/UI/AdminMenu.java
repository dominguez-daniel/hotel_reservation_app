package UI;

import Helper.Helper;
import Shared.SharedResource;
import api.AdminResource;
import model.Customer;
import model.IRoom;
import model.RoomType;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;


public class AdminMenu {
    static AdminResource adminAPI = new AdminResource();
    private final String numbersOnlyRegex = "^(\\d+)";
    private final Pattern numberPattern = Pattern.compile(numbersOnlyRegex);
    String emailRegex = "^(.+)@(.+).com$";
    Pattern emailPattern = Pattern.compile(emailRegex);
    Helper helper = new Helper();
    SharedResource sharedResource = new SharedResource();

    Scanner adminScanner = new Scanner(System.in);

     static Map<String, String> adminMenuOptionsMap = new HashMap<>() {{
        put("1", "See all Customers");
        put("2", "See all Rooms");
        put("3", "See all Reservations");
        put("4", "Add a Room");
        put("5", "Add Test Data");
        put("6", "Back to Main Menu");
    }};

    public void getAdminMenu() {
        System.out.println("Admin Menu");
        System.out.println("--------------------------------------");
        for (int i = 0; i < adminMenuOptionsMap.values().size(); i++) {
            String key = (i + 1) + "";
            System.out.println(key +". " + adminMenuOptionsMap.get(key));
        }
        System.out.println("--------------------------------------");
        System.out.println("Please select a number for the menu option");
    }

    public String selectOption(String option) {
        String output = mapInputToMenuItem(option);
        if (!adminMenuOptionsMap.containsKey(option)) {
            output = "-1";
        }

        return output;
    }

    private String mapInputToMenuItem(String input) {
            String output = switch (input) {
                case "1" -> "6";
                case "2" -> "7";
                case "3" -> "8";
                case "4" -> "9";
                case "5" -> "10";
                case "6" -> "11";
                default -> throw new IllegalStateException("Unexpected value: " + input);
            };
        return output;
    }

    public void seeAllRooms() {
        Collection<IRoom> rooms = adminAPI.getAllRooms();
        if (rooms.isEmpty()) {
            System.out.println("There are no rooms to display");
        } else {
            for (IRoom room : rooms) {
                System.out.println("Room: " + room.getRoomNumber() + ", Price: " + room.getRoomPrice() + ", Type: " + room.getRoomType());
            }
        }
    }

    public void addARoom() {
       boolean addRoom = true;

       while (addRoom) {
           boolean askQuestion;
           String roomNumber = null;
           Double price = null;
           RoomType roomType = null;

           System.out.println("Enter room number");
           askQuestion = true;
           while (askQuestion) {
               roomNumber = adminScanner.nextLine();

              if (!numberPattern.matcher(roomNumber).matches()) {
                  System.out.println("Please enter valid room number");
              } else {
                  askQuestion = false;
              }
           }

           System.out.println("Enter price per night");
           askQuestion = true;
           while (askQuestion) {
               String priceInput = adminScanner.nextLine();

               if (!numberPattern.matcher(priceInput).matches()) {
                   System.out.println("Please enter valid room price");
               } else {
                   price = Double.parseDouble(priceInput);
                   askQuestion = false;
               }
           }

           System.out.println("Enter room type: 1 for single bed, 2 for double bed");
           askQuestion = true;
           while (askQuestion) {
               String roomTypeInput = adminScanner.nextLine();

               if (roomTypeInput.equals("1") || roomTypeInput.equals("2")) {
                   roomType = mapInputToRoomType(roomTypeInput);
                   askQuestion = false;
               } else {
                   System.out.println("*Please select a valid room type: 1 for single bed, 2 for double bed*");
               }
           }

           try {
               adminAPI.addRoom(roomNumber, price, roomType);
           } catch (Exception ex) {
               System.out.println(ex.getLocalizedMessage());
           }

           askQuestion = true;
           String answer = null;
           while (askQuestion) {
               System.out.println("Would you like to add another room (y/n)");
               answer = adminScanner.nextLine();
                if (answer.equalsIgnoreCase("y") || answer.equalsIgnoreCase("n")) {
                    askQuestion = false;
                }
           }
           addRoom = helper.handleYesOrNoOption(answer);
       }
    }

    public void seeAllReservations() {
        adminAPI.displayAllReservations();
    }

    private RoomType mapInputToRoomType(String input) {
        RoomType output = switch (input) {
            case "1" -> RoomType.SINGLE;
            case "2" -> RoomType.DOUBLE;
            default -> throw new IllegalStateException("Unexpected value: " + input);
        };
        return output;
    }

    public void seeAllCustomers() {
        Collection<Customer> customers = adminAPI.getAllCustomers();

        if (customers.isEmpty()) {
            System.out.println("No customers to display");
        } else {
            System.out.println("Customers:");
            for (Customer customer : customers) {
                System.out.println("Last Name: " + customer.getLastName() + ", First Name: " + customer.getFirstName() + ", Email: " + customer.getEmail());
            }
        }
    }

    public void addTestData() {
        boolean askQuestion = false;

        askQuestion = true;
        while (askQuestion) {
            System.out.println("What kind of data would you like to enter? (1)Customer, (2)Reservation");
            String userInput = adminScanner.nextLine();

           if (userInput.equals("1") || userInput.equals("2")) {
               askQuestion = false;
               if (userInput.equals("1")) {
                   System.out.println("*Mock Customer*");
                   sharedResource.createUserAccount();
               } else {
                   boolean askForEmail = true;
                   while (askForEmail) {
                       System.out.println("Test Customer required - Please enter test email. (1)Exit");
                       String testEmail = adminScanner.nextLine();
                        if (testEmail.equals("1")) {
                            askForEmail = false;
                        }
                       if (emailPattern.matcher(testEmail).matches() && adminAPI.getCustomer(testEmail) != null) {
                           askForEmail = false;
                           System.out.println("*Create mock Room*");
                           addARoom();
                           System.out.println("*Create mock Reservation*");
                           sharedResource.findAndReserveRoom();
                       } else {
                           System.out.println("*Invalid entry*");
                       }
                   }

               }
           } else {
               System.out.println("*Please enter valid test data option*");
           }
        }

    }
}
