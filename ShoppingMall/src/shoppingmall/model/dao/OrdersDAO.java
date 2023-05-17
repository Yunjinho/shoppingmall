package shoppingmall.model.dao;

import java.util.ArrayList;

import shoppingmall.model.dto.CartsDTO;
import shoppingmall.model.dto.OrdersDTO;

public interface OrdersDAO {
	ArrayList<OrdersDTO> getUserOrderList(String userId);

	int insertUserOrderfromCart(ArrayList<CartsDTO> cartList);// 장바구니에서 

	int insertUserOrderfromProductDetail(int productId, String userId, int addressId);

	int deleteUserOrder(int orderId);
}
