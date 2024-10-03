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

    @DisplayName("[실패] 상품을 추가하지 않으면 주문할 수 없다.")
    @Test
    void validateOrder_Fail_EmptyItems() {
        // given
        Customer customer = Customer.of("유재석", "rebekha_latsonl1fi@trains.iyp");
        Order order = Order.createOrder(
                List.of(),
                12000,
                customer
        );

        // when
        boolean sut = orderService.validateOrder(order);

        // then
        assertThat(sut).isFalse();
    }

    @DisplayName("[실패] 총 가격이 0원이면 주문할 수 없다.")
    @Test
    void validateOrder_Fail_InvalidTotalPrice() {
        // given
        Item item = Item.of("동파육", 12000);
        Customer customer = Customer.of("유재석", "rebekha_latsonl1fi@trains.iyp");
        Order order = Order.createOrder(
                List.of(item),
                0,
                customer
        );

        // when
        boolean sut = orderService.validateOrder(order);

        // then
        assertThat(sut).isFalse();
    }

    @DisplayName("[실패] 사용자 정보가 없으면 주문할 수 없다.")
    @Test
    void validateOrder_Fail_NoCustomer() {
        // given
        Item item = Item.of("동파육", 12000);
        Order order = Order.createOrder(
                List.of(item),
                12000,
                null
        );

        // when
        boolean sut = orderService.validateOrder(order);

        // then
        assertThat(sut).isFalse();
    }
}
