package com.example.heroku.dto;

import java.util.List;

public class OrderRequestDTO {

	private Integer orderId;
	private ClientOrderDTO order;
	private Integer clientId;
	private List<ProductRequestDTO> products;
	private String address;

	/**
	 * @return the orderId
	 */
	public Integer getOrderId() {
		return orderId;
	}

	/**
	 * @param orderId the orderId to set
	 */
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	/**
	 * @return the order
	 */
	public ClientOrderDTO getOrder() {
		return order;
	}

	/**
	 * @param order the order to set
	 */
	public void setOrder(ClientOrderDTO order) {
		this.order = order;
	}

	/**
	 * @return the clientId
	 */
	public Integer getClientId() {
		return clientId;
	}

	/**
	 * @param clientId the clientId to set
	 */
	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}

	/**
	 * @return the products
	 */
	public List<ProductRequestDTO> getProducts() {
		return products;
	}

	/**
	 * @param products the products to set
	 */
	public void setProducts(List<ProductRequestDTO> products) {
		this.products = products;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

}
