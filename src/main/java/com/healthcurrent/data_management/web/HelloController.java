package com.healthcurrent.data_management.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
	
	@RequestMapping("/")
	public String index() {
		return "Greetings from Data Team!";
	}

//	@RequestMapping(value="/index", method = RequestMethod.GET)
//	public String hello() {
//		return "index";
//	}
}