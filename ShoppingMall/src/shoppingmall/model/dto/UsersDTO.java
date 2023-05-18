package shoppingmall.model.dto;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UsersDTO {
	String userId;
	String password;
	String userName;
	String phoneNumber;
	Date birthday; // java.sql.Date
	char isAdmin; // 어드민인지 아닌지 체크
	char gender;
	Timestamp createdAt;
	Timestamp updatedAt;
	
	
	// 조인 위한 변수 
	List<AddressesDTO> addressDto;
}
