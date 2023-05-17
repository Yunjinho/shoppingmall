package shoppingmall.model.dto;

import java.sql.Date;
import java.sql.Timestamp;

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
	char isAdmin; // 어드민인지 아닌지 ㅊ체크
	char gender;
	Timestamp createdAt;
	Timestamp updatedAt;
}
