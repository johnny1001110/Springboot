package com.trippergoplus.backend.activity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trippergoplus.backend.activity.model.ActivityLocation;


@Repository
public interface ActivityLocationRepository extends JpaRepository<ActivityLocation, Integer> {
}