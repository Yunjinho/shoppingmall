package shoppingmall.model.dao;

import java.util.ArrayList;

import shoppingmall.model.dto.AddressesDTO;

public interface AddressesDAO {
	ArrayList<AddressesDTO> getUserAddresses(String userId); // 사용자 주소 모두 조회

	int insertAddresses(AddressesDTO addressesDto); // 주소 등록

	int deleteAddresses(int addressesId); // 주소 삭제

	int updateAddresses(AddressesDTO addressesDto); // 주소 업데이트
}
