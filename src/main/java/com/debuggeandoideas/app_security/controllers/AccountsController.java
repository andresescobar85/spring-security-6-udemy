package com.debuggeandoideas.app_security.controllers;

import java.util.Collections;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/accounts")
public class AccountsController {
	
	
	/***ESTO SE USA CUANDO EL PROYECTO ES GRANDE Y HAY MAS RUTAS DE LAS DEBIDAS PARA CONFIGURAR****/
	//@PreAuthorize("hasAnyAuthority('VIEW_ACCOUNT','VIEW_CARDS')")
	@GetMapping
	public Map<String, String> accounts(){
		//business logic
		return Collections.singletonMap("msj", "accounts");
	}

}
