package shoppingmall.model.dao;

import java.util.ArrayList;

import shoppingmall.model.dto.CartsDTO;

public interface CartsDAO {
	ArrayList<CartsDTO> getUsersCartList(String userId); // 장바구니 목록 조회

	int insertCart(CartsDTO cartsDto); // 장바구니 담기

	int deleteFromCart(int cartId); // 장바구니에서 삭제

	int updateFromCart(CartsDTO cartsDto); // 장바구니 업데이트
}
