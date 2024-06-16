package com.trippergoplus.backend.activity.model;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "travelactivity")
@Component
public class Activity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "TourID")
	private int tourID;

	@OneToMany(mappedBy = "activity", cascade = CascadeType.ALL)
	private List<ActivityImage> activityImages;

	@Column(name = "Tourname")
	private String tourName;

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Activity [activityImages=");
		builder.append(activityImages);
		builder.append("]");
		return builder.toString();
	}

	@Column(name = "Tourcategory")
	private String tourCategory;

	@Column(name = "Tourdescription")
	private String tourDescription;

	@Column(name = "Includeditems")
	private String includedItems;

	@Column(name = "Excludeditems")
	private String excludedItems;

	@Column(name = "Restrictions")
	private String restrictions;

	@Column(name = "Additionalinformation")
	private String additional;

	@Column(name = "Maxparticipants")
	private int maxParticipants;
	@OneToOne

	@JoinColumn(name = "LocationID")
//	@Column(name = "LocationID")
	private ActivityLocation location;

	@Column(name = "Fulladdress")
	private String fullAddress;

	@Column(name = "Durationhours")
	private int durationhours;

	@Column(name = "Price")
	private int price;

	@Column(name = "Discount")
	private int discount;

	@Column(name = "Validfrom")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate validFrom;

	@Column(name = "Validto")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate validTo;

	public int getTourID() {
		return tourID;
	}

	public void setTourID(int tourID) {
		this.tourID = tourID;
	}

	public String getTourName() {
		return tourName;
	}

	public void setTourName(String tourName) {
		this.tourName = tourName;
	}

	public String getTourCategory() {
		return tourCategory;
	}

	public void setTourCategory(String tourCategory) {
		this.tourCategory = tourCategory;
	}

	public String getTourDescription() {
		return tourDescription;
	}

	public void setTourDescription(String tourDescription) {
		this.tourDescription = tourDescription;
	}

	public String getIncludedItems() {
		return includedItems;
	}

	public void setIncludedItems(String includedItems) {
		this.includedItems = includedItems;
	}

	public String getExcludedItems() {
		return excludedItems;
	}

	public void setExcludedItems(String excludedItems) {
		this.excludedItems = excludedItems;
	}

	public String getRestrictions() {
		return restrictions;
	}

	public void setRestrictions(String restrictions) {
		this.restrictions = restrictions;
	}

	public String getAdditional() {
		return additional;
	}

	public void setAdditional(String additional) {
		this.additional = additional;
	}

	public int getMaxParticipants() {
		return maxParticipants;
	}

	public void setMaxParticipants(int maxParticipants) {
		this.maxParticipants = maxParticipants;
	}

	public ActivityLocation getLocation() {
		return location;
	}

	public void setLocation(ActivityLocation location) {
		this.location = location;
	}

	public String getFullAddress() {
		return fullAddress;
	}

	public void setFullAddress(String fullAddress) {
		this.fullAddress = fullAddress;
	}

	public int getDurationhours() {
		return durationhours;
	}

	public void setDurationhours(int durationhours) {
		this.durationhours = durationhours;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getDiscount() {
		return discount;
	}

	public void setDiscount(int discount) {
		this.discount = discount;
	}

	public LocalDate getValidFrom() {
		return validFrom;
	}

	public void setValidFrom(LocalDate validFrom) {
		this.validFrom = validFrom;
	}

	public LocalDate getValidTo() {
		return validTo;
	}

	public void setValidTo(LocalDate validTo) {
		this.validTo = validTo;
	}

	public void setActivityImages(List<ActivityImage> activityImages) {

	}

	public List<ActivityImage> getActivityImages() {
		return activityImages;
	}

}