package shoppingmall.model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import shoppingmall.model.ShoppingMallDataSource;
import shoppingmall.model.dao.AddressesDAO;
import shoppingmall.model.dto.AddressesDTO;

public class AddressesDAOImpl implements AddressesDAO {

	@Override
	public ArrayList<AddressesDTO> getUserAddresses(String userId) {
		ArrayList<AddressesDTO> addressList = new ArrayList<>();
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM addresses WHERE userId = ?";
		try {
			con = ShoppingMallDataSource.getConnection();
			stmt = con.prepareStatement(sql);
			stmt.setString(1, userId);
			rs = stmt.executeQuery();
			while (rs.next()) {
				AddressesDTO addressesDto = new AddressesDTO();
				addressesDto.setAddressId(rs.getInt("addressId"));
				addressesDto.setUserId(rs.getString("userId"));
				addressesDto.setAddress(rs.getString("address"));
				addressesDto.setCreatedAt(rs.getTimestamp("createdAt"));
				addressesDto.setUpdatedAt(rs.getTimestamp("updatedAt"));
				addressList.add(addressesDto);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			ShoppingMallDataSource.closePreparedStatement(stmt);
			ShoppingMallDataSource.closeResultSet(rs);
			ShoppingMallDataSource.closeConnection(con);
		}
		return addressList;
	}

	@Override
	public int insertAddresses(String userId,String address) {
		int count = 0;
		Connection con = null;
		PreparedStatement stmt = null;
		String sql = "INSERT INTO addresses VALUES(ADDRESSES_SEQ.NEXTVAL,?,?,SYSDATE,SYSDATE)";
		try {
			con = ShoppingMallDataSource.getConnection();
			stmt = con.prepareStatement(sql);
			stmt.setString(1, userId);
			stmt.setString(2, address);
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
	public int deleteAddresses(int addressesId) {
		int count = 0;
		Connection con = null;
		PreparedStatement stmt = null;
		String sql = "DELETE FROM addresses WHERE addressId = ?";
		try {
			con = ShoppingMallDataSource.getConnection();
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, addressesId);
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
	public int updateAddresses(AddressesDTO addressesDto) {
		int count = 0;
		Connection con = null;
		PreparedStatement stmt = null;
		String sql = "UPDATE addresses SET address = ?,updatedAt = SYSDATE WHERE addressId=?";
		try {
			con = ShoppingMallDataSource.getConnection();
			stmt = con.prepareStatement(sql);
			stmt.setString(1, addressesDto.getAddress());
			stmt.setInt(2, addressesDto.getAddressId());
			count = stmt.executeUpdate();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			ShoppingMallDataSource.closePreparedStatement(stmt);
			ShoppingMallDataSource.closeConnection(con);
		}
		return count;
	}

}
