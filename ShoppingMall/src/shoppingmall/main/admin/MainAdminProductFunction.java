package shoppingmall.main.admin;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import shoppingmall.model.dao.impl.ProductsDAOImpl;
import shoppingmall.model.dto.ProductsDTO;

public class MainAdminProductFunction {
	private static Scanner sc = new Scanner(System.in);

	static ProductsDAOImpl productDao = new ProductsDAOImpl();

	// 상품 관리
	public static void productsManagement() {
		List<ProductsDTO> productsList = new ArrayList<>();
		productsList = productDao.getProductsList();
		String productStatus = "";

		int currentPage = 0;
		int currentIndex = 0;

		int lastIndex = productsList.size();

		// 마지막 페이지에 보여줄 개수
		int restList = lastIndex % 5;
		int lastPage;

		if (restList == 0) {
			lastPage = lastIndex / 5 - 1;
		} else {
			lastPage = lastIndex / 5;
		}

		// 5개씩 보여주기 위한 변수
		int plus;
		if (lastIndex <= 5) {
			// 보여줄 목록이 5개 이하이면
			plus = lastIndex;
		} else {
			plus = 5;
		}

		while (true) {
			System.out.println("-------------------------------- " + (currentPage + 1) + " 페이지 "
					+ "---------------------------------");
			System.out.println("상품 번호 | 상품 카테고리 |   상품 이름      | 상품 가격  | 상품 재고  | 상품 상태   |  상품 정보    |");
			for (int i = currentIndex; i < (currentIndex + plus); i++) {
				if (productsList.get(i).getProductStatus() == 1) {
					productStatus = "판매중";
				} else {
					productStatus = "판매 중지";
				}
				System.out.printf("%d\t %s\t      %10s\t %d\t   %d\t    %s\t    %-15s",
						productsList.get(i).getProductId(), productsList.get(i).getCategoryName(),
						productsList.get(i).getProductName(), productsList.get(i).getProductPrice(),
						productsList.get(i).getProductStock(), productStatus, productsList.get(i).getProductInfo());
				System.out.println();
			}
			System.out.println("-------------------------------- " + (currentPage + 1) + " 페이지 "
					+ "---------------------------------");
			System.out.println();
			System.out.println(
					"1.[이전 페이지] | 2.[다음 페이지] | 3.[상품 등록] | 4. [상품 수정] | 5.[상품 재고 변경] | 6.[상품 상태 변경] | 7.[페이지 나가기]");

			System.out.print("숫자를 입력하세요: ");
			int count = sc.nextInt();
			System.out.println();
			// 1. 이전 페이지
			if (count == 1) {
				if (currentPage == 0) {
					System.out.println("처음 페이지 입니다.");
					System.out.println();
					continue;
				}
				currentPage -= 1;
				currentIndex -= 5;
				plus = 5;
			}
			// 2. 다음 페이지
			else if (count == 2) {
				// 마지막 페이지에서 다음 페이지를 누르면
				if (currentPage == lastPage) {
					System.out.println("마지막 페이지 입니다.");
					System.out.println();
					continue;
				}
				// 그 외의 경우
				else {
					// 현재 페이지를 다음 페이지로 넘기고 현재 인덱스를 + 5 해준다.
					currentPage += 1;
					currentIndex += 5;
					// 넘어간 페이지가 마지막 페이지라면
					if (currentPage == lastPage) {
						// 마지막 페이지에서 보여줄 개수 = 전체 개수 - 현재 인덱스
						plus = lastIndex - currentIndex;
					}
					// 마지막 페이지가 아니라면
					else {
						// 보여줄 개수는 그대로 5개
						plus = 5;
					}
				}
			}
			// 3. 상품 등록
			else if (count == 3) {
				// 입력한 값을 가져와서
				registerProduct();

				// 등록한 상품을 보여주기 위해 새로운 productsList 저장
				productsList = productDao.getProductsList();

				// 상품이 등록되었으므로 마지막 인덱스 + 1
				lastIndex += 1;

				// 전에 보여준 상품이 5개라면
				if (plus == 5) {
					// 보여준 상품이 5개였고 마지막 페이지에서 등록을 했다면
					if (lastPage == currentPage) {
						// 마지막 페이지 + 1
						plus = 1;
						lastPage += 1;
						// 현재 페이지 + 1 & 현재 인덱스에서 다음페이지로 넘어가므로 + 5
						currentPage += 1;
						currentIndex += 5;
					}
					// 보여준 상품이 5개였고 마지막 페이지가 아닌 곳에서 등록을 했다면
					else {
						plus = 5;
					}
				}

				// 전에 보여준 상품이 5개 미만이라면
				else {
					plus += 1;
				}
			}
			// 4. 상품 수정 By ProductId
			else if (count == 4) {
				boolean result = updateProductByProductId();
				// 수정된 내용 반영하기 위해 새로운 productList 불러와서 저장
				productsList = productDao.getProductsList();
				System.out.println();
			}

			// 5. 상품 재고 변경 By ProductId
			else if (count == 5) {
				boolean result = updateProductStockByProductId();
				productsList = productDao.getProductsList();
				System.out.println();
			}
			// 6. 상품 상태 변경 By ProductId
			else if (count == 6) {
				boolean result = updateProductStatusByProductId();
				productsList = productDao.getProductsList();
				System.out.println();
			}
			// 나가기
			else {
				System.out.println("페이지 나가기를 눌렀습니다.");
				break;
			}
		}
		System.out.println();

	}

