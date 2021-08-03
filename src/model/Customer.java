package model;

import java.util.regex.Pattern;

public class Customer {
    private final String firstName;
    private final String lastName;
    private final String email;

    public Customer(String firstName, String lastName, String email) {
        String emailRegex = "^(.+)@(.+).com$";
        Pattern emailPattern = Pattern.compile(emailRegex);

        // if email does not match compiled regex, throw error
        if (!emailPattern.matcher(email).matches()) {
            throw new IllegalArgumentException("Invalid email format...");
        }

       this.firstName = firstName;
       this.lastName = lastName;
       this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "firstName: " + this.firstName
                + ", lastName: " + this.lastName
                + ", email: " + this.email;
    }
}
