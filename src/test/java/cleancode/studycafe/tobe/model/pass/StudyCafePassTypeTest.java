package cleancode.studycafe.tobe.model.pass;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StudyCafePassTypeTest {

    @DisplayName("[성공] 1인 고정석은 사물함을 구매할 수 있다.")
    @Test
    void isLockerType() {
        // given
        StudyCafePassType passType = StudyCafePassType.FIXED;

        // when
        boolean sut = passType.isLockerType();

        // then
        assertThat(sut).isTrue();
    }

    @DisplayName("시간 이용권은 사물함을 구매할 수 없다.")
    @Test
    void isLockerTypeWithHourly() {
        // given
        StudyCafePassType passType = StudyCafePassType.HOURLY;

        // when
        boolean sut = passType.isLockerType();

        // then
        assertThat(sut).isFalse();
    }

    @DisplayName("주단위 이용권은 사물함을 구매할 수 없다.")
    @Test
    void isLockerTypeWithWeekly() {
        // given
        StudyCafePassType passType = StudyCafePassType.WEEKLY;

        // when
        boolean sut = passType.isLockerType();

        // then
        assertThat(sut).isFalse();
    }
}
