package com.trippergoplus.backend.activity.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.trippergoplus.backend.activity.model.Activity;
import com.trippergoplus.backend.activity.model.ActivityImage;
import com.trippergoplus.backend.activity.model.ActivityLocation;
import com.trippergoplus.backend.activity.service.ActivityService;

@Controller
public class ActivityController {

	@Autowired
	private ActivityService aService;

	@GetMapping("/activityquery")
	public String getActivitiesByFiltersAndLocationAndDate(
			@RequestParam(value = "location", required = false) ActivityLocation location,
			@RequestParam(value = "validfrom", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate validfrom,
			@RequestParam(value = "validto", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate validto,
			@RequestParam(value = "tourCategories", required = false) List<String> tourCategories,
			@RequestParam(value = "prices", required = false) List<String> prices, Model model) {

		List<Activity> filteredActivities = new ArrayList<>();

		if (location != null && validfrom != null && validto != null) {
			// 如果提供了地點和日期，則進行地點和日期的查詢
			List<Activity> activitiesByLocationAndDate = aService.findByLocationAndDateRange(location, validfrom,
					validto);
			// 根據其他篩選條件進一步篩選
			if (!activitiesByLocationAndDate.isEmpty()) {
				if ((tourCategories != null && !tourCategories.isEmpty()) || (prices != null && !prices.isEmpty())) {
					// 使用其他篩選條件進一步篩選
					if (tourCategories != null && !tourCategories.isEmpty() && prices != null && !prices.isEmpty()) {
						// 同時根據活動分類和價格範圍進行篩選
						filteredActivities = aService.findByTourCategoriesAndPriceRangeAndLocationAndDate(
								tourCategories, prices, location, validfrom, validto);
					} else if (tourCategories != null && !tourCategories.isEmpty()) {
						// 根據活動分類進行篩選
						filteredActivities = aService.findByTourCategoriesAndLocationAndDate(tourCategories, location,
								validfrom, validto);
					} else {
						// 根據價格範圍進行篩選
						filteredActivities = aService.findByPriceRangeAndLocationAndDate(prices, location, validfrom,
								validto);
					}
				} else {
					// 如果未指定其他篩選條件，則直接使用地點和日期的查詢結果
					filteredActivities = activitiesByLocationAndDate;
				}
			}
		} else {
			// 如果沒有提供地點和日期，則直接根據其他篩選條件進行查詢
			if ((tourCategories != null && !tourCategories.isEmpty()) || (prices != null && !prices.isEmpty())) {
				// 使用其他篩選條件進行查詢
				if (tourCategories != null && !tourCategories.isEmpty() && prices != null && !prices.isEmpty()) {
					// 同時根據活動分類和價格範圍進行查詢
					filteredActivities = aService.findByTourCategoriesAndPriceRange(tourCategories, prices);
				} else if (tourCategories != null && !tourCategories.isEmpty()) {
					// 根據活動分類進行查詢
					filteredActivities = aService.findByTourCategories(tourCategories);
				} else {
					// 根據價格範圍進行查詢
					filteredActivities = aService.findByPriceRange(prices);
				}
			} else {
				// 如果沒有提供任何篩選條件，則可能執行默認操作，這裡示例返回所有活動
				filteredActivities = aService.findAll();
			}
		}

		model.addAttribute("activities", filteredActivities);
		return "/backend/activity/activityQueryAllFront";
	}

	@GetMapping("/activityqueryallfront")
	public String getAllActivitiesFront(Model model) {
		List<Activity> allActivities = aService.findAll();
		for (Activity activity : allActivities) {
			List<ActivityImage> activityImages = activity.getActivityImages();
			System.out.println(activityImages);
		}
		model.addAttribute("activities", allActivities);

		return "/backend/activity/activityQueryAllFront";
	}

	@GetMapping("/activityqueryfront")
	public String getActivityDetails(@RequestParam("tourId") Integer tourId, Model model) {
		Activity activity = aService.findById(tourId);
		if (activity != null) {
			List<ActivityImage> activityImages = activity.getActivityImages();
			System.out.println(activityImages);
			model.addAttribute("activity", activity);
			model.addAttribute("activityImages", activityImages);

			return "/backend/activity/activityQueryFront";
		} else {
			return "redirect:/error";
		}
	}

	@GetMapping("/activityqueryall")
	public String processQueryAll(Model model) {
		List<Activity> allActivities = aService.findAll();
		model.addAttribute("activities", allActivities);
		return "/backend/activity/activityQueryAll";
	}

	@DeleteMapping("/activity/delete/{id}")
	public ResponseEntity<String> deleteActivity(@PathVariable("id") Integer id) {
		aService.deleteById(id);
		return ResponseEntity.ok().build();
	}

	@PostMapping("/activity/insert")
	public String createActivity(@ModelAttribute("activity") Activity activity,
			@RequestParam("imageFiles") List<MultipartFile> imageFiles) {
		aService.insert(activity, imageFiles);
		return "redirect:/activityqueryall";
	}

	@GetMapping("/activity/edit/{id}")
	public String editActivity(@PathVariable("id") Integer id, Model model) {
		Activity activity = aService.findById(id);
		model.addAttribute("activity", activity);
		return "/backend/activity/editActivity";
	}

	@PostMapping("/activity/update/{tourID}")
	public String updateActivity(@PathVariable("tourID") Integer tourID, @ModelAttribute Activity activity) {
		activity.setTourID(tourID);
		aService.update(activity);
		return "redirect:/activityqueryall";
	}
}
