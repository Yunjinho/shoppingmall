package shoppingmall.main.user;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import shoppingmall.main.LoginSession;
import shoppingmall.model.dao.impl.AddressesDAOImpl;
import shoppingmall.model.dao.impl.CartsDAOImpl;
import shoppingmall.model.dao.impl.CategoriesDAOImpl;
import shoppingmall.model.dao.impl.OrderDetailsDAOImpl;
import shoppingmall.model.dao.impl.OrdersDAOImpl;
import shoppingmall.model.dao.impl.ProductsDAOImpl;
import shoppingmall.model.dao.impl.UsersDAOImpl;
import shoppingmall.model.dto.AddressesDTO;
import shoppingmall.model.dto.CartsDTO;
import shoppingmall.model.dto.CategoriesDTO;
import shoppingmall.model.dto.OrderDetailsDTO;
import shoppingmall.model.dto.ProductsDTO;
import shoppingmall.model.dto.UsersDTO;

public class MainUserFunction {
	private static Scanner sc = new Scanner(System.in);

	static UsersDAOImpl userDao = new UsersDAOImpl();
	static AddressesDAOImpl addressDao = new AddressesDAOImpl();
	static CategoriesDAOImpl categoryDao = new CategoriesDAOImpl();
	static ProductsDAOImpl productDao = new ProductsDAOImpl();
	static CartsDAOImpl cartDao = new CartsDAOImpl();
	static OrdersDAOImpl orderDao = new OrdersDAOImpl();
	static OrderDetailsDAOImpl orderDetailsDao = new OrderDetailsDAOImpl();

	// 로그인
	public static boolean login() {
		String userId;
		String password;

		System.out.print("아이디를 입력하세요: ");
		userId = sc.next();

		System.out.print("비밀번호를 입력하세요: ");
		password = sc.next();

		int check = userDao.login(userId, password);
		// 로그인 성공
		if (check > 0) {
			System.out.println("로그인 되었습니다.");
			LoginSession.setLoginUserId(userId);
			return true;
		}
		// 로그인 실패
		else {
			System.out.println("아이디나 비밀번호가 틀렸습니다. 다시 선택해 주세요.");
			System.out.println();
			return false;
		}
	}

	// 회원 가입
	public static boolean signUpUserInfo() {
		UsersDTO user = new UsersDTO();
		boolean result = false;
		System.out.print("아이디: ");
		user.setUserId(sc.next());
		// 아이디가 존재할 경우
		if (userDao.checkUserId(user.getUserId()) == false) {
			System.out.println("이미 존재하는 아이디입니다. 다른 아이디를 입력해주세요.");
			System.out.println();
			return result;
		}

		System.out.print("비밀번호: ");
		user.setPassword(sc.next());

		System.out.print("이름: ");
		user.setUserName(sc.next());

		System.out.print("핸드폰 번호[010-1234-1234]: ");
		String phoneNumber = sc.next();
		user.setPhoneNumber(phoneNumber);

		// DB에서 가져온 사용자 이름과 핸드폰 번호
		UsersDTO checkUserDto = userDao.checkUserInfo(user.getUserName(), user.getPhoneNumber());
		// 가져온 정보가 있다면

		if (checkUserDto.getPhoneNumber() == null) {

		} else if (checkUserDto.getPhoneNumber().equals(phoneNumber)) {

			System.out.println("이미 존재하는 회원정보 입니다.");
			System.out.println();
			return result;
		}

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
		return result;
	}

	// 아이디 찾기
	public static void findUserId() {
		boolean result = true;
		UsersDTO userInfo = new UsersDTO();

		System.out.print("사용자 이름을 입력하세요: ");
		String userName = sc.next();

		System.out.print("사용자 핸드폰 번호를 입력하세요: ");
		String phoneNumber = sc.next();

		userInfo = userDao.findUserIdByNameAndPhoneNumber(userName, phoneNumber);
		System.out.println();
		if (userInfo.getUserId() == null) {
			System.out.println("회원이 아닙니다.");
			System.out.println();
		} else {
			System.out.println("사용자 아이디 : " + userInfo.getUserId());
			System.out.println();
		}
	}

	// 유저 정보 조회
	public static UsersDTO viewUserInfo(String userId) {
		UsersDTO userDto = null;

		userDto = userDao.getUserInfo(userId);// 로그인한 정보 얻어오기
		System.out.print("고객 아이디  : " + userDto.getUserId() + "\n");
		System.out.print("고객 이름  : " + userDto.getUserName() + "\n");
		System.out.print("고객 전화번호  : " + userDto.getPhoneNumber() + "\n");
		System.out.print("고객 생년월일  : " + userDto.getBirthday() + "\n");
		System.out.print("고객 성별  : " + userDto.getGender() + "\n");

		return userDto;
	}

