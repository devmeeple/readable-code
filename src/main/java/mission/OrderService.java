package mission;

public class OrderService {
    private final LoggerService logger = new LoggerService();

    public boolean validateOrder(Order order) {
        if (order.isEmpty()) {
            logger.logInfo("주문 항목이 없습니다.");
            return false;
        }

        if (order.isNotValidTotalPrice()) {
            logger.logInfo("올바르지 않은 총 가격입니다.");
            return false;
        }

        if (order.hasNoCustomerInfo()) {
            logger.logInfo("사용자 정보가 없습니다.");
            return false;
        }

        return true;
    }
}
