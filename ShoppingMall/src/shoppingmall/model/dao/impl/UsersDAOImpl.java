package shoppingmall.model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import shoppingmall.main.LoginSession;
import shoppingmall.model.ShoppingMallDataSource;
import shoppingmall.model.dao.UsersDAO;
import shoppingmall.model.dto.UsersDTO;

public class UsersDAOImpl implements UsersDAO {

	@Override
	public boolean checkUserId(String userId) {
		String sql = "SELECT userId FROM users WHERE userId = ?";
		Connection con = null;
		PreparedStatement stmt = null;
		try {
			con = ShoppingMallDataSource.getConnection();
			stmt = con.prepareStatement(sql);
			stmt.setString(1, userId);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return false;
			} else {
				return true;
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			ShoppingMallDataSource.closePreparedStatement(stmt);
			ShoppingMallDataSource.closeConnection(con);
		}
	};

	@Override
	public UsersDTO checkUserInfo(String userName, String phoneNumber) {
		UsersDTO userDto = new UsersDTO();
		String sql = "SELECT userId, phoneNumber FROM users " + "WHERE userName = ? and phoneNumber = ?";
		Connection con = null;
		PreparedStatement stmt = null;

		try {
			con = ShoppingMallDataSource.getConnection();
			stmt = con.prepareStatement(sql);
			stmt.setString(1, userName);
			stmt.setString(2, phoneNumber);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				userDto.setUserId(rs.getString("userId"));
				userDto.setPhoneNumber(rs.getString("phoneNumber"));
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			ShoppingMallDataSource.closePreparedStatement(stmt);
			ShoppingMallDataSource.closeConnection(con);
		}
		return userDto;
	};

	@Override
	public UsersDTO findUserIdByNameAndPhoneNumber(String userName, String phoneNumber) {
		UsersDTO userDto = new UsersDTO();
		String sql = "SELECT userId, phoneNumber FROM users WHERE userName = ? and phoneNumber = ?";
		Connection con = null;
		PreparedStatement stmt = null;

		try {
			con = ShoppingMallDataSource.getConnection();
			stmt = con.prepareStatement(sql);
			stmt.setString(1, userName);
			stmt.setString(2, phoneNumber);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				userDto.setUserId(rs.getString("userId"));
				userDto.setPhoneNumber(rs.getString("phoneNumber"));
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			ShoppingMallDataSource.closePreparedStatement(stmt);
			ShoppingMallDataSource.closeConnection(con);
		}
		return userDto;
	}

	@Override
	public int signUp(UsersDTO userDto, String address) {

		int count = 0;
		int count2 = 0;
		String sql = "INSERT INTO users (userId, password, userName, phoneNumber, birthday, gender) "
				+ "VALUES (?,?,?,?,TO_DATE(?, 'yy/MM/dd'),?)";
		String addressSql = "INSERT INTO addresses(addressId, userId, address) VALUES (ADDRESSES_SEQ.NEXTVAL, ?, ?)";

		Connection con = null;
		PreparedStatement stmt = null;
		PreparedStatement stmt2 = null;
		try {
			con = ShoppingMallDataSource.getConnection();
			stmt = con.prepareStatement(sql);
			stmt.setString(1, userDto.getUserId()); // USERID
			stmt.setString(2, userDto.getPassword()); // PASSWORD
			stmt.setString(3, userDto.getUserName()); // USERNAME
			stmt.setString(4, userDto.getPhoneNumber()); // PHONENUMBER
			stmt.setDate(5, userDto.getBirthday());// BIRTHDAY
			stmt.setString(6, String.valueOf(userDto.getGender())); // GENDER
			count = stmt.executeUpdate();

			stmt2 = con.prepareStatement(addressSql);
			stmt2.setString(1, userDto.getUserId());
			stmt2.setString(2, address);
			count2 = stmt2.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			ShoppingMallDataSource.closePreparedStatement(stmt);
			ShoppingMallDataSource.closePreparedStatement(stmt2);
			ShoppingMallDataSource.closeConnection(con);
		}
		return count;
	}

	@Override
	public int login(String userId, String password) {
		int count = 0;
		String sql = "SELECT userId, isAdmin FROM users WHERE userId = ? AND password = ?";
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			con = ShoppingMallDataSource.getConnection();
			stmt = con.prepareStatement(sql);
			stmt.setString(1, userId);
			stmt.setString(2, password);
			rs = stmt.executeQuery();
			if (rs.next()) {
				LoginSession.isAdmin = rs.getInt("isAdmin");
				return count + 1;
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			ShoppingMallDataSource.closePreparedStatement(stmt);
			ShoppingMallDataSource.closeResultSet(rs);
			ShoppingMallDataSource.closeConnection(con);

		}
		return count;
	}

	@Override
	public int updateUsersInformation(UsersDTO userDto) {
		int count = 0;
		Connection con = null;
		PreparedStatement stmt = null;
		String sql = "UPDATE users SET password=?, userName=? , phoneNumber=? ,birthday = TO_DATE(?, 'yy/MM/dd'), updatedAt = sysdate where userId=?";
		try {
			con = ShoppingMallDataSource.getConnection();
			stmt = con.prepareStatement(sql);
			stmt.setString(1, userDto.getPassword());
			stmt.setString(2, userDto.getUserName());
			stmt.setString(3, userDto.getPhoneNumber());
			stmt.setDate(4, userDto.getBirthday());
			stmt.setString(5, userDto.getUserId());
			count = stmt.executeUpdate();
		} catch (Exception e) {
			throw new RuntimeException();
		} finally {
			ShoppingMallDataSource.closeConnection(con);
			ShoppingMallDataSource.closePreparedStatement(stmt);
		}
		return count;
	}

	@Override
	public UsersDTO getUserInfo(String userId) {
		UsersDTO userDto = new UsersDTO();
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM users WHERE userId = ?";
		try {
			con = ShoppingMallDataSource.getConnection();
			stmt = con.prepareStatement(sql);
			stmt.setString(1, userId);
			rs = stmt.executeQuery();
			if (rs.next()) {
				userDto.setUserId(rs.getString("userId"));
				userDto.setPassword(rs.getString("password"));
				userDto.setUserName(rs.getString("userName"));
				userDto.setPhoneNumber(rs.getString("phoneNumber"));
				userDto.setBirthday(rs.getDate("birthday"));
				userDto.setGender(rs.getString("gender").charAt(0));
				userDto.setCreatedAt(rs.getTimestamp("createdAt"));
				userDto.setUpdatedAt(rs.getTimestamp("updatedAt"));
			}
		} catch (Exception e) {
			throw new RuntimeException();
		} finally {
			ShoppingMallDataSource.closeConnection(con);
			ShoppingMallDataSource.closePreparedStatement(stmt);
			ShoppingMallDataSource.closeResultSet(rs);
		}

		return userDto;
	}

}
