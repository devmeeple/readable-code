package cleancode.studycafe.tobe.model.pass;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class StudyCafeSeatPassesTest {

    @DisplayName("구매 가능한 이용권을 조회한다.")
    @Test
    void findPassBy() {
        // given
        StudyCafeSeatPass seatPass = StudyCafeSeatPass.of(StudyCafePassType.FIXED, 30, 250000, 0.1);
        StudyCafeSeatPasses seatPasses = StudyCafeSeatPasses.of(List.of(seatPass));

        // when
        List<StudyCafeSeatPass> sut = seatPasses.findPassBy(StudyCafePassType.FIXED);

        // then
        assertThat(sut).hasSize(1);
    }
}
