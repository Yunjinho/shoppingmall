package shoppingmall.model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import shoppingmall.model.ShoppingMallDataSource;
import shoppingmall.model.dao.UsersDAO;
import shoppingmall.model.dto.UsersDTO;

public class UsersDAOImpl implements UsersDAO {

	@Override
	public boolean checkUserId(String userId) {
		String sql = "SELECT USERID FROM USERS WHERE USERID = ?";
		Connection con = null;
		PreparedStatement stmt = null;
		System.out.println(userId);
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
			// TODO: handle exception
			throw new RuntimeException(e);
		} finally {
			try {
				stmt.close();
			} catch (SQLException e) {
				// TODO: handle exception
			}
			ShoppingMallDataSource.closeConnection(con);
		}
	};

	@Override
	public int signUp(UsersDTO userDto, String address) {
		// TODO Auto-generated method stub
		int count = 0;
		int count2 = 0;
		String sql = "INSERT INTO USERS (USERID, PASSWORD, USERNAME, PHONENUMBER, BIRTHDAY, GENDER) "
				+ "VALUES (?,?,?,?," + "TO_DATE('" + userDto.getBirthday() + "', 'yy/MM/dd'),?)";
		String addressSql = "INSERT INTO ADDRESSES(ADDRESSID, USERID, ADDRESS) VALUES (ADDRESSES_SEQ.NEXTVAL, ?, ?)";

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
			stmt.setString(5, String.valueOf(userDto.getGender())); // GENDER

			stmt2 = con.prepareStatement(addressSql);
			stmt2.setString(1, userDto.getUserId());
			stmt2.setString(2, address);
			count = stmt.executeUpdate();
			count2 = stmt2.executeUpdate();
		} catch (SQLException e) {
			// TODO: handle exception
			throw new RuntimeException(e);
		} finally {
			ShoppingMallDataSource.closeConnection(con);
			try {
				stmt.close();
				stmt2.close();
			} catch (SQLException e) {
				// TODO: handle exception
			}
		}
		return count;
	}

	@Override
	public int login(String userId, String password) {
		int count = 0;
		String sql = "SELECT USERID FROM USERS WHERE USERID = ? and PASSWORD = ?";
		Connection con = null;
		PreparedStatement stmt = null;
		try {
			con = ShoppingMallDataSource.getConnection();
			stmt = con.prepareStatement(sql);
			stmt.setString(1, userId);
			stmt.setNString(2, password);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return count + 1;
			}
		} catch (SQLException e) {
			// TODO: handle exception
			throw new RuntimeException(e);
		} finally {
			ShoppingMallDataSource.closeConnection(con);
			try {
				stmt.close();
			} catch (SQLException e) {
				// TODO: handle exception
			}
		}
		return count;
	}

	@Override
	public int insertUsersInformation(UsersDTO userDto) {
		return 0;
	};

	@Override
	public int updateUsersInformation(UsersDTO userDto) {
		return 0;
	};

}
