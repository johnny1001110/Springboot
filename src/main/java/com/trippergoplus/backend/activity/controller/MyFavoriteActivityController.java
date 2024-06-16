package com.trippergoplus.backend.activity.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MyFavoriteActivityController {

	@GetMapping("/myFavoriteActivity")
	public String myFavoriteActivity() {
		return "backend/activity/myFavoriteActivity";
	}

}