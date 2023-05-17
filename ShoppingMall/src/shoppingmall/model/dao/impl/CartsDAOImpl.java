package shoppingmall.model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import shoppingmall.model.ShoppingMallDataSource;
import shoppingmall.model.dao.CartsDAO;
import shoppingmall.model.dto.CartsDTO;

public class CartsDAOImpl implements CartsDAO {

	@Override
	public ArrayList<CartsDTO> getUsersCartList(String userId) {
		ArrayList<CartsDTO> list=new ArrayList<CartsDTO>();
		Connection con=null;
		PreparedStatement stmt=null;
		ResultSet rs=null;
		String sql="select * from carts where userid=?";
		try {
			con=ShoppingMallDataSource.getConnection();
			stmt=con.prepareStatement(sql);
			stmt.setString(1, userId);
			rs=stmt.executeQuery();
			while(rs.next()) {
				CartsDTO cartsDto=new CartsDTO();
				cartsDto.setCartId(rs.getInt("cartId"));
				cartsDto.setUserId(rs.getString("userId"));
				cartsDto.setProductId(rs.getInt("productId"));
				cartsDto.setProductCount(rs.getInt("productCount"));
				cartsDto.setCreatedAt(rs.getTimestamp("createdAt"));
				cartsDto.setUpdatedAt(rs.getTimestamp("UpdatedAt"));
				list.add(cartsDto);
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
	public int insertCart(CartsDTO cartsDto) {
		int count=0;
		Connection con=null;
		PreparedStatement stmt=null;
		String sql="insert into carts values(carts_seq.nextval,?,?,?,sysdate,sysdate)";
		try {
			con=ShoppingMallDataSource.getConnection();
			stmt=con.prepareStatement(sql);
			stmt.setString(1, cartsDto.getUserId());
			stmt.setInt(2, cartsDto.getProductId());
			stmt.setInt(3, cartsDto.getProductCount());
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
	public int deleteFromCart(int cartId) {
		int count=0;
		Connection con=null;
		PreparedStatement stmt=null;
		String sql="delete from carts where cartId=?";
		try {
			con=ShoppingMallDataSource.getConnection();
			stmt=con.prepareStatement(sql);
			stmt.setInt(1, cartId);
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
	public int updateFromCart(CartsDTO cartsDto) {
		int count=0;
		Connection con=null;
		PreparedStatement stmt=null;
		String sql="update carts set productCount=?,updatedAt=sysdate where cartId=?";
		try {
			con=ShoppingMallDataSource.getConnection();
			stmt=con.prepareStatement(sql);
			stmt.setInt(1, cartsDto.getProductCount());
			stmt.setInt(2, cartsDto.getCartId());
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
