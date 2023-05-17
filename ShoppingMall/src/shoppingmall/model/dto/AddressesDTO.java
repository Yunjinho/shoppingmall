package shoppingmall.model.dto;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AddressesDTO {
	int addressId;
	String userId;
	String address;
	Timestamp createdAt;
	Timestamp updatedAt;
}
