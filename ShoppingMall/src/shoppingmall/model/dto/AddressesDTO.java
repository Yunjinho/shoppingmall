package shoppingmall.model.dto;

import java.sql.Timestamp;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AddressesDTO {
	String userId;
	String password;
	String userName;
	String phoneNumber;
	Date birthday;
	char isAdmin;// 어드민인지 아닌지 ㅊ체크
	char gender;
	Timestamp createdAt;
	Timestamp updatedAt;
}
