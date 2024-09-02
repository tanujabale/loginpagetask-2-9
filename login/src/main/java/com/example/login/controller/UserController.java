package com.example.login.controller;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.login.entity.User;
import com.example.login.service.UserService;

import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
	import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
	import org.springframework.web.bind.annotation.ModelAttribute;
	import org.springframework.web.bind.annotation.PostMapping;


	@Controller
	public class UserController {

	    @Autowired
	    private UserService userService;

	    @GetMapping("/register")
	    public String showRegistrationForm(Model model) {
	        model.addAttribute("user", new User());
	        return "register";
	    }

	    @PostMapping("/register")
	    public String registerUser(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, Model model) {
	        if (bindingResult.hasErrors()) {
	            String errorMessage = bindingResult.getFieldError("username") != null ?
	                bindingResult.getFieldError("username").getDefaultMessage() : 
	                bindingResult.getFieldError("password").getDefaultMessage();

	            model.addAttribute("errorMessage", errorMessage);
	            return "register";
	        }

	        try {
	            userService.registerUser(user);
	            return "redirect:/success";
	        } catch (Exception e) {
	            model.addAttribute("errorMessage", e.getMessage());
	            return "register";
	        }
	    }

	    @GetMapping("/success")
	    public String showSuccessPage() {
	        return "success";
	    }
	}
