package shoppingmall.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import shoppingmall.main.admin.MainAdminOrderFunction;
import shoppingmall.main.admin.MainAdminProductFunction;
import shoppingmall.main.user.MainUserFunction;
import shoppingmall.model.dto.CartsDTO;
import shoppingmall.model.dto.ProductsDTO;
import shoppingmall.model.dto.UsersDTO;

public class Application {
	private static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {
		try {
			while (true) {
				System.out.println("1.[회원가입] | 2.[로그인] | 3.[프로그램 종료]");
				System.out.print("번호 입력: ");
				int cmd = sc.nextInt();
				switch (cmd) {
				// 1. 회원가입
				case 1: {
					boolean result = MainUserFunction.signUpUserInfo();
					// 회원가입 성공 result = true
					// 회원가입 실패 result = false;
					break;
				}
				// 2. 로그인
				case 2: {
					// login
					boolean result = MainUserFunction.login();
					// 로그인 성공
					if (result) {
						// 관리자일 경우
						if (LoginSession.isAdmin == 1) {
							// 상품 관리
							while (true) {
								System.out.println(
										"--------------------------------- [관리자 페이지] ---------------------------------");
								System.out.println("1. [상품 관리] | 2. [배송 관리] | 3. [로그 아웃]");
								System.out.print("번호를 입력하세요: ");
								int adminCommand = sc.nextInt();
								sc.nextLine();
								System.out.println();

								// 로그아웃 / while문 탈출
								if (adminCommand == 3) {
									System.out.println("로그아웃 되었습니다.");
									System.out.println();
									LoginSession.setLoginUserId("");
									break;
								}

								switch (adminCommand) {
								// 1. 상품 관리
								case 1: {
									System.out.println(
											"------------------ [관리자 페이지] -> [상품 관리] -> [상품 정보] -------------------");
									// 상품 리스트 보여주기
									MainAdminProductFunction.productsManagement();
									break;
								}
								// 2. 배송 관리
								case 2: {
									System.out.println(
											"------------------------- [관리자 페이지] -> [배송 관리] -> [주문 내역] --------------------------");
									MainAdminOrderFunction.orderManagement();
									System.out.println("");
									break;
								}
								default:
									System.out.println("숫자를 잘못 입력하였습니다.");
									System.out.println();
									break;
								}
							}
						}

						// 일반 사용자일 경우
						else {
							// while문 탈출 시킬 flag
							boolean userFlag = true;
							while (userFlag) {
								System.out.println();
								System.out
										.println("---------------------------Shopping Mall---------------------------");
								System.out.println(
										"1. 사용자 정보 수정 | 2. 주소지 추가/수정  | 3. 카테고리별 상품 보기 | 4. 장바구니 목록 조회 및 결제 | 5. 배송 상태 확인  | 6. 로그아웃");
								System.out.print("번호를 입력하세요: ");
								int userCommand = sc.nextInt();
								switch (userCommand) {
								case 1: {
									System.out.println();
									System.out.println(
											"---------------------------[ 사용자 정보 수정 ]--------------------------");
									System.out.println();
									UsersDTO userDto = null;
									userDto = MainUserFunction.viewUserInfo(LoginSession.getLoginUserId());
									System.out.println();
									boolean flag = true;

									while (flag) {
										System.out.println("1. 일반 정보 수정 | 2. 비밀번호 변경 | 3. 뒤로가기");
										System.out.print("번호를 입력하세요: ");
										int command = sc.nextInt();
										System.out.println();
										switch (command) {
										case 1:
											System.out.println(
													"-------------------[ 사용자 정보 수정 ] -> [ 일반 정보 수정 ]------------------");
											System.out.println(
													"-------------------------[ 수정 정보 입력 ]-----------------------------");
											MainUserFunction.insertUserInfo(userDto);//
											flag = false;
											break;
										case 2:
											System.out.println(
													"-------------------[ 사용자 정보 수정 ] -> [ 비밀번호 수정 ]------------------");
											System.out.println(
													"-----------------------[ 수정 비밀 번호 입력 ]----------------------------");
											System.out.print("비밀번호: ");
											userDto.setPassword(sc.next());
											MainUserFunction.modifyUserInfo(userDto);// 입력받은 데이터로 정보 수정
											flag = false;
											break;
										case 3:
											flag = false;
											break;
										default:
											System.out.println("다시 입력 하세요.");
										}
									}
									break;
								}
								case 2: {
									System.out.println();
									System.out.println("----------------------[ 주소지 추가/수정 ]----------------------");
									System.out.println();

									boolean addressFlag = true;
									while (addressFlag) {
										System.out
												.println("1. 주소 목록 조회  | 2. 주소지 추가 | 3.주소지 수정 | 4. 주소지 삭제 | 5. 뒤로 가기");
										System.out.print("번호를 입력하세요: ");
										int addressCommand = sc.nextInt();
										sc.nextLine();
										System.out.println();
										// 뒤로가기 하려면 if문 / 상관없으면 switch
										// 1.주소 목록 조회
										if (addressCommand == 1) {
											System.out.println(
													"----------------------[ 주소지 추가/수정 ] -> [ 주소지 목록 조회 ]----------------------");
											MainUserFunction.viewAddress(LoginSession.getLoginUserId());
										}
										// 2. 주소지 추가
										else if (addressCommand == 2) {
											System.out.println(
													"----------------------[ 주소지 추가/수정 ] -> [ 주소지 추가 ]----------------------");
											MainUserFunction.addAddress(LoginSession.getLoginUserId());
										}
										// 3. 주소지 수정
										else if (addressCommand == 3) {
											System.out.println(
													"----------------------[ 주소지 추가/수정 ] -> [ 주소지 수정 ]----------------------");
											MainUserFunction.modifyAddress(LoginSession.getLoginUserId());
										}
										// 4. 주소지 삭제
										else if (addressCommand == 4) {
											System.out.println(
													"----------------------[ 주소지 추가/수정 ] -> [ 주소지 삭제 ]----------------------");
											MainUserFunction.deleteAddress(LoginSession.getLoginUserId());
										}
										// 5. 뒤로 가기
										else {
											addressFlag = false;
										}
									}
									break;
								}
								case 3: {
									System.out.println();
									System.out.println("----------------------[ 카테고리별 상품 보기 ]----------------------");
									System.out.println();
									int categoryNumber = MainUserFunction.viewProductsCategory();
									int currentPage = 0;
									int beforePage = 0;
									if (categoryNumber > -1) {
										boolean flag = true;
										while (flag) {
											List<ProductsDTO> productList = new ArrayList<ProductsDTO>();
											productList = MainUserFunction.viewProductsByCategory(categoryNumber,
													currentPage);
											System.out.println();
											// 페이징 처리
											if (!productList.isEmpty()) {
												beforePage = currentPage;
												if (currentPage == 0) {
													System.out.println("1. 다음  | 2. 상품 선택 | 3. 뒤로가기");
													int pageCommand = sc.nextInt();
													if (pageCommand == 1) {
														currentPage++;
													} else if (pageCommand == 2) {
														System.out.println();
														System.out.print("상품 번호를 입력하세요: ");
														int selectProduct = sc.nextInt();
														System.out.println();
														// 상품번호 범위 초과
														if (selectProduct < 1 || productList.size() < selectProduct) {
															System.out.println("잘못 입력하셨습니다.");
															System.out.println();
															continue;
														}
														MainUserFunction
																.viewProductDetail(productList.get(selectProduct - 1));
													} else if (pageCommand == 3) {
														flag = false;
													} else {
														System.out.println("잘못 입력하셨습니다.");
													}
												} else {
													System.out.println("1. 이전  | 2. 다음  | 3.상품 선택  | 4.뒤로가기");
													int pageCommand = sc.nextInt();
													if (pageCommand == 1) {
														currentPage--;
													} else if (pageCommand == 2) {
														currentPage++;
													} else if (pageCommand == 3) {
														System.out.println();
														System.out.print("상품 번호를 입력하세요: ");
														int selectProduct = sc.nextInt();
														// 상품번호 범위 초과
														if (selectProduct < 1 || productList.size() < selectProduct) {
															System.out.println("잘못 입력하셨습니다.");
															System.out.println();
															continue;
														}
														MainUserFunction
																.viewProductDetail(productList.get(selectProduct - 1));
													} else if (pageCommand == 4) {
														flag = false;
													} else {
														System.out.println("잘못 입력 하셨습니다.");
													}
												}
											} else {
												System.out.println();
												System.out.println("더 이상 페이지를 이동할 수 없습니다.");
												System.out.println();
												currentPage = beforePage;
											}
										}
									}
									break;
								}
								case 4: {
									System.out.println();
									System.out.println(
											"----------------------[ 카테고리별 상품 보기 ] -> [ 장바구니 목록 조회 ]----------------------");
									System.out.println();
									// 조회 페이지 들어가면 바로 카트 리스트 출력
									boolean cartFlag = true;
									while (cartFlag) {

										List<CartsDTO> cartsList = new ArrayList<CartsDTO>();
										cartsList = MainUserFunction.viewCartList(LoginSession.getLoginUserId());
										System.out.println("1. 장바구니 수정 | 2. 장바구니 상품 결제 | 3. 뒤로 가기");
										System.out.print("번호를 입력하세요: ");
										int cartCommand = sc.nextInt();
										System.out.println();
										// 1. 장바구니 수정
										if (cartCommand == 1) {
											boolean modifyFlag = true;
											while (modifyFlag) {
												System.out.println(
														"----------------------[ 카테고리별 상품 보기 ] -> [ 장바구니 수정 ]----------------------");
												if (cartsList.isEmpty()) {
													System.out.println();
													System.out.println("장바구니에 아무것도 없어요~");
													modifyFlag = false;
													continue;
												}
												System.out.println("1. 상품 수량 수정 | 2. 상품 삭제 | 3. 뒤로 가기");
												System.out.print("번호를 입력하세요: ");
												int modifyCommand = sc.nextInt();
												System.out.println();
												if (modifyCommand == 1) {
													System.out.println(
															"---------------[ 카테고리별 상품 보기 ] -> [ 장바구니 수정 ] ->[ 상품 수량 수정 ]---------------");
													MainUserFunction.modifyCartProductCount(LoginSession.loginUserId,
															cartsList);
												} else if (modifyCommand == 2) {
													System.out.println(
															"---------------[ 카테고리별 상품 보기 ] -> [ 장바구니 수정 ] ->[ 상품 삭제 ]---------------");
													MainUserFunction.deleteCartProduct(LoginSession.loginUserId,
															cartsList);
												} else if (modifyCommand == 3) {
													modifyFlag = false;
												} else {
													System.out.println("잘못된 입력입니다.");
													System.out.println();
												}
											}
										}
										// 2. 장바구니 상품결제
										else if (cartCommand == 2) {
											System.out.println();
											System.out.println(
													"----------------------[ 카테고리별 상품 보기 ] -> [ 장바구니 상품 결제 ]----------------------");
											if (cartsList.isEmpty()) {
												System.out.println();
												System.out.println("장바구니에 아무것도 없어요~");
												continue;
											}
											MainUserFunction.orderFromCart(LoginSession.loginUserId, cartsList);
											cartFlag = false;
										}
										// 3. 뒤로가기
										else if (cartCommand == 3) {
											cartFlag = false;
										} else {
											System.out.println("잘못된 입력입니다.");
											System.out.println();
										}
									}
									break;
								}
								case 5: {
									System.out.println();
									System.out.println("----------------------[ 구매 목록 보기 ]----------------------");
									System.out.println();
									boolean deliveryFlag = true;
									while (deliveryFlag) {
										System.out.println("1. 준비중인 상품 | 2. 배송중인 상품 | 3. 배송 완료된 상품 | 4. 뒤로가기");
										System.out.print("번호 입력: ");
										int deliveryNumber = sc.nextInt();
										switch (deliveryNumber) {
										case 1: {
											MainUserFunction.viewOrderList("상품 준비중", LoginSession.getLoginUserId());
											break;
										}
										case 2: {
											MainUserFunction.viewOrderList("배송중", LoginSession.getLoginUserId());
											break;
										}
										case 3: {
											MainUserFunction.viewOrderList("배송 완료", LoginSession.getLoginUserId());
											break;
										}
										case 4: {
											deliveryFlag = false;
										}
										default:
											System.out.println("잘못 입력하셨습니다.");
										}

									}
									break;
								}
								case 6: {
									System.out.println();
									System.out.println("로그아웃 되었습니다.");
									System.out.println();
									userFlag = false;
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
					System.out.println("프로그램이 종료되었습니다.");
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
		} catch (RuntimeException e) {
			System.out.println(e.getMessage());
		}
	}
}
