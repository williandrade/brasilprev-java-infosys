package com.example.heroku.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.heroku.dto.ClientOrderDTO;
import com.example.heroku.dto.OrderRequestDTO;
import com.example.heroku.dto.ProductRequestDTO;
import com.example.heroku.entity.ClientOrder;
import com.example.heroku.entity.OrderHistory;
import com.example.heroku.entity.Product;
import com.example.heroku.entity.ProductOrder;
import com.example.heroku.exception.CouldNotCalcTotalPriceException;
import com.example.heroku.exception.OrderNotFoundException;
import com.example.heroku.exception.ProductNotFoundException;
import com.example.heroku.exception.ProductOutOfStockException;
import com.example.heroku.repository.ClientOrderRepository;
import com.example.heroku.repository.OrderHistoryRepository;
import com.example.heroku.repository.ProductOrderRepository;
import com.example.heroku.repository.ProductRepository;
import com.example.heroku.util.HelperUtil;

@Service
public class OrderService {

	private static final int FIRST_ORDER_STATUS = 1;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private ClientOrderRepository clientOrderRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ProductOrderRepository productOrderRepository;

	@Autowired
	private OrderHistoryRepository orderHistoryRepository;

	public OrderRequestDTO makeAnOrder(OrderRequestDTO orderRequestDTO) {
		Float orderTotalPrice = this.calcTotalPrice(orderRequestDTO);

		if (orderTotalPrice == null) {
			throw new CouldNotCalcTotalPriceException();
		}

		Date now = HelperUtil.getNowDate();

		// CREATE THE ORDER
		ClientOrder clientOrder = new ClientOrder();
		clientOrder.setClientId(orderRequestDTO.getClientId());
		clientOrder.setOrderDate(now);
		clientOrder.setOrderStatusId(FIRST_ORDER_STATUS);
		clientOrder.setOrderTotalPrice(orderTotalPrice);
		clientOrder.setAddress(orderRequestDTO.getAddress());

		clientOrder = clientOrderRepository.save(clientOrder);

		// ADD A HISTORY LOG
		OrderHistory orderHistory = new OrderHistory();
		orderHistory.setHistoryDate(now);
		orderHistory.setOrderId(clientOrder.getId());
		orderHistory.setOrderStatusId(FIRST_ORDER_STATUS);
		orderHistoryRepository.save(orderHistory);

		for (ProductRequestDTO each : orderRequestDTO.getProducts()) {
			Product product = productRepository.findById(each.getId())
					.orElseThrow(() -> new ProductNotFoundException());

			int qtd = product.getStock() - each.getQtd();
			if (qtd < 0) {
				throw new ProductOutOfStockException();
			}
			product.setStock(qtd);
			product = productRepository.save(product);

			ProductOrder productOrder = new ProductOrder();
			productOrder.setObs(each.getObs());
			productOrder.setOrderId(clientOrder.getId());
			productOrder.setProduct(product);
			productOrder.setQtd(each.getQtd());

			this.productOrderRepository.save(productOrder);
		}

		orderRequestDTO.setOrderId(clientOrder.getId());

		return orderRequestDTO;
	}

	public List<OrderRequestDTO> findAll() {
		List<OrderRequestDTO> result = new ArrayList<>();
		List<ClientOrder> ordersEntity = clientOrderRepository.findAll();
		List<ClientOrderDTO> orders = Arrays.asList(modelMapper.map(ordersEntity, ClientOrderDTO[].class));

		for (ClientOrderDTO order : orders) {
			result.add(this.parseToOrderRequest(order));
		}

		return result;
	}

	public List<OrderRequestDTO> findByClientId(Integer id) {
		List<OrderRequestDTO> result = new ArrayList<>();
		List<ClientOrder> ordersEntity = clientOrderRepository.findAllByClientId(id);
		List<ClientOrderDTO> orders = Arrays.asList(modelMapper.map(ordersEntity, ClientOrderDTO[].class));

		for (ClientOrderDTO order : orders) {
			result.add(this.parseToOrderRequest(order));
		}

		return result;
	}

	public OrderRequestDTO findById(Integer id) {
		ClientOrder order = this.clientOrderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException());
		ClientOrderDTO orderDTO = modelMapper.map(order, ClientOrderDTO.class);
		return parseToOrderRequest(orderDTO);
	}

	public OrderRequestDTO changeOrderStatus(Integer orderId, Integer statusId, String obs) {
		ClientOrder order = this.clientOrderRepository.findById(orderId)
				.orElseThrow(() -> new OrderNotFoundException());

		order.setOrderStatusId(statusId);
		order = this.clientOrderRepository.save(order);

		// ADD A HISTORY LOG
		OrderHistory orderHistory = new OrderHistory();
		orderHistory.setHistoryDate(HelperUtil.getNowDate());
		orderHistory.setOrderId(order.getId());
		orderHistory.setObs(obs);
		orderHistory.setOrderStatusId(statusId);
		orderHistoryRepository.save(orderHistory);

		ClientOrderDTO orderDTO = modelMapper.map(order, ClientOrderDTO.class);
		return parseToOrderRequest(orderDTO);
	}

	private OrderRequestDTO parseToOrderRequest(ClientOrderDTO order) {
		OrderRequestDTO orderDTO = new OrderRequestDTO();
		orderDTO.setOrderId(order.getId());
		orderDTO.setOrder(order);
		orderDTO.setAddress(order.getAddress());

		orderDTO.setClientId(order.getClientId());
		List<ProductOrder> productsEntity = productOrderRepository.findAllByOrderId(order.getId());
		List<ProductRequestDTO> products = Arrays.asList(modelMapper.map(productsEntity, ProductRequestDTO[].class));
		orderDTO.setProducts(products);
		return orderDTO;
	}

	private Float calcTotalPrice(OrderRequestDTO order) {
		float result = 0f;
		if (order.getProducts() != null) {
			for (ProductRequestDTO product : order.getProducts()) {

				Product productEntity = this.productRepository.findById(product.getId())
						.orElseThrow(() -> new ProductNotFoundException());

				result += productEntity.getPrice();
			}
			return result;
		}
		return null;
	}

}