	// 상품 정보 입력
	public static ProductsDTO insertProductInfo() {
		ProductsDTO productDto = new ProductsDTO();
		sc.nextLine();
		System.out.print("상품 이름 [String] : ");
		productDto.setProductName(sc.nextLine());

		System.out.print("상품 가격 [Integer] : ");
		productDto.setProductPrice(sc.nextInt());
		sc.nextLine();

		System.out.print("상품 재고 [Integer] : ");
		productDto.setProductStock(sc.nextInt());
		sc.nextLine();

		System.out.print("상품 정보 [String] : ");
		productDto.setProductInfo(sc.nextLine());

		System.out.print("상품 카테고리 [Integer] [1.상의 | 2.하의 | 3.신발] : ");
		int categoryId = sc.nextInt();
		sc.nextLine();
		productDto.setCategoryId(categoryId);

		if (categoryId == 1) {
			productDto.setCategoryName("상의");
		} else if (categoryId == 2) {
			productDto.setCategoryName("하의");
		} else if (categoryId == 3) {
			productDto.setCategoryName("신발");
		}
		System.out.print("상품 상태 [Integer] [1.판매중 | 2.판매 중지] : ");
		int productStatus = sc.nextInt();
		if (productStatus == 1) {
			productDto.setProductStatus(1);
		} else {
			productDto.setProductStatus(0);
		}

		System.out.println();
		return productDto;
	}

	// 상품 등록
	public static void registerProduct() {
		int count = 0;
		ProductsDTO productDto = insertProductInfo();
		count = productDao.insertProduct(productDto);
		System.out.println("상품 등록이 완료되었습니다.");
		System.out.println("----------------------------- 등록 상품 정보----------------------------");
		System.out.println("[상품 이름] : " + productDto.getProductName());
		System.out.println("[상품 가격] : " + productDto.getProductPrice());
		System.out.println("[상품 재고] : " + productDto.getProductStock());
		System.out.println("[상품 정보] : " + productDto.getProductInfo());
		System.out.println("[상품 카테고리] : " + productDto.getCategoryName());
		System.out.println("-------------------------------------------------------------------");
		System.out.println();
	}

	// 상품번호로 상품 수정
	public static boolean updateProductByProductId() {
		int productId = selectUpdateProductId();
		if (!productDao.productCheckByProductId(productId)) {
			System.out.println("존재하지 않는 상품 번호 입니다. 다시 입력해주세요.");
			return false;
		}
		ProductsDTO productDto = new ProductsDTO();
		productDto = insertProductInfo();
		productDto.setProductId(productId);
		productDao.updateProductInfo(productDto);

		System.out.println("상품 업데이트가 완료되었습니다.");
		return true;
	}

	// 상품번호로 상품 재고 변경
	public static boolean updateProductStockByProductId() {
		int productId = selectUpdateProductId();
		// ProductId가 존재하는지 체크하는 로직 추가
		if (!productDao.productCheckByProductId(productId)) {
			System.out.println("존재하지 않는 상품 번호 입니다. 다시 입력해주세요.");
			return false;
		}
		System.out.print("변경할 상품 재고를 입력하세요: ");
		int productStock = sc.nextInt();
		sc.nextLine();
		System.out.println();

		if (productStock < 0) {
			System.out.println("수량이 잘못되었습니다. 메뉴를 선택해 주세요.");
			System.out.println();
			return false;
		}
		productDao.updateProductStock(productId, productStock);
		System.out.println(productId + "번 상품 재고가 " + productStock + "개로 변경이 완료되었습니다.");
		return true;
	}

	// 상품번호로 상품 상태 변경
	public static boolean updateProductStatusByProductId() {
		int productId = selectUpdateProductId();
		// ProductId가 존재하는지 체크하는 로직 추가
		if (!productDao.productCheckByProductId(productId)) {
			System.out.println("존재하지 않는 상품 번호 입니다. 다시 입력해주세요.");
			return false;
		}
		System.out.print("상품 상태 변경[1. 판매 중지 | 2. 판매중] : ");
		int productStatus = sc.nextInt();

		String status = "";
		// 이미 같은 값이면 업데이트 처리 안해도 되는 로직 추가

		int count = productDao.updateProductStatus(productId, productStatus - 1);
		if (productStatus == 1) {
			status = "판매 중지";
		} else {
			status = "판매중";
		}

		System.out.println();
		System.out.println(productId + "번 상품의 상태 [" + status + "] 변경");
		System.out.println();
		return true;
	}

	// 상품 아이디 입력
	public static int selectUpdateProductId() {
		System.out.print("수정할 상품 번호를 입력하세요: ");
		int productId = sc.nextInt();
		return productId;
	}
}
