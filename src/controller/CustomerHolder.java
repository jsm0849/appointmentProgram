/**
 * The CustomerHolder class is a helper class which holds a specific instance of itself that is shared between the
 * Main Screen and the update Customer pages.
 *
 * @author Jacob Smith
 */

package controller;

import model.Customer;

public final class CustomerHolder {
    private Customer selectedCustomer;
    private final static CustomerHolder INSTANCE = new CustomerHolder();

    /**
     * The constructor is private so other classes cannot generate a new instance of the class.
     */
    private CustomerHolder() {}
    /**
     * The getInstance method returns the instance of CustomerHolder which holds a particular Customer.
     *
     * @return the instance of CustomerHolder.
     */
    public static CustomerHolder getInstance() {
        return INSTANCE;
    }
    /**
     * The setCustomer method sets the specific Customer held by the instance of CustomerHolder.
     *
     * @param customer the Customer being held by the instance of the class.
     */
    public void setCustomer(Customer customer) {
        this.selectedCustomer = customer;
    }
    /**
     * The getCustomer method returns the specific Customer held by the instance of CustomerHolder.
     *
     * @return the Customer held by the instance of the class.
     */
    public Customer getCustomer() {
        return this.selectedCustomer;
    }
}
