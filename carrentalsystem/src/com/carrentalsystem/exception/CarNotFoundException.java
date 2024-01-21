package com.carrentalsystem.exception;
import com.carrentalsystem.dao.*;
public class CarNotFoundException extends Exception{
	
	public CarNotFoundException(int carID){
		System.out.println(carID+" not found");
	}
/*public void getMessage()
{
	System.out.println("CarID not valid"); 
			
}
*/
}