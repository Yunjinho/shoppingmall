package shoppingmall.model.dao;

import java.util.ArrayList;

import shoppingmall.model.dto.ProductsDTO;

public interface ProductsDAO {
	ArrayList<ProductsDTO> getProductListByCategory(int categoryId,int pageNum);//카테고리 별 상품 목록 출력 10개씩 끊어서 
	ProductsDTO getProductDetail(int productId);//상품 디테일
	
	int insertProduct(ProductsDTO prod);// 상품 입력
	int deleteProduct(int productId);// 상품 삭제
	int updateProductInfo(ProductsDTO prod);// 상품 수정
	int updateProductStock(ProductsDTO prod, int amount);//상품 수량 수정
}
