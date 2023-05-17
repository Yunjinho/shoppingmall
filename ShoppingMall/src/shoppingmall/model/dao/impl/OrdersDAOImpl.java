package shoppingmall.model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import shoppingmall.model.ShoppingMallDataSource;
import shoppingmall.model.dao.OrdersDAO;
import shoppingmall.model.dto.CartsDTO;
import shoppingmall.model.dto.OrdersDTO;

public class OrdersDAOImpl implements OrdersDAO {
	@Override
	public ArrayList<OrdersDTO> getUserOrderList(String userId) {
		ArrayList<OrdersDTO> list = new ArrayList<>();
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM ORDERS WHERE USERID=?";
		try {
			con = ShoppingMallDataSource.getConnection();
			stmt = con.prepareStatement(sql);
			stmt.setString(1, userId);
			rs = stmt.executeQuery();
			while (rs.next()) {
				OrdersDTO ordersDto = new OrdersDTO();
				ordersDto.setOrderId(rs.getInt("orderId"));
				ordersDto.setUserId(rs.getString("userId"));
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
	public int insertUserOrderfromCart(ArrayList<CartsDTO> cartList) {
		int count = 0;
		Connection con = null;
		PreparedStatement stmt = null;
		String sql = "INSERT INTO ORDERS VALUES(ORDERS_SEQ.NEXTVAL,?,?)";
		try {
			con = ShoppingMallDataSource.getConnection();
			stmt = con.prepareStatement(sql);
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
	public int insertUserOrderfromProductDetail(int productId, String userId, int addressId) {
		return 0;
	}

	@Override
	public int deleteUserOrder(int orderId) {
		return 0;
	}

}
