package cleancode.studycafe.tobe.model.pass.locker;

import cleancode.studycafe.tobe.model.pass.StudyCafePassType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StudyCafeLockerPassTest {

    @DisplayName("이용권이 다르면 선택할 수 없다.")
    @Test
    void isSamePassType() {
        // given
        StudyCafePassType passType = StudyCafePassType.WEEKLY;
        StudyCafeLockerPass lockerPass = StudyCafeLockerPass.of(StudyCafePassType.FIXED, 30, 10000);

        // when
        boolean sut = lockerPass.isSamePassType(passType);

        // then
        assertFalse(sut);
    }
}
