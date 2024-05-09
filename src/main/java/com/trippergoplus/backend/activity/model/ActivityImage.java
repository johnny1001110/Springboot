package com.trippergoplus.backend.activity.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "activityimage")
public class ActivityImage {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ImageID")
	private int imageID;

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ActivityImage [imagepath=");
		builder.append(imagepath);
		builder.append("]");
		return builder.toString();
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TourID")
	private Activity activity;

	@Column(name = "Imagepath")
	private String imagepath;

	public ActivityImage() {
	}

	public int getImageID() {
		return imageID;
	}

	public void setImageID(int imageID) {
		this.imageID = imageID;
	}

	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}

	public String getImagepath() {
		return imagepath;
	}

	public void setImagepath(String imagepath) {
		this.imagepath = imagepath;
	}

	public void setImagePath(byte[] imageData) {
		// TODO Auto-generated method stub

	}

}
