package shoppingmall.model.dao;

import shoppingmall.model.dto.UsersDTO;

public interface UsersDAO {
	boolean checkUserId(String userId); // 이미 존재하는 회원인지 확인

	int signUp(UsersDTO userDto, String address); // 회원 가입

	int login(String userId, String password); // 로그인

	int updateUsersInformation(String userId, String phoneNumber, String address); // 사용자 정보 수정
}
