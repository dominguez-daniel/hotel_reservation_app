package model;

import Helper.Helper;
import Service.ReservationService;

import java.util.Date;

public class Reservation {
    private Customer customer;
    private IRoom room;
    private Date checkInDate;
    private Date checkOutDate;

    public Reservation(Customer customer, IRoom room, Date checkIn, Date checkOut) {
        super();
        this.customer = customer;
        this.room = room;
        this.checkInDate = checkIn;
        this.checkOutDate = checkOut;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public IRoom getRoom() {
        return room;
    }

    public void setRoom(IRoom room) {
        this.room = room;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(Date checkInDate) {
        this.checkInDate = checkInDate;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(Date checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public String toString(Helper helper) {
        return "customer: " + this.customer
                + ", room: " + this.room
                + ", checkIn: " + helper.formatDate(this.checkInDate)
                + ", checkOut: " + helper.formatDate(this.checkOutDate);
    }
}
