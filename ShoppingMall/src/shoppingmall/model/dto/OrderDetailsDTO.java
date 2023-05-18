package shoppingmall.model.dto;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderDetailsDTO {
	int orderDetailId;
	int orderId;
	int productId;
	int productCount;
	String deliveryStatus;
	Timestamp createdAt;
	Timestamp updatedAt;
	// ----------------------
	ProductsDTO product;
	UsersDTO user;
}
