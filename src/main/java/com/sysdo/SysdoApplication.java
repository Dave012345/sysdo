package com.sysdo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * This is a web application for advertisments of used smart devices.
 *
 * After a simple registration, users can upload data from their used smart devices to the database of the system,
 * as well as they can upload an image file to the static file system.
 * Then, once their ad is out of date, they can delete it from their profile.
 * Visitors of the web application can browse for uploaded devices and view these by category,
 * search by keywords, and sort them by date or price.
 *
 * @author: David Burka
 * **/

@SpringBootApplication
public class SysdoApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(SysdoApplication.class, args);
		System.out.println();
	}
}
