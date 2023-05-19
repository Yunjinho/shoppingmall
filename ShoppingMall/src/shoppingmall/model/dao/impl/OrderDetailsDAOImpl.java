package shoppingmall.model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import shoppingmall.model.ShoppingMallDataSource;
import shoppingmall.model.dao.OrderDetailsDAO;
import shoppingmall.model.dto.OrderDetailsDTO;
import shoppingmall.model.dto.OrdersDTO;
import shoppingmall.model.dto.ProductsDTO;

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
		String sql = "insert into orderDetails values (orderDetails_seq.nextval,?,?,?,'상품 준비중',sysdate,sysdate)";
		try {
			con = ShoppingMallDataSource.getConnection();
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, orderDetailsDto.getOrderId());
			stmt.setInt(2, orderDetailsDto.getProductId());
			stmt.setInt(3, orderDetailsDto.getProductCount());
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
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = "SELECT od.orderDetailId, od.orderId, od.productCount, od.deliveryStatus, o.userId, o.totalPrice, o.address, p.productName, p.productStock "
				+ "FROM orderDetails od LEFT JOIN orders o ON o.orderId = od.orderId "
				+ "LEFT JOIN products p on p.productId = od.productId";
		try {
			con = ShoppingMallDataSource.getConnection();
			stmt = con.prepareStatement(sql);
			rs = stmt.executeQuery();
			while (rs.next()) {
				OrderDetailsDTO orderDetailsDto = new OrderDetailsDTO();
				ProductsDTO p = new ProductsDTO();
				p.setProductName(rs.getString("productName"));
				p.setProductStock(rs.getInt("productStock"));
				orderDetailsDto.setProductDto(p);

				OrdersDTO o = new OrdersDTO();
				o.setUserId(rs.getString("userId"));
				o.setTotalPrice(rs.getInt("totalPrice"));
				o.setAddress(rs.getString("address"));
				orderDetailsDto.setOrderDto(o);

				orderDetailsDto.setOrderDetailId(rs.getInt("orderDetailId"));
				orderDetailsDto.setOrderId(rs.getInt("orderId"));
				orderDetailsDto.setProductCount(rs.getInt("productCount"));
				orderDetailsDto.setDeliveryStatus(rs.getString("deliveryStatus"));

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
	public int updateOrderStatus(int orderDetailId, String status) {
		int count = 0;
		Connection con = null;
		PreparedStatement stmt = null;
		String sql = "UPDATE ORDERDETAILS SET DELIVERYSTATUS = ? WHERE ORDERDETAILID = ?";
		try {
			con = ShoppingMallDataSource.getConnection();
			stmt = con.prepareStatement(sql);
			stmt.setString(1, status);
			stmt.setInt(2, orderDetailId);
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

}
