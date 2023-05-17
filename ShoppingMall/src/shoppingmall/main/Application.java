package shoppingmall.main;

import java.util.Scanner;

import shoppingmall.model.dao.impl.UsersDAOImpl;
import shoppingmall.model.dto.UsersDTO;

public class Application {
	private static Scanner sc = new Scanner(System.in);

	static void login() {
		while (true) {
			System.out.print("아이디를 입력하세요: ");
			String userId = sc.next();

			System.out.print("비밀번호를 입력하세요: ");
			String password = sc.next();

			UsersDAOImpl userDao = new UsersDAOImpl();
			int check = userDao.login(userId, password);
			if (check > 0) {
				System.out.println("로그인 되었습니다.");
				System.out.println();
				break;
			} else {
				System.out.println("아이디나 비밀번호가 틀렸습니다.");
				System.out.println();
			}
		}
	}

	static void signUpUserInfo() {
		UsersDTO user = new UsersDTO();
		UsersDAOImpl userDao = new UsersDAOImpl();
		try {
			while (true) {
				System.out.print("아이디: ");
				user.setUserId(sc.next());
				boolean check = userDao.checkUserId(user.getUserId());
				if (check == false) {
					System.out.println("이미 존재하는 아이디입니다.");
					System.out.println();
					continue;
				} else {
					break;
				}
			}

			System.out.print("비밀번호: ");
			user.setPassword(sc.next());

			System.out.print("이름: ");
			user.setUserName(sc.next());

			System.out.print("핸드폰 번호[010-1234-1234]: ");
			user.setPhoneNumber(sc.next());

			System.out.print("생일[YYYY-MM-DD]: ");
			String birth = sc.next();
			java.sql.Date date = java.sql.Date.valueOf(birth);
			System.out.println("MAIN");
			user.setBirthday(date);

			System.out.print("성별[W/M]: ");
			user.setGender(sc.next().charAt(0));
			sc.nextLine();

			System.out.print("주소: ");
			String address = sc.nextLine();
			int check = userDao.signUp(user, address);

			if (check >= 1)
				System.out.println("회원가입이 완료되었습니다.");

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
	}

	public static void main(String[] args) {
		System.out.println("1. 회원가입 | 2. 로그인");
		int cmd = sc.nextInt();
		if (cmd == 1) {
			signUpUserInfo();
		} else if (cmd == 2) {
			login();
		}

=======

	public static void main(String[] args) throws Exception  {
>>>>>>> 817848f0b6c2b36fb9c574acaa5a77ce35cca47e
	}
}
