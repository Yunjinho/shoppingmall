package shoppingmall.model.dao;

import java.util.ArrayList;
import java.util.List;

import shoppingmall.model.dto.CartsDTO;
import shoppingmall.model.dto.OrdersDTO;

public interface OrdersDAO {
	ArrayList<OrdersDTO> getUserOrderList(String userId); // 사용자 주문 목록

	int insertUserOrderfromCart(String userId, String address,int cartTotalPrice,List<CartsDTO> cartList);// 장바구니에서 주문

	// int deleteUserOrder(int orderId); // 주문 취소

	ArrayList<OrdersDTO> getAllOrders();

	int insertUserOrderfromProductDetail(int productId, String userId, String address, int amount); // 상품 디테일에서 주문

}
