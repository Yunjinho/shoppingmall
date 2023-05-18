package shoppingmall.main;

import java.util.ArrayList;
import java.util.Scanner;

import shoppingmall.model.dao.impl.ProductsDAOImpl;
import shoppingmall.model.dao.impl.UsersDAOImpl;
import shoppingmall.model.dto.ProductsDTO;
import shoppingmall.model.dto.UsersDTO;

public class MainFunction {
	private static Scanner sc = new Scanner(System.in);

	public static boolean login() {
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

	public static boolean signUpUserInfo() {
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

	public static void getAllProducts() {
		int count = 0;
		ProductsDAOImpl productDao = new ProductsDAOImpl();
		ArrayList<ProductsDTO> productsDto = new ArrayList<>();
		productsDto = productDao.getProductsList();
		System.out.println("상품 번호 | 상품 카테고리 | 상품 이름           | 상품 가격  | 상품 재고  | 상품 정보   |  상품 상태    |");
		for (ProductsDTO productDto : productsDto) {
			if (productDto.getProductStatus() == 1) {
				System.out.printf("%d\t %s\t  %-10s\t %d\t %d\t %s\t %-10s", productDto.getProductId(),
						productDto.getCategoryName(), productDto.getProductName(), productDto.getProductPrice(),
						productDto.getProductStock(), "판매중", productDto.getProductInfo());
			} else {
				System.out.printf("%d\t %s\t  %-10s\t %d\t %d\t %s\t %-10s", productDto.getProductId(),
						productDto.getCategoryName(), productDto.getProductName(), productDto.getProductPrice(),
						productDto.getProductStock(), "판매 중지", productDto.getProductInfo());
			}
			System.out.println();
		}
		System.out.println();
	}

	public static ProductsDTO insertProductInfo() {
		ProductsDTO productDto = new ProductsDTO();
		sc.nextLine();
		System.out.print("상품 이름 [String] : ");
		productDto.setProductName(sc.nextLine());

		System.out.print("상품 가격 [Integer] : ");
		productDto.setProductPrice(sc.nextInt());

		System.out.print("상품 재고 [Integer] : ");
		productDto.setProductStock(sc.nextInt());
		sc.nextLine();

		System.out.print("상품 정보 [String] : ");
		productDto.setProductInfo(sc.nextLine());

		System.out.print("상품 카테고리 [Integer] [1.상의 | 2.하의 | 3.신발] : ");
		productDto.setCategoryId(sc.nextInt());
		sc.nextLine();
		System.out.println();
		return productDto;
	}

	public static int registerProduct() {

		int count = 0;
		ProductsDAOImpl productDao = new ProductsDAOImpl();
		ProductsDTO productDto = insertProductInfo();
		count = productDao.insertProduct(productDto);
		System.out.println("상품 등록이 완료되었습니다.");
//		System.out.println("[등록 상품 내용]");
//		System.out.println("상품 이름 :");
		return count;
	}

	// 상품번호로 상품 수정
	public static int updateProductByProductId() {
		int productId = selectUpdateProductId();
		ProductsDAOImpl productDao = new ProductsDAOImpl();
		ProductsDTO productDto = new ProductsDTO();
		productDto = insertProductInfo();

		productDto.setProductId(productId);
		productDao.updateProductInfo(productDto);

		System.out.println("상품 업데이트가 완료되었습니다.");
		return 0;
	}

	// 상품 재고 변경
	public static void updateProductStock() {
		int productId = selectUpdateProductId();
		int productStock = changeProductStock();
		ProductsDAOImpl productDao = new ProductsDAOImpl();
		productDao.updateProductStock(productId, productStock);
		System.out.println("상품 재고 변경이 완료되었습니다.");

	}

	// 상품 삭제 (상품 상태 변경)
	public static void deleteProductByProductId() {
		int productId = selectUpdateProductId();
		ProductsDAOImpl productDao = new ProductsDAOImpl();
		int count = productDao.deleteProduct(productId);
		if (count > 0) {
			System.out.println(productId + "번 상품이 판매 중지로 변경되었습니다.");
		} else {
			System.out.println("상품 번호를 잘못 입력하였습니다.");
		}
	}

	// 상품 재고 변경
	public static int changeProductStock() {
		// 상품 중에 고르기
		System.out.print("변경할 상품 재고를 입력하세요: ");
		int productStock = sc.nextInt();
		sc.nextLine();
		System.out.println();
		return productStock;
	}

	// 상품 아이디 입력
	public static int selectUpdateProductId() {
		getAllProducts();
		System.out.print("상품 번호를 선택하세요: ");
		int productId = sc.nextInt();
		sc.nextLine();
		System.out.println();
		return productId;
	}

}
