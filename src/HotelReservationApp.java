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

        mainMenu.getMainMenu(); // main menu init

        while (!exit && scanner.hasNext()) {
            try {
                String userInput = HotelReservationApp.parseUserInput(menuType, scanner.nextLine());

                switch (userInput) {
                    // USER OPTIONS(1-5)
                    case "1": // Find and Reserve a Room
                        mainMenu.findAndReserveRoom();
                        break;
                    case "2": // See my Reservations
                        mainMenu.seeAllMyReservations();
                        break;
                    case "3": // Create an Account
                        mainMenu.createAnAccount();
                        break;
                    case "4": // Admin
                        menuType = "ADMIN";
                        break;
                    case "5": // Exit
                        exit = true;
                        break;

                    // ADMIN OPTIONS(1-6)
                    case "6": // 1 See all Customers
                        adminMenu.seeAllCustomers();
                        break;
                    case "7": // 2 See all Rooms
                        adminMenu.seeAllRooms();
                        break;
                    case "8": // 3 See all Reservations
                        adminMenu.seeAllReservations();
                        break;
                    case "9": // 4 Add a Room
                        adminMenu.addARoom();
                        break;
                    case "10": // 5 Add Test Data
                        adminMenu.addTestData();
                        break;
                    case "11": // 6 Back to Main Menu
                        menuType = "MAIN";
                        break;
                    default:
                        System.out.println("Please select an option from the menu");
                }

                if (menuType.equals("MAIN")) {
                    mainMenu.getMainMenu();
                } else {
                    adminMenu.getAdminMenu();
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
