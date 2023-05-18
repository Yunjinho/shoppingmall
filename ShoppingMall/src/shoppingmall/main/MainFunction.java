package shoppingmall.main;

import java.util.List;
import java.util.Scanner;

import shoppingmall.model.dao.impl.AddressesDAOImpl;
import shoppingmall.model.dao.impl.CategoriesDAOImpl;
import shoppingmall.model.dao.impl.ProductsDAOImpl;
import shoppingmall.model.dao.impl.UsersDAOImpl;
import shoppingmall.model.dto.AddressesDTO;
import shoppingmall.model.dto.CategoriesDTO;
import shoppingmall.model.dto.ProductsDTO;
import shoppingmall.model.dto.UsersDTO;

public class MainFunction {
	private static Scanner sc = new Scanner(System.in);
	static UsersDAOImpl userDaoImpl=new UsersDAOImpl();
	static AddressesDAOImpl addressDaoImpl=new AddressesDAOImpl();
	static CategoriesDAOImpl categoriesDaoImpl=new CategoriesDAOImpl();
	
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

	}

	public static int registerProduct() {
		int count = 0;
		ProductsDTO productDto = new ProductsDTO();
		ProductsDAOImpl productDao = new ProductsDAOImpl();
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

		count = productDao.insertProduct(productDto);
		System.out.println("상품 등록이 완료되었습니다.");
//		System.out.println("[등록 상품 내용]");
//		System.out.println("상품 이름 :");
		return count;
	}
	
	public static void modifyUserInfo(String userId) {
		UsersDTO userDto=null;
		
		try {
			userDto=userDaoImpl.getUserInfo(userId);//로그인한 정보 얻어오기
			System.out.print("고객 아이디  : "+userDto.getUserId()+"\n");
			System.out.print("고객 이름  : "+userDto.getUserName()+"\n");
			System.out.print("고객 전화번호  : "+userDto.getPhoneNumber()+"\n");
			System.out.print("고객 생년월일  : "+userDto.getBirthday()+"\n");
			System.out.print("고객 성별  : "+userDto.getGender()+"\n");
		} catch (RuntimeException e) {
			System.out.println(e.getMessage());
		}
		
		System.out.println("1. 일반 정보 수정 | 2. 비밀번호 변경 | 3. 뒤로가기");
		System.out.print("번호를 입력하세요: ");
		int command = sc.nextInt();
		boolean flag=true;
		while(flag) {
			switch (command) {
			case 1:
				System.out.println("-----수정 정보 입력----");
				System.out.print("이름: ");
				userDto.setUserName(sc.next());
				System.out.print("핸드폰 번호[010-1234-1234]: ");
				userDto.setPhoneNumber(sc.next());
				System.out.print("생일[YYYY-MM-DD]: ");
				String birth = sc.next();
				java.sql.Date date = java.sql.Date.valueOf(birth);
				userDto.setBirthday(date);
				try {
					userDaoImpl.updateUsersInformation(userDto);// 입력받은 데이터로 정보 수정
				}catch (RuntimeException e) {
					System.out.println(e.getMessage());
				}
				flag=false;
				break;
			case 2:
				System.out.println("-----수정 비밀번호 입력----");
				System.out.print("비밀번호: ");
				userDto.setPassword(sc.next());
				try {
					userDaoImpl.updateUsersInformation(userDto);// 입력받은 데이터로 정보 수정
				}catch (RuntimeException e) {
					System.out.println(e.getMessage());
				}
				flag=false;
				break;
			case 3:
				flag=false;
			default:
				System.out.println("다시 입력 하세요.");
			}
		}
		
	}
	
	public static void inquireAddress(String userId) {
		UsersDTO userDto=new UsersDTO();
		try {
			userDto.setAddressDto(addressDaoImpl.getUserAddresses(userId));
		}catch(RuntimeException e) {
			System.out.println(e.getMessage());
		}
		for(AddressesDTO l:userDto.getAddressDto()) {
			System.out.print("주소: "+l.getAddress()+"\n");
		}
		System.out.println();
	}

	public static void addAddress(String userId) {
		System.out.print("주소 입력: ");
		sc.nextLine();
		String address=sc.nextLine();
		try {
			addressDaoImpl.insertAddresses(userId,address);
		}catch(RuntimeException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void modifyAddress(String userId) {
		UsersDTO userDto=new UsersDTO();
		AddressesDTO addressDto=new AddressesDTO();
		try {
			userDto.setAddressDto(addressDaoImpl.getUserAddresses(userId));
			for(int i=0;i<userDto.getAddressDto().size();i++) {
				System.out.print((i+1)+"번. 주소: " +userDto.getAddressDto().get(i).getAddress()+"\n");
			}
			System.out.println();
			System.out.print("수정하고 싶은 주소 번호: ");
			int modifyNum=sc.nextInt();
			addressDto=userDto.getAddressDto().get(modifyNum-1);
			System.out.println();
			sc.nextLine();
			System.out.print("주소 입력: ");
			String address=sc.nextLine();
			
			addressDto.setAddress(address);
			addressDaoImpl.updateAddresses(addressDto);
		}catch(RuntimeException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void deleteAddress(String userId) {
		UsersDTO userDto=new UsersDTO();
		AddressesDTO addressDto=new AddressesDTO();
		try {
			userDto.setAddressDto(addressDaoImpl.getUserAddresses(userId));
			for(int i=0;i<userDto.getAddressDto().size();i++) {
				System.out.print((i+1)+"번. 주소: " +userDto.getAddressDto().get(i).getAddress()+"\n");
			}
			System.out.println();
			System.out.print("삭제하고 싶은 주소 번호: ");
			int modifyNum=sc.nextInt();
			addressDto=userDto.getAddressDto().get(modifyNum-1);
			sc.nextLine();
			System.out.println();
			addressDaoImpl.deleteAddresses(addressDto.getAddressId());
		}catch(RuntimeException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void inquireProductsByCategory() {
		for(CategoriesDTO category:categoriesDaoImpl.getCategoriesNames()) {
		}
		
	}
}
