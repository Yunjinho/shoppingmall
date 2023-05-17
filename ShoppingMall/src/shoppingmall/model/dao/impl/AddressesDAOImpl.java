package shoppingmall.model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import shoppingmall.model.ShoppingMallDataSource;
import shoppingmall.model.dao.AddressesDAO;
import shoppingmall.model.dto.AddressesDTO;

public class AddressesDAOImpl implements AddressesDAO {

	@Override
	public ArrayList<AddressesDTO> getUserAddresses(String userId) {
		ArrayList<AddressesDTO> list =new ArrayList<>();
		Connection con= null;
		PreparedStatement stmt=null;
		ResultSet rs=null;
		String sql="select * from addresses where userid=?";
		try {
			con=ShoppingMallDataSource.getConnection();
			stmt=con.prepareStatement(sql);
			stmt.setString(1, userId);
			rs=stmt.executeQuery();
			while(rs.next()) {
				AddressesDTO addressesDto=new AddressesDTO();
				addressesDto.setAddressId(rs.getInt("addressId"));
				addressesDto.setUserId(rs.getString("userId"));
				addressesDto.setAddress(rs.getString("address"));
				addressesDto.setCreatedAt(rs.getTimestamp("createdAt"));
				addressesDto.setUpdatedAt(rs.getTimestamp("updatedAt"));
				list.add(addressesDto);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if(con!=null)
				ShoppingMallDataSource.closeConnection(con);
			if(stmt!=null) 
				try {stmt.close();}catch (SQLException e) {e.printStackTrace();}
			if(rs!=null) 
				try {rs.close();}catch (SQLException e) {e.printStackTrace();}
		}
		return list;
	}

	@Override
	public int insertAddresses(AddressesDTO addressesDto) {
		int count=0;
		Connection con= null;
		PreparedStatement stmt=null;
		String sql="insert into addresses values(addresses_seq,?,?,sysdate,sysdate)";
		try {
			con=ShoppingMallDataSource.getConnection();
			stmt=con.prepareStatement(sql);
			stmt.setString(1, addressesDto.getUserId());
			stmt.setString(2, addressesDto.getAddress());
			count=stmt.executeUpdate();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if(con!=null)
				ShoppingMallDataSource.closeConnection(con);
			if(stmt!=null) 
				try {stmt.close();}catch (SQLException e) {e.printStackTrace();}
		}
		return count;
	}

	@Override
	public int deleteAddresses(int addressesId) {
		int count=0;
		Connection con= null;
		PreparedStatement stmt=null;
		String sql="deelte from addresses where addressesId=?";
		try {
			con=ShoppingMallDataSource.getConnection();
			stmt=con.prepareStatement(sql);
			stmt.setInt(1, addressesId);
			count=stmt.executeUpdate();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if(con!=null)
				ShoppingMallDataSource.closeConnection(con);
			if(stmt!=null) 
				try {stmt.close();}catch (SQLException e) {e.printStackTrace();}
		}
		return count;
	}

	@Override
	public int updateAddresses(AddressesDTO addressesDto) {
		int count=0;
		Connection con= null;
		PreparedStatement stmt=null;
		String sql="update addresses set address=?,updatedAt=sysdate where addressId=?";
		try {
			con=ShoppingMallDataSource.getConnection();
			stmt=con.prepareStatement(sql);
			stmt.setString(1, addressesDto.getAddress());
			stmt.setInt(2, addressesDto.getAddressId());
			count=stmt.executeUpdate();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if(con!=null)
				ShoppingMallDataSource.closeConnection(con);
			if(stmt!=null) 
				try {stmt.close();}catch (SQLException e) {e.printStackTrace();}
		}
		return count;
	}

}
