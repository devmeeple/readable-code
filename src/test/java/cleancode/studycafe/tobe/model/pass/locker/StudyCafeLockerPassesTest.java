package cleancode.studycafe.tobe.model.pass.locker;

import cleancode.studycafe.tobe.model.pass.StudyCafePassType;
import cleancode.studycafe.tobe.model.pass.StudyCafeSeatPass;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
class StudyCafeLockerPassesTest {

    @DisplayName("보유한 사물함 이용권을 조회한다.")
    @Test
    void findLockerPassBy() {
        // given
        StudyCafeSeatPass seatPass = StudyCafeSeatPass.of(StudyCafePassType.FIXED, 30, 250000, 0.1);
        StudyCafeLockerPasses lockerPasses = StudyCafeLockerPasses.of(List.of());

        // when
        Optional<StudyCafeLockerPass> sut = lockerPasses.findLockerPassBy(seatPass);

        // then
        assertThat(sut).isEmpty();
    }
}
