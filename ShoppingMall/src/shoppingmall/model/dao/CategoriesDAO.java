package shoppingmall.model.dao;

import java.util.ArrayList;

import shoppingmall.model.dto.CategoriesDTO;

public interface CategoriesDAO {
	ArrayList<CategoriesDTO> getCategoriesNames();
	int insertCategories(String name);
}
