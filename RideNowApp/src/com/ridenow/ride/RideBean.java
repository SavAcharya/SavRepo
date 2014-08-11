package com.ridenow.ride;

public class RideBean {

	public RideBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RideBean(String rideFrom, String rideTo, String rideDate,
			String rideTime, int seats, int privacyType, int rideType) {
		super();
		this.rideFrom = rideFrom;
		this.rideTo = rideTo;
		this.rideDate = rideDate;
		this.rideTime = rideTime;
		this.seats = seats;
		this.privacyType = privacyType;
		this.rideType = rideType;
	}
	
	private String rideFrom;
	private String rideTo;
	private String rideDate;
	private String rideTime;
	private int seats;
	private int privacyType;
	private int rideType;

	public String getRideFrom() {
		return rideFrom;
	}

	public void setRideFrom(String rideFrom) {
		this.rideFrom = rideFrom;
	}

	public String getRideTo() {
		return rideTo;
	}

	public void setRideTo(String rideTo) {
		this.rideTo = rideTo;
	}

	public String getRideDate() {
		return rideDate;
	}

	public void setRideDate(String rideDate) {
		this.rideDate = rideDate;
	}

	public String getRideTime() {
		return rideTime;
	}

	public void setRideTime(String rideTime) {
		this.rideTime = rideTime;
	}

	public int getSeats() {
		return seats;
	}

	public void setSeats(int seats) {
		this.seats = seats;
	}

	public int getPrivacyType() {
		return privacyType;
	}

	public void setPrivacyType(int privacyType) {
		this.privacyType = privacyType;
	}

	public int getRideType() {
		return rideType;
	}

	public void setRideType(int rideType) {
		this.rideType = rideType;
	}

}
