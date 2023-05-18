package shoppingmall.model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import shoppingmall.model.ShoppingMallDataSource;
import shoppingmall.model.dao.OrdersDAO;
import shoppingmall.model.dto.AddressesDTO;
import shoppingmall.model.dto.OrdersDTO;
import shoppingmall.model.dto.ProductsDTO;
import shoppingmall.model.dto.UsersDTO;

public class OrdersDAOImpl implements OrdersDAO {
	@Override
	public ArrayList<OrdersDTO> getUserOrderList(String userId) {
		ArrayList<OrdersDTO> list = new ArrayList<>();
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM ORDERS WHERE USERID = ?";
		try {
			con = ShoppingMallDataSource.getConnection();
			stmt = con.prepareStatement(sql);
			stmt.setString(1, userId);
			rs = stmt.executeQuery();
			while (rs.next()) {
				OrdersDTO ordersDto = new OrdersDTO();
				ordersDto.setOrderId(rs.getInt("orderId"));
				ordersDto.setUserId(rs.getString("userId"));
				list.add(ordersDto);
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
	public int insertUserOrderfromCart(int cartId, String userId, AddressesDTO address) {
		int count = 0;
		int cartTotalPrice = 0;
		CartsDAOImpl cartsDaoImpl = new CartsDAOImpl();
		Connection con = null;
		PreparedStatement stmt = null;
		String sql = "INSERT INTO ORDERS VALUES(ORDERS_SEQ.NEXTVAL,?,?,?)";

		try {
			cartTotalPrice = cartsDaoImpl.getCartTotalPrice(cartId);
			con = ShoppingMallDataSource.getConnection();
			stmt = con.prepareStatement(sql);
			stmt.setString(1, userId);
			stmt.setInt(2, cartTotalPrice);
			stmt.setString(3, address.getAddress());
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
	public int insertUserOrderfromProductDetail(int productId, String userId, int addressId, int amount,
			AddressesDTO address) {
		int count = 0;
		int cartTotalPrice = 0;
		Connection con = null;
		PreparedStatement stmt = null;
		ProductsDAOImpl productsDaoImpl = new ProductsDAOImpl();
		ProductsDTO productDto = new ProductsDTO();

		String sql = "INSERT INTO ORDERS VALUES(ORDERS_SEQ.NEXTVAL,?,?,?)";
		try {
			productDto = productsDaoImpl.getProductDetail(productId);
			cartTotalPrice = productDto.getProductPrice() * amount;
			con = ShoppingMallDataSource.getConnection();
			stmt = con.prepareStatement(sql);
			stmt.setString(1, userId);
			stmt.setInt(2, cartTotalPrice);
			stmt.setString(3, address.getAddress());
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
	public ArrayList<OrdersDTO> getAllOrders() {
		int count = 0;
		int cartTotalPrice = 0;
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ArrayList<OrdersDTO> ordersList = new ArrayList<>();

		String sql = "SELECT o.orderId, o.userId, o.totalPrice, o.address, u.userName, u.phoneNumber "
				+ "FROM ORDERS o JOIN USERS u ON o.userId = u.userId ORDER BY ORDERID";
		try {
			con = ShoppingMallDataSource.getConnection();
			stmt = con.prepareStatement(sql);
			rs = stmt.executeQuery();
			while (rs.next()) {
				OrdersDTO ordersDto = new OrdersDTO();
				ordersDto.setOrderId(rs.getInt("orderId"));
				ordersDto.setUserId(rs.getString("userId"));
				ordersDto.setTotalPrice(rs.getInt("totalPrice"));
				ordersDto.setAddress(rs.getString("address"));
				UsersDTO u = new UsersDTO();
				u.setUserName(rs.getString("userName"));
				u.setPhoneNumber(rs.getString("phoneNumber"));
				ordersDto.setUser(u);

				ordersList.add(ordersDto);
			}

		} catch (SQLException e) {
			// TODO: handle exception
			throw new RuntimeException(e);
		} finally {
			ShoppingMallDataSource.closePreparedStatement(stmt);
			ShoppingMallDataSource.closeResultSet(rs);
			ShoppingMallDataSource.closeConnection(con);
		}
		return ordersList;
	}

}
