package com.debuggeandoideas.app_security.controllers;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/welcome")
public class WelcomeController {
	
	@GetMapping
	public Map<String, String> welcome(){
		return Collections.singletonMap("msj", "welcome");
	}

}
