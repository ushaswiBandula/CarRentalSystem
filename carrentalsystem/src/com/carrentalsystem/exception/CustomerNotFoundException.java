package com.carrentalsystem.exception;

public class CustomerNotFoundException extends Exception{
	/*public void getMessage()
	{
		System.out.println("CustomerID not valid");
				
	}*/
	public CustomerNotFoundException(int CustomerID){
		System.out.println(CustomerID +"CustomerID is not valid");
	}
}
