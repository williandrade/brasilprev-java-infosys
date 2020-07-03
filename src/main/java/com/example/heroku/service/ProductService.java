package com.example.heroku.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.heroku.entity.Product;
import com.example.heroku.exception.ProductNotFoundException;
import com.example.heroku.repository.ProductRepository;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;

	public List<Product> findAllAvailables() {
		return productRepository.findAllAvailables();
	}

	public Product save(Product product) {
		return productRepository.saveAndFlush(product);
	}

	public void delete(Integer id) {
		if (!productRepository.existsById(id)) {
			throw new ProductNotFoundException();
		}
		
		productRepository.deleteById(id);
	}

}
