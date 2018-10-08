package com.test.resources;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

	@GetMapping("/index")
	public String test() {
		return "Hello allow to All!";
	}
	
	
	@GetMapping("/test1") 
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public String test1() {
		return "Allow only Admin role!";
	}
	 
	@GetMapping("/test2") 
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public String test2() {
		return "Allow only HR Role!";
	}
	
	@GetMapping("/tes") 
	public String tes() {
		return "Allow only tes Role!";
	}
	/*
	@GetMapping("/test3") 
	public String test3() {
		return "Allow only ADMIN & HR Role!";
	}
	
	@GetMapping("/test4") 
	public String test4() {
		return "Allow only manager Role!";
	}*/
}
