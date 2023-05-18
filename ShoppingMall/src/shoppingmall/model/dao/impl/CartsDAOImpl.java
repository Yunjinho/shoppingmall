package shoppingmall.model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import shoppingmall.model.ShoppingMallDataSource;
import shoppingmall.model.dao.CartsDAO;
import shoppingmall.model.dto.CartsDTO;

public class CartsDAOImpl implements CartsDAO {

	@Override
	public ArrayList<CartsDTO> getUsersCartList(String userId) {
		ArrayList<CartsDTO> list = new ArrayList<CartsDTO>();
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM CARTS WHERE USERID=?";
		try {
			con = ShoppingMallDataSource.getConnection();
			stmt = con.prepareStatement(sql);
			stmt.setString(1, userId);
			rs = stmt.executeQuery();
			while (rs.next()) {
				CartsDTO cartsDto = new CartsDTO();
				cartsDto.setCartId(rs.getInt("cartId"));
				cartsDto.setUserId(rs.getString("userId"));
				cartsDto.setProductId(rs.getInt("productId"));
				cartsDto.setProductCount(rs.getInt("productCount"));
				cartsDto.setCreatedAt(rs.getTimestamp("createdAt"));
				cartsDto.setUpdatedAt(rs.getTimestamp("UpdatedAt"));
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
		String sql = "INSERT INTO CARTS VALUES(CARTS_SEQ.NEXTVAL,?,?,?,SYSDATE,SYSDATE)";
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

	@Override
	public int deleteFromCart(int cartId) {
		int count = 0;
		Connection con = null;
		PreparedStatement stmt = null;
		String sql = "DELETE FROM CARTS WHERE CARTID=?";
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
	public int updateFromCart(CartsDTO cartsDto) {
		int count = 0;
		Connection con = null;
		PreparedStatement stmt = null;
		String sql = "UPDATE CARTS SET PRODUCTCOUNT=?,UPDATEDAt=SYSDATE WHERE CARTID=?";
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
		int totalPrice=0;
		Connection con=null;
		PreparedStatement stmt=null;
		ResultSet rs=null;
		String sql="select sum(c.productcount*p.productprice) as cartTotalPrice from carts c join products p on c.productId=p.productId where cartId=?";
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

}
