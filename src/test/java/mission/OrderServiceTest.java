package mission;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class OrderServiceTest {

    private final OrderService orderService = new OrderService();

    @DisplayName("[성공] 주문 검증이 성공하면 주문을 추가한다.")
    @Test
    void validateOrder() {
        // given
        Item item = Item.of("동파육", 12000);
        Customer customer = Customer.of("유재석", "rebekha_latsonl1fi@trains.iyp");
        Order order = Order.createOrder(
                List.of(item),
                12000,
                customer
        );

        // when
        boolean sut = orderService.validateOrder(order);

        // then
        assertThat(sut).isTrue();
    }
}
