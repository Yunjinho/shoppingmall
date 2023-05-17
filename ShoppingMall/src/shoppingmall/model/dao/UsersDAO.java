package shoppingmall.model.dao;

import shoppingmall.model.dto.UsersDTO;

public interface UsersDAO {
	boolean checkUserId(String userId);

	int signUp(UsersDTO userDto, String address);

	int login(String userId, String password);

	int insertUsersInformation(UsersDTO userDto);

	int updateUsersInformation(UsersDTO userDto);
}
