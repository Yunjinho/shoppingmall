package shoppingmall.main.admin;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import shoppingmall.model.dao.impl.OrderDetailsDAOImpl;
import shoppingmall.model.dao.impl.OrdersDAOImpl;
import shoppingmall.model.dto.OrderDetailsDTO;
import shoppingmall.model.dto.OrdersDTO;

public class MainAdminOrderFunction {
	private static Scanner sc = new Scanner(System.in);

	static OrdersDAOImpl orderDao = new OrdersDAOImpl();
	static OrderDetailsDAOImpl orderDetailsDao = new OrderDetailsDAOImpl();

	// 전체 배송 관리
	public static void orderManagement() {
		List<OrdersDTO> ordersList = new ArrayList<>();
		ordersList = orderDao.getAllOrders();

		int currentPage = 0;
		int currentIndex = 0;

		int lastIndex = ordersList.size();

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
			System.out.println("---------------------------------------- " + (currentPage + 1) + " 페이지 "
					+ "----------------------------------------");
			System.out.println(
					"내역 번호 | 주문 번호 | 구매자 이름 |      핸드폰 번호             |       배송지              |  총 가격    |  배송 상태    |");
			for (int i = currentIndex; i < (currentIndex + plus); i++) {
				System.out.printf("%-5d   %-10d %-10s     %-20s %-20s %10d    %-10s", (i + 1),
						ordersList.get(i).getOrderId(), ordersList.get(i).getUserDto().getUserName(),
						ordersList.get(i).getUserDto().getPhoneNumber(), ordersList.get(i).getAddress(),
						ordersList.get(i).getTotalPrice(), ordersList.get(i).getOrderDetailsDto().getDeliveryStatus());
				System.out.println();
			}
			System.out.println("---------------------------------------- " + (currentPage + 1) + " 페이지 "
					+ "----------------------------------------");
			System.out.println();
			System.out.println("1.[이전 페이지] | 2.[다음 페이지] | 3.[주문 목록 상세 조회] | 4.[배송 상태 변경] | 5.[페이지 나가기]");
			System.out.print("메뉴를 입력하세요: ");
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
				if (currentPage == lastPage) {
					System.out.println("마지막 페이지 입니다.");
					System.out.println();
					continue;
				} else {
					currentPage += 1;
					currentIndex += 5;
					if (currentPage == lastPage) {
						plus = lastIndex - currentIndex;
					} else {
						plus = 5;
					}
				}
			}
			// 3. 주문 목록 상세 조회
			else if (count == 3) {
				getAllOrderDetailsList();
			}
			// 4. 배송 상태 변경
			else if (count == 4) {
				updateOrderStatus();
				ordersList = orderDao.getAllOrders();
			}
			// 나가기
			else {
				break;
			}
		}
	}

	// 주문 목록 상세 조회
	public static void getAllOrderDetailsList() {
		List<OrderDetailsDTO> orderDetailsList = new ArrayList<>();
		orderDetailsList = orderDetailsDao.getOrderDeatils();

		int currentPage = 0;
		int currentIndex = 0;

		int lastIndex = orderDetailsList.size();
		int lastPage = lastIndex / 5;

		int plus = 5;

		System.out.println(
				"---------------------------------------- [주문 상세 목록 조회] ----------------------------------------");
		while (true) {
			System.out.println("--------------------------------------------- " + (currentPage + 1) + " 페이지 "
					+ "---------------------------------------------");
			System.out.printf(
					"상세 주문 번호 | 주문 번호 | 상품 주문 개수 | 남은 상품 재고 |  배송 상태    | 주문자 아이디 | 총 주문 금액 |    배송지        | 주문 상품 이름 |");
			System.out.println();
			for (int i = currentIndex; i < (currentIndex + plus); i++) {
				System.out.printf("%d번\t   %d번\t   %d개\t       %d개\t           %s\t %s\t %s\t   %s\t %s\t",
						orderDetailsList.get(i).getOrderDetailId(), orderDetailsList.get(i).getOrderId(),
						orderDetailsList.get(i).getProductCount(),
						orderDetailsList.get(i).getProductDto().getProductStock(),
						orderDetailsList.get(i).getDeliveryStatus(), orderDetailsList.get(i).getOrderDto().getUserId(),
						orderDetailsList.get(i).getOrderDto().getTotalPrice(),
						orderDetailsList.get(i).getOrderDto().getAddress(),
						orderDetailsList.get(i).getProductDto().getProductName());
				System.out.println();
			}
			System.out.println(
					"---------------------------------------- [주문 상세 목록 조회] ----------------------------------------");
			System.out.println();
			System.out.println("1.[이전 페이지] | 2.[다음 페이지] | 3.[뒤로 가기]");

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
				if (currentPage == lastPage) {
					System.out.println("마지막 페이지 입니다.");
					System.out.println();
					continue;
				} else {
					currentPage += 1;
					currentIndex += 5;
					if (currentPage == lastPage) {
						plus = lastIndex - currentIndex;
					} else {
						plus = 5;
					}
				}
			}
			// 3. 나가기
			else {
				break;
			}
		}
	}

	// 배송 상태 변경
	public static void updateOrderStatus() {
		System.out.print("배송 상태를 변경할 주문 번호를 입력하세요: ");
		int orderId = sc.nextInt();
		sc.nextLine();
		// 변경할 주문 번호 잘못 입력 시
		if (!orderDao.orderCheckByOrderId(orderId)) {
			System.out.println("변경할 주문 번호를 잘 못 입력하였습니다.");
			System.out.println();
			return;
		}
		System.out.print("배송 상태를 입력하세요 [1.상품 준비중 | 2.배송중 | 3.배송 완료] : ");
		int statusId = sc.nextInt();
		sc.nextLine();
		String orderStatus = "";
		if (statusId == 1) {
			orderStatus = "상품 준비중";
		} else if (statusId == 2) {
			orderStatus = "배송중";
		} else if (statusId == 3) {
			orderStatus = "배송 완료";
		}
		System.out.println();
		int count = orderDetailsDao.updateOrderDetailStatusByOrderId(orderId, orderStatus);
	}
}
