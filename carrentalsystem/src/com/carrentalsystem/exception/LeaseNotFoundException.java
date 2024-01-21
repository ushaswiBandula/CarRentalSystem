package com.carrentalsystem.exception;

public class LeaseNotFoundException extends Exception {
	/*public void getMessage()
	{
		System.out.println("LeaseID not valid");
				
	}*/
	public LeaseNotFoundException(int LeaseID) {
		System.out.println(LeaseID +"LeaseID is not valid");
	}
}
