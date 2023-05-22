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
		ArrayList<ProductsDTO> productList = new ArrayList<>();
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		String sql = "select rowNumber,productId,productName,productPrice,productStock,productinfo,categoryId,createdAt,updatedAt from "
				+ "(select rownum as rowNumber,productId,productName,productPrice,productStock,productinfo,categoryId,createdAt,updatedAt from "
				+ "(select * from products p where p.categoryId=? and p.productStock > 0 and p.productstatus=1 order by createdAt desc)) "
				+ "where rowNumber between ? and ?";
		try {
			con = ShoppingMallDataSource.getConnection();
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, categoryId);
			stmt.setInt(2, (pageNum * 5) + 1);
			stmt.setInt(3, (pageNum + 1) * 5);
			rs = stmt.executeQuery();
			while (rs.next()) {
				ProductsDTO productDto = new ProductsDTO();
				productDto.setProductId(rs.getInt("productId"));
				productDto.setProductName(rs.getString("productName"));
				productDto.setProductPrice(rs.getInt("productPrice"));
				productDto.setProductStock(rs.getInt("productStock"));
				productDto.setProductInfo(rs.getString("productinfo"));
				productDto.setCategoryId(rs.getInt("categoryId"));
				productDto.setCreatedAt(rs.getTimestamp("createdAt"));
				productDto.setUpdatedAt(rs.getTimestamp("updatedAt"));
				productDto.setRowNumber(rs.getInt("rowNumber"));
				productList.add(productDto);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			ShoppingMallDataSource.closePreparedStatement(stmt);
			ShoppingMallDataSource.closeResultSet(rs);
			ShoppingMallDataSource.closeConnection(con);
		}
		return productList;
	}

	@Override
	public ArrayList<ProductsDTO> getProductsList() {
		ArrayList<ProductsDTO> productsList = new ArrayList<>();
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = "SELECT p.productId, c.categoryName, p.productName, p.productPrice, p.productStock, p.productInfo, p.productStatus"
				+ " FROM PRODUCTS p LEFT JOIN CATEGORIES c ON p.categoryId = c.categoryId ORDER BY p.productId";

		try {
			con = ShoppingMallDataSource.getConnection();
			stmt = con.prepareStatement(sql);
			rs = stmt.executeQuery();
			while (rs.next()) {
				ProductsDTO productDto = new ProductsDTO();
				productDto.setProductId(rs.getInt("productId"));
				productDto.setCategoryName(rs.getString("categoryName"));
				productDto.setProductName(rs.getString("productName"));
				productDto.setProductPrice(rs.getInt("productPrice"));
				productDto.setProductStock(rs.getInt("productStock"));
				productDto.setProductInfo(rs.getString("productInfo"));
				productDto.setProductStatus(rs.getInt("productStatus"));

				productsList.add(productDto);
			}
		} catch (SQLException e) {
			// TODO: handle exception
			throw new RuntimeException(e);
		} finally {
			ShoppingMallDataSource.closePreparedStatement(stmt);
			ShoppingMallDataSource.closeResultSet(rs);
			ShoppingMallDataSource.closeConnection(con);
		}
		return productsList;
	}

	@Override
	public ProductsDTO getProductDetail(int productId) {
		ProductsDTO productDto = new ProductsDTO();
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM products WHERE productId=?";
		try {
			con = ShoppingMallDataSource.getConnection();
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, productId);
			rs = stmt.executeQuery();
			if (rs.next()) {
				productDto = new ProductsDTO();
				productDto.setProductId(rs.getInt("productId")); // 상품 번호
				productDto.setProductName(rs.getString("productName")); // 상품 이름
				productDto.setProductPrice(rs.getInt("productPrice")); // 상품 가격
				productDto.setProductStock(rs.getInt("productStock")); // 상품 재고
				productDto.setProductInfo(rs.getString("productinfo")); // 상품 정보
				productDto.setUpdatedAt(rs.getTimestamp("updatedAt")); // 최신 등록일
				productDto.setProductStatus(rs.getInt("productStatus"));// 판매상태
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
	public int insertProduct(ProductsDTO productDto) {
		int count = 0;
		Connection con = null;
		PreparedStatement stmt = null;
		String sql = "INSERT INTO PRODUCTS (PRODUCTID, PRODUCTNAME, PRODUCTPRICE, PRODUCTSTOCK, PRODUCTINFO, CATEGORYID)"
				+ " VALUES (PRODUCTS_SEQ.NEXTVAL, ?, ?, ?, ?, ?)";
		try {
			con = ShoppingMallDataSource.getConnection();
			stmt = con.prepareStatement(sql);
			stmt.setString(1, productDto.getProductName());
			stmt.setInt(2, productDto.getProductPrice());
			stmt.setInt(3, productDto.getProductStock());
			stmt.setString(4, productDto.getProductInfo());
			stmt.setInt(5, productDto.getCategoryId());
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
	public int updateProductInfo(ProductsDTO productDto) {
		int count = 0;
		Connection con = null;
		PreparedStatement stmt = null;
		String sql = "UPDATE PRODUCTS "
				+ "SET productName = ?, productPrice = ?, productStock = ?, productInfo = ?, categoryId = ?,UPDATEDAT = SYSDATE, productStatus = ? "
				+ "WHERE PRODUCTID = ?";
		try {
			con = ShoppingMallDataSource.getConnection();
			stmt = con.prepareStatement(sql);

			stmt.setString(1, productDto.getProductName());
			stmt.setInt(2, productDto.getProductPrice());
			stmt.setInt(3, productDto.getProductStock());
			stmt.setString(4, productDto.getProductInfo());
			stmt.setInt(5, productDto.getCategoryId());
			stmt.setInt(6, productDto.getProductStatus());
			stmt.setInt(7, productDto.getProductId());
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
	public int updateProductStock(int productId, int productStock) {
		int count = 0;
		Connection con = null;
		PreparedStatement stmt = null;
		String sql = "UPDATE PRODUCTS SET PRODUCTSTOCK = ?, UPDATEDAT = SYSDATE WHERE PRODUCTID = ?";
		try {
			con = ShoppingMallDataSource.getConnection();
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, productStock);
			stmt.setInt(2, productId);
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
	public int updateProductStatus(int productId, int productStatus) {
		int count = 0;
		Connection con = null;
		PreparedStatement stmt = null;
		String sql = "UPDATE Products SET productStatus = ? WHERE productId = ?";
		try {
			con = ShoppingMallDataSource.getConnection();
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, productStatus);
			stmt.setInt(2, productId);
			count = stmt.executeUpdate();
		} catch (SQLException e) {
			// TODO: handle exception
			throw new RuntimeException(e);
		} finally {
			ShoppingMallDataSource.closePreparedStatement(stmt);
			ShoppingMallDataSource.closeConnection(con);
		}
		return count;
	}

	@Override
	public boolean productCheckByProductId(int productId) {
		// TODO Auto-generated method stub
		int count = 0;
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		String sql = "SELECT productId FROM Products WHERE productId = ?";
		try {
			con = ShoppingMallDataSource.getConnection();
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, productId);
			rs = stmt.executeQuery();
			if (rs.next()) {
				// 존재 하면 true
				return true;
			}

		} catch (SQLException e) {
			// TODO: handle exception
			throw new RuntimeException(e);
		}
		// 존재 하지 않으면 false
		return false;
	}

}
