package shoppingmall.model.dto;

import java.sql.Timestamp;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReviewsDTO {
	int reviewId;
	int productId;
	int userId;
	String content;
	Timestamp createdAt;
	Timestamp updatedAt;
}
