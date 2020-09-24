package com.trungtamjava.springbootdemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {
	@GetMapping("/login")
	public String login(Model model ,@RequestParam(name = "e",required = false) String error) {
		if (error != null ) {
			model.addAttribute("e",error);
		}
		return "/client/login";
	}
	
}
