package com.carrentalsystem.dao;
import com.carrentalsystem.entity.*;
import com.carrentalsystem.exception.*;

import java.sql.SQLException;
import java.sql.Date;
import java.util.List;

public interface ICarLeaseRepository {
	// Car Management
    void addCar(Vehicle car) throws SQLException;
    void removeCar(int carID) throws SQLException;
    List<Vehicle> listAvailableCars();
    List<Vehicle> listRentedCars();
    Vehicle findCarById(int carID) throws CarNotFoundException;
    void updateCar(Vehicle car) throws CarNotFoundException;

   // Customer Management
    void addCustomer(Customer customer);
    void removeCustomer(int customerID) throws CustomerNotFoundException;
    List<Customer> listCustomers();
    Customer findCustomerById(int customerID) throws CustomerNotFoundException;
    void updateCustomer(Customer customer) throws CustomerNotFoundException;
    

    // Lease Management
    Lease createLease(int carID, int customerID,  Date startDate, Date endDate, LeaseType type);
    void returnCar(int leaseID) throws LeaseNotFoundException;
    List<Lease> listActiveLeases();
    List<Lease> listLeaseHistory();
    Lease findLeaseById(int leaseId) throws LeaseNotFoundException;

    // Payment Handling
    void recordPayment(Lease lease, double amount);
    public double totalRevenue();
	List<Payment> findPaymentsByCustomerID(int customerID);
}
