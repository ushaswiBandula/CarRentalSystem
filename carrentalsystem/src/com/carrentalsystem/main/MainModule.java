package com.carrentalsystem.main;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

import com.carrentalsystem.entity.Customer;
import com.carrentalsystem.entity.Lease;
import com.carrentalsystem.entity.LeaseType;
import com.carrentalsystem.entity.Payment;
import com.carrentalsystem.entity.Status;
import com.carrentalsystem.entity.Vehicle;
import com.carrentalsystem.exception.CarNotFoundException;
import com.carrentalsystem.exception.CustomerNotFoundException;
import com.carrentalsystem.exception.LeaseNotFoundException;
import com.carrentalsystem.dao.*;
public class MainModule {
  static   Scanner sc=new Scanner(System.in); 
  static ICarLeaseRepository repo=new ICarLeaseRepositoryImpl();
	public static void main(String[] args) {
		
		// TODO Auto-generated method stub
		System.out.println("Welcome to  Car Rental System");
		System.out.println("Here are the services you can use in our application");
		System.out.println("1.Car Services");
		System.out.println("2.Customer Services");
		System.out.println("3.Lease Services");
		System.out.println("4.Payment Services");
		System.out.println("5.Exit");
		
		System.out.println("Please Enter a number from above options");
		int option = sc.nextInt();
		switch(option) {
		case 1: carServices();
				break;
		case 2: customerServices();
				break;
		case 3: leaseServices();
				break;
		case 4: paymentServices();
				break;
		case 5: break;
		default : break;
		}
	}
	private static void customerServices() {
		System.out.println("Please select Customer Services from below");
		System.out.println("1.Add Customer");
		System.out.println("2.Remove Customer");
		System.out.println("3.Get all customers details");
		System.out.println("4.Find the customer by id");
		System.out.println("5.update the customer information");
		System.out.println("6.Exit");
		int option = sc.nextInt();
		switch(option) {
		case 1: addCustomer();
				break;
		case 2: removeCustomer();
				break;
		case 3: displayAllCustomers();
				break;
		case 4: getCustomerById();
				break;
		case 5: updateCustInfo();
				break;
		case 6: break;
		default: break;
				
		}
		
	}
	private static void leaseServices() {
		System.out.println("Please select Lease Services from below");
		System.out.println("1.Create lease");
		System.out.println("2.Get car by leaseID");
		System.out.println("3.Get active leases");
		System.out.println("4.Get lease history");
		System.out.println("5.Get total cost of lease");
		System.out.println("6.Exit");

		int option = sc.nextInt();
		switch(option) {
		case 1: createLease();
				break;
		case 2: getCarByLeaseId();
				break;
		case 3: getActiveLeases();
				break;
		case 4: getLeaseHistory();
				break;
		case 5: totalLeaseCost();
				break;
		case 6: break;
		default: break;
				
		}
	}
	private static void paymentServices() {
		System.out.println("Please select payment Services from below");
		System.out.println("1.Record the payment");
		System.out.println("2.Get total revenue:");
		System.out.println("3.payment History: ");
		System.out.println("4.Exit");
		int option = sc.nextInt();
		switch(option) {
		case 1: recordPayment();
				break;
		case 2: totalPaymentRevenue();
				break;
		case 3: paymentHistory();
			break;
		case 4: break;
		default: break;
				
		}
	}

