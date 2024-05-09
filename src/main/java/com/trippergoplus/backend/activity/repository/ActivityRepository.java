package com.trippergoplus.backend.activity.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.trippergoplus.backend.activity.model.Activity;
import com.trippergoplus.backend.activity.model.ActivityLocation;

public interface ActivityRepository extends JpaRepository<Activity, Integer> {

	List<Activity> findByLocation(ActivityLocation location);

	List<Activity> findByTourCategory(String tourCategory);

	List<Activity> findByLocationAndValidFromBetween(ActivityLocation location, LocalDate validfrom, LocalDate validto);

	List<Activity> findByTourCategoryIn(List<String> tourCategories);

	List<Activity> findByPriceBetween(int minPrice, int maxPrice);

	List<Activity> findByTourCategoryInAndPriceBetween(List<String> tourCategories, int minPrice, int maxPrice);

	@Query("SELECT a FROM Activity a WHERE a.location = :location AND a.validFrom >= :validfrom AND a.validTo <= :validto")
	List<Activity> findByLocationAndValidFromAndValidToBetween(@Param("location") ActivityLocation location,
			@Param("validfrom") LocalDate validfrom, @Param("validto") LocalDate validto);

	List<Activity> findByTourCategoryInAndPriceBetweenAndLocationAndValidFromLessThanEqualAndValidToGreaterThanEqual(
			List<String> tourCategories, int minPrice, int maxPrice, ActivityLocation location, LocalDate validto,
			LocalDate validfrom);

	List<Activity> findByTourCategoryInAndLocationAndValidFromLessThanEqualAndValidToGreaterThanEqual(
			List<String> tourCategories, ActivityLocation location, LocalDate validto, LocalDate validfrom);

	List<Activity> findByPriceBetweenAndLocationAndValidFromLessThanEqualAndValidToGreaterThanEqual(int minPrice,
			int maxPrice, ActivityLocation location, LocalDate validto, LocalDate validfrom);
}