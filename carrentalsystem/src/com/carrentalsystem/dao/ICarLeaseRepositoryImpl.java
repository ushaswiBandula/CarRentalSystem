package com.carrentalsystem.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.SQLException; 
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

import com.carrentalsystem.entity.Customer;
import com.carrentalsystem.entity.Lease;
import com.carrentalsystem.entity.LeaseType;
import com.carrentalsystem.entity.Payment;
import com.carrentalsystem.entity.Status;
import com.carrentalsystem.entity.Vehicle;
import com.carrentalsystem.exception.CarNotFoundException;
import com.carrentalsystem.exception.CustomerNotFoundException;
import com.carrentalsystem.exception.LeaseNotFoundException;
import com.carrentalsystem.util.DBConnection;

public class ICarLeaseRepositoryImpl implements ICarLeaseRepository {
	Connection c;
	

	 public ICarLeaseRepositoryImpl()
	 {
		c= DBConnection.getConnection(); 
	 }
	@Override
	public void addCar(Vehicle car) throws SQLException{
		// TODO Auto-generated method stub
				 Statement statement=c.createStatement();
				int vehicleID=car.getVehicleID();
				String make=car.getMake();
				String model=car.getModel();
				int year=car.getYear(); 
				double Rate=car.getDailyRate();
				String status=car.getStatus().toString();
				int passengers=car.getPassengerCapacity();
				double engine=car.getEngineCapacity();
				String query="insert into Vehicle values('"+vehicleID+"','"+make+"','"+model+"','"+year+"','"+Rate+"','"+status+"','"+passengers+"','"+engine+"')";
				try
				{
					statement.executeUpdate(query);
					System.out.println("Record inserted successfully");
				}
				catch(Exception e)
				{
					System.out.println(e.getMessage());
				}
				
	}

	@Override
	public void removeCar(int carID) throws SQLException {  
		// TODO Auto-generated method stub
		 Statement statement=c.createStatement();
		 String query = "DELETE FROM Vehicle WHERE VehicleID = " + carID ; 

        try {
            int rowsAffected = statement.executeUpdate(query);

            if (rowsAffected > 0) {
                System.out.println("Record with carID " + carID + " removed successfully");
            } else {
                System.out.println("No record found with carID " + carID);
            }
        } catch (SQLException e) {
            System.out.println("Error removing record: " + e.getMessage());
        }
    }


	@Override
	public List<Vehicle> listAvailableCars() {
		// TODO Auto-generated method stub
		List<Vehicle> availableCars = new ArrayList<>();
        String query = "SELECT * FROM Vehicle WHERE Status = 'AVAILABLE'";

        try (PreparedStatement statement = c.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int vehicleID = resultSet.getInt("VehicleID");
                String make = resultSet.getString("Make");
                String model = resultSet.getString("Model");
                int year = resultSet.getInt("Year");
                double rate = resultSet.getDouble("dailyRate");
                Status status = Status.valueOf(resultSet.getString("Status").toUpperCase());
                int passengers = resultSet.getInt("PassengerCapacity");
                int engine = resultSet.getInt("EngineCapacity");

                Vehicle car = new Vehicle(vehicleID, make, model, year, rate, status, passengers, engine);
                availableCars.add(car);
            }

        } catch (SQLException e) {
            System.out.println("Error listing available cars: " + e.getMessage());
        }

