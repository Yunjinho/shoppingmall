package shoppingmall.model.dto;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductsDTO {
	int productId;
	String productName;
	int productPrice;
	int productStock;
	String productinfo;
	int categoryId;
	Timestamp createdAt;
	Timestamp updatedAt;

//-----------------------
	int rowNumber;
	String categoryName;
}
