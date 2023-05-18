package shoppingmall.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CategoriesDTO {
	int categoryId;
	String categoryName;
	//----
	int rowNumber;
}
