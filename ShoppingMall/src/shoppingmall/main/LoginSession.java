package shoppingmall.main;

public class LoginSession {
	public static String loginUserId;
	public static int isAdmin;

	public static void setLoginUserId(String userId) {
		loginUserId = userId;
	}

	public static String getLoginUserId() {
		return loginUserId;
	}

}
