import UI.AdminMenu;
import UI.MainMenu;

import java.util.Scanner;

public class HotelReservationApp {

    static MainMenu mainMenu = new MainMenu();
    static AdminMenu adminMenu = new AdminMenu();


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;
        String menuType = "MAIN";

        mainMenu.getMainMenu();

        while (!exit && scanner.hasNext()) {
            try {
                String userInput = HotelReservationApp.parseUserInput(menuType, scanner.nextLine());

                switch (userInput) {
                    // USER OPTIONS(1-5)
                    case "1": // Find and Reserve a Room
                        System.out.println("Find and reserve a room");
                        break;
                    case "2": // See my Reservations
                        System.out.println("See my reservations");
                        break;
                    case "3": // Create an Account
                        mainMenu.createAnAccount();
                        mainMenu.getMainMenu();
                        break;
                    case "4": // Admin
                        menuType = "ADMIN";
                        adminMenu.getAdminMenu();
                        break;
                    case "5": // Exit
                        exit = true;
                        break;

                    // ADMIN OPTIONS(1-6)
                    case "6": // 1 See all Customers
                        adminMenu.seeAllCustomers();
                        adminMenu.getAdminMenu();
                        break;
                    case "7": // 2 See all Rooms
                        adminMenu.seeAllRooms();
                        adminMenu.getAdminMenu();
                        break;
                    case "8": // 3 See all Reservations
                        System.out.println("See all reservations");
                        break;
                    case "9": // 4 Add a Room
                        adminMenu.addARoom();
                        adminMenu.getAdminMenu();
                        break;
                    case "10": // 5 Add Test Data
                        System.out.println("add test data");
                        break;
                    case "11": // 6 Back to Main Menu
                        menuType = "MAIN";
                        mainMenu.getMainMenu();
                        break;
                    default:
                        System.out.println("Please select an option from the menu");
                }
            } catch (Exception ex) {
                System.out.println(ex.getLocalizedMessage());
            }
        }
    }

    public static String parseUserInput(String menuType, String userInput) {
        String output = switch (menuType) {
            case "MAIN" -> mainMenu.selectOption(userInput);
            case "ADMIN" -> adminMenu.selectOption(userInput);
            default -> throw new IllegalStateException("Unexpected value: " + menuType);
        };
        return output;
    }
}
