package shoppingmall.model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import shoppingmall.model.ShoppingMallDataSource;
import shoppingmall.model.dao.ProductsDAO;
import shoppingmall.model.dto.ProductsDTO;

public class ProductsDAOImpl implements ProductsDAO {

	@Override
	public ArrayList<ProductsDTO> getProdListByCategory(int categoryId,int pageNum) {
		ArrayList<ProductsDTO> list=new ArrayList<ProductsDTO>();
		Connection con=null;
		PreparedStatement stmt=null;
		ResultSet rs=null;
		
		String sql="select rowNumber,productId,productName,productPrice,productStock,productinfo,categoryId,createdAt,updatedAt from "+
					"(select rownum as rowNumber,productId,productName,productPrice,productStock,productinfo,categoryId,createdAt,updatedAt from "+
					"(select * from products where categoryId=? order by updatedAt desc)) "+
					"where rowNum between ? and ?";
		try {
			stmt=con.prepareStatement(sql);
			stmt.setInt(1, categoryId);
			stmt.setInt(2, (pageNum*10)+1);
			stmt.setInt(3, (pageNum+1)*10);
			rs=stmt.executeQuery();
			while(rs.next()) {
				ProductsDTO productDto=new ProductsDTO();
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
		} catch (Exception e) {
			throw new RuntimeException();
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
	public ProductsDTO getProdDetail(int productId) {
		ProductsDTO productDto=null;
		Connection con=null;
		PreparedStatement stmt=null;
		ResultSet rs=null;
		String sql="select * from product where categoriyId=?";
		try {
			stmt=con.prepareStatement(sql);
			stmt.setInt(1, productId);
			rs=stmt.executeQuery();
			if(rs.next()) {
				productDto=new ProductsDTO();
				productDto.setProductId(rs.getInt("productId"));
				productDto.setProductName(rs.getString("productName"));
				productDto.setProductPrice(rs.getInt("productPrice"));
				productDto.setProductStock(rs.getInt("productStock"));
				productDto.setProductinfo(rs.getString("productinfo"));
				productDto.setCategoryId(rs.getInt("categoryId"));
				productDto.setCreatedAt(rs.getTimestamp("createdAt"));
				productDto.setUpdatedAt(rs.getTimestamp("updatedAt"));
			}
		} catch (Exception e) {
			throw new RuntimeException();
		}finally {
			if(con!=null)
				ShoppingMallDataSource.closeConnection(con);
			if(stmt!=null) 
				try {stmt.close();}catch (SQLException e) {e.printStackTrace();}
			if(rs!=null) 
				try {rs.close();}catch (SQLException e) {e.printStackTrace();}
		}
		
		return productDto;
	}

	@Override
	public int insertProduct(ProductsDTO prod) {
		int count=0;
		
		ArrayList<ProductsDTO> list=new ArrayList<ProductsDTO>();
		Connection con=null;
		PreparedStatement stmt=null;
		String sql="INSERT INTO products VALUES (PRODUCTS_SEQ.NEXTVAL,?,?,?,?,?,?,?)";
		try {
			con=ShoppingMallDataSource.getConnection();
			stmt=con.prepareStatement(sql);
			stmt.setString(1, prod.getProductName());
			stmt.setInt(2, prod.getProductPrice());
			stmt.setInt(3, prod.getProductStock());
			stmt.setString(4, prod.getProductinfo());
			stmt.setInt(5, prod.getCategoryId());
			stmt.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
			stmt.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
			count=stmt.executeUpdate();
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

	@Override
	public int deleteProduct(int productId) {
		int count=0;
		Connection con=null;
		PreparedStatement stmt=null;
		String sql="DELETE FROM products WHERE productId=?";
		try {
			con=ShoppingMallDataSource.getConnection();
			stmt=con.prepareStatement(sql);
			stmt.setInt(1, productId);
			count=stmt.executeUpdate();
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

	@Override
	public int updateProduct(ProductsDTO prod) {
		int count=0;
		Connection con=null;
		PreparedStatement stmt=null;
		String sql="UPDATE products SET productName=?,productPrice=?,productStock=?,productinfo=?,categoryId=?,updatedAt=?";
		try {
			con=ShoppingMallDataSource.getConnection();
			stmt=con.prepareStatement(sql);
			stmt.setString(1, prod.getProductName());
			stmt.setInt(2, prod.getProductPrice());
			stmt.setInt(3, prod.getProductStock());
			stmt.setString(4, prod.getProductinfo());
			stmt.setInt(5, prod.getCategoryId());
			stmt.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
			count=stmt.executeUpdate();
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
