package shoppingmall.model.dao;

import java.util.ArrayList;

public interface AddressesDAO {
	ArrayList<AddressesDAO> getUserAddresses(String userId);
	int insertAddresses(AddressesDAO addressesDao);
	int deleteAddresses(int addressesId);
	int updateAddresses(AddressesDAO addressesDao);
}
