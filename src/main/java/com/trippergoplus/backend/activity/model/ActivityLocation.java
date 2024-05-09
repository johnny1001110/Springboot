package com.trippergoplus.backend.activity.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "location")
public class ActivityLocation {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "LocationID")
	private int locationID;

	@Column(name = "City")
	private String city;

	@Override
	public String toString() {
		return city;
	}

	@OneToMany(mappedBy = "location", cascade = CascadeType.ALL)
	private List<Activity> travelActivities;

	public int getLocationID() {
		return locationID;
	}

	public void setLocationID(int locationID) {
		this.locationID = locationID;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public List<Activity> getTravelActivities() {
		return travelActivities;
	}

	public void setTravelActivities(List<Activity> travelActivities) {
		this.travelActivities = travelActivities;
	}
}
