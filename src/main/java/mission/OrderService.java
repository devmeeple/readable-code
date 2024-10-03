package mission;

import java.util.logging.Logger;

public class OrderService {
    private final Logger log = Logger.getLogger(this.getClass().getName());

    public boolean validateOrder(Order order) {
        if (order.isEmpty()) {
            log.info("주문 항목이 없습니다.");
            return false;
        }

        if (order.isNotValidTotalPrice()) {
            log.info("올바르지 않은 총 가격입니다.");
            return false;
        }

        if (order.hasNoCustomerInfo()) {
            log.info("사용자 정보가 없습니다.");
            return false;
        }
        
        return true;
    }
}
