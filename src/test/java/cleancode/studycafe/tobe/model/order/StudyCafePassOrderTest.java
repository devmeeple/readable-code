package cleancode.studycafe.tobe.model.order;

import cleancode.studycafe.tobe.model.pass.StudyCafePassType;
import cleancode.studycafe.tobe.model.pass.StudyCafeSeatPass;
import cleancode.studycafe.tobe.model.pass.locker.StudyCafeLockerPass;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StudyCafePassOrderTest {

    @DisplayName("선택한 이용권, 사물함 여부에 따른 최종 금액을 계산한다.")
    @Test
    void getTotalPrice() {
        // given
        StudyCafeSeatPass seatPass = StudyCafeSeatPass.of(StudyCafePassType.FIXED, 30, 250000, 0.1);
        StudyCafeLockerPass lockerPass = StudyCafeLockerPass.of(StudyCafePassType.FIXED, 30, 10000);
        StudyCafePassOrder passOrder = StudyCafePassOrder.of(seatPass, lockerPass);

        // when
        int sut = passOrder.getTotalPrice();

        // then
        assertThat(sut).isEqualTo(235000);
    }

    @DisplayName("좌석 이용권 할인 금액을 계산한다.")
    @Test
    void getDiscountPrice() {
        // given
        StudyCafeSeatPass seatPass = StudyCafeSeatPass.of(StudyCafePassType.WEEKLY, 14, 100000, 0.1);
        StudyCafePassOrder passOrder = StudyCafePassOrder.of(seatPass, null);

        // when
        int sut = passOrder.getDiscountPrice();

        // then
        assertThat(sut).isEqualTo(10000);
    }

}
