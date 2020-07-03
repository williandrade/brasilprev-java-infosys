package com.example.heroku.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.example.heroku.dto.OrderRequestDTO;
import com.example.heroku.service.OrderService;
import com.example.heroku.service.UserService;

@Controller
@RequestMapping("/order")
public class OrderController {

	@Autowired
	private OrderService orderService;

	@Autowired
	private UserService userService;

	@GetMapping
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public List<OrderRequestDTO> findAll(@RequestHeader("user-token") String userToken) {
		this.userService.authToken(userToken);
		return this.orderService.findAll();
	}

	@GetMapping("/{id}")
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public OrderRequestDTO findById(@PathVariable("id") Integer id) {
		return this.orderService.findById(id);
	}

	@GetMapping("/client/{id}")
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public List<OrderRequestDTO> findByClientId(@PathVariable("id") Integer id) {
		return this.orderService.findByClientId(id);
	}

	@PutMapping
	@ResponseBody
	@ResponseStatus(HttpStatus.CREATED)
	public OrderRequestDTO makeAnOrder(@RequestBody OrderRequestDTO orderRequestDTO) {
		return this.orderService.makeAnOrder(orderRequestDTO);
	}

}
