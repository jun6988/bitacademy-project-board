package com.bitacademy.projectboard.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


//// Home 버튼 누르면 root 페이지로 redirection 
//@RestController
//public class MainController {
//	
//	@GetMapping("/")
//	public String root() {
//		return "forward:/articles";
//	}
//
//}

//Home 버튼 누르면 root 페이지로 redirection 

@RestController
public class MainController {
	
	@GetMapping("/")
	public void root() {
		
	}

}
