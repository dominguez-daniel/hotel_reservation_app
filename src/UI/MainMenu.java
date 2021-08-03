package UI;

import api.HotelResource;
import model.RoomType;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;

public class MainMenu {
    HotelResource hotelResource = new HotelResource();
    String emailRegex = "^(.+)@(.+).com$";
    Pattern emailPattern = Pattern.compile(emailRegex);
    String nameRegex = "^[A-Za-z, ]++$";
    Pattern namePattern = Pattern.compile(nameRegex);
    Scanner hotelScanner = new Scanner(System.in);

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
        String email = null;
        String firstName = null;
        String lastName = null;
        boolean askQuestion;


        askQuestion = true;
        while (askQuestion) {
            System.out.println("Enter email in format name@email.com");
            email = hotelScanner.nextLine();
            // UI validation as well as backend.
            if (emailPattern.matcher(email).matches()) {
                askQuestion = false;
            }
        }

        askQuestion = true;
        while (askQuestion) {
            System.out.println("First Name");
            firstName = hotelScanner.nextLine();
            if (namePattern.matcher(firstName).matches()) {
                askQuestion = false;
            }
        }

        askQuestion = true;
        while (askQuestion) {
            System.out.println("Last Name");
            lastName = hotelScanner.nextLine();
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
