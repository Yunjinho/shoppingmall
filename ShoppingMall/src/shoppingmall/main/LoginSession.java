package shoppingmall.main;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginSession {
	public static String loginUserId;

	public static void setLoginUserId(String userId) {
		loginUserId = userId;
	}

	public static String getLoginUserId() {
		return loginUserId;
	}

}