        return availableCars;
		
	}

	@Override
	public List<Vehicle> listRentedCars() {
		// TODO Auto-generated method stub
		List<Vehicle> UnavailableCars = new ArrayList<>();
        String query = "SELECT * FROM Vehicle WHERE Status = 'UNAVAILABLE'";

        try (PreparedStatement statement = c.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int vehicleID = resultSet.getInt("VehicleID");
                String make = resultSet.getString("Make");
                String model = resultSet.getString("Model");
                int year = resultSet.getInt("Year");
                double rate = resultSet.getDouble("dailyRate");
                Status status = Status.valueOf(resultSet.getString("Status").toUpperCase());
                int passengers = resultSet.getInt("PassengerCapacity");
                int engine = resultSet.getInt("EngineCapacity");

                Vehicle car = new Vehicle(vehicleID, make, model, year, rate, status, passengers, engine);
                UnavailableCars.add(car);
            }

        } catch (SQLException e) {
            System.out.println("Error listing Rented cars: " + e.getMessage());
        }

        return UnavailableCars;
	}

	@Override
	public Vehicle findCarById(int carID) throws CarNotFoundException {
		// TODO Auto-generated method stub
		 String query = "SELECT * FROM Vehicle WHERE VehicleID = ?";
	        try (PreparedStatement statement = c.prepareStatement(query)) {
	            statement.setInt(1, carID);   

	            try (ResultSet resultSet = statement.executeQuery()) {
	                if (resultSet.next()) {
	                    // Vehicle found, create and return a Vehicle object
	                    int vehicleID = resultSet.getInt("VehicleID");
	                    String make = resultSet.getString("Make");
	                    String model = resultSet.getString("Model");
	                    int year = resultSet.getInt("Year");
	                    double rate = resultSet.getDouble("dailyRate");
	                    Status status = Status.valueOf(resultSet.getString("Status").toUpperCase());
	                    int passengers = resultSet.getInt("PassengerCapacity");
	                   int engine = resultSet.getInt("EngineCapacity");

	                    return new Vehicle(vehicleID, make, model, year, rate, status, passengers, engine);
	                } else {
	                    throw new CarNotFoundException(carID);
	                }
	            }
	        } catch (SQLException e) {
	            System.out.println("Error finding car by ID: " + e.getMessage());
	            throw new RuntimeException("Error finding car by ID", e);
	        }      
	
	}
	public void updateCar(Vehicle car) throws CarNotFoundException{
			String updateQuery = "UPDATE Vehicle SET  status=? WHERE vehicleID = ?";
	        try (
	             PreparedStatement preparedStatement = c.prepareStatement(updateQuery);
	        ) {
	            // Setting values for the parameters
	             preparedStatement.setString(1, car.getStatus().toString());
	            preparedStatement.setInt(2, car.getVehicleID());
	            // Executing the update
	            int rowsAffected = preparedStatement.executeUpdate();
	            if(rowsAffected==1)
	             System.out.println("car details updated successfully");
	            else
	            	throw new CarNotFoundException(car.getVehicleID());
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }



	@Override
	public void addCustomer(Customer customer) {
		// TODO Auto-generated method stub
		try
		{
		Statement statement=c.createStatement();
		int customerID=customer.getCustomerID();
		String firstName=customer.getFirstName();
	    String lastName=customer.getLastName();
	     String email=customer.getEmail();
	     String phoneNumber=customer.getPhoneNumber();
	     String query="insert into Customer values('"+customerID+"','"+firstName+"','"+lastName+"','"+email+"','"+phoneNumber+"')";
			
		statement.executeUpdate(query);
		System.out.println("Customer added successfully");
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		
	}

	@Override
	public void removeCustomer(int customerID) throws CustomerNotFoundException {
		// TODO Auto-generated method stub
		 String query = "DELETE FROM Customer WHERE CustomerID = ?";
	        try (PreparedStatement statement = c.prepareStatement(query)) {
	            statement.setInt(1, customerID);

	            int rowsAffected = statement.executeUpdate();

	            if (rowsAffected > 0) {
	                System.out.println("Customer with ID " + customerID + " removed successfully");
	            } else {
	               // System.out.println("No customer found with ID " + customerID);
	            	throw new CustomerNotFoundException(customerID);
	            }
	        } catch (SQLException e) {
	            System.out.println("Error removing customer: " + e.getMessage());
	        }
	}

	@Override
	public List<Customer> listCustomers() {
		// TODO Auto-generated method stub
		List<Customer> customers = new ArrayList<>();
        String query = "SELECT * FROM Customer";

        try (PreparedStatement statement = c.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int customerID = resultSet.getInt("CustomerID");
                String firstName = resultSet.getString("FirstName");
                String lastName = resultSet.getString("LastName");
                String email=resultSet.getString("Email");
                String phoneNumber = resultSet.getString("PhoneNumber");
                Customer customer = new Customer(customerID, firstName, lastName,email,phoneNumber);
                customers.add(customer);
            }

        } catch (SQLException e) {
            System.out.println("Error listing customers: " + e.getMessage());
            // Log the exception or take appropriate action
        }

        return customers;
    }


	@Override
	public Customer findCustomerById(int customerID) throws CustomerNotFoundException {
		// TODO Auto-generated method stub
		String query = "SELECT * FROM Customer WHERE CustomerID = ?";
        try (PreparedStatement statement = c.prepareStatement(query)) {
            statement.setInt(1, customerID);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    // Customer found, create and return a Customer object
                    String firstName = resultSet.getString("FirstName");
                    String lastName = resultSet.getString("LastName");
                    String email=resultSet.getString("Email");
                    String phoneNumber = resultSet.getString("PhoneNumber");

                    return new Customer(customerID, firstName, lastName,email,phoneNumber);
                } else {
                    // Customer with the specified ID not found, throw an exception
                    throw new CustomerNotFoundException(customerID);
                }
            }
        } catch (SQLException e) {
            // Handle database-related exceptions
            System.out.println("Error finding customer by ID: " + e.getMessage());
            throw new RuntimeException("Error finding customer by ID", e);
        }
    }
	
	public void updateCustomer(Customer customer ) throws CustomerNotFoundException{
		String updateQuery = "UPDATE Customer SET customerID = ?, firstName = ?, lastName=?, email=?, phoneNumber=?  WHERE customerID = ?";
        try (
             PreparedStatement preparedStatement = c.prepareStatement(updateQuery);
        ) {
            // Setting values for the parameters 
            preparedStatement.setInt(1, customer.getCustomerID());
            preparedStatement.setString(2,customer.getFirstName() );
            preparedStatement.setString(3, customer.getLastName());
            preparedStatement.setString(4, customer.getEmail());
            preparedStatement.setString(5, customer.getPhoneNumber());
            preparedStatement.setInt(6, customer.getCustomerID());


            // Executing the update
            int rowsAffected = preparedStatement.executeUpdate();
            if(rowsAffected==1)
             System.out.println("customer details updated successfully");
            else
            	throw new CustomerNotFoundException(customer.getCustomerID());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

	

	@Override
	public Lease createLease(int carID, int customerID, Date startDate, Date endDate, LeaseType type) {
		// TODO Auto-generated method stub
        String insertLeaseQuery = "INSERT INTO Lease (VehicleID, CustomerID, StartDate, EndDate, Type) VALUES (?, ?, ?, ?, ?)";
        
        try (PreparedStatement insertLeaseStatement = c.prepareStatement(insertLeaseQuery, PreparedStatement.RETURN_GENERATED_KEYS)) {
            insertLeaseStatement.setInt(1, carID);
            insertLeaseStatement.setInt(2, customerID);
            insertLeaseStatement.setDate(3, startDate);
            insertLeaseStatement.setDate(4, endDate);
            insertLeaseStatement.setString(5,type.toString());
            int rowsAffected = insertLeaseStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Lease created successfully");

                // Retrieve the auto-generated lease ID
                try (ResultSet generatedKeys = insertLeaseStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int leaseID = generatedKeys.getInt(1);  
                        return new Lease(leaseID, carID, customerID, startDate, endDate,type);
                    } else {
                        System.out.println("Failed to retrieve auto-generated Lease ID");
                    }
                }
            } else {
                System.out.println("Failed to create lease");
            }
        } catch (SQLException e) {
            System.out.println("Error creating lease: " + e.getMessage());
        }
        return null;
     
    }


	@Override
	public void returnCar(int leaseID) throws LeaseNotFoundException {
		// TODO Auto-generated method stub 
        String query = "SELECT * FROM Lease where leaseID=?" ;

        try (PreparedStatement updateLeaseStatusStatement = c.prepareStatement(query)) {
            updateLeaseStatusStatement.setInt(1, leaseID);

            ResultSet resultSet = updateLeaseStatusStatement.executeQuery();
            if (resultSet.next()) {
            	int leaseid=resultSet.getInt("leaseID");
        	    int vehicleID=resultSet.getInt("vehicleID");
        	   int customerID=resultSet.getInt("customerID");
        	   Date startDate=resultSet.getDate("startDate");
        	    Date endDate=resultSet.getDate("endDate");
        	     LeaseType type=LeaseType.valueOf(resultSet.getString("type").toUpperCase());
        	     
                System.out.println("Car returned successfully");
                System.out.println(leaseid+" "+vehicleID+" "+customerID+" "+startDate+" "+endDate+" "+type);
            } else {
                // If no rows are affected, the lease with the specified ID was not found
                throw new LeaseNotFoundException( leaseID );
            }
        } catch (SQLException e) {
            System.out.println("Error returning car: " + e.getMessage());
        }
	
	}
	public Lease findLeaseById(int leaseId) throws LeaseNotFoundException {
		String query = "SELECT * FROM Lease where leaseID=?" ;

        try (PreparedStatement statement = c.prepareStatement(query)) {
            statement.setInt(1, leaseId);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
            	int leaseid=resultSet.getInt("leaseID");
        	    int vehicleID=resultSet.getInt("vehicleID");
        	   int customerID=resultSet.getInt("customerID");
        	   Date startDate=resultSet.getDate("startDate");
        	    Date endDate=resultSet.getDate("endDate");
        	     LeaseType type=LeaseType.valueOf(resultSet.getString("type").toUpperCase());
        	     
                Lease l = new Lease(leaseid,vehicleID,customerID,startDate,endDate,type);
                return l;
                } else {
                // If no rows are affected, the lease with the specified ID was not found
                throw new LeaseNotFoundException( leaseId );
            }
        } catch (SQLException e) {
            System.out.println("Error returning car: " + e.getMessage());
        }
        return null;
	}

	@Override
	public List<Lease> listActiveLeases() {
		// TODO Auto-generated method stub
		List<Lease> activeleases = new ArrayList<>();
        String query = "SELECT * FROM Lease WHERE CURDATE() BETWEEN startDate AND endDate";
        	

        try (PreparedStatement statement = c.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int leaseID= resultSet.getInt("leaseID");
                int vehicleID = resultSet.getInt("VehicleID");
                int customerID = resultSet.getInt("CustomerID");
               Date startDate=resultSet.getDate("StartDate");
                Date endDate = resultSet.getDate("EndDate");
               LeaseType type = LeaseType.valueOf(resultSet.getString("type").toUpperCase());
                 Lease lease= new Lease(leaseID, vehicleID, customerID, startDate,endDate,type);
                activeleases.add(lease);
            }

        } catch (SQLException e) {
            System.out.println("Error listing active leases: " + e.getMessage());
        }

        return activeleases;
    }


	@Override
	public List<Lease> listLeaseHistory() {
		// TODO Auto-generated method stub
		List<Lease> leaseHistory = new ArrayList<>();
        String query = "SELECT * FROM Lease " ;

        try (PreparedStatement statement = c.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int leaseID= resultSet.getInt("leaseID");
                int vehicleID = resultSet.getInt("VehicleID");
                int customerID = resultSet.getInt("CustomerID");
               Date startDate=resultSet.getDate("StartDate");
                Date endDate = resultSet.getDate("EndDate");
               LeaseType type = LeaseType.valueOf(resultSet.getString("type").toUpperCase());
                 Lease lease= new Lease(leaseID, vehicleID, customerID, startDate,endDate,type);
                leaseHistory.add(lease);
            }

        } catch (SQLException e) {
            System.out.println("Error listing lease History: " + e.getMessage());
        }

        return leaseHistory;
	}

	@Override
	public void recordPayment(Lease lease, double amount) {
		// TODO Auto-generated method stub
String recordPaymentQuery = "INSERT INTO Payment (leaseid, paymentDate, amount) VALUES ( ?, ?, ?)";
        
        try (PreparedStatement recordPaymentStatement = c.prepareStatement(recordPaymentQuery, PreparedStatement.RETURN_GENERATED_KEYS)) {
        	recordPaymentStatement.setInt(1, lease.getLeaseID());
        	recordPaymentStatement.setDate(2, Date.valueOf(LocalDate.now()));
        	recordPaymentStatement.setDouble(3, amount);
            int rowsAffected = recordPaymentStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Payment recorded successfully");

               
            } else {
                System.out.println("Failed to record");
            }
        } catch (SQLException e) {
            System.out.println("Error recording payment: " + e.getMessage());
        }
	}
		public double totalRevenue()
		{
			try {
				Statement s=c.createStatement();
			String query="select sum(amount) from payment";
			ResultSet rs= s.executeQuery(query);
			if(rs.next()) {
				double totalamount=rs.getDouble(1);
				return totalamount;
			}
			}
			catch(SQLException e)
			{
				e.printStackTrace();
			}
			return 0;
		}
		@Override
		public List<Payment> findPaymentsByCustomerID(int customerID) {
			// TODO Auto-generated method stub
			List<Payment>payments=new ArrayList<>();
			try {
				Statement s=c.createStatement();
			String query="select * from Payment p inner join Lease l on p.leaseID=l.leaseID where l.customerID="+customerID;
			ResultSet rs= s.executeQuery(query);
			while(rs.next()) {
				Payment p=new Payment();
				p.setAmount(rs.getDouble("p.amount"));
				p.setLeaseID(rs.getInt("p.leaseID"));
				p.setPaymentDate(rs.getDate("p.paymentDate"));
				p.setPaymentID(rs.getInt("p.paymentID"));
				payments.add(p);
			}
			}
			catch(SQLException e)
			{
				e.printStackTrace();
			}
			return payments;
		}
}


