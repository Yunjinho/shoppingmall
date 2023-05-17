package shoppingmall.model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import shoppingmall.model.ShoppingMallDataSource;
import shoppingmall.model.dao.CategoriesDAO;
import shoppingmall.model.dto.CategoriesDTO;

public class CategoriesDAOImpl implements CategoriesDAO {

	@Override
	public ArrayList<CategoriesDTO> getCategoriesNames() {
		ArrayList<CategoriesDTO> categoryList = new ArrayList<CategoriesDTO>();
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			String sql = "select * from categories";
			con = ShoppingMallDataSource.getConnection();
			stmt = con.prepareStatement(sql);
			rs = stmt.executeQuery();
			while (rs.next()) {
				CategoriesDTO categoriesDto = new CategoriesDTO();
				categoriesDto.setCategoryId(rs.getInt("categoryId"));
				categoriesDto.setCategoryName(rs.getString("categoryName"));
				categoryList.add(categoriesDto);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			ShoppingMallDataSource.closePreparedStatement(stmt);
			ShoppingMallDataSource.closeResultSet(rs);
			ShoppingMallDataSource.closeConnection(con);
		}
		return categoryList;
	}

	@Override
	public int insertCategories(String name) {
		int count = 0;
		String sql = "INSERT INTO categories(categoryId,categoryName) VALUES (CATEGORIES_SEQ.NEXTVAL,?)";
		PreparedStatement stmt = null;
		Connection con = null;
		try {
			con = ShoppingMallDataSource.getConnection();
			stmt = con.prepareStatement(sql);
			stmt.setString(1, name);
			count = stmt.executeUpdate();
		} catch (Exception e) {
			throw new RuntimeException();
		} finally {
			ShoppingMallDataSource.closePreparedStatement(stmt);
			ShoppingMallDataSource.closeConnection(con);
		}

		return count;
	}

}
