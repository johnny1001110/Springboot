package com.trippergoplus.backend.activity.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.trippergoplus.backend.activity.model.Activity;
import com.trippergoplus.backend.activity.model.ActivityImage;
import com.trippergoplus.backend.activity.model.ActivityLocation;
import com.trippergoplus.backend.activity.repository.ActivityImageRepository;
import com.trippergoplus.backend.activity.repository.ActivityRepository;

@Service
@Transactional
public class ActivityService {

	@Autowired
	private ActivityRepository aRepos;

	@Autowired
	private ActivityImageRepository imageRepository;

	@Transactional
	public Activity insert(Activity activity, List<MultipartFile> imageFiles) {
		// 先儲存活動
		Activity savedActivity = aRepos.save(activity);

		// 再儲存活動照片
		List<ActivityImage> activityImages = new ArrayList<>();
		for (MultipartFile file : imageFiles) {
			// 處理並儲存每一張照片
			ActivityImage activityImage = new ActivityImage();
			activityImage.setActivity(savedActivity);
			activityImage.setImagepath(saveImage(file));
			activityImages.add(activityImage);
			System.out.println(activityImage);

			// 將圖片資料保存到資料庫
			imageRepository.save(activityImage);
		}
		savedActivity.setActivityImages(activityImages);
		System.out.println(savedActivity);
		return savedActivity;
	}

	private String saveImage(MultipartFile file) {
		try {
			// 取得檔案的原始名稱
			String originalFilename = file.getOriginalFilename();
			// 取得檔案的副檔名
			String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));

			// 使用當前時間生成唯一的檔案名稱
			LocalDateTime currentTime = LocalDateTime.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
			String formattedDateTime = currentTime.format(formatter);
			String fileName = formattedDateTime + fileExtension;

			// 指定圖片的儲存路徑，這裡假設儲存到相對路徑下的"images"資料夾
			String uploadDir = "src/main/resources/static/picture/activity/";
			// String uploadDir = "D:\\activity";

			// 檢查資料夾是否存在，若不存在則建立
			Path uploadPath = Paths.get(uploadDir);
			if (!Files.exists(uploadPath)) {
				Files.createDirectories(uploadPath);
			}

			// 儲存檔案
			Path filePath = uploadPath.resolve(fileName);
			Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

