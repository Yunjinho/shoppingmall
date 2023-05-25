package shoppingmall.model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import shoppingmall.model.ShoppingMallDataSource;
import shoppingmall.model.dao.OrdersDAO;
import shoppingmall.model.dto.CartsDTO;
import shoppingmall.model.dto.OrderDetailsDTO;
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
		String sql = "SELECT * FROM orders WHERE userId = ?";
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
	public int insertUserOrderfromCart(String userId, String address, int cartTotalPrice, List<CartsDTO> cartList) {
		int count = 0;
		CartsDAOImpl cartsDaoImpl = new CartsDAOImpl();
		OrderDetailsDAOImpl orderDetailsDaoImpl = new OrderDetailsDAOImpl();
		ProductsDAOImpl productsDaoImpl = new ProductsDAOImpl();

		Connection con = null;
		PreparedStatement stmt = null;
		PreparedStatement stmt2 = null;
		ResultSet rs = null;

		String sql = "INSERT INTO orders VALUES(?,?,?,?)";
		String sql2 = "SELECT ORDERS_SEQ.NEXTVAL AS currentSeqNumber FROM dual";

		try {
			con = ShoppingMallDataSource.getConnection();
			// order 테이블에 넣기
			stmt2 = con.prepareStatement(sql2);
			rs = stmt2.executeQuery();
			int orderPk = 0;
			if (rs.next()) {
				orderPk = rs.getInt("currentSeqNumber");
			}

			stmt = con.prepareStatement(sql);
			stmt.setInt(1, orderPk);
			stmt.setString(2, userId);
			stmt.setInt(3, cartTotalPrice);
			stmt.setString(4, address);
			count = stmt.executeUpdate();

			// 오더 디테일에 넣어주기
			for (CartsDTO l : cartList) {
				OrderDetailsDTO orderDetailsDto = new OrderDetailsDTO();
				ProductsDTO productDto = new ProductsDTO();
				productDto = productsDaoImpl.getProductDetail(l.getProductId());

				orderDetailsDto.setOrderId(orderPk);
				orderDetailsDto.setProductId(l.getProductId());
				orderDetailsDto.setProductCount(l.getProductCount());
				orderDetailsDaoImpl.insertOrderProduct(orderDetailsDto);
				// 상품 수량 수정
				productsDaoImpl.updateProductStock(l.getProductId(),
						productDto.getProductStock() - l.getProductCount());
			}

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			ShoppingMallDataSource.closePreparedStatement(stmt);
			ShoppingMallDataSource.closeConnection(con);
		}
		return count;
	}

	@Override
	public int insertUserOrderfromProductDetail(int productId, String userId, String address, int amount) {
		int count = 0;
		int cartTotalPrice = 0;
		ProductsDAOImpl productsDaoImpl = new ProductsDAOImpl();
		OrderDetailsDAOImpl orderDetailsDaoImpl = new OrderDetailsDAOImpl();
		ProductsDTO productDto = new ProductsDTO();
		productDto = productsDaoImpl.getProductDetail(productId);
		cartTotalPrice = productDto.getProductPrice() * amount;

		Connection con = null;
		PreparedStatement stmt = null;
		PreparedStatement stmt2 = null;
		ResultSet rs = null;

		String sql = "INSERT INTO orders VALUES(?,?,?,?)";
		String sql2 = "SELECT ORDERS_SEQ.NEXTVAL AS currentSeqNumber FROM dual";
		try {

			productDto = productsDaoImpl.getProductDetail(productId);
			cartTotalPrice = productDto.getProductPrice() * amount;
			con = ShoppingMallDataSource.getConnection();
			// order 테이블에 넣기
			stmt2 = con.prepareStatement(sql2);
			rs = stmt2.executeQuery();
			int orderPk = 0;
			if (rs.next()) {
				orderPk = rs.getInt("currentSeqNumber");
			}

			stmt = con.prepareStatement(sql);
			stmt.setInt(1, orderPk);
			stmt.setString(2, userId);
			stmt.setInt(3, cartTotalPrice);
			stmt.setString(4, address);
			count = stmt.executeUpdate();
			// 위에서 넣었던 오더테이블 pk 얻어오기
			OrderDetailsDTO orderDetailsDto = new OrderDetailsDTO();
			orderDetailsDto.setOrderId(orderPk);
			orderDetailsDto.setProductId(productId);
			orderDetailsDto.setProductCount(amount);
			orderDetailsDaoImpl.insertOrderProduct(orderDetailsDto);
			// 상품 수량 수정
			productsDaoImpl.updateProductStock(productId, productDto.getProductStock() - amount);

		} catch (Exception e) {
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

		String sql = "SELECT o.orderId, o.userId, o.totalPrice, o.address, u.userName, u.phoneNumber, od.deliveryStatus "
				+ "FROM orders o LEFT JOIN users u ON o.userId = u.userId "
				+ "LEFT JOIN orderDetails od ON od.orderId = o.orderId ORDER BY o.orderId";
		try {
			con = ShoppingMallDataSource.getConnection();
			stmt = con.prepareStatement(sql);
			rs = stmt.executeQuery();
			while (rs.next()) {
				// 주문 정보
				OrdersDTO ordersDto = new OrdersDTO();
				ordersDto.setOrderId(rs.getInt("orderId"));
				ordersDto.setUserId(rs.getString("userId"));
				ordersDto.setTotalPrice(rs.getInt("totalPrice"));
				ordersDto.setAddress(rs.getString("address"));

				// 사용자 정보
				UsersDTO u = new UsersDTO();
				u.setUserName(rs.getString("userName"));
				u.setPhoneNumber(rs.getString("phoneNumber"));
				ordersDto.setUserDto(u);

				// 배송 정보
				OrderDetailsDTO o = new OrderDetailsDTO();
				o.setDeliveryStatus(rs.getString("deliveryStatus"));
				ordersDto.setOrderDetailsDto(o);

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

	@Override
	public boolean orderCheckByOrderId(int orderId) {
		int count = 0;
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		String sql = "SELECT orderId FROM Orders WHERE orderId = ?";
		try {
			con = ShoppingMallDataSource.getConnection();
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, orderId);
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