	public static void carServices() {
		System.out.println("Please select Car Services from below");
		System.out.println("1.Add Car");
		System.out.println("2.Remove Car");
		System.out.println("3.Get All Avaialable Cars");
		System.out.println("4.Get Rented Cars List");
		System.out.println("5.Get Specific Car Details");
		System.out.println("6.update car Availability ");
		System.out.println("7.Exit");

		int option = sc.nextInt();
		switch(option) {
		case 1: addCar();
				break;
		case 2: removeCar();
				break;
		case 3: availableVehicles();
				break;
		case 4: rentedVehicles();
				break;
		case 5: getCarById();
				break;
		case 6:updateCar();
			break;
		default: break;
				
		}
	}
	public static void updateCar() {
		// TODO Auto-generated method stub
		System.out.println("Enter the following details:");
		
		System.out.println("Enter vehicleID");
		int vehicleId=sc.nextInt();
		sc.nextLine();
		System.out.println("Enter status: AVAILABLE OR UNAVAILABLE");
		String sts=sc.nextLine().toUpperCase();
		Status status=Status.valueOf(sts);
		try {
			Vehicle car=repo.findCarById(vehicleId);
			car.setStatus(status);
			repo.updateCar(car);
		} catch (CarNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("carID not found");
		}
		
		
	}
	public static void addCar() {
		System.out.println("Enter the following details:");
		
		System.out.println("Enter vehicleID");
		int vehicleId=sc.nextInt();
		sc.nextLine();
		System.out.println("Enter make");
		String make=sc.nextLine();
		System.out.println("Enter model");
		String model=sc.nextLine();
		System.out.println("Enter year");
		int year=sc.nextInt();
		System.out.println("Enter dailyRate");
		int dailyRate=sc.nextInt();
		sc.nextLine();
		System.out.println("Enter status: AVAILABLE OR UNAVAILABLE");
		String sts=sc.nextLine().toUpperCase();
		Status status=Status.valueOf(sts);
		System.out.println("Enter Passenger capacity");
		int passengerCapacity=sc.nextInt();
		System.out.println("Enter engine Capacity ");
		int engineCapacity=sc.nextInt();
		Vehicle car=new Vehicle(vehicleId,make,model,year,dailyRate,status,passengerCapacity,engineCapacity);
		try {
			repo.addCar(car);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(" kindly Enter proper details ");
		}
		
	}
	public static void removeCar() {
		System.out.println("Enter vehicleID");
		int vehicleId=sc.nextInt();
		try {
			repo.removeCar(vehicleId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("CarID not found , cannot be removed");
		}
	}
	public static void availableVehicles(){
		System.out.println("Available vehicles: ");
		List<Vehicle> availableCars = repo.listAvailableCars();
		for(int i=0;i<availableCars.size();i++) {
			System.out.println(availableCars.get(i));
		}
		  
	}

	public static void rentedVehicles(){
		System.out.println("Rented vehicles: ");
		List<Vehicle> rentedCars = repo.listRentedCars();
		for(int i=0;i<rentedCars.size();i++) {
			System.out.println(rentedCars.get(i));
		}
	}
	public static void getCarById() {
		System.out.println("Enter car id to get details:" );
		int carId = sc.nextInt();
		try {
			Vehicle car = repo.findCarById(carId);
			System.out.println(car);
		} catch (CarNotFoundException e) {
			System.out.println("Car Not Found:"+carId);
		}
	}
	public static void addCustomer()
	{
		System.out.println("Enter the following details");
		System.out.println("Enter CustomerID:");
		int customerID=sc.nextInt();
		sc.nextLine();
		System.out.println("Enter first name:");
		String firstName=sc.nextLine();
		System.out.println("Enter last name:");
		String lastName=sc.nextLine();
		System.out.println("Enter email:");
		String email=sc.nextLine();
		System.out.println("Enter phonenumber:");
		String phoneNumber=sc.nextLine();
		Customer c = new Customer(customerID,firstName,lastName,email,phoneNumber);
		repo.addCustomer(c);

	}
	public static void removeCustomer() {
		
		System.out.println("Enter CustomerID to delete:");
		int customerID = sc.nextInt();
		try {
			repo.removeCustomer(customerID);
		} catch (CustomerNotFoundException e) {
			System.out.println("Deletion Failed : Customer ID not found");
		}
		
	}
	public static void displayAllCustomers() {
		System.out.println("List of All Customers: ");
		List<Customer> allCustomers = repo.listCustomers();
		for(int i=0;i<allCustomers.size();i++) {
			System.out.println(allCustomers.get(i));
		}
	}
	public static void getCustomerById() {
		System.out.println("Enter the customer Id to get Details");
		int customerID = sc.nextInt();
		try {
			Customer c  =repo.findCustomerById(customerID);
			System.out.println(c);
		} catch (CustomerNotFoundException e) {
			System.out.println("Customer ID not found");
		}
	}
	public static void createLease() 
	{//leaseID generated automatically
		System.out.println("Enter vehicleID");	
		 int vehicleID=sc.nextInt();
			System.out.println("Enter CustomerID:");
	     int customerID=sc.nextInt();
	     sc.nextLine();

			System.out.println("Enter start date: like yyyy-mm-dd ");
	     String sDate=sc.nextLine();
	     Date startDate=Date.valueOf(sDate);
			System.out.println("Enter end date: like yyyy-mm-dd ");
	    String eDate=sc.nextLine();
	    Date endDate=Date.valueOf(eDate);
		System.out.println("Enter LeaseType:DailyLease or MonthlyLease");
	   String t=sc.nextLine();
		LeaseType type=LeaseType.valueOf(t.toUpperCase());
		//LeaseType type=LeaseType.DAILYLEASE;
		repo.createLease(customerID, vehicleID, startDate, endDate, type);
	}
	public static void getCarByLeaseId() {
		System.out.println("Enter Lease Id:");
		int leaseId = sc.nextInt();
		try {
			repo.returnCar(leaseId);
		} catch (LeaseNotFoundException e) {
			System.out.println("Lease ID Not Found");
		}
	}
	public static void getActiveLeases() {
		List<Lease> activeLeases = repo.listActiveLeases();
		System.out.println("List of Active Leases");
		for(int i=0;i<activeLeases.size();i++) {
			System.out.println(activeLeases.get(i));
		}
	}
	public static void getLeaseHistory() {
		List<Lease> leaseHistory = repo.listLeaseHistory();
		System.out.println("Lease History:");
		for(int i=0;i<leaseHistory.size();i++) {
			System.out.println(leaseHistory.get(i));
		}
	}
	public static void recordPayment() {
		System.out.println("Enter Lease Id:");
		int leaseId  = sc.nextInt();
		System.out.println("Enter amount");
		double amount  = sc.nextDouble();
		try {
			Lease lease = repo.findLeaseById(leaseId);
			repo.recordPayment(lease, amount);
		} catch (LeaseNotFoundException e) {
			System.out.println("Lease ID Not Found");
		}
	}
	
	public static void updateCustInfo()
	{

		System.out.println("Enter the following details");
		System.out.println("Enter CustomerID:");
		int customerID=sc.nextInt();
		sc.nextLine();
		System.out.println("Enter first name:");
		String firstName=sc.nextLine();
		System.out.println("Enter last name:");
		String lastName=sc.nextLine();
		System.out.println("Enter email:");
		String email=sc.nextLine();
		System.out.println("Enter phonenumber:");
		String phoneNumber=sc.nextLine();
		Customer c = new Customer(customerID,firstName,lastName,email,phoneNumber);
		try {
			repo.updateCustomer(c);
		} catch (CustomerNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("Customer id not found, Cant update customer information");
		}

	}
	
	
	public static void totalLeaseCost() 
	{
		System.out.println("Enter lease id");
		int leaseID=sc.nextInt();
		try {
			Lease l1=repo.findLeaseById(leaseID);
			LocalDate startDate=l1.getStartDate().toLocalDate();
			LocalDate endDate=l1.getEndDate().toLocalDate();
			long days=ChronoUnit.DAYS.between(startDate, endDate);
			long months=ChronoUnit.MONTHS.between(startDate, endDate);
			int vehicleID=l1.getVehicleID();
			Vehicle v;
			try {
				v = repo.findCarById(vehicleID);
				double dailyRate=v.getDailyRate();
				double totalcost;
				if(l1.getType().toString().equals("DAILYLEASE")) {
					totalcost=dailyRate*days;
					System.out.println("For daily Lease total lease cost is: "+ totalcost);

				}
				else
				{
					totalcost=dailyRate*months*30;
					System.out.println("For Monthly lease total lease cost is: "+ totalcost);

				}
			} catch (CarNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} catch (LeaseNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void paymentHistory()
	{
		System.out.println("Enter Customer ID");
		int customerID=sc.nextInt();
		List<Payment> payments=repo.findPaymentsByCustomerID(customerID);
			for(int i=0;i<payments.size();i++)
			{
				System.out.println(payments.get(i));
				
			}
	}
	public static void totalPaymentRevenue()
	{
	  double totalRev=repo.totalRevenue();
	  System.out.println("Total revenue of car rental system: "+ totalRev);
	}
}
