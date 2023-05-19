package shoppingmall.model.dao;

import java.util.ArrayList;

import shoppingmall.model.dto.CartsDTO;

public interface CartsDAO {
	ArrayList<CartsDTO> getUsersCartList(String userId); // 장바구니 목록 조회

	int insertCart(CartsDTO cartsDto); // 장바구니 담기

	int deleteCartProduct(int cartId); // 장바구니에서 상품 한개 삭제

	int deleteCartProducts(String userId); // 장바구니에서 상품 구매 후 장바구니 비우기

	int updateFromCart(CartsDTO cartsDto); // 장바구니 업데이트
	
	int getCartTotalPrice(int cartId);// 카트에 담겨있는 상품의 총 합
}
