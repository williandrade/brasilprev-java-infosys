package com.example.heroku.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.example.heroku.dto.LoginDTO;
import com.example.heroku.dto.UserDTO;
import com.example.heroku.entity.User;
import com.example.heroku.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private UserService userService;

	@PostMapping("/login")
	@ResponseBody
	@ResponseStatus(HttpStatus.ACCEPTED)
	public UserDTO logIn(@RequestBody LoginDTO loginDTO) {
		User user = this.userService.logIn(loginDTO.getEmail(), loginDTO.getPassword());
		UserDTO clientDTO = modelMapper.map(user, UserDTO.class);
		return clientDTO;
	}

}
