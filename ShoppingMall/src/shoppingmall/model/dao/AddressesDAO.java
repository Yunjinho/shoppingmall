package shoppingmall.model.dao;

import java.util.ArrayList;

import shoppingmall.model.dto.AddressesDTO;

public interface AddressesDAO {
	ArrayList<AddressesDTO> getUserAddresses(String userId);
	int insertAddresses(AddressesDTO addressesDto);
	int deleteAddresses(int addressesId);
	int updateAddresses(AddressesDTO addressesDto);
}
