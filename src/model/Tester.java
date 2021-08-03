package model;

public class Tester {

    public static void main(String args[]) {
        Customer c = new Customer("Daniel", "Dom", "ddom@gmail.com");
        System.out.println(c.toString());

        Customer o;
        try {
            o = new Customer("f", "l", "email");
            System.out.println(o.toString());
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getLocalizedMessage());
        }
    }

}
