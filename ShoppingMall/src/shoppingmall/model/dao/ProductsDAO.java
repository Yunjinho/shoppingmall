package shoppingmall.model.dao;

import java.util.ArrayList;

import shoppingmall.model.dto.ProductsDTO;

public interface ProductsDAO {
	ArrayList<ProductsDTO> getProductListByCategory(int categoryId, int pageNum);// 카테고리 별 상품 목록 출력 10개씩 끊어서

	ArrayList<ProductsDTO> getProductsList(); // 모든 상품 조회

	ProductsDTO getProductDetail(int productId);// 상품 디테일

	int insertProduct(ProductsDTO productDto);// 상품 등록

	int updateProductInfo(ProductsDTO produdctDto);// 상품 수정

	int updateProductStock(int productId, int productStock);// 상품 수량 수정

	int updateProductStatus(int updateProductId, int productStatus); // 상품 상태 변경
}
