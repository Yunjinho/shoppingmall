package shoppingmall.model.dao;

import java.util.ArrayList;

import shoppingmall.model.dto.OrderDetailsDTO;

public interface OrderDetailsDAO {
	ArrayList<OrderDetailsDTO> getUserOrderProducts(int orderDetailId);
	
	int insertOrderProduct(OrderDetailsDTO orderDetailsDto);
	int updateOrderProductfromUser(OrderDetailsDTO orderDetailsDto);
	int updateOrderProductfromManager(OrderDetailsDTO orderDetailsDto);
	int deleteOrderProduct(int orderDetailId);
}
