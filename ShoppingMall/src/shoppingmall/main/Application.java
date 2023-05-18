package shoppingmall.main;

import java.util.Scanner;

public class Application {
	private static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {

		while (true) {
			System.out.println("1. 회원가입 | 2. 로그인 | 3. 나가기");
			int cmd = sc.nextInt();
			switch (cmd) {
			// 1. 회원가입
			case 1: {
				boolean result = MainFunction.signUpUserInfo();
				// 회원가입 성공 result = true
				// 회원가입 실패 result = false;
				break;
			}
			// 2. 로그인
			case 2: {
				boolean result = MainFunction.login();
				// 로그인 성공
				if (result) {
					// 관리자일 경우
					if (LoginSession.getLoginUserId().equals("admin")) {
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

								MainFunction.registerProduct();
								break;
							case 2:
								System.out.println("상품 수정 페이지 입니다.");
								System.out.println();
								// 상품 목록 보여주고 선택
								/*
								 * boolean productFlag = true; while(productFlag)
								 */
								boolean productFlag = true;
								while (productFlag) {
									System.out.println("1. 상품 수정 | 2. 상품 수량 수정 | 3. 뒤로 가기");
									System.out.print("번호를 입력하세요: ");
									int productCommand = sc.nextInt();
									// 1. 상품 수정
									if (productCommand == 1) {

									}
									// 2. 상품 수량 수정
									else if (productCommand == 2) {
									} else if (productCommand == 3) {
										productFlag = false;
									} else {
										System.out.println("번호를 잘못 입력하였습니다.");
									}
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
								LoginSession.setLoginUserId("");
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
								
								MainFunction.modifyUserInfo(LoginSession.getLoginUserId());
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
									MainFunction.inquireAddress(LoginSession.getLoginUserId());
								}
								// 2. 주소지 추가
								else if (addressCommand == 2) {
									MainFunction.addAddress(LoginSession.getLoginUserId());
								}
								// 3. 주소지 수정
								else if (addressCommand == 3) {
									MainFunction.modifyAddress(LoginSession.getLoginUserId());
								}
								// 4. 주소지 삭제
								else if (addressCommand == 4) {
									MainFunction.deleteAddress(LoginSession.getLoginUserId());
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
								MainFunction.inquireProductsByCategory();
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
								LoginSession.loginUserId = "";
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
