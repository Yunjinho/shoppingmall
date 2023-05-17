package shoppingmall.model.dto;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CartsDTO {
	int cartId;
	String userId;
	int productId;
	Timestamp createdAt;
	Timestamp updatedAt;
}
