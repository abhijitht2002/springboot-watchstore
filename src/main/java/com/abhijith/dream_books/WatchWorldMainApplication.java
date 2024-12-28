package com.abhijith.dream_books;

import com.abhijith.dream_books.dao.categoryDao;
import com.abhijith.dream_books.dao.productDAO;
import com.abhijith.dream_books.entity.category;
import com.abhijith.dream_books.entity.product;
import com.abhijith.dream_books.entity.productImages;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class WatchWorldMainApplication {

	public static void main(String[] args) {
		SpringApplication.run(WatchWorldMainApplication.class, args);
	}

	@Bean
	public CommandLineRunner cmdRunner(categoryDao theCategoryDao, productDAO theProductDAO){
		return runner -> {
//			insertData(theCategoryDao);
//			insertMultipleData(theCategoryDao);
//			insertProductData(theProductDAO);
//			insertProductDataWithExImages(theProductDAO);
		};
	}

	private void insertProductDataWithExImages(productDAO theProductDAO) {

		System.out.println("inserting data...");
		//	conversion required double to BigDecimal
		BigDecimal newPrice = new BigDecimal("18401.00");
		//	inserting data
		product theProduct = new product(
				"Maritime Pro Lateen Sail Chronograph Watch",
				"TITAN",
				"men",
				"uploads/product_images/1830KL02_th.jpg",
				"Make a royal statement at special occasions with this masterpiece featuring a black layered maritime dial, two-toned dials, pointed gold indices, and subdials. The intricate second hand, date window, chronograph, and tachymeter add functionality and style. Pair it with a sophisticated evening gown or a tailored suit for a regal appearance.",
				"{\"movement\": \"quartz\", \"multifunction\": \"yes\", \"dayanddate\": \"both\", \"chronograph\": \"yes\", \"dial_colour\": \"green\", \"material\": \"Stainless Steel\", \"strap material\": \"leather\", \"strap colour\": \"brown\", \"clasp\": \"buckle\", \"water_resistance\": \"70 meters\"}",
				newPrice,
				LocalDateTime.now()
		);

		productImages img1 = new productImages(theProduct, "uploads/product_images/1830KL02_1.jpg");
		productImages img2 = new productImages(theProduct, "uploads/product_images/1830KL02_2.jpg");
		productImages img3 = new productImages(theProduct, "uploads/product_images/1830KL02_3.jpg");

		theProduct.addImage(img1);
		theProduct.addImage(img2);
		theProduct.addImage(img3);

		//	saving data
		System.out.println("saving data...");
		theProductDAO.save(theProduct);
		System.out.println("successfully saved!!! open workbench to see the data");
	}

	private void insertProductData(productDAO theProductDAO) {

		System.out.println("inserting data...");
		//	conversion required double to BigDecimal
		BigDecimal newPrice = new BigDecimal("18401.00");
		//	inserting data
		product theProduct = new product(
				"Maritime Pro Lateen Sail Chronograph Watch",
				"TITAN",
				"men",
				"uploads/product_images/1830KL02_th.jpg",
				"Make a royal statement at special occasions with this masterpiece featuring a black layered maritime dial, two-toned dials, pointed gold indices, and subdials. The intricate second hand, date window, chronograph, and tachymeter add functionality and style. Pair it with a sophisticated evening gown or a tailored suit for a regal appearance.",
				"{\"movement\": \"quartz\", \"multifunction\": \"yes\", \"dayanddate\": \"both\", \"chronograph\": \"yes\", \"dial_colour\": \"green\", \"material\": \"Stainless Steel\", \"strap material\": \"leather\", \"strap colour\": \"brown\", \"clasp\": \"buckle\", \"water_resistance\": \"70 meters\"}",
				newPrice,
				LocalDateTime.now()
		);
		//	saving data
		System.out.println("saving data...");
		theProductDAO.save(theProduct);
		System.out.println("successfully saved!!! open workbench to see the data");
	}

	private void insertMultipleData(categoryDao theCategoryDao) {

		System.out.println("inserting datas....");

		List<category> theCategories = Arrays.asList(
				new category("for her", "uploads/category_icons/for_her.jpg", "Elegant collection of watches for women"),
				new category("couple", "uploads/category_icons/couple.jpg", "Paired watches for you and your loved ones"),
				new category("kids", "uploads/category_icons/kids.jpg", "Fun and colorful watches for kids"),
				new category("sports", "uploads/category_icons/sports.jpg", "Durable and stylish sport watches")
		);

		System.out.println("saving.......");
		theCategoryDao.saveAll(theCategories);

		System.out.println("successfuly saved data ... open workbench to see the data");
	}

	private void insertData(categoryDao theCategoryDao) {

		System.out.println("inserting a new data");
		category theCategory = new category("for him", "uploads/category_icons/for_him.jpg","Great collection of watches for men");

		System.out.println("saving the data......");
		theCategoryDao.save(theCategory);

		System.out.println("successfuly saved data ... open workbench to see the data");
	}
}
