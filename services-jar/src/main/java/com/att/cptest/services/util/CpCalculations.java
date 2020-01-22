package com.att.cptest.services.util;

public class CpCalculations {

	
	public CpCalculations()
	{
		//write something
	}
	
	public String sdndUnplannedMinstoHours(String minutes)
	{
		double roundOff = (double)(Integer.parseInt(minutes))/60;
	    return (String.format("%.2f", roundOff));
	}
}
