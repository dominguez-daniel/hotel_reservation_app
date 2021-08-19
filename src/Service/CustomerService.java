package Service;

import model.Customer;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CustomerService {
    public static CustomerService customerService = null;
    private Map<String, Customer> customers = new HashMap<>();

    public static CustomerService getInstance() {
        if (customerService == null) {
            customerService = new CustomerService();
        }
        return customerService;
    }

    public void addCustomer(String email, String firstName, String lastName) throws Exception {
        if (customers.containsKey(email)) {
            throw new Exception("Email already exists");
        }
        customers.put(email, new Customer(firstName, lastName, email));
    }

    public Customer getCustomer(String email) {
        return customers.get(email);
    }

    public Collection<Customer> getAllCustomers() {
        return customers.values();
    }

}
