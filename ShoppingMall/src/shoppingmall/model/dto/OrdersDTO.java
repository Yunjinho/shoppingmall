package shoppingmall.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrdersDTO {
	int orderId;
	String userId;
	String address;
	int totalPrice;

	// --------------
	UsersDTO user;
}
