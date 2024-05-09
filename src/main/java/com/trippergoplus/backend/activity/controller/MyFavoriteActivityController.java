package com.trippergoplus.backend.activity.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MyFavoriteActivityController {

	@GetMapping("/myFavoriteActivity")
	public String myFavoriteActivity() {
		return "backend/activity/myFavoriteActivity";
	}

	@GetMapping("/myFavoriteActivity2")
	public String myFavoriteActivity2() {
		return "backend/activity/test2";
	}

	@GetMapping("/myFavoriteActivity3")
	public String myFavoriteActivity3() {
		return "backend/activity/test3";
	}

	@GetMapping("/myFavoriteActivity4")
	public String myFavoriteActivity4() {
		return "backend/activity/test4";
	}
}