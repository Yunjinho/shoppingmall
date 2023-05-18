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

//	@Override
//	public int insertUserOrderfromProductDetail(int productId, String userId, String address, int amount) {
//		int count = 0;
//		int cartTotalPrice = 0;
//		Connection con = null;
//		PreparedStatement stmt = null;
//		ProductsDAOImpl productsDaoImpl = new ProductsDAOImpl();
//		ProductsDTO productDto = new ProductsDTO();
//
//		String sql = "INSERT INTO ORDERS VALUES(ORDERS_SEQ.NEXTVAL,?,?,?)";
//		try {
//			ProductsDAOImpl productsDaoImpl = new ProductsDAOImpl();
//			ProductsDTO productDto = new ProductsDTO();
//			OrderDetailsDAOImpl orderDetailsDaoImpl=new OrderDetailsDAOImpl();
//			productDto = productsDaoImpl.getProductDetail(productId);
//			cartTotalPrice = productDto.getProductPrice() * amount;
//		PreparedStatement stmt2 = null;
//		
//		ResultSet rs=null;
//		String sql = "INSERT INTO ORDERS VALUES(?,?,?,?)";
//		String sql2= "select ORDERS_SEQ.NEXTVAL as currentSeqNumber from dual";
//		try {
//			
//			productDto=productsDaoImpl.getProductDetail(productId);
//			cartTotalPrice=productDto.getProductPrice()*amount;
//			con = ShoppingMallDataSource.getConnection();
//			
//			//order 테이블에 넣기
//			stmt2=con.prepareStatement(sql2);
//			rs=stmt2.executeQuery();
//			int orderPk=0;
//			if(rs.next()) {
//				orderPk=rs.getInt("currentSeqNumber");
//			}
//			
//			stmt = con.prepareStatement(sql);
//			stmt.setInt(1, orderPk);
//			stmt.setString(2, userId);
//			stmt.setInt(3, cartTotalPrice);
//			stmt.setString(4, address);
//			count = stmt.executeUpdate();
//			//위에서 넣었던 오더테이블 pk 얻어오기
//			OrderDetailsDTO orderDetailsDto=new OrderDetailsDTO(0,orderPk,productId,amount,null,null,null);
//			orderDetailsDaoImpl.insertOrderProduct(orderDetailsDto);
//			//상품 수량 수정
//			productsDaoImpl.updateProductStock(productId, productDto.getProductStock()-amount);
//			
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//			throw new RuntimeException(e);
//		} finally {
//			ShoppingMallDataSource.closePreparedStatement(stmt);
//			ShoppingMallDataSource.closeConnection(con);
//		}
//		return count;
//	}

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

	@Override
	public int insertUserOrderfromProductDetail(int productId, String userId, int addressId, int amount,
			AddressesDTO address) {
		// TODO Auto-generated method stub
		return 0;
	}

}
