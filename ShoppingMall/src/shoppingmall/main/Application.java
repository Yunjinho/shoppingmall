package shoppingmall.main;

import java.util.Scanner;

import shoppingmall.model.dao.impl.ProductsDAOImpl;
import shoppingmall.model.dao.impl.UsersDAOImpl;
import shoppingmall.model.dto.ProductsDTO;
import shoppingmall.model.dto.UsersDTO;

public class Application {
	private static Scanner sc = new Scanner(System.in);
	private static String loginUserId = "";

	static boolean login() {
		String userId;
		String password;

		System.out.print("아이디를 입력하세요: ");
		userId = sc.next();

		System.out.print("비밀번호를 입력하세요: ");
		password = sc.next();

		UsersDAOImpl userDao = new UsersDAOImpl();
		int check = userDao.login(userId, password);
		// 로그인 성공
		if (check > 0) {
			System.out.println("로그인 되었습니다.");
			System.out.println();
			loginUserId = userId;
			return true;
		}
		// 로그인 실패
		else {
			System.out.println("아이디나 비밀번호가 틀렸습니다. 다시 선택해 주세요.");
			System.out.println();
			return false;
		}
	}

	static boolean signUpUserInfo() {
		UsersDTO user = new UsersDTO();
		UsersDAOImpl userDao = new UsersDAOImpl();
		boolean result = false;
		try {
			System.out.print("아이디: ");
			user.setUserId(sc.next());
			boolean idCheck = userDao.checkUserId(user.getUserId());
			// 아이디가 존재할 경우
			if (idCheck == false) {
				System.out.println("이미 존재하는 아이디입니다. 다른 아이디를 입력해주세요.");
				System.out.println();
				return result;
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
			// 회원가입 성공 시
			if (check >= 1) {
				result = true;
				System.out.println("회원가입이 완료되었습니다. 로그인 해주세요.");
				System.out.println();
			} else {
				System.out.println("회원가입에 실패하였습니다. 다시 선택해주세요.");
				System.out.println();
			}

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		return result;
	}

	static int registerProduct() {
		int count = 0;
		ProductsDTO productsDto = new ProductsDTO();
		ProductsDAOImpl productsDao = new ProductsDAOImpl();
		return count;
	}

	public static void main(String[] args) {
		while (true) {
			System.out.println("1. 회원가입 | 2. 로그인 | 3. 나가기");
			int cmd = sc.nextInt();
			switch (cmd) {
			// 1. 회원가입
			case 1: {
				boolean result = signUpUserInfo();
				// 회원가입 성공 result = true
				// 회원가입 실패 result = false;
				break;
			}
			// 2. 로그인
			case 2: {
				boolean result = login();
				// 로그인 성공
				if (result) {
					// 관리자일 경우
					if (loginUserId.equals("admin")) {
						// while문 탈출 시킬 flag
						boolean adminFlag = true;
						// 상품 등록/수정/삭제
						while (adminFlag) {
							System.out
									.println("1. 상품 등록 | 2. 상품 수정 | 3. 상품 삭제 | 4. 배송 목록 조회 | 5. 배송 상태 조회 | 6. 로그 아웃 ");
							System.out.print("번호를 입력하세요: ");
							int adminCommand = sc.nextInt();
							switch (adminCommand) {
							case 1:
								System.out.println("상품 등록 페이지 입니다.");
								System.out.println();

								registerProduct();
								break;
							case 2:
								System.out.println("상품 수정 페이지 입니다.");
								System.out.println();
								// 상품 목록 보여주고 선택
								/*
								 * boolean productFlag = true; while(productFlag)
								 */
								System.out.println("1. 상품 수정 | 2. 상품 수량 수정 | 3. 뒤로 가기");
								System.out.println("번호를 입력하세요: ");
								int productCommand = sc.nextInt();
								// 1. 상품 수정
								if (productCommand == 1) {

								}
								// 2. 상품 수량 수정
								else if (productCommand == 2) {
								} else if (productCommand == 3) {
									// productFlag = false;
								} else {
									System.out.println("번호를 잘못 입력하였습니다.");
								}
								break;
							case 3:
								System.out.println("상품 삭제 페이지 입니다.");
								System.out.println();
								break;
							case 4:
								System.out.println("배송 목록 조회 페이지 입니다.");
								System.out.println();
								break;
							case 5:
								System.out.println("배송 상태 조회 페이지 입니다. ");
								System.out.println();
								break;
							case 6:
								System.out.println("로그아웃 되었습니다.");
								System.out.println();
								loginUserId = "";
								break;
							default:
								System.out.println("숫자를 잘못 입력하였습니다.");
								System.out.println();
								break;
							}
							if (adminCommand == 6)
								break;
						}
					}

					// 일반 사용자일 경우
					else {
						// while문 탈출 시킬 flag
						boolean userFlag = true;
						while (userFlag) {
							System.out.println(
									"1. 사용자 정보 수정 | 2. 주소지 추가/수정  | 3. 전체 상품 목록 조회 | 4. 카테고리별 상품 보기 | 5. 장바구니 목록 조회 및 결제 | 6. 로그아웃");
							System.out.print("번호를 입력하세요: ");
							int userCommand = sc.nextInt();
							switch (userCommand) {
							case 1: {
								System.out.println("사용자 정보 수정페이지 입니다.");
								System.out.println();
								break;
							}
							case 2: {
								System.out.println("주소지 추가/수정 페이지 입니다.");
								System.out.println();
								/*
								 * boolean addressFlag = true; while(addressFlag)
								 */
								System.out.println("1. 주소 목록 조회  | 2. 주소지 추가 | 3.주소지 수정 | 4. 주소지 삭제 | 5. 뒤로 가기");
								System.out.print("번호를 입력하세요: ");
								int addressCommand = sc.nextInt();
								System.out.println();
								// 뒤로가기 하려면 if문 / 상관없으면 switch
								// 1.주소 목록 조회
								if (addressCommand == 1) {
								}
								// 2. 주소지 추가
								else if (addressCommand == 2) {
								}
								// 3. 주소지 수정
								else if (addressCommand == 3) {
								}
								// 4. 주소지 삭제
								else if (addressCommand == 4) {
								}
								// 5. 뒤로 가기
								else {
									// addressFlag = false;
								}
								break;
							}
							case 3: {
								System.out.println("전체 상품 목록 조회 페이지입니다.");
								System.out.println();
								break;
							}
							case 4: {
								System.out.println("카테고리별 상품 보기 페이지입니다.");
								System.out.println();

								// index = categoryId
								System.out.println("1. 상의 | 2. 하의 | 3. 신발  ");
								break;
							}
							case 5: {
								System.out.println("장바구니 목록 조회 페이지 입니다.");
								System.out.println();
								// boolean cartFlag = true; while(cartFlag)
								System.out.println("1. 장바구니 조회 | 2. 장바구니 수정 | 3. 장바구니 상품 결제 | 4. 뒤로 가기");
								System.out.print("번호를 입력하세요: ");
								int cartCommand = sc.nextInt();
								System.out.println();
								// 1. 장바구니 조회
								if (cartCommand == 1) {
								}
								// 2. 장바구니 수정
								else if (cartCommand == 2) {
								}
								// 3. 장바구니 상품 결제
								else if (cartCommand == 3) {
								}
								// 4. 뒤로 가기
								else if (cartCommand == 4) {
									// cartFlag = false;
								}
								break;
							}
							case 6: {
								System.out.println("로그아웃 되었습니다.");
								System.out.println();
								loginUserId = "";
								break;
							}
							default: {
								System.out.println("숫자를 잘못 입력하였습니다.");
								System.out.println();
							}
							}
							// while문 나가는 break;
							if (userCommand == 6)
								break;
						}
					}
				}
				// 로그인 실패
				// case2 로그아웃 하는 break;
				break;
			}
			// 3. 나가기
			case 3: {
				System.out.println("시스템이 종료되었습니다.");
				System.exit(0);
				break;
			}
			// 잘못 입력
			default: {
				System.out.println("다시 선택해 주세요.");
				System.out.println();
				break;
			}
			}

		}
	}
}
