package shoppingmall.model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import shoppingmall.model.ShoppingMallDataSource;
import shoppingmall.model.dao.OrderDetailsDAO;
import shoppingmall.model.dto.OrderDetailsDTO;
import shoppingmall.model.dto.ProductsDTO;
import shoppingmall.model.dto.UsersDTO;

public class OrderDetailsDAOImpl implements OrderDetailsDAO {

	@Override
	public ArrayList<OrderDetailsDTO> getUserOrderProducts(int orderId) {
		ArrayList<OrderDetailsDTO> orderDetailsList = new ArrayList<OrderDetailsDTO>();
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = "select * from where orderId=?";
		try {
			con = ShoppingMallDataSource.getConnection();
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, orderId);
			rs = stmt.executeQuery();
			while (rs.next()) {
				OrderDetailsDTO orderDetailsDto = new OrderDetailsDTO();
				orderDetailsDto.setOrderDetailId(rs.getInt("orderDetailId"));
				orderDetailsDto.setOrderId(rs.getInt("orderId"));
				orderDetailsDto.setProductId(rs.getInt("productId"));
				orderDetailsDto.setDeliveryStatus(rs.getString("deliveryStatus"));
				orderDetailsDto.setCreatedAt(rs.getTimestamp("createdAt"));
				orderDetailsDto.setUpdatedAt(rs.getTimestamp("updatedAt"));
				orderDetailsList.add(orderDetailsDto);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			ShoppingMallDataSource.closePreparedStatement(stmt);
			ShoppingMallDataSource.closeResultSet(rs);
			ShoppingMallDataSource.closeConnection(con);
		}
		return orderDetailsList;
	}

	@Override
	public int insertOrderProduct(OrderDetailsDTO orderDetailsDto) {
		int count = 0;
		Connection con = null;
		PreparedStatement stmt = null;
		String sql = "insert into orderDetails values (orderDetails_seq.nextval,?,?,?,?,sysdate,sysdate)";
		try {
			con = ShoppingMallDataSource.getConnection();
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, orderDetailsDto.getOrderId());
			stmt.setInt(2, orderDetailsDto.getProductId());
			stmt.setInt(3, orderDetailsDto.getProductCount());
			stmt.setString(4, "상품 준비중");
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
	public int updateOrderProductfromUser(OrderDetailsDTO orderDetailsDto) {
		int count = 0;
		Connection con = null;
		PreparedStatement stmt = null;
		String sql = "update oderderDetails set productCount=? where orderDetailId=?";
		try {
			con = ShoppingMallDataSource.getConnection();
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, orderDetailsDto.getProductCount());
			stmt.setInt(2, orderDetailsDto.getOrderDetailId());
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
	public int updateOrderProductfromManager(OrderDetailsDTO orderDetailsDto) {
		int count = 0;
		Connection con = null;
		PreparedStatement stmt = null;
		String sql = "update oderderDetails set deliveryStatus=? where orderDetailId=?";
		try {
			con = ShoppingMallDataSource.getConnection();
			stmt = con.prepareStatement(sql);
			stmt.setString(1, orderDetailsDto.getDeliveryStatus());
			stmt.setInt(2, orderDetailsDto.getOrderDetailId());
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
	public int deleteOrderProduct(int orderDetailId) {
		int count = 0;
		Connection con = null;
		PreparedStatement stmt = null;
		String sql = "delete from orderDetails where orderDetailId=?";
		try {
			con = ShoppingMallDataSource.getConnection();
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, orderDetailId);
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
	public ArrayList<OrderDetailsDTO> getOrderDeatils() {
		ArrayList<OrderDetailsDTO> orderDetailsList = new ArrayList<OrderDetailsDTO>();
		OrderDetailsDTO orderDetailsDto = new OrderDetailsDTO();
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = "SELECT od.orderDetailId, od.orderId, od.productCount, od.deliveryStatus, od.createdAt, us.userId, us.userName, us.address, us.phoneNumber, p.productId, p.productName, p.productStock "
				+ "FROM ORDERDETAILS od JOIN PRODUCTS p ON od.productId = p.productId "
				+ "JOIN (SELECT o.orderId, o.userId, o.address, o.totalPrice, u.userName, u.phoneNumber FROM ORDERS o JOIN USERS u ON o.userId = u.userId) us "
				+ "ON us.orderId = od.orderId ORDER BY od.createdAt";
		try {
			con = ShoppingMallDataSource.getConnection();
			stmt = con.prepareStatement(sql);
			rs = stmt.executeQuery();
			while (rs.next()) {
				UsersDTO u = new UsersDTO();
				u.setUserId(rs.getString("userId"));
				u.setUserName(rs.getString("userName"));
				u.setPhoneNumber(rs.getString("phoneNumber"));
				u.setAddress(rs.getString("address"));

				ProductsDTO p = new ProductsDTO();
				p.setProductId(rs.getInt("productId"));
				p.setProductName(rs.getString("productName"));
				p.setProductStock(rs.getInt("productStock"));

				orderDetailsDto.setOrderDetailId(rs.getInt("orderDetailId"));
				orderDetailsDto.setOrderId(rs.getInt("orderId"));
				orderDetailsDto.setProductCount(rs.getInt("productCount"));
				orderDetailsDto.setDeliveryStatus(rs.getString("deliveryStatus"));
				orderDetailsDto.setCreatedAt(rs.getTimestamp("createdAt"));

				orderDetailsDto.setUser(u);
				orderDetailsDto.setProduct(p);
				orderDetailsList.add(orderDetailsDto);
			}
		} catch (SQLException e) {
			// TODO: handle exception
			throw new RuntimeException(e);
		} finally {
			ShoppingMallDataSource.closePreparedStatement(stmt);
			ShoppingMallDataSource.closeResultSet(rs);
			ShoppingMallDataSource.closeConnection(con);
		}
		return orderDetailsList;
	}

	@Override
	public int updateOrderStatus(int orderId, String status) {
		Connection con = null;
		PreparedStatement stmt = null;
		String sql = "UPDATE ORDERDETAILS SET DELIVERYSTATUS = ? WHERE ORDERDETAILID = ?";
		try {
			con = ShoppingMallDataSource.getConnection();
			stmt = con.prepareStatement(sql);
			stmt.setString(1, status);
			stmt.setInt(2, orderId);
		} catch (SQLException e) {
			// TODO: handle exception
			throw new RuntimeException(e);
		} finally {
			ShoppingMallDataSource.closePreparedStatement(stmt);
			ShoppingMallDataSource.closeConnection(con);
		}
		return 0;
	}

}