	// 유저 정보 수정
	public static void modifyUserPassword(UsersDTO userDto) {
		userDao.updateUsersInformation(userDto);// 입력받은 데이터로 정보 수정
		System.out.println("수정이 완료되었습니다.");
		System.out.println();
	}

	// 주소목록 조회
	public static void viewAddress(String userId) {
		UsersDTO userDto = new UsersDTO();

		userDto.setAddressDto(addressDao.getUserAddresses(userId));

		for (AddressesDTO l : userDto.getAddressDto()) {
			System.out.print("주소: " + l.getAddress() + "\n");
		}
		System.out.println();
	}

	// 주소 추가
	public static void addAddress(String userId) {
		System.out.print("주소 입력: ");
		sc.nextLine();
		String address = sc.nextLine();

		addressDao.insertAddresses(userId, address);
		System.out.println();

	}

	// 주소 수정
	public static void modifyAddress(String userId) {
		UsersDTO userDto = new UsersDTO();
		AddressesDTO addressDto = new AddressesDTO();
		userDto.setAddressDto(addressDao.getUserAddresses(userId));
		for (int i = 0; i < userDto.getAddressDto().size(); i++) {
			System.out.print((i + 1) + "번. 주소: " + userDto.getAddressDto().get(i).getAddress() + "\n");
		}
		System.out.println();
		System.out.print("수정하고 싶은 주소 번호: ");
		int modifyNum = sc.nextInt();
		if (modifyNum < 0 || userDto.getAddressDto().size() < modifyNum) {
			System.out.println("잘못된 번호 입니다.");
			return;
		}
		addressDto = userDto.getAddressDto().get(modifyNum - 1);
		System.out.println();
		sc.nextLine();
		System.out.print("주소 입력: ");
		String address = sc.nextLine();

		addressDto.setAddress(address);
		addressDao.updateAddresses(addressDto);

	}

	// 주소 삭제
	public static void deleteAddress(String userId) {
		UsersDTO userDto = new UsersDTO();
		AddressesDTO addressDto = new AddressesDTO();

		userDto.setAddressDto(addressDao.getUserAddresses(userId));
		for (int i = 0; i < userDto.getAddressDto().size(); i++) {
			System.out.print((i + 1) + "번. 주소: " + userDto.getAddressDto().get(i).getAddress() + "\n");
		}
		System.out.println();
		System.out.print("삭제하고 싶은 주소 번호: ");
		int modifyNum = sc.nextInt();
		if (modifyNum < 1 || userDto.getAddressDto().size() < modifyNum) {
			System.out.println("잘못된 입력입니다.");
			System.out.println();
			return;
		}
		addressDto = userDto.getAddressDto().get(modifyNum - 1);
		sc.nextLine();
		System.out.println();
		addressDao.deleteAddresses(addressDto.getAddressId());

	}

	// 상품 카테고리 보기
	public static int viewProductsCategory() {
		List<CategoriesDTO> categoryList = new ArrayList<CategoriesDTO>();
		categoryList = categoryDao.getCategoriesNames();
		for (int i = 0; i < categoryList.size(); i++) {
			System.out.print((i + 1) + ". " + categoryList.get(i).getCategoryName());
			if (i != categoryDao.getCategoriesNames().size() - 1) {
				System.out.print(" | ");
			}
		}
		System.out.println();
		System.out.print("번호를 입력하세요.");
		int selectNumber = sc.nextInt();
		if (categoryList.size() < selectNumber) {
			System.out.println("잘못된 입력입니다.");
			return -1;
		}
		int categoryNumber = categoryList.get(selectNumber - 1).getCategoryId();

		return categoryNumber;
	}

	// 카테고리별 상품보기
	public static List<ProductsDTO> viewProductsByCategory(int categoryNumber, int pageNum) {
		List<ProductsDTO> productsList = new ArrayList<ProductsDTO>();
		productsList = productDao.getProductListByCategory(categoryNumber, pageNum);

		System.out.println("상품 번호 |  상품 이름           | 상품 가격  | 상품 재고  | 상품 정보   |  상품 상태    |");
		for (int i = 0; i < productsList.size(); i++) {
			System.out.printf("%d\t%-10s     \t%d\t%d\t%s\t%s", (i + 1), productsList.get(i).getProductName(),
					productsList.get(i).getProductPrice(), productsList.get(i).getProductStock(), "판매중",
					productsList.get(i).getProductInfo());
			System.out.println();
		}
		return productsList;
	}

