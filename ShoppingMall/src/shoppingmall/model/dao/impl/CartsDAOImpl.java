package shoppingmall.model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import shoppingmall.model.ShoppingMallDataSource;
import shoppingmall.model.dao.CartsDAO;
import shoppingmall.model.dto.CartsDTO;
import shoppingmall.model.dto.ProductsDTO;

public class CartsDAOImpl implements CartsDAO {

	@Override
	public ArrayList<CartsDTO> getUsersCartList(String userId) {
		ArrayList<CartsDTO> list = new ArrayList<CartsDTO>();
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM carts c JOIN products p ON c.productid = p.productid WHERE userId = ?";
		try {
			con = ShoppingMallDataSource.getConnection();
			stmt = con.prepareStatement(sql);
			stmt.setString(1, userId);
			rs = stmt.executeQuery();
			while (rs.next()) {
				CartsDTO cartsDto = new CartsDTO();
				ProductsDTO productDto =new ProductsDTO();
				cartsDto.setCartId(rs.getInt("cartId"));
				cartsDto.setUserId(rs.getString("userId"));
				cartsDto.setProductId(rs.getInt("productId"));
				cartsDto.setProductCount(rs.getInt("productCount"));
				cartsDto.setCreatedAt(rs.getTimestamp("createdAt"));
				cartsDto.setUpdatedAt(rs.getTimestamp("UpdatedAt"));
				
				productDto.setProductStatus(rs.getInt("productstatus"));
				productDto.setProductName(rs.getString("productName"));
				productDto.setProductPrice(rs.getInt("productPrice"));
				cartsDto.setProductDto(productDto);
				list.add(cartsDto);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			ShoppingMallDataSource.closePreparedStatement(stmt);
			ShoppingMallDataSource.closeResultSet(rs);
			ShoppingMallDataSource.closeConnection(con);
		}
		return list;
	}

	@Override
	public int insertCart(CartsDTO cartsDto) {
		int count = 0;
		Connection con = null;
		PreparedStatement stmt = null;
		String sql = "INSERT INTO carts(cartId,userId,productId,productCount) VALUES(CARTS_SEQ.NEXTVAL,?,?,?)";
		try {
			con = ShoppingMallDataSource.getConnection();
			stmt = con.prepareStatement(sql);
			stmt.setString(1, cartsDto.getUserId());
			stmt.setInt(2, cartsDto.getProductId());
			stmt.setInt(3, cartsDto.getProductCount());
			count = stmt.executeUpdate();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			ShoppingMallDataSource.closePreparedStatement(stmt);
			ShoppingMallDataSource.closeConnection(con);
		}
		return count;
	}
	//장바구니에서 상품 한개 삭제
	@Override
	public int deleteCartProduct(int cartId) {
		int count = 0;
		Connection con = null;
		PreparedStatement stmt = null;
		String sql = "DELETE FROM carts WHERE cartId = ?";
		try {
			con = ShoppingMallDataSource.getConnection();
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, cartId);
			count = stmt.executeUpdate();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			ShoppingMallDataSource.closePreparedStatement(stmt);
			ShoppingMallDataSource.closeConnection(con);
		}
		return count;
	}

	@Override
	public int updateCart(CartsDTO cartsDto) {
		int count = 0;
		Connection con = null;
		PreparedStatement stmt = null;
		String sql = "UPDATE carts SET productCount = ?,updatedAt = SYSDATE WHERE cartId = ?";
		try {
			con = ShoppingMallDataSource.getConnection();
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, cartsDto.getProductCount());
			stmt.setInt(2, cartsDto.getCartId());
			count = stmt.executeUpdate();
		} catch (Exception e) {
			throw new RuntimeException(e);	
		} finally {
			ShoppingMallDataSource.closePreparedStatement(stmt);
			ShoppingMallDataSource.closeConnection(con);
		}
		return count;
	}

	@Override
	public int getCartTotalPrice(int cartId) {
		String sql="SELECT SUM(c.productcount*p.productprice) AS cartTotalPrice "+
				"FROM carts c JOIN products p ON c.productId = p.productId "+
				"WEHRE cartId = ?";

		
		int totalPrice=0;
		Connection con=null;
		PreparedStatement stmt=null;
		ResultSet rs=null;
		try {
			con=ShoppingMallDataSource.getConnection();
			stmt=con.prepareStatement(sql);
			stmt.setInt(1, cartId);
			rs=stmt.executeQuery();
			if(rs.next()) {
				totalPrice=rs.getInt("cartTotalPrice");
			}
		} catch (Exception e) {
			throw new RuntimeException();
		}finally {
			ShoppingMallDataSource.closeConnection(con);
			ShoppingMallDataSource.closePreparedStatement(stmt);
			ShoppingMallDataSource.closeResultSet(rs);
		}
		return totalPrice;
	}
	//장바구니에서 상품 일괄 구매 후 카트 전체 삭제
	@Override
	public int deleteCartProducts(String userId) {
		int count = 0;
		Connection con = null;
		PreparedStatement stmt = null;
		String sql = "DELETE FROM carts WHERE userId = ?";
		try {
			con = ShoppingMallDataSource.getConnection();
			stmt = con.prepareStatement(sql);
			stmt.setString(1, userId);
			count = stmt.executeUpdate();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			ShoppingMallDataSource.closePreparedStatement(stmt);
			ShoppingMallDataSource.closeConnection(con);
		}
		return count;
	}
	@Override
	public CartsDTO checkExistProduct(int productId) {
			Connection con = null;
			PreparedStatement stmt = null;
			ResultSet rs=null;
			String sql = "SELECT * FROM carts WHERE productId = ?";
			CartsDTO cart=new CartsDTO();
			try {
				con = ShoppingMallDataSource.getConnection();
				stmt = con.prepareStatement(sql);
				stmt.setInt(1, productId);
				rs=stmt.executeQuery();
				if(rs.next()) {
					cart.setCartId(rs.getInt("cartId"));
					cart.setProductCount(rs.getInt("productCount"));
					cart.setProductId(rs.getInt("productId"));
					cart.setUserId(rs.getString("userId"));
					
					return cart;
				}
			} catch (Exception e) {
				throw new RuntimeException(e);
			} finally {
				ShoppingMallDataSource.closeResultSet(rs);
				ShoppingMallDataSource.closePreparedStatement(stmt);
				ShoppingMallDataSource.closeConnection(con);
			}
		return null;
	}

}
