package shoppingmall.model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import shoppingmall.model.ShoppingMallDataSource;
import shoppingmall.model.dao.ProductsDAO;
import shoppingmall.model.dto.ProductsDTO;

public class ProductsDAOImpl implements ProductsDAO {

	@Override
	public ArrayList<ProductsDTO> getProductListByCategory(int categoryId, int pageNum) {
		ArrayList<ProductsDTO> list = new ArrayList<ProductsDTO>();
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		String sql = "select rowNumber,productId,productName,productPrice,productStock,productinfo,categoryId,createdAt,updatedAt from "
				+ "(select rownum as rowNumber,productId,productName,productPrice,productStock,productinfo,categoryId,createdAt,updatedAt from "
				+ "(select * from products p where p.categoryId=? and p.productStock > 0 order by updatedAt desc)) "
				+ "where rowNumber between ? and ?";
		try {
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, categoryId);
			stmt.setInt(2, (pageNum * 10) + 1);
			stmt.setInt(3, (pageNum + 1) * 10);
			rs = stmt.executeQuery();
			while (rs.next()) {
				ProductsDTO productDto = new ProductsDTO();
				productDto.setProductId(rs.getInt("productId"));
				productDto.setProductName(rs.getString("productName"));
				productDto.setProductPrice(rs.getInt("productPrice"));
				productDto.setProductStock(rs.getInt("productStock"));
				productDto.setProductinfo(rs.getString("productinfo"));
				productDto.setCategoryId(rs.getInt("categoryId"));
				productDto.setCreatedAt(rs.getTimestamp("createdAt"));
				productDto.setUpdatedAt(rs.getTimestamp("updatedAt"));
				productDto.setRowNumber(rs.getInt("rowNumber"));
				list.add(productDto);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			ShoppingMallDataSource.closePreparedStatement(stmt);
			ShoppingMallDataSource.closeResultSet(rs);
			ShoppingMallDataSource.closeConnection(con);
		}
		return list;
	}

	@Override
	public ProductsDTO getProductDetail(int productId) {
		ProductsDTO productDto = null;
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM PRODUCT WHERE CATEGORYID=?";
		try {
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, productId);
			rs = stmt.executeQuery();
			if (rs.next()) {
				productDto = new ProductsDTO();
				productDto.setProductId(rs.getInt("productId"));
				productDto.setProductName(rs.getString("productName"));
				productDto.setProductPrice(rs.getInt("productPrice"));
				productDto.setProductStock(rs.getInt("productStock"));
				productDto.setProductinfo(rs.getString("productinfo"));
				productDto.setCategoryId(rs.getInt("categoryId"));
				productDto.setCreatedAt(rs.getTimestamp("createdAt"));
				productDto.setUpdatedAt(rs.getTimestamp("updatedAt"));
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			ShoppingMallDataSource.closePreparedStatement(stmt);
			ShoppingMallDataSource.closeResultSet(rs);
			ShoppingMallDataSource.closeConnection(con);
		}

		return productDto;
	}

	@Override
	public int insertProduct(ProductsDTO prod) {
		int count = 0;
		Connection con = null;
		PreparedStatement stmt = null;
		String sql = "INSERT INTO PRODUCTS VALUES (PRODUCTS_SEQ.NEXTVAL,?,?,?,?,?,SYSDATE,SYSDATE)";
		try {
			con = ShoppingMallDataSource.getConnection();
			stmt = con.prepareStatement(sql);
			stmt.setString(1, prod.getProductName());
			stmt.setInt(2, prod.getProductPrice());
			stmt.setInt(3, prod.getProductStock());
			stmt.setString(4, prod.getProductinfo());
			stmt.setInt(5, prod.getCategoryId());
			count = stmt.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			ShoppingMallDataSource.closePreparedStatement(stmt);
			ShoppingMallDataSource.closeConnection(con);
		}
		return count;
	}

	@Override
	public int deleteProduct(int productId) {
		int count = 0;
		Connection con = null;
		PreparedStatement stmt = null;
		String sql = "DELETE FROM PRODUCTS WHERE PRODUCTID=?";
		try {
			con = ShoppingMallDataSource.getConnection();
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, productId);
			count = stmt.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			ShoppingMallDataSource.closePreparedStatement(stmt);
			ShoppingMallDataSource.closeConnection(con);
		}
		return count;
	}

	@Override
	public int updateProductInfo(ProductsDTO prod) {
		int count = 0;
		Connection con = null;
		PreparedStatement stmt = null;
		String sql = "UPDATE PRODUCTS SET productName=?,productPrice=?,productStock=?,productinfo=?,categoryId=?,updatedAt=sysdate where productId=?";
		try {
			con = ShoppingMallDataSource.getConnection();
			stmt = con.prepareStatement(sql);
			stmt.setString(1, prod.getProductName());
			stmt.setInt(2, prod.getProductPrice());
			stmt.setInt(3, prod.getProductStock());
			stmt.setString(4, prod.getProductinfo());
			stmt.setInt(5, prod.getCategoryId());
			stmt.setInt(6, prod.getProductId());
			count = stmt.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			ShoppingMallDataSource.closePreparedStatement(stmt);
			ShoppingMallDataSource.closeConnection(con);
		}
		return count;
	}

	@Override
	public int updateProductStock(ProductsDTO prod, int amount) {
		int count = 0;
		Connection con = null;
		PreparedStatement stmt = null;
		String sql = "UPDATE PRODUCTS SET productStock=? updatedAt=sysdate where productId=?";
		try {
			con = ShoppingMallDataSource.getConnection();
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, prod.getProductStock() + amount);
			stmt.setInt(2, prod.getProductId());
			count = stmt.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			ShoppingMallDataSource.closePreparedStatement(stmt);
			ShoppingMallDataSource.closeConnection(con);
		}
		return count;
	}

}