	// 상품 디테일 보기
	public static void viewProductDetail(ProductsDTO productDto) {
		System.out.println("상품 번호 |  상품 이름           | 상품 가격  | 상품 재고  | 상품 정보   |  상품 상태    |");
		System.out.printf("%d\t  %-10s\t %d\t %d\t %s\t %-10s", productDto.getProductId(), productDto.getProductName(),
				productDto.getProductPrice(), productDto.getProductStock(), "판매중", productDto.getProductInfo());
		System.out.println();
		System.out.print("상품 수량: ");
		int amount = sc.nextInt();
		if ((productDto.getProductStock() - amount) > -1) {
			System.out.println("1. 장바구니에 넣기 | 2. 바로 구매하기");
			System.out.print("번호 입력: ");
			int orderCommand = sc.nextInt();
			if (orderCommand == 1) {
				CartsDTO cartDto=null;;
				cartDto=cartDao.checkExistProduct(productDto.getProductId());
				if(cartDto!=null) {
					int count=cartDto.getProductCount();
					cartDto.setProductCount(count+amount);
					cartDao.updateCart(cartDto);
				}else {
					cartDto=new CartsDTO();
					cartDto.setProductId(productDto.getProductId());
					cartDto.setUserId(LoginSession.getLoginUserId());
					cartDto.setProductCount(amount);
					cartDao.insertCart(cartDto);
				}
			} else if (orderCommand == 2) {
				UsersDTO userDto = new UsersDTO();
				userDto.setAddressDto(addressDao.getUserAddresses(LoginSession.getLoginUserId()));
				int count = 1;
				for (AddressesDTO l : userDto.getAddressDto()) {
					System.out.print(count + ". 주소: " + l.getAddress() + "\n");
					count++;
				}
				System.out.println();
				System.out.print("주소 선택: ");
				int addressNum = sc.nextInt();
				if (addressNum < 1 || userDto.getAddressDto().size() < addressNum) {
					System.out.println("잘못 선택하셨습니다.");
					System.out.println();
					return;
				}
				orderDao.insertUserOrderfromProductDetail(productDto.getProductId(), LoginSession.getLoginUserId(),
						userDto.getAddressDto().get(addressNum - 1).getAddress(), amount);
			} else {
				System.out.println("잘못된 선택입니다.");
			}
		} else {
			System.out.println("재고가 부족합니다.");
			System.out.println();
		}

	}

	// 카트 리스트 보기
	public static List<CartsDTO> viewCartList(String userId) {
		List<CartsDTO> cartsList = new ArrayList<CartsDTO>();
		cartsList = cartDao.getUsersCartList(userId);
		if (cartsList.size() > 0) {
			System.out.println("카트 번호 |   상품 이름      |   구매 수량      |   상품 가격     |  상품   상태  |");
			for (int i = 0; i < cartsList.size(); i++) {
				System.out.printf("%d\t  %-10s\t %d\t %d\t\t %-20s\n", (i + 1),
						cartsList.get(i).getProductDto().getProductName(), cartsList.get(i).getProductCount(),
						cartsList.get(i).getProductDto().getProductPrice(),
						cartsList.get(i).getProductDto().getProductStatus() == 1 ? "판매 중" : "판매 중지");
			}
			int totalPrice = 0;
			for (CartsDTO l : cartsList) {
				totalPrice += cartDao.getCartTotalPrice(l.getCartId());
			}
			System.out.println("총 금액: " + totalPrice);
			System.out.println();
		} else {
			System.out.println("텅~");
			System.out.println();
		}
		return cartsList;
	}

	// 장바구니 상품 수량 수정
	public static void modifyCartProductCount(String loginUserId, List<CartsDTO> cartList) {
		CartsDTO cartDto = new CartsDTO();
		System.out.println("수정하실 카트 번호와 수량을 입력하세요");
		System.out.print("카트 번호 : ");
		int cartNumber = sc.nextInt();
		if (cartNumber < 1 || cartList.size() < cartNumber) {
			System.out.println("잘못 입력하셨습니다 :)");
			System.out.println();
			return;
		}
		cartDto = cartList.get(cartNumber - 1);

		System.out.print("수량 : ");
		int productCount = sc.nextInt();

		// 입력받은 수량이 음수가 되는건 아닌지 체크
		if (productCount < 1) {
			System.out.println("수량을 다시 확인해 주세요 :)");
		} else {
			cartDto.setProductCount(productCount);
			cartDao.updateCart(cartDto);
		}
	}

	// 장바구니 상품 삭제
	public static void deleteCartProduct(String loginUserId, List<CartsDTO> cartsList) {
		CartsDTO cartDto = new CartsDTO();
		System.out.println("삭제하실 카트 번호를 입력해주세요.");
		System.out.print("카트 번호: ");
		int cartNumber = sc.nextInt();
		if (cartNumber < 1 || cartsList.size() < cartNumber) {
			System.out.println("잘못 입력하셨습니다 :)");
			System.out.println();
			return;
		}
		cartDto = cartsList.get(cartNumber - 1);
		cartDao.deleteCartProduct(cartDto.getCartId());
	}

