package shoppingmall.model.dao;

import java.util.ArrayList;

import shoppingmall.model.dto.CartsDTO;

public interface CartsDAO {
	ArrayList<CartsDTO> getUsersCartList(String userId);
	
	int insertCart(CartsDTO cartsDto);
	int deleteFromCart(int cartId);
	int updateFromCart(CartsDTO cartsDto);
}
