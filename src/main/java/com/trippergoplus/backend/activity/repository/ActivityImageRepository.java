package com.trippergoplus.backend.activity.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trippergoplus.backend.activity.model.ActivityImage;


public interface ActivityImageRepository extends JpaRepository<ActivityImage, Integer> {

}