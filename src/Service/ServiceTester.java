package Service;

import api.HotelResource;

public class ServiceTester {
    public static void main(String[] args) throws Exception {

        HotelResource hs = new HotelResource();

        hs.createACustomer("dd@email.com", "Daniel", "Dom");
        hs.createACustomer("ff@email.com", "Frank", "Lo");
        hs.createACustomer("ss@email.com", "Spencer", "So");


        System.out.println(hs.getCustomer("dd@email.com").toString());

    }
}