			// 回傳儲存的檔案路徑
			return fileName;
		} catch (IOException e) {
			e.printStackTrace();
			// 若發生錯誤，回傳空字串或是null
			return "";
		}
	}

	public void update(Activity activity) {
		aRepos.save(activity);
	}

	public List<Activity> findAll() {
		return aRepos.findAll();
	}

	public void deleteById(Integer id) {
		aRepos.deleteById(id);
	}

	public Activity findById(Integer id) {
		Optional<Activity> op1 = aRepos.findById(id);

		if (op1.isPresent()) {
			return op1.get();
		}

		return null;
	}

	public Page<Activity> findAllByPage(Pageable pageable) {
		return aRepos.findAll(pageable);
	}

	public List<Activity> findByLocation(ActivityLocation location) {
		return aRepos.findByLocation(location);
	}

	public List<Activity> findByTourCategories(List<String> tourCategories) {
		return aRepos.findByTourCategoryIn(tourCategories);
	}

	public List<Activity> findByPriceRange(List<String> prices) {
		// 初始化最小值和最大值为整型最小值和最大值
		int minPrice = Integer.MAX_VALUE;
		int maxPrice = Integer.MIN_VALUE;

		// 检查是否传递了价格范围参数
		if (prices != null && !prices.isEmpty()) {
			// 遍历价格范围列表，找到最小值和最大值
			for (String priceRange : prices) {
				String[] range = priceRange.split("~");
				if (range.length == 2) {
					int lowerBound = Integer.parseInt(range[0]);
					int upperBound = Integer.parseInt(range[1]);
					System.out.println(lowerBound);
					System.out.println(upperBound);
					// 更新最小值和最大值
					minPrice = Math.min(minPrice, lowerBound);
					maxPrice = Math.max(maxPrice, upperBound);
				}
			}
		}

		// 使用价格范围查询活动
		return aRepos.findByPriceBetween(minPrice, maxPrice);
	}

	public List<Activity> findByLocationAndDateRange(ActivityLocation location, LocalDate validfrom,
			LocalDate validto) {
		return aRepos.findByLocationAndValidFromAndValidToBetween(location, validfrom, validto);
	}

	public List<Activity> findByTourCategoriesAndPriceRange(List<String> tourCategories, List<String> prices) {
		List<Activity> result = new ArrayList<>();

		// 檢查是否有傳遞了活動類別和價格範圍參數
		if (tourCategories != null && !tourCategories.isEmpty() && prices != null && !prices.isEmpty()) {
			// 初始化最小價格和最大價格為整型最小值和最大值
			int minPrice = Integer.MAX_VALUE;
			int maxPrice = Integer.MIN_VALUE;

			// 遍歷價格範圍列表，找到最小值和最大值
			for (String priceRange : prices) {
				String[] range = priceRange.split("~");
				if (range.length == 2) {
					int lowerBound = Integer.parseInt(range[0]);
					int upperBound = Integer.parseInt(range[1]);
					// 更新最小值和最大值
					minPrice = Math.min(minPrice, lowerBound);
					maxPrice = Math.max(maxPrice, upperBound);
				}
			}

			// 使用價格範圍和活動類別同時查詢活動
			result = aRepos.findByTourCategoryInAndPriceBetween(tourCategories, minPrice, maxPrice);
		}

		return result;
	}

	public List<Activity> findByTourCategoriesAndPriceRangeAndLocationAndDate(List<String> tourCategories,
			List<String> prices, ActivityLocation location, LocalDate validfrom, LocalDate validto) {
		List<Activity> result = new ArrayList<>();

		// 檢查是否有傳遞了活動類別、價格範圍、地點和日期參數
		if (tourCategories != null && !tourCategories.isEmpty() && prices != null && !prices.isEmpty()
				&& location != null && validfrom != null && validto != null) {
			// 初始化最小價格和最大價格為整型最小值和最大值
			int minPrice = Integer.MAX_VALUE;
			int maxPrice = Integer.MIN_VALUE;

			// 遍歷價格範圍列表，找到最小值和最大值
			for (String priceRange : prices) {
				String[] range = priceRange.split("~");
				if (range.length == 2) {
					int lowerBound = Integer.parseInt(range[0]);
					int upperBound = Integer.parseInt(range[1]);
					// 更新最小值和最大值
					minPrice = Math.min(minPrice, lowerBound);
					maxPrice = Math.max(maxPrice, upperBound);
				}
			}

			// 使用價格範圍、活動類別、地點和日期同時查詢活動
			result = aRepos
					.findByTourCategoryInAndPriceBetweenAndLocationAndValidFromLessThanEqualAndValidToGreaterThanEqual(
							tourCategories, minPrice, maxPrice, location, validto, validfrom);
		}

		return result;
	}

	public List<Activity> findByTourCategoriesAndLocationAndDate(List<String> tourCategories, ActivityLocation location,
			LocalDate validfrom, LocalDate validto) {
		return aRepos.findByTourCategoryInAndLocationAndValidFromLessThanEqualAndValidToGreaterThanEqual(tourCategories,
				location, validto, validfrom);
	}

	public List<Activity> findByPriceRangeAndLocationAndDate(List<String> prices, ActivityLocation location,
			LocalDate validfrom, LocalDate validto) {
		// 初始化最小值和最大值为整型最小值和最大值
		int minPrice = Integer.MAX_VALUE;
		int maxPrice = Integer.MIN_VALUE;

		// 检查是否传递了价格范围参数
		if (prices != null && !prices.isEmpty()) {
			// 遍历价格范围列表，找到最小值和最大值
			for (String priceRange : prices) {
				String[] range = priceRange.split("~");
				if (range.length == 2) {
					int lowerBound = Integer.parseInt(range[0]);
					int upperBound = Integer.parseInt(range[1]);
					// 更新最小值和最大值
					minPrice = Math.min(minPrice, lowerBound);
					maxPrice = Math.max(maxPrice, upperBound);
				}
			}
		}

		// 使用价格范围和地点日期查询活动
		return aRepos.findByPriceBetweenAndLocationAndValidFromLessThanEqualAndValidToGreaterThanEqual(minPrice,
				maxPrice, location, validto, validfrom);
	}
}
