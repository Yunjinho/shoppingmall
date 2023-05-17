package shoppingmall.model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import shoppingmall.model.ShoppingMallDataSource;
import shoppingmall.model.dao.CategoriesDAO;
import shoppingmall.model.dto.CategoriesDTO;

public class CategoriesDAOImpl implements CategoriesDAO {

	@Override
	public ArrayList<CategoriesDTO> getCategoriesNames() {
		ArrayList<CategoriesDTO> list = new ArrayList<CategoriesDTO>();
		Connection con=null;
		PreparedStatement stmt=null;
		ResultSet rs=null;
		try {
			String sql="select * from categories";
			con=ShoppingMallDataSource.getConnection();
			stmt=con.prepareStatement(sql);
			rs=stmt.executeQuery();
			while(rs.next()) {
				CategoriesDTO categoriesDto=new CategoriesDTO();
				categoriesDto.setCategoryId(rs.getInt("categoryId"));
				categoriesDto.setCategoryName(rs.getString("categoryName"));
				list.add(categoriesDto);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}finally {
			if(con!=null)
				ShoppingMallDataSource.closeConnection(con);
			if(stmt!=null) 
				try {stmt.close();}catch (SQLException e) {e.printStackTrace();}
			if(rs!=null) 
				try {rs.close();}catch (SQLException e) {e.printStackTrace();}
		}
		return list;
	}

	@Override
	public int insertCategories(String name) {
		int count=0;
		String sql="INSERT INTO categories(categoryId,categoryName) VALUES (CATEGORIES_SEQ.NEXTVAL,?)";
		PreparedStatement stmt=null;
		Connection con=null;
		try {
			con=ShoppingMallDataSource.getConnection();
			stmt=con.prepareStatement(sql);
			stmt.setString(1, name);
			count = stmt.executeUpdate();
		} catch (Exception e) {
			throw new RuntimeException();
		}finally {
			if(con!=null)
				ShoppingMallDataSource.closeConnection(con);
			if(stmt!=null) 
				try {stmt.close();}catch (SQLException e) {e.printStackTrace();}
		}
		
		return count;
	}
	
}
