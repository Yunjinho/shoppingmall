package shoppingmall.model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import shoppingmall.model.ShoppingMallDataSource;
import shoppingmall.model.dao.OrderDetailsDAO;
import shoppingmall.model.dto.OrderDetailsDTO;

public class OrderDetailsDAOImpl implements OrderDetailsDAO {

	@Override
	public ArrayList<OrderDetailsDTO> getUserOrderProducts(int orderId) {
		ArrayList<OrderDetailsDTO> list= new ArrayList<OrderDetailsDTO>();
		Connection con=null;
		PreparedStatement stmt=null;
		ResultSet rs=null;
		String sql="select * from where orderId=?";
		try {
			con=ShoppingMallDataSource.getConnection();
			stmt=con.prepareStatement(sql);
			stmt.setInt(1,orderId);
			rs=stmt.executeQuery();
			while(rs.next()) {
				OrderDetailsDTO orderDetailsDto=new OrderDetailsDTO();
				orderDetailsDto.setOrderDetailId(rs.getInt("orderDetailId"));
				orderDetailsDto.setOrderId(rs.getInt("orderId"));
				orderDetailsDto.setProductId(rs.getInt("productId"));
				orderDetailsDto.setDeliveryStatus(rs.getString("deliveryStatus"));
				orderDetailsDto.setCreatedAt(rs.getTimestamp("createdAt"));
				orderDetailsDto.setUpdatedAt(rs.getTimestamp("updatedAt"));
				list.add(orderDetailsDto);
			}
		} catch (Exception e) {
			throw new RuntimeException();
		}finally {
			ShoppingMallDataSource.closeConnection(con);
			ShoppingMallDataSource.closePreparedStatement(stmt);
			ShoppingMallDataSource.closeResultSet(rs);
		}
		return list;
	}

	@Override
	public int insertOrderProduct(OrderDetailsDTO orderDetailsDto) {
		int count =0;
		Connection con=null;
		PreparedStatement stmt=null;
		String sql="insert into orderDetails values (orderDetails_seq.nextval,?,?,?,'상품 준비중',sysdate,sysdate)";
		try {
			con=ShoppingMallDataSource.getConnection();
			stmt=con.prepareStatement(sql);
			stmt.setInt(1, orderDetailsDto.getOrderId());
			stmt.setInt(2, orderDetailsDto.getProductId());
			stmt.setInt(3, orderDetailsDto.getProductCount());
			count=stmt.executeUpdate();
		} catch (Exception e) {
			throw new RuntimeException();
		}finally {
			ShoppingMallDataSource.closeConnection(con);
			ShoppingMallDataSource.closePreparedStatement(stmt);
		}
		return count;
	}

	@Override
	public int updateOrderProductfromUser(OrderDetailsDTO orderDetailsDto) {
		int count=0;
		Connection con=null;
		PreparedStatement stmt=null;
		String sql="update oderderDetails set productCount=? where orderDetailId=?";
		try {
			con=ShoppingMallDataSource.getConnection();
			stmt=con.prepareStatement(sql);
			stmt.setInt(1, orderDetailsDto.getProductCount());
			stmt.setInt(2, orderDetailsDto.getOrderDetailId());
			count=stmt.executeUpdate();
		} catch (Exception e) {
		}finally {
			ShoppingMallDataSource.closeConnection(con);
			ShoppingMallDataSource.closePreparedStatement(stmt);
		}
		return count;
	}
	
	@Override
	public int updateOrderProductfromManager(OrderDetailsDTO orderDetailsDto) {
		int count=0;
		Connection con=null;
		PreparedStatement stmt=null;
		String sql="update oderderDetails set deliveryStatus=? where orderDetailId=?";
		try {
			con=ShoppingMallDataSource.getConnection();
			stmt=con.prepareStatement(sql);
			stmt.setString(1, orderDetailsDto.getDeliveryStatus());
			stmt.setInt(2, orderDetailsDto.getOrderDetailId());
			count=stmt.executeUpdate();
		} catch (Exception e) {
		}finally {
			ShoppingMallDataSource.closeConnection(con);
			ShoppingMallDataSource.closePreparedStatement(stmt);
		}
		return count;
	}
	
	@Override
	public int deleteOrderProduct(int orderDetailId) {
		int count=0;
		Connection con=null;
		PreparedStatement stmt=null;
		String sql="delete from orderDetails where orderDetailId=?";
		try {
			con=ShoppingMallDataSource.getConnection();
			stmt=con.prepareStatement(sql);
			stmt.setInt(1, orderDetailId);
			count=stmt.executeUpdate();
		} catch (Exception e) {
		}finally {
			ShoppingMallDataSource.closeConnection(con);
			ShoppingMallDataSource.closePreparedStatement(stmt);
		}
		return count;
	}

}
