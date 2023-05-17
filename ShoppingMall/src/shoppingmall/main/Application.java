package shoppingmall.main;

import java.util.Scanner;

import shoppingmall.model.dao.impl.UsersDAOImpl;
import shoppingmall.model.dto.UsersDTO;

public class Application {
	private static Scanner sc = new Scanner(System.in);
	private static String loginUserId = "";

	static boolean login() {
		String userId;
		String password;
		boolean result = true;
		while (true) {
			System.out.print("아이디를 입력하세요: ");
			userId = sc.next();

			System.out.print("비밀번호를 입력하세요: ");
			password = sc.next();

			UsersDAOImpl userDao = new UsersDAOImpl();
			int check = userDao.login(userId, password);
			if (check > 0) {
				System.out.println("로그인 되었습니다.");
				System.out.println();
				loginUserId = userId;
				return false;
			} else {
				System.out.println("아이디나 비밀번호가 틀렸습니다.");
				System.out.println();
				return true;
			}
		}
	}

	static boolean signUpUserInfo() {
		UsersDTO user = new UsersDTO();
		UsersDAOImpl userDao = new UsersDAOImpl();
		boolean result = false;
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
			user.setBirthday(date);

			System.out.print("성별[W/M]: ");
			user.setGender(sc.next().charAt(0));
			sc.nextLine();

			System.out.print("주소: ");
			String address = sc.nextLine();
			int check = userDao.signUp(user, address);
			if (check >= 1)
				result = true;
			System.out.println("회원가입이 완료되었습니다.");

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		return result;
	}

	public static void main(String[] args) {
		while (true) {
			System.out.println("1. 회원가입 | 2. 로그인 | 3. 나가기");
			int cmd = sc.nextInt();
			if (cmd == 1) {
				boolean result = signUpUserInfo();
				if (result == true) {
				} else {
					System.out.println("다시 선택해 주세요.");
					System.out.println();
					continue;
				}
			} else if (cmd == 2) {
				boolean result = true;
				result = login();
				while (result) {
					result = login();
					System.out.println("B: 뒤로가기 | C: 계속하기");
					String c = sc.next();
					if (c.toLowerCase().equals("b")) {
						break;
					} else {
						System.out.println("잘못입력하였습니다.");
					}
				}
				// 관리자일 경우
				if (loginUserId.equals("admin")) {
					// 상품 등록/수정/삭제
					while (true) {
						System.out.println("1. 상품 등록 | 2. 상품 수정 | 3. 상품 삭제 | 4. 뒤로 가기 | 5. 로그 아웃 ");
						System.out.print("번호를 입력하세요: ");
						int n = sc.nextInt();
						switch (n) {
						case 1:
							System.out.println("상품 등록 페이지 입니다.");
							System.out.println();
							break;
						case 2:
							System.out.println("상품 수정 페이지 입니다.");
							System.out.println();
							break;
						case 3:
							System.out.println("상품 삭제 페이지 입니다.");
							System.out.println();
							break;
						case 4:
							System.out.println("이전 페이지로 돌아갑니다.");
							System.out.println();
							break;
						case 5:
							System.out.println("로그아웃 되었습니다.");
							System.out.println();
							loginUserId = "";
							break;
						default:
							System.out.println("숫자를 잘못 입력하였습니다.");
							System.out.println();
							break;
						}
						if (n == 4 || n == 5)
							break;
					}
				}
				// 일반 사용자일 경우
				else {
					System.out.println("1. 사용자 정보 수정 | 2. 주소지 추가 ");
				}
			} else if (cmd == 3) {
				System.out.println("시스템이 종료되었습니다.");
				System.exit(0);
				break;
			} else {
				System.out.println("다시 선택해 주세요.");
				System.out.println();
			}
		}
	}
}
