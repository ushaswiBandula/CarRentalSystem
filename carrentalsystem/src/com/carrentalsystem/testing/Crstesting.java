package com.carrentalsystem.testing;
import static org.junit.Assert.*;

import java.sql.Date;
import java.sql.SQLException;

import org.junit.Test;

import com.carrentalsystem.dao.ICarLeaseRepository;
import com.carrentalsystem.dao.ICarLeaseRepositoryImpl;
import com.carrentalsystem.entity.Lease;
import com.carrentalsystem.entity.LeaseType;
import com.carrentalsystem.entity.Status;
import com.carrentalsystem.entity.Vehicle;
import com.carrentalsystem.exception.CarNotFoundException;
import com.carrentalsystem.exception.CustomerNotFoundException;
import com.carrentalsystem.exception.LeaseNotFoundException;
public class Crstesting {
ICarLeaseRepository repo= new ICarLeaseRepositoryImpl(); 	    
	    @Test
	    public void testCarCreationAndRetrieval() throws SQLException {
	        
	        // Test Case: Car Creation
	    	Status type=Status.UNAVAILABLE;
	        Vehicle v=new Vehicle(226,"hyundai","i22",2009,1200.0,type,4,900);
	        repo.addCar(v);
	        Vehicle retrievedVehicle;
			try {
				retrievedVehicle = repo.findCarById(226);
				//System.out.println(v);
				//System.out.println(retrievedVehicle);
			
		        assertEquals(v.toString(), retrievedVehicle.toString());

			} catch (CarNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	    
	   @Test
	   public void testLeaseCreationAndRetrieval() {
	    	
	    	LeaseType leaseType=LeaseType.MONTHLYLEASE;
	        Lease createdLease=repo.createLease(222,101, Date.valueOf("2023-08-01"),Date.valueOf("2023-08-10"),leaseType);
	        Lease retrievedLease;
	        try {
				retrievedLease=repo.findLeaseById(createdLease.getLeaseID());
				System.out.println(createdLease);
				System.out.println(retrievedLease);
			
		        assertEquals(createdLease.toString(), retrievedLease.toString());
			} catch (LeaseNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	  
	    }

	    @Test(expected = CarNotFoundException.class)
	    public void testExceptionHandlingCustomerIdNotFound() throws CarNotFoundException {		
	    	repo.findCarById(250);

	    }

	    @Test(expected = CustomerNotFoundException.class)
	    public void testExceptionHandlingCarIdNotFound() throws CustomerNotFoundException {
	        repo.findCustomerById(455);
	        }

	    @Test(expected = LeaseNotFoundException.class)
	    public void testExceptionHandlingLeaseIdNotFound() throws LeaseNotFoundException {
	    repo.findLeaseById(1000);   
	    }
	}

