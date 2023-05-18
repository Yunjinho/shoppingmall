package shoppingmall.model.dao;

import java.util.ArrayList;

import shoppingmall.model.dto.OrderDetailsDTO;

public interface OrderDetailsDAO {
	ArrayList<OrderDetailsDTO> getUserOrderProducts(int orderDetailId); // 사용자 주문 상품 조회

	int insertOrderProduct(OrderDetailsDTO orderDetailsDto); // 사용자 상품 주문

	int updateOrderProductfromUser(OrderDetailsDTO orderDetailsDto); // 사용자 주문

	int updateOrderProductfromManager(OrderDetailsDTO orderDetailsDto); // 관리자 배송 상태 변경

	int deleteOrderProduct(int orderDetailId); // 사용자 주문 삭제

	int updateOrderStatus(int orderId);
}
