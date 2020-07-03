package com.example.heroku.controller;

import java.util.Arrays;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.example.heroku.dto.ProductDTO;
import com.example.heroku.entity.Product;
import com.example.heroku.service.ProductService;
import com.example.heroku.service.UserService;

@Controller
@RequestMapping("/product")
public class ProductController {

	@Autowired
	private ProductService productService;

	@Autowired
	private UserService userService;

	@Autowired
	private ModelMapper modelMapper;

	@GetMapping("/available")
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public List<ProductDTO> allAvailables() {
		List<Product> products = this.productService.findAllAvailables();

		List<ProductDTO> result = Arrays.asList(modelMapper.map(products, ProductDTO[].class));

		return result;
	}

	@PutMapping
	@ResponseBody
	@ResponseStatus(HttpStatus.CREATED)
	public ProductDTO save(@RequestHeader("user-token") String userToken, @RequestBody ProductDTO productDTO) {
		this.userService.authToken(userToken);
		Product entity = modelMapper.map(productDTO, Product.class);
		Product product = this.productService.save(entity);

		ProductDTO result = modelMapper.map(product, ProductDTO.class);
		return result;
	}

	@PostMapping
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public ProductDTO update(@RequestHeader("user-token") String userToken, @RequestBody ProductDTO productDTO) {
		this.userService.authToken(userToken);
		Product entity = modelMapper.map(productDTO, Product.class);
		Product product = this.productService.save(entity);

		ProductDTO result = modelMapper.map(product, ProductDTO.class);
		return result;
	}

	@DeleteMapping("/{id}")
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public void delete(@RequestHeader("user-token") String userToken, @PathVariable("id") Integer id) {
		this.userService.authToken(userToken);
		this.productService.delete(id);
	}

}
