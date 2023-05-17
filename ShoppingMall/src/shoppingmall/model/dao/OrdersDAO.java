package shoppingmall.model.dao;

import java.util.ArrayList;

import shoppingmall.model.dto.CartsDTO;
import shoppingmall.model.dto.OrdersDTO;

public interface OrdersDAO {
	ArrayList<OrdersDTO> getUserOrderList(String userId); // 사용자 주문 목록

	int insertUserOrderfromCart(ArrayList<CartsDTO> cartList);// 장바구니에서 주문

	int insertUserOrderfromProductDetail(int productId, String userId, int addressId); // 상품 디테일에서 주문

	int deleteUserOrder(int orderId); // 주문 취소
}