	// 장바구니에서 주문하기
	public static void orderFromCart(String userId, List<CartsDTO> cartsList) {
		UsersDTO userDto = new UsersDTO();

		userDto.setAddressDto(addressDao.getUserAddresses(userId));

		for (int i = 0; i < userDto.getAddressDto().size(); i++) {
			System.out.print((i + 1) + ". 주소: " + userDto.getAddressDto().get(i).getAddress() + "\n");
		}
		System.out.println();
		System.out.println("주소를 선택해 주세요");
		System.out.print("번호 입력: ");
		int addressNumber = sc.nextInt();
		if (addressNumber < 1 || userDto.getAddressDto().size() < addressNumber) {
			System.out.println("잘못된 입력입니다.");
			return;
		}
		// 상품 수량 체크
		if (checkProduct(cartsList)) {
			// 오더테이블에 넣기
			orderDao.insertUserOrderfromCart(userId, userDto.getAddressDto().get(addressNumber - 1).getAddress(),
					cartDao.getCartTotalPrice(cartsList.get(0).getCartId()), cartsList);
			// 넣은 계정의 카트는 삭제
			cartDao.deleteCartProducts(userId);
		}
	}

	// 구매목록에 넣기전 수량 체크
	private static boolean checkProduct(List<CartsDTO> cartsList) {
		ProductsDTO productDto = new ProductsDTO();
		// 카트 리스트 순회
		for (CartsDTO l : cartsList) {
			productDto = productDao.getProductDetail(l.getProductId());
			if (productDto.getProductStatus() == 0) {
				System.out.println(productDto.getProductName() + "의 상품은 판매 중지 상태입니다. 장바구니에서 제거해주세요.");
				return false;
			} else {
				if (productDto.getProductStock() - l.getProductCount() < 0) {
					System.out.println(productDto.getProductName() + " 상품의 재고가 "
							+ Math.abs(productDto.getProductStock() - l.getProductCount())
							+ "개 부족합니다 . 장바구니에서 수량을 수정해주세요.");
					return false;
				}
			}
		}
		return true;
	}

	public static void modifyUserInfo(UsersDTO userDto) {
		System.out.print("이름: ");
		userDto.setUserName(sc.next());
		System.out.print("핸드폰 번호[010-1234-1234]: ");
		userDto.setPhoneNumber(sc.next());
		System.out.print("생일[YYYY-MM-DD]: ");
		String birth = sc.next();
		java.sql.Date date = java.sql.Date.valueOf(birth);
		userDto.setBirthday(date);
		userDao.updateUsersInformation(userDto);// 입력받은 데이터로 정보 수정
		System.out.println();
	}

	public static void viewOrderList(String deliveryStatus, String userId) {
		List<OrderDetailsDTO> orderDetailsList = new ArrayList<OrderDetailsDTO>();
		orderDetailsList = orderDetailsDao.getOrderListForUser(deliveryStatus, userId);
		if (orderDetailsList.isEmpty()) {
			System.out.println("텅~");
			System.out.println();
			return;
		}
		System.out.println("|    상품 이름           |   상품 가격      |   구매 수량      |   구매 일자      |   배송 현황     |");
		for (OrderDetailsDTO l : orderDetailsList) {
			SimpleDateFormat simdate = new SimpleDateFormat("yyyy-MM-dd");
			String date = simdate.format(l.getCreatedAt());
			System.out.printf("%18s\t  %d\t\t %d\t %10s\t %5s\n", l.getProductDto().getProductName(),
					l.getProductDto().getProductPrice(), l.getProductCount(), date, l.getDeliveryStatus());
		}
	}

	// 비밀번호 찾기 변경
	public static void changeUserPassword() {
		UsersDTO user = new UsersDTO();
		System.out.print("아이디: ");
		String userId = sc.next();

		// 아이디 체크
		if (userDao.checkUserId(userId) == true) {
			System.out.println("존재하지 않는 아이디 입니다.");
			System.out.println();
			return;
		}
		user = userDao.getUserInfo(userId);
		System.out.println("회원가입 때 사용한 성함과 핸드폰 번호를 입력해주세요");
		System.out.print("이름: ");
		String name = sc.next();
		System.out.print("핸드폰 번호[010-1234-1234]: ");
		String phoneNuber = sc.next();
		if (user.getUserName().equals(name) && user.getPhoneNumber().equals(phoneNuber)) {

			System.out.print("변경할 비밀번호: ");
			user.setPassword(sc.next());
			// 정보 변경
			userDao.updateUsersInformation(user);
			System.out.println("완료 되었습니다.");
			System.out.println();

			return;
		}
		System.out.println("입력하신 정보가 일치하지 않습니다.");

	}
}
